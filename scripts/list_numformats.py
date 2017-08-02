
import operator, os, json, sys
from collections import defaultdict

join = os.path.join
PWD = os.path.realpath(os.path.dirname(__file__))
DATA = join(PWD, '..', 'compiler', 'src', 'main', 'resources', 'data')

#FMTS = ('numbers', 'decimalFormats-numberSystem-latn', 'standard')
DEC_STD = ('numbers', 'decimalFormats-numberSystem-latn', 'standard')
PCT_STD = ('numbers', 'percentFormats-numberSystem-latn', 'standard')
CUR_STD = ('numbers', 'currencyFormats-numberSystem-latn', 'standard')
CUR_ACC = ('numbers', 'currencyFormats-numberSystem-latn', 'accounting')

def scan():
    for root, dirs, names in os.walk(join(DATA, 'cldr-numbers-modern')):
        for n in names:
            if n == 'numbers.json':
                yield join(root, n)

get = lambda o, *keys: reduce(operator.getitem, keys, o)

def main():
    groups = {
        'decimal': defaultdict(int),
        'currency': defaultdict(int)
    }


    groups = {
        'decimal-standard': defaultdict(int),
        'percent-standard': defaultdict(int),
        'currency-standard': defaultdict(int),
        'currency-accounting': defaultdict(int)
    }

    for path in scan():
        o = json.load(open(path, 'rb'))
        m = o['main']
        for code in m.iterkeys():
            p = get(m, code, *DEC_STD)
            groups['decimal-standard'][p] += 1
            p = get(m, code, *PCT_STD)
            groups['percent-standard'][p] += 1
            p = get(m, code, *CUR_STD)
            groups['currency-standard'][p] += 1
            p = get(m, code, *CUR_ACC)
            groups['currency-accounting'][p] += 1

    for k, counts in groups.iteritems():
        print k.upper()
        recs = sorted((c, s) for s, c in counts.iteritems())
        for c, s in reversed(recs):
            print str(c).rjust(5), s.encode('utf-8')
        print '-'*74

if __name__ == '__main__':
    main()

