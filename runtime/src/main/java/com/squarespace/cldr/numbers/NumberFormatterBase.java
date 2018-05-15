package com.squarespace.cldr.numbers;

import static com.squarespace.cldr.numbers.DecimalFormatStyle.PERCENT;
import static com.squarespace.cldr.numbers.DecimalFormatStyle.PERCENT_SCALED;
import static com.squarespace.cldr.numbers.DecimalFormatStyle.PERMILLE;
import static com.squarespace.cldr.numbers.NumberFormatMode.SIGNIFICANT_MAXFRAC;
import static com.squarespace.cldr.numbers.NumberFormattingUtils.integerDigits;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.squarespace.cldr.CLDR;
import com.squarespace.cldr.numbers.NumberPattern.Format;
import com.squarespace.cldr.numbers.NumberPattern.Node;
import com.squarespace.cldr.numbers.NumberPattern.Symbol;
import com.squarespace.cldr.numbers.NumberPattern.Text;
import com.squarespace.cldr.parse.FieldPattern;
import com.squarespace.cldr.parse.FieldPattern.Field;
import com.squarespace.cldr.parse.WrapperPatternParser;
import com.squarespace.cldr.plurals.PluralCategory;
import com.squarespace.cldr.plurals.PluralRules;
import com.squarespace.cldr.units.Unit;
import com.squarespace.cldr.units.UnitFormatOptions;
import com.squarespace.cldr.units.UnitValue;


/**
 * Base class for localized decimal number formatters.
 */
abstract class NumberFormatterBase implements NumberFormatter {

  protected static final Map<String, NumberPattern> NUMBER_PATTERN_CACHE = new ConcurrentHashMap<>();
  protected static final Map<String, List<FieldPattern.Node>> UNIT_PATTERN_CACHE = new ConcurrentHashMap<>();
  protected static final PluralRules PLURAL_RULES = CLDR.get().getPluralRules();
  protected static final NumberFormatterParams GENERIC_PARAMS = new NumberFormatterParams();
  private static final char NBSP = '\u00a0';

  protected final CLDR.Locale bundleId;
  protected final NumberFormatterParams params;
  protected final NumberPattern[] decimalStandard;
  protected final NumberPattern[] percentStandard;
  protected final NumberPattern[] currencyStandard;
  protected final NumberPattern[] currencyAccounting;
  protected final NumberPattern[] unitStandard;
  
  protected NumberFormatterBase(
      CLDR.Locale bundleId,
      NumberFormatterParams params,
      NumberPattern[] decimalStandard,
      NumberPattern[] percentStandard,
      NumberPattern[] currencyStandard,
      NumberPattern[] currencyAccounting,
      NumberPattern[] unitStandard) {

    this.bundleId = bundleId;
    this.params = params;
    this.decimalStandard = decimalStandard;
    this.percentStandard = percentStandard;
    this.currencyStandard = currencyStandard;
    this.currencyAccounting = currencyAccounting;
    this.unitStandard = unitStandard;
  }
  
  protected abstract int getDivisor_DECIMAL_SHORT(int digits);
  protected abstract int getDivisor_DECIMAL_LONG(int digits);
  protected abstract int getDivisor_CURRENCY_SHORT(int digits);
  
  protected abstract NumberPattern[] getPattern_DECIMAL_SHORT(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_DECIMAL_LONG(int digits, PluralCategory category);
  protected abstract NumberPattern[] getPattern_CURRENCY_SHORT(int digits, PluralCategory category);

  protected abstract List<FieldPattern.Node> getPattern_UNITS_SHORT(Unit unit, PluralCategory category);
  protected abstract List<FieldPattern.Node> getPattern_UNITS_NARROW(Unit unit, PluralCategory category);
  protected abstract List<FieldPattern.Node> getPattern_UNITS_LONG(Unit unit, PluralCategory category);
  
  protected abstract String getCurrencyPluralName(CLDR.Currency code, PluralCategory category);
  protected abstract void wrapUnits(PluralCategory category, DigitBuffer dbuf, String unit, StringBuilder dest);

  public abstract String getCurrencySymbol(CLDR.Currency code);
  public abstract String getNarrowCurrencySymbol(CLDR.Currency code);
  public abstract String getCurrencyDisplayName(CLDR.Currency code);
  public abstract int getCurrencyDigits(CLDR.Currency code);
  
  /**
   * Return the bundle identifier associated with this number formatter.
   */
  public CLDR.Locale bundleId() {
    return bundleId;
  }
  
  /**
   * Format a sequence of unit values.
   */
  public void formatUnits(List<UnitValue> values, StringBuilder destination, UnitFormatOptions options) {
    int size = values.size();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        destination.append(' ');
      }
      formatUnit(values.get(i), destination, options);
    }
  }
  
  /**
   * Format a unit value.
   */
  public void formatUnit(UnitValue value, StringBuilder destination, UnitFormatOptions options) {
    BigDecimal n = value.amount();
    boolean grouping = orDefault(options.grouping(), false);
    NumberFormatContext ctx = new NumberFormatContext(options, NumberFormatMode.DEFAULT);
    NumberPattern numberPattern = select(n, unitStandard);
    ctx.set(numberPattern);

    BigDecimal q = ctx.adjust(n);
    NumberOperands operands = new NumberOperands(q.toPlainString());
    PluralCategory category = PLURAL_RULES.evalCardinal(bundleId.language(), operands);
    
    Unit unit = value.unit();
    List<FieldPattern.Node> unitPattern = null;
    
    // We allow unit to be null to accomodate the caller as a last resort.
    // If the unit is not available we still format a number.
    if (unit != null) {
      switch (options.format()) {
        case LONG:
          unitPattern = getPattern_UNITS_LONG(unit, category);
          break;
          
        case NARROW:
          unitPattern = getPattern_UNITS_NARROW(unit, category);
          break;
          
        case SHORT:
        default:
          unitPattern = getPattern_UNITS_SHORT(unit, category);
          break;
      }
    }
    
    DigitBuffer number = new DigitBuffer();
    NumberFormattingUtils.format(q, number, params, false, grouping, ctx.minIntDigits(), 
        numberPattern.format.primaryGroupingSize(), 
        numberPattern.format.secondaryGroupingSize());

    if (unitPattern == null) {
      number.appendTo(destination);
      
    } else {
      DigitBuffer dbuf = new DigitBuffer();
      for (FieldPattern.Node node : unitPattern) {
        if (node instanceof FieldPattern.Text) {
          dbuf.append(((FieldPattern.Text)node).text());
          
        } else if (node instanceof Field) {
          Field field = (Field)node;
          if (field.ch() == '0') {
            format(numberPattern, number, dbuf, null, null);
          }
        }
      }
      dbuf.appendTo(destination);
    }
  }

  /**
   * Format the number into the buffer using the given options.
   */
  public void formatDecimal(BigDecimal n, StringBuilder destination, DecimalFormatOptions options) {
    DigitBuffer dbuf = new DigitBuffer();
    DecimalFormatStyle style = options.style();
    boolean grouping = orDefault(options.grouping(), false);
    boolean currency = false;
    NumberFormatMode formatMode = orDefault(options.formatMode(), NumberFormatMode.DEFAULT);
    
    switch (style) {
      case DECIMAL:
      {
        NumberFormatContext ctx = new NumberFormatContext(options, NumberFormatMode.DEFAULT);
        NumberPattern pattern = select(n, decimalStandard);
        ctx.set(pattern);
        BigDecimal q = ctx.adjust(n);

        DigitBuffer number = new DigitBuffer();
        NumberFormattingUtils.format(q, number, params, currency, grouping,
            ctx.minIntDigits(),
            pattern.format.primaryGroupingSize(),
            pattern.format.secondaryGroupingSize());
        format(pattern, number, dbuf, null, null);
        dbuf.appendTo(destination);
        break;
      }

      case LONG:
      case SHORT:
      {
        NumberFormatContext ctx = new NumberFormatContext(options, NumberFormatMode.SIGNIFICANT);
        int nDigits = integerDigits(n);
        int nDivisor = getDivisor_DECIMAL_LONG(nDigits);

        // Q1 is the number divided by the divisor (if any).
        BigDecimal q1 = n;
        if (nDivisor > 0) {
          q1 = n.movePointLeft(nDivisor).stripTrailingZeros();
        }
        int q1Digits = integerDigits(q1);

        // Select an initial pattern using the OTHER plural category.
        NumberPattern pattern;
        if (style == DecimalFormatStyle.LONG) {
          pattern = select(n, getPattern_DECIMAL_LONG(nDigits, PluralCategory.OTHER));
        } else {
          pattern = select(n, getPattern_DECIMAL_SHORT(nDigits, PluralCategory.OTHER));
        }
        
        // Q2 is the number adjusted using the pattern and options.
        ctx.setCompact(pattern, q1Digits, nDivisor);
        BigDecimal q2 = ctx.adjust(q1);
        int q2Digits = integerDigits(q2);

        // Number rounded up, we need to select a new divisor and pattern.
        if (q2Digits > q1Digits) {
          // Bump the number of digits by 1 to select the next divisor/pattern.
          nDigits++;
          int divisor = getDivisor_DECIMAL_LONG(nDigits);
          if (style == DecimalFormatStyle.LONG) {
            pattern = select(n, getPattern_DECIMAL_LONG(nDigits, PluralCategory.OTHER));
          } else {
            pattern = select(n, getPattern_DECIMAL_SHORT(nDigits, PluralCategory.OTHER));
          }
          
          // If divisor changed, we need to divide and adjust again. 
          // Otherwise we just use Q2 as-is.
          if (divisor > nDivisor) {
            q1 = n.movePointLeft(divisor).stripTrailingZeros();
            ctx.setCompact(pattern, integerDigits(q1), divisor);
            q2 = ctx.adjust(q1);
          }
        }

        // Compute the plural category.
        NumberOperands operands = new NumberOperands(q2.toPlainString());
        PluralCategory category = PLURAL_RULES.evalCardinal(bundleId.language(), operands);

        // Select the final pluralized pattern.
        if (style == DecimalFormatStyle.LONG) {
          pattern = select(n, getPattern_DECIMAL_LONG(nDigits, category));
        } else {
          pattern = select(n, getPattern_DECIMAL_SHORT(nDigits, category));
        }
        
        // Format the number into the buffer.
        DigitBuffer number = new DigitBuffer();
        NumberFormattingUtils.format(q2, number, params, false, grouping, 
            pattern.format.minimumIntegerDigits, 
            decimalStandard[0].format.primaryGroupingSize, 
            decimalStandard[0].format.secondaryGroupingSize);
        
        // Format the entire pattern and append to destination.
        format(pattern, number, dbuf, null, null);
        dbuf.appendTo(destination);
        break;
      }
      
      case PERCENT:
      case PERCENT_SCALED:
      case PERMILLE:
      case PERMILLE_SCALED:
      {
        // In default modes, scale the number. Otherwise assume it is already scaled
        // appropriately.
        if (style == PERCENT || style == PERMILLE) {
          n = n.movePointRight(style == PERCENT ? 2 : 3);
        }
        String symbol = (style == PERCENT) || (style == PERCENT_SCALED) ? params.percent : params.perMille;
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
      BigDecimal n, CLDR.Currency currencyCode, StringBuilder destination, CurrencyFormatOptions options) {
    
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
        NumberFormatContext ctx = new NumberFormatContext(options, SIGNIFICANT_MAXFRAC, currencyDigits);
        int nDigits = integerDigits(n);
        int nDivisor = getDivisor_CURRENCY_SHORT(nDigits);

        // Q1 is the number divided by the divisor (if any).
        BigDecimal q1 = n;
        if (nDivisor > 0) {
          q1 = n.movePointLeft(nDivisor).stripTrailingZeros();
        }
        int q1Digits = integerDigits(q1);

        // Select an initial pattern using the OTHER plural category.
        NumberPattern pattern = select(n, getPattern_CURRENCY_SHORT(nDigits, PluralCategory.OTHER));
        
        // Q2 is the number adjusted using the pattern and options.
        ctx.setCompact(pattern, q1Digits, nDivisor);
        BigDecimal q2 = ctx.adjust(q1);
        int q2Digits = integerDigits(q2);

        // Number rounded up, we need to select a new divisor and pattern.
        if (q2Digits > q1Digits) {
          // Bump the number of digits by 1 to select the next divisor/pattern.
          nDigits++;
          int divisor = getDivisor_CURRENCY_SHORT(nDigits);
          pattern = select(n, getPattern_CURRENCY_SHORT(nDigits, PluralCategory.OTHER));
          
          // If divisor changed, we need to divide and adjust again. 
          // Otherwise we just use Q2 as-is.
          if (divisor > nDivisor) {
            q1 = n.movePointLeft(divisor).stripTrailingZeros();
            ctx.setCompact(pattern, integerDigits(q1), divisor);
            q2 = ctx.adjust(q1);
          }
        }

        // Compute the plural category.
        NumberOperands operands = new NumberOperands(q2.toPlainString());
        PluralCategory category = PLURAL_RULES.evalCardinal(bundleId.language(), operands);

        // Select the final pluralized pattern.
        pattern = select(n, getPattern_CURRENCY_SHORT(nDigits, category));
        
        // Format the number into the buffer.
        String symbol = getCurrencySymbol(currencyCode);
        DigitBuffer number = new DigitBuffer();
        NumberFormattingUtils.format(q2, number, params, true, grouping, 
            pattern.format.minimumIntegerDigits,
            currencyStandard[0].format.primaryGroupingSize, 
            currencyStandard[0].format.secondaryGroupingSize);
        
        // Format the entire pattern and append to destination.
        format(pattern, number, dbuf, symbol, null);
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
        PluralCategory category = PLURAL_RULES.evalCardinal(bundleId.language(), operands);
        number.reset();

        // Format the number
        setup(params, pattern, n, number, options, null, formatMode, grouping, currencyDigits, -1, -1);
        format(pattern, number, dbuf, null, null);
        
        // Wrap the result with the wrapper selected by the plural category.
        String unit = style == CurrencyFormatStyle.CODE 
            ? currencyCode.toString() 
            : getCurrencyPluralName(currencyCode, category);
        wrapUnits(category, dbuf, unit, destination);
        break;
      }
    }
  }
  
  protected int getDivisorShift(int digits, int[] divisorSet) {
    int index = Math.min(15, Math.max(0, digits - 1));
    return divisorSet[index];
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
    return NUMBER_PATTERN_CACHE.computeIfAbsent(pattern, s -> new NumberPatternParser().parse(s));
  }

  protected static List<FieldPattern.Node> unitPattern(String pattern) {
    return UNIT_PATTERN_CACHE.computeIfAbsent(pattern,  s -> new WrapperPatternParser().parseWrapper(pattern));
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
