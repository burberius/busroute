# Bus Route Rest Service

## Assumptions
* Routes are operated in both directions, so you can drive A -> B and B -> A

## Performance
You can generate a big data file with DemoDataGenerator.
The test with a 27MB file with 4020016 station entries resulted in 6567ms for one query using a H2 backend.

More tests to come...
* other DB like Derby and HSQLDB
* try a simple HashMap
