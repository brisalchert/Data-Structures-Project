# Data Structures Project - Brian Salchert & Aden D'Occhio

---

## ❖・Introduction・❖

The objective of this project is to design a program for managing a clothing shop. This includes managing several types of products and creating a user interface for searching through and sorting products. There are several data structures with which one may implement these features, and each has its advantages and disadvantages. In this document, we will discuss these different implementations and which is most preferable for the project.

---

### ❖・Data Structures・❖

We have several options for which data structure we should use to implement the clothing store itself:

* Array
* Linked List
* Tree
* Hashmap

We can start by ruling out the tree. Trees are useful for storing individual nodes in a hierarchical structure -- this project does not lend itself to that kind of data storage. That leaves us with just three options: the array, the linked list, and the hashmap.

#### The Array

Advantages:

* Storing the clothing store's items in arrays for each item type is perhaps the simplest way to implement the store's data. It requires very little effort to set up and is easy to understand.
* Inserting or deleting an item at the end of an array for a particular item type is an 0(1) operation.

Disadvantages:

* Inserting or deleting an item at the beginning or middle of an array for a particular item type is an 0(n) operation, which is very costly compared to other options.
* Sorting an array in-place requires many deletions and insertions which become very costly with large data sets. Using another temporary array alleviates this issue to some degree, but requires more memory space.
* Searching an array is not an 0(1) operation, which is a requirement for this project.

#### The Linked List

Advantages:

* Inserting or deleting an item in a linked list is an 0(1) operation, regardless of the item's location in the list.
* Sorting a linked list in-place is not nearly as costly as with an array, since we only need to adjust the links of the nodes.

Disadvantages:

* Searching a linked list is not an 0(1) operation, which is a requirement for this project.

#### The Hashmap

Advantages:

* Inserting or deleting an item in a hashmap is an 0(1) operation, regardless of the item's location.
* Searching a hashmap is an 0(1) operation, making it the best option for this feature.

Disadvantages:

* The structure of the hashmap is necessary for its function, and thus we cannot perform sorting algorithms directly on it. However, Java's built-in HashMap API is iterable and includes a definition for the iterator() method, and thus we can extract the items for sorting using an iterator.

#### Chosen Data Structure

Given the pros and cons of each data structure listed above, we will choose to use the hashmap in our implementation. The hashmap allows for efficient insertion and deletion, and most importantly, 0(1) searching. While we cannot sort the hashmap directly, we can easily extract the data from the hashmap using an iterator, which is not very costly (extraction with an iterator is 0(n), and all possible sorting algorithms will be at least 0(n), meaning, in theory, this does not create any inefficiency at all).

---

### ❖・Sorting Algorithms・❖

We have many options for sorting algorithms to use for our data. Here are a few worth considering:

* Selection Sort
* Insertion Sort
* Merge Sort
* Quick Sort
* Bucket Sort

Of these options, we can first exclude selection sort and insertion sort, since both of these algorithms have a worst-case time complexity of 0(n^2). This leaves us with merge sort, quick sort, and bucket sort.

Merge sort and quick sort have worst-case time complexities of 0(n*log(n)), making them much better candidates for large data sets than selection sort and insertion sort. Bucket sort technically has a worst-case time complexity of 0(n^2), although this is for the scenario in which every element ends up in the same bucket. If we implement our data such that the set is uniformly distributed across the set of buckets, the average time complexity becomes 0(n), and we avoid the worst-case scenario of 0(n^2).

#### Chosen Sorting Algorithm

We will implement the sorting features of our clothing store using the bucket sort. By using randomized ID numbers for our items, we can ensure a uniform distribution of items across the buckets used for the sort, and thus we achieve sorting with 0(n) time complexity on average.

---

### ❖・Searching Algorithms・❖

There are several sorting algorithms that could be used for this project, although none compare to the efficiency gained by using a hashmap to locate items. The hashmap allows us to search in 0(1) time complexity, and thus it is unnecessary to consider other sorting algorithms, as we cannot do any better than this. For the sake of argument, we will list a few sorting algorithms we could have chosen and their runtimes.

* Sequential Search: This searching algorithm is very simple, although it is extremely inefficient, returning the queried item in 0(n) time complexity in the worst-case.
* Binary Search: This algorithm affords us slightly better time complexity at 0(log(n)), although it requires the data set to be sorted beforehand.

---

## ❖・Implementation・❖
