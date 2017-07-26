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

public class _NumberFormatter_zh extends NumberFormatterBase {
  public static final NumberPattern[] DECIMAL_STANDARD = patterns("#,##0.###", "-#,##0.###");

  public static final NumberPattern[] PERCENT_STANDARD = patterns("#,##0%", "-#,##0%");

  public static final NumberPattern[] CURRENCY_STANDARD = patterns("¤#,##0.00", "-¤#,##0.00");

  public static final NumberPattern[] CURRENCY_ACCOUNTING = patterns("¤#,##0.00", "(¤#,##0.00)");

  public static final NumberPattern[] DECIMAL_STANDARD_COMPACT = patterns("#,##0", "-#,##0");

  public static final NumberPattern[] CURRENCY_STANDARD_COMPACT = patterns("¤#,##0", "-¤#,##0");

  private final NumberPattern[] DECIMAL_SHORT_1K_OTHER = patterns("#,##0", "-#,##0");

  private final NumberPattern[] DECIMAL_SHORT_10K_OTHER = patterns("0万", "-0万");

  private final NumberPattern[] DECIMAL_SHORT_100K_OTHER = patterns("00万", "-00万");

  private final NumberPattern[] DECIMAL_SHORT_1M_OTHER = patterns("000万", "-000万");

  private final NumberPattern[] DECIMAL_SHORT_10M_OTHER = patterns("0000万", "-0000万");

  private final NumberPattern[] DECIMAL_SHORT_100M_OTHER = patterns("0亿", "-0亿");

  private final NumberPattern[] DECIMAL_SHORT_1B_OTHER = patterns("00亿", "-00亿");

  private final NumberPattern[] DECIMAL_SHORT_10B_OTHER = patterns("000亿", "-000亿");

  private final NumberPattern[] DECIMAL_SHORT_100B_OTHER = patterns("0000亿", "-0000亿");

  private final NumberPattern[] DECIMAL_SHORT_1T_OTHER = patterns("0兆", "-0兆");

  private final NumberPattern[] DECIMAL_SHORT_10T_OTHER = patterns("00兆", "-00兆");

  private final NumberPattern[] DECIMAL_SHORT_100T_OTHER = patterns("000兆", "-000兆");

  private final int DECIMAL_SHORT_1K_POWER = 1;

  private final int DECIMAL_SHORT_10K_POWER = 4;

  private final int DECIMAL_SHORT_100K_POWER = 4;

  private final int DECIMAL_SHORT_1M_POWER = 4;

  private final int DECIMAL_SHORT_10M_POWER = 4;

  private final int DECIMAL_SHORT_100M_POWER = 8;

  private final int DECIMAL_SHORT_1B_POWER = 8;

  private final int DECIMAL_SHORT_10B_POWER = 8;

  private final int DECIMAL_SHORT_100B_POWER = 8;

  private final int DECIMAL_SHORT_1T_POWER = 12;

  private final int DECIMAL_SHORT_10T_POWER = 12;

  private final int DECIMAL_SHORT_100T_POWER = 12;

  private final NumberPattern[] DECIMAL_LONG_1K_OTHER = patterns("#,##0", "-#,##0");

  private final NumberPattern[] DECIMAL_LONG_10K_OTHER = patterns("0万", "-0万");

  private final NumberPattern[] DECIMAL_LONG_100K_OTHER = patterns("00万", "-00万");

  private final NumberPattern[] DECIMAL_LONG_1M_OTHER = patterns("000万", "-000万");

  private final NumberPattern[] DECIMAL_LONG_10M_OTHER = patterns("0000万", "-0000万");

  private final NumberPattern[] DECIMAL_LONG_100M_OTHER = patterns("0亿", "-0亿");

  private final NumberPattern[] DECIMAL_LONG_1B_OTHER = patterns("00亿", "-00亿");

  private final NumberPattern[] DECIMAL_LONG_10B_OTHER = patterns("000亿", "-000亿");

  private final NumberPattern[] DECIMAL_LONG_100B_OTHER = patterns("0000亿", "-0000亿");

  private final NumberPattern[] DECIMAL_LONG_1T_OTHER = patterns("0兆", "-0兆");

  private final NumberPattern[] DECIMAL_LONG_10T_OTHER = patterns("00兆", "-00兆");

  private final NumberPattern[] DECIMAL_LONG_100T_OTHER = patterns("000兆", "-000兆");

  private final int DECIMAL_LONG_1K_POWER = 1;

  private final int DECIMAL_LONG_10K_POWER = 4;

  private final int DECIMAL_LONG_100K_POWER = 4;

  private final int DECIMAL_LONG_1M_POWER = 4;

  private final int DECIMAL_LONG_10M_POWER = 4;

  private final int DECIMAL_LONG_100M_POWER = 8;

  private final int DECIMAL_LONG_1B_POWER = 8;

  private final int DECIMAL_LONG_10B_POWER = 8;

  private final int DECIMAL_LONG_100B_POWER = 8;

  private final int DECIMAL_LONG_1T_POWER = 12;

  private final int DECIMAL_LONG_10T_POWER = 12;

  private final int DECIMAL_LONG_100T_POWER = 12;

  private final NumberPattern[] CURRENCY_SHORT_1K_OTHER = patterns("¤#,##0", "-¤#,##0");

  private final NumberPattern[] CURRENCY_SHORT_10K_OTHER = patterns("¤0万", "-¤0万");

  private final NumberPattern[] CURRENCY_SHORT_100K_OTHER = patterns("¤00万", "-¤00万");

  private final NumberPattern[] CURRENCY_SHORT_1M_OTHER = patterns("¤000万", "-¤000万");

  private final NumberPattern[] CURRENCY_SHORT_10M_OTHER = patterns("¤0000万", "-¤0000万");

  private final NumberPattern[] CURRENCY_SHORT_100M_OTHER = patterns("¤0亿", "-¤0亿");

  private final NumberPattern[] CURRENCY_SHORT_1B_OTHER = patterns("¤00亿", "-¤00亿");

  private final NumberPattern[] CURRENCY_SHORT_10B_OTHER = patterns("¤000亿", "-¤000亿");

  private final NumberPattern[] CURRENCY_SHORT_100B_OTHER = patterns("¤0000亿", "-¤0000亿");

  private final NumberPattern[] CURRENCY_SHORT_1T_OTHER = patterns("¤0兆", "-¤0兆");

  private final NumberPattern[] CURRENCY_SHORT_10T_OTHER = patterns("¤00兆", "-¤00兆");

  private final NumberPattern[] CURRENCY_SHORT_100T_OTHER = patterns("¤000兆", "-¤000兆");

  private final int CURRENCY_SHORT_1K_POWER = 1;

  private final int CURRENCY_SHORT_10K_POWER = 4;

  private final int CURRENCY_SHORT_100K_POWER = 4;

  private final int CURRENCY_SHORT_1M_POWER = 4;

  private final int CURRENCY_SHORT_10M_POWER = 4;

  private final int CURRENCY_SHORT_100M_POWER = 8;

  private final int CURRENCY_SHORT_1B_POWER = 8;

  private final int CURRENCY_SHORT_10B_POWER = 8;

  private final int CURRENCY_SHORT_100B_POWER = 8;

  private final int CURRENCY_SHORT_1T_POWER = 12;

  private final int CURRENCY_SHORT_10T_POWER = 12;

  private final int CURRENCY_SHORT_100T_POWER = 12;

  public _NumberFormatter_zh() {
    super(
        new CLDRLocale("zh", "", "", ""),
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
          case OTHER:
              default: return DECIMAL_SHORT_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_SHORT_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
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
          case OTHER:
              default: return DECIMAL_LONG_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case OTHER:
              default: return DECIMAL_LONG_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
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
          case OTHER:
              default: return CURRENCY_SHORT_1K_OTHER;
        }
      }
      case 5: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_10K_OTHER;
        }
      }
      case 6: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_100K_OTHER;
        }
      }
      case 7: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_1M_OTHER;
        }
      }
      case 8: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_10M_OTHER;
        }
      }
      case 9: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_100M_OTHER;
        }
      }
      case 10: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_1B_OTHER;
        }
      }
      case 11: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_10B_OTHER;
        }
      }
      case 12: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_100B_OTHER;
        }
      }
      case 13: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_1T_OTHER;
        }
      }
      case 14: {
        switch (category) {
          case OTHER:
              default: return CURRENCY_SHORT_10T_OTHER;
        }
      }
      case 15: default: {
        switch (category) {
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
      case "MXN": return "MX$";
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
      case "TWD": return "NT$";
      case "RSD": return "RSD";
      case "ZRZ": return "ZRZ";
      case "UYI": return "UYI";
      case "MYR": return "MYR";
      case "FKP": return "FKP";
      case "UYP": return "UYP";
      case "XOF": return "CFA";
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
      case "THB": return "THB";
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
      case "ISJ": return "ISJ";
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
      case "XAF": return "FCFA";
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
      case "AUD": return "AU$";
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
      case "XCD": return "EC$";
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
      case "GBP": return "£";
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
      case "CNY": return "￥";
      case "ESA": return "ESA";
      case "GWE": return "GWE";
      case "ESB": return "ESB";
      case "BMD": return "BMD";
      case "PHP": return "PHP";
      case "XXX": return "XXX";
      case "PYG": return "PYG";
      case "JMD": return "JMD";
      case "GWP": return "GWP";
      case "ESP": return "ESP";
      case "COP": return "COP";
      case "USD": return "US$";
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
      case "ALK": return "ALK";
      case "ALL": return "ALL";
      case "GHC": return "GHC";
      case "MTL": return "MTL";
      case "ZMW": return "ZMW";
      case "MTP": return "MTP";
      case "ILP": return "ILP";
      case "MDC": return "MDC";
      case "ILR": return "ILS";
      case "TRL": return "TRL";
      case "ILS": return "₪";
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
      case "HKD": return "HK$";
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
      case "MVP": return "";
      case "MVR": return "MVR";
      case "KRO": return "KRO";
      case "SRG": return "SRG";
      case "DDM": return "DDM";
      case "INR": return "₹";
      case "LTT": return "LTT";
      case "KRW": return "￦";
      case "JPY": return "JP¥";
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
      case "NZD": return "NZ$";
      case "BRL": return "R$";
      case "BRN": return "BRN";
      default: return "";
    }
  }

  public String getCurrencyDisplayName(String code) {
    switch (code) {
      case "UGS": return "乌干达先令 (1966–1987)";
      case "FJD": return "斐济元";
      case "MXN": return "墨西哥比索";
      case "STD": return "圣多美和普林西比多布拉";
      case "BRR": return "巴西克鲁塞罗 (1993–1994)";
      case "LVL": return "拉脱维亚拉特";
      case "SCR": return "塞舌尔卢比";
      case "CDF": return "刚果法郎";
      case "MXP": return "墨西哥银比索 (1861–1992)";
      case "ZAL": return "南非兰特 (金融)";
      case "BBD": return "巴巴多斯元";
      case "HNL": return "洪都拉斯伦皮拉";
      case "UGX": return "乌干达先令";
      case "LVR": return "拉脱维亚卢布";
      case "MXV": return "墨西哥（资金）";
      case "ZAR": return "南非兰特";
      case "BRZ": return "巴西克鲁塞罗 (1942–1967)";
      case "CUC": return "古巴可兑换比索";
      case "BSD": return "巴哈马元";
      case "SDD": return "苏丹第纳尔 (1992–2007)";
      case "SDG": return "苏丹镑";
      case "ZRN": return "新扎伊尔 (1993–1998)";
      case "IQD": return "伊拉克第纳尔";
      case "SDP": return "旧苏丹镑";
      case "CUP": return "古巴比索";
      case "GMD": return "冈比亚达拉西";
      case "TWD": return "新台币";
      case "RSD": return "塞尔维亚第纳尔";
      case "ZRZ": return "扎伊尔 (1971–1993)";
      case "UYI": return "乌拉圭比索（索引单位）";
      case "MYR": return "马来西亚林吉特";
      case "FKP": return "福克兰群岛镑";
      case "UYP": return "乌拉圭比索 (1975–1993)";
      case "XOF": return "西非法郎";
      case "ARA": return "阿根廷奥斯特拉尔";
      case "UYU": return "乌拉圭比索";
      case "SUR": return "苏联卢布";
      case "CVE": return "佛得角埃斯库多";
      case "OMR": return "阿曼里亚尔";
      case "KES": return "肯尼亚先令";
      case "SEK": return "瑞典克朗";
      case "MZE": return "莫桑比克埃斯库多";
      case "ARL": return "阿根廷法定比索 (1970–1983)";
      case "ARM": return "阿根廷比索 (1881–1970)";
      case "BTN": return "不丹努尔特鲁姆";
      case "GNF": return "几内亚法郎";
      case "ARP": return "阿根廷比索 (1983–1985)";
      case "MZN": return "莫桑比克美提卡";
      case "MZM": return "旧莫桑比克美提卡";
      case "SVC": return "萨尔瓦多科朗";
      case "ARS": return "阿根廷比索";
      case "QAR": return "卡塔尔里亚尔";
      case "IRR": return "伊朗里亚尔";
      case "NLG": return "荷兰盾";
      case "GNS": return "几内亚西里";
      case "XPD": return "钯";
      case "THB": return "泰铢";
      case "UZS": return "乌兹别克斯坦苏姆";
      case "XPF": return "太平洋法郎";
      case "BDT": return "孟加拉塔卡";
      case "LYD": return "利比亚第纳尔";
      case "BUK": return "缅元";
      case "KWD": return "科威特第纳尔";
      case "XPT": return "铂";
      case "RUB": return "俄罗斯卢布";
      case "ISK": return "冰岛克朗";
      case "BEC": return "比利时法郎（可兑换）";
      case "ISJ": return "冰岛克朗(1918–1981)";
      case "BEF": return "比利时法郎";
      case "MKD": return "马其顿第纳尔";
      case "BEL": return "比利时法郎（金融）";
      case "RUR": return "俄国卢布 (1991–1998)";
      case "DZD": return "阿尔及利亚第纳尔";
      case "PAB": return "巴拿马巴波亚";
      case "MKN": return "马其顿第纳尔 (1992–1993)";
      case "SGD": return "新加坡元";
      case "KGS": return "吉尔吉斯斯坦索姆";
      case "HRD": return "克罗地亚第纳尔";
      case "XAF": return "中非法郎";
      case "XAG": return "银";
      case "ATS": return "奥地利先令";
      case "CHF": return "瑞士法郎";
      case "HRK": return "克罗地亚库纳";
      case "ITL": return "意大利里拉";
      case "CHE": return "欧元 (WIR)";
      case "DJF": return "吉布提法郎";
      case "MLF": return "马里法郎";
      case "XRE": return "RINET 基金";
      case "TZS": return "坦桑尼亚先令";
      case "ADP": return "安道尔比塞塔";
      case "VND": return "越南盾";
      case "XAU": return "黄金";
      case "AUD": return "澳大利亚元";
      case "CHW": return "法郎 (WIR)";
      case "KHR": return "柬埔寨瑞尔";
      case "IDR": return "印度尼西亚盾";
      case "XBA": return "欧洲复合单位";
      case "KYD": return "开曼元";
      case "VNN": return "越南盾 (1978–1985)";
      case "XBC": return "欧洲计算单位 (XBC)";
      case "YDD": return "也门第纳尔";
      case "XBB": return "欧洲货币联盟";
      case "BWP": return "博茨瓦纳普拉";
      case "GQE": return "赤道几内亚埃奎勒";
      case "SHP": return "圣赫勒拿群岛磅";
      case "CYP": return "塞浦路斯镑";
      case "XBD": return "欧洲计算单位 (XBD)";
      case "TJS": return "塔吉克斯坦索莫尼";
      case "TJR": return "塔吉克斯坦卢布";
      case "AED": return "阿联酋迪拉姆";
      case "RWF": return "卢旺达法郎";
      case "DKK": return "丹麦克朗";
      case "BGL": return "保加利亚硬列弗";
      case "ZWD": return "津巴布韦元 (1980–2008)";
      case "BGN": return "保加利亚列弗";
      case "BGM": return "保加利亚社会党列弗";
      case "YUD": return "南斯拉夫硬第纳尔 (1966–1990)";
      case "MMK": return "缅甸元";
      case "BGO": return "保加利亚列弗 (1879–1952)";
      case "NOK": return "挪威克朗";
      case "SYP": return "叙利亚镑";
      case "ZWL": return "津巴布韦元 (2009)";
      case "YUM": return "南斯拉夫新第纳尔 (1994–2002)";
      case "LKR": return "斯里兰卡卢比";
      case "YUN": return "南斯拉夫可兑换第纳尔 (1990–1992)";
      case "ZWR": return "津巴布韦元 (2008)";
      case "CZK": return "捷克克朗";
      case "IEP": return "爱尔兰镑";
      case "YUR": return "南斯拉夫改良第纳尔 (1992–1993)";
      case "GRD": return "希腊德拉克马";
      case "XCD": return "东加勒比元";
      case "HTG": return "海地古德";
      case "XSU": return "XSU";
      case "AFA": return "阿富汗尼 (1927–2002)";
      case "BHD": return "巴林第纳尔";
      case "SIT": return "斯洛文尼亚托拉尔";
      case "PTE": return "葡萄牙埃斯库多";
      case "KZT": return "哈萨克斯坦坚戈";
      case "SZL": return "斯威士兰里兰吉尼";
      case "YER": return "也门里亚尔";
      case "AFN": return "阿富汗尼";
      case "BYB": return "白俄罗斯新卢布 (1994–1999)";
      case "RHD": return "罗得西亚元";
      case "AWG": return "阿鲁巴弗罗林";
      case "NPR": return "尼泊尔卢比";
      case "MNT": return "蒙古图格里克";
      case "GBP": return "英镑";
      case "BYN": return "白俄罗斯卢布";
      case "XTS": return "测试货币代码";
      case "HUF": return "匈牙利福林";
      case "BYR": return "白俄罗斯卢布 (2000–2016)";
      case "BIF": return "布隆迪法郎";
      case "XUA": return "XUA";
      case "XDR": return "特别提款权";
      case "BZD": return "伯利兹元";
      case "MOP": return "澳门币";
      case "NAD": return "纳米比亚元";
      case "SKK": return "斯洛伐克克朗";
      case "PEI": return "秘鲁印第";
      case "TMM": return "土库曼斯坦马纳特 (1993–2009)";
      case "PEN": return "秘鲁索尔";
      case "WST": return "萨摩亚塔拉";
      case "TMT": return "土库曼斯坦马纳特";
      case "FRF": return "法国法郎";
      case "CLF": return "智利（资金）";
      case "CLE": return "智利埃斯库多";
      case "PES": return "秘鲁索尔 (1863–1965)";
      case "GTQ": return "危地马拉格查尔";
      case "CLP": return "智利比索";
      case "XEU": return "欧洲货币单位";
      case "TND": return "突尼斯第纳尔";
      case "SLL": return "塞拉利昂利昂";
      case "XFO": return "法国金法郎";
      case "DOP": return "多米尼加比索";
      case "KMF": return "科摩罗法郎";
      case "XFU": return "法国法郎 (UIC)";
      case "GEK": return "乔治亚库蓬拉瑞特";
      case "GEL": return "格鲁吉亚拉里";
      case "MAD": return "摩洛哥迪拉姆";
      case "MAF": return "摩洛哥法郎";
      case "AZM": return "阿塞拜疆马纳特 (1993–2006)";
      case "TOP": return "汤加潘加";
      case "AZN": return "阿塞拜疆马纳特";
      case "PGK": return "巴布亚新几内亚基那";
      case "UAH": return "乌克兰格里夫纳";
      case "UAK": return "乌克兰币";
      case "ERN": return "厄立特里亚纳克法";
      case "TPE": return "帝汶埃斯库多";
      case "MRO": return "毛里塔尼亚乌吉亚";
      case "CNY": return "人民币";
      case "ESA": return "西班牙比塞塔（帐户 A）";
      case "GWE": return "葡萄牙几内亚埃斯库多";
      case "ESB": return "西班牙比塞塔（兑换帐户）";
      case "BMD": return "百慕大元";
      case "PHP": return "菲律宾比索";
      case "XXX": return "未知货币";
      case "PYG": return "巴拉圭瓜拉尼";
      case "JMD": return "牙买加元";
      case "GWP": return "几内亚比绍比索";
      case "ESP": return "西班牙比塞塔";
      case "COP": return "哥伦比亚比索";
      case "USD": return "美元";
      case "COU": return "哥伦比亚币";
      case "MCF": return "摩纳哥法郎";
      case "USN": return "美元（次日）";
      case "ETB": return "埃塞俄比亚比尔";
      case "VEB": return "委内瑞拉玻利瓦尔 (1871–2008)";
      case "ECS": return "厄瓜多尔苏克雷";
      case "USS": return "美元（当日）";
      case "SOS": return "索马里先令";
      case "VEF": return "委内瑞拉玻利瓦尔";
      case "VUV": return "瓦努阿图瓦图";
      case "LAK": return "老挝基普";
      case "BND": return "文莱元";
      case "ECV": return "厄瓜多尔 (UVC)";
      case "ZMK": return "赞比亚克瓦查 (1968–2012)";
      case "LRD": return "利比里亚元";
      case "ALK": return "阿尔巴尼亚列克(1946–1965)";
      case "ALL": return "阿尔巴尼亚列克";
      case "GHC": return "加纳塞第";
      case "MTL": return "马耳他里拉";
      case "ZMW": return "赞比亚克瓦查";
      case "MTP": return "马耳他镑";
      case "ILP": return "以色列镑";
      case "MDC": return "摩尔多瓦库邦";
      case "ILR": return "以色列谢克尔(1980–1985)";
      case "TRL": return "土耳其里拉 (1922–2005)";
      case "ILS": return "以色列新谢克尔";
      case "GHS": return "加纳塞地";
      case "GYD": return "圭亚那元";
      case "KPW": return "朝鲜元";
      case "BOB": return "玻利维亚诺";
      case "MDL": return "摩尔多瓦列伊";
      case "AMD": return "亚美尼亚德拉姆";
      case "TRY": return "土耳其里拉";
      case "LBP": return "黎巴嫩镑";
      case "BOL": return "玻利维亚诺 (1863–1963)";
      case "JOD": return "约旦第纳尔";
      case "HKD": return "港元";
      case "BOP": return "玻利维亚比索";
      case "EUR": return "欧元";
      case "LSL": return "莱索托洛蒂";
      case "CAD": return "加拿大元";
      case "BOV": return "玻利维亚 Mvdol（资金）";
      case "EEK": return "爱沙尼亚克朗";
      case "MUR": return "毛里求斯卢比";
      case "ROL": return "旧罗马尼亚列伊";
      case "GIP": return "直布罗陀镑";
      case "RON": return "罗马尼亚列伊";
      case "NGN": return "尼日利亚奈拉";
      case "CRC": return "哥斯达黎加科朗";
      case "PKR": return "巴基斯坦卢比";
      case "ANG": return "荷属安的列斯盾";
      case "KRH": return "韩元 (1953–1962)";
      case "SRD": return "苏里南元";
      case "LTL": return "立陶宛立特";
      case "SAR": return "沙特里亚尔";
      case "TTD": return "特立尼达和多巴哥元";
      case "MVP": return "马尔代夫卢比(1947–1981)";
      case "MVR": return "马尔代夫卢菲亚";
      case "KRO": return "韩元 (1945–1953)";
      case "SRG": return "苏里南盾";
      case "DDM": return "东德奥斯特马克";
      case "INR": return "印度卢比";
      case "LTT": return "立陶宛塔咯呐司";
      case "KRW": return "韩元";
      case "JPY": return "日元";
      case "AOA": return "安哥拉宽扎";
      case "PLN": return "波兰兹罗提";
      case "SBD": return "所罗门群岛元";
      case "CSD": return "旧塞尔维亚第纳尔";
      case "CSK": return "捷克硬克朗";
      case "LUC": return "卢森堡可兑换法郎";
      case "LUF": return "卢森堡法郎";
      case "AOK": return "安哥拉宽扎 (1977–1990)";
      case "PLZ": return "波兰兹罗提 (1950–1995)";
      case "AON": return "安哥拉新宽扎 (1990–2000)";
      case "MWK": return "马拉维克瓦查";
      case "LUL": return "卢森堡金融法郎";
      case "AOR": return "安哥拉重新调整宽扎 (1995–1999)";
      case "BAD": return "波士尼亚-赫塞哥维纳第纳尔 (1992–1994)";
      case "MGA": return "马达加斯加阿里亚里";
      case "NIC": return "尼加拉瓜科多巴 (1988–1991)";
      case "FIM": return "芬兰马克";
      case "DEM": return "德国马克";
      case "MGF": return "马达加斯加法郎";
      case "BAM": return "波斯尼亚-黑塞哥维那可兑换马克";
      case "BAN": return "波士尼亚-赫塞哥维纳新第纳尔 (1994–1997)";
      case "EGP": return "埃及镑";
      case "SSP": return "南苏丹镑";
      case "BRC": return "巴西克鲁扎多 (1986–1989)";
      case "BRB": return "巴西新克鲁赛罗 (1967–1986)";
      case "BRE": return "巴西克鲁塞罗 (1990–1993)";
      case "NIO": return "尼加拉瓜科多巴";
      case "NZD": return "新西兰元";
      case "BRL": return "巴西雷亚尔";
      case "BRN": return "巴西新克鲁扎多 (1989–1990)";
      default: return "";
    }
  }

  protected String getCurrencyPluralName(String code, PluralCategory category) {
    switch (code) {
      case "ADP": {
        switch (category) {
          case OTHER:
              default: return "安道尔比塞塔";
        }
      }
      case "AED": {
        switch (category) {
          case OTHER:
              default: return "阿联酋迪拉姆";
        }
      }
      case "AFN": {
        switch (category) {
          case OTHER:
              default: return "阿富汗尼";
        }
      }
      case "ALK": {
        switch (category) {
          case OTHER:
              default: return "阿尔巴尼亚列克(1946–1965)";
        }
      }
      case "ALL": {
        switch (category) {
          case OTHER:
              default: return "阿尔巴尼亚列克";
        }
      }
      case "AMD": {
        switch (category) {
          case OTHER:
              default: return "亚美尼亚德拉姆";
        }
      }
      case "ANG": {
        switch (category) {
          case OTHER:
              default: return "荷属安的列斯盾";
        }
      }
      case "AOA": {
        switch (category) {
          case OTHER:
              default: return "安哥拉宽扎";
        }
      }
      case "AOK": {
        switch (category) {
          case OTHER:
              default: return "安哥拉宽扎 (1977–1990)";
        }
      }
      case "AON": {
        switch (category) {
          case OTHER:
              default: return "安哥拉新宽扎 (1990–2000)";
        }
      }
      case "AOR": {
        switch (category) {
          case OTHER:
              default: return "安哥拉重新调整宽扎 (1995–1999)";
        }
      }
      case "ARA": {
        switch (category) {
          case OTHER:
              default: return "阿根廷奥斯特拉尔";
        }
      }
      case "ARL": {
        switch (category) {
          case OTHER:
              default: return "阿根廷法定比索 (1970–1983)";
        }
      }
      case "ARM": {
        switch (category) {
          case OTHER:
              default: return "阿根廷比索 (1881–1970)";
        }
      }
      case "ARP": {
        switch (category) {
          case OTHER:
              default: return "阿根廷比索 (1983–1985)";
        }
      }
      case "ARS": {
        switch (category) {
          case OTHER:
              default: return "阿根廷比索";
        }
      }
      case "ATS": {
        switch (category) {
          case OTHER:
              default: return "奥地利先令";
        }
      }
      case "AUD": {
        switch (category) {
          case OTHER:
              default: return "澳大利亚元";
        }
      }
      case "AWG": {
        switch (category) {
          case OTHER:
              default: return "阿鲁巴基尔德元";
        }
      }
      case "AZM": {
        switch (category) {
          case OTHER:
              default: return "阿塞拜疆马纳特 (1993–2006)";
        }
      }
      case "AZN": {
        switch (category) {
          case OTHER:
              default: return "阿塞拜疆马纳特";
        }
      }
      case "BAD": {
        switch (category) {
          case OTHER:
              default: return "波士尼亚-赫塞哥维纳第纳尔 (1992–1994)";
        }
      }
      case "BAM": {
        switch (category) {
          case OTHER:
              default: return "波斯尼亚-黑塞哥维那可兑换马克";
        }
      }
      case "BAN": {
        switch (category) {
          case OTHER:
              default: return "波士尼亚-赫塞哥维纳新第纳尔 (1994–1997)";
        }
      }
      case "BBD": {
        switch (category) {
          case OTHER:
              default: return "巴巴多斯元";
        }
      }
      case "BDT": {
        switch (category) {
          case OTHER:
              default: return "孟加拉塔卡";
        }
      }
      case "BEC": {
        switch (category) {
          case OTHER:
              default: return "比利时法郎（可兑换）";
        }
      }
      case "BEF": {
        switch (category) {
          case OTHER:
              default: return "比利时法郎";
        }
      }
      case "BEL": {
        switch (category) {
          case OTHER:
              default: return "比利时法郎（金融）";
        }
      }
      case "BGL": {
        switch (category) {
          case OTHER:
              default: return "保加利亚硬列弗";
        }
      }
      case "BGM": {
        switch (category) {
          case OTHER:
              default: return "保加利亚社会党列弗";
        }
      }
      case "BGN": {
        switch (category) {
          case OTHER:
              default: return "保加利亚新列弗";
        }
      }
      case "BGO": {
        switch (category) {
          case OTHER:
              default: return "保加利亚列弗 (1879–1952)";
        }
      }
      case "BHD": {
        switch (category) {
          case OTHER:
              default: return "巴林第纳尔";
        }
      }
      case "BIF": {
        switch (category) {
          case OTHER:
              default: return "布隆迪法郎";
        }
      }
      case "BMD": {
        switch (category) {
          case OTHER:
              default: return "百慕大元";
        }
      }
      case "BND": {
        switch (category) {
          case OTHER:
              default: return "文莱元";
        }
      }
      case "BOB": {
        switch (category) {
          case OTHER:
              default: return "玻利维亚诺";
        }
      }
      case "BOL": {
        switch (category) {
          case OTHER:
              default: return "玻利维亚诺 (1863–1963)";
        }
      }
      case "BOP": {
        switch (category) {
          case OTHER:
              default: return "玻利维亚比索";
        }
      }
      case "BOV": {
        switch (category) {
          case OTHER:
              default: return "玻利维亚 Mvdol（资金）";
        }
      }
      case "BRB": {
        switch (category) {
          case OTHER:
              default: return "巴西新克鲁赛罗 (1967–1986)";
        }
      }
      case "BRC": {
        switch (category) {
          case OTHER:
              default: return "巴西克鲁扎多 (1986–1989)";
        }
      }
      case "BRE": {
        switch (category) {
          case OTHER:
              default: return "巴西克鲁塞罗 (1990–1993)";
        }
      }
      case "BRL": {
        switch (category) {
          case OTHER:
              default: return "巴西雷亚尔";
        }
      }
      case "BRN": {
        switch (category) {
          case OTHER:
              default: return "巴西新克鲁扎多 (1989–1990)";
        }
      }
      case "BRR": {
        switch (category) {
          case OTHER:
              default: return "巴西克鲁塞罗 (1993–1994)";
        }
      }
      case "BRZ": {
        switch (category) {
          case OTHER:
              default: return "巴西克鲁塞罗 (1942–1967)";
        }
      }
      case "BSD": {
        switch (category) {
          case OTHER:
              default: return "巴哈马元";
        }
      }
      case "BTN": {
        switch (category) {
          case OTHER:
              default: return "不丹努尔特鲁姆";
        }
      }
      case "BWP": {
        switch (category) {
          case OTHER:
              default: return "博茨瓦纳普拉";
        }
      }
      case "BYB": {
        switch (category) {
          case OTHER:
              default: return "白俄罗斯新卢布 (1994–1999)";
        }
      }
      case "BYN": {
        switch (category) {
          case OTHER:
              default: return "白俄罗斯卢布";
        }
      }
      case "BYR": {
        switch (category) {
          case OTHER:
              default: return "白俄罗斯卢布 (2000–2016)";
        }
      }
      case "BZD": {
        switch (category) {
          case OTHER:
              default: return "伯利兹元";
        }
      }
      case "CAD": {
        switch (category) {
          case OTHER:
              default: return "加拿大元";
        }
      }
      case "CDF": {
        switch (category) {
          case OTHER:
              default: return "刚果法郎";
        }
      }
      case "CHE": {
        switch (category) {
          case OTHER:
              default: return "欧元 (WIR)";
        }
      }
      case "CHF": {
        switch (category) {
          case OTHER:
              default: return "瑞士法郎";
        }
      }
      case "CHW": {
        switch (category) {
          case OTHER:
              default: return "法郎 (WIR)";
        }
      }
      case "CLE": {
        switch (category) {
          case OTHER:
              default: return "智利埃斯库多";
        }
      }
      case "CLF": {
        switch (category) {
          case OTHER:
              default: return "智利（资金）";
        }
      }
      case "CLP": {
        switch (category) {
          case OTHER:
              default: return "智利比索";
        }
      }
      case "CNY": {
        switch (category) {
          case OTHER:
              default: return "人民币";
        }
      }
      case "COP": {
        switch (category) {
          case OTHER:
              default: return "哥伦比亚比索";
        }
      }
      case "COU": {
        switch (category) {
          case OTHER:
              default: return "哥伦比亚币";
        }
      }
      case "CRC": {
        switch (category) {
          case OTHER:
              default: return "哥斯达黎加科朗";
        }
      }
      case "CSD": {
        switch (category) {
          case OTHER:
              default: return "旧塞尔维亚第纳尔";
        }
      }
      case "CSK": {
        switch (category) {
          case OTHER:
              default: return "捷克硬克朗";
        }
      }
      case "CUC": {
        switch (category) {
          case OTHER:
              default: return "古巴可兑换比索";
        }
      }
      case "CUP": {
        switch (category) {
          case OTHER:
              default: return "古巴比索";
        }
      }
      case "CVE": {
        switch (category) {
          case OTHER:
              default: return "佛得角埃斯库多";
        }
      }
      case "CYP": {
        switch (category) {
          case OTHER:
              default: return "塞浦路斯镑";
        }
      }
      case "CZK": {
        switch (category) {
          case OTHER:
              default: return "捷克克朗";
        }
      }
      case "DDM": {
        switch (category) {
          case OTHER:
              default: return "东德奥斯特马克";
        }
      }
      case "DEM": {
        switch (category) {
          case OTHER:
              default: return "德国马克";
        }
      }
      case "DJF": {
        switch (category) {
          case OTHER:
              default: return "吉布提法郎";
        }
      }
      case "DKK": {
        switch (category) {
          case OTHER:
              default: return "丹麦克朗";
        }
      }
      case "DOP": {
        switch (category) {
          case OTHER:
              default: return "多米尼加比索";
        }
      }
      case "DZD": {
        switch (category) {
          case OTHER:
              default: return "阿尔及利亚第纳尔";
        }
      }
      case "ECS": {
        switch (category) {
          case OTHER:
              default: return "厄瓜多尔苏克雷";
        }
      }
      case "ECV": {
        switch (category) {
          case OTHER:
              default: return "厄瓜多尔 (UVC)";
        }
      }
      case "EEK": {
        switch (category) {
          case OTHER:
              default: return "爱沙尼亚克朗";
        }
      }
      case "EGP": {
        switch (category) {
          case OTHER:
              default: return "埃及镑";
        }
      }
      case "ERN": {
        switch (category) {
          case OTHER:
              default: return "厄立特里亚纳克法";
        }
      }
      case "ESA": {
        switch (category) {
          case OTHER:
              default: return "西班牙比塞塔（帐户 A）";
        }
      }
      case "ESB": {
        switch (category) {
          case OTHER:
              default: return "西班牙比塞塔（兑换帐户）";
        }
      }
      case "ESP": {
        switch (category) {
          case OTHER:
              default: return "西班牙比塞塔";
        }
      }
      case "ETB": {
        switch (category) {
          case OTHER:
              default: return "埃塞俄比亚比尔";
        }
      }
      case "EUR": {
        switch (category) {
          case OTHER:
              default: return "欧元";
        }
      }
      case "FIM": {
        switch (category) {
          case OTHER:
              default: return "芬兰马克";
        }
      }
      case "FJD": {
        switch (category) {
          case OTHER:
              default: return "斐济元";
        }
      }
      case "FKP": {
        switch (category) {
          case OTHER:
              default: return "福克兰群岛镑";
        }
      }
      case "FRF": {
        switch (category) {
          case OTHER:
              default: return "法国法郎";
        }
      }
      case "GBP": {
        switch (category) {
          case OTHER:
              default: return "英镑";
        }
      }
      case "GEK": {
        switch (category) {
          case OTHER:
              default: return "乔治亚库蓬拉瑞特";
        }
      }
      case "GEL": {
        switch (category) {
          case OTHER:
              default: return "格鲁吉亚拉里";
        }
      }
      case "GHC": {
        switch (category) {
          case OTHER:
              default: return "加纳塞第";
        }
      }
      case "GHS": {
        switch (category) {
          case OTHER:
              default: return "加纳塞地";
        }
      }
      case "GIP": {
        switch (category) {
          case OTHER:
              default: return "直布罗陀镑";
        }
      }
      case "GMD": {
        switch (category) {
          case OTHER:
              default: return "冈比亚达拉西";
        }
      }
      case "GNF": {
        switch (category) {
          case OTHER:
              default: return "几内亚法郎";
        }
      }
      case "GNS": {
        switch (category) {
          case OTHER:
              default: return "几内亚西里";
        }
      }
      case "GQE": {
        switch (category) {
          case OTHER:
              default: return "赤道几内亚埃奎勒";
        }
      }
      case "GRD": {
        switch (category) {
          case OTHER:
              default: return "希腊德拉克马";
        }
      }
      case "GTQ": {
        switch (category) {
          case OTHER:
              default: return "危地马拉格查尔";
        }
      }
      case "GWE": {
        switch (category) {
          case OTHER:
              default: return "葡萄牙几内亚埃斯库多";
        }
      }
      case "GWP": {
        switch (category) {
          case OTHER:
              default: return "几内亚比绍比索";
        }
      }
      case "GYD": {
        switch (category) {
          case OTHER:
              default: return "圭亚那元";
        }
      }
      case "HKD": {
        switch (category) {
          case OTHER:
              default: return "港元";
        }
      }
      case "HNL": {
        switch (category) {
          case OTHER:
              default: return "洪都拉斯伦皮拉";
        }
      }
      case "HRD": {
        switch (category) {
          case OTHER:
              default: return "克罗地亚第纳尔";
        }
      }
      case "HRK": {
        switch (category) {
          case OTHER:
              default: return "克罗地亚库纳";
        }
      }
      case "HTG": {
        switch (category) {
          case OTHER:
              default: return "海地古德";
        }
      }
      case "HUF": {
        switch (category) {
          case OTHER:
              default: return "匈牙利福林";
        }
      }
      case "IDR": {
        switch (category) {
          case OTHER:
              default: return "印度尼西亚盾";
        }
      }
      case "IEP": {
        switch (category) {
          case OTHER:
              default: return "爱尔兰镑";
        }
      }
      case "ILP": {
        switch (category) {
          case OTHER:
              default: return "以色列镑";
        }
      }
      case "ILR": {
        switch (category) {
          case OTHER:
              default: return "以色列谢克尔(1980–1985)";
        }
      }
      case "ILS": {
        switch (category) {
          case OTHER:
              default: return "以色列新谢克尔";
        }
      }
      case "INR": {
        switch (category) {
          case OTHER:
              default: return "印度卢比";
        }
      }
      case "IQD": {
        switch (category) {
          case OTHER:
              default: return "伊拉克第纳尔";
        }
      }
      case "IRR": {
        switch (category) {
          case OTHER:
              default: return "伊朗里亚尔";
        }
      }
      case "ISJ": {
        switch (category) {
          case OTHER:
              default: return "冰岛克朗(1918–1981)";
        }
      }
      case "ISK": {
        switch (category) {
          case OTHER:
              default: return "冰岛克朗";
        }
      }
      case "ITL": {
        switch (category) {
          case OTHER:
              default: return "意大利里拉";
        }
      }
      case "JMD": {
        switch (category) {
          case OTHER:
              default: return "牙买加元";
        }
      }
      case "JOD": {
        switch (category) {
          case OTHER:
              default: return "约旦第纳尔";
        }
      }
      case "JPY": {
        switch (category) {
          case OTHER:
              default: return "日元";
        }
      }
      case "KES": {
        switch (category) {
          case OTHER:
              default: return "肯尼亚先令";
        }
      }
      case "KGS": {
        switch (category) {
          case OTHER:
              default: return "吉尔吉斯斯坦索姆";
        }
      }
      case "KHR": {
        switch (category) {
          case OTHER:
              default: return "柬埔寨瑞尔";
        }
      }
      case "KMF": {
        switch (category) {
          case OTHER:
              default: return "科摩罗法郎";
        }
      }
      case "KPW": {
        switch (category) {
          case OTHER:
              default: return "朝鲜元";
        }
      }
      case "KRW": {
        switch (category) {
          case OTHER:
              default: return "韩元";
        }
      }
      case "KWD": {
        switch (category) {
          case OTHER:
              default: return "科威特第纳尔";
        }
      }
      case "KYD": {
        switch (category) {
          case OTHER:
              default: return "开曼元";
        }
      }
      case "KZT": {
        switch (category) {
          case OTHER:
              default: return "哈萨克斯坦坚戈";
        }
      }
      case "LAK": {
        switch (category) {
          case OTHER:
              default: return "老挝基普";
        }
      }
      case "LBP": {
        switch (category) {
          case OTHER:
              default: return "黎巴嫩镑";
        }
      }
      case "LKR": {
        switch (category) {
          case OTHER:
              default: return "斯里兰卡卢比";
        }
      }
      case "LRD": {
        switch (category) {
          case OTHER:
              default: return "利比里亚元";
        }
      }
      case "LSL": {
        switch (category) {
          case OTHER:
              default: return "莱索托洛蒂";
        }
      }
      case "LTL": {
        switch (category) {
          case OTHER:
              default: return "立陶宛立特";
        }
      }
      case "LTT": {
        switch (category) {
          case OTHER:
              default: return "立陶宛塔咯呐司";
        }
      }
      case "LUC": {
        switch (category) {
          case OTHER:
              default: return "卢森堡可兑换法郎";
        }
      }
      case "LUF": {
        switch (category) {
          case OTHER:
              default: return "卢森堡法郎";
        }
      }
      case "LUL": {
        switch (category) {
          case OTHER:
              default: return "卢森堡金融法郎";
        }
      }
      case "LVL": {
        switch (category) {
          case OTHER:
              default: return "拉脱维亚拉特";
        }
      }
      case "LVR": {
        switch (category) {
          case OTHER:
              default: return "拉脱维亚卢布";
        }
      }
      case "LYD": {
        switch (category) {
          case OTHER:
              default: return "利比亚第纳尔";
        }
      }
      case "MAD": {
        switch (category) {
          case OTHER:
              default: return "摩洛哥迪拉姆";
        }
      }
      case "MAF": {
        switch (category) {
          case OTHER:
              default: return "摩洛哥法郎";
        }
      }
      case "MCF": {
        switch (category) {
          case OTHER:
              default: return "摩纳哥法郎";
        }
      }
      case "MDC": {
        switch (category) {
          case OTHER:
              default: return "摩尔多瓦库邦";
        }
      }
      case "MDL": {
        switch (category) {
          case OTHER:
              default: return "摩尔多瓦列伊";
        }
      }
      case "MGA": {
        switch (category) {
          case OTHER:
              default: return "马达加斯加阿里亚里";
        }
      }
      case "MGF": {
        switch (category) {
          case OTHER:
              default: return "马达加斯加法郎";
        }
      }
      case "MKD": {
        switch (category) {
          case OTHER:
              default: return "马其顿第纳尔";
        }
      }
      case "MKN": {
        switch (category) {
          case OTHER:
              default: return "马其顿第纳尔 (1992–1993)";
        }
      }
      case "MLF": {
        switch (category) {
          case OTHER:
              default: return "马里法郎";
        }
      }
      case "MMK": {
        switch (category) {
          case OTHER:
              default: return "缅甸元";
        }
      }
      case "MNT": {
        switch (category) {
          case OTHER:
              default: return "蒙古图格里克";
        }
      }
      case "MOP": {
        switch (category) {
          case OTHER:
              default: return "澳门元";
        }
      }
      case "MRO": {
        switch (category) {
          case OTHER:
              default: return "毛里塔尼亚乌吉亚";
        }
      }
      case "MTL": {
        switch (category) {
          case OTHER:
              default: return "马耳他里拉";
        }
      }
      case "MTP": {
        switch (category) {
          case OTHER:
              default: return "马耳他镑";
        }
      }
      case "MUR": {
        switch (category) {
          case OTHER:
              default: return "毛里求斯卢比";
        }
      }
      case "MVP": {
        switch (category) {
          case OTHER:
              default: return "马尔代夫卢比(1947–1981)";
        }
      }
      case "MVR": {
        switch (category) {
          case OTHER:
              default: return "马尔代夫卢菲亚";
        }
      }
      case "MWK": {
        switch (category) {
          case OTHER:
              default: return "马拉维克瓦查";
        }
      }
      case "MXN": {
        switch (category) {
          case OTHER:
              default: return "墨西哥比索";
        }
      }
      case "MXP": {
        switch (category) {
          case OTHER:
              default: return "墨西哥银比索 (1861–1992)";
        }
      }
      case "MXV": {
        switch (category) {
          case OTHER:
              default: return "墨西哥（资金）";
        }
      }
      case "MYR": {
        switch (category) {
          case OTHER:
              default: return "马来西亚林吉特";
        }
      }
      case "MZE": {
        switch (category) {
          case OTHER:
              default: return "莫桑比克埃斯库多";
        }
      }
      case "MZM": {
        switch (category) {
          case OTHER:
              default: return "旧莫桑比克美提卡";
        }
      }
      case "MZN": {
        switch (category) {
          case OTHER:
              default: return "莫桑比克美提卡";
        }
      }
      case "NAD": {
        switch (category) {
          case OTHER:
              default: return "纳米比亚元";
        }
      }
      case "NGN": {
        switch (category) {
          case OTHER:
              default: return "尼日利亚奈拉";
        }
      }
      case "NIC": {
        switch (category) {
          case OTHER:
              default: return "尼加拉瓜科多巴 (1988–1991)";
        }
      }
      case "NIO": {
        switch (category) {
          case OTHER:
              default: return "尼加拉瓜金科多巴";
        }
      }
      case "NLG": {
        switch (category) {
          case OTHER:
              default: return "荷兰盾";
        }
      }
      case "NOK": {
        switch (category) {
          case OTHER:
              default: return "挪威克朗";
        }
      }
      case "NPR": {
        switch (category) {
          case OTHER:
              default: return "尼泊尔卢比";
        }
      }
      case "NZD": {
        switch (category) {
          case OTHER:
              default: return "新西兰元";
        }
      }
      case "OMR": {
        switch (category) {
          case OTHER:
              default: return "阿曼里亚尔";
        }
      }
      case "PAB": {
        switch (category) {
          case OTHER:
              default: return "巴拿马巴波亚";
        }
      }
      case "PEI": {
        switch (category) {
          case OTHER:
              default: return "秘鲁印第";
        }
      }
      case "PEN": {
        switch (category) {
          case OTHER:
              default: return "秘鲁索尔";
        }
      }
      case "PES": {
        switch (category) {
          case OTHER:
              default: return "秘鲁索尔 (1863–1965)";
        }
      }
      case "PGK": {
        switch (category) {
          case OTHER:
              default: return "巴布亚新几内亚基那";
        }
      }
      case "PHP": {
        switch (category) {
          case OTHER:
              default: return "菲律宾比索";
        }
      }
      case "PKR": {
        switch (category) {
          case OTHER:
              default: return "巴基斯坦卢比";
        }
      }
      case "PLN": {
        switch (category) {
          case OTHER:
              default: return "波兰兹罗提";
        }
      }
      case "PLZ": {
        switch (category) {
          case OTHER:
              default: return "波兰兹罗提 (1950–1995)";
        }
      }
      case "PTE": {
        switch (category) {
          case OTHER:
              default: return "葡萄牙埃斯库多";
        }
      }
      case "PYG": {
        switch (category) {
          case OTHER:
              default: return "巴拉圭瓜拉尼";
        }
      }
      case "QAR": {
        switch (category) {
          case OTHER:
              default: return "卡塔尔里亚尔";
        }
      }
      case "RHD": {
        switch (category) {
          case OTHER:
              default: return "罗得西亚元";
        }
      }
      case "ROL": {
        switch (category) {
          case OTHER:
              default: return "旧罗马尼亚列伊";
        }
      }
      case "RON": {
        switch (category) {
          case OTHER:
              default: return "罗马尼亚列伊";
        }
      }
      case "RSD": {
        switch (category) {
          case OTHER:
              default: return "塞尔维亚第纳尔";
        }
      }
      case "RUB": {
        switch (category) {
          case OTHER:
              default: return "俄罗斯卢布";
        }
      }
      case "RUR": {
        switch (category) {
          case OTHER:
              default: return "俄国卢布 (1991–1998)";
        }
      }
      case "RWF": {
        switch (category) {
          case OTHER:
              default: return "卢旺达法郎";
        }
      }
      case "SAR": {
        switch (category) {
          case OTHER:
              default: return "沙特里亚尔";
        }
      }
      case "SBD": {
        switch (category) {
          case OTHER:
              default: return "所罗门群岛元";
        }
      }
      case "SCR": {
        switch (category) {
          case OTHER:
              default: return "塞舌尔卢比";
        }
      }
      case "SDD": {
        switch (category) {
          case OTHER:
              default: return "苏丹第纳尔 (1992–2007)";
        }
      }
      case "SDG": {
        switch (category) {
          case OTHER:
              default: return "苏丹镑";
        }
      }
      case "SDP": {
        switch (category) {
          case OTHER:
              default: return "旧苏丹镑";
        }
      }
      case "SEK": {
        switch (category) {
          case OTHER:
              default: return "瑞典克朗";
        }
      }
      case "SGD": {
        switch (category) {
          case OTHER:
              default: return "新加坡元";
        }
      }
      case "SHP": {
        switch (category) {
          case OTHER:
              default: return "圣赫勒拿群岛磅";
        }
      }
      case "SIT": {
        switch (category) {
          case OTHER:
              default: return "斯洛文尼亚托拉尔";
        }
      }
      case "SKK": {
        switch (category) {
          case OTHER:
              default: return "斯洛伐克克朗";
        }
      }
      case "SLL": {
        switch (category) {
          case OTHER:
              default: return "塞拉利昂利昂";
        }
      }
      case "SOS": {
        switch (category) {
          case OTHER:
              default: return "索马里先令";
        }
      }
      case "SRD": {
        switch (category) {
          case OTHER:
              default: return "苏里南元";
        }
      }
      case "SRG": {
        switch (category) {
          case OTHER:
              default: return "苏里南盾";
        }
      }
      case "SSP": {
        switch (category) {
          case OTHER:
              default: return "南苏丹镑";
        }
      }
      case "STD": {
        switch (category) {
          case OTHER:
              default: return "圣多美和普林西比多布拉";
        }
      }
      case "SUR": {
        switch (category) {
          case OTHER:
              default: return "苏联卢布";
        }
      }
      case "SVC": {
        switch (category) {
          case OTHER:
              default: return "萨尔瓦多科朗";
        }
      }
      case "SYP": {
        switch (category) {
          case OTHER:
              default: return "叙利亚镑";
        }
      }
      case "SZL": {
        switch (category) {
          case OTHER:
              default: return "斯威士兰里兰吉尼";
        }
      }
      case "THB": {
        switch (category) {
          case OTHER:
              default: return "泰铢";
        }
      }
      case "TJR": {
        switch (category) {
          case OTHER:
              default: return "塔吉克斯坦卢布";
        }
      }
      case "TJS": {
        switch (category) {
          case OTHER:
              default: return "塔吉克斯坦索莫尼";
        }
      }
      case "TMM": {
        switch (category) {
          case OTHER:
              default: return "土库曼斯坦马纳特 (1993–2009)";
        }
      }
      case "TMT": {
        switch (category) {
          case OTHER:
              default: return "土库曼斯坦马纳特";
        }
      }
      case "TND": {
        switch (category) {
          case OTHER:
              default: return "突尼斯第纳尔";
        }
      }
      case "TOP": {
        switch (category) {
          case OTHER:
              default: return "汤加潘加";
        }
      }
      case "TRL": {
        switch (category) {
          case OTHER:
              default: return "土耳其里拉 (1922–2005)";
        }
      }
      case "TRY": {
        switch (category) {
          case OTHER:
              default: return "土耳其里拉";
        }
      }
      case "TTD": {
        switch (category) {
          case OTHER:
              default: return "特立尼达和多巴哥元";
        }
      }
      case "TWD": {
        switch (category) {
          case OTHER:
              default: return "新台币";
        }
      }
      case "TZS": {
        switch (category) {
          case OTHER:
              default: return "坦桑尼亚先令";
        }
      }
      case "UAH": {
        switch (category) {
          case OTHER:
              default: return "乌克兰格里夫纳";
        }
      }
      case "UAK": {
        switch (category) {
          case OTHER:
              default: return "乌克兰币";
        }
      }
      case "UGS": {
        switch (category) {
          case OTHER:
              default: return "乌干达先令 (1966–1987)";
        }
      }
      case "UGX": {
        switch (category) {
          case OTHER:
              default: return "乌干达先令";
        }
      }
      case "USD": {
        switch (category) {
          case OTHER:
              default: return "美元";
        }
      }
      case "USN": {
        switch (category) {
          case OTHER:
              default: return "美元（次日）";
        }
      }
      case "USS": {
        switch (category) {
          case OTHER:
              default: return "美元（当日）";
        }
      }
      case "UYI": {
        switch (category) {
          case OTHER:
              default: return "乌拉圭比索（索引单位）";
        }
      }
      case "UYP": {
        switch (category) {
          case OTHER:
              default: return "乌拉圭比索 (1975–1993)";
        }
      }
      case "UYU": {
        switch (category) {
          case OTHER:
              default: return "乌拉圭比索";
        }
      }
      case "UZS": {
        switch (category) {
          case OTHER:
              default: return "乌兹别克斯坦苏姆";
        }
      }
      case "VEB": {
        switch (category) {
          case OTHER:
              default: return "委内瑞拉玻利瓦尔 (1871–2008)";
        }
      }
      case "VEF": {
        switch (category) {
          case OTHER:
              default: return "委内瑞拉玻利瓦尔";
        }
      }
      case "VND": {
        switch (category) {
          case OTHER:
              default: return "越南盾";
        }
      }
      case "VUV": {
        switch (category) {
          case OTHER:
              default: return "瓦努阿图瓦图";
        }
      }
      case "WST": {
        switch (category) {
          case OTHER:
              default: return "萨摩亚塔拉";
        }
      }
      case "XAF": {
        switch (category) {
          case OTHER:
              default: return "中非法郎";
        }
      }
      case "XCD": {
        switch (category) {
          case OTHER:
              default: return "东加勒比元";
        }
      }
      case "XEU": {
        switch (category) {
          case OTHER:
              default: return "欧洲货币单位";
        }
      }
      case "XOF": {
        switch (category) {
          case OTHER:
              default: return "西非法郎";
        }
      }
      case "XPF": {
        switch (category) {
          case OTHER:
              default: return "太平洋法郎";
        }
      }
      case "XXX": {
        switch (category) {
          case OTHER:
              default: return "（未知货币）";
        }
      }
      case "YDD": {
        switch (category) {
          case OTHER:
              default: return "也门第纳尔";
        }
      }
      case "YER": {
        switch (category) {
          case OTHER:
              default: return "也门里亚尔";
        }
      }
      case "YUD": {
        switch (category) {
          case OTHER:
              default: return "南斯拉夫硬第纳尔 (1966–1990)";
        }
      }
      case "YUM": {
        switch (category) {
          case OTHER:
              default: return "南斯拉夫新第纳尔 (1994–2002)";
        }
      }
      case "YUN": {
        switch (category) {
          case OTHER:
              default: return "南斯拉夫可兑换第纳尔 (1990–1992)";
        }
      }
      case "YUR": {
        switch (category) {
          case OTHER:
              default: return "南斯拉夫改良第纳尔 (1992–1993)";
        }
      }
      case "ZAL": {
        switch (category) {
          case OTHER:
              default: return "南非兰特 (金融)";
        }
      }
      case "ZAR": {
        switch (category) {
          case OTHER:
              default: return "南非兰特";
        }
      }
      case "ZMK": {
        switch (category) {
          case OTHER:
              default: return "赞比亚克瓦查 (1968–2012)";
        }
      }
      case "ZMW": {
        switch (category) {
          case OTHER:
              default: return "赞比亚克瓦查";
        }
      }
      case "ZRN": {
        switch (category) {
          case OTHER:
              default: return "新扎伊尔 (1993–1998)";
        }
      }
      case "ZRZ": {
        switch (category) {
          case OTHER:
              default: return "扎伊尔 (1971–1993)";
        }
      }
      case "ZWD": {
        switch (category) {
          case OTHER:
              default: return "津巴布韦元 (1980–2008)";
        }
      }
      case "ZWL": {
        switch (category) {
          case OTHER:
              default: return "津巴布韦元 (2009)";
        }
      }
      case "ZWR": {
        switch (category) {
          case OTHER:
              default: return "津巴布韦元 (2008)";
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
