
import json, os, sys
from collections import OrderedDict
from lxml.etree import fromstring, tostring

# Builds a temporary patch for 'pluralRanges' JSON from original
# supplemental 'pluralRanges.xml'. This data is currently missing
# from the JSON CLDR export.

ROOT = '//pluralRanges'

def read(path):
    data = open(path, 'rb').read()
    return fromstring(data)

def main():
    tree = read(sys.argv[1])

    ranges = OrderedDict()
    for n in tree.xpath('//pluralRanges'):
        locales = n.attrib.get('locales').split()
        recs = []
        for c in n.xpath('./pluralRange'):
            rec = dict((k, v) for k, v in c.attrib.iteritems())
            recs.append(rec)
        for k in locales:
            ranges[k] = recs

    sort = OrderedDict()
    for k in sorted(ranges.iterkeys()):
        sort[k] = ranges[k]

    res = dict(
        supplemental = dict(
            pluralRanges = sort
        )
    )
    print json.dumps(res, indent=2)


if __name__ == '__main__':
    main()

