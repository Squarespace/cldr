package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.NumberFormattingUtils.integerDigits;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.CLDRLocale;
import com.squarespace.cldr.PluralRules;
import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPattern.Node;
import com.squarespace.cldr.numbers.NumberPattern.Symbol;
import com.squarespace.cldr.numbers.NumberPattern.Text;
import com.squarespace.cldr.plurals.PluralCategory;


/**
 * Base class for localized decimal number formatters.
 */
public abstract class NumberFormatterBase implements NumberFormatter {

  protected static final PluralRules PLURAL_RULES = CLDR.get().getPluralRules();
  
  protected static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
  protected static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
  protected static final NumberPattern[] PLURAL = patterns("#,##0.##", "-#,##0.##");
  protected static final DecimalFormatOptions PLURAL_OPTS = buildPluralOpts();
  protected static final NumberFormatterParams GENERIC_PARAMS = new NumberFormatterParams();
  private static final char NBSP = '\u00a0';

  protected final CLDRLocale locale;
  protected final NumberFormatterParams params;
  protected final NumberPattern[] decimalStandard;
  protected final NumberPattern[] percentStandard;
  protected final NumberPattern[] currencyStandard;
  protected final NumberPattern[] currencyAccounting;

  protected NumberFormatterBase(
      CLDRLocale locale,
      NumberFormatterParams params,
      NumberPattern[] decimalStandard,
      NumberPattern[] percentStandard,
      NumberPattern[] currencyStandard,
      NumberPattern[] currencyAccounting) {

    this.locale = locale;
    this.params = params;
    this.decimalStandard = decimalStandard;
    this.percentStandard = percentStandard;
    this.currencyStandard = currencyStandard;
    this.currencyAccounting = currencyAccounting;
  }
  
  protected abstract int getPowerOfTen_DECIMAL_SHORT(int digits);
  protected abstract int getPowerOfTen_DECIMAL_LONG(int digits);
  protected abstract int getPowerOfTen_CURRENCY_SHORT(int digits);
  protected abstract NumberPattern[] getPattern_DECIMAL_SHORT(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_DECIMAL_LONG(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_CURRENCY_SHORT(int digits, PluralCategory category);
  protected abstract String getCurrencyPluralName(String code, PluralCategory category);
  protected abstract void wrapUnits(PluralCategory category, DigitBuffer dbuf, String unit, StringBuilder dest);

  public abstract String getCurrencySymbol(String code);
  public abstract String getNarrowCurrencySymbol(String code);
  public abstract String getCurrencyDisplayName(String code);
  public abstract int getCurrencyDigits(String code);
  
  /**
   * Return the locale associated with this number formatter.
   */
  public CLDRLocale locale() {
    return locale;
  }
  
  /**
   * Format the number into the buffer using the given options.
   */
  public void formatDecimal(BigDecimal n, StringBuilder destination, DecimalFormatOptions options) {
    DigitBuffer dbuf = new DigitBuffer();
    DecimalFormatStyle style = options.style();
    boolean grouping = orDefault(options.grouping(), false);
    NumberFormatMode formatMode = orDefault(options.formatMode(), NumberFormatMode.DEFAULT);
    
    switch (style) {
      case DECIMAL:
      {
        NumberPattern pattern = select(n, decimalStandard);
        DigitBuffer number = new DigitBuffer();
        setup(params, pattern, n, number, options, null, formatMode, grouping, -1, -1, -1);
        format(pattern, number, dbuf, null, null);
        dbuf.appendTo(destination);
        break;
      }

      case LONG:
      case SHORT:
      {
        // Set the plural category for the number
        NumberOperands operands = new NumberOperands(n.toPlainString());
        PluralCategory category = PLURAL_RULES.evalCardinal(locale.language(), operands);

        int digits = integerDigits(n);
        int divisor;
        NumberPattern pattern;

        // Select the divisor and pattern based on the number of integer digits and plural category.
        if (style == DecimalFormatStyle.LONG) {
          divisor = getPowerOfTen_DECIMAL_LONG(digits);
          pattern = select(n, getPattern_DECIMAL_LONG(digits, category));
        } else {
          divisor = getPowerOfTen_DECIMAL_SHORT(digits);
          pattern = select(n, getPattern_DECIMAL_SHORT(digits, category));
        }

        if (divisor != 0) {
          //
          // Check if we might need to select the next pattern up due to rounding.
          // This handles cases where "999,999" formats to "1 M" instead of "1,000 K"
          // Check if this number rounds up to exceed minimum integer digits
          //
          RoundingMode roundingMode = options.roundMode.toRoundingMode();
          BigDecimal nn = n.movePointLeft(divisor);
          
          // TODO: Peek at the formatting mode to determine whether this number will round.
          nn = nn.setScale(0, roundingMode);
          
          int nnDigits = integerDigits(nn);
          if (nnDigits > pattern.format.minimumIntegerDigits() && digits < 15) {
            // Select pattern that supports 1 more digit than the original number,
            // to account for rounding.
            digits++;
            if (style == DecimalFormatStyle.LONG) {
              divisor = getPowerOfTen_DECIMAL_LONG(digits);
              pattern = select(n, getPattern_DECIMAL_LONG(digits, category));
            } else {
              divisor = getPowerOfTen_DECIMAL_SHORT(digits);
              pattern = select(n, getPattern_DECIMAL_SHORT(digits, category));
            }
          }
          
          n = n.movePointLeft(divisor);
        }

        // For compact forms by default we want to show a certain number of significant digits.
        // So "1200" becomes "1.2 K" instead of just "1 K". This can be overridden by the 
        // NumberFormatOptions as needed.
        formatMode = orDefault(options.formatMode(), NumberFormatMode.SIGNIFICANT);
        int intDigits = integerDigits(n);
        int minIntDigits = pattern.format().minimumIntegerDigits();
        int maxSigDigits = Math.max(intDigits + 1, Math.min(minIntDigits + 1, 3));
        int minSigDigits = minIntDigits;
        
        // Format the number according to the pattern
        DigitBuffer number = new DigitBuffer();
        setup(params, pattern, n, number, options, null, formatMode, grouping, -1, maxSigDigits, minSigDigits);
        format(pattern, number, dbuf, null, null);
        dbuf.appendTo(destination);
        break;
      }

      case PERCENT:
      case PERMILLE:
      {
        n = n.multiply(style == DecimalFormatStyle.PERCENT ? ONE_HUNDRED : ONE_THOUSAND);
        String symbol = style == DecimalFormatStyle.PERCENT ? params.percent : params.perMille;
        NumberPattern pattern = select(n, percentStandard);
        DigitBuffer number = new DigitBuffer();
        setup(params, pattern, n, number, options, null, formatMode, grouping, -1, -1, -1);
        format(pattern, number, dbuf, null, symbol);
        dbuf.appendTo(destination);
        break;
      }
    }
  }

  /**
   * Format the currency into the buffer using the given options.
   */
  public void formatCurrency(
      BigDecimal n, String currencyCode, StringBuilder destination, CurrencyFormatOptions options) {
    
    DigitBuffer dbuf = new DigitBuffer();
    CurrencyFormatStyle style = options.style();
    NumberFormatMode formatMode = orDefault(options.formatMode(), NumberFormatMode.DEFAULT);
    boolean grouping = orDefault(options.grouping(), true);
    int currencyDigits = getCurrencyDigits(currencyCode);

    switch (style) {
      case SYMBOL:
      case ACCOUNTING:
      {
        NumberPattern[] patterns = style == CurrencyFormatStyle.SYMBOL ? currencyStandard : currencyAccounting;
        NumberPattern pattern = select(n, patterns);
        String symbol = options.symbolWidth() == CurrencySymbolWidth.NARROW 
            ? getNarrowCurrencySymbol(currencyCode) : getCurrencySymbol(currencyCode);
        DigitBuffer other = new DigitBuffer();
        setup(params, pattern, n, other, options, symbol, formatMode, grouping, currencyDigits, -1, -1);
        format(pattern, other, dbuf, symbol, null);
        dbuf.appendTo(destination);
        break;
      }

      case SHORT:
      {
        // Set the plural category for the number
        NumberOperands operands = new NumberOperands(n.toPlainString());
        PluralCategory category = PLURAL_RULES.evalCardinal(locale.language(), operands);

        // Select the divisor and pattern based on the number of integer digits and plural category.
        int digits = integerDigits(n);
        int power = getPowerOfTen_CURRENCY_SHORT(digits);
        NumberPattern pattern = select(n, getPattern_CURRENCY_SHORT(digits, category));
        
        // Optionally divide the number to get the compact form.
        if (power != 0) {
          //
          // Check if we might need to select the next pattern up due to rounding.
          // This handles cases where "999,999" formats to "1 M" instead of "1,000 K"
          // Check if this number rounds up to exceed minimum integer digits
          //
          RoundingMode roundingMode = options.roundMode.toRoundingMode();
          BigDecimal nn = n.movePointLeft(power);
          nn = nn.setScale(0, roundingMode);

          // TODO: Peek at the formatting mode to determine whether this number will round
          // when we do the final format.

          int nnDigits = integerDigits(nn);
          if (nnDigits > pattern.format.minimumIntegerDigits() && digits < 15) {
            // Select pattern that supports 1 more digit than the original number,
            // to account for rounding.
            digits++;

            power = getPowerOfTen_CURRENCY_SHORT(digits);
            pattern = select(n, getPattern_CURRENCY_SHORT(digits, category));
          }
          
          n = n.movePointLeft(power);
        }
        
        // For compact forms by default we want to show a certain number of significant digits.
        // So "1200" becomes "1.2 K" instead of just "1 K". This can be overridden by the 
        // NumberFormatOptions as needed.
        int minIntDigits = pattern.format().minimumIntegerDigits();
        int maxSigDigits = Math.min(minIntDigits + 1, 3);
        int minSigDigits = minIntDigits;

        // Format the number according to the pattern
        String symbol = getCurrencySymbol(currencyCode);
        DigitBuffer other = new DigitBuffer();
        formatMode = orDefault(options.formatMode(), NumberFormatMode.SIGNIFICANT_MAXFRAC);
        setup(params, pattern, n, other, options, symbol, formatMode,grouping, -1, maxSigDigits, minSigDigits);
        format(pattern, other, dbuf, symbol, null);
        dbuf.appendTo(destination);
        break;
      }
      
      case NAME:
      case CODE:
      {
        // Format the number with generic formatter parameters and grouping off to get the plural category.
        NumberPattern pattern = select(n, decimalStandard);
        DigitBuffer number = new DigitBuffer();
        setup(GENERIC_PARAMS, pattern, n, number, options, null, formatMode, false, currencyDigits, -1, -1);
        
        // Set the operands and get the plural category.
        NumberOperands operands = new NumberOperands(number);
        PluralCategory category = PLURAL_RULES.evalCardinal(locale.language(), operands);
        number.reset();

        // Format the number
        setup(params, pattern, n, number, options, null, formatMode, grouping, currencyDigits, -1, -1);
        format(pattern, number, dbuf, null, null);
        
        // Wrap the result with the wrapper selected by the plural category.
        String unit = style == CurrencyFormatStyle.CODE ? currencyCode : getCurrencyPluralName(currencyCode, category);
        wrapUnits(category, dbuf, unit, destination);
        break;
      }
    }
  }
  
  private static DecimalFormatOptions buildPluralOpts() {
    DecimalFormatOptions opts = new DecimalFormatOptions(DecimalFormatStyle.DECIMAL);
    opts.setGrouping(false).setMinimumFractionDigits(0).setMinimumIntegerDigits(1);
    return opts;
  }


  /**
   * Select the appropriate pattern to format a positive or negative number.
   */
  protected static NumberPattern select(BigDecimal n, NumberPattern[] patterns) {
    return n.signum() == -1 ? patterns[1] : patterns[0];
  }

  /**
   * Return the integer, or a default if null.
  */
  protected static int orDefault(Integer n, int alt) {
    return n == null ? alt : n;
  }

  /**
   * Return the boolean, or a default if null.
   */
  protected static boolean orDefault(Boolean b, boolean alt) {
    return b == null ? alt : b;
  }

  /**
   * Return the format mode, or a default if null.
   */
  protected static NumberFormatMode orDefault(NumberFormatMode m, NumberFormatMode alt) {
    return m == null ? alt : m;
  }
  
  /**
   * Construct a pair of patterns [positive, negative].
   */
  protected static NumberPattern[] patterns(String positive, String negative) {
    return new NumberPattern[] { parse(positive), parse(negative) };
  }

  /**
   * Parse a string as a number pattern.
   */
  protected static NumberPattern parse(String pattern) {
    return new NumberPatternParser().parse(pattern);
  }

  protected void setup(
      NumberFormatterParams formatterParams,
      NumberPattern pattern,
      BigDecimal n,
      DigitBuffer buf,
      NumberFormatOptions<?> options,
      String currency,
      NumberFormatMode formatMode,
      boolean grouping,
      int currencyDigits,
      int maxSigDigits,
      int minSigDigits) {

    Format format = pattern.format();
    int minIntDigits = orDefault(options.minimumIntegerDigits(), format.minimumIntegerDigits());
    
    int maxFracDigits = currencyDigits == -1 ? format.maximumFractionDigits() : currencyDigits;
    int minFracDigits = currencyDigits == -1 ? format.minimumFractionDigits() : currencyDigits;
    maxFracDigits = orDefault(options.maximumFractionDigits(), maxFracDigits);
    minFracDigits = orDefault(options.minimumFractionDigits(), minFracDigits);

    // Only set the min and max significant digits variables in this mode.
    switch (formatMode) {
      case SIGNIFICANT:
      case SIGNIFICANT_MAXFRAC:
        maxSigDigits = orDefault(options.maximumSignificantDigits(), maxSigDigits);
        minSigDigits = orDefault(options.minimumSignificantDigits(), minSigDigits);
        break;
        
      default:
        maxSigDigits = -1;
        minSigDigits = -1;
        break;
    }

    n = NumberFormattingUtils.setup(
        n,
        options.roundMode(),
        formatMode,
        minIntDigits,
        maxFracDigits,
        minFracDigits,
        maxSigDigits,
        minSigDigits);

    NumberFormattingUtils.format(
        n,
        buf,
        formatterParams,
        currency != null,
        grouping,
        minIntDigits,
        format.primaryGroupingSize(),
        format.secondaryGroupingSize());
  }
  
  /**
   * Formats a number using a pattern and options.
   */
  protected void format(
      NumberPattern pattern,
      DigitBuffer number,
      DigitBuffer buf,
      String currency,
      String percent) {

    // Flag indicating if we've emitted the formatted number yet.
    boolean emitted = false;

    // Iterate over the nodes in the format, handling each type.
    List<Node> nodes = pattern.parsed();
    int size = nodes.size();
    for (int i = 0; i < size; i++) {
      Node node = nodes.get(i);
      if (node instanceof Text) {
        buf.append(((Text)node).text());

      } else if (node instanceof Format) {
        buf.append(number);
        emitted = true;

      } else if (node instanceof Symbol) {
        switch ((Symbol)node) {
          case MINUS:
            buf.append(params.minus);
            break;

          case PERCENT:
            if (percent != null) {
              buf.append(percent);
            }
            break;

          case CURRENCY:
            if (currency == null || currency.isEmpty()) {
              break;
            }

            // Spacing around currency depending on if it occurs before or
            // after the formatted digits.
            if (emitted) {
              char firstCh = currency.charAt(0);
              if (!isSymbol(firstCh) && Character.isDigit(buf.last())) {
                buf.append(NBSP);
              }
              buf.append(currency);

            } else {
              buf.append(currency);
              boolean nextFormat = i < (size - 1) && nodes.get(i + 1) instanceof Format;
              char lastCh = currency.charAt(currency.length() - 1);
              if (!isSymbol(lastCh) && nextFormat) {
                buf.append(NBSP);
              }
            }
            break;
        }
      }
    }
  }

  /**
   * Returns true if the character is in any of the symbol categories.
   */
  public static boolean isSymbol(char ch) {
    switch (Character.getType(ch)) {
      case Character.MATH_SYMBOL:
      case Character.CURRENCY_SYMBOL:
      case Character.MODIFIER_SYMBOL:
      case Character.OTHER_SYMBOL:
        return true;
        
      default:
        return false;
    }
  }

}
