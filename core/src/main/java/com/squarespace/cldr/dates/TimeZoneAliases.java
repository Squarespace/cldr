package com.squarespace.cldr.dates;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Hand-built list of extra timezone aliases, containing all ZoneId identifiers that
 * do not map 1:1 to a CLDR locale identifier or alias. Created using the
 * backward mapping in TZDB v2017b.
 */
public class TimeZoneAliases {

  private static final Map<String, String> ALIASES = new LinkedHashMap<String, String>() {{
    put("Africa/Asmara", "Africa/Asmera");
    put("America/Argentina/Buenos_Aires", "America/Buenos_Aires");
    put("America/Argentina/Catamarca", "America/Catamarca");
    put("America/Argentina/ComodRivadavia", "America/Catamarca");
    put("America/Argentina/Cordoba", "America/Cordoba");
    put("America/Argentina/Jujuy", "America/Jujuy");
    put("America/Argentina/Mendoza", "America/Mendoza");
    put("America/Atikokan", "America/Coral_Harbour");
    put("America/Atka", "America/Adak");
    put("America/Ensenada", "America/Tijuana");
    put("America/Fort_Wayne", "America/Indianapolis");
    put("America/Indiana/Indianapolis", "America/Indianapolis");
    put("America/Kentucky/Louisville", "America/Louisville");
    put("America/Knox_IN", "America/Indiana/Knox");
    put("America/Porto_Acre", "America/Rio_Branco");
    put("America/Rosario", "America/Cordoba");
    put("America/Santa_Isabel", "America/Tijuana");
    put("America/Virgin", "America/Port_of_Spain");
    put("Asia/Ashkhabad", "Asia/Ashgabat");
    put("Asia/Chungking", "Asia/Shanghai");
    put("Asia/Dacca", "Asia/Dhaka");
    put("Asia/Famagusta", null); // TODO: new zone, not in cldr
    put("Asia/Ho_Chi_Minh", "Asia/Saigon");
    put("Asia/Istanbul", "Europe/Istanbul");
    put("Asia/Kathmandu", "Asia/Katmandu");
    put("Asia/Kolkata", "Asia/Calcutta");
    put("Asia/Macao", "Asia/Macau");
    put("Asia/Tel_Aviv", "Asia/Jerusalem");
    put("Asia/Thimbu", "Asia/Thimphu");
    put("Asia/Ujung_Pandang", "Asia/Makassar");
    put("Asia/Ulan_Bator", "Asia/Ulaanbaatar");
    put("Asia/Yangon", "Asia/Rangoon");
    put("Atlantic/Faroe", "Atlantic/Faeroe");
    put("Australia/ACT", "Australia/Sydney");
    put("Australia/Canberra", "Australia/Sydney");
    put("Australia/LHI", "Australia/Lord_Howe");
    put("Australia/NSW", "Australia/Sydney");
    put("Australia/North", "Australia/Darwin");
    put("Australia/Queensland", "Australia/Brisbane");
    put("Australia/South", "Australia/Adelaide");
    put("Australia/Tasmania", "Australia/Hobart");
    put("Australia/Victoria", "Australia/Melbourne");
    put("Australia/West", "Australia/Perth");
    put("Australia/Yancowinna", "Australia/Broken_Hill");
    put("Brazil/Acre", "America/Rio_Branco");
    put("Brazil/DeNoronha", "America/Noronha");
    put("Brazil/East", "America/Sao_Paulo");
    put("Brazil/West", "America/Manaus");
    put("CET", null); // TODO: not in cldr
    put("CST6CDT", "America/Chicago");
    put("Canada/Atlantic", "America/Halifax");
    put("Canada/Central", "America/Winnipeg");
    put("Canada/East-Saskatchewan", "America/Regina"); 
    put("Canada/Eastern", "America/Regina");
    put("Canada/Mountain", "America/Edmonton");
    put("Canada/Newfoundland", "America/St_Johns");
    put("Canada/Pacific", "America/Vancouver");
    put("Canada/Saskatchewan", "America/Regina");
    put("Canada/Yukon", "America/Whitehorse");
    put("Chile/Continental", "America/Santiago");
    put("Chile/EasterIsland", "Pacific/Easter");
    put("Cuba", "America/Havana");
    put("EET", null); // TODO: not in cldr
    put("EST5EDT", "America/New_York");
    put("Egypt", "Africa/Cairo");
    put("Eire", "Europe/Dublin");
    put("Etc/GMT+0", "Etc/GMT");
    put("Etc/GMT+1", "Etc/GMT1");
    put("Etc/GMT+10", "Etc/GMT10");
    put("Etc/GMT+11", "Etc/GMT11");
    put("Etc/GMT+12", "Etc/GMT12");
    put("Etc/GMT+2", "Etc/GMT2");
    put("Etc/GMT+3", "Etc/GMT3");
    put("Etc/GMT+4", "Etc/GMT4");
    put("Etc/GMT+5", "Etc/GMT5");
    put("Etc/GMT+6", "Etc/GMT6");
    put("Etc/GMT+7", "Etc/GMT7");
    put("Etc/GMT+8", "Etc/GMT8");
    put("Etc/GMT+9", "Etc/GMT9");
    put("Etc/GMT-0", "Etc/GMT");
    put("Etc/GMT0", "Etc/GMT");
    put("Etc/Greenwich", "Etc/GMT");
    put("Etc/UCT", "Etc/UTC");
    put("Etc/Universal", "Etc/UTC");
    put("Etc/Zulu", "Etc/UTC");
    put("Europe/Nicosia", "Asia/Nicosia");
    put("Europe/Tiraspol", "Europe/Chisinau");
    put("GB", "Europe/London");
    put("GB-Eire", "Europe/London");
    put("GMT", "Etc/GMT");
    put("GMT0", "Etc/GMT");
    put("Greenwich", "Etc/GMT");
    put("Hongkong", "Asia/Hong_Kong");
    put("Iceland", "Atlantic/Reykjavik");
    put("Iran", "Asia/Tehran");
    put("Israel", "Asia/Jerusalem");
    put("Jamaica", "America/Jamaica");
    put("Japan", "Asia/Tokyo");
    put("Kwajalein", "Pacific/Kwajalein");
    put("Libya", "Africa/Tripoli");
    put("MET", null); // TODO: not in cldr
    put("MST7MDT", "America/Denver");
    put("Mexico/BajaNorte", "America/Tijuana");
    put("Mexico/BajaSur", "America/Mazatlan");
    put("Mexico/General", "America/Mexico_City");
    put("NZ", "Pacific/Auckland");
    put("NZ-CHAT", "Pacific/Chatham");
    put("Navajo", "America/Denver");
    put("PRC", "Asia/Shanghai");
    put("PST8PDT", "America/Los_Angeles");
    put("Pacific/Chuuk", "Pacific/Truk");
    put("Pacific/Pohnpei", "Pacific/Ponape");
    put("Pacific/Samoa", "Pacific/Pago_Pago");
    put("Poland", "Europe/Warsaw");
    put("Portugal", "Europe/Lisbon");
    put("ROK", "Asia/Seoul");
    put("Singapore", "Asia/Singapore");
    put("Turkey", "Europe/Istanbul");
    put("UCT", "Etc/UTC");
    put("US/Alaska", "America/Anchorage");
    put("US/Aleutian", "America/Adak");
    put("US/Arizona", "America/Phoenix");
    put("US/Central", "America/Chicago");
    put("US/East-Indiana", "America/Indianapolis");
    put("US/Eastern", "America/New_York");
    put("US/Hawaii", "Pacific/Honolulu");
    put("US/Indiana-Starke", "America/Indiana/Knox");
    put("US/Michigan", "America/Detroit");
    put("US/Mountain", "America/Denver");
    put("US/Pacific", "America/Los_Angeles");
    put("US/Pacific-New", "America/Los_Angeles");
    put("US/Samoa", "Pacific/Pago_Pago");
    put("UTC", "Etc/UTC");
    put("Universal", "Etc/UTC");
    put("W-SU", "Europe/Moscow");
    put("WET", null); // TODO: not in cldr
    put("Zulu", "Etc/UTC");
  }};
  
  public static String getAlias(String zone) {
    return ALIASES.get(zone);
  }
}
