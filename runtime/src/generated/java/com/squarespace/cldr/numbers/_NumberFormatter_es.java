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

public class _NumberFormatter_es extends NumberFormatterBase {
  public static final NumberPattern[] DECIMAL_STANDARD = patterns("#,##0.###", "-#,##0.###");

  public static final NumberPattern[] PERCENT_STANDARD = patterns("#,##0 %", "-#,##0 %");

  public static final NumberPattern[] CURRENCY_STANDARD = patterns("#,##0.00 ¤", "-#,##0.00 ¤");

  public static final NumberPattern[] CURRENCY_ACCOUNTING = patterns("#,##0.00 ¤", "-#,##0.00 ¤");

  public static final NumberPattern[] DECIMAL_STANDARD_COMPACT = patterns("#,##0", "-#,##0");

  public static final NumberPattern[] CURRENCY_STANDARD_COMPACT = patterns("#,##0 ¤", "-#,##0 ¤");

  private final NumberPattern[] DECIMAL_SHORT_1K_ONE = patterns("0 K", "-0 K");

  private final NumberPattern[] DECIMAL_SHORT_1K_OTHER = patterns("0 K", "-0 K");

  private final NumberPattern[] DECIMAL_SHORT_10K_ONE = patterns("00 K", "-00 K");

  private final NumberPattern[] DECIMAL_SHORT_10K_OTHER = patterns("00 K", "-00 K");

  private final NumberPattern[] DECIMAL_SHORT_100K_ONE = patterns("000 K", "-000 K");

  private final NumberPattern[] DECIMAL_SHORT_100K_OTHER = patterns("000 K", "-000 K");

  private final NumberPattern[] DECIMAL_SHORT_1M_ONE = patterns("0 M", "-0 M");

  private final NumberPattern[] DECIMAL_SHORT_1M_OTHER = patterns("0 M", "-0 M");

  private final NumberPattern[] DECIMAL_SHORT_10M_ONE = patterns("00 M", "-00 M");

  private final NumberPattern[] DECIMAL_SHORT_10M_OTHER = patterns("00 M", "-00 M");

  private final NumberPattern[] DECIMAL_SHORT_100M_ONE = patterns("000 M", "-000 M");

  private final NumberPattern[] DECIMAL_SHORT_100M_OTHER = patterns("000 M", "-000 M");

  private final NumberPattern[] DECIMAL_SHORT_1B_ONE = patterns("0000 M", "-0000 M");

  private final NumberPattern[] DECIMAL_SHORT_1B_OTHER = patterns("0000 M", "-0000 M");

  private final NumberPattern[] DECIMAL_SHORT_10B_ONE = patterns("00 MRD", "-00 MRD");

  private final NumberPattern[] DECIMAL_SHORT_10B_OTHER = patterns("00 MRD", "-00 MRD");

  private final NumberPattern[] DECIMAL_SHORT_100B_ONE = patterns("000 MRD", "-000 MRD");

  private final NumberPattern[] DECIMAL_SHORT_100B_OTHER = patterns("000 MRD", "-000 MRD");

  private final NumberPattern[] DECIMAL_SHORT_1T_ONE = patterns("0 B", "-0 B");

  private final NumberPattern[] DECIMAL_SHORT_1T_OTHER = patterns("0 B", "-0 B");

  private final NumberPattern[] DECIMAL_SHORT_10T_ONE = patterns("00 B", "-00 B");

  private final NumberPattern[] DECIMAL_SHORT_10T_OTHER = patterns("00 B", "-00 B");

  private final NumberPattern[] DECIMAL_SHORT_100T_ONE = patterns("000 B", "-000 B");

  private final NumberPattern[] DECIMAL_SHORT_100T_OTHER = patterns("000 B", "-000 B");

  private final int DECIMAL_SHORT_1K_POWER = 3;

  private final int DECIMAL_SHORT_10K_POWER = 3;

  private final int DECIMAL_SHORT_100K_POWER = 3;

  private final int DECIMAL_SHORT_1M_POWER = 6;

  private final int DECIMAL_SHORT_10M_POWER = 6;

  private final int DECIMAL_SHORT_100M_POWER = 6;

  private final int DECIMAL_SHORT_1B_POWER = 6;

  private final int DECIMAL_SHORT_10B_POWER = 9;

  private final int DECIMAL_SHORT_100B_POWER = 9;

  private final int DECIMAL_SHORT_1T_POWER = 12;

  private final int DECIMAL_SHORT_10T_POWER = 12;

  private final int DECIMAL_SHORT_100T_POWER = 12;

  private final NumberPattern[] DECIMAL_LONG_1K_ONE = patterns("0 mil", "-0 mil");

  private final NumberPattern[] DECIMAL_LONG_1K_OTHER = patterns("0 mil", "-0 mil");

  private final NumberPattern[] DECIMAL_LONG_10K_ONE = patterns("00 mil", "-00 mil");

  private final NumberPattern[] DECIMAL_LONG_10K_OTHER = patterns("00 mil", "-00 mil");

  private final NumberPattern[] DECIMAL_LONG_100K_ONE = patterns("000 mil", "-000 mil");

  private final NumberPattern[] DECIMAL_LONG_100K_OTHER = patterns("000 mil", "-000 mil");

  private final NumberPattern[] DECIMAL_LONG_1M_ONE = patterns("0 millón", "-0 millón");

  private final NumberPattern[] DECIMAL_LONG_1M_OTHER = patterns("0 millones", "-0 millones");

  private final NumberPattern[] DECIMAL_LONG_10M_ONE = patterns("00 millones", "-00 millones");

  private final NumberPattern[] DECIMAL_LONG_10M_OTHER = patterns("00 millones", "-00 millones");

  private final NumberPattern[] DECIMAL_LONG_100M_ONE = patterns("000 millones", "-000 millones");

  private final NumberPattern[] DECIMAL_LONG_100M_OTHER = patterns("000 millones", "-000 millones");

  private final NumberPattern[] DECIMAL_LONG_1B_ONE = patterns("0 mil millones", "-0 mil millones");

  private final NumberPattern[] DECIMAL_LONG_1B_OTHER = patterns("0 mil millones", "-0 mil millones");

  private final NumberPattern[] DECIMAL_LONG_10B_ONE = patterns("00 mil millones", "-00 mil millones");

  private final NumberPattern[] DECIMAL_LONG_10B_OTHER = patterns("00 mil millones", "-00 mil millones");

  private final NumberPattern[] DECIMAL_LONG_100B_ONE = patterns("000 mil millones", "-000 mil millones");

  private final NumberPattern[] DECIMAL_LONG_100B_OTHER = patterns("000 mil millones", "-000 mil millones");

  private final NumberPattern[] DECIMAL_LONG_1T_ONE = patterns("0 billón", "-0 billón");

  private final NumberPattern[] DECIMAL_LONG_1T_OTHER = patterns("0 billones", "-0 billones");

  private final NumberPattern[] DECIMAL_LONG_10T_ONE = patterns("00 billones", "-00 billones");

  private final NumberPattern[] DECIMAL_LONG_10T_OTHER = patterns("00 billones", "-00 billones");

  private final NumberPattern[] DECIMAL_LONG_100T_ONE = patterns("000 billones", "-000 billones");

  private final NumberPattern[] DECIMAL_LONG_100T_OTHER = patterns("000 billones", "-000 billones");

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

  private final NumberPattern[] CURRENCY_SHORT_1K_ONE = patterns("0 K ¤", "-0 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_1K_OTHER = patterns("0 K ¤", "-0 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_10K_ONE = patterns("00 K ¤", "-00 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_10K_OTHER = patterns("00 K ¤", "-00 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_100K_ONE = patterns("000 K ¤", "-000 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_100K_OTHER = patterns("000 K ¤", "-000 K ¤");

  private final NumberPattern[] CURRENCY_SHORT_1M_ONE = patterns("0 M ¤", "-0 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_1M_OTHER = patterns("0 M ¤", "-0 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_10M_ONE = patterns("00 M ¤", "-00 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_10M_OTHER = patterns("00 M ¤", "-00 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_100M_ONE = patterns("000 M ¤", "-000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_100M_OTHER = patterns("000 M ¤", "-000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_1B_ONE = patterns("0000 M ¤", "-0000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_1B_OTHER = patterns("0000 M ¤", "-0000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_10B_ONE = patterns("00 MRD ¤", "-00 MRD ¤");

  private final NumberPattern[] CURRENCY_SHORT_10B_OTHER = patterns("00 MRD ¤", "-00 MRD ¤");

  private final NumberPattern[] CURRENCY_SHORT_100B_ONE = patterns("000 MRD ¤", "-000 MRD ¤");

  private final NumberPattern[] CURRENCY_SHORT_100B_OTHER = patterns("000 MRD ¤", "-000 MRD ¤");

  private final NumberPattern[] CURRENCY_SHORT_1T_ONE = patterns("0 B ¤", "-0 B ¤");

  private final NumberPattern[] CURRENCY_SHORT_1T_OTHER = patterns("0 B ¤", "-0 B ¤");

  private final NumberPattern[] CURRENCY_SHORT_10T_ONE = patterns("00 B ¤", "-00 B ¤");

  private final NumberPattern[] CURRENCY_SHORT_10T_OTHER = patterns("00 B ¤", "-00 B ¤");

  private final NumberPattern[] CURRENCY_SHORT_100T_ONE = patterns("000 B ¤", "-000 B ¤");

  private final NumberPattern[] CURRENCY_SHORT_100T_OTHER = patterns("000 B ¤", "-000 B ¤");

  private final int CURRENCY_SHORT_1K_POWER = 3;

  private final int CURRENCY_SHORT_10K_POWER = 3;

  private final int CURRENCY_SHORT_100K_POWER = 3;

  private final int CURRENCY_SHORT_1M_POWER = 6;

  private final int CURRENCY_SHORT_10M_POWER = 6;

  private final int CURRENCY_SHORT_100M_POWER = 6;

  private final int CURRENCY_SHORT_1B_POWER = 6;

  private final int CURRENCY_SHORT_10B_POWER = 9;

  private final int CURRENCY_SHORT_100B_POWER = 9;

  private final int CURRENCY_SHORT_1T_POWER = 12;

  private final int CURRENCY_SHORT_10T_POWER = 12;

  private final int CURRENCY_SHORT_100T_POWER = 12;

  public _NumberFormatter_es() {
    super(
        new CLDRLocale("es", "", "", ""),
        new _Params(),
        // decimal standard
        patterns("#,##0.###", "-#,##0.###"),
        // percent standard
        patterns("#,##0 %", "-#,##0 %"),
        // currency standard
        patterns("#,##0.00 ¤", "-#,##0.00 ¤"),
        // currency accounting
        patterns("#,##0.00 ¤", "-#,##0.00 ¤")
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
      case "UGS": return "UGS";
      case "FJD": return "FJD";
      case "MXN": return "MXN";
      case "STD": return "STD";
      case "BRR": return "BRR";
      case "LVL": return "LVL";
      case "SCR": return "SCR";
      case "CDF": return "CDF";
      case "MXP": return "MXP";
      case "ZAL": return "ZAL";
      case "BBD": return "BBD";
      case "HNL": return "HNL";
      case "UGX": return "UGX";
      case "LVR": return "LVR";
      case "MXV": return "MXV";
      case "ZAR": return "ZAR";
      case "BRZ": return "BRZ";
      case "CUC": return "CUC";
      case "BSD": return "BSD";
      case "SDD": return "SDD";
      case "SDG": return "SDG";
      case "ZRN": return "ZRN";
      case "IQD": return "IQD";
      case "SDP": return "SDP";
      case "CUP": return "CUP";
      case "GMD": return "GMD";
      case "TWD": return "TWD";
      case "RSD": return "RSD";
      case "ZRZ": return "ZRZ";
      case "UYI": return "UYI";
      case "MYR": return "MYR";
      case "FKP": return "FKP";
      case "UYP": return "UYP";
      case "XOF": return "XOF";
      case "ARA": return "ARA";
      case "UYU": return "UYU";
      case "SUR": return "SUR";
      case "CVE": return "CVE";
      case "OMR": return "OMR";
      case "KES": return "KES";
      case "SEK": return "SEK";
      case "MZE": return "MZE";
      case "ARL": return "ARL";
      case "ARM": return "ARM";
      case "BTN": return "BTN";
      case "GNF": return "GNF";
      case "ARP": return "ARP";
      case "MZN": return "MZN";
      case "MZM": return "MZM";
      case "SVC": return "SVC";
      case "ARS": return "ARS";
      case "QAR": return "QAR";
      case "IRR": return "IRR";
      case "NLG": return "NLG";
      case "GNS": return "GNS";
      case "XPD": return "XPD";
      case "THB": return "฿";
      case "UZS": return "UZS";
      case "XPF": return "CFPF";
      case "BDT": return "BDT";
      case "LYD": return "LYD";
      case "BUK": return "BUK";
      case "KWD": return "KWD";
      case "XPT": return "XPT";
      case "RUB": return "RUB";
      case "ISK": return "ISK";
      case "BEC": return "BEC";
      case "BEF": return "BEF";
      case "MKD": return "MKD";
      case "BEL": return "BEL";
      case "RUR": return "RUR";
      case "DZD": return "DZD";
      case "PAB": return "PAB";
      case "MKN": return "MKN";
      case "SGD": return "SGD";
      case "KGS": return "KGS";
      case "HRD": return "HRD";
      case "XAF": return "XAF";
      case "XAG": return "XAG";
      case "ATS": return "ATS";
      case "CHF": return "CHF";
      case "HRK": return "HRK";
      case "ITL": return "ITL";
      case "CHE": return "CHE";
      case "DJF": return "DJF";
      case "MLF": return "MLF";
      case "XRE": return "XRE";
      case "TZS": return "TZS";
      case "ADP": return "ADP";
      case "VND": return "₫";
      case "XAU": return "XAU";
      case "AUD": return "AUD";
      case "CHW": return "CHW";
      case "KHR": return "KHR";
      case "IDR": return "IDR";
      case "XBA": return "XBA";
      case "KYD": return "KYD";
      case "VNN": return "VNN";
      case "XBC": return "XBC";
      case "YDD": return "YDD";
      case "XBB": return "XBB";
      case "BWP": return "BWP";
      case "GQE": return "GQE";
      case "SHP": return "SHP";
      case "CYP": return "CYP";
      case "XBD": return "XBD";
      case "TJS": return "TJS";
      case "TJR": return "TJR";
      case "AED": return "AED";
      case "RWF": return "RWF";
      case "DKK": return "DKK";
      case "BGL": return "BGL";
      case "ZWD": return "ZWD";
      case "BGN": return "BGN";
      case "BGM": return "BGM";
      case "YUD": return "YUD";
      case "MMK": return "MMK";
      case "BGO": return "BGO";
      case "NOK": return "NOK";
      case "SYP": return "SYP";
      case "ZWL": return "ZWL";
      case "YUM": return "YUM";
      case "LKR": return "LKR";
      case "YUN": return "YUN";
      case "ZWR": return "ZWR";
      case "CZK": return "CZK";
      case "IEP": return "IEP";
      case "YUR": return "YUR";
      case "GRD": return "GRD";
      case "XCD": return "XCD";
      case "HTG": return "HTG";
      case "XSU": return "XSU";
      case "AFA": return "AFA";
      case "BHD": return "BHD";
      case "SIT": return "SIT";
      case "PTE": return "PTE";
      case "KZT": return "KZT";
      case "SZL": return "SZL";
      case "YER": return "YER";
      case "AFN": return "AFN";
      case "BYB": return "BYB";
      case "RHD": return "RHD";
      case "AWG": return "AWG";
      case "NPR": return "NPR";
      case "MNT": return "MNT";
      case "GBP": return "GBP";
      case "BYN": return "BYN";
      case "XTS": return "XTS";
      case "HUF": return "HUF";
      case "BYR": return "BYR";
      case "BIF": return "BIF";
      case "XUA": return "XUA";
      case "XDR": return "XDR";
      case "BZD": return "BZD";
      case "MOP": return "MOP";
      case "NAD": return "NAD";
      case "SKK": return "SKK";
      case "PEI": return "PEI";
      case "TMM": return "TMM";
      case "PEN": return "PEN";
      case "WST": return "WST";
      case "TMT": return "TMT";
      case "FRF": return "FRF";
      case "CLF": return "CLF";
      case "CLE": return "CLE";
      case "PES": return "PES";
      case "GTQ": return "GTQ";
      case "CLP": return "CLP";
      case "XEU": return "XEU";
      case "TND": return "TND";
      case "SLL": return "SLL";
      case "XFO": return "XFO";
      case "DOP": return "DOP";
      case "KMF": return "KMF";
      case "XFU": return "XFU";
      case "GEK": return "GEK";
      case "GEL": return "GEL";
      case "MAD": return "MAD";
      case "MAF": return "MAF";
      case "AZM": return "AZM";
      case "TOP": return "TOP";
      case "AZN": return "AZN";
      case "PGK": return "PGK";
      case "UAH": return "UAH";
      case "UAK": return "UAK";
      case "ERN": return "ERN";
      case "TPE": return "TPE";
      case "MRO": return "MRO";
      case "CNY": return "CNY";
      case "ESA": return "ESA";
      case "GWE": return "GWE";
      case "ESB": return "ESB";
      case "BMD": return "BMD";
      case "PHP": return "PHP";
      case "XXX": return "XXX";
      case "PYG": return "PYG";
      case "JMD": return "JMD";
      case "GWP": return "GWP";
      case "ESP": return "₧";
      case "COP": return "COP";
      case "USD": return "$";
      case "COU": return "COU";
      case "MCF": return "MCF";
      case "USN": return "USN";
      case "ETB": return "ETB";
      case "VEB": return "VEB";
      case "ECS": return "ECS";
      case "USS": return "USS";
      case "SOS": return "SOS";
      case "VEF": return "VEF";
      case "VUV": return "VUV";
      case "LAK": return "LAK";
      case "BND": return "BND";
      case "ECV": return "ECV";
      case "ZMK": return "ZMK";
      case "LRD": return "LRD";
      case "ALL": return "ALL";
      case "GHC": return "GHC";
      case "MTL": return "MTL";
      case "ZMW": return "ZMW";
      case "MTP": return "MTP";
      case "ILP": return "ILP";
      case "MDC": return "MDC";
      case "TRL": return "TRL";
      case "ILS": return "ILS";
      case "GHS": return "GHS";
      case "GYD": return "GYD";
      case "KPW": return "KPW";
      case "BOB": return "BOB";
      case "MDL": return "MDL";
      case "AMD": return "AMD";
      case "TRY": return "TRY";
      case "LBP": return "LBP";
      case "BOL": return "BOL";
      case "JOD": return "JOD";
      case "HKD": return "HKD";
      case "BOP": return "BOP";
      case "EUR": return "€";
      case "LSL": return "LSL";
      case "CAD": return "CA$";
      case "BOV": return "BOV";
      case "EEK": return "EEK";
      case "MUR": return "MUR";
      case "ROL": return "ROL";
      case "GIP": return "GIP";
      case "RON": return "RON";
      case "NGN": return "NGN";
      case "CRC": return "CRC";
      case "PKR": return "PKR";
      case "ANG": return "ANG";
      case "KRH": return "KRH";
      case "SRD": return "SRD";
      case "LTL": return "LTL";
      case "SAR": return "SAR";
      case "TTD": return "TTD";
      case "MVR": return "MVR";
      case "KRO": return "KRO";
      case "SRG": return "SRG";
      case "DDM": return "DDM";
      case "INR": return "INR";
      case "LTT": return "LTT";
      case "KRW": return "KRW";
      case "JPY": return "JPY";
      case "AOA": return "AOA";
      case "PLN": return "PLN";
      case "SBD": return "SBD";
      case "CSD": return "CSD";
      case "CSK": return "CSK";
      case "LUC": return "LUC";
      case "LUF": return "LUF";
      case "AOK": return "AOK";
      case "PLZ": return "PLZ";
      case "AON": return "AON";
      case "MWK": return "MWK";
      case "LUL": return "LUL";
      case "AOR": return "AOR";
      case "BAD": return "BAD";
      case "MGA": return "MGA";
      case "NIC": return "NIC";
      case "FIM": return "FIM";
      case "DEM": return "DEM";
      case "MGF": return "MGF";
      case "BAM": return "BAM";
      case "BAN": return "BAN";
      case "EGP": return "EGP";
      case "SSP": return "SSP";
      case "BRC": return "BRC";
      case "BRB": return "BRB";
      case "BRE": return "BRE";
      case "NIO": return "NIO";
      case "NZD": return "NZD";
      case "BRL": return "BRL";
      case "BRN": return "BRN";
      default: return "";
    }
  }

  public String getCurrencyDisplayName(String code) {
    switch (code) {
      case "UGS": return "chelín ugandés (1966–1987)";
      case "FJD": return "dólar fiyiano";
      case "MXN": return "peso mexicano";
      case "STD": return "dobra";
      case "BRR": return "cruceiro brasileño";
      case "LVL": return "lats letón";
      case "SCR": return "rupia seychellense";
      case "CDF": return "franco congoleño";
      case "MXP": return "peso de plata mexicano (1861–1992)";
      case "ZAL": return "rand sudafricano (financiero)";
      case "BBD": return "dólar barbadense";
      case "HNL": return "lempira hondureño";
      case "UGX": return "chelín ugandés";
      case "LVR": return "rublo letón";
      case "MXV": return "unidad de inversión (UDI) mexicana";
      case "ZAR": return "rand";
      case "BRZ": return "BRZ";
      case "CUC": return "peso cubano convertible";
      case "BSD": return "dólar bahameño";
      case "SDD": return "dinar sudanés";
      case "SDG": return "libra sudanesa";
      case "ZRN": return "nuevo zaire zaireño";
      case "IQD": return "dinar iraquí";
      case "SDP": return "libra sudanesa antigua";
      case "CUP": return "peso cubano";
      case "GMD": return "dalasi";
      case "TWD": return "nuevo dólar taiwanés";
      case "RSD": return "dinar serbio";
      case "ZRZ": return "zaire zaireño";
      case "UYI": return "peso uruguayo en unidades indexadas";
      case "MYR": return "ringit";
      case "FKP": return "libra malvinense";
      case "UYP": return "peso uruguayo (1975–1993)";
      case "XOF": return "franco CFA BCEAO";
      case "ARA": return "austral argentino";
      case "UYU": return "peso uruguayo";
      case "SUR": return "rublo soviético";
      case "CVE": return "escudo de Cabo Verde";
      case "OMR": return "rial omaní";
      case "KES": return "chelín keniano";
      case "SEK": return "corona sueca";
      case "MZE": return "escudo mozambiqueño";
      case "ARL": return "ARL";
      case "ARM": return "ARM";
      case "BTN": return "gultrum";
      case "GNF": return "franco guineano";
      case "ARP": return "peso argentino (1983–1985)";
      case "MZN": return "metical";
      case "MZM": return "antiguo metical mozambiqueño";
      case "SVC": return "colón salvadoreño";
      case "ARS": return "peso argentino";
      case "QAR": return "rial catarí";
      case "IRR": return "rial iraní";
      case "NLG": return "florín neerlandés";
      case "GNS": return "syli guineano";
      case "XPD": return "paladio";
      case "THB": return "bat";
      case "UZS": return "sum";
      case "XPF": return "franco CFP";
      case "BDT": return "taka";
      case "LYD": return "dinar libio";
      case "BUK": return "kyat birmano";
      case "KWD": return "dinar kuwaití";
      case "XPT": return "platino";
      case "RUB": return "rublo ruso";
      case "ISK": return "corona islandesa";
      case "BEC": return "franco belga (convertible)";
      case "BEF": return "franco belga";
      case "MKD": return "dinar macedonio";
      case "BEL": return "franco belga (financiero)";
      case "RUR": return "rublo ruso (1991–1998)";
      case "DZD": return "dinar argelino";
      case "PAB": return "balboa panameño";
      case "MKN": return "MKN";
      case "SGD": return "dólar singapurense";
      case "KGS": return "som";
      case "HRD": return "dinar croata";
      case "XAF": return "franco CFA BEAC";
      case "XAG": return "plata";
      case "ATS": return "chelín austriaco";
      case "CHF": return "franco suizo";
      case "HRK": return "kuna";
      case "ITL": return "lira italiana";
      case "CHE": return "euro WIR";
      case "DJF": return "franco yibutiano";
      case "MLF": return "franco malí";
      case "XRE": return "fondos RINET";
      case "TZS": return "chelín tanzano";
      case "ADP": return "peseta andorrana";
      case "VND": return "dong";
      case "XAU": return "oro";
      case "AUD": return "dólar australiano";
      case "CHW": return "franco WIR";
      case "KHR": return "riel";
      case "IDR": return "rupia indonesia";
      case "XBA": return "unidad compuesta europea";
      case "KYD": return "dólar de las Islas Caimán";
      case "VNN": return "VNN";
      case "XBC": return "unidad de cuenta europea (XBC)";
      case "YDD": return "dinar yemení";
      case "XBB": return "unidad monetaria europea";
      case "BWP": return "pula";
      case "GQE": return "ekuele de Guinea Ecuatorial";
      case "SHP": return "libra de Santa Elena";
      case "CYP": return "libra chipriota";
      case "XBD": return "unidad de cuenta europea (XBD)";
      case "TJS": return "somoni tayiko";
      case "TJR": return "rublo tayiko";
      case "AED": return "dírham de los Emiratos Árabes Unidos";
      case "RWF": return "franco ruandés";
      case "DKK": return "corona danesa";
      case "BGL": return "lev fuerte búlgaro";
      case "ZWD": return "dólar de Zimbabue";
      case "BGN": return "lev búlgaro";
      case "BGM": return "BGM";
      case "YUD": return "dinar fuerte yugoslavo";
      case "MMK": return "kiat";
      case "BGO": return "BGO";
      case "NOK": return "corona noruega";
      case "SYP": return "libra siria";
      case "ZWL": return "dólar zimbabuense";
      case "YUM": return "super dinar yugoslavo";
      case "LKR": return "rupia esrilanquesa";
      case "YUN": return "dinar convertible yugoslavo";
      case "ZWR": return "ZWR";
      case "CZK": return "corona checa";
      case "IEP": return "libra irlandesa";
      case "YUR": return "YUR";
      case "GRD": return "dracma griego";
      case "XCD": return "dólar del Caribe Oriental";
      case "HTG": return "gourde haitiano";
      case "XSU": return "XSU";
      case "AFA": return "afgani (1927–2002)";
      case "BHD": return "dinar bahreiní";
      case "SIT": return "tólar esloveno";
      case "PTE": return "escudo portugués";
      case "KZT": return "tenge kazako";
      case "SZL": return "lilangeni";
      case "YER": return "rial yemení";
      case "AFN": return "afgani";
      case "BYB": return "nuevo rublo bielorruso (1994–1999)";
      case "RHD": return "dólar rodesiano";
      case "AWG": return "florín arubeño";
      case "NPR": return "rupia nepalí";
      case "MNT": return "tugrik";
      case "GBP": return "libra británica";
      case "BYN": return "rublo bielorruso";
      case "XTS": return "código reservado para pruebas";
      case "HUF": return "forinto húngaro";
      case "BYR": return "rublo bielorruso (2000–2016)";
      case "BIF": return "franco burundés";
      case "XUA": return "XUA";
      case "XDR": return "derechos especiales de giro";
      case "BZD": return "dólar beliceño";
      case "MOP": return "pataca de Macao";
      case "NAD": return "dólar namibio";
      case "SKK": return "corona eslovaca";
      case "PEI": return "inti peruano";
      case "TMM": return "manat turcomano (1993–2009)";
      case "PEN": return "sol peruano";
      case "WST": return "tala";
      case "TMT": return "manat turcomano";
      case "FRF": return "franco francés";
      case "CLF": return "unidad de fomento chilena";
      case "CLE": return "CLE";
      case "PES": return "sol peruano (1863–1965)";
      case "GTQ": return "quetzal guatemalteco";
      case "CLP": return "peso chileno";
      case "XEU": return "unidad de moneda europea";
      case "TND": return "dinar tunecino";
      case "SLL": return "leona";
      case "XFO": return "franco oro francés";
      case "DOP": return "peso dominicano";
      case "KMF": return "franco comorense";
      case "XFU": return "franco UIC francés";
      case "GEK": return "kupon larit georgiano";
      case "GEL": return "lari";
      case "MAD": return "dírham marroquí";
      case "MAF": return "franco marroquí";
      case "AZM": return "manat azerí (1993–2006)";
      case "TOP": return "paanga";
      case "AZN": return "manat azerí";
      case "PGK": return "kina";
      case "UAH": return "grivna";
      case "UAK": return "karbovanet ucraniano";
      case "ERN": return "nakfa";
      case "TPE": return "escudo timorense";
      case "MRO": return "uguiya";
      case "CNY": return "yuan";
      case "ESA": return "peseta española (cuenta A)";
      case "GWE": return "escudo de Guinea Portuguesa";
      case "ESB": return "peseta española (cuenta convertible)";
      case "BMD": return "dólar de Bermudas";
      case "PHP": return "peso filipino";
      case "XXX": return "moneda desconocida";
      case "PYG": return "guaraní paraguayo";
      case "JMD": return "dólar jamaicano";
      case "GWP": return "peso de Guinea-Bissáu";
      case "ESP": return "peseta española";
      case "COP": return "peso colombiano";
      case "USD": return "dólar estadounidense";
      case "COU": return "unidad de valor real colombiana";
      case "MCF": return "MCF";
      case "USN": return "dólar estadounidense (día siguiente)";
      case "ETB": return "bir";
      case "VEB": return "bolívar venezolano (1871–2008)";
      case "ECS": return "sucre ecuatoriano";
      case "USS": return "dólar estadounidense (mismo día)";
      case "SOS": return "chelín somalí";
      case "VEF": return "bolívar venezolano";
      case "VUV": return "vatu";
      case "LAK": return "kip";
      case "BND": return "dólar bruneano";
      case "ECV": return "unidad de valor constante (UVC) ecuatoriana";
      case "ZMK": return "kwacha zambiano (1968–2012)";
      case "LRD": return "dólar liberiano";
      case "ALL": return "lek";
      case "GHC": return "cedi ghanés (1979–2007)";
      case "MTL": return "lira maltesa";
      case "ZMW": return "kuacha zambiano";
      case "MTP": return "libra maltesa";
      case "ILP": return "libra israelí";
      case "MDC": return "MDC";
      case "TRL": return "lira turca (1922–2005)";
      case "ILS": return "nuevo séquel israelí";
      case "GHS": return "cedi";
      case "GYD": return "dólar guyanés";
      case "KPW": return "won norcoreano";
      case "BOB": return "boliviano";
      case "MDL": return "leu moldavo";
      case "AMD": return "dram";
      case "TRY": return "lira turca";
      case "LBP": return "libra libanesa";
      case "BOL": return "BOL";
      case "JOD": return "dinar jordano";
      case "HKD": return "dólar hongkonés";
      case "BOP": return "peso boliviano";
      case "EUR": return "euro";
      case "LSL": return "loti lesothense";
      case "CAD": return "dólar canadiense";
      case "BOV": return "MVDOL boliviano";
      case "EEK": return "corona estonia";
      case "MUR": return "rupia mauriciana";
      case "ROL": return "antiguo leu rumano";
      case "GIP": return "libra gibraltareña";
      case "RON": return "leu rumano";
      case "NGN": return "naira";
      case "CRC": return "colón costarricense";
      case "PKR": return "rupia pakistaní";
      case "ANG": return "florín de las Antillas Neerlandesas";
      case "KRH": return "KRH";
      case "SRD": return "dólar surinamés";
      case "LTL": return "litas lituano";
      case "SAR": return "rial saudí";
      case "TTD": return "dólar de Trinidad y Tobago";
      case "MVR": return "rufiya";
      case "KRO": return "KRO";
      case "SRG": return "florín surinamés";
      case "DDM": return "ostmark de Alemania del Este";
      case "INR": return "rupia india";
      case "LTT": return "talonas lituano";
      case "KRW": return "won surcoreano";
      case "JPY": return "yen";
      case "AOA": return "kuanza";
      case "PLN": return "esloti";
      case "SBD": return "dólar salomonense";
      case "CSD": return "antiguo dinar serbio";
      case "CSK": return "corona fuerte checoslovaca";
      case "LUC": return "franco convertible luxemburgués";
      case "LUF": return "franco luxemburgués";
      case "AOK": return "kwanza angoleño (1977–1990)";
      case "PLZ": return "zloty polaco (1950–1995)";
      case "AON": return "nuevo kwanza angoleño (1990–2000)";
      case "MWK": return "kwacha malauí";
      case "LUL": return "franco financiero luxemburgués";
      case "AOR": return "kwanza reajustado angoleño (1995–1999)";
      case "BAD": return "dinar bosnio";
      case "MGA": return "ariari";
      case "NIC": return "córdoba nicaragüense (1988–1991)";
      case "FIM": return "marco finlandés";
      case "DEM": return "marco alemán";
      case "MGF": return "franco malgache";
      case "BAM": return "marco convertible de Bosnia-Herzegovina";
      case "BAN": return "BAN";
      case "EGP": return "libra egipcia";
      case "SSP": return "libra sursudanesa";
      case "BRC": return "cruzado brasileño";
      case "BRB": return "nuevo cruceiro brasileño (1967–1986)";
      case "BRE": return "cruceiro brasileño (1990–1993)";
      case "NIO": return "córdoba nicaragüense";
      case "NZD": return "dólar neozelandés";
      case "BRL": return "real brasileño";
      case "BRN": return "nuevo cruzado brasileño";
      default: return "";
    }
  }

  protected String getCurrencyPluralName(String code, PluralCategory category) {
    switch (code) {
      case "ADP": {
        switch (category) {
          case ONE: return "peseta andorrana";
          case OTHER:
              default: return "pesetas andorranas";
        }
      }
      case "AED": {
        switch (category) {
          case ONE: return "dírham de los Emiratos Árabes Unidos";
          case OTHER:
              default: return "dírhams de los Emiratos Árabes Unidos";
        }
      }
      case "AFN": {
        switch (category) {
          case ONE: return "afgani";
          case OTHER:
              default: return "afganis";
        }
      }
      case "ALL": {
        switch (category) {
          case ONE: return "lek";
          case OTHER:
              default: return "lekes";
        }
      }
      case "AMD": {
        switch (category) {
          case ONE: return "dram";
          case OTHER:
              default: return "drams";
        }
      }
      case "ANG": {
        switch (category) {
          case ONE: return "florín de las Antillas Neerlandesas";
          case OTHER:
              default: return "florines de las Antillas Neerlandesas";
        }
      }
      case "AOA": {
        switch (category) {
          case ONE: return "kuanza";
          case OTHER:
              default: return "kuanzas";
        }
      }
      case "ARA": {
        switch (category) {
          case ONE: return "austral argentino";
          case OTHER:
              default: return "australes argentinos";
        }
      }
      case "ARP": {
        switch (category) {
          case ONE: return "peso argentino (ARP)";
          case OTHER:
              default: return "pesos argentinos (ARP)";
        }
      }
      case "ARS": {
        switch (category) {
          case ONE: return "peso argentino";
          case OTHER:
              default: return "pesos argentinos";
        }
      }
      case "ATS": {
        switch (category) {
          case ONE: return "chelín austriaco";
          case OTHER:
              default: return "chelines austriacos";
        }
      }
      case "AUD": {
        switch (category) {
          case ONE: return "dólar australiano";
          case OTHER:
              default: return "dólares australianos";
        }
      }
      case "AWG": {
        switch (category) {
          case ONE: return "florín arubeño";
          case OTHER:
              default: return "florines arubeños";
        }
      }
      case "AZN": {
        switch (category) {
          case ONE: return "manat azerí";
          case OTHER:
              default: return "manat azeríes";
        }
      }
      case "BAD": {
        switch (category) {
          case ONE: return "dinar bosnio";
          case OTHER:
              default: return "dinares bosnios";
        }
      }
      case "BAM": {
        switch (category) {
          case ONE: return "marco convertible de Bosnia-Herzegovina";
          case OTHER:
              default: return "marcos convertibles de Bosnia-Herzegovina";
        }
      }
      case "BBD": {
        switch (category) {
          case ONE: return "dólar barbadense";
          case OTHER:
              default: return "dólares barbadenses";
        }
      }
      case "BDT": {
        switch (category) {
          case ONE: return "taka";
          case OTHER:
              default: return "takas";
        }
      }
      case "BEC": {
        switch (category) {
          case ONE: return "franco belga (convertible)";
          case OTHER:
              default: return "francos belgas (convertibles)";
        }
      }
      case "BEF": {
        switch (category) {
          case ONE: return "franco belga";
          case OTHER:
              default: return "francos belgas";
        }
      }
      case "BEL": {
        switch (category) {
          case ONE: return "franco belga (financiero)";
          case OTHER:
              default: return "francos belgas (financieros)";
        }
      }
      case "BGL": {
        switch (category) {
          case ONE: return "lev fuerte búlgaro";
          case OTHER:
              default: return "leva fuertes búlgaros";
        }
      }
      case "BGN": {
        switch (category) {
          case ONE: return "lev búlgaro";
          case OTHER:
              default: return "levas búlgaras";
        }
      }
      case "BHD": {
        switch (category) {
          case ONE: return "dinar bahreiní";
          case OTHER:
              default: return "dinares bahreiníes";
        }
      }
      case "BIF": {
        switch (category) {
          case ONE: return "franco burundés";
          case OTHER:
              default: return "francos burundeses";
        }
      }
      case "BMD": {
        switch (category) {
          case ONE: return "dólar de Bermudas";
          case OTHER:
              default: return "dólares de Bermudas";
        }
      }
      case "BND": {
        switch (category) {
          case ONE: return "dólar bruneano";
          case OTHER:
              default: return "dólares bruneanos";
        }
      }
      case "BOB": {
        switch (category) {
          case ONE: return "boliviano";
          case OTHER:
              default: return "bolivianos";
        }
      }
      case "BOP": {
        switch (category) {
          case ONE: return "peso boliviano";
          case OTHER:
              default: return "pesos bolivianos";
        }
      }
      case "BOV": {
        switch (category) {
          case ONE: return "MVDOL boliviano";
          case OTHER:
              default: return "MVDOL bolivianos";
        }
      }
      case "BRB": {
        switch (category) {
          case ONE: return "nuevo cruzado brasileño (BRB)";
          case OTHER:
              default: return "nuevos cruzados brasileños (BRB)";
        }
      }
      case "BRC": {
        switch (category) {
          case ONE: return "cruzado brasileño";
          case OTHER:
              default: return "cruzados brasileños";
        }
      }
      case "BRE": {
        switch (category) {
          case ONE: return "cruceiro brasileño (BRE)";
          case OTHER:
              default: return "cruceiros brasileños (BRE)";
        }
      }
      case "BRL": {
        switch (category) {
          case ONE: return "real brasileño";
          case OTHER:
              default: return "reales brasileños";
        }
      }
      case "BRN": {
        switch (category) {
          case ONE: return "nuevo cruzado brasileño";
          case OTHER:
              default: return "nuevos cruzados brasileños";
        }
      }
      case "BRR": {
        switch (category) {
          case ONE: return "cruceiro brasileño";
          case OTHER:
              default: return "cruceiros brasileños";
        }
      }
      case "BSD": {
        switch (category) {
          case ONE: return "dólar bahameño";
          case OTHER:
              default: return "dólares bahameños";
        }
      }
      case "BTN": {
        switch (category) {
          case ONE: return "gultrum";
          case OTHER:
              default: return "gultrums";
        }
      }
      case "BUK": {
        switch (category) {
          case ONE: return "kyat birmano";
          case OTHER:
              default: return "kyat birmanos";
        }
      }
      case "BWP": {
        switch (category) {
          case ONE: return "pula";
          case OTHER:
              default: return "pulas";
        }
      }
      case "BYB": {
        switch (category) {
          case ONE: return "nuevo rublo bielorruso";
          case OTHER:
              default: return "nuevos rublos bielorrusos";
        }
      }
      case "BYN": {
        switch (category) {
          case ONE: return "rublo bielorruso";
          case OTHER:
              default: return "rublos bielorrusos";
        }
      }
      case "BYR": {
        switch (category) {
          case ONE: return "rublo bielorruso (2000–2016)";
          case OTHER:
              default: return "rublos bielorrusos (2000–2016)";
        }
      }
      case "BZD": {
        switch (category) {
          case ONE: return "dólar beliceño";
          case OTHER:
              default: return "dólares beliceños";
        }
      }
      case "CAD": {
        switch (category) {
          case ONE: return "dólar canadiense";
          case OTHER:
              default: return "dólares canadienses";
        }
      }
      case "CDF": {
        switch (category) {
          case ONE: return "franco congoleño";
          case OTHER:
              default: return "francos congoleños";
        }
      }
      case "CHE": {
        switch (category) {
          case ONE: return "euro WIR";
          case OTHER:
              default: return "euros WIR";
        }
      }
      case "CHF": {
        switch (category) {
          case ONE: return "franco suizo";
          case OTHER:
              default: return "francos suizos";
        }
      }
      case "CHW": {
        switch (category) {
          case ONE: return "franco WIR";
          case OTHER:
              default: return "francos WIR";
        }
      }
      case "CLF": {
        switch (category) {
          case ONE: return "unidad de fomento chilena";
          case OTHER:
              default: return "unidades de fomento chilenas";
        }
      }
      case "CLP": {
        switch (category) {
          case ONE: return "peso chileno";
          case OTHER:
              default: return "pesos chilenos";
        }
      }
      case "CNY": {
        switch (category) {
          case ONE: return "yuan";
          case OTHER:
              default: return "yuanes";
        }
      }
      case "COP": {
        switch (category) {
          case ONE: return "peso colombiano";
          case OTHER:
              default: return "pesos colombianos";
        }
      }
      case "COU": {
        switch (category) {
          case ONE: return "unidad de valor real";
          case OTHER:
              default: return "unidades de valor reales";
        }
      }
      case "CRC": {
        switch (category) {
          case ONE: return "colón costarricense";
          case OTHER:
              default: return "colones costarricenses";
        }
      }
      case "CSD": {
        switch (category) {
          case ONE: return "antiguo dinar serbio";
          case OTHER:
              default: return "antiguos dinares serbios";
        }
      }
      case "CSK": {
        switch (category) {
          case ONE: return "corona fuerte checoslovaca";
          case OTHER:
              default: return "coronas fuertes checoslovacas";
        }
      }
      case "CUC": {
        switch (category) {
          case ONE: return "peso cubano convertible";
          case OTHER:
              default: return "pesos cubanos convertibles";
        }
      }
      case "CUP": {
        switch (category) {
          case ONE: return "peso cubano";
          case OTHER:
              default: return "pesos cubanos";
        }
      }
      case "CVE": {
        switch (category) {
          case ONE: return "escudo de Cabo Verde";
          case OTHER:
              default: return "escudos de Cabo Verde";
        }
      }
      case "CYP": {
        switch (category) {
          case ONE: return "libra chipriota";
          case OTHER:
              default: return "libras chipriotas";
        }
      }
      case "CZK": {
        switch (category) {
          case ONE: return "corona checa";
          case OTHER:
              default: return "coronas checas";
        }
      }
      case "DDM": {
        switch (category) {
          case ONE: return "marco de la República Democrática Alemana";
          case OTHER:
              default: return "marcos de la República Democrática Alemana";
        }
      }
      case "DEM": {
        switch (category) {
          case ONE: return "marco alemán";
          case OTHER:
              default: return "marcos alemanes";
        }
      }
      case "DJF": {
        switch (category) {
          case ONE: return "franco yibutiano";
          case OTHER:
              default: return "francos yibutianos";
        }
      }
      case "DKK": {
        switch (category) {
          case ONE: return "corona danesa";
          case OTHER:
              default: return "coronas danesas";
        }
      }
      case "DOP": {
        switch (category) {
          case ONE: return "peso dominicano";
          case OTHER:
              default: return "pesos dominicanos";
        }
      }
      case "DZD": {
        switch (category) {
          case ONE: return "dinar argelino";
          case OTHER:
              default: return "dinares argelinos";
        }
      }
      case "ECS": {
        switch (category) {
          case ONE: return "sucre ecuatoriano";
          case OTHER:
              default: return "sucres ecuatorianos";
        }
      }
      case "ECV": {
        switch (category) {
          case ONE: return "unidad de valor constante (UVC) ecuatoriana";
          case OTHER:
              default: return "unidades de valor constante (UVC) ecuatorianas";
        }
      }
      case "EEK": {
        switch (category) {
          case ONE: return "corona estonia";
          case OTHER:
              default: return "coronas estonias";
        }
      }
      case "EGP": {
        switch (category) {
          case ONE: return "libra egipcia";
          case OTHER:
              default: return "libras egipcias";
        }
      }
      case "ERN": {
        switch (category) {
          case ONE: return "nakfa";
          case OTHER:
              default: return "nakfas";
        }
      }
      case "ESA": {
        switch (category) {
          case ONE: return "peseta española (cuenta A)";
          case OTHER:
              default: return "pesetas españolas (cuenta A)";
        }
      }
      case "ESB": {
        switch (category) {
          case ONE: return "peseta española (cuenta convertible)";
          case OTHER:
              default: return "pesetas españolas (cuenta convertible)";
        }
      }
      case "ESP": {
        switch (category) {
          case ONE: return "peseta española";
          case OTHER:
              default: return "pesetas españolas";
        }
      }
      case "ETB": {
        switch (category) {
          case ONE: return "bir";
          case OTHER:
              default: return "bires";
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
          case ONE: return "marco finlandés";
          case OTHER:
              default: return "marcos finlandeses";
        }
      }
      case "FJD": {
        switch (category) {
          case ONE: return "dólar fiyiano";
          case OTHER:
              default: return "dólares fiyianos";
        }
      }
      case "FKP": {
        switch (category) {
          case ONE: return "libra malvinense";
          case OTHER:
              default: return "libras malvinenses";
        }
      }
      case "FRF": {
        switch (category) {
          case ONE: return "franco francés";
          case OTHER:
              default: return "francos franceses";
        }
      }
      case "GBP": {
        switch (category) {
          case ONE: return "libra británica";
          case OTHER:
              default: return "libras británicas";
        }
      }
      case "GEL": {
        switch (category) {
          case ONE: return "lari";
          case OTHER:
              default: return "laris";
        }
      }
      case "GHS": {
        switch (category) {
          case ONE: return "cedi";
          case OTHER:
              default: return "cedis";
        }
      }
      case "GIP": {
        switch (category) {
          case ONE: return "libra gibraltareña";
          case OTHER:
              default: return "libras gibraltareñas";
        }
      }
      case "GMD": {
        switch (category) {
          case ONE: return "dalasi";
          case OTHER:
              default: return "dalasis";
        }
      }
      case "GNF": {
        switch (category) {
          case ONE: return "franco guineano";
          case OTHER:
              default: return "francos guineanos";
        }
      }
      case "GQE": {
        switch (category) {
          case ONE: return "ekuele de Guinea Ecuatorial";
          case OTHER:
              default: return "ekueles de Guinea Ecuatorial";
        }
      }
      case "GRD": {
        switch (category) {
          case ONE: return "dracma griego";
          case OTHER:
              default: return "dracmas griegos";
        }
      }
      case "GTQ": {
        switch (category) {
          case ONE: return "quetzal guatemalteco";
          case OTHER:
              default: return "quetzales guatemaltecos";
        }
      }
      case "GYD": {
        switch (category) {
          case ONE: return "dólar guyanés";
          case OTHER:
              default: return "dólares guyaneses";
        }
      }
      case "HKD": {
        switch (category) {
          case ONE: return "dólar hongkonés";
          case OTHER:
              default: return "dólares hongkoneses";
        }
      }
      case "HNL": {
        switch (category) {
          case ONE: return "lempira hondureño";
          case OTHER:
              default: return "lempiras hondureños";
        }
      }
      case "HRD": {
        switch (category) {
          case ONE: return "dinar croata";
          case OTHER:
              default: return "dinares croatas";
        }
      }
      case "HRK": {
        switch (category) {
          case ONE: return "kuna";
          case OTHER:
              default: return "kunas";
        }
      }
      case "HTG": {
        switch (category) {
          case ONE: return "gourde haitiano";
          case OTHER:
              default: return "gourdes haitianos";
        }
      }
      case "HUF": {
        switch (category) {
          case ONE: return "forinto húngaro";
          case OTHER:
              default: return "forintos húngaros";
        }
      }
      case "IDR": {
        switch (category) {
          case ONE: return "rupia indonesia";
          case OTHER:
              default: return "rupias indonesias";
        }
      }
      case "IEP": {
        switch (category) {
          case ONE: return "libra irlandesa";
          case OTHER:
              default: return "libras irlandesas";
        }
      }
      case "ILP": {
        switch (category) {
          case ONE: return "libra israelí";
          case OTHER:
              default: return "libras israelíes";
        }
      }
      case "ILS": {
        switch (category) {
          case ONE: return "nuevo séquel israelí";
          case OTHER:
              default: return "nuevos séqueles israelíes";
        }
      }
      case "INR": {
        switch (category) {
          case ONE: return "rupia india";
          case OTHER:
              default: return "rupias indias";
        }
      }
      case "IQD": {
        switch (category) {
          case ONE: return "dinar iraquí";
          case OTHER:
              default: return "dinares iraquíes";
        }
      }
      case "IRR": {
        switch (category) {
          case ONE: return "rial iraní";
          case OTHER:
              default: return "riales iraníes";
        }
      }
      case "ISK": {
        switch (category) {
          case ONE: return "corona islandesa";
          case OTHER:
              default: return "coronas islandesas";
        }
      }
      case "ITL": {
        switch (category) {
          case ONE: return "lira italiana";
          case OTHER:
              default: return "liras italianas";
        }
      }
      case "JMD": {
        switch (category) {
          case ONE: return "dólar jamaicano";
          case OTHER:
              default: return "dólares jamaicanos";
        }
      }
      case "JOD": {
        switch (category) {
          case ONE: return "dinar jordano";
          case OTHER:
              default: return "dinares jordanos";
        }
      }
      case "JPY": {
        switch (category) {
          case ONE: return "yen";
          case OTHER:
              default: return "yenes";
        }
      }
      case "KES": {
        switch (category) {
          case ONE: return "chelín keniano";
          case OTHER:
              default: return "chelines kenianos";
        }
      }
      case "KGS": {
        switch (category) {
          case ONE: return "som";
          case OTHER:
              default: return "soms";
        }
      }
      case "KHR": {
        switch (category) {
          case ONE: return "riel";
          case OTHER:
              default: return "rieles";
        }
      }
      case "KMF": {
        switch (category) {
          case ONE: return "franco comorense";
          case OTHER:
              default: return "francos comorenses";
        }
      }
      case "KPW": {
        switch (category) {
          case ONE: return "won norcoreano";
          case OTHER:
              default: return "wons norcoreanos";
        }
      }
      case "KRW": {
        switch (category) {
          case ONE: return "won surcoreano";
          case OTHER:
              default: return "wons surcoreanos";
        }
      }
      case "KWD": {
        switch (category) {
          case ONE: return "dinar kuwaití";
          case OTHER:
              default: return "dinares kuwaitíes";
        }
      }
      case "KYD": {
        switch (category) {
          case ONE: return "dólar de las Islas Caimán";
          case OTHER:
              default: return "dólares de las Islas Caimán";
        }
      }
      case "KZT": {
        switch (category) {
          case ONE: return "tenge kazako";
          case OTHER:
              default: return "tenges kazakos";
        }
      }
      case "LAK": {
        switch (category) {
          case ONE: return "kip";
          case OTHER:
              default: return "kips";
        }
      }
      case "LBP": {
        switch (category) {
          case ONE: return "libra libanesa";
          case OTHER:
              default: return "libras libanesas";
        }
      }
      case "LKR": {
        switch (category) {
          case ONE: return "rupia esrilanquesa";
          case OTHER:
              default: return "rupias esrilanquesas";
        }
      }
      case "LRD": {
        switch (category) {
          case ONE: return "dólar liberiano";
          case OTHER:
              default: return "dólares liberianos";
        }
      }
      case "LTL": {
        switch (category) {
          case ONE: return "litas lituana";
          case OTHER:
              default: return "litas lituanas";
        }
      }
      case "LTT": {
        switch (category) {
          case ONE: return "talonas lituana";
          case OTHER:
              default: return "talonas lituanas";
        }
      }
      case "LUC": {
        switch (category) {
          case ONE: return "franco convertible luxemburgués";
          case OTHER:
              default: return "francos convertibles luxemburgueses";
        }
      }
      case "LUF": {
        switch (category) {
          case ONE: return "franco luxemburgués";
          case OTHER:
              default: return "francos luxemburgueses";
        }
      }
      case "LUL": {
        switch (category) {
          case ONE: return "franco financiero luxemburgués";
          case OTHER:
              default: return "francos financieros luxemburgueses";
        }
      }
      case "LVL": {
        switch (category) {
          case ONE: return "lats letón";
          case OTHER:
              default: return "lats letónes";
        }
      }
      case "LVR": {
        switch (category) {
          case ONE: return "rublo letón";
          case OTHER:
              default: return "rublos letones";
        }
      }
      case "LYD": {
        switch (category) {
          case ONE: return "dinar libio";
          case OTHER:
              default: return "dinares libios";
        }
      }
      case "MAD": {
        switch (category) {
          case ONE: return "dírham marroquí";
          case OTHER:
              default: return "dírhams marroquíes";
        }
      }
      case "MAF": {
        switch (category) {
          case ONE: return "franco marroquí";
          case OTHER:
              default: return "francos marroquíes";
        }
      }
      case "MDL": {
        switch (category) {
          case ONE: return "leu moldavo";
          case OTHER:
              default: return "leus moldavos";
        }
      }
      case "MGA": {
        switch (category) {
          case ONE: return "ariari";
          case OTHER:
              default: return "ariaris";
        }
      }
      case "MKD": {
        switch (category) {
          case ONE: return "dinar macedonio";
          case OTHER:
              default: return "dinares macedonios";
        }
      }
      case "MMK": {
        switch (category) {
          case ONE: return "kiat";
          case OTHER:
              default: return "kiats";
        }
      }
      case "MNT": {
        switch (category) {
          case ONE: return "tugrik";
          case OTHER:
              default: return "tugriks";
        }
      }
      case "MOP": {
        switch (category) {
          case ONE: return "pataca de Macao";
          case OTHER:
              default: return "patacas de Macao";
        }
      }
      case "MRO": {
        switch (category) {
          case ONE: return "uguiya";
          case OTHER:
              default: return "uguiyas";
        }
      }
      case "MTL": {
        switch (category) {
          case ONE: return "lira maltesa";
          case OTHER:
              default: return "liras maltesas";
        }
      }
      case "MTP": {
        switch (category) {
          case ONE: return "libra maltesa";
          case OTHER:
              default: return "libras maltesas";
        }
      }
      case "MUR": {
        switch (category) {
          case ONE: return "rupia mauriciana";
          case OTHER:
              default: return "rupias mauricianas";
        }
      }
      case "MVR": {
        switch (category) {
          case ONE: return "rufiya";
          case OTHER:
              default: return "rufiyas";
        }
      }
      case "MWK": {
        switch (category) {
          case ONE: return "kwacha malauí";
          case OTHER:
              default: return "kwachas malauís";
        }
      }
      case "MXN": {
        switch (category) {
          case ONE: return "peso mexicano";
          case OTHER:
              default: return "pesos mexicanos";
        }
      }
      case "MXP": {
        switch (category) {
          case ONE: return "peso de plata mexicano (MXP)";
          case OTHER:
              default: return "pesos de plata mexicanos (MXP)";
        }
      }
      case "MXV": {
        switch (category) {
          case ONE: return "unidad de inversión (UDI) mexicana";
          case OTHER:
              default: return "unidades de inversión (UDI) mexicanas";
        }
      }
      case "MYR": {
        switch (category) {
          case ONE: return "ringit";
          case OTHER:
              default: return "ringits";
        }
      }
      case "MZE": {
        switch (category) {
          case ONE: return "escudo mozambiqueño";
          case OTHER:
              default: return "escudos mozambiqueños";
        }
      }
      case "MZN": {
        switch (category) {
          case ONE: return "metical";
          case OTHER:
              default: return "meticales";
        }
      }
      case "NAD": {
        switch (category) {
          case ONE: return "dólar namibio";
          case OTHER:
              default: return "dólares namibios";
        }
      }
      case "NGN": {
        switch (category) {
          case ONE: return "naira";
          case OTHER:
              default: return "nairas";
        }
      }
      case "NIC": {
        switch (category) {
          case ONE: return "córdoba nicaragüense (1988–1991)";
          case OTHER:
              default: return "córdobas nicaragüenses (1988–1991)";
        }
      }
      case "NIO": {
        switch (category) {
          case ONE: return "córdoba nicaragüense";
          case OTHER:
              default: return "córdobas nicaragüenses";
        }
      }
      case "NLG": {
        switch (category) {
          case ONE: return "florín neerlandés";
          case OTHER:
              default: return "florines neerlandeses";
        }
      }
      case "NOK": {
        switch (category) {
          case ONE: return "corona noruega";
          case OTHER:
              default: return "coronas noruegas";
        }
      }
      case "NPR": {
        switch (category) {
          case ONE: return "rupia nepalí";
          case OTHER:
              default: return "rupias nepalíes";
        }
      }
      case "NZD": {
        switch (category) {
          case ONE: return "dólar neozelandés";
          case OTHER:
              default: return "dólares neozelandeses";
        }
      }
      case "OMR": {
        switch (category) {
          case ONE: return "rial omaní";
          case OTHER:
              default: return "riales omaníes";
        }
      }
      case "PAB": {
        switch (category) {
          case ONE: return "balboa panameño";
          case OTHER:
              default: return "balboas panameños";
        }
      }
      case "PEI": {
        switch (category) {
          case ONE: return "inti peruano";
          case OTHER:
              default: return "intis peruanos";
        }
      }
      case "PEN": {
        switch (category) {
          case ONE: return "sol peruano";
          case OTHER:
              default: return "soles peruanos";
        }
      }
      case "PES": {
        switch (category) {
          case ONE: return "sol peruano (1863–1965)";
          case OTHER:
              default: return "soles peruanos (1863–1965)";
        }
      }
      case "PGK": {
        switch (category) {
          case ONE: return "kina";
          case OTHER:
              default: return "kinas";
        }
      }
      case "PHP": {
        switch (category) {
          case ONE: return "peso filipino";
          case OTHER:
              default: return "pesos filipinos";
        }
      }
      case "PKR": {
        switch (category) {
          case ONE: return "rupia pakistaní";
          case OTHER:
              default: return "rupias pakistaníes";
        }
      }
      case "PLN": {
        switch (category) {
          case ONE: return "esloti";
          case OTHER:
              default: return "eslotis";
        }
      }
      case "PLZ": {
        switch (category) {
          case ONE: return "zloty polaco (PLZ)";
          case OTHER:
              default: return "zlotys polacos (PLZ)";
        }
      }
      case "PTE": {
        switch (category) {
          case ONE: return "escudo portugués";
          case OTHER:
              default: return "escudos portugueses";
        }
      }
      case "PYG": {
        switch (category) {
          case ONE: return "guaraní paraguayo";
          case OTHER:
              default: return "guaraníes paraguayos";
        }
      }
      case "QAR": {
        switch (category) {
          case ONE: return "rial catarí";
          case OTHER:
              default: return "riales cataríes";
        }
      }
      case "ROL": {
        switch (category) {
          case ONE: return "antiguo leu rumano";
          case OTHER:
              default: return "antiguos lei rumanos";
        }
      }
      case "RON": {
        switch (category) {
          case ONE: return "leu rumano";
          case OTHER:
              default: return "leus rumanos";
        }
      }
      case "RSD": {
        switch (category) {
          case ONE: return "dinar serbio";
          case OTHER:
              default: return "dinares serbios";
        }
      }
      case "RUB": {
        switch (category) {
          case ONE: return "rublo ruso";
          case OTHER:
              default: return "rublos rusos";
        }
      }
      case "RUR": {
        switch (category) {
          case ONE: return "rublo ruso (RUR)";
          case OTHER:
              default: return "rublos rusos (RUR)";
        }
      }
      case "RWF": {
        switch (category) {
          case ONE: return "franco ruandés";
          case OTHER:
              default: return "francos ruandeses";
        }
      }
      case "SAR": {
        switch (category) {
          case ONE: return "rial saudí";
          case OTHER:
              default: return "riales saudíes";
        }
      }
      case "SBD": {
        switch (category) {
          case ONE: return "dólar salomonense";
          case OTHER:
              default: return "dólares salomonenses";
        }
      }
      case "SCR": {
        switch (category) {
          case ONE: return "rupia seychellense";
          case OTHER:
              default: return "rupias seychellenses";
        }
      }
      case "SDD": {
        switch (category) {
          case ONE: return "dinar sudanés";
          case OTHER:
              default: return "dinares sudaneses";
        }
      }
      case "SDG": {
        switch (category) {
          case ONE: return "libra sudanesa";
          case OTHER:
              default: return "libras sudanesas";
        }
      }
      case "SDP": {
        switch (category) {
          case ONE: return "libra sudanesa antigua";
          case OTHER:
              default: return "libras sudanesas antiguas";
        }
      }
      case "SEK": {
        switch (category) {
          case ONE: return "corona sueca";
          case OTHER:
              default: return "coronas suecas";
        }
      }
      case "SGD": {
        switch (category) {
          case ONE: return "dólar singapurense";
          case OTHER:
              default: return "dólares singapurenses";
        }
      }
      case "SHP": {
        switch (category) {
          case ONE: return "libra de Santa Elena";
          case OTHER:
              default: return "libras de Santa Elena";
        }
      }
      case "SIT": {
        switch (category) {
          case ONE: return "tólar esloveno";
          case OTHER:
              default: return "tólares eslovenos";
        }
      }
      case "SKK": {
        switch (category) {
          case ONE: return "corona eslovaca";
          case OTHER:
              default: return "coronas eslovacas";
        }
      }
      case "SLL": {
        switch (category) {
          case ONE: return "leona";
          case OTHER:
              default: return "leonas";
        }
      }
      case "SOS": {
        switch (category) {
          case ONE: return "chelín somalí";
          case OTHER:
              default: return "chelines somalíes";
        }
      }
      case "SRD": {
        switch (category) {
          case ONE: return "dólar surinamés";
          case OTHER:
              default: return "dólares surinameses";
        }
      }
      case "SSP": {
        switch (category) {
          case ONE: return "libra sursudanesa";
          case OTHER:
              default: return "libras sursudanesas";
        }
      }
      case "STD": {
        switch (category) {
          case ONE: return "dobra";
          case OTHER:
              default: return "dobras";
        }
      }
      case "SUR": {
        switch (category) {
          case ONE: return "rublo soviético";
          case OTHER:
              default: return "rublos soviéticos";
        }
      }
      case "SVC": {
        switch (category) {
          case ONE: return "colón salvadoreño";
          case OTHER:
              default: return "colones salvadoreños";
        }
      }
      case "SYP": {
        switch (category) {
          case ONE: return "libra siria";
          case OTHER:
              default: return "libras sirias";
        }
      }
      case "SZL": {
        switch (category) {
          case ONE: return "lilangeni";
          case OTHER:
              default: return "lilangenis";
        }
      }
      case "THB": {
        switch (category) {
          case ONE: return "bat";
          case OTHER:
              default: return "bats";
        }
      }
      case "TJS": {
        switch (category) {
          case ONE: return "somoni tayiko";
          case OTHER:
              default: return "somonis tayikos";
        }
      }
      case "TMM": {
        switch (category) {
          case ONE: return "manat turcomano (1993–2009)";
          case OTHER:
              default: return "manats turcomanos (1993–2009)";
        }
      }
      case "TMT": {
        switch (category) {
          case ONE: return "manat turcomano";
          case OTHER:
              default: return "manats turcomanos";
        }
      }
      case "TND": {
        switch (category) {
          case ONE: return "dinar tunecino";
          case OTHER:
              default: return "dinares tunecinos";
        }
      }
      case "TOP": {
        switch (category) {
          case ONE: return "paanga";
          case OTHER:
              default: return "paangas";
        }
      }
      case "TRL": {
        switch (category) {
          case ONE: return "lira turca (1922–2005)";
          case OTHER:
              default: return "liras turcas (1922–2005)";
        }
      }
      case "TRY": {
        switch (category) {
          case ONE: return "lira turca";
          case OTHER:
              default: return "liras turcas";
        }
      }
      case "TTD": {
        switch (category) {
          case ONE: return "dólar de Trinidad y Tobago";
          case OTHER:
              default: return "dólares de Trinidad y Tobago";
        }
      }
      case "TWD": {
        switch (category) {
          case ONE: return "nuevo dólar taiwanés";
          case OTHER:
              default: return "nuevos dólares taiwaneses";
        }
      }
      case "TZS": {
        switch (category) {
          case ONE: return "chelín tanzano";
          case OTHER:
              default: return "chelines tanzanos";
        }
      }
      case "UAH": {
        switch (category) {
          case ONE: return "grivna";
          case OTHER:
              default: return "grivnas";
        }
      }
      case "UAK": {
        switch (category) {
          case ONE: return "karbovanet ucraniano";
          case OTHER:
              default: return "karbovanets ucranianos";
        }
      }
      case "UGX": {
        switch (category) {
          case ONE: return "chelín ugandés";
          case OTHER:
              default: return "chelines ugandeses";
        }
      }
      case "USD": {
        switch (category) {
          case ONE: return "dólar estadounidense";
          case OTHER:
              default: return "dólares estadounidenses";
        }
      }
      case "USN": {
        switch (category) {
          case ONE: return "dólar estadounidense (día siguiente)";
          case OTHER:
              default: return "dólares estadounidenses (día siguiente)";
        }
      }
      case "USS": {
        switch (category) {
          case ONE: return "dólar estadounidense (mismo día)";
          case OTHER:
              default: return "dólares estadounidenses (mismo día)";
        }
      }
      case "UYI": {
        switch (category) {
          case ONE: return "peso uruguayo en unidades indexadas";
          case OTHER:
              default: return "pesos uruguayos en unidades indexadas";
        }
      }
      case "UYP": {
        switch (category) {
          case ONE: return "peso uruguayo (UYP)";
          case OTHER:
              default: return "pesos uruguayos (UYP)";
        }
      }
      case "UYU": {
        switch (category) {
          case ONE: return "peso uruguayo";
          case OTHER:
              default: return "pesos uruguayos";
        }
      }
      case "UZS": {
        switch (category) {
          case ONE: return "sum";
          case OTHER:
              default: return "sums";
        }
      }
      case "VEB": {
        switch (category) {
          case ONE: return "bolívar venezolano (1871–2008)";
          case OTHER:
              default: return "bolívares venezolanos (1871–2008)";
        }
      }
      case "VEF": {
        switch (category) {
          case ONE: return "bolívar venezolano";
          case OTHER:
              default: return "bolívares venezolanos";
        }
      }
      case "VND": {
        switch (category) {
          case ONE: return "dong";
          case OTHER:
              default: return "dongs";
        }
      }
      case "VUV": {
        switch (category) {
          case ONE: return "vatu";
          case OTHER:
              default: return "vatus";
        }
      }
      case "WST": {
        switch (category) {
          case ONE: return "tala";
          case OTHER:
              default: return "talas";
        }
      }
      case "XAF": {
        switch (category) {
          case ONE: return "franco CFA BEAC";
          case OTHER:
              default: return "francos CFA BEAC";
        }
      }
      case "XAG": {
        switch (category) {
          case ONE: return "plata";
          case OTHER:
              default: return "plata";
        }
      }
      case "XAU": {
        switch (category) {
          case ONE: return "oro";
          case OTHER:
              default: return "oro";
        }
      }
      case "XBA": {
        switch (category) {
          case ONE: return "unidad compuesta europea";
          case OTHER:
              default: return "unidades compuestas europeas";
        }
      }
      case "XBB": {
        switch (category) {
          case ONE: return "unidad monetaria europea";
          case OTHER:
              default: return "unidades monetarias europeas";
        }
      }
      case "XBC": {
        switch (category) {
          case ONE: return "unidad de cuenta europea (XBC)";
          case OTHER:
              default: return "unidades de cuenta europeas (XBC)";
        }
      }
      case "XBD": {
        switch (category) {
          case ONE: return "unidad de cuenta europea (XBD)";
          case OTHER:
              default: return "unidades de cuenta europeas (XBD)";
        }
      }
      case "XCD": {
        switch (category) {
          case ONE: return "dólar del Caribe Oriental";
          case OTHER:
              default: return "dólares del Caribe Oriental";
        }
      }
      case "XEU": {
        switch (category) {
          case ONE: return "unidad de moneda europea";
          case OTHER:
              default: return "unidades de moneda europeas";
        }
      }
      case "XFO": {
        switch (category) {
          case ONE: return "franco oro francés";
          case OTHER:
              default: return "francos oro franceses";
        }
      }
      case "XFU": {
        switch (category) {
          case ONE: return "franco UIC francés";
          case OTHER:
              default: return "francos UIC franceses";
        }
      }
      case "XOF": {
        switch (category) {
          case ONE: return "franco CFA BCEAO";
          case OTHER:
              default: return "francos CFA BCEAO";
        }
      }
      case "XPD": {
        switch (category) {
          case ONE: return "paladio";
          case OTHER:
              default: return "paladio";
        }
      }
      case "XPF": {
        switch (category) {
          case ONE: return "franco CFP";
          case OTHER:
              default: return "francos CFP";
        }
      }
      case "XPT": {
        switch (category) {
          case ONE: return "platino";
          case OTHER:
              default: return "platino";
        }
      }
      case "XXX": {
        switch (category) {
          case ONE: return "(moneda desconocida)";
          case OTHER:
              default: return "(moneda desconocida)";
        }
      }
      case "YER": {
        switch (category) {
          case ONE: return "rial yemení";
          case OTHER:
              default: return "riales yemeníes";
        }
      }
      case "YUN": {
        switch (category) {
          case ONE: return "dinar convertible yugoslavo";
          case OTHER:
              default: return "dinares convertibles yugoslavos";
        }
      }
      case "ZAR": {
        switch (category) {
          case ONE: return "rand";
          case OTHER:
              default: return "rands";
        }
      }
      case "ZMK": {
        switch (category) {
          case ONE: return "kwacha zambiano (1968–2012)";
          case OTHER:
              default: return "kwachas zambianos (1968–2012)";
        }
      }
      case "ZMW": {
        switch (category) {
          case ONE: return "kuacha zambiano";
          case OTHER:
              default: return "kuachas zambianos";
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
      this.decimal = ",";
      this.group = ".";
      this.percent = "%";
      this.minus = "-";
      this.plus = "+";
      this.exponential = "E";
      this.superscriptingExponent = "×";
      this.perMille = "‰";
      this.infinity = "∞";
      this.nan = "NaN";
      this.currencyDecimal = ",";
      this.currencyGroup = ".";
    }
  }
}
