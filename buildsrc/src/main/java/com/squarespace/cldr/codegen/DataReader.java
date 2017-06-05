package com.squarespace.cldr.codegen;

import static com.squarespace.cldr.codegen.Utils.findChild;
import static groovy.json.StringEscapeUtils.escapeJava;
import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.io.Resources;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squarespace.cldr.codegen.DateTimeData.Format;
import com.squarespace.cldr.codegen.DateTimeData.Skeleton;
import com.squarespace.cldr.codegen.DateTimeData.Variants;
import com.squarespace.cldr.codegen.parse.PluralRuleGrammar;
import com.squarespace.cldr.codegen.parse.PluralType;
import com.squarespace.compiler.common.Maybe;
import com.squarespace.compiler.parse.Node;
import com.squarespace.compiler.parse.Pair;
import com.squarespace.compiler.parse.Struct;


/**
 * Reads CLDR data files.
 */
public class DataReader {

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
      "data/cldr-dates-modern/main/"
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

  private final List<String> availableLocales = new ArrayList<>();

  private final Map<LocaleID, DateTimeData> calendars = new HashMap<>();

  private final Map<String, PluralData> ordinals = new HashMap<>();

  private final Map<String, PluralData> cardinals = new HashMap<>();

  private final Map<String, String> likelySubtags = new HashMap<>();

  /**
   * Flag indicating whether we've populated the global DataReader instance.
   */
  private boolean initialized;

  private DataReader() {
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
   * Return pluralization rules for ordinal numbers.
   */
  public Map<String, PluralData> ordinals() {
    return ordinals;
  }

  /**
   * Return pluralization rules for cardinal numbers.
   * @return
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
   * Initialize the global DataReader instance.
   */
  private void init() throws IOException {
    Map<String, Integer> minDays = null;
    Map<String, Integer> firstDays = null;

    for (Path path : Utils.listResources(FILES)) {
      String fileName = path.getFileName().toString();
      if (!fileName.endsWith(".json")) {
        continue;
      }

      JsonObject root = load(path);
      switch (fileName) {

        case "availableLocales.json":
          JsonObject locales = resolve(root, "availableLocales");
          for (JsonElement elem : locales.getAsJsonArray("modern")) {
            availableLocales.add(elem.getAsString());
          }
          break;

        case "ca-gregorian.json":
          for (String code : localeCodes(root)) {
            LocaleID id = parseIdentity(code, root);
            DateTimeData data = parseCalendar(code, root);
            data.setID(id);
            calendars.put(id, data);
          }
          break;

        case "likelySubtags.json":
          parseLikelySubtags(root);
          break;

        case "ordinals.json":
          parsePlurals(root, "plurals-type-ordinal", ordinals);
          break;

        case "plurals.json":
          parsePlurals(root, "plurals-type-cardinal", cardinals);
          break;

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
      data.setMinDays(value);

      value = firstDays.getOrDefault(territory, defaultFirstDay);
      data.setFirstDay(value - 1);
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
  private DateTimeData parseCalendar(String code, JsonObject root) throws IOException {
    DateTimeData data = new DateTimeData();
    JsonObject node = resolve(root, "main", code, "dates", "calendars", "gregorian");

    data.setEras(parseEras(node));
    data.setDayPeriodsFormat(parseDayPeriods(node, "format"));
    data.setDayPeriodsStandalone(parseDayPeriods(node, "stand-alone"));
    data.setMonthsFormat(parseMonths(node, "format"));
    data.setMonthsStandalone(parseMonths(node, "stand-alone"));
    data.setWeekdaysFormat(parseWeekdays(node, "format"));
    data.setWeekdaysStandalone(parseWeekdays(node, "stand-alone"));
    data.setQuartersFormat(parseQuarters(node, "format"));
    data.setQuartersStandalone(parseQuarters(node, "stand-alone"));
    data.setDateFormats(parseDateFormats(node));
    data.setTimeFormats(parseTimeFormats(node));
    data.setDateTimeFormats(parseDateTimeFormats(node));
    data.setDateTimeSkeletons(parseDateTimeSkeletons(node));

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
          throw new IllegalArgumentException(format("failed to parse rule: \"%s\"", escapeJava(value)));
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
   * Extract the locale codes from a JSON object.
   */
  private List<String> localeCodes(JsonObject root) {
    return objectKeys(root.get("main").getAsJsonObject());
  }

  /**
   * Recursively resolve a series of keys against a JSON object. This is used to
   * drill down into a JSON object tree.
   */
  private JsonObject resolve(JsonObject node, String ...keys) {
    for (String key : keys) {
      node = node.get(key).getAsJsonObject();
    }
    return node;
  }

  /**
   * Extract a value from a JSON object and convert it to a string.
   */
  private String string(JsonObject node, String key) {
    JsonElement value = node.get(key);
    return value == null ? "" : value.getAsString();
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
