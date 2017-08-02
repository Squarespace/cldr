
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'compiler', 'src', 'main', 'resources', 'data')

FMTS = ('dates', 'calendars', 'gregorian', 'dateTimeFormats', 'availableFormats')

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-dates-modern')):
        for n in names:
            if n == 'ca-gregorian.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    counts = defaultdict(int)
    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            for skel in get(m, code, *FMTS):
                counts[skel] += 1
            #for fmt in get(m, code, *FMTS).itervalues():
            #    counts[fmt] += 1

    recs = sorted((c, s) for s, c in counts.iteritems())
    for c, s in reversed(recs):
        print str(c).rjust(5), s.encode('utf-8')

if __name__ == '__main__':
    main()

