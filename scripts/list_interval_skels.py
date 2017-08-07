
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'compiler', 'src', 'main', 'resources', 'data')

FMTS = ('dates', 'calendars', 'gregorian', 'dateTimeFormats', 'intervalFormats')

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-dates-modern')):
        for n in names:
            if n == 'ca-gregorian.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    counts = defaultdict(int)
    fallbacks = defaultdict(int)
    patterns = defaultdict(int)
    fields = defaultdict(int)
    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            obj = get(m, code, *FMTS)
            for skel in obj:
                counts[skel] += 1
                if skel == 'intervalFormatFallback':
                    val = obj[skel]
                    fallbacks[val] += 1
                else:
                    for k, v in obj[skel].iteritems():
                        patterns[v] += 1
                        fields[k] += 1

    for group in (counts, fallbacks, fields):
        recs = sorted((c, s) for s, c in group.iteritems())
        for c, s in reversed(recs):
            print str(c).rjust(5), s.encode('utf-8')

        print '='*74
        print

if __name__ == '__main__':
    main()

