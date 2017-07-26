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

public class _NumberFormatter_fr extends NumberFormatterBase {
  public static final NumberPattern[] DECIMAL_STANDARD = patterns("#,##0.###", "-#,##0.###");

  public static final NumberPattern[] PERCENT_STANDARD = patterns("#,##0 %", "-#,##0 %");

  public static final NumberPattern[] CURRENCY_STANDARD = patterns("#,##0.00 ¤", "-#,##0.00 ¤");

  public static final NumberPattern[] CURRENCY_ACCOUNTING = patterns("#,##0.00 ¤", "(#,##0.00 ¤)");

  public static final NumberPattern[] DECIMAL_STANDARD_COMPACT = patterns("#,##0", "-#,##0");

  public static final NumberPattern[] CURRENCY_STANDARD_COMPACT = patterns("#,##0 ¤", "-#,##0 ¤");

  private final NumberPattern[] DECIMAL_SHORT_1K_ONE = patterns("0 k", "-0 k");

  private final NumberPattern[] DECIMAL_SHORT_1K_OTHER = patterns("0 k", "-0 k");

  private final NumberPattern[] DECIMAL_SHORT_10K_ONE = patterns("00 k", "-00 k");

  private final NumberPattern[] DECIMAL_SHORT_10K_OTHER = patterns("00 k", "-00 k");

  private final NumberPattern[] DECIMAL_SHORT_100K_ONE = patterns("000 k", "-000 k");

  private final NumberPattern[] DECIMAL_SHORT_100K_OTHER = patterns("000 k", "-000 k");

  private final NumberPattern[] DECIMAL_SHORT_1M_ONE = patterns("0 M", "-0 M");

  private final NumberPattern[] DECIMAL_SHORT_1M_OTHER = patterns("0 M", "-0 M");

  private final NumberPattern[] DECIMAL_SHORT_10M_ONE = patterns("00 M", "-00 M");

  private final NumberPattern[] DECIMAL_SHORT_10M_OTHER = patterns("00 M", "-00 M");

  private final NumberPattern[] DECIMAL_SHORT_100M_ONE = patterns("000 M", "-000 M");

  private final NumberPattern[] DECIMAL_SHORT_100M_OTHER = patterns("000 M", "-000 M");

  private final NumberPattern[] DECIMAL_SHORT_1B_ONE = patterns("0 Md", "-0 Md");

  private final NumberPattern[] DECIMAL_SHORT_1B_OTHER = patterns("0 Md", "-0 Md");

  private final NumberPattern[] DECIMAL_SHORT_10B_ONE = patterns("00 Md", "-00 Md");

  private final NumberPattern[] DECIMAL_SHORT_10B_OTHER = patterns("00 Md", "-00 Md");

  private final NumberPattern[] DECIMAL_SHORT_100B_ONE = patterns("000 Md", "-000 Md");

  private final NumberPattern[] DECIMAL_SHORT_100B_OTHER = patterns("000 Md", "-000 Md");

  private final NumberPattern[] DECIMAL_SHORT_1T_ONE = patterns("0 Bn", "-0 Bn");

  private final NumberPattern[] DECIMAL_SHORT_1T_OTHER = patterns("0 Bn", "-0 Bn");

  private final NumberPattern[] DECIMAL_SHORT_10T_ONE = patterns("00 Bn", "-00 Bn");

  private final NumberPattern[] DECIMAL_SHORT_10T_OTHER = patterns("00 Bn", "-00 Bn");

  private final NumberPattern[] DECIMAL_SHORT_100T_ONE = patterns("000 Bn", "-000 Bn");

  private final NumberPattern[] DECIMAL_SHORT_100T_OTHER = patterns("000 Bn", "-000 Bn");

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

  private final NumberPattern[] DECIMAL_LONG_1K_ONE = patterns("0 millier", "-0 millier");

  private final NumberPattern[] DECIMAL_LONG_1K_OTHER = patterns("0 mille", "-0 mille");

  private final NumberPattern[] DECIMAL_LONG_10K_ONE = patterns("00 mille", "-00 mille");

  private final NumberPattern[] DECIMAL_LONG_10K_OTHER = patterns("00 mille", "-00 mille");

  private final NumberPattern[] DECIMAL_LONG_100K_ONE = patterns("000 mille", "-000 mille");

  private final NumberPattern[] DECIMAL_LONG_100K_OTHER = patterns("000 mille", "-000 mille");

  private final NumberPattern[] DECIMAL_LONG_1M_ONE = patterns("0 million", "-0 million");

  private final NumberPattern[] DECIMAL_LONG_1M_OTHER = patterns("0 millions", "-0 millions");

  private final NumberPattern[] DECIMAL_LONG_10M_ONE = patterns("00 million", "-00 million");

  private final NumberPattern[] DECIMAL_LONG_10M_OTHER = patterns("00 millions", "-00 millions");

  private final NumberPattern[] DECIMAL_LONG_100M_ONE = patterns("000 million", "-000 million");

  private final NumberPattern[] DECIMAL_LONG_100M_OTHER = patterns("000 millions", "-000 millions");

  private final NumberPattern[] DECIMAL_LONG_1B_ONE = patterns("0 milliard", "-0 milliard");

  private final NumberPattern[] DECIMAL_LONG_1B_OTHER = patterns("0 milliards", "-0 milliards");

  private final NumberPattern[] DECIMAL_LONG_10B_ONE = patterns("00 milliard", "-00 milliard");

  private final NumberPattern[] DECIMAL_LONG_10B_OTHER = patterns("00 milliards", "-00 milliards");

  private final NumberPattern[] DECIMAL_LONG_100B_ONE = patterns("000 milliards", "-000 milliards");

  private final NumberPattern[] DECIMAL_LONG_100B_OTHER = patterns("000 milliards", "-000 milliards");

  private final NumberPattern[] DECIMAL_LONG_1T_ONE = patterns("0 billion", "-0 billion");

  private final NumberPattern[] DECIMAL_LONG_1T_OTHER = patterns("0 billions", "-0 billions");

  private final NumberPattern[] DECIMAL_LONG_10T_ONE = patterns("00 billions", "-00 billions");

  private final NumberPattern[] DECIMAL_LONG_10T_OTHER = patterns("00 billions", "-00 billions");

  private final NumberPattern[] DECIMAL_LONG_100T_ONE = patterns("000 billions", "-000 billions");

  private final NumberPattern[] DECIMAL_LONG_100T_OTHER = patterns("000 billions", "-000 billions");

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

  private final NumberPattern[] CURRENCY_SHORT_1K_ONE = patterns("0 k ¤", "-0 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_1K_OTHER = patterns("0 k ¤", "-0 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_10K_ONE = patterns("00 k ¤", "-00 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_10K_OTHER = patterns("00 k ¤", "-00 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_100K_ONE = patterns("000 k ¤", "-000 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_100K_OTHER = patterns("000 k ¤", "-000 k ¤");

  private final NumberPattern[] CURRENCY_SHORT_1M_ONE = patterns("0 M ¤", "-0 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_1M_OTHER = patterns("0 M ¤", "-0 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_10M_ONE = patterns("00 M ¤", "-00 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_10M_OTHER = patterns("00 M ¤", "-00 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_100M_ONE = patterns("000 M ¤", "-000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_100M_OTHER = patterns("000 M ¤", "-000 M ¤");

  private final NumberPattern[] CURRENCY_SHORT_1B_ONE = patterns("0 Md ¤", "-0 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_1B_OTHER = patterns("0 Md ¤", "-0 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_10B_ONE = patterns("00 Md ¤", "-00 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_10B_OTHER = patterns("00 Md ¤", "-00 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_100B_ONE = patterns("000 Md ¤", "-000 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_100B_OTHER = patterns("000 Md ¤", "-000 Md ¤");

  private final NumberPattern[] CURRENCY_SHORT_1T_ONE = patterns("0 Bn ¤", "-0 Bn ¤");

  private final NumberPattern[] CURRENCY_SHORT_1T_OTHER = patterns("0 Bn ¤", "-0 Bn ¤");

  private final NumberPattern[] CURRENCY_SHORT_10T_ONE = patterns("00 Bn ¤", "-00 Bn ¤");

  private final NumberPattern[] CURRENCY_SHORT_10T_OTHER = patterns("00 Bn ¤", "-00 Bn ¤");

  private final NumberPattern[] CURRENCY_SHORT_100T_ONE = patterns("000 Bn ¤", "-000 Bn ¤");

  private final NumberPattern[] CURRENCY_SHORT_100T_OTHER = patterns("000 Bn ¤", "-000 Bn ¤");

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

  public _NumberFormatter_fr() {
    super(
        new CLDRLocale("fr", "", "", ""),
        new _Params(),
        // decimal standard
        patterns("#,##0.###", "-#,##0.###"),
        // percent standard
        patterns("#,##0 %", "-#,##0 %"),
        // currency standard
        patterns("#,##0.00 ¤", "-#,##0.00 ¤"),
        // currency accounting
        patterns("#,##0.00 ¤", "(#,##0.00 ¤)")
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
      case "FJD": return "$FJ";
      case "MXN": return "$MX";
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
      case "BSD": return "$BS";
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
      case "FKP": return "£FK";
      case "UYP": return "UYP";
      case "XOF": return "CFA";
      case "ARA": return "ARA";
      case "UYU": return "$UY";
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
      case "ARS": return "$AR";
      case "QAR": return "QAR";
      case "IRR": return "IRR";
      case "NLG": return "NLG";
      case "GNS": return "GNS";
      case "XPD": return "XPD";
      case "THB": return "THB";
      case "UZS": return "UZS";
      case "XPF": return "FCFP";
      case "BDT": return "BDT";
      case "LYD": return "LYD";
      case "BUK": return "BUK";
      case "KWD": return "KWD";
      case "XPT": return "XPT";
      case "RUB": return "RUB";
      case "ISK": return "ISK";
      case "BEC": return "BEC";
      case "BEF": return "FB";
      case "MKD": return "MKD";
      case "BEL": return "BEL";
      case "RUR": return "RUR";
      case "DZD": return "DZD";
      case "PAB": return "PAB";
      case "MKN": return "MKN";
      case "SGD": return "$SG";
      case "KGS": return "KGS";
      case "HRD": return "HRD";
      case "XAF": return "FCFA";
      case "XAG": return "XAG";
      case "ATS": return "ATS";
      case "CHF": return "CHF";
      case "HRK": return "HRK";
      case "ITL": return "₤IT";
      case "CHE": return "CHE";
      case "DJF": return "DJF";
      case "MLF": return "MLF";
      case "XRE": return "XRE";
      case "TZS": return "TZS";
      case "ADP": return "ADP";
      case "VND": return "₫";
      case "XAU": return "XAU";
      case "AUD": return "$AU";
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
      case "CYP": return "£CY";
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
      case "IEP": return "£IE";
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
      case "RHD": return "$RH";
      case "AWG": return "AWG";
      case "NPR": return "NPR";
      case "MNT": return "MNT";
      case "GBP": return "£GB";
      case "BYN": return "BYN";
      case "XTS": return "XTS";
      case "HUF": return "HUF";
      case "BYR": return "BYR";
      case "BIF": return "BIF";
      case "XUA": return "XUA";
      case "XDR": return "XDR";
      case "BZD": return "$BZ";
      case "MOP": return "MOP";
      case "NAD": return "$NA";
      case "SKK": return "SKK";
      case "PEI": return "PEI";
      case "TMM": return "TMM";
      case "PEN": return "PEN";
      case "WST": return "WS$";
      case "TMT": return "TMT";
      case "FRF": return "F";
      case "CLF": return "CLF";
      case "CLE": return "CLE";
      case "PES": return "PES";
      case "GTQ": return "GTQ";
      case "CLP": return "$CL";
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
      case "BMD": return "$BM";
      case "PHP": return "PHP";
      case "XXX": return "XXX";
      case "PYG": return "PYG";
      case "JMD": return "JMD";
      case "GWP": return "GWP";
      case "ESP": return "ESP";
      case "COP": return "$CO";
      case "USD": return "$US";
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
      case "BND": return "$BN";
      case "ECV": return "ECV";
      case "ZMK": return "ZMK";
      case "LRD": return "LRD";
      case "ALK": return "";
      case "ALL": return "ALL";
      case "GHC": return "GHC";
      case "MTL": return "MTL";
      case "ZMW": return "ZMW";
      case "MTP": return "£MT";
      case "ILP": return "£IL";
      case "MDC": return "MDC";
      case "TRL": return "TRL";
      case "ILS": return "₪";
      case "GHS": return "GHS";
      case "GYD": return "GYD";
      case "KPW": return "KPW";
      case "BOB": return "BOB";
      case "MDL": return "MDL";
      case "AMD": return "AMD";
      case "TRY": return "TRY";
      case "LBP": return "£LB";
      case "BOL": return "BOL";
      case "JOD": return "JOD";
      case "HKD": return "HKD";
      case "BOP": return "BOP";
      case "EUR": return "€";
      case "LSL": return "LSL";
      case "CAD": return "$CA";
      case "BOV": return "BOV";
      case "EEK": return "EEK";
      case "MUR": return "MUR";
      case "ROL": return "ROL";
      case "GIP": return "£GI";
      case "RON": return "RON";
      case "NGN": return "NGN";
      case "CRC": return "CRC";
      case "PKR": return "PKR";
      case "ANG": return "ANG";
      case "KRH": return "KRH";
      case "SRD": return "$SR";
      case "LTL": return "LTL";
      case "SAR": return "SAR";
      case "TTD": return "$TT";
      case "MVR": return "MVR";
      case "KRO": return "KRO";
      case "SRG": return "SRG";
      case "DDM": return "DDM";
      case "INR": return "₹";
      case "LTT": return "LTT";
      case "KRW": return "₩";
      case "JPY": return "JPY";
      case "AOA": return "AOA";
      case "PLN": return "PLN";
      case "SBD": return "$SB";
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
      case "NZD": return "$NZ";
      case "BRL": return "R$";
      case "BRN": return "BRN";
      default: return "";
    }
  }

  public String getCurrencyDisplayName(String code) {
    switch (code) {
      case "UGS": return "shilling ougandais (1966–1987)";
      case "FJD": return "dollar fidjien";
      case "MXN": return "peso mexicain";
      case "STD": return "dobra santoméen";
      case "BRR": return "cruzeiro";
      case "LVL": return "lats letton";
      case "SCR": return "roupie des Seychelles";
      case "CDF": return "franc congolais";
      case "MXP": return "peso d’argent mexicain (1861–1992)";
      case "ZAL": return "rand sud-africain (financier)";
      case "BBD": return "dollar barbadien";
      case "HNL": return "lempira hondurien";
      case "UGX": return "shilling ougandais";
      case "LVR": return "rouble letton";
      case "MXV": return "unité de conversion mexicaine (UDI)";
      case "ZAR": return "rand sud-africain";
      case "BRZ": return "BRZ";
      case "CUC": return "peso cubain convertible";
      case "BSD": return "dollar bahaméen";
      case "SDD": return "dinar soudanais";
      case "SDG": return "livre soudanaise";
      case "ZRN": return "nouveau zaïre zaïrien";
      case "IQD": return "dinar irakien";
      case "SDP": return "livre soudanaise (1956–2007)";
      case "CUP": return "peso cubain";
      case "GMD": return "dalasi gambien";
      case "TWD": return "nouveau dollar taïwanais";
      case "RSD": return "dinar serbe";
      case "ZRZ": return "zaïre zaïrois";
      case "UYI": return "peso uruguayen (unités indexées)";
      case "MYR": return "ringgit malais";
      case "FKP": return "livre des îles Malouines";
      case "UYP": return "peso uruguayen (1975–1993)";
      case "XOF": return "franc CFA (BCEAO)";
      case "ARA": return "austral argentin";
      case "UYU": return "peso uruguayen";
      case "SUR": return "rouble soviétique";
      case "CVE": return "escudo capverdien";
      case "OMR": return "rial omanais";
      case "KES": return "shilling kényan";
      case "SEK": return "couronne suédoise";
      case "MZE": return "escudo mozambicain";
      case "ARL": return "ARL";
      case "ARM": return "ARM";
      case "BTN": return "ngultrum bouthanais";
      case "GNF": return "franc guinéen";
      case "ARP": return "peso argentin (1983–1985)";
      case "MZN": return "metical mozambicain";
      case "MZM": return "métical";
      case "SVC": return "colón salvadorien";
      case "ARS": return "peso argentin";
      case "QAR": return "rial qatari";
      case "IRR": return "rial iranien";
      case "NLG": return "florin néerlandais";
      case "GNS": return "syli guinéen";
      case "XPD": return "palladium";
      case "THB": return "baht thaïlandais";
      case "UZS": return "sum ouzbek";
      case "XPF": return "franc CFP";
      case "BDT": return "taka bangladeshi";
      case "LYD": return "dinar libyen";
      case "BUK": return "kyat birman";
      case "KWD": return "dinar koweïtien";
      case "XPT": return "platine";
      case "RUB": return "rouble russe";
      case "ISK": return "couronne islandaise";
      case "BEC": return "franc belge (convertible)";
      case "BEF": return "franc belge";
      case "MKD": return "denar macédonien";
      case "BEL": return "franc belge (financier)";
      case "RUR": return "rouble russe (1991–1998)";
      case "DZD": return "dinar algérien";
      case "PAB": return "balboa panaméen";
      case "MKN": return "MKN";
      case "SGD": return "dollar de Singapour";
      case "KGS": return "som kirghize";
      case "HRD": return "dinar croate";
      case "XAF": return "franc CFA (BEAC)";
      case "XAG": return "argent";
      case "ATS": return "schilling autrichien";
      case "CHF": return "franc suisse";
      case "HRK": return "kuna croate";
      case "ITL": return "lire italienne";
      case "CHE": return "euro WIR";
      case "DJF": return "franc djiboutien";
      case "MLF": return "franc malien";
      case "XRE": return "type de fonds RINET";
      case "TZS": return "shilling tanzanien";
      case "ADP": return "peseta andorrane";
      case "VND": return "dông vietnamien";
      case "XAU": return "or";
      case "AUD": return "dollar australien";
      case "CHW": return "franc WIR";
      case "KHR": return "riel cambodgien";
      case "IDR": return "roupie indonésienne";
      case "XBA": return "unité européenne composée";
      case "KYD": return "dollar des îles Caïmans";
      case "VNN": return "VNN";
      case "XBC": return "unité de compte européenne (XBC)";
      case "YDD": return "dinar du Yémen";
      case "XBB": return "unité monétaire européenne";
      case "BWP": return "pula botswanais";
      case "GQE": return "ekwélé équatoguinéen";
      case "SHP": return "livre de Sainte-Hélène";
      case "CYP": return "livre chypriote";
      case "XBD": return "unité de compte européenne (XBD)";
      case "TJS": return "somoni tadjik";
      case "TJR": return "rouble tadjik";
      case "AED": return "dirham des Émirats arabes unis";
      case "RWF": return "franc rwandais";
      case "DKK": return "couronne danoise";
      case "BGL": return "lev bulgare (1962–1999)";
      case "ZWD": return "dollar zimbabwéen";
      case "BGN": return "lev bulgare";
      case "BGM": return "BGM";
      case "YUD": return "nouveau dinar yougoslave";
      case "MMK": return "kyat myanmarais";
      case "BGO": return "BGO";
      case "NOK": return "couronne norvégienne";
      case "SYP": return "livre syrienne";
      case "ZWL": return "dollar zimbabwéen (2009)";
      case "YUM": return "dinar yougoslave Noviy";
      case "LKR": return "roupie srilankaise";
      case "YUN": return "dinar yougoslave convertible";
      case "ZWR": return "dollar zimbabwéen (2008)";
      case "CZK": return "couronne tchèque";
      case "IEP": return "livre irlandaise";
      case "YUR": return "YUR";
      case "GRD": return "drachme grecque";
      case "XCD": return "dollar des Caraïbes orientales";
      case "HTG": return "gourde haïtienne";
      case "XSU": return "XSU";
      case "AFA": return "afghani (1927–2002)";
      case "BHD": return "dinar bahreïni";
      case "SIT": return "tolar slovène";
      case "PTE": return "escudo portugais";
      case "KZT": return "tenge kazakh";
      case "SZL": return "lilangeni swazi";
      case "YER": return "rial yéménite";
      case "AFN": return "afghani afghan";
      case "BYB": return "nouveau rouble biélorusse (1994–1999)";
      case "RHD": return "dollar rhodésien";
      case "AWG": return "florin arubais";
      case "NPR": return "roupie népalaise";
      case "MNT": return "tugrik mongol";
      case "GBP": return "livre sterling";
      case "BYN": return "rouble biélorusse";
      case "XTS": return "(devise de test)";
      case "HUF": return "forint hongrois";
      case "BYR": return "rouble biélorusse (2000–2016)";
      case "BIF": return "franc burundais";
      case "XUA": return "XUA";
      case "XDR": return "droit de tirage spécial";
      case "BZD": return "dollar bélizéen";
      case "MOP": return "pataca macanaise";
      case "NAD": return "dollar namibien";
      case "SKK": return "couronne slovaque";
      case "PEI": return "inti péruvien";
      case "TMM": return "manat turkmène";
      case "PEN": return "sol péruvien";
      case "WST": return "tala samoan";
      case "TMT": return "nouveau manat turkmène";
      case "FRF": return "franc français";
      case "CLF": return "unité d’investissement chilienne";
      case "CLE": return "CLE";
      case "PES": return "sol péruvien (1863–1985)";
      case "GTQ": return "quetzal guatémaltèque";
      case "CLP": return "peso chilien";
      case "XEU": return "unité de compte européenne (ECU)";
      case "TND": return "dinar tunisien";
      case "SLL": return "leone sierra-léonais";
      case "XFO": return "franc or";
      case "DOP": return "peso dominicain";
      case "KMF": return "franc comorien";
      case "XFU": return "franc UIC";
      case "GEK": return "coupon de lari géorgien";
      case "GEL": return "lari géorgien";
      case "MAD": return "dirham marocain";
      case "MAF": return "franc marocain";
      case "AZM": return "manat azéri (1993–2006)";
      case "TOP": return "pa’anga tongan";
      case "AZN": return "manat azéri";
      case "PGK": return "kina papouan-néo-guinéen";
      case "UAH": return "hryvnia ukrainienne";
      case "UAK": return "karbovanetz";
      case "ERN": return "nafka érythréen";
      case "TPE": return "escudo timorais";
      case "MRO": return "ouguiya mauritanien";
      case "CNY": return "yuan renminbi chinois";
      case "ESA": return "peseta espagnole (compte A)";
      case "GWE": return "escudo de Guinée portugaise";
      case "ESB": return "peseta espagnole (compte convertible)";
      case "BMD": return "dollar bermudien";
      case "PHP": return "peso philippin";
      case "XXX": return "devise inconnue ou non valide";
      case "PYG": return "guaraní paraguayen";
      case "JMD": return "dollar jamaïcain";
      case "GWP": return "peso bissau-guinéen";
      case "ESP": return "peseta espagnole";
      case "COP": return "peso colombien";
      case "USD": return "dollar des États-Unis";
      case "COU": return "unité de valeur réelle colombienne";
      case "MCF": return "MCF";
      case "USN": return "dollar des Etats-Unis (jour suivant)";
      case "ETB": return "birr éthiopien";
      case "VEB": return "bolivar vénézuélien (1871–2008)";
      case "ECS": return "sucre équatorien";
      case "USS": return "dollar des Etats-Unis (jour même)";
      case "SOS": return "shilling somalien";
      case "VEF": return "bolivar vénézuélien";
      case "VUV": return "vatu vanuatuan";
      case "LAK": return "kip loatien";
      case "BND": return "dollar brunéien";
      case "ECV": return "unité de valeur constante équatoriale (UVC)";
      case "ZMK": return "kwacha zambien (1968–2012)";
      case "LRD": return "dollar libérien";
      case "ALK": return "lek albanais (1947–1961)";
      case "ALL": return "lek albanais";
      case "GHC": return "cédi";
      case "MTL": return "lire maltaise";
      case "ZMW": return "kwacha zambien";
      case "MTP": return "livre maltaise";
      case "ILP": return "livre israélienne";
      case "MDC": return "MDC";
      case "TRL": return "livre turque (1844–2005)";
      case "ILS": return "nouveau shekel israélien";
      case "GHS": return "cédi ghanéen";
      case "GYD": return "dollar du Guyana";
      case "KPW": return "won nord-coréen";
      case "BOB": return "boliviano bolivien";
      case "MDL": return "leu moldave";
      case "AMD": return "dram arménien";
      case "TRY": return "livre turque";
      case "LBP": return "livre libanaise";
      case "BOL": return "BOL";
      case "JOD": return "dinar jordanien";
      case "HKD": return "dollar de Hong Kong";
      case "BOP": return "peso bolivien";
      case "EUR": return "euro";
      case "LSL": return "loti lesothan";
      case "CAD": return "dollar canadien";
      case "BOV": return "mvdol bolivien";
      case "EEK": return "couronne estonienne";
      case "MUR": return "roupie mauricienne";
      case "ROL": return "ancien leu roumain";
      case "GIP": return "livre de Gibraltar";
      case "RON": return "leu roumain";
      case "NGN": return "naira nigérian";
      case "CRC": return "colón costaricain";
      case "PKR": return "roupie pakistanaise";
      case "ANG": return "florin antillais";
      case "KRH": return "KRH";
      case "SRD": return "dollar surinamais";
      case "LTL": return "litas lituanien";
      case "SAR": return "rial saoudien";
      case "TTD": return "dollar trinidadien";
      case "MVR": return "rufiyaa maldivien";
      case "KRO": return "KRO";
      case "SRG": return "florin surinamais";
      case "DDM": return "mark est-allemand";
      case "INR": return "roupie indienne";
      case "LTT": return "talonas lituanien";
      case "KRW": return "won sud-coréen";
      case "JPY": return "yen japonais";
      case "AOA": return "kwanza angolais";
      case "PLN": return "zloty polonais";
      case "SBD": return "dollar des îles Salomon";
      case "CSD": return "dinar serbo-monténégrin";
      case "CSK": return "couronne forte tchécoslovaque";
      case "LUC": return "franc convertible luxembourgeois";
      case "LUF": return "franc luxembourgeois";
      case "AOK": return "kwanza angolais (1977–1990)";
      case "PLZ": return "zloty (1950–1995)";
      case "AON": return "nouveau kwanza angolais (1990–2000)";
      case "MWK": return "kwacha malawite";
      case "LUL": return "franc financier luxembourgeois";
      case "AOR": return "kwanza angolais réajusté (1995–1999)";
      case "BAD": return "dinar bosniaque";
      case "MGA": return "ariary malgache";
      case "NIC": return "cordoba";
      case "FIM": return "mark finlandais";
      case "DEM": return "mark allemand";
      case "MGF": return "franc malgache";
      case "BAM": return "mark convertible bosniaque";
      case "BAN": return "BAN";
      case "EGP": return "livre égyptienne";
      case "SSP": return "livre sud-soudanaise";
      case "BRC": return "cruzado brésilien (1986–1989)";
      case "BRB": return "nouveau cruzeiro brésilien (1967–1986)";
      case "BRE": return "cruzeiro brésilien (1990–1993)";
      case "NIO": return "córdoba oro nicaraguayen";
      case "NZD": return "dollar néo-zélandais";
      case "BRL": return "réal brésilien";
      case "BRN": return "nouveau cruzado";
      default: return "";
    }
  }

  protected String getCurrencyPluralName(String code, PluralCategory category) {
    switch (code) {
      case "ADP": {
        switch (category) {
          case ONE: return "peseta andorrane";
          case OTHER:
              default: return "pesetas andorranes";
        }
      }
      case "AED": {
        switch (category) {
          case ONE: return "dirham des Émirats arabes unis";
          case OTHER:
              default: return "dirhams des Émirats arabes unis";
        }
      }
      case "AFA": {
        switch (category) {
          case ONE: return "afghani (1927–2002)";
          case OTHER:
              default: return "afghanis (1927–2002)";
        }
      }
      case "AFN": {
        switch (category) {
          case ONE: return "afghani afghan";
          case OTHER:
              default: return "afghanis afghan";
        }
      }
      case "ALK": {
        switch (category) {
          case ONE: return "lek albanais (1947–1961)";
          case OTHER:
              default: return "leks albanais (1947–1961)";
        }
      }
      case "ALL": {
        switch (category) {
          case ONE: return "lek albanais";
          case OTHER:
              default: return "leks albanais";
        }
      }
      case "AMD": {
        switch (category) {
          case ONE: return "dram arménien";
          case OTHER:
              default: return "drams arméniens";
        }
      }
      case "ANG": {
        switch (category) {
          case ONE: return "florin antillais";
          case OTHER:
              default: return "florins antillais";
        }
      }
      case "AOA": {
        switch (category) {
          case ONE: return "kwanza angolais";
          case OTHER:
              default: return "kwanzas angolais";
        }
      }
      case "AOK": {
        switch (category) {
          case ONE: return "kwanza angolais (1977–1990)";
          case OTHER:
              default: return "kwanzas angolais (1977–1990)";
        }
      }
      case "AON": {
        switch (category) {
          case ONE: return "nouveau kwanza angolais (1990–2000)";
          case OTHER:
              default: return "nouveaux kwanzas angolais (1990–2000)";
        }
      }
      case "AOR": {
        switch (category) {
          case ONE: return "kwanza angolais réajusté (1995–1999)";
          case OTHER:
              default: return "kwanzas angolais réajustés (1995–1999)";
        }
      }
      case "ARA": {
        switch (category) {
          case ONE: return "austral argentin";
          case OTHER:
              default: return "australs argentins";
        }
      }
      case "ARP": {
        switch (category) {
          case ONE: return "peso argentin (1983–1985)";
          case OTHER:
              default: return "pesos argentins (1983–1985)";
        }
      }
      case "ARS": {
        switch (category) {
          case ONE: return "peso argentin";
          case OTHER:
              default: return "pesos argentins";
        }
      }
      case "ATS": {
        switch (category) {
          case ONE: return "schilling autrichien";
          case OTHER:
              default: return "schillings autrichiens";
        }
      }
      case "AUD": {
        switch (category) {
          case ONE: return "dollar australien";
          case OTHER:
              default: return "dollars australiens";
        }
      }
      case "AWG": {
        switch (category) {
          case ONE: return "florin arubais";
          case OTHER:
              default: return "florins arubais";
        }
      }
      case "AZM": {
        switch (category) {
          case ONE: return "manat azéri (1993–2006)";
          case OTHER:
              default: return "manats azéris (1993–2006)";
        }
      }
      case "AZN": {
        switch (category) {
          case ONE: return "manat azéri";
          case OTHER:
              default: return "manats azéris";
        }
      }
      case "BAD": {
        switch (category) {
          case ONE: return "dinar bosniaque";
          case OTHER:
              default: return "dinars bosniaques";
        }
      }
      case "BAM": {
        switch (category) {
          case ONE: return "mark convertible bosniaque";
          case OTHER:
              default: return "marks convertibles bosniaques";
        }
      }
      case "BBD": {
        switch (category) {
          case ONE: return "dollar barbadien";
          case OTHER:
              default: return "dollars barbadiens";
        }
      }
      case "BDT": {
        switch (category) {
          case ONE: return "taka bangladeshi";
          case OTHER:
              default: return "takas bangladeshis";
        }
      }
      case "BEC": {
        switch (category) {
          case ONE: return "franc belge (convertible)";
          case OTHER:
              default: return "francs belges (convertibles)";
        }
      }
      case "BEF": {
        switch (category) {
          case ONE: return "franc belge";
          case OTHER:
              default: return "francs belges";
        }
      }
      case "BEL": {
        switch (category) {
          case ONE: return "franc belge (financier)";
          case OTHER:
              default: return "francs belges (financiers)";
        }
      }
      case "BGL": {
        switch (category) {
          case ONE: return "lev bulgare (1962–1999)";
          case OTHER:
              default: return "levs bulgares (1962–1999)";
        }
      }
      case "BGN": {
        switch (category) {
          case ONE: return "lev bulgare";
          case OTHER:
              default: return "levs bulgares";
        }
      }
      case "BHD": {
        switch (category) {
          case ONE: return "dinar bahreïni";
          case OTHER:
              default: return "dinars bahreïnis";
        }
      }
      case "BIF": {
        switch (category) {
          case ONE: return "franc burundais";
          case OTHER:
              default: return "francs burundais";
        }
      }
      case "BMD": {
        switch (category) {
          case ONE: return "dollar bermudien";
          case OTHER:
              default: return "dollars bermudiens";
        }
      }
      case "BND": {
        switch (category) {
          case ONE: return "dollar brunéien";
          case OTHER:
              default: return "dollars brunéiens";
        }
      }
      case "BOB": {
        switch (category) {
          case ONE: return "boliviano bolivien";
          case OTHER:
              default: return "bolivianos boliviens";
        }
      }
      case "BOP": {
        switch (category) {
          case ONE: return "peso bolivien";
          case OTHER:
              default: return "pesos boliviens";
        }
      }
      case "BOV": {
        switch (category) {
          case ONE: return "mvdol bolivien";
          case OTHER:
              default: return "mvdols boliviens";
        }
      }
      case "BRB": {
        switch (category) {
          case ONE: return "nouveau cruzeiro brésilien (1967–1986)";
          case OTHER:
              default: return "nouveaux cruzeiros brésiliens (1967–1986)";
        }
      }
      case "BRC": {
        switch (category) {
          case ONE: return "cruzado brésilien (1986–1989)";
          case OTHER:
              default: return "cruzados brésiliens (1986–1989)";
        }
      }
      case "BRE": {
        switch (category) {
          case ONE: return "cruzeiro brésilien (1990–1993)";
          case OTHER:
              default: return "cruzeiros brésiliens (1990–1993)";
        }
      }
      case "BRL": {
        switch (category) {
          case ONE: return "réal brésilien";
          case OTHER:
              default: return "réals brésiliens";
        }
      }
      case "BRN": {
        switch (category) {
          case ONE: return "nouveau cruzado brésilien (1989–1990)";
          case OTHER:
              default: return "nouveaux cruzados brésiliens (1989–1990)";
        }
      }
      case "BRR": {
        switch (category) {
          case ONE: return "cruzeiro réal brésilien (1993–1994)";
          case OTHER:
              default: return "cruzeiros réals brésiliens (1993–1994)";
        }
      }
      case "BSD": {
        switch (category) {
          case ONE: return "dollar bahaméen";
          case OTHER:
              default: return "dollars bahaméens";
        }
      }
      case "BTN": {
        switch (category) {
          case ONE: return "ngultrum bouthanais";
          case OTHER:
              default: return "ngultrums bouthanais";
        }
      }
      case "BUK": {
        switch (category) {
          case ONE: return "kyat birman";
          case OTHER:
              default: return "kyats birmans";
        }
      }
      case "BWP": {
        switch (category) {
          case ONE: return "pula botswanais";
          case OTHER:
              default: return "pulas botswanais";
        }
      }
      case "BYB": {
        switch (category) {
          case ONE: return "nouveau rouble biélorusse (1994–1999)";
          case OTHER:
              default: return "nouveaux roubles biélorusses (1994–1999)";
        }
      }
      case "BYN": {
        switch (category) {
          case ONE: return "rouble biélorusse";
          case OTHER:
              default: return "roubles biélorusses";
        }
      }
      case "BYR": {
        switch (category) {
          case ONE: return "rouble biélorusse (2000–2016)";
          case OTHER:
              default: return "roubles biélorusses (2000–2016)";
        }
      }
      case "BZD": {
        switch (category) {
          case ONE: return "dollar bélizéen";
          case OTHER:
              default: return "dollars bélizéens";
        }
      }
      case "CAD": {
        switch (category) {
          case ONE: return "dollar canadien";
          case OTHER:
              default: return "dollars canadiens";
        }
      }
      case "CDF": {
        switch (category) {
          case ONE: return "franc congolais";
          case OTHER:
              default: return "francs congolais";
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
          case ONE: return "franc suisse";
          case OTHER:
              default: return "francs suisses";
        }
      }
      case "CHW": {
        switch (category) {
          case ONE: return "franc WIR";
          case OTHER:
              default: return "francs WIR";
        }
      }
      case "CLF": {
        switch (category) {
          case ONE: return "unité d’investissement chilienne";
          case OTHER:
              default: return "unités d’investissement chiliennes";
        }
      }
      case "CLP": {
        switch (category) {
          case ONE: return "peso chilien";
          case OTHER:
              default: return "pesos chiliens";
        }
      }
      case "CNY": {
        switch (category) {
          case ONE: return "yuan renminbi chinois";
          case OTHER:
              default: return "yuans renminbi chinois";
        }
      }
      case "COP": {
        switch (category) {
          case ONE: return "peso colombien";
          case OTHER:
              default: return "pesos colombiens";
        }
      }
      case "COU": {
        switch (category) {
          case ONE: return "unité de valeur réelle colombienne";
          case OTHER:
              default: return "unités de valeur réelle colombiennes";
        }
      }
      case "CRC": {
        switch (category) {
          case ONE: return "colón costaricain";
          case OTHER:
              default: return "colóns costaricains";
        }
      }
      case "CSD": {
        switch (category) {
          case ONE: return "dinar serbo-monténégrin";
          case OTHER:
              default: return "dinars serbo-monténégrins";
        }
      }
      case "CSK": {
        switch (category) {
          case ONE: return "couronne forte tchécoslovaque";
          case OTHER:
              default: return "couronnes fortes tchécoslovaques";
        }
      }
      case "CUC": {
        switch (category) {
          case ONE: return "peso cubain convertible";
          case OTHER:
              default: return "pesos cubains convertibles";
        }
      }
      case "CUP": {
        switch (category) {
          case ONE: return "peso cubain";
          case OTHER:
              default: return "pesos cubains";
        }
      }
      case "CVE": {
        switch (category) {
          case ONE: return "escudo capverdien";
          case OTHER:
              default: return "escudos capverdiens";
        }
      }
      case "CYP": {
        switch (category) {
          case ONE: return "livre chypriote";
          case OTHER:
              default: return "livres chypriotes";
        }
      }
      case "CZK": {
        switch (category) {
          case ONE: return "couronne tchèque";
          case OTHER:
              default: return "couronnes tchèques";
        }
      }
      case "DDM": {
        switch (category) {
          case ONE: return "mark est-allemand";
          case OTHER:
              default: return "marks est-allemands";
        }
      }
      case "DEM": {
        switch (category) {
          case ONE: return "mark allemand";
          case OTHER:
              default: return "marks allemands";
        }
      }
      case "DJF": {
        switch (category) {
          case ONE: return "franc djiboutien";
          case OTHER:
              default: return "francs djiboutiens";
        }
      }
      case "DKK": {
        switch (category) {
          case ONE: return "couronne danoise";
          case OTHER:
              default: return "couronnes danoises";
        }
      }
      case "DOP": {
        switch (category) {
          case ONE: return "peso dominicain";
          case OTHER:
              default: return "pesos dominicains";
        }
      }
      case "DZD": {
        switch (category) {
          case ONE: return "dinar algérien";
          case OTHER:
              default: return "dinars algériens";
        }
      }
      case "ECS": {
        switch (category) {
          case ONE: return "sucre équatorien";
          case OTHER:
              default: return "sucres équatoriens";
        }
      }
      case "ECV": {
        switch (category) {
          case ONE: return "unité de valeur constante équatorienne (UVC)";
          case OTHER:
              default: return "unités de valeur constante équatoriennes (UVC)";
        }
      }
      case "EEK": {
        switch (category) {
          case ONE: return "couronne estonienne";
          case OTHER:
              default: return "couronnes estoniennes";
        }
      }
      case "EGP": {
        switch (category) {
          case ONE: return "livre égyptienne";
          case OTHER:
              default: return "livres égyptiennes";
        }
      }
      case "ERN": {
        switch (category) {
          case ONE: return "nafka érythréen";
          case OTHER:
              default: return "nafkas érythréens";
        }
      }
      case "ESA": {
        switch (category) {
          case ONE: return "peseta espagnole (compte A)";
          case OTHER:
              default: return "pesetas espagnoles (compte A)";
        }
      }
      case "ESB": {
        switch (category) {
          case ONE: return "peseta espagnole (compte convertible)";
          case OTHER:
              default: return "pesetas espagnoles (compte convertible)";
        }
      }
      case "ESP": {
        switch (category) {
          case ONE: return "peseta espagnole";
          case OTHER:
              default: return "pesetas espagnoles";
        }
      }
      case "ETB": {
        switch (category) {
          case ONE: return "birr éthiopien";
          case OTHER:
              default: return "birrs éthiopiens";
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
          case ONE: return "mark finlandais";
          case OTHER:
              default: return "marks finlandais";
        }
      }
      case "FJD": {
        switch (category) {
          case ONE: return "dollar fidjien";
          case OTHER:
              default: return "dollars fidjiens";
        }
      }
      case "FKP": {
        switch (category) {
          case ONE: return "livre des îles Malouines";
          case OTHER:
              default: return "livres des îles Malouines";
        }
      }
      case "FRF": {
        switch (category) {
          case ONE: return "franc français";
          case OTHER:
              default: return "francs français";
        }
      }
      case "GBP": {
        switch (category) {
          case ONE: return "livre sterling";
          case OTHER:
              default: return "livres sterling";
        }
      }
      case "GEK": {
        switch (category) {
          case ONE: return "coupon de lari géorgien";
          case OTHER:
              default: return "coupons de lari géorgiens";
        }
      }
      case "GEL": {
        switch (category) {
          case ONE: return "lari géorgien";
          case OTHER:
              default: return "lari géorgiens";
        }
      }
      case "GHC": {
        switch (category) {
          case ONE: return "cédi ghanéen (1967–2007)";
          case OTHER:
              default: return "cédis ghanéens (1967–2007)";
        }
      }
      case "GHS": {
        switch (category) {
          case ONE: return "cédi ghanéen";
          case OTHER:
              default: return "cédis ghanéens";
        }
      }
      case "GIP": {
        switch (category) {
          case ONE: return "livre de Gibraltar";
          case OTHER:
              default: return "livres de Gibraltar";
        }
      }
      case "GMD": {
        switch (category) {
          case ONE: return "dalasi gambien";
          case OTHER:
              default: return "dalasis gambiens";
        }
      }
      case "GNF": {
        switch (category) {
          case ONE: return "franc guinéen";
          case OTHER:
              default: return "francs guinéens";
        }
      }
      case "GNS": {
        switch (category) {
          case ONE: return "syli guinéen";
          case OTHER:
              default: return "sylis guinéens";
        }
      }
      case "GQE": {
        switch (category) {
          case ONE: return "ekwélé équatoguinéen";
          case OTHER:
              default: return "ekwélés équatoguinéens";
        }
      }
      case "GRD": {
        switch (category) {
          case ONE: return "drachme grecque";
          case OTHER:
              default: return "drachmes grecques";
        }
      }
      case "GTQ": {
        switch (category) {
          case ONE: return "quetzal guatémaltèque";
          case OTHER:
              default: return "quetzals guatémaltèques";
        }
      }
      case "GWE": {
        switch (category) {
          case ONE: return "escudo de Guinée portugaise";
          case OTHER:
              default: return "escudos de Guinée portugaise";
        }
      }
      case "GWP": {
        switch (category) {
          case ONE: return "peso bissau-guinéen";
          case OTHER:
              default: return "pesos bissau-guinéens";
        }
      }
      case "GYD": {
        switch (category) {
          case ONE: return "dollar du Guyana";
          case OTHER:
              default: return "dollars du Guyana";
        }
      }
      case "HKD": {
        switch (category) {
          case ONE: return "dollar de Hong Kong";
          case OTHER:
              default: return "dollars de Hong Kong";
        }
      }
      case "HNL": {
        switch (category) {
          case ONE: return "lempira hondurien";
          case OTHER:
              default: return "lempiras honduriens";
        }
      }
      case "HRD": {
        switch (category) {
          case ONE: return "dinar croate";
          case OTHER:
              default: return "dinars croates";
        }
      }
      case "HRK": {
        switch (category) {
          case ONE: return "kuna croate";
          case OTHER:
              default: return "kunas croates";
        }
      }
      case "HTG": {
        switch (category) {
          case ONE: return "gourde haïtienne";
          case OTHER:
              default: return "gourdes haïtiennes";
        }
      }
      case "HUF": {
        switch (category) {
          case ONE: return "forint hongrois";
          case OTHER:
              default: return "forints hongrois";
        }
      }
      case "IDR": {
        switch (category) {
          case ONE: return "roupie indonésienne";
          case OTHER:
              default: return "roupies indonésiennes";
        }
      }
      case "IEP": {
        switch (category) {
          case ONE: return "livre irlandaise";
          case OTHER:
              default: return "livres irlandaises";
        }
      }
      case "ILP": {
        switch (category) {
          case ONE: return "livre israélienne";
          case OTHER:
              default: return "livres israéliennes";
        }
      }
      case "ILS": {
        switch (category) {
          case ONE: return "nouveau shekel israélien";
          case OTHER:
              default: return "nouveaux shekels israéliens";
        }
      }
      case "INR": {
        switch (category) {
          case ONE: return "roupie indienne";
          case OTHER:
              default: return "roupies indiennes";
        }
      }
      case "IQD": {
        switch (category) {
          case ONE: return "dinar irakien";
          case OTHER:
              default: return "dinars irakiens";
        }
      }
      case "IRR": {
        switch (category) {
          case ONE: return "rial iranien";
          case OTHER:
              default: return "rials iraniens";
        }
      }
      case "ISK": {
        switch (category) {
          case ONE: return "couronne islandaise";
          case OTHER:
              default: return "couronnes islandaises";
        }
      }
      case "ITL": {
        switch (category) {
          case ONE: return "lire italienne";
          case OTHER:
              default: return "lires italiennes";
        }
      }
      case "JMD": {
        switch (category) {
          case ONE: return "dollar jamaïcain";
          case OTHER:
              default: return "dollars jamaïcains";
        }
      }
      case "JOD": {
        switch (category) {
          case ONE: return "dinar jordanien";
          case OTHER:
              default: return "dinars jordaniens";
        }
      }
      case "JPY": {
        switch (category) {
          case ONE: return "yen japonais";
          case OTHER:
              default: return "yens japonais";
        }
      }
      case "KES": {
        switch (category) {
          case ONE: return "shilling kényan";
          case OTHER:
              default: return "shillings kényans";
        }
      }
      case "KGS": {
        switch (category) {
          case ONE: return "som kirghize";
          case OTHER:
              default: return "soms kirghizes";
        }
      }
      case "KHR": {
        switch (category) {
          case ONE: return "riel cambodgien";
          case OTHER:
              default: return "riels cambodgiens";
        }
      }
      case "KMF": {
        switch (category) {
          case ONE: return "franc comorien";
          case OTHER:
              default: return "francs comoriens";
        }
      }
      case "KPW": {
        switch (category) {
          case ONE: return "won nord-coréen";
          case OTHER:
              default: return "wons nord-coréens";
        }
      }
      case "KRW": {
        switch (category) {
          case ONE: return "won sud-coréen";
          case OTHER:
              default: return "wons sud-coréens";
        }
      }
      case "KWD": {
        switch (category) {
          case ONE: return "dinar koweïtien";
          case OTHER:
              default: return "dinar koweïtiens";
        }
      }
      case "KYD": {
        switch (category) {
          case ONE: return "dollar des îles Caïmans";
          case OTHER:
              default: return "dollars des îles Caïmans";
        }
      }
      case "KZT": {
        switch (category) {
          case ONE: return "tenge kazakh";
          case OTHER:
              default: return "tenges kazakhs";
        }
      }
      case "LAK": {
        switch (category) {
          case ONE: return "kip loatien";
          case OTHER:
              default: return "kips loatiens";
        }
      }
      case "LBP": {
        switch (category) {
          case ONE: return "livre libanaise";
          case OTHER:
              default: return "livres libanaises";
        }
      }
      case "LKR": {
        switch (category) {
          case ONE: return "roupie srilankaise";
          case OTHER:
              default: return "roupies srilankaises";
        }
      }
      case "LRD": {
        switch (category) {
          case ONE: return "dollar libérien";
          case OTHER:
              default: return "dollars libériens";
        }
      }
      case "LSL": {
        switch (category) {
          case ONE: return "loti lesothan";
          case OTHER:
              default: return "maloti lesothans";
        }
      }
      case "LTL": {
        switch (category) {
          case ONE: return "litas lituanien";
          case OTHER:
              default: return "litas lituaniens";
        }
      }
      case "LTT": {
        switch (category) {
          case ONE: return "talonas lituanien";
          case OTHER:
              default: return "talonas lituaniens";
        }
      }
      case "LUC": {
        switch (category) {
          case ONE: return "franc convertible luxembourgeois";
          case OTHER:
              default: return "francs convertibles luxembourgeois";
        }
      }
      case "LUF": {
        switch (category) {
          case ONE: return "franc luxembourgeois";
          case OTHER:
              default: return "francs luxembourgeois";
        }
      }
      case "LUL": {
        switch (category) {
          case ONE: return "franc financier luxembourgeois";
          case OTHER:
              default: return "francs financiers luxembourgeois";
        }
      }
      case "LVL": {
        switch (category) {
          case ONE: return "lats letton";
          case OTHER:
              default: return "lats lettons";
        }
      }
      case "LVR": {
        switch (category) {
          case ONE: return "rouble letton";
          case OTHER:
              default: return "roubles lettons";
        }
      }
      case "LYD": {
        switch (category) {
          case ONE: return "dinar libyen";
          case OTHER:
              default: return "dinars libyens";
        }
      }
      case "MAD": {
        switch (category) {
          case ONE: return "dirham marocain";
          case OTHER:
              default: return "dirhams marocains";
        }
      }
      case "MAF": {
        switch (category) {
          case ONE: return "franc marocain";
          case OTHER:
              default: return "francs marocains";
        }
      }
      case "MDL": {
        switch (category) {
          case ONE: return "leu moldave";
          case OTHER:
              default: return "leus moldaves";
        }
      }
      case "MGA": {
        switch (category) {
          case ONE: return "ariary malgache";
          case OTHER:
              default: return "ariarys malgaches";
        }
      }
      case "MGF": {
        switch (category) {
          case ONE: return "franc malgache";
          case OTHER:
              default: return "francs malgaches";
        }
      }
      case "MKD": {
        switch (category) {
          case ONE: return "denar macédonien";
          case OTHER:
              default: return "denars macédoniens";
        }
      }
      case "MLF": {
        switch (category) {
          case ONE: return "franc malien";
          case OTHER:
              default: return "francs maliens";
        }
      }
      case "MMK": {
        switch (category) {
          case ONE: return "kyat myanmarais";
          case OTHER:
              default: return "kyats myanmarais";
        }
      }
      case "MNT": {
        switch (category) {
          case ONE: return "tugrik mongol";
          case OTHER:
              default: return "tugriks mongols";
        }
      }
      case "MOP": {
        switch (category) {
          case ONE: return "pataca macanaise";
          case OTHER:
              default: return "patacas macanaises";
        }
      }
      case "MRO": {
        switch (category) {
          case ONE: return "ouguiya mauritanien";
          case OTHER:
              default: return "ouguiyas mauritaniens";
        }
      }
      case "MTL": {
        switch (category) {
          case ONE: return "lire maltaise";
          case OTHER:
              default: return "lires maltaises";
        }
      }
      case "MTP": {
        switch (category) {
          case ONE: return "livre maltaise";
          case OTHER:
              default: return "livres maltaises";
        }
      }
      case "MUR": {
        switch (category) {
          case ONE: return "roupie mauricienne";
          case OTHER:
              default: return "roupies mauriciennes";
        }
      }
      case "MVR": {
        switch (category) {
          case ONE: return "rufiyaa maldivienne";
          case OTHER:
              default: return "rufiyaas maldiviennes";
        }
      }
      case "MWK": {
        switch (category) {
          case ONE: return "kwacha malawite";
          case OTHER:
              default: return "kwachas malawites";
        }
      }
      case "MXN": {
        switch (category) {
          case ONE: return "peso mexicain";
          case OTHER:
              default: return "pesos mexicains";
        }
      }
      case "MXP": {
        switch (category) {
          case ONE: return "peso d’argent mexicain (1861–1992)";
          case OTHER:
              default: return "pesos d’argent mexicains (1861–1992)";
        }
      }
      case "MXV": {
        switch (category) {
          case ONE: return "unité de conversion mexicaine (UDI)";
          case OTHER:
              default: return "unités de conversion mexicaines (UDI)";
        }
      }
      case "MYR": {
        switch (category) {
          case ONE: return "ringgit malais";
          case OTHER:
              default: return "ringgits malais";
        }
      }
      case "MZE": {
        switch (category) {
          case ONE: return "escudo mozambicain";
          case OTHER:
              default: return "escudos mozambicains";
        }
      }
      case "MZM": {
        switch (category) {
          case ONE: return "metical mozambicain (1980–2006)";
          case OTHER:
              default: return "meticais mozambicains (1980–2006)";
        }
      }
      case "MZN": {
        switch (category) {
          case ONE: return "metical mozambicain";
          case OTHER:
              default: return "meticais mozambicains";
        }
      }
      case "NAD": {
        switch (category) {
          case ONE: return "dollar namibien";
          case OTHER:
              default: return "dollars namibiens";
        }
      }
      case "NGN": {
        switch (category) {
          case ONE: return "naira nigérian";
          case OTHER:
              default: return "nairas nigérians";
        }
      }
      case "NIC": {
        switch (category) {
          case ONE: return "córdoba nicaraguayen (1912–1988)";
          case OTHER:
              default: return "córdobas nicaraguayens (1912–1988)";
        }
      }
      case "NIO": {
        switch (category) {
          case ONE: return "córdoba oro nicaraguayen";
          case OTHER:
              default: return "córdobas oro nicaraguayens";
        }
      }
      case "NLG": {
        switch (category) {
          case ONE: return "florin néerlandais";
          case OTHER:
              default: return "florins néerlandais";
        }
      }
      case "NOK": {
        switch (category) {
          case ONE: return "couronne norvégienne";
          case OTHER:
              default: return "couronnes norvégiennes";
        }
      }
      case "NPR": {
        switch (category) {
          case ONE: return "roupie népalaise";
          case OTHER:
              default: return "roupies népalaises";
        }
      }
      case "NZD": {
        switch (category) {
          case ONE: return "dollar néo-zélandais";
          case OTHER:
              default: return "dollars néo-zélandais";
        }
      }
      case "OMR": {
        switch (category) {
          case ONE: return "rial omanais";
          case OTHER:
              default: return "rials omanis";
        }
      }
      case "PAB": {
        switch (category) {
          case ONE: return "balboa panaméen";
          case OTHER:
              default: return "balboas panaméens";
        }
      }
      case "PEI": {
        switch (category) {
          case ONE: return "inti péruvien";
          case OTHER:
              default: return "intis péruviens";
        }
      }
      case "PEN": {
        switch (category) {
          case ONE: return "sol péruvien";
          case OTHER:
              default: return "sols péruviens";
        }
      }
      case "PES": {
        switch (category) {
          case ONE: return "sol péruvien (1863–1985)";
          case OTHER:
              default: return "sols péruviens (1863–1985)";
        }
      }
      case "PGK": {
        switch (category) {
          case ONE: return "kina papouan-néo-guinéen";
          case OTHER:
              default: return "kinas papouan-néo-guinéens";
        }
      }
      case "PHP": {
        switch (category) {
          case ONE: return "peso philippin";
          case OTHER:
              default: return "pesos philippins";
        }
      }
      case "PKR": {
        switch (category) {
          case ONE: return "roupie pakistanaise";
          case OTHER:
              default: return "roupies pakistanaises";
        }
      }
      case "PLN": {
        switch (category) {
          case ONE: return "zloty polonais";
          case OTHER:
              default: return "zlotys polonais";
        }
      }
      case "PLZ": {
        switch (category) {
          case ONE: return "zloty polonais (1950–1995)";
          case OTHER:
              default: return "zlotys polonais (1950–1995)";
        }
      }
      case "PTE": {
        switch (category) {
          case ONE: return "escudo portugais";
          case OTHER:
              default: return "escudos portugais";
        }
      }
      case "PYG": {
        switch (category) {
          case ONE: return "guaraní paraguayen";
          case OTHER:
              default: return "guaranís paraguayens";
        }
      }
      case "QAR": {
        switch (category) {
          case ONE: return "rial qatari";
          case OTHER:
              default: return "rials qataris";
        }
      }
      case "RHD": {
        switch (category) {
          case ONE: return "dollar rhodésien";
          case OTHER:
              default: return "dollars rhodésiens";
        }
      }
      case "ROL": {
        switch (category) {
          case ONE: return "leu roumain (1952–2005)";
          case OTHER:
              default: return "lei roumains (1952–2005)";
        }
      }
      case "RON": {
        switch (category) {
          case ONE: return "leu roumain";
          case OTHER:
              default: return "lei roumains";
        }
      }
      case "RSD": {
        switch (category) {
          case ONE: return "dinar serbe";
          case OTHER:
              default: return "dinars serbes";
        }
      }
      case "RUB": {
        switch (category) {
          case ONE: return "rouble russe";
          case OTHER:
              default: return "roubles russes";
        }
      }
      case "RUR": {
        switch (category) {
          case ONE: return "rouble russe (1991–1998)";
          case OTHER:
              default: return "roubles russes (1991–1998)";
        }
      }
      case "RWF": {
        switch (category) {
          case ONE: return "franc rwandais";
          case OTHER:
              default: return "francs rwandais";
        }
      }
      case "SAR": {
        switch (category) {
          case ONE: return "rial saoudien";
          case OTHER:
              default: return "rials saoudiens";
        }
      }
      case "SBD": {
        switch (category) {
          case ONE: return "dollar des îles Salomon";
          case OTHER:
              default: return "dollars des îles Salomon";
        }
      }
      case "SCR": {
        switch (category) {
          case ONE: return "roupie des Seychelles";
          case OTHER:
              default: return "roupies des Seychelles";
        }
      }
      case "SDD": {
        switch (category) {
          case ONE: return "dinar soudanais (1992–2007)";
          case OTHER:
              default: return "dinars soudanais (1992–2007)";
        }
      }
      case "SDG": {
        switch (category) {
          case ONE: return "livre soudanaise";
          case OTHER:
              default: return "livres soudanaises";
        }
      }
      case "SDP": {
        switch (category) {
          case ONE: return "livre soudanaise (1956–2007)";
          case OTHER:
              default: return "livres soudanaises (1956–2007)";
        }
      }
      case "SEK": {
        switch (category) {
          case ONE: return "couronne suédoise";
          case OTHER:
              default: return "couronnes suédoises";
        }
      }
      case "SGD": {
        switch (category) {
          case ONE: return "dollar de Singapour";
          case OTHER:
              default: return "dollars de Singapour";
        }
      }
      case "SHP": {
        switch (category) {
          case ONE: return "livre de Sainte-Hélène";
          case OTHER:
              default: return "livres de Sainte-Hélène";
        }
      }
      case "SIT": {
        switch (category) {
          case ONE: return "tolar slovène";
          case OTHER:
              default: return "tolars slovènes";
        }
      }
      case "SKK": {
        switch (category) {
          case ONE: return "couronne slovaque";
          case OTHER:
              default: return "couronnes slovaques";
        }
      }
      case "SLL": {
        switch (category) {
          case ONE: return "leone sierra-léonais";
          case OTHER:
              default: return "leones sierra-léonais";
        }
      }
      case "SOS": {
        switch (category) {
          case ONE: return "shilling somalien";
          case OTHER:
              default: return "shillings somaliens";
        }
      }
      case "SRD": {
        switch (category) {
          case ONE: return "dollar surinamais";
          case OTHER:
              default: return "dollars surinamais";
        }
      }
      case "SRG": {
        switch (category) {
          case ONE: return "florin surinamais";
          case OTHER:
              default: return "florins surinamais";
        }
      }
      case "SSP": {
        switch (category) {
          case ONE: return "livre sud-soudanaise";
          case OTHER:
              default: return "livres sud-soudanaises";
        }
      }
      case "STD": {
        switch (category) {
          case ONE: return "dobra santoméen";
          case OTHER:
              default: return "dobras santoméens";
        }
      }
      case "SUR": {
        switch (category) {
          case ONE: return "rouble soviétique";
          case OTHER:
              default: return "roubles soviétiques";
        }
      }
      case "SVC": {
        switch (category) {
          case ONE: return "colón salvadorien";
          case OTHER:
              default: return "colóns salvadoriens";
        }
      }
      case "SYP": {
        switch (category) {
          case ONE: return "livre syrienne";
          case OTHER:
              default: return "livres syriennes";
        }
      }
      case "SZL": {
        switch (category) {
          case ONE: return "lilangeni swazi";
          case OTHER:
              default: return "lilangenis swazis";
        }
      }
      case "THB": {
        switch (category) {
          case ONE: return "baht thaïlandais";
          case OTHER:
              default: return "bahts thaïlandais";
        }
      }
      case "TJR": {
        switch (category) {
          case ONE: return "rouble tadjik";
          case OTHER:
              default: return "roubles tadjiks";
        }
      }
      case "TJS": {
        switch (category) {
          case ONE: return "somoni tadjik";
          case OTHER:
              default: return "somonis tadjiks";
        }
      }
      case "TMM": {
        switch (category) {
          case ONE: return "manat turkmène";
          case OTHER:
              default: return "manats turkmènes";
        }
      }
      case "TMT": {
        switch (category) {
          case ONE: return "nouveau manat turkmène";
          case OTHER:
              default: return "nouveaux manats turkmènes";
        }
      }
      case "TND": {
        switch (category) {
          case ONE: return "dinar tunisien";
          case OTHER:
              default: return "dinars tunisiens";
        }
      }
      case "TOP": {
        switch (category) {
          case ONE: return "pa’anga tongan";
          case OTHER:
              default: return "pa’angas tongans";
        }
      }
      case "TPE": {
        switch (category) {
          case ONE: return "escudo timorais";
          case OTHER:
              default: return "escudos timorais";
        }
      }
      case "TRL": {
        switch (category) {
          case ONE: return "livre turque (1844–2005)";
          case OTHER:
              default: return "livres turques (1844–2005)";
        }
      }
      case "TRY": {
        switch (category) {
          case ONE: return "livre turque";
          case OTHER:
              default: return "livres turques";
        }
      }
      case "TTD": {
        switch (category) {
          case ONE: return "dollar de Trinité-et-Tobago";
          case OTHER:
              default: return "dollars de Trinité-et-Tobago";
        }
      }
      case "TWD": {
        switch (category) {
          case ONE: return "nouveau dollar taïwanais";
          case OTHER:
              default: return "nouveaux dollars taïwanais";
        }
      }
      case "TZS": {
        switch (category) {
          case ONE: return "shilling tanzanien";
          case OTHER:
              default: return "shillings tanzaniens";
        }
      }
      case "UAH": {
        switch (category) {
          case ONE: return "hryvnia ukrainienne";
          case OTHER:
              default: return "hryvnias ukrainiennes";
        }
      }
      case "UAK": {
        switch (category) {
          case ONE: return "karbovanets ukrainien (1992–1996)";
          case OTHER:
              default: return "karbovanets ukrainiens (1992–1996)";
        }
      }
      case "UGS": {
        switch (category) {
          case ONE: return "shilling ougandais (1966–1987)";
          case OTHER:
              default: return "shillings ougandais (1966–1987)";
        }
      }
      case "UGX": {
        switch (category) {
          case ONE: return "shilling ougandais";
          case OTHER:
              default: return "shillings ougandais";
        }
      }
      case "USD": {
        switch (category) {
          case ONE: return "dollar des États-Unis";
          case OTHER:
              default: return "dollars des États-Unis";
        }
      }
      case "USN": {
        switch (category) {
          case ONE: return "dollar des États-Unis (jour suivant)";
          case OTHER:
              default: return "dollars des États-Unis (jour suivant)";
        }
      }
      case "USS": {
        switch (category) {
          case ONE: return "dollar des États-Unis (jour même)";
          case OTHER:
              default: return "dollars des États-Unis (jour même)";
        }
      }
      case "UYI": {
        switch (category) {
          case ONE: return "peso uruguayen (unités indexées)";
          case OTHER:
              default: return "pesos uruguayen (unités indexées)";
        }
      }
      case "UYP": {
        switch (category) {
          case ONE: return "peso uruguayen (1975–1993)";
          case OTHER:
              default: return "pesos uruguayens (1975–1993)";
        }
      }
      case "UYU": {
        switch (category) {
          case ONE: return "peso uruguayen";
          case OTHER:
              default: return "pesos uruguayens";
        }
      }
      case "UZS": {
        switch (category) {
          case ONE: return "sum ouzbek";
          case OTHER:
              default: return "sums ouzbeks";
        }
      }
      case "VEB": {
        switch (category) {
          case ONE: return "bolivar vénézuélien (1871–2008)";
          case OTHER:
              default: return "bolivar vénézuélien (1871–2008)";
        }
      }
      case "VEF": {
        switch (category) {
          case ONE: return "bolivar vénézuélien";
          case OTHER:
              default: return "bolivars vénézuéliens";
        }
      }
      case "VND": {
        switch (category) {
          case ONE: return "dông vietnamien";
          case OTHER:
              default: return "dôngs vietnamiens";
        }
      }
      case "VUV": {
        switch (category) {
          case ONE: return "vatu vanuatuan";
          case OTHER:
              default: return "vatus vanuatuans";
        }
      }
      case "WST": {
        switch (category) {
          case ONE: return "tala samoan";
          case OTHER:
              default: return "talas samoans";
        }
      }
      case "XAF": {
        switch (category) {
          case ONE: return "franc CFA (BEAC)";
          case OTHER:
              default: return "francs CFA (BEAC)";
        }
      }
      case "XAG": {
        switch (category) {
          case ONE: return "once troy d’argent";
          case OTHER:
              default: return "onces troy d’argent";
        }
      }
      case "XAU": {
        switch (category) {
          case ONE: return "once troy d’or";
          case OTHER:
              default: return "onces troy d’or";
        }
      }
      case "XBA": {
        switch (category) {
          case ONE: return "unité composée européenne (EURCO)";
          case OTHER:
              default: return "unités composées européennes (EURCO)";
        }
      }
      case "XBB": {
        switch (category) {
          case ONE: return "unité monétaire européenne (UME–6)";
          case OTHER:
              default: return "unités monétaires européennes (UME–6)";
        }
      }
      case "XBC": {
        switch (category) {
          case ONE: return "unité de compte 9 européenne (UEC–9)";
          case OTHER:
              default: return "unités de compte 9 européennes (UEC–9)";
        }
      }
      case "XBD": {
        switch (category) {
          case ONE: return "unité de compte 17 européenne (UEC–17)";
          case OTHER:
              default: return "unités de compte 17 européennes (UEC–17)";
        }
      }
      case "XCD": {
        switch (category) {
          case ONE: return "dollar des Caraïbes orientales";
          case OTHER:
              default: return "dollars des Caraïbes orientales";
        }
      }
      case "XDR": {
        switch (category) {
          case ONE: return "droit de tirage spécial";
          case OTHER:
              default: return "droits de tirage spéciaux";
        }
      }
      case "XFO": {
        switch (category) {
          case ONE: return "franc or";
          case OTHER:
              default: return "francs or";
        }
      }
      case "XFU": {
        switch (category) {
          case ONE: return "franc UIC";
          case OTHER:
              default: return "francs UIC";
        }
      }
      case "XOF": {
        switch (category) {
          case ONE: return "franc CFA (BCEAO)";
          case OTHER:
              default: return "francs CFA (BCEAO)";
        }
      }
      case "XPD": {
        switch (category) {
          case ONE: return "once troy de palladium";
          case OTHER:
              default: return "onces troy de palladium";
        }
      }
      case "XPF": {
        switch (category) {
          case ONE: return "franc CFP";
          case OTHER:
              default: return "francs CFP";
        }
      }
      case "XPT": {
        switch (category) {
          case ONE: return "once troy de platine";
          case OTHER:
              default: return "onces troy de platine";
        }
      }
      case "XRE": {
        switch (category) {
          case ONE: return "unité de fonds RINET";
          case OTHER:
              default: return "unités de fonds RINET";
        }
      }
      case "XTS": {
        switch (category) {
          case ONE: return "(devise de test)";
          case OTHER:
              default: return "(devises de test)";
        }
      }
      case "XXX": {
        switch (category) {
          case ONE: return "devise inconnue";
          case OTHER:
              default: return "devises inconnues";
        }
      }
      case "YDD": {
        switch (category) {
          case ONE: return "dinar nord-yéménite";
          case OTHER:
              default: return "dinars nord-yéménites";
        }
      }
      case "YER": {
        switch (category) {
          case ONE: return "rial yéménite";
          case OTHER:
              default: return "rials yéménites";
        }
      }
      case "YUD": {
        switch (category) {
          case ONE: return "dinar fort yougoslave (1966–1989)";
          case OTHER:
              default: return "dinars forts yougoslaves (1966–1989)";
        }
      }
      case "YUM": {
        switch (category) {
          case ONE: return "nouveau dinar yougoslave (1994–2003)";
          case OTHER:
              default: return "nouveaux dinars yougoslaves (1994–2003)";
        }
      }
      case "YUN": {
        switch (category) {
          case ONE: return "dinar convertible yougoslave (1990–1992)";
          case OTHER:
              default: return "dinars convertibles yougoslaves (1990–1992)";
        }
      }
      case "ZAL": {
        switch (category) {
          case ONE: return "rand sud-africain (financier)";
          case OTHER:
              default: return "rands sud-africains (financiers)";
        }
      }
      case "ZAR": {
        switch (category) {
          case ONE: return "rand sud-africain";
          case OTHER:
              default: return "rands sud-africains";
        }
      }
      case "ZMK": {
        switch (category) {
          case ONE: return "kwacha zambien (1968–2012)";
          case OTHER:
              default: return "kwachas zambiens (1968–2012)";
        }
      }
      case "ZMW": {
        switch (category) {
          case ONE: return "kwacha zambien";
          case OTHER:
              default: return "kwachas zambiens";
        }
      }
      case "ZRN": {
        switch (category) {
          case ONE: return "nouveau zaïre zaïrien";
          case OTHER:
              default: return "nouveaux zaïres zaïriens";
        }
      }
      case "ZRZ": {
        switch (category) {
          case ONE: return "zaïre zaïrois";
          case OTHER:
              default: return "zaïres zaïrois";
        }
      }
      case "ZWD": {
        switch (category) {
          case ONE: return "dollar zimbabwéen";
          case OTHER:
              default: return "dollars zimbabwéens";
        }
      }
      case "ZWL": {
        switch (category) {
          case ONE: return "dollar zimbabwéen (2009)";
          case OTHER:
              default: return "dollars zimbabwéens (2009)";
        }
      }
      case "ZWR": {
        switch (category) {
          case ONE: return "dollar zimbabwéen (2008)";
          case OTHER:
              default: return "dollars zimbabwéens (2008)";
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
      this.group = " ";
      this.percent = "%";
      this.minus = "-";
      this.plus = "+";
      this.exponential = "E";
      this.superscriptingExponent = "×";
      this.perMille = "‰";
      this.infinity = "∞";
      this.nan = "NaN";
      this.currencyDecimal = ",";
      this.currencyGroup = " ";
    }
  }
}
