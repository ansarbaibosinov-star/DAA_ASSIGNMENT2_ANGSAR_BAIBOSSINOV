# DAA Assignment 2 - Data Structures
## In-Memory Workload Engine

---

## 1. Introduction

This project implements and evaluates three fundamental data structures
from scratch: DynamicArray, MyLinkedList, and MinHeap.

The goal is to compare their theoretical complexity and practical
performance using several controlled workloads.

The implementation uses primitive `int` values. The main source code does
not use Java's built-in `ArrayList`, `LinkedList`, or `PriorityQueue`.

The evaluation considers execution time, array or node access steps,
element or pointer moves, and element comparisons.

---

# 2. Data Structures

## 2.1 DynamicArray

DynamicArray stores elements in a contiguous primitive `int[]` array.

When the array becomes full, its capacity is doubled and the existing
elements are copied into the new array.

The main operations are:

- add(x)
- add(index, x)
- remove(index)
- get(index)
- contains(x)

The structure provides constant-time random access because an element
can be accessed directly using its array index.

Insertion and removal at arbitrary positions may require shifting many
elements.

---

## 2.2 MyLinkedList

MyLinkedList is implemented using custom nodes.

Each node contains:

- an integer value;
- a reference to the next node.

The structure maintains references to both the head and the tail.

The main operations are:

- add(x)
- add(index, x)
- remove(index)
- get(index)
- contains(x)

The list does not require contiguous memory, but indexed access requires
traversing nodes from the head.

---

## 2.3 MinHeap

MinHeap is implemented using a primitive `int[]` array.

The heap follows the binary min-heap property:

> Every parent is less than or equal to its children.

The main operations are:

- insert(x)
- peekMin()
- extractMin()

Insertion uses bubble-up.

Extraction replaces the root with the last element and restores the
heap property using sift-down.

---

# 3. Complexity Analysis

The following table summarizes the asymptotic complexity of the implemented
operations.

| Data Structure | Operation | Best | Average | Worst |
|---|---|---:|---:|---:|
| DynamicArray | add(x) | Θ(1) | Θ(1) amortized | O(n) |
| DynamicArray | add(index, x) | Θ(1) | Θ(n) | Θ(n) |
| DynamicArray | remove(index) | Θ(1) | Θ(n) | Θ(n) |
| DynamicArray | get(index) | Θ(1) | Θ(1) | Θ(1) |
| DynamicArray | contains(x) | Θ(1) | Θ(n) | Θ(n) |
| MyLinkedList | add(x) | Θ(1) | Θ(1) | Θ(1) |
| MyLinkedList | add(index, x) | Θ(1) | Θ(n) | Θ(n) |
| MyLinkedList | remove(index) | Θ(1) | Θ(n) | Θ(n) |
| MyLinkedList | get(index) | Θ(1) | Θ(n) | Θ(n) |
| MyLinkedList | contains(x) | Θ(1) | Θ(n) | Θ(n) |
| MinHeap | insert(x) | Θ(1) | Θ(log n) | Θ(log n) |
| MinHeap | peekMin() | Θ(1) | Θ(1) | Θ(1) |
| MinHeap | extractMin() | Θ(1) | Θ(log n) | Θ(log n) |

### Notes

For `DynamicArray.add(x)`, the normal insertion is Θ(1), but resizing
requires copying all existing elements. Therefore the amortized complexity
is Θ(1), while a single resize operation can take Θ(n).

For `MyLinkedList.add(x)`, the implementation maintains a tail reference,
so adding at the end does not require traversal.

For indexed linked-list operations, the implementation must traverse the
list to reach the required position, resulting in Θ(n) worst-case time.

For `MinHeap.insert(x)`, the new element may move from a leaf to the root,
requiring at most the height of the heap, which is Θ(log n).

For `MinHeap.extractMin()`, the replacement element may move from the root
to a leaf, also requiring Θ(log n) in the worst case.

---

# 4. Auxiliary Space Complexity

| Data Structure | Auxiliary Space |
|---|---:|
| DynamicArray | Θ(n) |
| MyLinkedList | Θ(n) |
| MinHeap | Θ(n) |

DynamicArray requires memory for its internal array.

MyLinkedList requires one node object for each stored element.

MinHeap requires an internal primitive array whose capacity grows with
the number of elements.

The benchmark itself additionally allocates input arrays and temporary
benchmark data.

---

# 5. Metrics

Three operation counters were used.

## Steps

One array-cell read or one movement to the next linked-list node.

Examples:

- reading `data[index]` counts as one step;
- moving from one linked-list node to `current.next` counts as one step.

## Moves

One element shift in an array or one link/pointer update.

Examples:

- shifting an array element during insertion;
- changing a linked-list pointer;
- swapping heap elements.

## Comparisons

One comparison between two elements.

Examples:

- comparing two values in `contains`;
- comparing a heap parent with a child.

The counters are implemented inside the data structure operations.

---

# 6. Benchmark Methodology

The benchmark uses four workloads.

The input sizes are:

```text
100
1000
10000
100000
```
The same deterministic data is generated using:

Random(42)

Each configuration has:

one warm-up run;
five measured runs.

The first warm-up run is discarded.

The median of the five measured execution times is written to
results/results.csv.

This approach reduces the influence of JVM warm-up and individual
execution-time fluctuations.

7. Workload W1 - Random Access

W1 compares indexed access in DynamicArray and MyLinkedList.

For each input size:

The structure is filled with n values.
10,000 random indices are generated.
get(index) is executed for every generated index.
Execution time and operation counters are recorded.

The expected theoretical difference is significant.

DynamicArray provides Θ(1) indexed access because array elements are stored
contiguously.

MyLinkedList requires traversal from the head, resulting in Θ(n) indexed
access in the worst case.

W1 Time

W1 Operations

The W1 results demonstrate the strong difference between direct array
indexing and linked-list traversal.

As n increases, the number of node traversals for MyLinkedList grows
substantially.

DynamicArray performs approximately the same number of measured get
operations regardless of n, while the linked list performs more node
steps as the average requested index becomes larger.

8. Workload W2 - Search

W2 compares linear search using contains(x).

For every input size:

The structure is filled with n values.
1,000 search queries are generated.
500 queries use values that are present.
500 queries use values that are absent.
The search operations are measured.

Both structures have Θ(n) worst-case search complexity.

The main difference is memory layout and access behavior.

W2 Time

W2 Operations

The operation counts are similar because both structures perform a linear
search through their elements.

However, the execution time can differ because DynamicArray stores its
elements contiguously, while MyLinkedList follows pointers between
separately allocated nodes.

9. Workload W3 - Insert & Remove

W3 evaluates insertion and removal at two positions:

head;
middle.

For each position, 1,000 insertions and 1,000 removals are performed.

W3 Time

W3 Operations

For DynamicArray, insertion and removal in the middle require shifting
elements.

Therefore, a large number of array moves are produced.

At the head, the number of shifted elements is particularly large because
many existing elements must be moved.

For MyLinkedList, changing a link itself is inexpensive once the correct
node is reached.

However, indexed insertion and removal still require traversal to the
target position.

Therefore, the linked list can perform well for operations near the head,
while middle operations still require substantial traversal.

10. Workload W4 - Priority Processing

W4 evaluates MinHeap.

For each input size:

n values are inserted into the heap.
extractMin() is executed n times.
The extracted sequence is checked to be non-decreasing.
Both insertion and extraction are included in the measured time.
W4 Time

W4 Operations

The heap maintains the smallest value at the root.

Each insertion can move an element upward through the heap, while each
extraction can move an element downward.

Therefore, both operations have logarithmic worst-case complexity.

The complete W4 workload is suitable for priority processing because it
repeatedly inserts values and removes the minimum value.

11. Loop Invariant Proof 1 - DynamicArray.contains()

The first loop invariant is applied to the loop inside
DynamicArray.contains().

The loop examines the array from left to right.

Invariant

Before every iteration with index i, all elements at positions

0 ... i - 1

have already been checked, and none of them is equal to the searched
value x.

Initialization

At the beginning of the loop:

i = 0

There are no previously checked elements.

Therefore, the invariant is true because the empty set of checked
elements contains no element equal to x.

Maintenance

During the current iteration, the algorithm compares:

data[i] == x

If the condition is true, the method immediately returns true, which
is correct because the searched value has been found.

If the condition is false, then:

data[i] != x

The algorithm moves to the next index.

Therefore, after incrementing i, all positions from 0 through i - 1
have been checked and none contains x.

Thus, the invariant is preserved.

Termination

The loop terminates when:

i == size

At this point every element from index 0 to size - 1 has been checked.

According to the invariant, none of these elements is equal to x.

Therefore, returning false is correct.

Conclusion

The loop invariant proves that DynamicArray.contains(x) correctly
returns true if the value exists in the array and false otherwise.

12. Loop Invariant Proof 2 - MinHeap.siftDown()

The second loop invariant is applied to the siftDown() operation of
MinHeap.

Invariant

Before every iteration of the siftDown() loop:

The subtrees below the current node are valid min-heaps, and the only
possible violation of the min-heap property is at the current node.

Initialization

After extractMin() removes the root, the last element is moved to the
root.

Before siftDown() begins, all subtrees below the root remain valid
min-heaps because their internal relationships have not changed.

Therefore, the only possible violation is at the current root.

The invariant is true.

Maintenance

The algorithm determines the smaller of the left and right children.

If the current element is smaller than or equal to the smaller child,
the min-heap property is satisfied and the loop terminates.

Otherwise, the current element is swapped with the smaller child.

The violation is then moved to the child's position.

The subtrees below that child were already valid min-heaps, so the only
possible violation is now at the new current position.

Therefore, the invariant is preserved.

Termination

The loop terminates in one of two cases.

First, the current node has no left child. It is therefore a leaf and
cannot violate the heap property.

Second, the current node is less than or equal to its smaller child.
Therefore, it satisfies the min-heap property.

In both cases the heap property has been restored.

Conclusion

The loop invariant proves that siftDown() correctly restores the
min-heap property after removing the minimum element.

13. Performance Discussion

DynamicArray benefits from contiguous memory and good cache locality.
When consecutive elements are accessed, the CPU cache can load nearby
memory efficiently because of spatial locality.
This makes sequential access through an array very efficient in practice.
DynamicArray also provides direct indexed access without requiring
pointer traversal.
However, insertion and removal near the beginning of the array require
many elements to be shifted.
MyLinkedList avoids large array shifts because insertion and removal can
be performed by changing links.
However, linked-list nodes are stored separately in memory, which leads
to pointer chasing during traversal.
Each node also has object-related memory overhead in addition to storing
the integer and reference.
The allocation of many nodes can also increase garbage-collection
pressure.
Therefore, DynamicArray is usually preferable for random access and
memory-efficient sequential storage.
MyLinkedList can be advantageous when frequent link changes are required
and the required node has already been located.
For priority-processing workloads, MinHeap is preferable because it
provides logarithmic insertion and minimum extraction.
A heap is particularly useful when the application repeatedly needs the
smallest element rather than arbitrary indexed access.
The benchmark results demonstrate that theoretical complexity and memory
layout both influence practical performance.

14. Correctness Testing

The test suite contains 22 JUnit tests.

The tests verify:

normal insertion;
indexed insertion;
removal;
indexed access;
searching;
dynamic resizing;
invalid indices;
empty structures;
one-element structures;
duplicate values;
heap property;
sorted heap extraction.

The heap property is checked after insertions and extractions.

The final extracted sequence is also verified to be non-decreasing.

15. Conclusion

The project demonstrates the practical differences between dynamic arrays,
linked lists, and binary min-heaps.

DynamicArray provides excellent indexed access and good cache locality,
while MyLinkedList requires traversal for indexed operations.

DynamicArray insertion and removal at arbitrary positions can require many
element moves.

MyLinkedList can change links without shifting a large block of elements,
but traversal and memory locality remain important factors.

MinHeap provides efficient logarithmic-time priority operations and
constant-time access to the minimum element.

The benchmark combines theoretical analysis with measured execution time
and operation counters.

Overall, the best data structure depends on the operations required by
the workload rather than on one structure being universally faster.