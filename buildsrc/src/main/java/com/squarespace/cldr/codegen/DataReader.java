package com.squarespace.cldr.codegen;

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


/**
 * Reads CLDR data files.
 */
public class DataReader {

  private static final String[] FILES = new String[] {
      "data/cldr-core/",
      "data/cldr-dates-modern/main/"
  };

  private static String[] WEEKDAY_KEYS = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

  private static Map<String, Integer> NUMERIC_DAYS = new HashMap<>();

  static {
    for (int i = 0; i < WEEKDAY_KEYS.length; i++) {
      NUMERIC_DAYS.put(WEEKDAY_KEYS[i], i + 1);
    }
  }

  private static final JsonParser JSON_PARSER = new JsonParser();

  private static DataReader instance = new DataReader();

  private final List<String> availableLocales = new ArrayList<>();

  private final Map<LocaleID, DateTimeData> calendars = new HashMap<>();

  private final Map<String, String> likelySubtags = new HashMap<>();

  private boolean initialized;

  private DataReader() {
  }

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

  public Map<LocaleID, DateTimeData> calendars() {
    return calendars;
  }

  public Map<String, String> likelySubtags() {
    return likelySubtags;
  }

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

  private JsonObject load(Path path) throws IOException {
    try (InputStream stream = Resources.getResource(path.toString()).openStream()) {
      return (JsonObject) JSON_PARSER.parse(new InputStreamReader(stream));
    }
  }

  private LocaleID parseIdentity(String code, JsonObject root) throws IOException {
    JsonObject node = resolve(root, "main", code, "identity");
    return new LocaleID(
        string(node, "language"),
        string(node, "territory"),
        string(node, "script"),
        string(node, "variant"));
  }

  private void parseLikelySubtags(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "likelySubtags");
    for (Map.Entry<String, JsonElement> entry : node.entrySet()) {
      likelySubtags.put(entry.getKey(), entry.getValue().getAsString());
    }
  }

  private DateTimeData parseCalendar(String code, JsonObject root) throws IOException {
    DateTimeData data = new DateTimeData();
    JsonObject node = resolveCalendar(code, root);

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

  private Variants parseMonths(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "months", group);
    return new Variants(
        arrayMap(resolve(node, "abbreviated"), 12),
        arrayMap(resolve(node, "narrow"), 12),
        new String[] { },
        arrayMap(resolve(node, "wide"), 12));
  }

  private Variants parseWeekdays(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "days", group);
    return new Variants(
        weekdayMap(resolve(node, "abbreviated")),
        weekdayMap(resolve(node, "narrow")),
        weekdayMap(resolve(node, "short")),
        weekdayMap(resolve(node, "wide")));
  }

  private Variants parseQuarters(JsonObject calendar, String group) {
    JsonObject node = resolve(calendar, "quarters", group);
    return new Variants(
        arrayMap(resolve(node, "abbreviated"), 4),
        arrayMap(resolve(node, "narrow"), 4),
        new String[] { },
        arrayMap(resolve(node, "wide"), 4));
  }

  private Format parseDateFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateFormats");
    return new Format(
      string(node, "short"),
      string(node, "medium"),
      string(node, "long"),
      string(node, "full"));
  }

  private Format parseTimeFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "timeFormats");
    return new Format(
        string(node, "short"),
        string(node, "medium"),
        string(node, "long"),
        string(node, "full"));
  }

  private Format parseDateTimeFormats(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateTimeFormats");
    return new Format(
        string(node, "short"),
        string(node, "medium"),
        string(node, "long"),
        string(node, "full"));
  }

  private List<Skeleton> parseDateTimeSkeletons(JsonObject calendar) {
    JsonObject node = resolve(calendar, "dateTimeFormats", "availableFormats");
    List<Skeleton> value = new ArrayList<>();
    for (String key : objectKeys(node)) {
      value.add(new Skeleton(key, string(node, key)));
    }
    return value;
  }

  private Map<String, Integer> parseMinDays(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "weekData", "minDays");
    Map<String, Integer> m = new HashMap<>();
    for (String key : objectKeys(node)) {
      m.put(key, Integer.valueOf(string(node, key)));
    }
    return m;
  }

  private Map<String, Integer> parseFirstDays(JsonObject root) {
    JsonObject node = resolve(root, "supplemental", "weekData", "firstDay");
    Map<String, Integer> m = new HashMap<>();
    for (String key : objectKeys(node)) {
      String value = string(node, key);
      m.put(key, NUMERIC_DAYS.get(value));
    }
    return m;
  }

  private List<String> localeCodes(JsonObject root) {
    return objectKeys(root.get("main").getAsJsonObject());
  }

  private JsonObject resolveCalendar(String code, JsonObject root) {
    return resolve(root, "main", code, "dates", "calendars", "gregorian");
  }

  private JsonObject resolve(JsonObject node, String ...keys) {
    for (String key : keys) {
      node = node.get(key).getAsJsonObject();
    }
    return node;
  }

  private String string(JsonObject node, String key) {
    JsonElement value = node.get(key);
    return value == null ? "" : value.getAsString();
  }

  private String[] weekdayMap(JsonObject node) {
    String[] res = new String[7];
    for (int i = 0; i < 7; i++) {
      res[i] = node.get(WEEKDAY_KEYS[i]).getAsString();
    }
    return res;
  }

  private String[] arrayMap(JsonObject node, int n) {
    String[] res = new String[n];
    for (int i = 0; i < n; i++) {
      res[i] = node.get(Integer.toString(i + 1)).getAsString();
    }
    return res;
  }

  private List<String> objectKeys(JsonObject obj) {
    List<String> keys = new ArrayList<>();
    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
      keys.add(entry.getKey());
    }
    return keys;
  }

}
