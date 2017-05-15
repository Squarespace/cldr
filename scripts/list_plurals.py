
import operator, os, json, re, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'buildsrc', 'src', 'main', 'resources', 'data')
RE_STRIP = re.compile('^([^@]+)\s+(@(integer|decimal).+)')
ORDER = {
    'zero': 0,
    'one': 1,
    'two': 2,
    'few': 3,
    'many': 4,
    'other': 5
}

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-core')):
        for n in names:
            if n == 'plurals.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def category_cmp(a, b):
    return cmp(ORDER[a[0]], ORDER[b[0]])

def main():
    counts = defaultdict(int)
    for path in scan():
        o = json.load(open(path, 'rb'))
        m = get(o, 'supplemental', 'plurals-type-cardinal')
        for code in sorted(m.iterkeys()):
            items = ((k[17:], v) for k, v in get(m, code).iteritems())
            items = sorted(items, cmp=category_cmp)
            for k, v in items:
                g = RE_STRIP.match(v)
                v = ''
                if g:
                    v = g.group(1)
                print code.rjust(5), k.rjust(7), ' ', v
            print

if __name__ == '__main__':
    main()

