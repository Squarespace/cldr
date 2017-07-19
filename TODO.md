
# TODO

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




# DONE


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

