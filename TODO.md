
# TODO

### Complete enhanced language matching and related todos

* Support new !enUS / GB distance, matching a partition to a region.
* Finish territory aliases substitution
* Add more test cases from ICU, other places


### Expose methods to retrieve names for stuff.

Currencies are already available, just need to expose methods on
NumberFormatter.

### Apply list patterns to unit sequences

Need to add cldr-misc-modern submodule.


### Intern strings and duplicate constants to reduce jar size

Many of the strings are duplicated across many locales and can be defined in
one place as constants and referenced.


### List formatting

Joining lists together with commas, AND and OR.  Release v32 adds disjunctive
list.

http://www.unicode.org/reports/tr35/tr35-general.html#ListPatterns


### Support at-least and ranges for decimals, currencies and units.

http://www.unicode.org/reports/tr35/tr35-numbers.html#Miscellaneous_Patterns


