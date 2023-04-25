# Data Structures Project - Brian Salchert & Aden D'Occhio

---

## ❖・Running the Program・❖

To interact with and use the clothing store, simply run main.java in the terminal window. This will open the user interface for the store. You will be prompted to select an action from a list of available actions. Based on the action chosen, more actions will become available. In order to log in as an administrator, you will need to provide a password, which is set to "password". Logging in will provide access to the "Edit" action from the home menu, which allows for adding and removing items from the store while the program is running.

---

## ❖・Introduction・❖

The objective of this project is to design a program for managing a clothing shop. This includes managing several types of products and creating a user interface for searching through and sorting products. There are several data structures with which one may implement these features, and each has its advantages and disadvantages. In this document, we will discuss these different implementations and which is most preferable for the project.

---

## ❖・Design・❖

This section discusses the design choices made for the project. This includes which data structures we decided to use, which sorting algorithm we chose, runtimes and space complexities for various operations, and more.

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

Merge sort and quick sort have worst-case time complexities of 0(n×log(n)), making them much better candidates for large data sets than selection sort and insertion sort. Bucket sort technically has a worst-case time complexity of 0(n^2), although this is for the scenario in which every element ends up in the same bucket. If we implement our data such that the set is uniformly distributed across the set of buckets, the average time complexity becomes 0(n×log(n)), and we avoid the worst-case scenario of 0(n^2). On top of this, the best-case time complexity becomes 0(n), which outperforms merge and quick sort.

#### Chosen Sorting Algorithm

We will implement the sorting features of our clothing store using the bucket sort. By using randomized ID numbers for our items, we can ensure a uniform distribution of items across the buckets used for the sort, and thus we achieve sorting with 0(n×log(n)) time complexity on average, with a best-case of 0(n).

Bucket sort has a space complexity of 0(n), since it does not sort the items in place. However, space is not a large concern for this project, and as such this is not an issue.

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

All products inherit from the base product class. A product has a unique ID that is used to identify it from other products. A product also contains other information such as a title, type, and Values all which can be searched by.


---

### ❖・The Catalog・❖

The catalog class is responsible for all methods that manage the products, acting as the main entry point to the product database for the UI.

* Add Product
* Remove Product
* Get Product
* Search by value
* Search Suggestion

---

### ❖・Sorting・❖

In order to support sorting by multiple criteria and in different orders (low to high versus high to low), we created an enumeration called SortCategory that implements comparable. Each value in the enumeration has its own implementation of the compare() method, which allows us to define the order for that particular sort.

The catalog contains a method called bucketSort() that takes as input a SortCategory and a LinkedList of products. The buckets are initialized based on the type of sort being performed, and then each item in the list is added to its respective bucket (Example: items priced between $0.00 and $4.99 would be placed in the first bucket in a price-low-to-high sort). Then, each bucket is sorted using insertion sort, which is efficient for small input sizes (such as the size of the buckets). Finally, the results of the individual sorts are concatenated together into a LinkedList that is returned as output.

---

### ❖・Word Suggestion・❖

Word suggestion works by using a modified tree data structure where nodes of the tree contain an identifying character and results. Nodes can also have a children which represent possible paths. When a word is searched, it is broken into a character array that is used as a path to try and traverse the tree. If that path is part of the tree, then the value representing the string will be returned. If the path is not in the tree then it will be followed for as long as possible and return the results that could be found from the subtree starting at the character-path's end. The result that is closest to the search by edit actions will be returned as long as it doesn't exceed 3 actions otherwise no word is suggested. Run time is O(n), were n is the length of the character array.

---

### ❖・User Interface・❖

The User Interface (UI) for the store consists of methods corresponding to different menus or necessary inputs from the user. For example, there is a method called printHome() that prints the home menu of the store and then  calls a method called homeAction() to get the next action from the user. By designing the UI this way, we can easily return to any menu simply by calling its method.

User actions are handled mostly through the use of switch statements, with the cases being the lowercase selection of actions given to the user. The default case for each switch assumes that the user made an error in their input and calls the method again for the user to make another attempt.

When interacting with the catalog, the program keeps track of the most recently returned set of search results. This is the list of items that is used for sorting and other search actions. To accommodate a large number of items without over-populating the menu, we chose to limit the number of items per page of results to 20 products. When viewing searched or sorted products, the user can enter page numbers through the available "page" action to view different pages of the list of items.

For editing actions, we chose to allow administrative users to add or remove single items, or add in bulk a selection of random items. This allowed us to stress-test our program as well as measure the runtime of certain methods.

---

## ❖・Testing・❖

This section provides measured runtimes and details related to testing our program.

---

### ❖・Initializing the Store・❖

Upon running the program, the store populates the catalog with 10,000 items to start. It then records the runtime for this operation and outputs the result to the terminal.

    Output: Filled catalog in 141 milliseconds.

---

### ❖・Deleting Products・❖

Product removal is accessed through the administrative "Edit" menu and allows removal by product ID. For reference, 1 millisecond = 1,000 microseconds.

    Input: Enter the ID of a product to remove: 2
    Output: Removed product with ID 2 in 208 microseconds.

    Input: Enter the ID of a product to remove: 18000
    Output: Removed product with ID 18000 in 78 microseconds.

    Input: Enter the ID of a product to remove: 5789
    Output: Removed product with ID 5789 in 36 microseconds.

---

### ❖・Inserting Products・❖

Product adding is also accessed through the "Edit" menu and allows the user to add a specific product by specifying its characteristics or add many random products all at once.

Since the catalog is implemented using a HashMap, new products are inserted in arbitrary locations that the programmer does not choose. As such, we cannot specifically insert at the beginning, middle, or end of the HashMap. This is compounded by the fact that we assigned the products random IDs. However, we should expect similar runtime for any additions since adding to a HashMap is always a 0(1) operation.

    Test cases for the "Add" operation:

    Input: hat, green, small, 20, "cool green hat"
    Output: Product added to catalog in 220 microseconds.

    Input: shirt, blue, medium, 40, "blue shirt with pattern"
    Output: Product added to catalog in 226 microseconds.

    Input: plush, purple, xlarge, seal, 50, "enormous seal"
    Output: Product added to catalog in 121 microseconds.


    Test cases for the "AddBulk" operation:

    Input: 20
    Output: Added 20 products to the catalog in 325 microseconds.

    Input: 100
    Output: Added 100 products to the catalog in 569 microseconds.

    Input: 1000
    Output: Added 1000 products to the catalog in 4261 microseconds.

    Input: 2000
    Output: Could not add 2000 products: quantity too large.

    Input: -1
    Output: Invalid input -- please try again.

---

### ❖・Searching・❖

The following test cases will verify the accuracy of the product search function. The search allows search by title, ID, or attributes. For ease of reading, the actual search results are excluded here, although testing verifies that they do match the query.

    Input: red shirt
    Output: Found 602 results for the following query in 1221 microseconds:
    "red shirt", [Shirt, Red]

    Input: 961
    Output: Found 1 results for the following query in 30 microseconds:
    "961", []

---

### ❖・Word Correction・❖

The following test cases verify that some spelling errors are corrected by the program.

    Search input: rid shirt
    Output: Replaced rid with Red

    Search input: bleu pant medum
    Output: Replaced bleu with Blue
            Replaced pant with Pants
            Replaced medum with Medium

Several input actions as listed below are also reinterpreted by the program, although the program does not inform the user that they were corrected. Rather, it just continues with the interpreted action.

    Input: hoem
    Interpretation: "Home"

    Input: saerch
    Interpretation: "Search"

    Input: paeg
    Interpretation: "Page"

    Input: buyy
    Interpretation: "Buy"

---

## ❖・Conclusion・❖

The program successfully emulates a simple clothing store in the terminal window using Java. The word suggestion effectively corrects user input, and all methods sport efficient runtimes due to careful selection of the data structures and algorithms used.
