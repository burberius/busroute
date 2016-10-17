# Bus Route Rest Service

## Assumptions
* Routes are operated in both directions, so you can drive A -> B and B -> A

## Performance
You can generate a big data file with DemoDataGenerator. One generated file of 27MB 
with 4020016 station entries is placed under *src/test/resources* and used for 
the performance evaluation.

In the project root is a *performance-test.sh* script that runs 10 different queries
with 5 misses and 5 hits. It prints the performance in seconds with millisecond
resolution.

### H2 database with join
Results:
5,468
1,660
8,283
2,664
6,234
6,141
6,409
6,166
6,230
1,732

### HSQLDB database with join
Results:
8,945
1,457
7,117
2,333
5,212
5,216
5,201
5,237
5,227
1,384


### Derby database with join
Starting up took extremely long (27 minutes) compared to the other databases!

Results:
8,662
2,682
12,006
3,913
9,209
9,300
9,273
9,420
9,262
2,721


### Simple in memory storage as set
This stores the connections as string of lower station number + space + higher station number.
Example "4 8". Via the set redundant entries are neglected. In this approach the route number
is completely ignored!

You can switch the storage by creating a file *application.properties* and adding the line
```
storage=mem
```

Unfortunately the application didn't start up within 60 minutes!

Results with 1000 lines / 969993 entries:
0,422
0,034
0,023
0,020
0,016
0,013
0,013
0,013
0,024
0,023

Results with 1000 lines / 39759 on HSQLDB:
0,592
0,054
0,065
0,046
0,059
0,042
0,037
0,052
0,054
0,033

The logfile of the service showed a very fast response time of the mem storage against the db
version.

### More posibilities
* Use a standalone database, for example MySQL
* Add an index
* Store entries with source and destination in the database, without route number, so no join is needed

## Result
It looks as if for smaller datasets the memory storage via a set has the highest performance. But the 
disadvantage is the high startup time. From the in memory database implementations HSQLDB is the fastest.
