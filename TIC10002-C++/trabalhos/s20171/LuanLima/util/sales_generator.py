import sys
from random import randrange

months = ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez']
years = [10, 11, 12, 13, 14, 15, 16, 17]


if len(sys.argv) > 2:

    salesNumber = int(sys.argv[1])
    output = sys.argv[2]

    with open(output, "w") as f:
        for i in range(salesNumber):
            month = months[randrange(0, 12)]
            year = years[randrange(0, 8)]
            place_code = randrange(1, 40)
            saler_code = randrange(1, 30)
            total = randrange(1, 1000)
            f.write(str(place_code) + ", " + str(year) + "/" + str(month) + ", " +
                    str(saler_code) + ", " + str(total) + "\n")




else:
    print "Argument missed (how many sales?)"
    exit(1)
