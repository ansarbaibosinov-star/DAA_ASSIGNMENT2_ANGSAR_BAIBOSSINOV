# DAA Assignment 2 - Data Structures

## 1. Project Description

This project implements three fundamental data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

The project evaluates their correctness, complexity, and performance using
several benchmark workloads.

The implementation uses primitive `int` values and does not use
`java.util.ArrayList`, `LinkedList`, or `PriorityQueue` in the main source code.

---

## 2. Data Structures

### DynamicArray

`DynamicArray` is implemented using a primitive `int[]` array.

Supported operations:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

When the internal array becomes full, its capacity is doubled.

The dynamic resizing provides amortized Θ(1) time for adding an element
to the end of the array.

---

### MyLinkedList

`MyLinkedList` is implemented using custom linked nodes.

Each node stores:

- an integer value;
- a reference to the next node.

The structure maintains both `head` and `tail` references.

Supported operations:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

---

### MinHeap

`MinHeap` is implemented using a primitive `int[]` array and the binary
min-heap representation.

Supported operations:

- `insert(x)`
- `peekMin()`
- `extractMin()`

The heap maintains the min-heap property after every insertion and
extraction.

---

## 3. Metrics

The benchmark counts three types of operations.

### Steps

One array-cell read or one move to the next linked-list node.

### Moves

One element shift in an array or one pointer/link update.

### Comparisons

One comparison between two elements.

The metrics are collected inside the implemented data structure methods.

---

## 4. Benchmark Workloads

The benchmark uses:

```text
n = 100
n = 1000
n = 10000
n = 100000
```
The same deterministic data is generated using:

Random(42)

Five measured runs are performed for every configuration.
The first run is discarded as a warm-up and the median of the five
measured runs is recorded.

W1 - Random Access

DynamicArray and MyLinkedList are filled with n values.

Then 10,000 random get(index) operations are performed.

This workload demonstrates the difference between constant-time array
access and linear-time linked-list access.

W2 - Search

DynamicArray and MyLinkedList are filled with n values.

Then 1,000 contains(x) queries are performed:

500 present values;
500 absent values.

This workload measures sequential search performance.

W3 - Insert & Remove

DynamicArray and MyLinkedList are tested using:

1,000 insertions at the head;
1,000 removals at the head;
1,000 insertions in the middle;
1,000 removals in the middle.

The workload demonstrates the cost of shifting array elements and
traversing linked-list nodes.

W4 - Priority Processing

MinHeap receives n inserted values.

Then extractMin() is executed n times.

The extracted values are checked to ensure that they are in
non-decreasing order.

Both insertion and extraction are included in the measured workload.

5. Testing

JUnit 5 tests are provided for all three data structures.

The tests cover:

normal operations;
empty structures;
one-element structures;
duplicate values;
first and last indices;
invalid indices;
automatic array resizing;
heap property after insertion;
heap property after extraction;
sorted heap output.

The project contains 22 JUnit tests.

6. How to Run
   Run tests
   mvn test
   Run benchmark and generate plots

Run:

src/main/java/com/daa/assignment2/Main.java

The benchmark creates:

results/results.csv

and the plots are saved in:

results/plots/
7. Results

The benchmark produces measurements for:

execution time;
steps;
moves;
comparisons.

The results are stored in CSV format and visualized using line charts.

The generated plots are located in:

results/plots/
8. Project Structure
   DAA_Assignment2
   ├── pom.xml
   ├── README.md
   ├── REPORT.md
   ├── results
   │   ├── results.csv
   │   └── plots
   └── src
   ├── main
   │   └── java
   │       └── com
   │           └── daa
   │               └── assignment2
   │                   ├── DataStructure.java
   │                   ├── DynamicArray.java
   │                   ├── MyLinkedList.java
   │                   ├── MinHeap.java
   │                   ├── Metrics.java
   │                   ├── BenchmarkResult.java
   │                   ├── Benchmark.java
   │                   ├── PlotGenerator.java
   │                   └── Main.java
   │
   └── test
   └── java
   └── com
   └── daa
   └── assignment2
   ├── DynamicArrayTest.java
   ├── MyLinkedListTest.java
   └── MinHeapTest.java
9. Restrictions

The main implementation does not use:

java.util.ArrayList
java.util.LinkedList
java.util.PriorityQueue

The data structures use primitive int storage.

Java standard library collections may be used in tests if needed.

10. Loop Invariants

Two loop invariant proofs are included in REPORT.md:

DynamicArray.contains()
MinHeap.siftDown()

The report contains initialization, maintenance, termination, and
conclusion for both invariants.