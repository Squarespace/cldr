package com.squarespace.cldr.codegen.reader;

import static com.squarespace.cldr.codegen.Utils.findChild;
import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import com.google.common.collect.Streams;
import com.google.common.io.Resources;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squarespace.cldr.codegen.LocaleID;
import com.squarespace.cldr.codegen.Utils;
import com.squarespace.cldr.codegen.parse.PluralRuleGrammar;
import com.squarespace.cldr.codegen.parse.PluralType;
import com.squarespace.cldr.codegen.reader.DateTimeData.Format;
import com.squarespace.cldr.codegen.reader.DateTimeData.Skeleton;
import com.squarespace.cldr.codegen.reader.DateTimeData.Variants;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZone;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZoneEntry;
import com.squarespace.cldr.codegen.reader.TimeZoneData.MetaZoneInfo;
import com.squarespace.cldr.codegen.reader.TimeZoneData.TimeZoneInfo;
import com.squarespace.cldr.codegen.reader.TimeZoneData.TimeZoneName;
import com.squarespace.cldr.numbers.NumberPattern;
import com.squarespace.cldr.numbers.NumberPatternParser;
import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Struct;


/**
 * Reads CLDR data files.
 */
public class DataReader {

  private static final List<String> FILENAMES_MUST_EXIST = Arrays.asList(
      "aliases.json",
      "availableLocales.json",
      "ca-gregorian.json",
      "currencies.json",
      "currencyData.json",
      "defaultContent.json",
      "likelySubtags.json",
      "metaZones.json",
      "numbers.json",
      "ordinals.json",
      "plurals.json",
      "timeZoneNames.json",
      "weekData.json"
  );

  /**
   * Parse to read our data files.
   */
  private static final JsonParser JSON_PARSER = new JsonParser();

  /**
   * Global instance holding all parsed locale data.
   */
  private static DataReader instance = new DataReader();

  /**
   * Relative paths to the file trees to walk and parse.
   */
  private static final String[] FILES = new String[] {
      "data/cldr-core/",
      "data/cldr-dates-modern/main/",
      "data/cldr-numbers-modern/main/"
  };

  private static final String[] PATCHES = new String[] {
      "patches/"
  };
  
  /**
   * Index into the weekday maps.
   */
  private static String[] WEEKDAY_KEYS = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

  /**
   * Mapping of weekday to 1-based index.
   */
  private static Map<String, Integer> NUMERIC_DAYS = new HashMap<>();

  static {
    for (int i = 0; i < WEEKDAY_KEYS.length; i++) {
      NUMERIC_DAYS.put(WEEKDAY_KEYS[i], i + 1);
    }
  }
  
  private final Map<String, JsonObject> patches = new LinkedHashMap<>();
  private final List<String> availableLocales = new ArrayList<>();
  private final List<LocaleID> defaultContent = new ArrayList<>();
  private final Map<LocaleID, DateTimeData> calendars = new HashMap<>();
  private final Map<LocaleID, TimeZoneData> timezones = new HashMap<>();
  private final Map<String, MetaZone> metazones = new HashMap<>();
  private final Map<String, String> timezoneAliases = new LinkedHashMap<>();
  private final Map<LocaleID, NumberData> numbers = new HashMap<>();
  private final Map<String, CurrencyData> currencies = new LinkedHashMap<>();
  private final Map<String, PluralData> ordinals = new HashMap<>();
  private final Map<String, PluralData> cardinals = new HashMap<>();
  private final Map<String, String> likelySubtags = new HashMap<>();
  private final NumberPatternParser numberPatternParser = new NumberPatternParser();
  
  private final DateTimeFormatter metazoneFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
      .withZone(ZoneId.of("UTC"));
  
  /**
   * Flag indicating whether we've populated the global DataReader instance.
   */
  private boolean initialized;

  private DataReader() {
  }

  public static void main(String[] args) throws Exception {
    DataReader.get();
  }

  /**
   * Returns the global DataReader instance, populating it if necessary.
   */
  public static DataReader get() throws IOException {
    if (!instance.initialized) {
      long start = System.currentTimeMillis();
      instance.init();
      long elapsed = System.currentTimeMillis() - start;
      System.out.printf("Loaded CLDR data in %d ms.\n", elapsed);
    }
    return instance;
  }

  public List<LocaleID> getDefaultContent() {
    return defaultContent;
  }
  
  public List<String> getAvailableLocales() {
    return availableLocales;
  }

  /**
   * Returns all calendar related locale data.
   */
  public Map<LocaleID, DateTimeData> calendars() {
    return calendars;
  }

  /**
   * List of all timezones by locale.
   */
  public Map<LocaleID, TimeZoneData> timezones() {
    return timezones;
  }

  /**
   * List of timezone aliases, e.g. in case of deprecation.
   */
  public Map<String, String> timezoneAliases() {
    return timezoneAliases;
  }

  /**
   * List of all metazones.
   */
  public Map<String, MetaZone> metazones() {
    return metazones;
  }
  
  /**
   * Return pluralization rules for ordinal numbers.
   */
  public Map<String, PluralData> ordinals() {
    return ordinals;
  }

  /**
   * Return pluralization rules for cardinal numbers.
   */
  public Map<String, PluralData> cardinals() {
    return cardinals;
  }

  /**
   * List of likely subtags, to assist with locale matching.
   */
  public Map<String, String> likelySubtags() {
    return likelySubtags;
  }

  /**
   * Number formatting data.
   */
  public Map<LocaleID, NumberData> numbers() {
    return numbers;
  }

  /**
   * Currency formatting data.
   */
  public Map<String, CurrencyData> currencies() {
    return currencies;
  }

  /**
   * Initialize the global DataReader instance.
   */
  private void init() throws IOException {
    Map<String, Integer> minDays = null;
    Map<String, Integer> firstDays = null;

    loadPatches();
    
    List<Path> resources = Utils.listResources(FILES);
    sanityCheck(resources);
    for (Path path : resources) {
      String fileName = path.getFileName().toString();
      if (!fileName.endsWith(".json")) {
        continue;
      }

      JsonObject root = load(path);
      switch (fileName) {

        case "aliases.json":
        {
          parseTimeZoneAliases(root);
          break;
        }
        
        case "availableLocales.json":
          JsonObject locales = resolve(root, "availableLocales");
          for (JsonElement elem : locales.getAsJsonArray("modern")) {
            availableLocales.add(elem.getAsString());
          }
          break;

        case "ca-gregorian.json":
        {
          String code = localeCode(root);
          LocaleID id = parseIdentity(code, root);
          root = resolve(root, "main", code);
          DateTimeData data = parseCalendar(root);
          data.id = id;
          calendars.put(id, data);
          break;
        }

        case "currencies.json":
        {
          String code = localeCode(root);
          LocaleID id = parseIdentity(code, root);
          root = resolve(root, "main", code);
          parseCurrencyData(id, code, root);
          break;
        }

        case "currencyData.json":
          parseSupplementalCurrencyData(root);
          break;

        case "defaultContent.json":
          parseDefaultContent(root);
          break;
          
        case "likelySubtags.json":
          parseLikelySubtags(root);
          break;

        case "metaZones.json":
        {
          parseMetaZones(root);
          break;
        }
          
        case "numbers.json":
        {
          String code = localeCode(root);
          root = patch(fileName, code, root);
          LocaleID id = parseIdentity(code, root);
          root = resolve(root, "main", code);
          parseNumberData(id, code, root);
          break;
        }

        case "ordinals.json":
          parsePlurals(root, "plurals-type-ordinal", ordinals);
          break;

        case "plurals.json":
          parsePlurals(root, "plurals-type-cardinal", cardinals);
          break;

        case "timeZoneNames.json":
        {
          String code = localeCode(root);
          LocaleID id = parseIdentity(code, root);
          TimeZoneData data = parseTimeZoneData(code, root);
          timezones.put(id, data);
          break;
        }
        
        case "weekData.json":
          minDays = parseMinDays(root);
          firstDays = parseFirstDays(root);
          break;
      }
    }
    
    Integer defaultFirstDay = firstDays.get("001");
    Integer defaultMinDays = minDays.get("001");

    for (Map.Entry<LocaleID, DateTimeData> entry : calendars.entrySet()) {
      String territory = entry.getKey().territory;
      DateTimeData data = entry.getValue();

      Integer value = minDays.getOrDefault(territory, defaultMinDays);
      data.minDays = value;

      value = firstDays.getOrDefault(territory, defaultFirstDay);
      data.firstDay = value - 1;
    }
  }

  /**
   * Load patches which override values in the CLDR data. Use this to restore
   * certain cases to our preferred form.
   */
  private void loadPatches() throws IOException {
    List<Path> paths = Utils.listResources(PATCHES);
    for (Path path : paths) {
      String fileName = path.getFileName().toString();
      if (fileName.endsWith(".json")) {
        JsonObject root = load(path);
        patches.put(fileName, root);
      }
    }
  }
  
  /**
   * Apply a patch to the given filename, if applicable.
   */
  private JsonObject patch(String fileName, String code, JsonObject root) {
    for (Map.Entry<String, JsonObject> entry : patches.entrySet()) {
      JsonObject patch = entry.getValue();
      if (!patch.get("filename").getAsString().equals(fileName)) {
        continue;
      }
      
      // Check if this patch should be applied to the locale we're processing/
      JsonArray locales = patch.getAsJsonArray("locales");
      boolean shouldPatch = false;
      for (JsonElement locale : locales) {
        if (locale.getAsString().equals(code)) {
          shouldPatch = true;
          break;
        }
      }
      
      if (!shouldPatch) {
        return root;
      }
  
      JsonObject src = resolve(patch, "patch");
      JsonObject dst = root;
      if (patch.get("type").getAsString().equals("main")) {
        dst = resolve(root, "main", code);
      }
      
      System.out.printf("Applying patch %s to %s\n", entry.getKey(), code);
      patchObject(src, dst);
    }
    
    return root;
    
  }

  /**
   * Overlay nested values from src on top of dst.
   */
  private void patchObject(JsonObject src, JsonObject dst) {
    for (String key : objectKeys(src)) {
      JsonElement se = src.get(key);
      JsonElement de = dst.get(key);
      if (de == null) {
        continue;
      }
      if (se.isJsonObject() && de.isJsonObject()) {
        patchObject(se.getAsJsonObject(), de.getAsJsonObject());
        continue;
      }
      if (se.isJsonPrimitive() && de.isJsonPrimitive()) {
        dst.add(key, se);
      }
    }
  }
  
  /**
   * Blow up if required paths do not exist.
   */
  private void sanityCheck(List<Path> resources) {
    Set<String> fileNames = resources.stream()
        .map(p -> p.getFileName().toString())
        .collect(Collectors.toSet());
    for (String name : FILENAMES_MUST_EXIST) {
      if (!fileNames.contains(name)) {
        String msg = String.format("CLDR DATA MISSING! Can't find '%s'. "
            + " You may need to run 'git submodule update'", name);
        throw new RuntimeException(msg);
      }
    }
  }

  /**
   * Loads and parses a JSON file, returning a reference to its root object node.
   */
  private JsonObject load(Path path) throws IOException {
    try (InputStream stream = Resources.getResource(path.toString()).openStream()) {
      return (JsonObject) JSON_PARSER.parse(new InputStreamReader(stream));
    }
  }

  /**
   * Parse the locale identity header from a calendar JSON tree.
   */
  private LocaleID parseIdentity(String code, JsonObject root) throws IOException {
    JsonObject node = resolve(root, "main", code, "identity");
    return new LocaleID(
        string(node, "language"),
        string(node, "territory"),
        string(node, "script"),
        string(node, "variant"));
  }

  /**
   * Parse defaultContent, locales that are defined in terms of the base language locale.
   * For example, en-US is American English and uses the language locale en.
   * See: http://cldr.unicode.org/translation/default-content
   */
  private void parseDefaultContent(JsonObject root) {
    Streams.stream(root.getAsJsonArray("defaultContent"))
      .forEach(e -> defaultContent.add(LocaleID.parse(e.getAsString())));
  }
  
  /**
   * Parse likely subtags tree.
   */
  private void parseLikelySubtags(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "likelySubtags");
    for (Map.Entry<String, JsonElement> entry : node.entrySet()) {
      likelySubtags.put(entry.getKey(), entry.getValue().getAsString());
    }
  }

  /**
   * Parse calendar date-time attributes.
   */
  private DateTimeData parseCalendar(JsonObject root) throws IOException {
    DateTimeData data = new DateTimeData();
    JsonObject node = resolve(root, "dates", "calendars", "gregorian");

    data.eras = parseEras(node);
    data.dayPeriodsFormat = parseDayPeriods(node, "format");
    data.dayPeriodsStandalone = parseDayPeriods(node, "stand-alone");
    data.monthsFormat = parseMonths(node, "format");
    data.monthsStandalone = parseMonths(node, "stand-alone");
    data.weekdaysFormat = parseWeekdays(node, "format");
    data.weekdaysStandalone = parseWeekdays(node, "stand-alone");
    data.quartersFormat = parseQuarters(node, "format");
    data.quartersStandalone = parseQuarters(node, "stand-alone");
    data.dateFormats = parseDateFormats(node);
    data.timeFormats = parseTimeFormats(node);
    data.dateTimeFormats = parseDateTimeFormats(node);
    data.dateTimeSkeletons = parseDateTimeSkeletons(node);

    // date time intervals and fallback format
    data.intervalFormats = new HashMap<>();
    node = resolve(node, "dateTimeFormats", "intervalFormats");
    for (String key : objectKeys(node)) {
      if (key.equals("intervalFormatFallback")) {
        data.intervalFallbackFormat = string(node, key);
      } else {
        Map<String, String> format = data.intervalFormats.get(key);
        if (format == null) {
          format = new HashMap<>();
          data.intervalFormats.put(key, format);
        }
        
        JsonObject fieldNode = node.getAsJsonObject(key);
        for (String field : objectKeys(fieldNode)) {
          if (!field.endsWith("-alt-variant")) {
            format.put(field, string(fieldNode, field));
          }
        }
      }
    }
    
    return data;
  }

  /**
   * Parse era information (BC, AD).
   */
  private Variants parseEras(JsonObject calendar) {
    JsonObject node = resolve(calendar, "eras");

    JsonObject abbr = resolve(node, "eraAbbr");
    JsonObject narrow = resolve(node, "eraNarrow");
    JsonObject wide = resolve(node, "eraNames");

    return new Variants(
        new String[] { string(abbr, "0"), string(abbr, "1") },
        new String[] { string(narrow, "0"), string(narrow, "1") },
        new String[] { },
        new String[] { string(wide, "0"), string(wide, "1") });
  }

  /**
   * Parse day period information (AM, PM).
   */
  private Variants parseDayPeriods(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "dayPeriods", group);

    JsonObject abbr = resolve(node, "abbreviated");
    JsonObject narrow = resolve(node, "narrow");
    JsonObject wide = resolve(node, "wide");

    return new Variants(
        new String[] { string(abbr, "am"), string(abbr, "pm") },
        new String[] { string(narrow, "am"), string(narrow, "pm") },
        new String[] { },
        new String[] { string(wide, "am"), string(wide, "pm") });
  }

  /**
   * Parse month names of varying widths (N, Nov., November).
   */
  private Variants parseMonths(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "months", group);
    return new Variants(
        arrayMap(resolve(node, "abbreviated"), 12),
        arrayMap(resolve(node, "narrow"), 12),
        new String[] { },
        arrayMap(resolve(node, "wide"), 12));
  }

  /**
   * Parse week day names of varying widths (W, Wed., Wednesday)
   */
  private Variants parseWeekdays(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "days", group);
    return new Variants(
        weekdayMap(resolve(node, "abbreviated")),
        weekdayMap(resolve(node, "narrow")),
        weekdayMap(resolve(node, "short")),
        weekdayMap(resolve(node, "wide")));
  }

  /**
   * Parse quarter names of varying widths (Q1, 1st Quarter)
   */
  private Variants parseQuarters(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "quarters", group);
    return new Variants(
        arrayMap(resolve(node, "abbreviated"), 4),
        arrayMap(resolve(node, "narrow"), 4),
        new String[] { },
        arrayMap(resolve(node, "wide"), 4));
  }

  /**
   * Parse standard date formats of varying sizes.
   */
  private Format parseDateFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateFormats");
    return new Format(
      string(node, "short"),
      string(node, "medium"),
      string(node, "long"),
      string(node, "full"));
  }

  /**
   * Parse standard time formats of varying sizes.
   */
  private Format parseTimeFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "timeFormats");
    return new Format(
        string(node, "short"),
        string(node, "medium"),
        string(node, "long"),
        string(node, "full"));
  }

  /**
   * Parse standard date-time wrapper formats of varying sizes.
   */
  private Format parseDateTimeFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateTimeFormats");
    return new Format(
        string(node, "short"),
        string(node, "medium"),
        string(node, "long"),
        string(node, "full"));
  }

  /**
   * Parse date-time skeletons.
   */
  private List<Skeleton> parseDateTimeSkeletons(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateTimeFormats", "availableFormats");
    List<Skeleton> value = new ArrayList<>();
    for (String key : objectKeys(node)) {
      value.add(new Skeleton(key, string(node, key)));
    }
    return value;
  }

  /**
   * Parse the "min days" values. This indicates the minimum number of days
   * a week must include for it to be counted as the first week of the year;
   * otherwise it's the last week of the previous year.
   */
  private Map<String, Integer> parseMinDays(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "weekData", "minDays");
    Map<String, Integer> m = new HashMap<>();
    for (String key : objectKeys(node)) {
      m.put(key, Integer.valueOf(string(node, key)));
    }
    return m;
  }

  /**
   * Parse the "first day" values. This is the value of the first day of the week
   * in a calendar view.
   */
  private Map<String, Integer> parseFirstDays(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "weekData", "firstDay");
    Map<String, Integer> m = new HashMap<>();
    for (String key : objectKeys(node)) {
      String value = string(node, key);
      m.put(key, NUMERIC_DAYS.get(value));
    }
    return m;
  }

  /**
   * Extract weekday names from a locale's mapping.
   */
  private String[] weekdayMap(JsonObject node) {
    String[] res = new String[7];
    for (int i = 0; i < 7; i++) {
      res[i] = node.get(WEEKDAY_KEYS[i]).getAsString();
    }
    return res;
  }

  /**
   * Parse pluralization rules. These rules indicate which pluralization category
   * is in effect, based on the value of the plural operands which are computed
   * from an integer or decimal value.
   */
  private void parsePlurals(JsonObject root, String type, Map<String, PluralData> map) {
    JsonObject node = resolve(root, "supplemental", type);
    for (Map.Entry<String, JsonElement> entry : node.entrySet()) {
      String language = entry.getKey();
      PluralData data = new PluralData();
      for (Map.Entry<String, JsonElement> ruleNode : entry.getValue().getAsJsonObject().entrySet()) {
        String category = ruleNode.getKey().replaceFirst("^pluralRule-count-", "");
        String value = ruleNode.getValue().getAsString();

        Maybe<Pair<Node<PluralType>, CharSequence>> result = PluralRuleGrammar.parse(value);
        if (result.isNothing()) {
          throw new IllegalArgumentException(format("failed to parse rule: \"%s\"", StringEscapeUtils.escapeJava(value)));
        }

        Struct<PluralType> rule = result.get()._1.asStruct();
        Node<PluralType> conditionNode = findChild(rule, PluralType.OR_CONDITION);
        Node<PluralType> sampleNode = findChild(rule, PluralType.SAMPLE);
        String sample = sampleNode == null ? "" : (String) sampleNode.asAtom().value();
        data.add(category, new PluralData.Rule(value, conditionNode, sample));
      }
      map.put(language, data);
    }
  }

  /**
   * Parse number and some currency data for locale.
   */
  private NumberData parseNumberData(LocaleID id, String code, JsonObject root) {
    NumberData data = getNumberData(id);

    JsonObject numbers = resolve(root, "numbers");
    data.minimumGroupingDigits = Integer.valueOf(string(numbers, "minimumGroupingDigits"));

    JsonObject symbols = resolve(numbers, "symbols-numberSystem-latn");
    data.decimal = string(symbols, "decimal");
    data.group = string(symbols, "group");
    data.percent = string(symbols, "percentSign");
    data.plus = string(symbols, "plusSign");
    data.minus = string(symbols, "minusSign");
    data.exponential = string(symbols, "exponential");
    data.superscriptingExponent = string(symbols, "superscriptingExponent");
    data.perMille = string(symbols, "perMille");
    data.infinity = string(symbols, "infinity");
    data.nan = string(symbols, "nan");

    // The fields 'currencyDecimal' and 'currencyGroup' are only defined for a few locales
    data.currencyDecimal = string(symbols, "currencyDecimal", data.decimal);
    data.currencyGroup = string(symbols, "currencyGroup", data.group);

    JsonObject decimalFormats = resolve(numbers, "decimalFormats-numberSystem-latn");
    data.decimalFormatStandard = getNumberPattern(string(decimalFormats, "standard"));
    data.decimalFormatShort = getPluralNumberPattern(resolve(decimalFormats, "short", "decimalFormat"));
    data.decimalFormatLong = getPluralNumberPattern(resolve(decimalFormats, "long", "decimalFormat"));

    JsonObject percentFormats = resolve(numbers, "percentFormats-numberSystem-latn");
    data.percentFormatStandard = getNumberPattern(string(percentFormats, "standard"));

    JsonObject currencyFormats = resolve(numbers, "currencyFormats-numberSystem-latn");
    data.currencyFormatStandard = getNumberPattern(string(currencyFormats, "standard"));
    data.currencyFormatAccounting = getNumberPattern(string(currencyFormats, "accounting"));
    data.currencyFormatShort = getPluralNumberPattern(resolve(currencyFormats, "short", "standard"));

    data.currencyUnitPattern = new HashMap<>();
    for (String key : objectKeys(currencyFormats)) {
      if (key.startsWith("unitPattern-count")) {
        data.currencyUnitPattern.put(key, string(currencyFormats, key));
      }
    }
    return data;
  }

  /**
   * Parse locale-specific currency data.
   */
  private void parseCurrencyData(LocaleID id, String code, JsonObject root) {
    NumberData data = getNumberData(id);
    data.currencySymbols = new LinkedHashMap<>();
    data.narrowCurrencySymbols = new LinkedHashMap<>();
    data.currencyDisplayName = new LinkedHashMap<>();
    data.currencyDisplayNames = new LinkedHashMap<>();
    JsonObject currencies = resolve(root, "numbers", "currencies");
    for (String currencyCode : objectKeys(currencies)) {
      JsonObject node = resolve(currencies, currencyCode);

      data.currencyDisplayName.put(currencyCode, string(node, "displayName"));
      String symbol = string(node, "symbol");
      String narrowSymbol = string(node, "symbol-alt-narrow");
      narrowSymbol = narrowSymbol.equals("") ? symbol : narrowSymbol;
      data.currencySymbols.put(currencyCode, symbol);
      data.narrowCurrencySymbols.put(currencyCode, narrowSymbol);

      Map<String, String> displayNames = new LinkedHashMap<>();
      for (String key : objectKeys(node)) {
        if (key.startsWith("displayName-count")) {
          displayNames.put(key, node.get(key).getAsString());
        }
      }
      data.currencyDisplayNames.put(currencyCode, displayNames);
    }
  }

  /**
   * Parse supplemental currency data.
   */
  private void parseSupplementalCurrencyData(JsonObject root) {
    JsonObject fractions = resolve(root, "supplemental", "currencyData", "fractions");
    for (String code : objectKeys(fractions)) {
      CurrencyData data = getCurrencyData(code);
      JsonObject node = fractions.get(code).getAsJsonObject();
      data.digits = Integer.valueOf(string(node, "_digits"));
      // Note: _rounding = 0 for all currencies in the modern data set.
    }
  }

  /**
   * Get or create the number data object for this locale.
   */
  private NumberData getNumberData(LocaleID id) {
    NumberData data = numbers.get(id);
    if (data == null) {
      data = new NumberData();
      data.setID(id);
      numbers.put(id, data);
    }
    return data;
  }

  /**
   * Get or create the currency data object for this code.
   */
  private CurrencyData getCurrencyData(String code) {
    CurrencyData data = currencies.get(code);
    if (data == null) {
      data = new CurrencyData();
      data.setCode(code);
      currencies.put(code, data);
    }
    return data;
  }

  /**
   * Return a pair of patterns (positive, negative) derived from the given pattern string.
   */
  private List<NumberPattern> getNumberPattern(String pattern) {
    int i = pattern.indexOf(';');
    if (i == -1) {
      return Arrays.asList(
          parseNumberPattern(pattern),
          parseNumberPattern("-" + pattern));
    }
    return Arrays.asList(
        parseNumberPattern(pattern.substring(0, i)),
        parseNumberPattern(pattern.substring(i+1)));
  }

  /**
   * Parse the patterns to attempt to catch errors at code generation time.
   */
  private NumberPattern parseNumberPattern(String pattern) {
    return numberPatternParser.parse(pattern);
  }
  
  /**
   * Parse groups of related formats whose keys include a plural category for the locale.
   * EN:
   *    1000-count-one       "0 thousand"
   *    1000-count-other     "0 thousand"
   * FR:
   *    1000-count-one       "0 millier"
   *    1000-count-other     "0 mille"
   */
  private Map<String, List<NumberPattern>> getPluralNumberPattern(JsonObject node) {
    Map<String, List<NumberPattern>> result = new LinkedHashMap<>();
    for (Map.Entry<String, JsonElement> entry : node.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue().getAsString();
      List<NumberPattern> formats = getNumberPattern(value);
      result.put(key, formats);
    }
    return result;
  }

  private TimeZoneData parseTimeZoneData(String code, JsonObject root) {
    TimeZoneData data = new TimeZoneData();
    
    data.timeZoneInfo = parseTimeZoneInfos(code, root);
    data.metaZoneInfo = parseMetaZoneInfos(code, root);

    JsonObject node = resolve(root, "main", code, "dates", "timeZoneNames");
    data.hourFormat = string(node, "hourFormat");
    data.gmtFormat = string(node, "gmtFormat");
    data.gmtZeroFormat = string(node, "gmtZeroFormat");
    data.regionFormat = string(node, "regionFormat");
    data.regionFormatStandard = string(node, "regionFormat-type-standard");
    data.regionFormatDaylight = string(node, "regionFormat-type-daylight");
    data.fallbackFormat = string(node, "fallbackFormat");
    
    return data;
  }
  
  /**
   * Parse timezone names.
   */
  private List<TimeZoneInfo> parseTimeZoneInfos(String code, JsonObject root) {
    JsonObject node = resolve(root, "main", code, "dates", "timeZoneNames", "zone");
    return parseTimeZoneInfo(node, Collections.emptyList());
  }

  /**
   * Recursively parse the zone labels until we get to a zone info object.
   */
  private List<TimeZoneInfo> parseTimeZoneInfo(JsonObject root, List<String> prefix) {
    List<TimeZoneInfo> zones = new ArrayList<>();
    for (String label : objectKeys(root)) {
      JsonObject value = resolve(root, label);
      List<String> zone = new ArrayList<>(prefix);
      zone.add(label);
      if (isTimeZone(value)) {
        TimeZoneInfo info = parseTimeZoneObject(value, zone);
        zones.add(info);
      } else {
        zones.addAll(parseTimeZoneInfo(value, zone));
      }
    }
    return zones;
  }
  
  private static final String[] TIMEZONE_KEYS = new String[] {
    "exemplarCity", "long", "short"  
  };
 
  /**
   * Returns true if this object looks like it holds TimeZoneInfo, otherwise false.
   */
  private boolean isTimeZone(JsonObject root) {
    for (String key : TIMEZONE_KEYS) {
      if (root.has(key)) {
        return true;
      }
    }
    return false;
  }
  
  private TimeZoneInfo parseTimeZoneObject(JsonObject root, List<String> zone) {
    TimeZoneInfo info = new TimeZoneInfo();
    info.zone = StringUtils.join(zone, '/');
    info.exemplarCity = string(root, "exemplarCity");
    info.nameLong = parseTimeZoneName(root, "long");
    info.nameShort = parseTimeZoneName(root, "short");
    return info;
  }
  
  private TimeZoneName parseTimeZoneName(JsonObject root, String width) {
    JsonObject node = resolve(root, width);
    if (node == null) {
      return null;
    }
    TimeZoneName name = new TimeZoneName();
    name.generic = string(node, "generic", null);
    name.standard = string(node, "standard", null);
    name.daylight = string(node, "daylight", null);
    return name;
  }

  private List<MetaZoneInfo> parseMetaZoneInfos(String code, JsonObject root) {
    JsonObject node = resolve(root, "main", code, "dates", "timeZoneNames", "metazone");
    if (node == null) {
      return Collections.emptyList();
    }
    return parseMetaZoneInfo(node, Collections.emptyList());
  }
  
  private List<MetaZoneInfo> parseMetaZoneInfo(JsonObject root, List<String> prefix) {
    List<MetaZoneInfo> zones = new ArrayList<>();
    for (String label : objectKeys(root)) {
      JsonObject value = resolve(root, label);
      List<String> zone = new ArrayList<>(prefix);
      zone.add(label);
      if (isTimeZone(value)) {
        MetaZoneInfo info = parseMetaZoneObject(value, zone);
        zones.add(info);
      } else {
        zones.addAll(parseMetaZoneInfo(value, zone));
      }
    }
    return zones;
  }

  private MetaZoneInfo parseMetaZoneObject(JsonObject root, List<String> zone) {
    MetaZoneInfo info = new MetaZoneInfo();
    info.zone = StringUtils.join(zone, '/');
    info.nameLong = parseTimeZoneName(root, "long");
    info.nameShort = parseTimeZoneName(root, "short");
    return info;
  }
  
  /**
   * Parses timezone aliases. These are timezone ids that have been removed
   * for a given reason.
   */
  private void parseTimeZoneAliases(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "metadata", "alias", "zoneAlias");
    parseTimeZoneAlias(node, Collections.emptyList());
  }

  /**
   * Recursively parse timezone aliases.
   */
  private void parseTimeZoneAlias(JsonObject root, List<String> prefix) {
    for (String label : objectKeys(root)) {
      JsonObject node = resolve(root, label);
      List<String> zone = new ArrayList<>(prefix);
      zone.add(label);
      String replacement = string(node, "_replacement", null);
      if (replacement != null) {
        String name = StringUtils.join(zone, '/');
        timezoneAliases.put(name, replacement);
      } else {
        parseTimeZoneAlias(node, zone);
      }
    }
  }
  
  /**
   * Parse the metazone mapping from supplemental data.
   */
  private void parseMetaZones(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "metaZones", "metazoneInfo", "timezone");
    for (MetaZone zone : _parseMetaZones(node, Collections.emptyList())) {
      metazones.put(zone.zone, zone);
    }
  }
  
  private List<MetaZone> _parseMetaZones(JsonObject root, List<String> prefix) {
    List<MetaZone> zones = new ArrayList<>();
    for (String label : objectKeys(root)) {
      JsonElement node = root.get(label);
      List<String> zone = new ArrayList<>(prefix);
      zone.add(label);
      if (node.isJsonArray()) {
        // Parse list of metazones
        MetaZone metazone = parseMetaZone(zone, node.getAsJsonArray());
        zones.add(metazone);
      } else {
        zones.addAll(_parseMetaZones(node.getAsJsonObject(), zone));
      }
    }
    return zones;
  }

  private MetaZone parseMetaZone(List<String> prefix, JsonArray zones) {
    MetaZone zone = new MetaZone();
    zone.zone = StringUtils.join(prefix, '/');
    zone.metazones = new ArrayList<>();
    for (JsonElement elem : zones) {
      JsonObject node = resolve(elem.getAsJsonObject(), "usesMetazone");
      MetaZoneEntry entry = new MetaZoneEntry();
      entry.metazone = string(node, "_mzone");
      String from = string(node, "_from", null);
      String to = string(node, "_to", null);
      if (from != null) {
        entry.fromString = from;
        entry.from = parseMetaZoneTimestamp(from);
      }
      if (to != null) {
        entry.toString = to;
        entry.to = parseMetaZoneTimestamp(to);
      }
      zone.metazones.add(entry);
    }
    return zone;
  }
  
  private ZonedDateTime parseMetaZoneTimestamp(String rep) {
    return ZonedDateTime.parse(rep, metazoneFmt);
  }
  
  /**
   * Extract the locale codes from a JSON object.
   */
  private String localeCode(JsonObject root) {
    return objectKeys(root.get("main").getAsJsonObject()).get(0);
  }

  /**
   * Recursively resolve a series of keys against a JSON object. This is used to
   * drill down into a JSON object tree.
   */
  private JsonObject resolve(JsonObject node, String ...keys) {
    for (String key : keys) {
      JsonElement n = node.get(key);
      if (n == null) {
        return null;
      }
      node = n.getAsJsonObject();
    }
    return node;
  }

  /**
   * Extract a value from a JSON object and convert it to a string.
   */
  private String string(JsonObject node, String key) {
    return string(node, key, "");
  }

  /**
   * Extract a value from a JSON object and convert it to a string.
   */
  private String string(JsonObject node, String key, String default_) {
    JsonElement value = node.get(key);
    if (value != null && value.isJsonPrimitive()) {
      return value.getAsString();
    }
    return default_;
  }

  /**
   * Converts a CLDR (1-based index, associative) array to a String array.
   */
  private String[] arrayMap(JsonObject node, int n) {
    String[] res = new String[n];
    for (int i = 0; i < n; i++) {
      res[i] = node.get(Integer.toString(i + 1)).getAsString();
    }
    return res;
  }

  /**
   * Return a list of the keys on a JSON object.
   */
  private List<String> objectKeys(JsonObject obj) {
    List<String> keys = new ArrayList<>();
    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
      keys.add(entry.getKey());
    }
    return keys;
  }

}
