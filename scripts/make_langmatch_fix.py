
import json, os, sys
from collections import OrderedDict
from lxml.etree import fromstring, tostring

# Builds a temporary patch for 'languageMatching' JSON from original
# supplemental 'languageInfo.xml'. This is a temporary fix for a bug
# in the JSON cldr-core data:
# http://unicode.org/cldr/trac/ticket/10397

ROOT = '//languageMatches[@type="written_new"]'

def read(path):
    data = open(path, 'rb').read()
    return fromstring(data)

def main():
    tree = read(sys.argv[1])

    rules = OrderedDict()
    for n in tree.xpath(ROOT + '/languageMatch'):
        desired = n.attrib.get('desired')
        rec = dict()
        for k in ('supported', 'distance', 'oneway'):
            v = n.attrib.get(k)
            if v is not None:
                if k == 'oneway':
                    v = '1'
                rec[k] = v
        rules[desired] = rec

    node = tree.xpath(ROOT + '/paradigmLocales')[0]
    locales = node.attrib.get('locales')

    variables = OrderedDict()
    for n in tree.xpath(ROOT + '/matchVariable'):
        _id = n.attrib.get('id')
        val = n.attrib.get('value')
        variables[_id] = val

    res = dict(
        supplemental = dict(
            languageMatching = dict(
                written_new = dict(
                    paradigmLocales = locales,
                    matchVariable = variables,
                    languageMatch = rules
                )
            )
        )
    )
    print json.dumps(res, indent=2)


if __name__ == '__main__':
    main()

