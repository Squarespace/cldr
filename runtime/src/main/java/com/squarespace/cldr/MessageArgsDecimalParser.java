package com.squarespace.cldr;

import com.squarespace.cldr.numbers.DecimalFormatOptions;
import com.squarespace.cldr.numbers.DecimalFormatStyle;
import com.squarespace.cldr.numbers.NumberFormatMode;
import com.squarespace.cldr.numbers.NumberFormatOptions;
import com.squarespace.cldr.numbers.NumberRoundMode;


class MessageArgsDecimalParser implements MessageArgsParser {

  private static final String TRUE = "true";
  
  // Arbitrary value just to have an upper limit.
  private static final int CLAMP_MAX = 50;

  private final DecimalFormatOptions opts = new DecimalFormatOptions();
  
  public DecimalFormatOptions options() {
    return opts;
  }
  
  public void reset() {
    opts.reset();
  }
  
  @Override
  public void set(String key, String value) {
    if (key.equals("style")) {
      switch (value) {
        case "percent":
          opts.setStyle(DecimalFormatStyle.PERCENT);
          break;

        case "permille":
          opts.setStyle(DecimalFormatStyle.PERMILLE);
          break;

        case "short":
          opts.setStyle(DecimalFormatStyle.SHORT);
          break;

        case "long":
          opts.setStyle(DecimalFormatStyle.LONG);
          break;

        case "standard":
        case "decimal":
          opts.setStyle(DecimalFormatStyle.DECIMAL);
          break;

        default:
          break;
      }
      return;
    }
    
    setNumberOption(key, value, opts);
  }
  
  protected static void setNumberOption(String arg, String value, NumberFormatOptions<?> opts) {
    switch (arg) {
      case "group":
      case "grouping":
        opts.setGrouping(isEmpty(value) || TRUE.equalsIgnoreCase(value));
        break;

      case "no-group":
      case "no-grouping":
        opts.setGrouping(isEmpty(value) || TRUE.equalsIgnoreCase(value));
        break;

      case "mode":
        switch (value) {
          case "significant":
            opts.setFormatMode(NumberFormatMode.SIGNIFICANT);
            break;

          case "significant-maxfrac":
          case "significant-maxFrac":
            opts.setFormatMode(NumberFormatMode.SIGNIFICANT_MAXFRAC);
            break;

          case "default":
          case "fractions":
            opts.setFormatMode(NumberFormatMode.DEFAULT);
            break;

          default:
            break;
        }
        break;

      case "round":
      case "rounding":
        switch (value) {
          case "ceil":
            opts.setRoundMode(NumberRoundMode.CEIL);
            break;

          case "truncate": opts.setRoundMode(NumberRoundMode.TRUNCATE); break;

          case "floor":
            opts.setRoundMode(NumberRoundMode.FLOOR);
            break;

          case "round":
          case "default":
          case "half-even":
            opts.setRoundMode(NumberRoundMode.ROUND);

          default:
            break;
        }
        break;

      case "minint":
      case "minInt":
        opts.setMinimumIntegerDigits(clamp(toInt(value), 0, CLAMP_MAX));
        break;

      case "maxfrac":
      case "maxFrac":
        opts.setMaximumFractionDigits(clamp(toInt(value), 0, CLAMP_MAX));
        break;

      case "minfrac":
      case "minFrac":
        opts.setMinimumFractionDigits(clamp(toInt(value), 0, CLAMP_MAX));
        break;

      case "maxsig":
      case "maxSig":
        opts.setMaximumSignificantDigits(clamp(toInt(value), 1, CLAMP_MAX));
        break;

      case "minsig":
      case "minSig":
        opts.setMinimumSignificantDigits(clamp(toInt(value), 1, CLAMP_MAX));
        break;

      default:
        break;
    }
  }

  private static int toInt(String v) {
    return v == null ? 1 : (int) MessageFormat.toLong(v, 0, v.length());
  }

  private static int clamp(int value, int min, int max) {
    return value < min ? min : (value > max ? max : value);
  }

  protected static boolean isEmpty(String v) {
    return v == null || v.equals("");
  }

}
