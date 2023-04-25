# Data Structures Project - Brian Salchert & Aden D'Occhio

---

## ❖・Running the Program・❖

To interact with and use the clothing store, simply run main.java in the terminal window. This will open the user interface for the store. You will be prompted to select an action from a list of available actions. Based on the action chosen, more actions will become available. In order to log in as an administrator, you will need to provide a password, which is set to "password". Logging in will provide access to the "Edit" action from the home menu, which allows for adding and removing items from the store while the program is running.

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

Merge sort and quick sort have worst-case time complexities of 0(n×log(n)), making them much better candidates for large data sets than selection sort and insertion sort. Bucket sort technically has a worst-case time complexity of 0(n^2), although this is for the scenario in which every element ends up in the same bucket. If we implement our data such that the set is uniformly distributed across the set of buckets, the average time complexity becomes 0(n×log(n)), and we avoid the worst-case scenario of 0(n^2).

#### Chosen Sorting Algorithm

We will implement the sorting features of our clothing store using the bucket sort. By using randomized ID numbers for our items, we can ensure a uniform distribution of items across the buckets used for the sort, and thus we achieve sorting with 0(n) time complexity on average.

---

### ❖・Searching Algorithms・❖

There are several sorting algorithms that could be used for this project, although none compare to the efficiency gained by using a hashmap to locate items. The hashmap allows us to search in 0(1) time complexity, and thus it is unnecessary to consider other sorting algorithms, as we cannot do any better than this. For the sake of argument, we will list a few sorting algorithms we could have chosen and their runtimes.

* Sequential Search: This searching algorithm is very simple, although it is extremely inefficient, returning the queried item in 0(n) time complexity in the worst-case.
* Binary Search: This algorithm affords us slightly better time complexity at 0(log(n)), although it requires the data set to be sorted beforehand.

---

## ❖・Implementation・❖

This section provides more detailed discussion of some of the code created for this project. This may include classes, enumerations, or even just methods that are worth examining in more detail.

---

### ❖・Products・❖



---

### ❖・The Catalog・❖



---

### ❖・Sorting・❖

In order to support sorting by multiple criteria and in different orders (low to high versus high to low), we created an enumeration called SortCategory that implements comparable. Each value in the enumeration has its own implementation of the compare() method, which allows us to define the order for that particular sort.

The Catalog contains a method called bucketSort() that takes as input a SortCategory and a LinkedList of products. The buckets are initialized based on the type of sort being performed, and then each item in the list is added to its respective bucket (Example: items priced between $0.00 and $4.99 would be placed in the first bucket in a price-low-to-high sort). Then, each bucket is sorted using Insertion Sort, which is efficient for small input sizes (such as the size of the buckets). Finally, the results of the individual sorts are concatenated together into a LinkedList that is returned as output.

---

### ❖・Word Suggestion・❖



---

### ❖・User Interface・❖

The User Interface (UI) for the store consists of methods corresponding to different menus or necessary inputs from the user. For example, there is a method called printHome() that prints the home menu of the store and then  calls a method called homeAction() to get the next action from the user. By designing the UI this way, we can easily return to any menu simply by calling its method.

User actions are handled mostly through the use of switch statements, with the cases being the lowercase selection of actions given to the user. The default case for each switch assumes that the user made an error in their input and calls the method again for the user to make another attempt.

When interacting with the Catalog, the program keeps track of the most recently returned set of search results. This is the list of items that is used for sorting and other search actions. To accommodate a large number of items without over-populating the menu, we chose to limit the number of items per page of results to 20 products. When viewing searched or sorted products, the user can enter page numbers through the available "page" action to view different pages of the list of items.

For editing actions, we chose to allow administrative users to add or remove single items, or add in bulk a selection of random items. This allowed us to stress-test our program as well as measure the runtime of certain methods.
