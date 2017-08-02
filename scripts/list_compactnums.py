
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'compiler', 'src', 'main', 'resources', 'data')

LONG = ('numbers', 'decimalFormats-numberSystem-latn', 'long', 'decimalFormat')
SHORT = ('numbers', 'decimalFormats-numberSystem-latn', 'short', 'decimalFormat')

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-numbers-modern')):
        for n in names:
            if n == 'numbers.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    groups = {
        'long': defaultdict(int),
        'short': defaultdict(int)
    }

    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            longkey = '%s-long' % code
            shortkey = '%s-short' % code
            groups[longkey] = defaultdict(int)
            groups[shortkey] = defaultdict(int)

            p = get(m, code, *LONG)
            for k, v in p.iteritems():
                if v == '0':
                    pass
                    #groups[longkey][k] += 1
                else:
                    groups['long'][v] += 1

            p = get(m, code, *SHORT)
            for k, v in p.iteritems():
                if v == '0':
                    pass
                    #groups[shortkey][k] += 1
                else:
                    groups['short'][v] += 1

            for k in (longkey, shortkey):
                if not groups[k]:
                    del groups[k]

    for k, counts in groups.iteritems():
        print k.upper()
        recs = sorted((c, s) for s, c in counts.iteritems())
        for c, s in reversed(recs):
            print str(c).rjust(5), s.encode('utf-8')
        print '-'*74

if __name__ == '__main__':
    main()

