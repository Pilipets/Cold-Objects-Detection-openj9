# Cold-Objects-Detection-openj9

The long-term goal of this project is to reduce the memory cost of running Java applications in consolidated cloud environments.\
The current project goal is identifying "cold" (rarely accessed) objects and/or memory ranges in the JVM heap ("temperature measurement").

## State of the problem:
Analysis of the existing papers, the approaches they used along the results achieved - Research_JVM.pdf

## Implementation in the Java interpreter only(-Xint) - without JIT compiler involved
Non-array objects: adding accessCount fields as hidden fields(like monitors) into the layout of the objects, counting each put/get/unsafe field accesses.\
Array objects: adding accessCount fields into the object headers. Currently, those don't work correctly in all the cases.

All the stats like access counters/age/size/ptr are collected on GC cycles - the time period between two GC cycles is more than N seconds or more than N GC cycles passed.\
Treat the first 4 bits of access counters as age(0-15) and the remaining 28 as access counters.

Picked dacapo benchmark sunflow/h2 workloads - https://github.com/dacapobench/dacapobench.

Took https://github.com/eclipse-openj9/openj9 and modified it to run with the changes above applied as https://github.com/Pilipets/openj9-omr/tree/research_jvm, https://github.com/Pilipets/openj9/tree/research_jvm. \
More details regarding running the setup and dumps analysis are in the notes folder or the next chapter. 

## Post-results analysis
Visuals available at https://github.com/Pilipets/Pilipets-Cold-Objects-Detection-openj9/blob/master/scripts/results.ipynb \
Clean code from ipynb - https://github.com/Pilipets/Pilipets-Cold-Objects-Detection-openj9/blob/master/scripts/results.py

There are 3 types of graphs:
- histogram with total size of objects for bins with access counts;
- age distribution of objects per bins above;
- cumulative size of objects for a given access count and multiple ages;

Access count bin selection is the following - take 10 percentiles of the access count distribution, then split the last bin to take 10 more percentiles.\
Did the snapshot dumps if the timing between two GC cycles is more than 5 seconds or more than 5 GC cycles passed.

PDF results can be viewed at https://github.com/Pilipets/Pilipets-Cold-Objects-Detection-openj9/blob/master/results/final_results/h2_no_decay.pdf

## TODO:
Look for the TODO.md of the repo for the details and thoughts regarding the steps below.

- Add more representative workloads;
- Define object decay/coldness metric formula;
- Implement the logic for JIT;
- Measure performance overhead;
