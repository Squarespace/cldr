package com.squarespace.cldr.numbers;

import java.math.BigDecimal;
import java.util.List;

import com.squarespace.cldr.CLDRLocale;
import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPattern.Node;
import com.squarespace.cldr.numbers.NumberPattern.Symbol;
import com.squarespace.cldr.numbers.NumberPattern.Text;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.plurals.PluralRules;


/**
 * General-purpose decimal number formatter. NOTE: These methods are not intended to be
 * called directly by clients, only from generated or test code.
 */
public abstract class NumberFormatterBase implements NumberFormatter {

  protected static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
  protected static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
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
  
  protected abstract BigDecimal getDivisor_DECIMAL_SHORT(int digits);
  protected abstract BigDecimal getDivisor_DECIMAL_LONG(int digits);
  protected abstract BigDecimal getDivisor_CURRENCY_SHORT(int digits);
  protected abstract NumberPattern[] getPattern_DECIMAL_SHORT(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_DECIMAL_LONG(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_CURRENCY_SHORT(int digits, PluralCategory category);
  protected abstract String getCurrencySymbol(String code);
  protected abstract String getCurrencyDisplayName(String code);
  protected abstract String getCurrencyPluralName(String code, PluralCategory category);
  protected abstract void wrapUnits(PluralCategory category, DigitBuffer dbuf, String unit, StringBuilder dest);

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
    NumberOperands operands = new NumberOperands();
    NumberFormatStyle style = options.style();
    switch (style) {
      case DECIMAL:
      {
        NumberPattern pattern = select(n, decimalStandard);
        format(pattern, n, dbuf, options, operands, null, null, -1, -1);
        dbuf.appendTo(destination);
        break;
      }

      case LONG:
      case SHORT:
      {
        // Set the plural category for the number
        operands.set(n.toPlainString());
        PluralCategory category = PluralRules.evalCardinal(locale.language(), operands);

        int digits = integerDigits(n);
        BigDecimal divisor;
        NumberPattern pattern;
        
        // Select the divisor and pattern based on the number of integer digits and plural category.
        if (style == NumberFormatStyle.LONG) {
          divisor = getDivisor_DECIMAL_LONG(digits);
          pattern = select(n, getPattern_DECIMAL_LONG(digits, category));
        } else {
          divisor = getDivisor_DECIMAL_SHORT(digits);
          pattern = select(n, getPattern_DECIMAL_SHORT(digits, category));
        }

        // Optionally divide the number to get the compact form.
        if (divisor != null) {
          n = n.divide(divisor);
          operands.set(n.toPlainString());
        }

        // TODO: compact forms rounding twice and check if number of integer digits increased.
        // we may need to re-select divisor and pattern. example is formatting 999999 as
        // "1,000 K" or "1 M". To get the latter we need to select again post-rounding.

        // For compact forms by default we want to show a certain number of significant digits.
        // So "1200" becomes "1.2 K" instead of just "1 K". This can be overridden by the 
        // NumberFormatOptions as needed.
        int minIntDigits = pattern.format().minimumIntegerDigits();
        int maxSigDigits = Math.min(minIntDigits + 1, 3);
        int minSigDigits = minIntDigits;

        // Format the number according to the pattern
        format(pattern, n, dbuf, options, operands, null, null, maxSigDigits, minSigDigits);
        dbuf.appendTo(destination);
        break;
      }

      case PERCENT:
      case PERMILLE:
      {
        NumberPattern pattern = select(n, percentStandard);
        String symbol = style == NumberFormatStyle.PERCENT ? params.percent : params.perMille;
        n = n.multiply(style == NumberFormatStyle.PERCENT ? ONE_HUNDRED : ONE_THOUSAND);
        format(pattern, n, dbuf, options, operands, null, symbol, -1, -1);
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
    NumberOperands operands = new NumberOperands();
    CurrencyFormatStyle style = options.style();
    switch (style) {
      case SYMBOL:
      case ACCOUNTING:
      {
        NumberPattern[] patterns = style == CurrencyFormatStyle.SYMBOL ? currencyStandard : currencyAccounting;
        NumberPattern pattern = select(n, patterns);
        String symbol = getCurrencySymbol(currencyCode);
        format(pattern, n, dbuf, options, operands, symbol, null, -1, -1);
        dbuf.appendTo(destination);
        break;
      }

      case SHORT:
      {
        // Set the plural category for the number
        operands.set(n.toPlainString());
        PluralCategory category = PluralRules.evalCardinal(locale.language(), operands);

        // Select the divisor and pattern based on the number of integer digits and plural category.
        int digits = integerDigits(n);
        BigDecimal divisor = getDivisor_CURRENCY_SHORT(digits);
        NumberPattern pattern = select(n, getPattern_CURRENCY_SHORT(digits, category));
        
        // Optionally divide the number to get the compact form.
        if (divisor != null) {
          n = n.divide(divisor);
          operands.set(n.toPlainString());
        }

        // TODO: compact forms rounding twice and check if number of integer digits increased.
        // we may need to re-select divisor and pattern. example is formatting 999999 as
        // "1,000 K" or "1 M". To get the latter we need to select again post-rounding.
        
        // For compact forms by default we want to show a certain number of significant digits.
        // So "1200" becomes "1.2 K" instead of just "1 K". This can be overridden by the 
        // NumberFormatOptions as needed.
        int minIntDigits = pattern.format().minimumIntegerDigits();
        int maxSigDigits = Math.min(minIntDigits + 1, 3);
        int minSigDigits = minIntDigits;

        // Format the number according to the pattern
        String symbol = getCurrencySymbol(currencyCode);
        format(pattern, n, dbuf, options, operands, symbol, null, maxSigDigits, minSigDigits);
        dbuf.appendTo(destination);
        break;
      }
      
      case NAME:
      case CODE:
      {
        operands.set(n.toPlainString());
        PluralCategory category = PluralRules.evalCardinal(locale.language(), operands);
        NumberPattern pattern = select(n, decimalStandard);
        format(pattern, n, dbuf, options, operands, null, null, -1, -1);
        String unit = style == CurrencyFormatStyle.CODE ? currencyCode : getCurrencyPluralName(currencyCode, category);
        wrapUnits(category, dbuf, unit, destination);
        break;
      }
    }
  }
  
  /**
   * Count the number of integer digits in the number.
   */
  protected static int integerDigits(BigDecimal n) {
    int digits = n.precision();
    if (n.scale() > 0) {
      digits -= n.scale();
    }
    return digits;
  }

  /**
   * Select the appropriate pattern to format a positive or negative number.
   */
  protected static NumberPattern select(BigDecimal n, NumberPattern[] patterns) {
    return n.compareTo(BigDecimal.ZERO) == -1 ? patterns[1] : patterns[0];
  }

  /**
   * Return the integer, or a default if null.
  */
  private static int orDefault(Integer n, int alt) {
    return n == null ? alt : n;
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

  /**
   * Formats a number using a pattern and options.
   */
  protected void format(
      NumberPattern pattern,
      BigDecimal n,
      DigitBuffer buf,
      NumberFormatOptions<?> options,
      NumberOperands operands,
      String currency,
      String percent,
      int maxSigDigits,
      int minSigDigits) {

    // Only set the min and max significant digits variables in this mode.
    if (options.formatMode == NumberFormatMode.SIGNIFICANT) {
      maxSigDigits = orDefault(options.maximumSignificantDigits(), maxSigDigits);
      minSigDigits = orDefault(options.minimumSignificantDigits(), minSigDigits);
    } else {
      maxSigDigits = -1;
      minSigDigits = -1;
    }

    // Peek at the number format in the pattern and use it to setup the operands and
    // counts for the number of integer and decimal digits to format.
    Format format = pattern.format();
    long[] digits = NumberFormattingUtils.setup(
        n,
        operands,
        options.roundMode(),
        orDefault(options.minimumIntegerDigits(), format.minimumIntegerDigits()),
        orDefault(options.maximumFractionDigits(), format.maximumFractionDigits()),
        orDefault(options.minimumFractionDigits(), format.minimumFractionDigits()),
        maxSigDigits,
        minSigDigits);

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
        NumberFormattingUtils.format(digits,
            buf,
            operands,
            params,
            currency != null,
            options.grouping(),
            format.primaryGroupingSize(),
            format.secondaryGroupingSize());
        
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
