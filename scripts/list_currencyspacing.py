
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'buildsrc', 'src', 'main', 'resources', 'data')

CUR = ('numbers', 'currencyFormats-numberSystem-latn', 'currencySpacing')
BEFORE = 'beforeCurrency'
AFTER = 'afterCurrency'


def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-numbers-modern')):
        for n in names:
            if n == 'numbers.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    groups = defaultdict(int)

    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            for name in ('currencyMatch','surroundingMatch','insertBetween'):
                for which in (BEFORE, AFTER):
                    path = (which, name)
                    val = get(m, code, *(CUR + path))
                    key = '-'.join(path) + ' -> ' + repr(val)
                    groups[key] += 1

    recs = sorted((c, s) for s, c in groups.iteritems())
    for c, s in reversed(recs):
        print str(c).rjust(5), s.encode('utf-8')

if __name__ == '__main__':
    main()

