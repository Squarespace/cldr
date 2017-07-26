
# TODO

### High test coverage for main formatter code pathways.


### Timezone formatting

This was punted in the initial pass for date/time formatting.


### Units

Unit formatting (square feet, Megabytes, etc)


----------------------------------------------------------

# FIXED


### Use scaling instead of division for compact forms.

Each compact form has a divisor that is a power of 10. We currently
divide the number by this divisor, but instead we can simply rescale
the number by the number of zeros in the divisor.  This will boost
performance a bit for these forms.


### Remove use of number operands in formatting.

Rely entirely on BigDecimal scaling for producing canonical number format.
Then emit localized form directly from canonical string representation.


### Fix NumberOperands for numbers exceeding range of long

Prevent overflow in operands for very large numbers. Periodically reduce
the integer portion during the parse, and stop after maximum decimal digits
are reached.


### Compact forms defaults for values < 1000.

Docs on this are here:
http://www.unicode.org/reports/tr35/tr35-numbers.html#Compact_Number_Formats

Use the normal number / currency format, but set max fractions to 0 and use
a min/max significant digit value of 2 or 3. Both can be overridden by the
user options.

Need to update the code generator to generate this modified default pattern.
Added rendering of patterns so we can generate a modified format off a 
pattern, e.g. changing maxFrac to 0 for compact forms.


### Pluralized form rounding issue, e.g. "1 M" instead of "1,000 K"

This occurs since the pattern and divisor are selected before the rounding
is done, and if the rounding results in an extra integer digit being added
we currency do not re-select the pattern / divisor.  Detect if the number
of digits increased and if so, select a new divisor, re-divide and select
the final pattern.

Procedure:

 a. 999,999 is 6 digits and chooses divisor "1000"
 b. 6 digits chooses pattern "000K".
 c. rounding produces 1,000 with 4 integer digits, but chosen pattern
    expects 3 integer digits.
 d. formatting would produce "1,000K" instead of "1M".
 e. use 7 digits to select new divisor "1000000"
 f. 7 digits chooses pattern "0M"
 g. redivide and format.

### Rounding issues for pluralized formatting. 

We select pluralized patterns using the plural of the original number value.
However, fractional digits affect the pluralized form, so we may choose
a plural pattern when the result should be singular. 

When truncating fractions above, pluralization can change. Need to handle:

 "1.00" -> "1.00 US dollars" -> "1 US dollar"

This may require formatting twice, or pre-formatting using the extracted
format parameters, then using this formatted number in isolation to select
the correct pluralized pattern.


### Proper defaulting of format.

Examine other styles to see if some params need to be defaulted unless
overridden. For example some modes may prefer SIGNIFICANT_MAXFRAC for
properly setting currency decimals.

Compact currencies prefer SIGNIFICANT_MAXFRAC to be used. Caller should
force FRACTIONS format to override this and use the min/max fraction settings.


### Proper defaulting of grouping

Change grouping option to a Boolean which may be null. If null it will
default to: grouping off for decimals, grouping on for currencies.


### Add new format mode that selects best output for compact currencies.

Need to devise options to configure formatting of decimals for CODE and NAME.

When formatting with these styles, the formatter uses the standard currency
pattern.  For en_US this is "#,##0.00" which results in:

  minimum fraction digits: 2
  maximum fraction digits: 2

However, we would like to format by default, removing any unnecessary fraction
digits. Formatting "1.00" and USD would produce "1 US dollar" instead of "1.00
US dollars".

This mode would use a mixture of min significant digits and max fractions. For
example:

 minimum significant digits: 1
 maximum fraction digits:    2

Example: "1.00" would be formatted as "$1" and "1.032" would be
          formatted as "$1.03".

We could add these as dedicated modes to the existing ones:

   default         - use whatever the pattern's default. the minInt maxFrac and
                     minFrac options override the pattern.
   significant     - use the min / max significant digits only.
   minsig-maxfrac  - ensure minimum significant digits and maximum fraction
                     digits are honored.  other fraction settings are ignored.
                     max-sig is infinite by default.
