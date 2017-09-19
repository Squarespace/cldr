
# TODO

### Expose methods to retrieve names for stuff.

Currencies are already available, just need to expose methods on
NumberFormatter.


### Apply list patterns to unit sequences

Need to add cldr-misc-modern submodule.


### Enhanced language matching

Given a list of supported bundles and a list of desired locales (sorted from
most- to least-desired) find the best bundle match.

See: http://www.unicode.org/reports/tr35/tr35.html#EnhancedLanguageMatching


### Intern strings and duplicate constants to reduce jar size

Many of the strings are duplicated across many locales and can be defined in
one place as constants and referenced.


### List formatting

Joining lists together with commas, AND and OR.  Release v32 adds disjunctive
list.

http://www.unicode.org/reports/tr35/tr35-general.html#ListPatterns


### Support at-least and ranges for decimals, currencies and units.

http://www.unicode.org/reports/tr35/tr35-numbers.html#Miscellaneous_Patterns


