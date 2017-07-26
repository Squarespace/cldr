//
//
// AUTO-GENERATED CLASS - DO NOT EDIT
//
//
package com.squarespace.cldr.numbers;

import com.squarespace.cldr.CLDRLocale;
import com.squarespace.cldr.plurals.PluralCategory;
import java.lang.String;
import java.lang.StringBuilder;

public class _NumberFormatter_en extends NumberFormatterBase {
  public static final NumberPattern[] DECIMAL_STANDARD = patterns("#,##0.###", "-#,##0.###");

  public static final NumberPattern[] PERCENT_STANDARD = patterns("#,##0%", "-#,##0%");

  public static final NumberPattern[] CURRENCY_STANDARD = patterns("¤#,##0.00", "-¤#,##0.00");

  public static final NumberPattern[] CURRENCY_ACCOUNTING = patterns("¤#,##0.00", "(¤#,##0.00)");

  public static final NumberPattern[] DECIMAL_STANDARD_COMPACT = patterns("#,##0", "-#,##0");

  public static final NumberPattern[] CURRENCY_STANDARD_COMPACT = patterns("¤#,##0", "-¤#,##0");

  private final NumberPattern[] DECIMAL_SHORT_1K_ONE = patterns("0K", "-0K");

  private final NumberPattern[] DECIMAL_SHORT_1K_OTHER = patterns("0K", "-0K");

  private final NumberPattern[] DECIMAL_SHORT_10K_ONE = patterns("00K", "-00K");

  private final NumberPattern[] DECIMAL_SHORT_10K_OTHER = patterns("00K", "-00K");

  private final NumberPattern[] DECIMAL_SHORT_100K_ONE = patterns("000K", "-000K");

  private final NumberPattern[] DECIMAL_SHORT_100K_OTHER = patterns("000K", "-000K");

  private final NumberPattern[] DECIMAL_SHORT_1M_ONE = patterns("0M", "-0M");

  private final NumberPattern[] DECIMAL_SHORT_1M_OTHER = patterns("0M", "-0M");

  private final NumberPattern[] DECIMAL_SHORT_10M_ONE = patterns("00M", "-00M");

  private final NumberPattern[] DECIMAL_SHORT_10M_OTHER = patterns("00M", "-00M");

  private final NumberPattern[] DECIMAL_SHORT_100M_ONE = patterns("000M", "-000M");

  private final NumberPattern[] DECIMAL_SHORT_100M_OTHER = patterns("000M", "-000M");

  private final NumberPattern[] DECIMAL_SHORT_1B_ONE = patterns("0B", "-0B");

  private final NumberPattern[] DECIMAL_SHORT_1B_OTHER = patterns("0B", "-0B");

  private final NumberPattern[] DECIMAL_SHORT_10B_ONE = patterns("00B", "-00B");

  private final NumberPattern[] DECIMAL_SHORT_10B_OTHER = patterns("00B", "-00B");

  private final NumberPattern[] DECIMAL_SHORT_100B_ONE = patterns("000B", "-000B");

  private final NumberPattern[] DECIMAL_SHORT_100B_OTHER = patterns("000B", "-000B");

  private final NumberPattern[] DECIMAL_SHORT_1T_ONE = patterns("0T", "-0T");

  private final NumberPattern[] DECIMAL_SHORT_1T_OTHER = patterns("0T", "-0T");

  private final NumberPattern[] DECIMAL_SHORT_10T_ONE = patterns("00T", "-00T");

  private final NumberPattern[] DECIMAL_SHORT_10T_OTHER = patterns("00T", "-00T");

  private final NumberPattern[] DECIMAL_SHORT_100T_ONE = patterns("000T", "-000T");

  private final NumberPattern[] DECIMAL_SHORT_100T_OTHER = patterns("000T", "-000T");

  private final int DECIMAL_SHORT_1K_POWER = 3;

  private final int DECIMAL_SHORT_10K_POWER = 3;

  private final int DECIMAL_SHORT_100K_POWER = 3;

  private final int DECIMAL_SHORT_1M_POWER = 6;

  private final int DECIMAL_SHORT_10M_POWER = 6;

  private final int DECIMAL_SHORT_100M_POWER = 6;

  private final int DECIMAL_SHORT_1B_POWER = 9;

  private final int DECIMAL_SHORT_10B_POWER = 9;

  private final int DECIMAL_SHORT_100B_POWER = 9;

  private final int DECIMAL_SHORT_1T_POWER = 12;

  private final int DECIMAL_SHORT_10T_POWER = 12;

  private final int DECIMAL_SHORT_100T_POWER = 12;

  private final NumberPattern[] DECIMAL_LONG_1K_ONE = patterns("0 thousand", "-0 thousand");

  private final NumberPattern[] DECIMAL_LONG_1K_OTHER = patterns("0 thousand", "-0 thousand");

  private final NumberPattern[] DECIMAL_LONG_10K_ONE = patterns("00 thousand", "-00 thousand");

  private final NumberPattern[] DECIMAL_LONG_10K_OTHER = patterns("00 thousand", "-00 thousand");

  private final NumberPattern[] DECIMAL_LONG_100K_ONE = patterns("000 thousand", "-000 thousand");

  private final NumberPattern[] DECIMAL_LONG_100K_OTHER = patterns("000 thousand", "-000 thousand");

  private final NumberPattern[] DECIMAL_LONG_1M_ONE = patterns("0 million", "-0 million");

  private final NumberPattern[] DECIMAL_LONG_1M_OTHER = patterns("0 million", "-0 million");

  private final NumberPattern[] DECIMAL_LONG_10M_ONE = patterns("00 million", "-00 million");

  private final NumberPattern[] DECIMAL_LONG_10M_OTHER = patterns("00 million", "-00 million");

  private final NumberPattern[] DECIMAL_LONG_100M_ONE = patterns("000 million", "-000 million");

  private final NumberPattern[] DECIMAL_LONG_100M_OTHER = patterns("000 million", "-000 million");

  private final NumberPattern[] DECIMAL_LONG_1B_ONE = patterns("0 billion", "-0 billion");

  private final NumberPattern[] DECIMAL_LONG_1B_OTHER = patterns("0 billion", "-0 billion");

  private final NumberPattern[] DECIMAL_LONG_10B_ONE = patterns("00 billion", "-00 billion");

  private final NumberPattern[] DECIMAL_LONG_10B_OTHER = patterns("00 billion", "-00 billion");

  private final NumberPattern[] DECIMAL_LONG_100B_ONE = patterns("000 billion", "-000 billion");

  private final NumberPattern[] DECIMAL_LONG_100B_OTHER = patterns("000 billion", "-000 billion");

  private final NumberPattern[] DECIMAL_LONG_1T_ONE = patterns("0 trillion", "-0 trillion");

  private final NumberPattern[] DECIMAL_LONG_1T_OTHER = patterns("0 trillion", "-0 trillion");

  private final NumberPattern[] DECIMAL_LONG_10T_ONE = patterns("00 trillion", "-00 trillion");

  private final NumberPattern[] DECIMAL_LONG_10T_OTHER = patterns("00 trillion", "-00 trillion");

  private final NumberPattern[] DECIMAL_LONG_100T_ONE = patterns("000 trillion", "-000 trillion");

  private final NumberPattern[] DECIMAL_LONG_100T_OTHER = patterns("000 trillion", "-000 trillion");

  private final int DECIMAL_LONG_1K_POWER = 3;

  private final int DECIMAL_LONG_10K_POWER = 3;

  private final int DECIMAL_LONG_100K_POWER = 3;

  private final int DECIMAL_LONG_1M_POWER = 6;

  private final int DECIMAL_LONG_10M_POWER = 6;

  private final int DECIMAL_LONG_100M_POWER = 6;

  private final int DECIMAL_LONG_1B_POWER = 9;

  private final int DECIMAL_LONG_10B_POWER = 9;

  private final int DECIMAL_LONG_100B_POWER = 9;

  private final int DECIMAL_LONG_1T_POWER = 12;

  private final int DECIMAL_LONG_10T_POWER = 12;

  private final int DECIMAL_LONG_100T_POWER = 12;

  private final NumberPattern[] CURRENCY_SHORT_1K_ONE = patterns("¤0K", "-¤0K");

  private final NumberPattern[] CURRENCY_SHORT_1K_OTHER = patterns("¤0K", "-¤0K");

  private final NumberPattern[] CURRENCY_SHORT_10K_ONE = patterns("¤00K", "-¤00K");

  private final NumberPattern[] CURRENCY_SHORT_10K_OTHER = patterns("¤00K", "-¤00K");

  private final NumberPattern[] CURRENCY_SHORT_100K_ONE = patterns("¤000K", "-¤000K");

  private final NumberPattern[] CURRENCY_SHORT_100K_OTHER = patterns("¤000K", "-¤000K");

  private final NumberPattern[] CURRENCY_SHORT_1M_ONE = patterns("¤0M", "-¤0M");

  private final NumberPattern[] CURRENCY_SHORT_1M_OTHER = patterns("¤0M", "-¤0M");

  private final NumberPattern[] CURRENCY_SHORT_10M_ONE = patterns("¤00M", "-¤00M");

  private final NumberPattern[] CURRENCY_SHORT_10M_OTHER = patterns("¤00M", "-¤00M");

  private final NumberPattern[] CURRENCY_SHORT_100M_ONE = patterns("¤000M", "-¤000M");

  private final NumberPattern[] CURRENCY_SHORT_100M_OTHER = patterns("¤000M", "-¤000M");

  private final NumberPattern[] CURRENCY_SHORT_1B_ONE = patterns("¤0B", "-¤0B");

  private final NumberPattern[] CURRENCY_SHORT_1B_OTHER = patterns("¤0B", "-¤0B");

  private final NumberPattern[] CURRENCY_SHORT_10B_ONE = patterns("¤00B", "-¤00B");

  private final NumberPattern[] CURRENCY_SHORT_10B_OTHER = patterns("¤00B", "-¤00B");

  private final NumberPattern[] CURRENCY_SHORT_100B_ONE = patterns("¤000B", "-¤000B");

  private final NumberPattern[] CURRENCY_SHORT_100B_OTHER = patterns("¤000B", "-¤000B");

  private final NumberPattern[] CURRENCY_SHORT_1T_ONE = patterns("¤0T", "-¤0T");

  private final NumberPattern[] CURRENCY_SHORT_1T_OTHER = patterns("¤0T", "-¤0T");

  private final NumberPattern[] CURRENCY_SHORT_10T_ONE = patterns("¤00T", "-¤00T");

  private final NumberPattern[] CURRENCY_SHORT_10T_OTHER = patterns("¤00T", "-¤00T");

  private final NumberPattern[] CURRENCY_SHORT_100T_ONE = patterns("¤000T", "-¤000T");

  private final NumberPattern[] CURRENCY_SHORT_100T_OTHER = patterns("¤000T", "-¤000T");

  private final int CURRENCY_SHORT_1K_POWER = 3;

  private final int CURRENCY_SHORT_10K_POWER = 3;

  private final int CURRENCY_SHORT_100K_POWER = 3;

  private final int CURRENCY_SHORT_1M_POWER = 6;

  private final int CURRENCY_SHORT_10M_POWER = 6;

  private final int CURRENCY_SHORT_100M_POWER = 6;

  private final int CURRENCY_SHORT_1B_POWER = 9;

  private final int CURRENCY_SHORT_10B_POWER = 9;

  private final int CURRENCY_SHORT_100B_POWER = 9;

  private final int CURRENCY_SHORT_1T_POWER = 12;

  private final int CURRENCY_SHORT_10T_POWER = 12;

  private final int CURRENCY_SHORT_100T_POWER = 12;

  public _NumberFormatter_en() {
    super(
        new CLDRLocale("en", "", "", ""),
        new _Params(),
        // decimal standard
        patterns("#,##0.###", "-#,##0.###"),
        // percent standard
        patterns("#,##0%", "-#,##0%"),
        // currency standard
        patterns("¤#,##0.00", "-¤#,##0.00"),
        // currency accounting
        patterns("¤#,##0.00", "(¤#,##0.00)")
        );
  }

  protected int getPowerOfTen_DECIMAL_SHORT(int digits) {
    if (digits < 4) {
      return 0;
    }
    switch (digits) {
      case 4: return DECIMAL_SHORT_1K_POWER;
      case 5: return DECIMAL_SHORT_10K_POWER;
      case 6: return DECIMAL_SHORT_100K_POWER;
      case 7: return DECIMAL_SHORT_1M_POWER;
      case 8: return DECIMAL_SHORT_10M_POWER;
      case 9: return DECIMAL_SHORT_100M_POWER;
      case 10: return DECIMAL_SHORT_1B_POWER;
      case 11: return DECIMAL_SHORT_10B_POWER;
      case 12: return DECIMAL_SHORT_100B_POWER;
      case 13: return DECIMAL_SHORT_1T_POWER;
      case 14: return DECIMAL_SHORT_10T_POWER;
      case 15:
          default: return DECIMAL_SHORT_100T_POWER;
    }
  }

  protected NumberPattern[] getPattern_DECIMAL_SHORT(int digits, PluralCategory category) {
    if (digits < 4 || category == null) {
      return DECIMAL_STANDARD_COMPACT;
    }
    switch (digits) {
      case 4: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_1K_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_10K_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_100K_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_1M_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_10M_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_100M_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_1B_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_10B_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_100B_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_1T_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_10T_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
          case ONE: return DECIMAL_SHORT_100T_ONE;
          case OTHER:
              default: return DECIMAL_SHORT_100T_OTHER;
        }
      }
    }
  }

  protected int getPowerOfTen_DECIMAL_LONG(int digits) {
    if (digits < 4) {
      return 0;
    }
    switch (digits) {
      case 4: return DECIMAL_LONG_1K_POWER;
      case 5: return DECIMAL_LONG_10K_POWER;
      case 6: return DECIMAL_LONG_100K_POWER;
      case 7: return DECIMAL_LONG_1M_POWER;
      case 8: return DECIMAL_LONG_10M_POWER;
      case 9: return DECIMAL_LONG_100M_POWER;
      case 10: return DECIMAL_LONG_1B_POWER;
      case 11: return DECIMAL_LONG_10B_POWER;
      case 12: return DECIMAL_LONG_100B_POWER;
      case 13: return DECIMAL_LONG_1T_POWER;
      case 14: return DECIMAL_LONG_10T_POWER;
      case 15:
          default: return DECIMAL_LONG_100T_POWER;
    }
  }

  protected NumberPattern[] getPattern_DECIMAL_LONG(int digits, PluralCategory category) {
    if (digits < 4 || category == null) {
      return DECIMAL_STANDARD_COMPACT;
    }
    switch (digits) {
      case 4: {
        switch (category) {
          case ONE: return DECIMAL_LONG_1K_ONE;
          case OTHER:
              default: return DECIMAL_LONG_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case ONE: return DECIMAL_LONG_10K_ONE;
          case OTHER:
              default: return DECIMAL_LONG_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case ONE: return DECIMAL_LONG_100K_ONE;
          case OTHER:
              default: return DECIMAL_LONG_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case ONE: return DECIMAL_LONG_1M_ONE;
          case OTHER:
              default: return DECIMAL_LONG_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case ONE: return DECIMAL_LONG_10M_ONE;
          case OTHER:
              default: return DECIMAL_LONG_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case ONE: return DECIMAL_LONG_100M_ONE;
          case OTHER:
              default: return DECIMAL_LONG_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case ONE: return DECIMAL_LONG_1B_ONE;
          case OTHER:
              default: return DECIMAL_LONG_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case ONE: return DECIMAL_LONG_10B_ONE;
          case OTHER:
              default: return DECIMAL_LONG_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case ONE: return DECIMAL_LONG_100B_ONE;
          case OTHER:
              default: return DECIMAL_LONG_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case ONE: return DECIMAL_LONG_1T_ONE;
          case OTHER:
              default: return DECIMAL_LONG_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case ONE: return DECIMAL_LONG_10T_ONE;
          case OTHER:
              default: return DECIMAL_LONG_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
          case ONE: return DECIMAL_LONG_100T_ONE;
          case OTHER:
              default: return DECIMAL_LONG_100T_OTHER;
        }
      }
    }
  }

  protected int getPowerOfTen_CURRENCY_SHORT(int digits) {
    if (digits < 4) {
      return 0;
    }
    switch (digits) {
      case 4: return CURRENCY_SHORT_1K_POWER;
      case 5: return CURRENCY_SHORT_10K_POWER;
      case 6: return CURRENCY_SHORT_100K_POWER;
      case 7: return CURRENCY_SHORT_1M_POWER;
      case 8: return CURRENCY_SHORT_10M_POWER;
      case 9: return CURRENCY_SHORT_100M_POWER;
      case 10: return CURRENCY_SHORT_1B_POWER;
      case 11: return CURRENCY_SHORT_10B_POWER;
      case 12: return CURRENCY_SHORT_100B_POWER;
      case 13: return CURRENCY_SHORT_1T_POWER;
      case 14: return CURRENCY_SHORT_10T_POWER;
      case 15:
          default: return CURRENCY_SHORT_100T_POWER;
    }
  }

  protected NumberPattern[] getPattern_CURRENCY_SHORT(int digits, PluralCategory category) {
    if (digits < 4 || category == null) {
      return CURRENCY_STANDARD_COMPACT;
    }
    switch (digits) {
      case 4: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_1K_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_10K_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_100K_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_1M_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_10M_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_100M_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_1B_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_10B_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_100B_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_1T_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_10T_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
          case ONE: return CURRENCY_SHORT_100T_ONE;
          case OTHER:
              default: return CURRENCY_SHORT_100T_OTHER;
        }
      }
    }
  }

  public String getCurrencySymbol(String code) {
    switch (code) {
      case "ADP": return "ADP";
      case "AED": return "AED";
      case "AFA": return "AFA";
      case "AFN": return "AFN";
      case "ALK": return "";
      case "ALL": return "ALL";
      case "AMD": return "AMD";
      case "ANG": return "ANG";
      case "AOA": return "AOA";
      case "AOK": return "AOK";
      case "AON": return "AON";
      case "AOR": return "AOR";
      case "ARA": return "ARA";
      case "ARL": return "ARL";
      case "ARM": return "ARM";
      case "ARP": return "ARP";
      case "ARS": return "ARS";
      case "ATS": return "ATS";
      case "AUD": return "A$";
      case "AWG": return "AWG";
      case "AZM": return "AZM";
      case "AZN": return "AZN";
      case "BAD": return "BAD";
      case "BAM": return "BAM";
      case "BAN": return "BAN";
      case "BBD": return "BBD";
      case "BDT": return "BDT";
      case "BEC": return "BEC";
      case "BEF": return "BEF";
      case "BEL": return "BEL";
      case "BGL": return "BGL";
      case "BGM": return "BGM";
      case "BGN": return "BGN";
      case "BGO": return "BGO";
      case "BHD": return "BHD";
      case "BIF": return "BIF";
      case "BMD": return "BMD";
      case "BND": return "BND";
      case "BOB": return "BOB";
      case "BOL": return "BOL";
      case "BOP": return "BOP";
      case "BOV": return "BOV";
      case "BRB": return "BRB";
      case "BRC": return "BRC";
      case "BRE": return "BRE";
      case "BRL": return "R$";
      case "BRN": return "BRN";
      case "BRR": return "BRR";
      case "BRZ": return "BRZ";
      case "BSD": return "BSD";
      case "BTN": return "BTN";
      case "BUK": return "BUK";
      case "BWP": return "BWP";
      case "BYB": return "BYB";
      case "BYN": return "BYN";
      case "BYR": return "BYR";
      case "BZD": return "BZD";
      case "CAD": return "CA$";
      case "CDF": return "CDF";
      case "CHE": return "CHE";
      case "CHF": return "CHF";
      case "CHW": return "CHW";
      case "CLE": return "CLE";
      case "CLF": return "CLF";
      case "CLP": return "CLP";
      case "CNX": return "";
      case "CNY": return "CN¥";
      case "COP": return "COP";
      case "COU": return "COU";
      case "CRC": return "CRC";
      case "CSD": return "CSD";
      case "CSK": return "CSK";
      case "CUC": return "CUC";
      case "CUP": return "CUP";
      case "CVE": return "CVE";
      case "CYP": return "CYP";
      case "CZK": return "CZK";
      case "DDM": return "DDM";
      case "DEM": return "DEM";
      case "DJF": return "DJF";
      case "DKK": return "DKK";
      case "DOP": return "DOP";
      case "DZD": return "DZD";
      case "ECS": return "ECS";
      case "ECV": return "ECV";
      case "EEK": return "EEK";
      case "EGP": return "EGP";
      case "ERN": return "ERN";
      case "ESA": return "ESA";
      case "ESB": return "ESB";
      case "ESP": return "ESP";
      case "ETB": return "ETB";
      case "EUR": return "€";
      case "FIM": return "FIM";
      case "FJD": return "FJD";
      case "FKP": return "FKP";
      case "FRF": return "FRF";
      case "GBP": return "£";
      case "GEK": return "GEK";
      case "GEL": return "GEL";
      case "GHC": return "GHC";
      case "GHS": return "GHS";
      case "GIP": return "GIP";
      case "GMD": return "GMD";
      case "GNF": return "GNF";
      case "GNS": return "GNS";
      case "GQE": return "GQE";
      case "GRD": return "GRD";
      case "GTQ": return "GTQ";
      case "GWE": return "GWE";
      case "GWP": return "GWP";
      case "GYD": return "GYD";
      case "HKD": return "HK$";
      case "HNL": return "HNL";
      case "HRD": return "HRD";
      case "HRK": return "HRK";
      case "HTG": return "HTG";
      case "HUF": return "HUF";
      case "IDR": return "IDR";
      case "IEP": return "IEP";
      case "ILP": return "ILP";
      case "ILR": return "";
      case "ILS": return "₪";
      case "INR": return "₹";
      case "IQD": return "IQD";
      case "IRR": return "IRR";
      case "ISJ": return "";
      case "ISK": return "ISK";
      case "ITL": return "ITL";
      case "JMD": return "JMD";
      case "JOD": return "JOD";
      case "JPY": return "¥";
      case "KES": return "KES";
      case "KGS": return "KGS";
      case "KHR": return "KHR";
      case "KMF": return "KMF";
      case "KPW": return "KPW";
      case "KRH": return "KRH";
      case "KRO": return "KRO";
      case "KRW": return "₩";
      case "KWD": return "KWD";
      case "KYD": return "KYD";
      case "KZT": return "KZT";
      case "LAK": return "LAK";
      case "LBP": return "LBP";
      case "LKR": return "LKR";
      case "LRD": return "LRD";
      case "LSL": return "LSL";
      case "LTL": return "LTL";
      case "LTT": return "LTT";
      case "LUC": return "LUC";
      case "LUF": return "LUF";
      case "LUL": return "LUL";
      case "LVL": return "LVL";
      case "LVR": return "LVR";
      case "LYD": return "LYD";
      case "MAD": return "MAD";
      case "MAF": return "MAF";
      case "MCF": return "MCF";
      case "MDC": return "MDC";
      case "MDL": return "MDL";
      case "MGA": return "MGA";
      case "MGF": return "MGF";
      case "MKD": return "MKD";
      case "MKN": return "MKN";
      case "MLF": return "MLF";
      case "MMK": return "MMK";
      case "MNT": return "MNT";
      case "MOP": return "MOP";
      case "MRO": return "MRO";
      case "MTL": return "MTL";
      case "MTP": return "MTP";
      case "MUR": return "MUR";
      case "MVP": return "";
      case "MVR": return "MVR";
      case "MWK": return "MWK";
      case "MXN": return "MX$";
      case "MXP": return "MXP";
      case "MXV": return "MXV";
      case "MYR": return "MYR";
      case "MZE": return "MZE";
      case "MZM": return "MZM";
      case "MZN": return "MZN";
      case "NAD": return "NAD";
      case "NGN": return "NGN";
      case "NIC": return "NIC";
      case "NIO": return "NIO";
      case "NLG": return "NLG";
      case "NOK": return "NOK";
      case "NPR": return "NPR";
      case "NZD": return "NZ$";
      case "OMR": return "OMR";
      case "PAB": return "PAB";
      case "PEI": return "PEI";
      case "PEN": return "PEN";
      case "PES": return "PES";
      case "PGK": return "PGK";
      case "PHP": return "PHP";
      case "PKR": return "PKR";
      case "PLN": return "PLN";
      case "PLZ": return "PLZ";
      case "PTE": return "PTE";
      case "PYG": return "PYG";
      case "QAR": return "QAR";
      case "RHD": return "RHD";
      case "ROL": return "ROL";
      case "RON": return "RON";
      case "RSD": return "RSD";
      case "RUB": return "RUB";
      case "RUR": return "RUR";
      case "RWF": return "RWF";
      case "SAR": return "SAR";
      case "SBD": return "SBD";
      case "SCR": return "SCR";
      case "SDD": return "SDD";
      case "SDG": return "SDG";
      case "SDP": return "SDP";
      case "SEK": return "SEK";
      case "SGD": return "SGD";
      case "SHP": return "SHP";
      case "SIT": return "SIT";
      case "SKK": return "SKK";
      case "SLL": return "SLL";
      case "SOS": return "SOS";
      case "SRD": return "SRD";
      case "SRG": return "SRG";
      case "SSP": return "SSP";
      case "STD": return "STD";
      case "SUR": return "SUR";
      case "SVC": return "SVC";
      case "SYP": return "SYP";
      case "SZL": return "SZL";
      case "THB": return "THB";
      case "TJR": return "TJR";
      case "TJS": return "TJS";
      case "TMM": return "TMM";
      case "TMT": return "TMT";
      case "TND": return "TND";
      case "TOP": return "TOP";
      case "TPE": return "TPE";
      case "TRL": return "TRL";
      case "TRY": return "TRY";
      case "TTD": return "TTD";
      case "TWD": return "NT$";
      case "TZS": return "TZS";
      case "UAH": return "UAH";
      case "UAK": return "UAK";
      case "UGS": return "UGS";
      case "UGX": return "UGX";
      case "USD": return "$";
      case "USN": return "USN";
      case "USS": return "USS";
      case "UYI": return "UYI";
      case "UYP": return "UYP";
      case "UYU": return "UYU";
      case "UZS": return "UZS";
      case "VEB": return "VEB";
      case "VEF": return "VEF";
      case "VND": return "₫";
      case "VNN": return "VNN";
      case "VUV": return "VUV";
      case "WST": return "WST";
      case "XAF": return "FCFA";
      case "XAG": return "XAG";
      case "XAU": return "XAU";
      case "XBA": return "XBA";
      case "XBB": return "XBB";
      case "XBC": return "XBC";
      case "XBD": return "XBD";
      case "XCD": return "EC$";
      case "XDR": return "XDR";
      case "XEU": return "XEU";
      case "XFO": return "XFO";
      case "XFU": return "XFU";
      case "XOF": return "CFA";
      case "XPD": return "XPD";
      case "XPF": return "CFPF";
      case "XPT": return "XPT";
      case "XRE": return "XRE";
      case "XSU": return "XSU";
      case "XTS": return "XTS";
      case "XUA": return "XUA";
      case "XXX": return "XXX";
      case "YDD": return "YDD";
      case "YER": return "YER";
      case "YUD": return "YUD";
      case "YUM": return "YUM";
      case "YUN": return "YUN";
      case "YUR": return "YUR";
      case "ZAL": return "ZAL";
      case "ZAR": return "ZAR";
      case "ZMK": return "ZMK";
      case "ZMW": return "ZMW";
      case "ZRN": return "ZRN";
      case "ZRZ": return "ZRZ";
      case "ZWD": return "ZWD";
      case "ZWL": return "ZWL";
      case "ZWR": return "ZWR";
      default: return "";
    }
  }

  public String getNarrowCurrencySymbol(String code) {
    switch (code) {
      case "ADP": return "ADP";
      case "AED": return "AED";
      case "AFA": return "AFA";
      case "AFN": return "AFN";
      case "ALK": return "";
      case "ALL": return "ALL";
      case "AMD": return "AMD";
      case "ANG": return "ANG";
      case "AOA": return "Kz";
      case "AOK": return "AOK";
      case "AON": return "AON";
      case "AOR": return "AOR";
      case "ARA": return "ARA";
      case "ARL": return "ARL";
      case "ARM": return "ARM";
      case "ARP": return "ARP";
      case "ARS": return "$";
      case "ATS": return "ATS";
      case "AUD": return "$";
      case "AWG": return "AWG";
      case "AZM": return "AZM";
      case "AZN": return "AZN";
      case "BAD": return "BAD";
      case "BAM": return "KM";
      case "BAN": return "BAN";
      case "BBD": return "$";
      case "BDT": return "৳";
      case "BEC": return "BEC";
      case "BEF": return "BEF";
      case "BEL": return "BEL";
      case "BGL": return "BGL";
      case "BGM": return "BGM";
      case "BGN": return "BGN";
      case "BGO": return "BGO";
      case "BHD": return "BHD";
      case "BIF": return "BIF";
      case "BMD": return "$";
      case "BND": return "$";
      case "BOB": return "Bs";
      case "BOL": return "BOL";
      case "BOP": return "BOP";
      case "BOV": return "BOV";
      case "BRB": return "BRB";
      case "BRC": return "BRC";
      case "BRE": return "BRE";
      case "BRL": return "R$";
      case "BRN": return "BRN";
      case "BRR": return "BRR";
      case "BRZ": return "BRZ";
      case "BSD": return "$";
      case "BTN": return "BTN";
      case "BUK": return "BUK";
      case "BWP": return "P";
      case "BYB": return "BYB";
      case "BYN": return "р.";
      case "BYR": return "BYR";
      case "BZD": return "$";
      case "CAD": return "$";
      case "CDF": return "CDF";
      case "CHE": return "CHE";
      case "CHF": return "CHF";
      case "CHW": return "CHW";
      case "CLE": return "CLE";
      case "CLF": return "CLF";
      case "CLP": return "$";
      case "CNX": return "";
      case "CNY": return "¥";
      case "COP": return "$";
      case "COU": return "COU";
      case "CRC": return "₡";
      case "CSD": return "CSD";
      case "CSK": return "CSK";
      case "CUC": return "$";
      case "CUP": return "$";
      case "CVE": return "CVE";
      case "CYP": return "CYP";
      case "CZK": return "Kč";
      case "DDM": return "DDM";
      case "DEM": return "DEM";
      case "DJF": return "DJF";
      case "DKK": return "kr";
      case "DOP": return "$";
      case "DZD": return "DZD";
      case "ECS": return "ECS";
      case "ECV": return "ECV";
      case "EEK": return "EEK";
      case "EGP": return "E£";
      case "ERN": return "ERN";
      case "ESA": return "ESA";
      case "ESB": return "ESB";
      case "ESP": return "₧";
      case "ETB": return "ETB";
      case "EUR": return "€";
      case "FIM": return "FIM";
      case "FJD": return "$";
      case "FKP": return "£";
      case "FRF": return "FRF";
      case "GBP": return "£";
      case "GEK": return "GEK";
      case "GEL": return "₾";
      case "GHC": return "GHC";
      case "GHS": return "GHS";
      case "GIP": return "£";
      case "GMD": return "GMD";
      case "GNF": return "FG";
      case "GNS": return "GNS";
      case "GQE": return "GQE";
      case "GRD": return "GRD";
      case "GTQ": return "Q";
      case "GWE": return "GWE";
      case "GWP": return "GWP";
      case "GYD": return "$";
      case "HKD": return "$";
      case "HNL": return "L";
      case "HRD": return "HRD";
      case "HRK": return "kn";
      case "HTG": return "HTG";
      case "HUF": return "Ft";
      case "IDR": return "Rp";
      case "IEP": return "IEP";
      case "ILP": return "ILP";
      case "ILR": return "";
      case "ILS": return "₪";
      case "INR": return "₹";
      case "IQD": return "IQD";
      case "IRR": return "IRR";
      case "ISJ": return "";
      case "ISK": return "kr";
      case "ITL": return "ITL";
      case "JMD": return "$";
      case "JOD": return "JOD";
      case "JPY": return "¥";
      case "KES": return "KES";
      case "KGS": return "KGS";
      case "KHR": return "៛";
      case "KMF": return "CF";
      case "KPW": return "₩";
      case "KRH": return "KRH";
      case "KRO": return "KRO";
      case "KRW": return "₩";
      case "KWD": return "KWD";
      case "KYD": return "$";
      case "KZT": return "₸";
      case "LAK": return "₭";
      case "LBP": return "L£";
      case "LKR": return "Rs";
      case "LRD": return "$";
      case "LSL": return "LSL";
      case "LTL": return "Lt";
      case "LTT": return "LTT";
      case "LUC": return "LUC";
      case "LUF": return "LUF";
      case "LUL": return "LUL";
      case "LVL": return "Ls";
      case "LVR": return "LVR";
      case "LYD": return "LYD";
      case "MAD": return "MAD";
      case "MAF": return "MAF";
      case "MCF": return "MCF";
      case "MDC": return "MDC";
      case "MDL": return "MDL";
      case "MGA": return "Ar";
      case "MGF": return "MGF";
      case "MKD": return "MKD";
      case "MKN": return "MKN";
      case "MLF": return "MLF";
      case "MMK": return "K";
      case "MNT": return "₮";
      case "MOP": return "MOP";
      case "MRO": return "MRO";
      case "MTL": return "MTL";
      case "MTP": return "MTP";
      case "MUR": return "Rs";
      case "MVP": return "";
      case "MVR": return "MVR";
      case "MWK": return "MWK";
      case "MXN": return "$";
      case "MXP": return "MXP";
      case "MXV": return "MXV";
      case "MYR": return "RM";
      case "MZE": return "MZE";
      case "MZM": return "MZM";
      case "MZN": return "MZN";
      case "NAD": return "$";
      case "NGN": return "₦";
      case "NIC": return "NIC";
      case "NIO": return "C$";
      case "NLG": return "NLG";
      case "NOK": return "kr";
      case "NPR": return "Rs";
      case "NZD": return "$";
      case "OMR": return "OMR";
      case "PAB": return "PAB";
      case "PEI": return "PEI";
      case "PEN": return "PEN";
      case "PES": return "PES";
      case "PGK": return "PGK";
      case "PHP": return "₱";
      case "PKR": return "Rs";
      case "PLN": return "zł";
      case "PLZ": return "PLZ";
      case "PTE": return "PTE";
      case "PYG": return "₲";
      case "QAR": return "QAR";
      case "RHD": return "RHD";
      case "ROL": return "ROL";
      case "RON": return "lei";
      case "RSD": return "RSD";
      case "RUB": return "₽";
      case "RUR": return "р.";
      case "RWF": return "RF";
      case "SAR": return "SAR";
      case "SBD": return "$";
      case "SCR": return "SCR";
      case "SDD": return "SDD";
      case "SDG": return "SDG";
      case "SDP": return "SDP";
      case "SEK": return "kr";
      case "SGD": return "$";
      case "SHP": return "£";
      case "SIT": return "SIT";
      case "SKK": return "SKK";
      case "SLL": return "SLL";
      case "SOS": return "SOS";
      case "SRD": return "$";
      case "SRG": return "SRG";
      case "SSP": return "£";
      case "STD": return "Db";
      case "SUR": return "SUR";
      case "SVC": return "SVC";
      case "SYP": return "£";
      case "SZL": return "SZL";
      case "THB": return "฿";
      case "TJR": return "TJR";
      case "TJS": return "TJS";
      case "TMM": return "TMM";
      case "TMT": return "TMT";
      case "TND": return "TND";
      case "TOP": return "T$";
      case "TPE": return "TPE";
      case "TRL": return "TRL";
      case "TRY": return "₺";
      case "TTD": return "$";
      case "TWD": return "$";
      case "TZS": return "TZS";
      case "UAH": return "₴";
      case "UAK": return "UAK";
      case "UGS": return "UGS";
      case "UGX": return "UGX";
      case "USD": return "$";
      case "USN": return "USN";
      case "USS": return "USS";
      case "UYI": return "UYI";
      case "UYP": return "UYP";
      case "UYU": return "$";
      case "UZS": return "UZS";
      case "VEB": return "VEB";
      case "VEF": return "Bs";
      case "VND": return "₫";
      case "VNN": return "VNN";
      case "VUV": return "VUV";
      case "WST": return "WST";
      case "XAF": return "FCFA";
      case "XAG": return "XAG";
      case "XAU": return "XAU";
      case "XBA": return "XBA";
      case "XBB": return "XBB";
      case "XBC": return "XBC";
      case "XBD": return "XBD";
      case "XCD": return "$";
      case "XDR": return "XDR";
      case "XEU": return "XEU";
      case "XFO": return "XFO";
      case "XFU": return "XFU";
      case "XOF": return "CFA";
      case "XPD": return "XPD";
      case "XPF": return "CFPF";
      case "XPT": return "XPT";
      case "XRE": return "XRE";
      case "XSU": return "XSU";
      case "XTS": return "XTS";
      case "XUA": return "XUA";
      case "XXX": return "XXX";
      case "YDD": return "YDD";
      case "YER": return "YER";
      case "YUD": return "YUD";
      case "YUM": return "YUM";
      case "YUN": return "YUN";
      case "YUR": return "YUR";
      case "ZAL": return "ZAL";
      case "ZAR": return "R";
      case "ZMK": return "ZMK";
      case "ZMW": return "ZK";
      case "ZRN": return "ZRN";
      case "ZRZ": return "ZRZ";
      case "ZWD": return "ZWD";
      case "ZWL": return "ZWL";
      case "ZWR": return "ZWR";
      default: return "";
    }
  }

  public String getCurrencyDisplayName(String code) {
    switch (code) {
      case "ADP": return "Andorran Peseta";
      case "AED": return "United Arab Emirates Dirham";
      case "AFA": return "Afghan Afghani (1927–2002)";
      case "AFN": return "Afghan Afghani";
      case "ALK": return "Albanian Lek (1946–1965)";
      case "ALL": return "Albanian Lek";
      case "AMD": return "Armenian Dram";
      case "ANG": return "Netherlands Antillean Guilder";
      case "AOA": return "Angolan Kwanza";
      case "AOK": return "Angolan Kwanza (1977–1991)";
      case "AON": return "Angolan New Kwanza (1990–2000)";
      case "AOR": return "Angolan Readjusted Kwanza (1995–1999)";
      case "ARA": return "Argentine Austral";
      case "ARL": return "Argentine Peso Ley (1970–1983)";
      case "ARM": return "Argentine Peso (1881–1970)";
      case "ARP": return "Argentine Peso (1983–1985)";
      case "ARS": return "Argentine Peso";
      case "ATS": return "Austrian Schilling";
      case "AUD": return "Australian Dollar";
      case "AWG": return "Aruban Florin";
      case "AZM": return "Azerbaijani Manat (1993–2006)";
      case "AZN": return "Azerbaijani Manat";
      case "BAD": return "Bosnia-Herzegovina Dinar (1992–1994)";
      case "BAM": return "Bosnia-Herzegovina Convertible Mark";
      case "BAN": return "Bosnia-Herzegovina New Dinar (1994–1997)";
      case "BBD": return "Barbadian Dollar";
      case "BDT": return "Bangladeshi Taka";
      case "BEC": return "Belgian Franc (convertible)";
      case "BEF": return "Belgian Franc";
      case "BEL": return "Belgian Franc (financial)";
      case "BGL": return "Bulgarian Hard Lev";
      case "BGM": return "Bulgarian Socialist Lev";
      case "BGN": return "Bulgarian Lev";
      case "BGO": return "Bulgarian Lev (1879–1952)";
      case "BHD": return "Bahraini Dinar";
      case "BIF": return "Burundian Franc";
      case "BMD": return "Bermudan Dollar";
      case "BND": return "Brunei Dollar";
      case "BOB": return "Bolivian Boliviano";
      case "BOL": return "Bolivian Boliviano (1863–1963)";
      case "BOP": return "Bolivian Peso";
      case "BOV": return "Bolivian Mvdol";
      case "BRB": return "Brazilian New Cruzeiro (1967–1986)";
      case "BRC": return "Brazilian Cruzado (1986–1989)";
      case "BRE": return "Brazilian Cruzeiro (1990–1993)";
      case "BRL": return "Brazilian Real";
      case "BRN": return "Brazilian New Cruzado (1989–1990)";
      case "BRR": return "Brazilian Cruzeiro (1993–1994)";
      case "BRZ": return "Brazilian Cruzeiro (1942–1967)";
      case "BSD": return "Bahamian Dollar";
      case "BTN": return "Bhutanese Ngultrum";
      case "BUK": return "Burmese Kyat";
      case "BWP": return "Botswanan Pula";
      case "BYB": return "Belarusian Ruble (1994–1999)";
      case "BYN": return "Belarusian Ruble";
      case "BYR": return "Belarusian Ruble (2000–2016)";
      case "BZD": return "Belize Dollar";
      case "CAD": return "Canadian Dollar";
      case "CDF": return "Congolese Franc";
      case "CHE": return "WIR Euro";
      case "CHF": return "Swiss Franc";
      case "CHW": return "WIR Franc";
      case "CLE": return "Chilean Escudo";
      case "CLF": return "Chilean Unit of Account (UF)";
      case "CLP": return "Chilean Peso";
      case "CNX": return "Chinese People’s Bank Dollar";
      case "CNY": return "Chinese Yuan";
      case "COP": return "Colombian Peso";
      case "COU": return "Colombian Real Value Unit";
      case "CRC": return "Costa Rican Colón";
      case "CSD": return "Serbian Dinar (2002–2006)";
      case "CSK": return "Czechoslovak Hard Koruna";
      case "CUC": return "Cuban Convertible Peso";
      case "CUP": return "Cuban Peso";
      case "CVE": return "Cape Verdean Escudo";
      case "CYP": return "Cypriot Pound";
      case "CZK": return "Czech Koruna";
      case "DDM": return "East German Mark";
      case "DEM": return "German Mark";
      case "DJF": return "Djiboutian Franc";
      case "DKK": return "Danish Krone";
      case "DOP": return "Dominican Peso";
      case "DZD": return "Algerian Dinar";
      case "ECS": return "Ecuadorian Sucre";
      case "ECV": return "Ecuadorian Unit of Constant Value";
      case "EEK": return "Estonian Kroon";
      case "EGP": return "Egyptian Pound";
      case "ERN": return "Eritrean Nakfa";
      case "ESA": return "Spanish Peseta (A account)";
      case "ESB": return "Spanish Peseta (convertible account)";
      case "ESP": return "Spanish Peseta";
      case "ETB": return "Ethiopian Birr";
      case "EUR": return "Euro";
      case "FIM": return "Finnish Markka";
      case "FJD": return "Fijian Dollar";
      case "FKP": return "Falkland Islands Pound";
      case "FRF": return "French Franc";
      case "GBP": return "British Pound";
      case "GEK": return "Georgian Kupon Larit";
      case "GEL": return "Georgian Lari";
      case "GHC": return "Ghanaian Cedi (1979–2007)";
      case "GHS": return "Ghanaian Cedi";
      case "GIP": return "Gibraltar Pound";
      case "GMD": return "Gambian Dalasi";
      case "GNF": return "Guinean Franc";
      case "GNS": return "Guinean Syli";
      case "GQE": return "Equatorial Guinean Ekwele";
      case "GRD": return "Greek Drachma";
      case "GTQ": return "Guatemalan Quetzal";
      case "GWE": return "Portuguese Guinea Escudo";
      case "GWP": return "Guinea-Bissau Peso";
      case "GYD": return "Guyanaese Dollar";
      case "HKD": return "Hong Kong Dollar";
      case "HNL": return "Honduran Lempira";
      case "HRD": return "Croatian Dinar";
      case "HRK": return "Croatian Kuna";
      case "HTG": return "Haitian Gourde";
      case "HUF": return "Hungarian Forint";
      case "IDR": return "Indonesian Rupiah";
      case "IEP": return "Irish Pound";
      case "ILP": return "Israeli Pound";
      case "ILR": return "Israeli Shekel (1980–1985)";
      case "ILS": return "Israeli New Shekel";
      case "INR": return "Indian Rupee";
      case "IQD": return "Iraqi Dinar";
      case "IRR": return "Iranian Rial";
      case "ISJ": return "Icelandic Króna (1918–1981)";
      case "ISK": return "Icelandic Króna";
      case "ITL": return "Italian Lira";
      case "JMD": return "Jamaican Dollar";
      case "JOD": return "Jordanian Dinar";
      case "JPY": return "Japanese Yen";
      case "KES": return "Kenyan Shilling";
      case "KGS": return "Kyrgystani Som";
      case "KHR": return "Cambodian Riel";
      case "KMF": return "Comorian Franc";
      case "KPW": return "North Korean Won";
      case "KRH": return "South Korean Hwan (1953–1962)";
      case "KRO": return "South Korean Won (1945–1953)";
      case "KRW": return "South Korean Won";
      case "KWD": return "Kuwaiti Dinar";
      case "KYD": return "Cayman Islands Dollar";
      case "KZT": return "Kazakhstani Tenge";
      case "LAK": return "Laotian Kip";
      case "LBP": return "Lebanese Pound";
      case "LKR": return "Sri Lankan Rupee";
      case "LRD": return "Liberian Dollar";
      case "LSL": return "Lesotho Loti";
      case "LTL": return "Lithuanian Litas";
      case "LTT": return "Lithuanian Talonas";
      case "LUC": return "Luxembourgian Convertible Franc";
      case "LUF": return "Luxembourgian Franc";
      case "LUL": return "Luxembourg Financial Franc";
      case "LVL": return "Latvian Lats";
      case "LVR": return "Latvian Ruble";
      case "LYD": return "Libyan Dinar";
      case "MAD": return "Moroccan Dirham";
      case "MAF": return "Moroccan Franc";
      case "MCF": return "Monegasque Franc";
      case "MDC": return "Moldovan Cupon";
      case "MDL": return "Moldovan Leu";
      case "MGA": return "Malagasy Ariary";
      case "MGF": return "Malagasy Franc";
      case "MKD": return "Macedonian Denar";
      case "MKN": return "Macedonian Denar (1992–1993)";
      case "MLF": return "Malian Franc";
      case "MMK": return "Myanmar Kyat";
      case "MNT": return "Mongolian Tugrik";
      case "MOP": return "Macanese Pataca";
      case "MRO": return "Mauritanian Ouguiya";
      case "MTL": return "Maltese Lira";
      case "MTP": return "Maltese Pound";
      case "MUR": return "Mauritian Rupee";
      case "MVP": return "Maldivian Rupee (1947–1981)";
      case "MVR": return "Maldivian Rufiyaa";
      case "MWK": return "Malawian Kwacha";
      case "MXN": return "Mexican Peso";
      case "MXP": return "Mexican Silver Peso (1861–1992)";
      case "MXV": return "Mexican Investment Unit";
      case "MYR": return "Malaysian Ringgit";
      case "MZE": return "Mozambican Escudo";
      case "MZM": return "Mozambican Metical (1980–2006)";
      case "MZN": return "Mozambican Metical";
      case "NAD": return "Namibian Dollar";
      case "NGN": return "Nigerian Naira";
      case "NIC": return "Nicaraguan Córdoba (1988–1991)";
      case "NIO": return "Nicaraguan Córdoba";
      case "NLG": return "Dutch Guilder";
      case "NOK": return "Norwegian Krone";
      case "NPR": return "Nepalese Rupee";
      case "NZD": return "New Zealand Dollar";
      case "OMR": return "Omani Rial";
      case "PAB": return "Panamanian Balboa";
      case "PEI": return "Peruvian Inti";
      case "PEN": return "Peruvian Sol";
      case "PES": return "Peruvian Sol (1863–1965)";
      case "PGK": return "Papua New Guinean Kina";
      case "PHP": return "Philippine Peso";
      case "PKR": return "Pakistani Rupee";
      case "PLN": return "Polish Zloty";
      case "PLZ": return "Polish Zloty (1950–1995)";
      case "PTE": return "Portuguese Escudo";
      case "PYG": return "Paraguayan Guarani";
      case "QAR": return "Qatari Rial";
      case "RHD": return "Rhodesian Dollar";
      case "ROL": return "Romanian Leu (1952–2006)";
      case "RON": return "Romanian Leu";
      case "RSD": return "Serbian Dinar";
      case "RUB": return "Russian Ruble";
      case "RUR": return "Russian Ruble (1991–1998)";
      case "RWF": return "Rwandan Franc";
      case "SAR": return "Saudi Riyal";
      case "SBD": return "Solomon Islands Dollar";
      case "SCR": return "Seychellois Rupee";
      case "SDD": return "Sudanese Dinar (1992–2007)";
      case "SDG": return "Sudanese Pound";
      case "SDP": return "Sudanese Pound (1957–1998)";
      case "SEK": return "Swedish Krona";
      case "SGD": return "Singapore Dollar";
      case "SHP": return "St. Helena Pound";
      case "SIT": return "Slovenian Tolar";
      case "SKK": return "Slovak Koruna";
      case "SLL": return "Sierra Leonean Leone";
      case "SOS": return "Somali Shilling";
      case "SRD": return "Surinamese Dollar";
      case "SRG": return "Surinamese Guilder";
      case "SSP": return "South Sudanese Pound";
      case "STD": return "São Tomé & Príncipe Dobra";
      case "SUR": return "Soviet Rouble";
      case "SVC": return "Salvadoran Colón";
      case "SYP": return "Syrian Pound";
      case "SZL": return "Swazi Lilangeni";
      case "THB": return "Thai Baht";
      case "TJR": return "Tajikistani Ruble";
      case "TJS": return "Tajikistani Somoni";
      case "TMM": return "Turkmenistani Manat (1993–2009)";
      case "TMT": return "Turkmenistani Manat";
      case "TND": return "Tunisian Dinar";
      case "TOP": return "Tongan Paʻanga";
      case "TPE": return "Timorese Escudo";
      case "TRL": return "Turkish Lira (1922–2005)";
      case "TRY": return "Turkish Lira";
      case "TTD": return "Trinidad & Tobago Dollar";
      case "TWD": return "New Taiwan Dollar";
      case "TZS": return "Tanzanian Shilling";
      case "UAH": return "Ukrainian Hryvnia";
      case "UAK": return "Ukrainian Karbovanets";
      case "UGS": return "Ugandan Shilling (1966–1987)";
      case "UGX": return "Ugandan Shilling";
      case "USD": return "US Dollar";
      case "USN": return "US Dollar (Next day)";
      case "USS": return "US Dollar (Same day)";
      case "UYI": return "Uruguayan Peso (Indexed Units)";
      case "UYP": return "Uruguayan Peso (1975–1993)";
      case "UYU": return "Uruguayan Peso";
      case "UZS": return "Uzbekistani Som";
      case "VEB": return "Venezuelan Bolívar (1871–2008)";
      case "VEF": return "Venezuelan Bolívar";
      case "VND": return "Vietnamese Dong";
      case "VNN": return "Vietnamese Dong (1978–1985)";
      case "VUV": return "Vanuatu Vatu";
      case "WST": return "Samoan Tala";
      case "XAF": return "Central African CFA Franc";
      case "XAG": return "Silver";
      case "XAU": return "Gold";
      case "XBA": return "European Composite Unit";
      case "XBB": return "European Monetary Unit";
      case "XBC": return "European Unit of Account (XBC)";
      case "XBD": return "European Unit of Account (XBD)";
      case "XCD": return "East Caribbean Dollar";
      case "XDR": return "Special Drawing Rights";
      case "XEU": return "European Currency Unit";
      case "XFO": return "French Gold Franc";
      case "XFU": return "French UIC-Franc";
      case "XOF": return "West African CFA Franc";
      case "XPD": return "Palladium";
      case "XPF": return "CFP Franc";
      case "XPT": return "Platinum";
      case "XRE": return "RINET Funds";
      case "XSU": return "Sucre";
      case "XTS": return "Testing Currency Code";
      case "XUA": return "ADB Unit of Account";
      case "XXX": return "Unknown Currency";
      case "YDD": return "Yemeni Dinar";
      case "YER": return "Yemeni Rial";
      case "YUD": return "Yugoslavian Hard Dinar (1966–1990)";
      case "YUM": return "Yugoslavian New Dinar (1994–2002)";
      case "YUN": return "Yugoslavian Convertible Dinar (1990–1992)";
      case "YUR": return "Yugoslavian Reformed Dinar (1992–1993)";
      case "ZAL": return "South African Rand (financial)";
      case "ZAR": return "South African Rand";
      case "ZMK": return "Zambian Kwacha (1968–2012)";
      case "ZMW": return "Zambian Kwacha";
      case "ZRN": return "Zairean New Zaire (1993–1998)";
      case "ZRZ": return "Zairean Zaire (1971–1993)";
      case "ZWD": return "Zimbabwean Dollar (1980–2008)";
      case "ZWL": return "Zimbabwean Dollar (2009)";
      case "ZWR": return "Zimbabwean Dollar (2008)";
      default: return "";
    }
  }

  protected String getCurrencyPluralName(String code, PluralCategory category) {
    switch (code) {
      case "ADP": {
        switch (category) {
          case ONE: return "Andorran peseta";
          case OTHER:
              default: return "Andorran pesetas";
        }
      }
      case "AED": {
        switch (category) {
          case ONE: return "UAE dirham";
          case OTHER:
              default: return "UAE dirhams";
        }
      }
      case "AFA": {
        switch (category) {
          case ONE: return "Afghan afghani (1927–2002)";
          case OTHER:
              default: return "Afghan afghanis (1927–2002)";
        }
      }
      case "AFN": {
        switch (category) {
          case ONE: return "Afghan Afghani";
          case OTHER:
              default: return "Afghan Afghanis";
        }
      }
      case "ALK": {
        switch (category) {
          case ONE: return "Albanian lek (1946–1965)";
          case OTHER:
              default: return "Albanian lekë (1946–1965)";
        }
      }
      case "ALL": {
        switch (category) {
          case ONE: return "Albanian lek";
          case OTHER:
              default: return "Albanian lekë";
        }
      }
      case "AMD": {
        switch (category) {
          case ONE: return "Armenian dram";
          case OTHER:
              default: return "Armenian drams";
        }
      }
      case "ANG": {
        switch (category) {
          case ONE: return "Netherlands Antillean guilder";
          case OTHER:
              default: return "Netherlands Antillean guilders";
        }
      }
      case "AOA": {
        switch (category) {
          case ONE: return "Angolan kwanza";
          case OTHER:
              default: return "Angolan kwanzas";
        }
      }
      case "AOK": {
        switch (category) {
          case ONE: return "Angolan kwanza (1977–1991)";
          case OTHER:
              default: return "Angolan kwanzas (1977–1991)";
        }
      }
      case "AON": {
        switch (category) {
          case ONE: return "Angolan new kwanza (1990–2000)";
          case OTHER:
              default: return "Angolan new kwanzas (1990–2000)";
        }
      }
      case "AOR": {
        switch (category) {
          case ONE: return "Angolan readjusted kwanza (1995–1999)";
          case OTHER:
              default: return "Angolan readjusted kwanzas (1995–1999)";
        }
      }
      case "ARA": {
        switch (category) {
          case ONE: return "Argentine austral";
          case OTHER:
              default: return "Argentine australs";
        }
      }
      case "ARL": {
        switch (category) {
          case ONE: return "Argentine peso ley (1970–1983)";
          case OTHER:
              default: return "Argentine pesos ley (1970–1983)";
        }
      }
      case "ARM": {
        switch (category) {
          case ONE: return "Argentine peso (1881–1970)";
          case OTHER:
              default: return "Argentine pesos (1881–1970)";
        }
      }
      case "ARP": {
        switch (category) {
          case ONE: return "Argentine peso (1983–1985)";
          case OTHER:
              default: return "Argentine pesos (1983–1985)";
        }
      }
      case "ARS": {
        switch (category) {
          case ONE: return "Argentine peso";
          case OTHER:
              default: return "Argentine pesos";
        }
      }
      case "ATS": {
        switch (category) {
          case ONE: return "Austrian schilling";
          case OTHER:
              default: return "Austrian schillings";
        }
      }
      case "AUD": {
        switch (category) {
          case ONE: return "Australian dollar";
          case OTHER:
              default: return "Australian dollars";
        }
      }
      case "AWG": {
        switch (category) {
          case ONE: return "Aruban florin";
          case OTHER:
              default: return "Aruban florin";
        }
      }
      case "AZM": {
        switch (category) {
          case ONE: return "Azerbaijani manat (1993–2006)";
          case OTHER:
              default: return "Azerbaijani manats (1993–2006)";
        }
      }
      case "AZN": {
        switch (category) {
          case ONE: return "Azerbaijani manat";
          case OTHER:
              default: return "Azerbaijani manats";
        }
      }
      case "BAD": {
        switch (category) {
          case ONE: return "Bosnia-Herzegovina dinar (1992–1994)";
          case OTHER:
              default: return "Bosnia-Herzegovina dinars (1992–1994)";
        }
      }
      case "BAM": {
        switch (category) {
          case ONE: return "Bosnia-Herzegovina convertible mark";
          case OTHER:
              default: return "Bosnia-Herzegovina convertible marks";
        }
      }
      case "BAN": {
        switch (category) {
          case ONE: return "Bosnia-Herzegovina new dinar (1994–1997)";
          case OTHER:
              default: return "Bosnia-Herzegovina new dinars (1994–1997)";
        }
      }
      case "BBD": {
        switch (category) {
          case ONE: return "Barbadian dollar";
          case OTHER:
              default: return "Barbadian dollars";
        }
      }
      case "BDT": {
        switch (category) {
          case ONE: return "Bangladeshi taka";
          case OTHER:
              default: return "Bangladeshi takas";
        }
      }
      case "BEC": {
        switch (category) {
          case ONE: return "Belgian franc (convertible)";
          case OTHER:
              default: return "Belgian francs (convertible)";
        }
      }
      case "BEF": {
        switch (category) {
          case ONE: return "Belgian franc";
          case OTHER:
              default: return "Belgian francs";
        }
      }
      case "BEL": {
        switch (category) {
          case ONE: return "Belgian franc (financial)";
          case OTHER:
              default: return "Belgian francs (financial)";
        }
      }
      case "BGL": {
        switch (category) {
          case ONE: return "Bulgarian hard lev";
          case OTHER:
              default: return "Bulgarian hard leva";
        }
      }
      case "BGM": {
        switch (category) {
          case ONE: return "Bulgarian socialist lev";
          case OTHER:
              default: return "Bulgarian socialist leva";
        }
      }
      case "BGN": {
        switch (category) {
          case ONE: return "Bulgarian lev";
          case OTHER:
              default: return "Bulgarian leva";
        }
      }
      case "BGO": {
        switch (category) {
          case ONE: return "Bulgarian lev (1879–1952)";
          case OTHER:
              default: return "Bulgarian leva (1879–1952)";
        }
      }
      case "BHD": {
        switch (category) {
          case ONE: return "Bahraini dinar";
          case OTHER:
              default: return "Bahraini dinars";
        }
      }
      case "BIF": {
        switch (category) {
          case ONE: return "Burundian franc";
          case OTHER:
              default: return "Burundian francs";
        }
      }
      case "BMD": {
        switch (category) {
          case ONE: return "Bermudan dollar";
          case OTHER:
              default: return "Bermudan dollars";
        }
      }
      case "BND": {
        switch (category) {
          case ONE: return "Brunei dollar";
          case OTHER:
              default: return "Brunei dollars";
        }
      }
      case "BOB": {
        switch (category) {
          case ONE: return "Bolivian boliviano";
          case OTHER:
              default: return "Bolivian bolivianos";
        }
      }
      case "BOL": {
        switch (category) {
          case ONE: return "Bolivian boliviano (1863–1963)";
          case OTHER:
              default: return "Bolivian bolivianos (1863–1963)";
        }
      }
      case "BOP": {
        switch (category) {
          case ONE: return "Bolivian peso";
          case OTHER:
              default: return "Bolivian pesos";
        }
      }
      case "BOV": {
        switch (category) {
          case ONE: return "Bolivian mvdol";
          case OTHER:
              default: return "Bolivian mvdols";
        }
      }
      case "BRB": {
        switch (category) {
          case ONE: return "Brazilian new cruzeiro (1967–1986)";
          case OTHER:
              default: return "Brazilian new cruzeiros (1967–1986)";
        }
      }
      case "BRC": {
        switch (category) {
          case ONE: return "Brazilian cruzado (1986–1989)";
          case OTHER:
              default: return "Brazilian cruzados (1986–1989)";
        }
      }
      case "BRE": {
        switch (category) {
          case ONE: return "Brazilian cruzeiro (1990–1993)";
          case OTHER:
              default: return "Brazilian cruzeiros (1990–1993)";
        }
      }
      case "BRL": {
        switch (category) {
          case ONE: return "Brazilian real";
          case OTHER:
              default: return "Brazilian reals";
        }
      }
      case "BRN": {
        switch (category) {
          case ONE: return "Brazilian new cruzado (1989–1990)";
          case OTHER:
              default: return "Brazilian new cruzados (1989–1990)";
        }
      }
      case "BRR": {
        switch (category) {
          case ONE: return "Brazilian cruzeiro (1993–1994)";
          case OTHER:
              default: return "Brazilian cruzeiros (1993–1994)";
        }
      }
      case "BRZ": {
        switch (category) {
          case ONE: return "Brazilian cruzeiro (1942–1967)";
          case OTHER:
              default: return "Brazilian cruzeiros (1942–1967)";
        }
      }
      case "BSD": {
        switch (category) {
          case ONE: return "Bahamian dollar";
          case OTHER:
              default: return "Bahamian dollars";
        }
      }
      case "BTN": {
        switch (category) {
          case ONE: return "Bhutanese ngultrum";
          case OTHER:
              default: return "Bhutanese ngultrums";
        }
      }
      case "BUK": {
        switch (category) {
          case ONE: return "Burmese kyat";
          case OTHER:
              default: return "Burmese kyats";
        }
      }
      case "BWP": {
        switch (category) {
          case ONE: return "Botswanan pula";
          case OTHER:
              default: return "Botswanan pulas";
        }
      }
      case "BYB": {
        switch (category) {
          case ONE: return "Belarusian ruble (1994–1999)";
          case OTHER:
              default: return "Belarusian rubles (1994–1999)";
        }
      }
      case "BYN": {
        switch (category) {
          case ONE: return "Belarusian ruble";
          case OTHER:
              default: return "Belarusian rubles";
        }
      }
      case "BYR": {
        switch (category) {
          case ONE: return "Belarusian ruble (2000–2016)";
          case OTHER:
              default: return "Belarusian rubles (2000–2016)";
        }
      }
      case "BZD": {
        switch (category) {
          case ONE: return "Belize dollar";
          case OTHER:
              default: return "Belize dollars";
        }
      }
      case "CAD": {
        switch (category) {
          case ONE: return "Canadian dollar";
          case OTHER:
              default: return "Canadian dollars";
        }
      }
      case "CDF": {
        switch (category) {
          case ONE: return "Congolese franc";
          case OTHER:
              default: return "Congolese francs";
        }
      }
      case "CHE": {
        switch (category) {
          case ONE: return "WIR euro";
          case OTHER:
              default: return "WIR euros";
        }
      }
      case "CHF": {
        switch (category) {
          case ONE: return "Swiss franc";
          case OTHER:
              default: return "Swiss francs";
        }
      }
      case "CHW": {
        switch (category) {
          case ONE: return "WIR franc";
          case OTHER:
              default: return "WIR francs";
        }
      }
      case "CLE": {
        switch (category) {
          case ONE: return "Chilean escudo";
          case OTHER:
              default: return "Chilean escudos";
        }
      }
      case "CLF": {
        switch (category) {
          case ONE: return "Chilean unit of account (UF)";
          case OTHER:
              default: return "Chilean units of account (UF)";
        }
      }
      case "CLP": {
        switch (category) {
          case ONE: return "Chilean peso";
          case OTHER:
              default: return "Chilean pesos";
        }
      }
      case "CNX": {
        switch (category) {
          case ONE: return "Chinese People’s Bank dollar";
          case OTHER:
              default: return "Chinese People’s Bank dollars";
        }
      }
      case "CNY": {
        switch (category) {
          case ONE: return "Chinese yuan";
          case OTHER:
              default: return "Chinese yuan";
        }
      }
      case "COP": {
        switch (category) {
          case ONE: return "Colombian peso";
          case OTHER:
              default: return "Colombian pesos";
        }
      }
      case "COU": {
        switch (category) {
          case ONE: return "Colombian real value unit";
          case OTHER:
              default: return "Colombian real value units";
        }
      }
      case "CRC": {
        switch (category) {
          case ONE: return "Costa Rican colón";
          case OTHER:
              default: return "Costa Rican colóns";
        }
      }
      case "CSD": {
        switch (category) {
          case ONE: return "Serbian dinar (2002–2006)";
          case OTHER:
              default: return "Serbian dinars (2002–2006)";
        }
      }
      case "CSK": {
        switch (category) {
          case ONE: return "Czechoslovak hard koruna";
          case OTHER:
              default: return "Czechoslovak hard korunas";
        }
      }
      case "CUC": {
        switch (category) {
          case ONE: return "Cuban convertible peso";
          case OTHER:
              default: return "Cuban convertible pesos";
        }
      }
      case "CUP": {
        switch (category) {
          case ONE: return "Cuban peso";
          case OTHER:
              default: return "Cuban pesos";
        }
      }
      case "CVE": {
        switch (category) {
          case ONE: return "Cape Verdean escudo";
          case OTHER:
              default: return "Cape Verdean escudos";
        }
      }
      case "CYP": {
        switch (category) {
          case ONE: return "Cypriot pound";
          case OTHER:
              default: return "Cypriot pounds";
        }
      }
      case "CZK": {
        switch (category) {
          case ONE: return "Czech koruna";
          case OTHER:
              default: return "Czech korunas";
        }
      }
      case "DDM": {
        switch (category) {
          case ONE: return "East German mark";
          case OTHER:
              default: return "East German marks";
        }
      }
      case "DEM": {
        switch (category) {
          case ONE: return "German mark";
          case OTHER:
              default: return "German marks";
        }
      }
      case "DJF": {
        switch (category) {
          case ONE: return "Djiboutian franc";
          case OTHER:
              default: return "Djiboutian francs";
        }
      }
      case "DKK": {
        switch (category) {
          case ONE: return "Danish krone";
          case OTHER:
              default: return "Danish kroner";
        }
      }
      case "DOP": {
        switch (category) {
          case ONE: return "Dominican peso";
          case OTHER:
              default: return "Dominican pesos";
        }
      }
      case "DZD": {
        switch (category) {
          case ONE: return "Algerian dinar";
          case OTHER:
              default: return "Algerian dinars";
        }
      }
      case "ECS": {
        switch (category) {
          case ONE: return "Ecuadorian sucre";
          case OTHER:
              default: return "Ecuadorian sucres";
        }
      }
      case "ECV": {
        switch (category) {
          case ONE: return "Ecuadorian unit of constant value";
          case OTHER:
              default: return "Ecuadorian units of constant value";
        }
      }
      case "EEK": {
        switch (category) {
          case ONE: return "Estonian kroon";
          case OTHER:
              default: return "Estonian kroons";
        }
      }
      case "EGP": {
        switch (category) {
          case ONE: return "Egyptian pound";
          case OTHER:
              default: return "Egyptian pounds";
        }
      }
      case "ERN": {
        switch (category) {
          case ONE: return "Eritrean nakfa";
          case OTHER:
              default: return "Eritrean nakfas";
        }
      }
      case "ESA": {
        switch (category) {
          case ONE: return "Spanish peseta (A account)";
          case OTHER:
              default: return "Spanish pesetas (A account)";
        }
      }
      case "ESB": {
        switch (category) {
          case ONE: return "Spanish peseta (convertible account)";
          case OTHER:
              default: return "Spanish pesetas (convertible account)";
        }
      }
      case "ESP": {
        switch (category) {
          case ONE: return "Spanish peseta";
          case OTHER:
              default: return "Spanish pesetas";
        }
      }
      case "ETB": {
        switch (category) {
          case ONE: return "Ethiopian birr";
          case OTHER:
              default: return "Ethiopian birrs";
        }
      }
      case "EUR": {
        switch (category) {
          case ONE: return "euro";
          case OTHER:
              default: return "euros";
        }
      }
      case "FIM": {
        switch (category) {
          case ONE: return "Finnish markka";
          case OTHER:
              default: return "Finnish markkas";
        }
      }
      case "FJD": {
        switch (category) {
          case ONE: return "Fijian dollar";
          case OTHER:
              default: return "Fijian dollars";
        }
      }
      case "FKP": {
        switch (category) {
          case ONE: return "Falkland Islands pound";
          case OTHER:
              default: return "Falkland Islands pounds";
        }
      }
      case "FRF": {
        switch (category) {
          case ONE: return "French franc";
          case OTHER:
              default: return "French francs";
        }
      }
      case "GBP": {
        switch (category) {
          case ONE: return "British pound";
          case OTHER:
              default: return "British pounds";
        }
      }
      case "GEK": {
        switch (category) {
          case ONE: return "Georgian kupon larit";
          case OTHER:
              default: return "Georgian kupon larits";
        }
      }
      case "GEL": {
        switch (category) {
          case ONE: return "Georgian lari";
          case OTHER:
              default: return "Georgian laris";
        }
      }
      case "GHC": {
        switch (category) {
          case ONE: return "Ghanaian cedi (1979–2007)";
          case OTHER:
              default: return "Ghanaian cedis (1979–2007)";
        }
      }
      case "GHS": {
        switch (category) {
          case ONE: return "Ghanaian cedi";
          case OTHER:
              default: return "Ghanaian cedis";
        }
      }
      case "GIP": {
        switch (category) {
          case ONE: return "Gibraltar pound";
          case OTHER:
              default: return "Gibraltar pounds";
        }
      }
      case "GMD": {
        switch (category) {
          case ONE: return "Gambian dalasi";
          case OTHER:
              default: return "Gambian dalasis";
        }
      }
      case "GNF": {
        switch (category) {
          case ONE: return "Guinean franc";
          case OTHER:
              default: return "Guinean francs";
        }
      }
      case "GNS": {
        switch (category) {
          case ONE: return "Guinean syli";
          case OTHER:
              default: return "Guinean sylis";
        }
      }
      case "GQE": {
        switch (category) {
          case ONE: return "Equatorial Guinean ekwele";
          case OTHER:
              default: return "Equatorial Guinean ekwele";
        }
      }
      case "GRD": {
        switch (category) {
          case ONE: return "Greek drachma";
          case OTHER:
              default: return "Greek drachmas";
        }
      }
      case "GTQ": {
        switch (category) {
          case ONE: return "Guatemalan quetzal";
          case OTHER:
              default: return "Guatemalan quetzals";
        }
      }
      case "GWE": {
        switch (category) {
          case ONE: return "Portuguese Guinea escudo";
          case OTHER:
              default: return "Portuguese Guinea escudos";
        }
      }
      case "GWP": {
        switch (category) {
          case ONE: return "Guinea-Bissau peso";
          case OTHER:
              default: return "Guinea-Bissau pesos";
        }
      }
      case "GYD": {
        switch (category) {
          case ONE: return "Guyanaese dollar";
          case OTHER:
              default: return "Guyanaese dollars";
        }
      }
      case "HKD": {
        switch (category) {
          case ONE: return "Hong Kong dollar";
          case OTHER:
              default: return "Hong Kong dollars";
        }
      }
      case "HNL": {
        switch (category) {
          case ONE: return "Honduran lempira";
          case OTHER:
              default: return "Honduran lempiras";
        }
      }
      case "HRD": {
        switch (category) {
          case ONE: return "Croatian dinar";
          case OTHER:
              default: return "Croatian dinars";
        }
      }
      case "HRK": {
        switch (category) {
          case ONE: return "Croatian kuna";
          case OTHER:
              default: return "Croatian kunas";
        }
      }
      case "HTG": {
        switch (category) {
          case ONE: return "Haitian gourde";
          case OTHER:
              default: return "Haitian gourdes";
        }
      }
      case "HUF": {
        switch (category) {
          case ONE: return "Hungarian forint";
          case OTHER:
              default: return "Hungarian forints";
        }
      }
      case "IDR": {
        switch (category) {
          case ONE: return "Indonesian rupiah";
          case OTHER:
              default: return "Indonesian rupiahs";
        }
      }
      case "IEP": {
        switch (category) {
          case ONE: return "Irish pound";
          case OTHER:
              default: return "Irish pounds";
        }
      }
      case "ILP": {
        switch (category) {
          case ONE: return "Israeli pound";
          case OTHER:
              default: return "Israeli pounds";
        }
      }
      case "ILR": {
        switch (category) {
          case ONE: return "Israeli shekel (1980–1985)";
          case OTHER:
              default: return "Israeli shekels (1980–1985)";
        }
      }
      case "ILS": {
        switch (category) {
          case ONE: return "Israeli new shekel";
          case OTHER:
              default: return "Israeli new shekels";
        }
      }
      case "INR": {
        switch (category) {
          case ONE: return "Indian rupee";
          case OTHER:
              default: return "Indian rupees";
        }
      }
      case "IQD": {
        switch (category) {
          case ONE: return "Iraqi dinar";
          case OTHER:
              default: return "Iraqi dinars";
        }
      }
      case "IRR": {
        switch (category) {
          case ONE: return "Iranian rial";
          case OTHER:
              default: return "Iranian rials";
        }
      }
      case "ISJ": {
        switch (category) {
          case ONE: return "Icelandic króna (1918–1981)";
          case OTHER:
              default: return "Icelandic krónur (1918–1981)";
        }
      }
      case "ISK": {
        switch (category) {
          case ONE: return "Icelandic króna";
          case OTHER:
              default: return "Icelandic krónur";
        }
      }
      case "ITL": {
        switch (category) {
          case ONE: return "Italian lira";
          case OTHER:
              default: return "Italian liras";
        }
      }
      case "JMD": {
        switch (category) {
          case ONE: return "Jamaican dollar";
          case OTHER:
              default: return "Jamaican dollars";
        }
      }
      case "JOD": {
        switch (category) {
          case ONE: return "Jordanian dinar";
          case OTHER:
              default: return "Jordanian dinars";
        }
      }
      case "JPY": {
        switch (category) {
          case ONE: return "Japanese yen";
          case OTHER:
              default: return "Japanese yen";
        }
      }
      case "KES": {
        switch (category) {
          case ONE: return "Kenyan shilling";
          case OTHER:
              default: return "Kenyan shillings";
        }
      }
      case "KGS": {
        switch (category) {
          case ONE: return "Kyrgystani som";
          case OTHER:
              default: return "Kyrgystani soms";
        }
      }
      case "KHR": {
        switch (category) {
          case ONE: return "Cambodian riel";
          case OTHER:
              default: return "Cambodian riels";
        }
      }
      case "KMF": {
        switch (category) {
          case ONE: return "Comorian franc";
          case OTHER:
              default: return "Comorian francs";
        }
      }
      case "KPW": {
        switch (category) {
          case ONE: return "North Korean won";
          case OTHER:
              default: return "North Korean won";
        }
      }
      case "KRH": {
        switch (category) {
          case ONE: return "South Korean hwan (1953–1962)";
          case OTHER:
              default: return "South Korean hwan (1953–1962)";
        }
      }
      case "KRO": {
        switch (category) {
          case ONE: return "South Korean won (1945–1953)";
          case OTHER:
              default: return "South Korean won (1945–1953)";
        }
      }
      case "KRW": {
        switch (category) {
          case ONE: return "South Korean won";
          case OTHER:
              default: return "South Korean won";
        }
      }
      case "KWD": {
        switch (category) {
          case ONE: return "Kuwaiti dinar";
          case OTHER:
              default: return "Kuwaiti dinars";
        }
      }
      case "KYD": {
        switch (category) {
          case ONE: return "Cayman Islands dollar";
          case OTHER:
              default: return "Cayman Islands dollars";
        }
      }
      case "KZT": {
        switch (category) {
          case ONE: return "Kazakhstani tenge";
          case OTHER:
              default: return "Kazakhstani tenges";
        }
      }
      case "LAK": {
        switch (category) {
          case ONE: return "Laotian kip";
          case OTHER:
              default: return "Laotian kips";
        }
      }
      case "LBP": {
        switch (category) {
          case ONE: return "Lebanese pound";
          case OTHER:
              default: return "Lebanese pounds";
        }
      }
      case "LKR": {
        switch (category) {
          case ONE: return "Sri Lankan rupee";
          case OTHER:
              default: return "Sri Lankan rupees";
        }
      }
      case "LRD": {
        switch (category) {
          case ONE: return "Liberian dollar";
          case OTHER:
              default: return "Liberian dollars";
        }
      }
      case "LSL": {
        switch (category) {
          case ONE: return "Lesotho loti";
          case OTHER:
              default: return "Lesotho lotis";
        }
      }
      case "LTL": {
        switch (category) {
          case ONE: return "Lithuanian litas";
          case OTHER:
              default: return "Lithuanian litai";
        }
      }
      case "LTT": {
        switch (category) {
          case ONE: return "Lithuanian talonas";
          case OTHER:
              default: return "Lithuanian talonases";
        }
      }
      case "LUC": {
        switch (category) {
          case ONE: return "Luxembourgian convertible franc";
          case OTHER:
              default: return "Luxembourgian convertible francs";
        }
      }
      case "LUF": {
        switch (category) {
          case ONE: return "Luxembourgian franc";
          case OTHER:
              default: return "Luxembourgian francs";
        }
      }
      case "LUL": {
        switch (category) {
          case ONE: return "Luxembourg financial franc";
          case OTHER:
              default: return "Luxembourg financial francs";
        }
      }
      case "LVL": {
        switch (category) {
          case ONE: return "Latvian lats";
          case OTHER:
              default: return "Latvian lati";
        }
      }
      case "LVR": {
        switch (category) {
          case ONE: return "Latvian ruble";
          case OTHER:
              default: return "Latvian rubles";
        }
      }
      case "LYD": {
        switch (category) {
          case ONE: return "Libyan dinar";
          case OTHER:
              default: return "Libyan dinars";
        }
      }
      case "MAD": {
        switch (category) {
          case ONE: return "Moroccan dirham";
          case OTHER:
              default: return "Moroccan dirhams";
        }
      }
      case "MAF": {
        switch (category) {
          case ONE: return "Moroccan franc";
          case OTHER:
              default: return "Moroccan francs";
        }
      }
      case "MCF": {
        switch (category) {
          case ONE: return "Monegasque franc";
          case OTHER:
              default: return "Monegasque francs";
        }
      }
      case "MDC": {
        switch (category) {
          case ONE: return "Moldovan cupon";
          case OTHER:
              default: return "Moldovan cupon";
        }
      }
      case "MDL": {
        switch (category) {
          case ONE: return "Moldovan leu";
          case OTHER:
              default: return "Moldovan lei";
        }
      }
      case "MGA": {
        switch (category) {
          case ONE: return "Malagasy ariary";
          case OTHER:
              default: return "Malagasy ariaries";
        }
      }
      case "MGF": {
        switch (category) {
          case ONE: return "Malagasy franc";
          case OTHER:
              default: return "Malagasy francs";
        }
      }
      case "MKD": {
        switch (category) {
          case ONE: return "Macedonian denar";
          case OTHER:
              default: return "Macedonian denari";
        }
      }
      case "MKN": {
        switch (category) {
          case ONE: return "Macedonian denar (1992–1993)";
          case OTHER:
              default: return "Macedonian denari (1992–1993)";
        }
      }
      case "MLF": {
        switch (category) {
          case ONE: return "Malian franc";
          case OTHER:
              default: return "Malian francs";
        }
      }
      case "MMK": {
        switch (category) {
          case ONE: return "Myanmar kyat";
          case OTHER:
              default: return "Myanmar kyats";
        }
      }
      case "MNT": {
        switch (category) {
          case ONE: return "Mongolian tugrik";
          case OTHER:
              default: return "Mongolian tugriks";
        }
      }
      case "MOP": {
        switch (category) {
          case ONE: return "Macanese pataca";
          case OTHER:
              default: return "Macanese patacas";
        }
      }
      case "MRO": {
        switch (category) {
          case ONE: return "Mauritanian ouguiya";
          case OTHER:
              default: return "Mauritanian ouguiyas";
        }
      }
      case "MTL": {
        switch (category) {
          case ONE: return "Maltese lira";
          case OTHER:
              default: return "Maltese lira";
        }
      }
      case "MTP": {
        switch (category) {
          case ONE: return "Maltese pound";
          case OTHER:
              default: return "Maltese pounds";
        }
      }
      case "MUR": {
        switch (category) {
          case ONE: return "Mauritian rupee";
          case OTHER:
              default: return "Mauritian rupees";
        }
      }
      case "MVP": {
        switch (category) {
          case ONE: return "Maldivian rupee (1947–1981)";
          case OTHER:
              default: return "Maldivian rupees (1947–1981)";
        }
      }
      case "MVR": {
        switch (category) {
          case ONE: return "Maldivian rufiyaa";
          case OTHER:
              default: return "Maldivian rufiyaas";
        }
      }
      case "MWK": {
        switch (category) {
          case ONE: return "Malawian kwacha";
          case OTHER:
              default: return "Malawian kwachas";
        }
      }
      case "MXN": {
        switch (category) {
          case ONE: return "Mexican peso";
          case OTHER:
              default: return "Mexican pesos";
        }
      }
      case "MXP": {
        switch (category) {
          case ONE: return "Mexican silver peso (1861–1992)";
          case OTHER:
              default: return "Mexican silver pesos (1861–1992)";
        }
      }
      case "MXV": {
        switch (category) {
          case ONE: return "Mexican investment unit";
          case OTHER:
              default: return "Mexican investment units";
        }
      }
      case "MYR": {
        switch (category) {
          case ONE: return "Malaysian ringgit";
          case OTHER:
              default: return "Malaysian ringgits";
        }
      }
      case "MZE": {
        switch (category) {
          case ONE: return "Mozambican escudo";
          case OTHER:
              default: return "Mozambican escudos";
        }
      }
      case "MZM": {
        switch (category) {
          case ONE: return "Mozambican metical (1980–2006)";
          case OTHER:
              default: return "Mozambican meticals (1980–2006)";
        }
      }
      case "MZN": {
        switch (category) {
          case ONE: return "Mozambican metical";
          case OTHER:
              default: return "Mozambican meticals";
        }
      }
      case "NAD": {
        switch (category) {
          case ONE: return "Namibian dollar";
          case OTHER:
              default: return "Namibian dollars";
        }
      }
      case "NGN": {
        switch (category) {
          case ONE: return "Nigerian naira";
          case OTHER:
              default: return "Nigerian nairas";
        }
      }
      case "NIC": {
        switch (category) {
          case ONE: return "Nicaraguan córdoba (1988–1991)";
          case OTHER:
              default: return "Nicaraguan córdobas (1988–1991)";
        }
      }
      case "NIO": {
        switch (category) {
          case ONE: return "Nicaraguan córdoba";
          case OTHER:
              default: return "Nicaraguan córdobas";
        }
      }
      case "NLG": {
        switch (category) {
          case ONE: return "Dutch guilder";
          case OTHER:
              default: return "Dutch guilders";
        }
      }
      case "NOK": {
        switch (category) {
          case ONE: return "Norwegian krone";
          case OTHER:
              default: return "Norwegian kroner";
        }
      }
      case "NPR": {
        switch (category) {
          case ONE: return "Nepalese rupee";
          case OTHER:
              default: return "Nepalese rupees";
        }
      }
      case "NZD": {
        switch (category) {
          case ONE: return "New Zealand dollar";
          case OTHER:
              default: return "New Zealand dollars";
        }
      }
      case "OMR": {
        switch (category) {
          case ONE: return "Omani rial";
          case OTHER:
              default: return "Omani rials";
        }
      }
      case "PAB": {
        switch (category) {
          case ONE: return "Panamanian balboa";
          case OTHER:
              default: return "Panamanian balboas";
        }
      }
      case "PEI": {
        switch (category) {
          case ONE: return "Peruvian inti";
          case OTHER:
              default: return "Peruvian intis";
        }
      }
      case "PEN": {
        switch (category) {
          case ONE: return "Peruvian sol";
          case OTHER:
              default: return "Peruvian soles";
        }
      }
      case "PES": {
        switch (category) {
          case ONE: return "Peruvian sol (1863–1965)";
          case OTHER:
              default: return "Peruvian soles (1863–1965)";
        }
      }
      case "PGK": {
        switch (category) {
          case ONE: return "Papua New Guinean kina";
          case OTHER:
              default: return "Papua New Guinean kina";
        }
      }
      case "PHP": {
        switch (category) {
          case ONE: return "Philippine peso";
          case OTHER:
              default: return "Philippine pesos";
        }
      }
      case "PKR": {
        switch (category) {
          case ONE: return "Pakistani rupee";
          case OTHER:
              default: return "Pakistani rupees";
        }
      }
      case "PLN": {
        switch (category) {
          case ONE: return "Polish zloty";
          case OTHER:
              default: return "Polish zlotys";
        }
      }
      case "PLZ": {
        switch (category) {
          case ONE: return "Polish zloty (PLZ)";
          case OTHER:
              default: return "Polish zlotys (PLZ)";
        }
      }
      case "PTE": {
        switch (category) {
          case ONE: return "Portuguese escudo";
          case OTHER:
              default: return "Portuguese escudos";
        }
      }
      case "PYG": {
        switch (category) {
          case ONE: return "Paraguayan guarani";
          case OTHER:
              default: return "Paraguayan guaranis";
        }
      }
      case "QAR": {
        switch (category) {
          case ONE: return "Qatari rial";
          case OTHER:
              default: return "Qatari rials";
        }
      }
      case "RHD": {
        switch (category) {
          case ONE: return "Rhodesian dollar";
          case OTHER:
              default: return "Rhodesian dollars";
        }
      }
      case "ROL": {
        switch (category) {
          case ONE: return "Romanian leu (1952–2006)";
          case OTHER:
              default: return "Romanian Lei (1952–2006)";
        }
      }
      case "RON": {
        switch (category) {
          case ONE: return "Romanian leu";
          case OTHER:
              default: return "Romanian lei";
        }
      }
      case "RSD": {
        switch (category) {
          case ONE: return "Serbian dinar";
          case OTHER:
              default: return "Serbian dinars";
        }
      }
      case "RUB": {
        switch (category) {
          case ONE: return "Russian ruble";
          case OTHER:
              default: return "Russian rubles";
        }
      }
      case "RUR": {
        switch (category) {
          case ONE: return "Russian ruble (1991–1998)";
          case OTHER:
              default: return "Russian rubles (1991–1998)";
        }
      }
      case "RWF": {
        switch (category) {
          case ONE: return "Rwandan franc";
          case OTHER:
              default: return "Rwandan francs";
        }
      }
      case "SAR": {
        switch (category) {
          case ONE: return "Saudi riyal";
          case OTHER:
              default: return "Saudi riyals";
        }
      }
      case "SBD": {
        switch (category) {
          case ONE: return "Solomon Islands dollar";
          case OTHER:
              default: return "Solomon Islands dollars";
        }
      }
      case "SCR": {
        switch (category) {
          case ONE: return "Seychellois rupee";
          case OTHER:
              default: return "Seychellois rupees";
        }
      }
      case "SDD": {
        switch (category) {
          case ONE: return "Sudanese dinar (1992–2007)";
          case OTHER:
              default: return "Sudanese dinars (1992–2007)";
        }
      }
      case "SDG": {
        switch (category) {
          case ONE: return "Sudanese pound";
          case OTHER:
              default: return "Sudanese pounds";
        }
      }
      case "SDP": {
        switch (category) {
          case ONE: return "Sudanese pound (1957–1998)";
          case OTHER:
              default: return "Sudanese pounds (1957–1998)";
        }
      }
      case "SEK": {
        switch (category) {
          case ONE: return "Swedish krona";
          case OTHER:
              default: return "Swedish kronor";
        }
      }
      case "SGD": {
        switch (category) {
          case ONE: return "Singapore dollar";
          case OTHER:
              default: return "Singapore dollars";
        }
      }
      case "SHP": {
        switch (category) {
          case ONE: return "St. Helena pound";
          case OTHER:
              default: return "St. Helena pounds";
        }
      }
      case "SIT": {
        switch (category) {
          case ONE: return "Slovenian tolar";
          case OTHER:
              default: return "Slovenian tolars";
        }
      }
      case "SKK": {
        switch (category) {
          case ONE: return "Slovak koruna";
          case OTHER:
              default: return "Slovak korunas";
        }
      }
      case "SLL": {
        switch (category) {
          case ONE: return "Sierra Leonean leone";
          case OTHER:
              default: return "Sierra Leonean leones";
        }
      }
      case "SOS": {
        switch (category) {
          case ONE: return "Somali shilling";
          case OTHER:
              default: return "Somali shillings";
        }
      }
      case "SRD": {
        switch (category) {
          case ONE: return "Surinamese dollar";
          case OTHER:
              default: return "Surinamese dollars";
        }
      }
      case "SRG": {
        switch (category) {
          case ONE: return "Surinamese guilder";
          case OTHER:
              default: return "Surinamese guilders";
        }
      }
      case "SSP": {
        switch (category) {
          case ONE: return "South Sudanese pound";
          case OTHER:
              default: return "South Sudanese pounds";
        }
      }
      case "STD": {
        switch (category) {
          case ONE: return "São Tomé & Príncipe dobra";
          case OTHER:
              default: return "São Tomé & Príncipe dobras";
        }
      }
      case "SUR": {
        switch (category) {
          case ONE: return "Soviet rouble";
          case OTHER:
              default: return "Soviet roubles";
        }
      }
      case "SVC": {
        switch (category) {
          case ONE: return "Salvadoran colón";
          case OTHER:
              default: return "Salvadoran colones";
        }
      }
      case "SYP": {
        switch (category) {
          case ONE: return "Syrian pound";
          case OTHER:
              default: return "Syrian pounds";
        }
      }
      case "SZL": {
        switch (category) {
          case ONE: return "Swazi lilangeni";
          case OTHER:
              default: return "Swazi emalangeni";
        }
      }
      case "THB": {
        switch (category) {
          case ONE: return "Thai baht";
          case OTHER:
              default: return "Thai baht";
        }
      }
      case "TJR": {
        switch (category) {
          case ONE: return "Tajikistani ruble";
          case OTHER:
              default: return "Tajikistani rubles";
        }
      }
      case "TJS": {
        switch (category) {
          case ONE: return "Tajikistani somoni";
          case OTHER:
              default: return "Tajikistani somonis";
        }
      }
      case "TMM": {
        switch (category) {
          case ONE: return "Turkmenistani manat (1993–2009)";
          case OTHER:
              default: return "Turkmenistani manat (1993–2009)";
        }
      }
      case "TMT": {
        switch (category) {
          case ONE: return "Turkmenistani manat";
          case OTHER:
              default: return "Turkmenistani manat";
        }
      }
      case "TND": {
        switch (category) {
          case ONE: return "Tunisian dinar";
          case OTHER:
              default: return "Tunisian dinars";
        }
      }
      case "TOP": {
        switch (category) {
          case ONE: return "Tongan paʻanga";
          case OTHER:
              default: return "Tongan paʻanga";
        }
      }
      case "TPE": {
        switch (category) {
          case ONE: return "Timorese escudo";
          case OTHER:
              default: return "Timorese escudos";
        }
      }
      case "TRL": {
        switch (category) {
          case ONE: return "Turkish lira (1922–2005)";
          case OTHER:
              default: return "Turkish Lira (1922–2005)";
        }
      }
      case "TRY": {
        switch (category) {
          case ONE: return "Turkish lira";
          case OTHER:
              default: return "Turkish Lira";
        }
      }
      case "TTD": {
        switch (category) {
          case ONE: return "Trinidad & Tobago dollar";
          case OTHER:
              default: return "Trinidad & Tobago dollars";
        }
      }
      case "TWD": {
        switch (category) {
          case ONE: return "New Taiwan dollar";
          case OTHER:
              default: return "New Taiwan dollars";
        }
      }
      case "TZS": {
        switch (category) {
          case ONE: return "Tanzanian shilling";
          case OTHER:
              default: return "Tanzanian shillings";
        }
      }
      case "UAH": {
        switch (category) {
          case ONE: return "Ukrainian hryvnia";
          case OTHER:
              default: return "Ukrainian hryvnias";
        }
      }
      case "UAK": {
        switch (category) {
          case ONE: return "Ukrainian karbovanets";
          case OTHER:
              default: return "Ukrainian karbovantsiv";
        }
      }
      case "UGS": {
        switch (category) {
          case ONE: return "Ugandan shilling (1966–1987)";
          case OTHER:
              default: return "Ugandan shillings (1966–1987)";
        }
      }
      case "UGX": {
        switch (category) {
          case ONE: return "Ugandan shilling";
          case OTHER:
              default: return "Ugandan shillings";
        }
      }
      case "USD": {
        switch (category) {
          case ONE: return "US dollar";
          case OTHER:
              default: return "US dollars";
        }
      }
      case "USN": {
        switch (category) {
          case ONE: return "US dollar (next day)";
          case OTHER:
              default: return "US dollars (next day)";
        }
      }
      case "USS": {
        switch (category) {
          case ONE: return "US dollar (same day)";
          case OTHER:
              default: return "US dollars (same day)";
        }
      }
      case "UYI": {
        switch (category) {
          case ONE: return "Uruguayan peso (indexed units)";
          case OTHER:
              default: return "Uruguayan pesos (indexed units)";
        }
      }
      case "UYP": {
        switch (category) {
          case ONE: return "Uruguayan peso (1975–1993)";
          case OTHER:
              default: return "Uruguayan pesos (1975–1993)";
        }
      }
      case "UYU": {
        switch (category) {
          case ONE: return "Uruguayan peso";
          case OTHER:
              default: return "Uruguayan pesos";
        }
      }
      case "UZS": {
        switch (category) {
          case ONE: return "Uzbekistani som";
          case OTHER:
              default: return "Uzbekistani som";
        }
      }
      case "VEB": {
        switch (category) {
          case ONE: return "Venezuelan bolívar (1871–2008)";
          case OTHER:
              default: return "Venezuelan bolívars (1871–2008)";
        }
      }
      case "VEF": {
        switch (category) {
          case ONE: return "Venezuelan bolívar";
          case OTHER:
              default: return "Venezuelan bolívars";
        }
      }
      case "VND": {
        switch (category) {
          case ONE: return "Vietnamese dong";
          case OTHER:
              default: return "Vietnamese dong";
        }
      }
      case "VNN": {
        switch (category) {
          case ONE: return "Vietnamese dong (1978–1985)";
          case OTHER:
              default: return "Vietnamese dong (1978–1985)";
        }
      }
      case "VUV": {
        switch (category) {
          case ONE: return "Vanuatu vatu";
          case OTHER:
              default: return "Vanuatu vatus";
        }
      }
      case "WST": {
        switch (category) {
          case ONE: return "Samoan tala";
          case OTHER:
              default: return "Samoan tala";
        }
      }
      case "XAF": {
        switch (category) {
          case ONE: return "Central African CFA franc";
          case OTHER:
              default: return "Central African CFA francs";
        }
      }
      case "XAG": {
        switch (category) {
          case ONE: return "troy ounce of silver";
          case OTHER:
              default: return "troy ounces of silver";
        }
      }
      case "XAU": {
        switch (category) {
          case ONE: return "troy ounce of gold";
          case OTHER:
              default: return "troy ounces of gold";
        }
      }
      case "XBA": {
        switch (category) {
          case ONE: return "European composite unit";
          case OTHER:
              default: return "European composite units";
        }
      }
      case "XBB": {
        switch (category) {
          case ONE: return "European monetary unit";
          case OTHER:
              default: return "European monetary units";
        }
      }
      case "XBC": {
        switch (category) {
          case ONE: return "European unit of account (XBC)";
          case OTHER:
              default: return "European units of account (XBC)";
        }
      }
      case "XBD": {
        switch (category) {
          case ONE: return "European unit of account (XBD)";
          case OTHER:
              default: return "European units of account (XBD)";
        }
      }
      case "XCD": {
        switch (category) {
          case ONE: return "East Caribbean dollar";
          case OTHER:
              default: return "East Caribbean dollars";
        }
      }
      case "XDR": {
        switch (category) {
          case ONE: return "special drawing rights";
          case OTHER:
              default: return "special drawing rights";
        }
      }
      case "XEU": {
        switch (category) {
          case ONE: return "European currency unit";
          case OTHER:
              default: return "European currency units";
        }
      }
      case "XFO": {
        switch (category) {
          case ONE: return "French gold franc";
          case OTHER:
              default: return "French gold francs";
        }
      }
      case "XFU": {
        switch (category) {
          case ONE: return "French UIC-franc";
          case OTHER:
              default: return "French UIC-francs";
        }
      }
      case "XOF": {
        switch (category) {
          case ONE: return "West African CFA franc";
          case OTHER:
              default: return "West African CFA francs";
        }
      }
      case "XPD": {
        switch (category) {
          case ONE: return "troy ounce of palladium";
          case OTHER:
              default: return "troy ounces of palladium";
        }
      }
      case "XPF": {
        switch (category) {
          case ONE: return "CFP franc";
          case OTHER:
              default: return "CFP francs";
        }
      }
      case "XPT": {
        switch (category) {
          case ONE: return "troy ounce of platinum";
          case OTHER:
              default: return "troy ounces of platinum";
        }
      }
      case "XRE": {
        switch (category) {
          case ONE: return "RINET Funds unit";
          case OTHER:
              default: return "RINET Funds units";
        }
      }
      case "XSU": {
        switch (category) {
          case ONE: return "Sucre";
          case OTHER:
              default: return "Sucres";
        }
      }
      case "XTS": {
        switch (category) {
          case ONE: return "Testing Currency unit";
          case OTHER:
              default: return "Testing Currency units";
        }
      }
      case "XUA": {
        switch (category) {
          case ONE: return "ADB unit of account";
          case OTHER:
              default: return "ADB units of account";
        }
      }
      case "XXX": {
        switch (category) {
          case ONE: return "(unknown unit of currency)";
          case OTHER:
              default: return "(unknown currency)";
        }
      }
      case "YDD": {
        switch (category) {
          case ONE: return "Yemeni dinar";
          case OTHER:
              default: return "Yemeni dinars";
        }
      }
      case "YER": {
        switch (category) {
          case ONE: return "Yemeni rial";
          case OTHER:
              default: return "Yemeni rials";
        }
      }
      case "YUD": {
        switch (category) {
          case ONE: return "Yugoslavian hard dinar (1966–1990)";
          case OTHER:
              default: return "Yugoslavian hard dinars (1966–1990)";
        }
      }
      case "YUM": {
        switch (category) {
          case ONE: return "Yugoslavian new dinar (1994–2002)";
          case OTHER:
              default: return "Yugoslavian new dinars (1994–2002)";
        }
      }
      case "YUN": {
        switch (category) {
          case ONE: return "Yugoslavian convertible dinar (1990–1992)";
          case OTHER:
              default: return "Yugoslavian convertible dinars (1990–1992)";
        }
      }
      case "YUR": {
        switch (category) {
          case ONE: return "Yugoslavian reformed dinar (1992–1993)";
          case OTHER:
              default: return "Yugoslavian reformed dinars (1992–1993)";
        }
      }
      case "ZAL": {
        switch (category) {
          case ONE: return "South African rand (financial)";
          case OTHER:
              default: return "South African rands (financial)";
        }
      }
      case "ZAR": {
        switch (category) {
          case ONE: return "South African rand";
          case OTHER:
              default: return "South African rand";
        }
      }
      case "ZMK": {
        switch (category) {
          case ONE: return "Zambian kwacha (1968–2012)";
          case OTHER:
              default: return "Zambian kwachas (1968–2012)";
        }
      }
      case "ZMW": {
        switch (category) {
          case ONE: return "Zambian kwacha";
          case OTHER:
              default: return "Zambian kwachas";
        }
      }
      case "ZRN": {
        switch (category) {
          case ONE: return "Zairean new zaire (1993–1998)";
          case OTHER:
              default: return "Zairean new zaires (1993–1998)";
        }
      }
      case "ZRZ": {
        switch (category) {
          case ONE: return "Zairean zaire (1971–1993)";
          case OTHER:
              default: return "Zairean zaires (1971–1993)";
        }
      }
      case "ZWD": {
        switch (category) {
          case ONE: return "Zimbabwean dollar (1980–2008)";
          case OTHER:
              default: return "Zimbabwean dollars (1980–2008)";
        }
      }
      case "ZWL": {
        switch (category) {
          case ONE: return "Zimbabwean dollar (2009)";
          case OTHER:
              default: return "Zimbabwean dollars (2009)";
        }
      }
      case "ZWR": {
        switch (category) {
          case ONE: return "Zimbabwean dollar (2008)";
          case OTHER:
              default: return "Zimbabwean dollars (2008)";
        }
      }
      default: return "";
    }
  }

  protected void wrapUnits(PluralCategory category, DigitBuffer dbuf, String unit,
      StringBuilder dest) {
    switch (category) {
      case OTHER:
      default: {
        dbuf.appendTo(dest);
        dest.append(" ");
        dest.append(unit);
        break;
      }
      case ONE: {
        dbuf.appendTo(dest);
        dest.append(" ");
        dest.append(unit);
        break;
      }
    }
  }

  private static class _Params extends NumberFormatterParams {
    _Params() {
      this.decimal = ".";
      this.group = ",";
      this.percent = "%";
      this.minus = "-";
      this.plus = "+";
      this.exponential = "E";
      this.superscriptingExponent = "×";
      this.perMille = "‰";
      this.infinity = "∞";
      this.nan = "NaN";
      this.currencyDecimal = ".";
      this.currencyGroup = ",";
    }
  }
}
