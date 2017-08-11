
import json, os, sys
from lxml.etree import fromstring, tostring

# Builds a temporary patch for 'languageMatching' JSON from original
# supplemental 'languageInfo.xml'. This is a temporary fix for a bug
# in the JSON cldr-core data:
# http://unicode.org/cldr/trac/ticket/10397

def read(path):
    data = open(path, 'rb').read()
    return fromstring(data)

def main():
    tree = read(sys.argv[1])
    recs = {}
    for n in tree.xpath('//languageMatches[@type="written"]/languageMatch'):
        desired = n.attrib.get('desired')
        rec = dict()
        for k in ('supported','percent','oneway'):
            v = n.attrib.get(k)
            if v is not None:
                if k == 'oneway':
                    v = 1
                rec[k] = v
        recs[desired] = rec

    res = dict(
        supplemental = dict(
            languageMatching = dict(
                written = recs
            )
        )
    )
    print json.dumps(res, indent=2)


if __name__ == '__main__':
    main()

