# TODO steps with thoughts and references:                                                                                                                              - Pick a more representative workload for analysis https://github.com/WASdev/sample.daytrader7, https://github.com/AlexeyKhrabrov/jitserver-benchmarks/tree/main/benchmarks/daytrader

- Fix array objects: Adding the accessCount field in the object headers doesn't work, as some applications still write into those fields. Can't store as a hidden field like monitor because there are no fields for arrays.
- Measure the overhead of the solution for the interpreter - run with and without the counting logic.
- Define heuristics for exponential decay for object counters. Which formula to use? What's the lowest value to decrease? What's the decrease rate? Decay = AccessCount * e^(-λ * TimeElapsed)
- Define coldness metric. Which access counter value to consider as cold? Probably, as a function of two parameters - max hot memory size(30% of the application footprint, for example) and performance overhead(estimate the performance cost for the movement/accessing of the objects>
- Write a few simple Java assert apps to confirm that logic works - catch the scenarios where array-like objects don't.
- Implement access counters logic for the JIT compiler.
- Address method invocations. When invoking a method, we will use the class pointer from the object header to get the offset. If no fields are accessed, we will miss such access. On the other hand, with JIT turned on, most of this stuff will be optimized, so without counting such >

- Implement the actual movement of the objects to cold areas
Evacuating or copy-forwarding: completely moving all objects out of a region to other regions;
Sliding: moving within the same region, just pushing/sliding them to the low addresses of the region so that the order of objects is preserved.
We are interested in evacuating - CopyForwardScheme.cpp with GenerationGC.

Two approaches:
- Create a special cold reserving context (or multiple of them if we want to have a few levels of coldness) that is for NUMA only now;
- Play with region ages and dedicate a special age (for example, maxage + 1 or -1) for cold objects;
