
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'compiler', 'src', 'main', 'resources', 'data')

FMTS = ('units', 'long')

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-units-modern')):
        for n in names:
            if n == 'units.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    counts = defaultdict(int)
    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            for key in get(m, code, *FMTS):
                if key not in ('per', 'coordinateUnit'):
                    category, unit = key.split('-', 1)
                    print category, unit
    return

    recs = sorted((c, s) for s, c in counts.iteritems())
    for c, s in reversed(recs):
        print str(c).rjust(5), s.encode('utf-8')

if __name__ == '__main__':
    main()

