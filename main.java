import Attributes.*;
import DataStructure.*;
import SearchMap.*;
import Products.Product;
import Products.SortCategory;
import TestScripts.TestingMethods;

import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(10000);
        TestingMethods test = new TestingMethods(catalog);
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Filled catalog in " + test.load() / 1000000 + " milliseconds."); // fill Catalog and record time to fill

        printHome(catalog, false);

    }

    /**
     * Makes spelling propositions based on the user input for actions in the UI
     * @param action action entered by user
     * @param catalog catalog being navigated
     * @return proposed action
     */
    public static String actionPropositions(String action, Catalog catalog){
        for(Values.Actions x : Values.Actions.values()){ //looks to see if action is valid value
            if(x.name().equalsIgnoreCase(action)){
                return x.name();
            }
        }
        Proposition prop = catalog.getActionProp().proposition(action);
        if (prop == null) {
            return action;
        }
        return prop.name();
    }

    /**
     * Takes in a users search and tokenizes it to process and return the results of the search
     *
     * @param search  the users search
     * @param catalog the catalog being searched
     * @return results for products matching search
     */
    public static HashSet<Product> tokenizeSearch(String search, Catalog catalog) {
        HashMap<String, Values> validTokens = new HashMap<>();
        HashSet<Product> searchResults = new HashSet<>();

        for (Values attribute : Values.values()) {
            validTokens.put(attribute.name().toLowerCase(), attribute); // define valid tokens
        }

        Scanner tokenScan = new Scanner(search);

        while (tokenScan.hasNext()) { //extract possible tokens for search
            String token = tokenScan.next();
            if (validTokens.containsKey(token.toLowerCase())) { //looks if token is valid
                Values searchValue = validTokens.get(token.toLowerCase());
                searchValue.getCategory().getSearchSet().add(searchValue);
            }else{ //look if suggestion can be made
                Proposition p = catalog.getSearchProp().proposition(token);
                if (p instanceof Values v){
                    v.getCategory().getSearchSet().add(validTokens.get(v.name().toLowerCase()));
                    System.out.println("\tReplaced " + token + " with " + v.name()); //show user the replacement
                    System.out.println();
                }
            }
        }

        //get all possible search quires
        ArrayList<ArrayList<Values>> searchQueries = catalog.searchQueries(Values.Category.values(), new Values[Values.Category.values().length] , 0, 0, 0, new ArrayList<>());
        for (ArrayList<Values> query : searchQueries) {
            searchResults.addAll(catalog.getByAtt(query));

        }

        return searchResults;
    }

    /**
     * Prints the home page of the clothing store
     * @param catalog the catalog of products in the store
     * @param isAdmin true if the user is logged in as an administrator
     */
    public static void printHome(Catalog catalog, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################"); // 100 chars
        System.out.println();
        System.out.println("\tWelcome to the clothing store!");
        System.out.println("\tPlease choose an action below:");
        System.out.println();

        // Print either the normal menu or administrative menu based on user privilege
        if (isAdmin) {
            System.out.println("\tSearch (Search the catalog)\n\tEdit (Edit the catalog)\n\tExit (Exit the store)");
        }
        else {
            System.out.println("\tSearch (Search the catalog)\n\tLogin (Login as an administrator)\n\tExit (Exit the store)");
        }

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");


        String action = actionPropositions(input.nextLine(), catalog);

        homeAction(catalog, action, isAdmin);
    }

    /**
     * Function for handling actions from the home menu
     * @param catalog the catalog of products
     * @param action the action selected by the user
     * @param isAdmin true if the user is logged in as an administrator
     */
    private static void homeAction(Catalog catalog, String action, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "search" -> {
                // Set the current page to the first page
                int pageIndex = 0;

                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                // Get search query
                System.out.print("\tEnter your search query: ");
                HashSet<Product> searchSetResults = new HashSet<>();
                HashSet<Product> titleSetResults = new HashSet<>();
                String search = input.nextLine();

                long startTime;
                long endTime;
                long microseconds;

                startTime = System.nanoTime();

                try
                {   //clear search set from previous searches
                    for (Values.Category category : Values.Category.values()) {
                        category.getSearchSet().clear();
                    }
                    //search the catalog by ID
                    Product result = catalog.get(Integer.parseInt(search));
                    if(result != null){
                        searchSetResults.add(result);
                    }
                }
                catch (NumberFormatException e)
                {
                    // Search the catalog for the items whose title matches the query
                    if(catalog.getTitleCatalog().get(search.toLowerCase()) != null){
                        titleSetResults.addAll(catalog.getTitleCatalog().get(search.toLowerCase()));
                    }
                    // Search the catalog for the items matching the query
                    searchSetResults.addAll(tokenizeSearch(search, catalog));
                }

                ArrayList<Values> usedValues = new ArrayList<>();
                for(Values.Category category : Values.Category.values()){
                    usedValues.addAll(category.getSearchSet());
                }


                endTime = System.nanoTime();

                microseconds = ((endTime - startTime) / 1000);

                // Add title search results to the front of LinkedList searchResults
                LinkedList<Product> searchResults = new LinkedList<>(titleSetResults);

                // Add the other search results to searchResults
                for (Product product : searchSetResults) { //makes sure that title search results aren't printed twice
                    if(!titleSetResults.contains(product)){
                        searchResults.add(product);
                    }
                }

                // Print the search results
                System.out.println();
                System.out.println("\tFound " + searchResults.size() + " results for the following query in " + microseconds + " microseconds:");
                System.out.println("\t\"" + search + "\", " + usedValues);
                System.out.println();

                // Print all products for the current page of products
                printPage(searchResults, pageIndex);

                // Get the user's next action to perform on the search results
                getSearchAction(catalog, searchResults, isAdmin);
            }
            case "login" -> {
                if (!isAdmin) {
                    System.out.println();
                    System.out.println("####################################################################################################");
                    System.out.println();

                    // Request password
                    System.out.print("\tEnter password: ");
                    String password = input.nextLine();

                    // Verify password
                    if (password.equals("password")) {
                        isAdmin = true;
                    }

                    System.out.println();
                    System.out.println("####################################################################################################");
                    System.out.println();
                }
                else {
                    // Report to the user they are already logged in
                    System.out.println();
                    System.out.println("\tUser is already an admin.");
                    System.out.println();
                }

                printHome(catalog, isAdmin);
            }
            case "edit" -> {
                // Check for editing privileges
                if (isAdmin) {
                    getEditAction(catalog, isAdmin);
                }
                else {
                    System.out.println("\tUser does not have administrative privileges.");

                    printHome(catalog, isAdmin);
                }
            }
            case "exit" -> {
                break;
            }
            default -> {
                System.out.println();
                System.out.println("\tAction not recognized -- please try again.");

                // Return to home menu
                printHome(catalog, isAdmin);
            }
        }
    }

    /**
     * Function for getting the edit action from the user
     * @param catalog the catalog of products
     * @param isAdmin true if the user is logged in as an administrator
     */
    private static void getEditAction(Catalog catalog, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.println("\tPlease choose an edit action below:");
        System.out.println();
        System.out.println("\tAdd (add products to the catalog)\n\tAddBulk (add random products to the catalog)\n\tRemove (remove products from the catalog)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        // Correct the user input
        String action = actionPropositions(input.nextLine(), catalog);

        System.out.println();

        editAction(catalog, action, isAdmin);
    }

    /**
     * Responds to the edit action chosen by the user, allowing them to edit the catalog
     * @param catalog the catalog of products
     * @param action the user's action
     * @param isAdmin true if the user has administrative privileges
     */
    private static void editAction(Catalog catalog, String action, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "add" -> {
                // Ask the user for each attribute of the product
                Values type = getType();
                Values color = getColor();
                Values size = getSize();
                Values animal = null;
                if (type == Values.Plush) {
                    animal = getAnimal();
                }

                Values[] attributes;

                // Set the attribute list appropriately for plush/non-plush products
                if (animal != null) {
                    attributes = new Values[] {color, size, animal};
                }
                else {
                    attributes = new Values[] {color, size};
                }

                double price = getPrice();
                int daysAfterMinDay = getDaysAfterMinDay();
                String title = getTitle();

                // Add the product to the catalog
                long startTime;
                long endTime;

                startTime = System.nanoTime();

                catalog.addProduct(type, price, daysAfterMinDay, title, attributes);

                endTime = System.nanoTime();

                System.out.println();
                System.out.println("\tProduct added to catalog in " + (endTime - startTime) / 1000 + " microseconds.");

                // Return to the edit menu
                getEditAction(catalog, isAdmin);
            }
            case "addbulk" -> {
                TestingMethods bulk = new TestingMethods(catalog);

                // Add random products in bulk
                bulk.addBulk(getNumProducts());

                // Return to the edit menu
                getEditAction(catalog, isAdmin);
            }
            case "remove" -> {
                int id;
                System.out.print("\tEnter the ID of a product to remove: ");

                // Check for invalid input
                try {
                    id = Integer.parseInt(input.nextLine());
                }
                catch (NumberFormatException error) {
                    System.out.println();
                    System.out.println("\tInvalid ID.");

                    getEditAction(catalog, isAdmin);
                    break;
                }

                System.out.println();

                // Attempt to remove the product from the catalog
                if (catalog.containsID(id)) {
                    long startTime;
                    long endTime;

                    startTime = System.nanoTime();

                    catalog.removeProduct(id);

                    endTime = System.nanoTime();

                    System.out.println("\tRemoved product with ID " + id + " in " + (endTime - startTime) / 1000 + " microseconds.");

                    // Return to the edit menu
                    getEditAction(catalog, isAdmin);
                }
                else {
                    // Report that the product could not be removed
                    System.out.println("\tCould not remove ID " + id + ": ID does not exist.");

                    // Return to the edit menu
                    getEditAction(catalog, isAdmin);
                }
            }
            case "home" -> {
                printHome(catalog, isAdmin);
            }
            case "exit" -> {
                break;
            }
            default -> {
                System.out.println("\tEdit action not recognized -- please try again.");

                // Ask the user for input again
                getEditAction(catalog, isAdmin);
            }
        }
    }

    /**
     * Performs the post-search action input by the user
     * @param catalog the catalog of products
     * @param action the user's chosen action
     * @param isAdmin true if the user is logged in as an administrator
     */
    private static void searchAction(Catalog catalog, LinkedList<Product> searchResults, String action, boolean isAdmin) {
        switch (action.toLowerCase()) {
            case "page" -> {
                Scanner input = new Scanner(System.in);

                System.out.println();
                System.out.print("\tEnter a page number: ");

                // If the input is an integer as expected, print the page
                if (input.hasNextInt()) {
                    System.out.println();
                    printPage(searchResults, (input.nextInt() - 1));
                }
                else {
                    System.out.println();
                    System.out.println("\tInvalid input.");
                }

                // Return to the search menu
                getSearchAction(catalog, searchResults, isAdmin);
            }
            case "sort" -> {
                getSortCriterion(catalog, searchResults, isAdmin);
            }
            case "search" -> {
                homeAction(catalog, "search", isAdmin);
            }
            case "buy" -> {
                buyProduct(catalog, searchResults, isAdmin);
            }
            case "home" -> {
                printHome(catalog, isAdmin);
            }
            case "exit" -> {
                break;
            }
            default -> {
                System.out.println();
                System.out.println("\tAction not recognized -- please try again.");

                // Ask the user for input again
                getSearchAction(catalog, searchResults, isAdmin);
            }
        }
    }

    /**
     * Prompts the user to buy a product and removes it from the catalog if it exists
     * @param catalog the catalog of products
     * @param searchResults the list of searched products from the previous search
     * @param isAdmin true if the user has administrative privileges
     */
    private static void buyProduct(Catalog catalog, LinkedList<Product> searchResults, boolean isAdmin) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("\tEnter the ID of the product you wish to purchase:");
        System.out.print("\tProduct ID: ");

        int id = input.nextInt();

        System.out.println();

        // Remove the product from the catalog if it exists
        if (catalog.containsID(id)) {
            System.out.println("\tPurchased product: \"" + catalog.get(id).getTitle() + "\" for " + currencyFormatter.format(catalog.get(id).getPrice()) + ".");

            // Remove product from catalog and searchResults
            Product prodToBuy = catalog.get(id);
            catalog.removeProduct(id);
            searchResults.remove(prodToBuy);

            // Return to the search menu
            getSearchAction(catalog, searchResults, isAdmin);
        }
        else {
            System.out.println("\tInvalid ID -- cannot purchase product.");

            // Return to the search menu
            getSearchAction(catalog, searchResults, isAdmin);
        }
    }

    /**
     * Function for printing the search action selection menu and choosing the search action
     * @param catalog the catalog of products
     * @param searchResults the list of products matching the search results
     * @param isAdmin true if the user is logged in as an administrator
     */
    private static void getSearchAction(Catalog catalog, LinkedList<Product> searchResults, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.println("\tPlease choose a search action below:");
        System.out.println();
        System.out.println("\tPage (Change pages in search results)\n\tSort (Sort the list of searched items)\n\tSearch (New query)\n\tBuy (Buy a product)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        // Correct the user input if necessary
        String action =  actionPropositions(input.nextLine(), catalog);

        // Respond to the user input
        searchAction(catalog, searchResults, action, isAdmin);
    }

    /**
     * Function for printing the sort action selection menu and choosing the sort action
     * @param catalog the catalog of products
     * @param searchResults the list of products matching the search results
     * @param isAdmin true if the user is logged in as an administrator
     */
    private static void getSortCriterion(Catalog catalog, LinkedList<Product> searchResults, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.println("\tPlease choose a sort criterion below:");
        System.out.println();
        System.out.println("\tPriceMin (Price Ascending)\n\tPriceMax (Price Descending)\n\tDateOld (Listing Date Ascending)\n\tDateNew (Listing Date Descending)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter a criterion: ");

        // Correct the user input if necessary
        String action =  actionPropositions(input.nextLine(), catalog);

        // Sort the search results by the chosen criterion
        sort(catalog, searchResults, action, isAdmin);
    }

    /**
     * Sorts the list of search results by the criterion specified by the user
     * @param catalog the catalog of products
     * @param searchResults the list of products matching the search query
     * @param criterion the sort criterion chosen by the user
     * @param isAdmin true if the user is logged in as an admin
     */
    private static void sort(Catalog catalog, LinkedList<Product> searchResults, String criterion, boolean isAdmin) {
        Scanner input = new Scanner(System.in);
        // Set default page for sort to the first page of results
        int pageIndex = 0;

        switch (criterion.toLowerCase()) {
            case "pricemin" -> {
                // Bucketsort by minimum price
                searchResults = catalog.bucketSort(SortCategory.PriceCheapToExpensive, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by minimum price:");
                System.out.println();

                // Print the sorted search results
                printPage(searchResults, pageIndex);

                // Return to the sorting menu
                getSortAction(catalog, searchResults, isAdmin);
            }
            case "pricemax" -> {
                // Bucketsort by maximum price
                searchResults = catalog.bucketSort(SortCategory.PriceExpensiveToCheap, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by maximum price:");
                System.out.println();

                // Print the sorted search results
                printPage(searchResults, pageIndex);

                // Return to the sorting menu
                getSortAction(catalog, searchResults, isAdmin);
            }
            case "dateold" -> {
                // Bucketsort by oldest date
                searchResults = catalog.bucketSort(SortCategory.DateOldToNew, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by earliest listing date:");
                System.out.println();

                // Print the sorted search results
                printPage(searchResults, pageIndex);

                // Return to the sorting menu
                getSortAction(catalog, searchResults, isAdmin);
            }
            case "datenew" -> {
                // Bucketsort by newest date
                searchResults = catalog.bucketSort(SortCategory.DateNewToOld, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by latest listing date:");
                System.out.println();

                // Print the sorted search results
                printPage(searchResults, pageIndex);

                // Return to the sorting menu
                getSortAction(catalog, searchResults, isAdmin);
            }
            default -> {
                System.out.println();
                System.out.println("\tSort criterion not recognized -- please try again.");

                // Ask user for sort criterion again
                getSortCriterion(catalog, searchResults, isAdmin);
            }
        }
    }

    /**
     * Gets the sort action from the user
     * @param catalog the catalog of products
     * @param searchResults the list of products matching the search query
     * @param isAdmin true if the user is logged in as an admin
     */
    private static void getSortAction(Catalog catalog, LinkedList<Product> searchResults, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.println("\tPlease choose a sort action below:");
        System.out.println();
        System.out.println("\tPage (Change pages in the sorted results)\n\tSort (Choose a new sort criterion)\n\tSearch (New query)\n\tBuy (Buy a product)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        // Correct user input if necessary
        String action =  actionPropositions(input.nextLine(), catalog);

        // Respond to the user input
        sortAction(catalog, searchResults, action, isAdmin);
    }

    private static void sortAction(Catalog catalog, LinkedList<Product> searchResults, String action, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "page" -> {
                System.out.println();
                System.out.print("\tEnter a page number: ");

                // If the input is an integer as expected, print the page
                if (input.hasNextInt()) {
                    System.out.println();
                    printPage(searchResults, (input.nextInt() - 1));
                }
                else {
                    System.out.println();
                    System.out.println("\tInvalid input.");
                }

                // Return to the sort menu
                getSortAction(catalog, searchResults, isAdmin);
            }
            case "sort" -> {
                getSortCriterion(catalog, searchResults, isAdmin);
            }
            case "search" -> {
                homeAction(catalog, "search", isAdmin);
            }
            case "buy" -> {
                buyProduct(catalog, searchResults, isAdmin);
            }
            case "home" -> {
                printHome(catalog, isAdmin);
            }
            case "exit" -> {
                break;
            }
            default -> {
                System.out.println("\tAction not recognized -- please try again.");

                getSearchAction(catalog, searchResults, isAdmin);
            }
        }
    }

    /**
     * Gets the product type from an admin user
     * @return the chosen product category
     */
    private static Values getType() {
        Scanner input = new Scanner(System.in);

        System.out.println("\tChoose a product type:");
        System.out.println();
        System.out.println("\t\tHat\n\t\tShirt\n\t\tPants\n\t\tPlush");
        System.out.println();
        System.out.print("\tEnter product type: ");

        String choice = input.nextLine().toLowerCase();

        System.out.println();

        switch (choice) {
            case "hat" -> {
                return Values.Hat;
            }
            case "shirt" -> {
                return Values.Shirt;
            }
            case "pants" -> {
                return Values.Pants;
            }
            case "plush" -> {
                return Values.Plush;
            }
            default -> {
                System.out.println("\tInvalid choice -- please select again.");

                // Ask again for input
                return getType();
            }
        }
    }

    /**
     * Gets the product color from an admin user
     * @return the chosen color category
     */
    private static Values getColor() {
        Scanner input = new Scanner(System.in);

        System.out.println("\tChoose a color:");
        System.out.println();
        System.out.println("\t\tRed\n\t\tGreen\n\t\tBlue\n\t\tPurple");
        System.out.println();
        System.out.print("\tEnter product color: ");

        String choice = input.nextLine().toLowerCase();

        System.out.println();

        switch (choice) {
            case "red" -> {
                return Values.Red;
            }
            case "green" -> {
                return Values.Green;
            }
            case "blue" -> {
                return Values.Blue;
            }
            case "purple" -> {
                return Values.Purple;
            }
            default -> {
                System.out.println("\tInvalid choice -- please select again.");

                // Ask again for input
                return getColor();
            }
        }
    }

    /**
     * Gets the product size from an admin user
     * @return the chosen size category
     */
    private static Values getSize() {
        Scanner input = new Scanner(System.in);

        System.out.println("\tChoose a size:");
        System.out.println();
        System.out.println("\t\tSmall\n\t\tMedium\n\t\tLarge\n\t\tXLarge");
        System.out.println();
        System.out.print("\tEnter product size: ");

        String choice = input.nextLine().toLowerCase();

        System.out.println();

        switch (choice) {
            case "small" -> {
                return Values.Small;
            }
            case "medium" -> {
                return Values.Medium;
            }
            case "large" -> {
                return Values.Large;
            }
            case "xlarge" -> {
                return Values.XLarge;
            }
            default -> {
                System.out.println("\tInvalid choice -- please select again.");

                // Ask again for input
                return getSize();
            }
        }
    }

    /**
     * Gets the plush animal from an admin user
     * @return the chosen animal category
     */
    private static Values getAnimal() {
        Scanner input = new Scanner(System.in);

        System.out.println("\tChoose an animal:");
        System.out.println();
        System.out.println("\t\tCat\n\t\tDog\n\t\tSeal\n\t\tRabbit");
        System.out.println();
        System.out.print("\tEnter plush animal: ");

        String choice = input.nextLine().toLowerCase();

        System.out.println();

        switch (choice) {
            case "cat" -> {
                return Values.Cat;
            }
            case "dog" -> {
                return Values.Dog;
            }
            case "seal" -> {
                return Values.Seal;
            }
            case "rabbit" -> {
                return Values.Rabbit;
            }
            default -> {
                System.out.println();
                System.out.println("\tInvalid choice -- please select again.");

                // Ask again for input
                return getAnimal();
            }
        }
    }

    /**
     * Gets the days after Jan 1st 2020 a new product is listed
     * @return days after the minimum listing date
     */
    private static int getDaysAfterMinDay() {
        Calendar minDay = Calendar.getInstance();
        // Set the minDay to Jan 1st, 2020
        minDay.set(Calendar.YEAR, 2020);
        minDay.set(Calendar.DAY_OF_YEAR, 1);

        Date current = new Date();

        // Get the nanosecond values for the minimum listing date and the current date
        Temporal start = minDay.toInstant();
        Temporal end = current.toInstant();

        // Return the days after the minimum listing date
        // Testing revealed that the date needed to be corrected by two days
        return (int) (ChronoUnit.DAYS.between(start, end) + 2);
    }

    /**
     * Gets the product price from an admin user
     * @return the chosen price
     */
    private static double getPrice() {
        Scanner input = new Scanner(System.in);

        System.out.print("\tEnter a listing price: ");

        // Get a valid price from the user
        if (input.hasNextDouble()) {
            System.out.println();

            return input.nextDouble();
        }
        else {
            System.out.println();
            System.out.println("\tInvalid price -- please select again.");

            return getPrice();
        }
    }

    /**
     * Gets the product title from an admin user
     * @return the chosen title
     */
    private static String getTitle() {
        Scanner input = new Scanner(System.in);

        System.out.print("\tEnter a product title: ");

        return input.nextLine();
    }

    /**
     * Gets the number of products to add in bulk from an admin user
     * @return the number of products to add
     */
    private static int getNumProducts() {
        Scanner input = new Scanner(System.in);

        System.out.print("\tEnter the number of random products to add: ");

        if (input.hasNextInt()) {
            System.out.println();

            return input.nextInt();
        }
        else {
            System.out.println();
            System.out.println("\tInvalid input -- please try again.");

            return getNumProducts();
        }
    }

    private static void printPage(LinkedList<Product> searchResults, int pageIndex) {
        // Set the number of items to display per page
        final int pageSize = 20;
        // Set the max page index
        int maxPageIndex = (searchResults.size() / pageSize);
        int endIndex;

        // Ensure validity of the pageIndex
        if (pageIndex > maxPageIndex || pageIndex < 0) {
            System.out.println("\tInvalid page index.");

            return;
        }

        // Set the endIndex for printing to the minimum of the index of the max page length or
        // the end of the search results
        endIndex = Math.min(((pageIndex + 1) * pageSize), searchResults.size());

        // Print catalog header
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("\tID     Item Name               Product     Attributes                  Price       Listing Date");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Print all products for the current page of products
        for (int productIndex = (pageIndex * pageSize); productIndex < endIndex; productIndex++) {
            System.out.println("\t" + searchResults.get(productIndex));
        }

        // Inform the user of what page they are on and how many pages there are
        System.out.println();
        System.out.println("\tPage: " + (pageIndex + 1) + "/" + (maxPageIndex + 1));
    }
}
