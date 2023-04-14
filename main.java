import Attributes.*;
import DataStructure.*;
import SearchMap.*;
import Products.Product;
import Products.SortCategory;
import TestScripts.TestingMethods;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        Catalog catalog = new Catalog(200);
        TestingMethods test = new TestingMethods(catalog);
        PropositionTree searchMap = new PropositionTree();
        Scanner input = new Scanner(System.in);

        test.load(); //fill Catalog

        printHome(catalog, false);

    }


    public static LinkedList<Product> tokenizeSearch(String search, Catalog catalog) {
        HashMap<String, Values> validTokens = new HashMap<>();
        LinkedList<Product> searchResults = new LinkedList<>();
        ArrayList<Values> usedValues = new ArrayList<>();

        for (Values.Category category : Values.Category.values()) {
            category.getSearchSet().clear();
        }

        for (Values attribute : Values.values()) {
            validTokens.put(attribute.name().toLowerCase(), attribute);
        }

        Scanner tokenScan = new Scanner(search);

        while (tokenScan.hasNext()) {
            String token = tokenScan.next();
            if (validTokens.containsKey(token.toLowerCase())) {
                Values searchValue = validTokens.get(token.toLowerCase());
                searchValue.getCategory().getSearchSet().add(searchValue);
            }else{
                if(!catalog.getSearchMap().fillerWords.contains(token.toLowerCase())){
                    for(Values v : catalog.getSearchMap().proposition(token)){
                        v.getCategory().getSearchSet().add(validTokens.get(v.name().toLowerCase()));
                        System.out.println("\tReplaced " + token + " with " + v.name());
                        System.out.println();
                        break;
                    }
                }

            }
        }

        ArrayList<ArrayList<Values>> searchQueries = catalog.searchQueries(Values.Category.values(), new Values[Values.Category.values().length] , 0, 0, 0, new ArrayList<>());
        for (ArrayList<Values> query : searchQueries) {
            searchResults.addAll(catalog.getByAtt(query));

            for (Values value : query) {
                // Check if the value has not been used in a previous query
                if (!usedValues.contains(value)) {
                    usedValues.add(value);
                }
            }
        }

        System.out.println("\tFound " + searchResults.size() + " results for the following query:");
        System.out.println("\t" + usedValues);
        System.out.println();

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("\tID     Item Name               Product     Attributes                  Price       Listing Date");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (Product product : searchResults) {
            System.out.println("\t" + product);
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

        String action = input.nextLine();
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
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                System.out.print("\tEnter your search query: ");
                String search = input.nextLine();

                System.out.println();

                LinkedList<Product> searchResults = tokenizeSearch(search, catalog);

                getSearchAction(catalog, searchResults, isAdmin);
            }
            case "login" -> {
                if (!isAdmin) {
                    System.out.println();
                    System.out.println("####################################################################################################");
                    System.out.println();

                    System.out.print("\tEnter password: ");
                    String password = input.nextLine();

                    if (password.equals("password")) {
                        isAdmin = true;
                    }

                    System.out.println();
                    System.out.println("####################################################################################################");
                    System.out.println();
                }
                else {
                    System.out.println();
                    System.out.println("\tUser is already an admin.");
                    System.out.println();
                }

                printHome(catalog, isAdmin);
            }
            case "edit" -> {
                if (isAdmin) {
                    getEditAction(catalog, isAdmin);
                }
                else {
                    System.out.println();
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
        System.out.println("\tAdd (add products to the catalog)\n\tRemove (remove products from the catalog)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        String action = input.nextLine();

        System.out.println();

        editAction(catalog, action, isAdmin);
    }

    private static void editAction(Catalog catalog, String action, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "add" -> {
                Values type = getType();
                Values color = getColor();
                Values size = getSize();
                Values animal = null;
                if (type == Values.Plush) {
                    animal = getAnimal();
                }

                Values[] attributes;

                if (animal != null) {
                    attributes = new Values[] {color, size, animal};
                }
                else {
                    attributes = new Values[] {color, size};
                }

                double price = getPrice();
                int daysAfterMinDay = getDaysAfterMinDay();
                String title = getTitle();

                catalog.addProduct(type, price, daysAfterMinDay, title, attributes);

                System.out.println();
                System.out.println("Product added to catalog.");

                getEditAction(catalog, isAdmin);
            }
            case "remove" -> {
                int id;
                System.out.print("Enter the ID of a product to remove: ");

                try {
                    id = Integer.parseInt(input.nextLine());
                }
                catch (NumberFormatException error) {
                    System.out.println();
                    System.out.println("Invalid ID.");

                    getEditAction(catalog, isAdmin);
                    break;
                }

                System.out.println();

                if (catalog.containsID(id)) {
                    catalog.removeProduct(id);

                    System.out.println("Removed product with ID " + id + ".");

                    getEditAction(catalog, isAdmin);
                }
                else {
                    System.out.println("Could not remove ID " + id + ": ID does not exist.");

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
                System.out.println("Edit action not recognized -- please try again.");

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

                getSearchAction(catalog, searchResults, isAdmin);
            }
        }
    }

    private static void buyProduct(Catalog catalog, LinkedList<Product> searchresults, boolean isAdmin) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("\tEnter the ID of the product you wish to purchase:");
        System.out.print("\tProduct ID: ");

        int id = input.nextInt();

        System.out.println();

        if (catalog.containsID(id)) {
            System.out.println("\tPurchased product: \"" + catalog.get(id).getTitle() + "\" for " + currencyFormatter.format(catalog.get(id).getPrice()) + ".");

            // Remove product
            catalog.removeProduct(id);

            getSearchAction(catalog, searchresults, isAdmin);
        }
        else {
            System.out.println("\tInvalid ID -- cannot purchase product.");

            getSearchAction(catalog, searchresults, isAdmin);
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
        System.out.println("\tSort (Sort the list of searched items)\n\tSearch (New query)\n\tBuy (Buy a product)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        String action = input.nextLine();

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

        String action = input.nextLine();
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

        switch (criterion.toLowerCase()) {
            case "pricemin" -> {
                searchResults = catalog.bucketSort(SortCategory.PriceCheapToExpensive, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by minimum price:");
                System.out.println();

                // Print the sorted search results
                for (Product product : searchResults) {
                    System.out.println("\t" + product);
                }

                getSortAction(catalog, searchResults, isAdmin);
            }
            case "pricemax" -> {
                searchResults = catalog.bucketSort(SortCategory.PriceExpensiveToCheap, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by maximum price:");
                System.out.println();

                // Print the sorted search results
                for (Product product : searchResults) {
                    System.out.println("\t" + product);
                }

                getSortAction(catalog, searchResults, isAdmin);
            }
            case "dateold" -> {
                searchResults = catalog.bucketSort(SortCategory.DateOldToNew, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by earliest listing date:");
                System.out.println();

                // Print the sorted search results
                for (Product product : searchResults) {
                    System.out.println("\t" + product);
                }

                getSortAction(catalog, searchResults, isAdmin);
            }
            case "datenew" -> {
                searchResults = catalog.bucketSort(SortCategory.DateNewToOld, searchResults);

                System.out.println();
                System.out.println("\tSearch results sorted by latest listing date:");
                System.out.println();

                // Print the sorted search results
                for (Product product : searchResults) {
                    System.out.println("\t" + product);
                }

                getSortAction(catalog, searchResults, isAdmin);
            }
            default -> {
                System.out.println();
                System.out.println("\tSort criterion not recognized -- please try again.");

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
        System.out.println("\tSort (Choose a new sort criterion)\n\tSearch (New query)\n\tBuy (Buy a product)\n\tHome (Return to the homepage)\n\tExit (Exit the store)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        String action = input.nextLine();

        sortAction(catalog, searchResults, action, isAdmin);
    }

    private static void sortAction(Catalog catalog, LinkedList<Product> searchResults, String action, boolean isAdmin) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
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
     * Tokenizes a search query and searches the catalog for those products, returning the list of products
     * @param search the search query
     * @param catalog the catalog of products
     * @return a list of products matching the search query
     */
//    public static LinkedList<Product> tokenizedSearch(String search, Catalog catalog) {
//        LinkedList<Product> searchResults = new LinkedList<>();
//        HashMap<String, ColorCategory> tokenColors = new HashMap<>();
//        HashMap<String, SizeCategory> tokenSizes = new HashMap<>();
//        HashMap<String, AnimalCategory> tokenAnimals = new HashMap<>();
//        HashMap<String, ProductCategory> tokenTypes = new HashMap<>();
//
//        for (ColorCategory color: ColorCategory.values()) {
//            tokenColors.put(color.name().toLowerCase(), color);
//        }
//
//        for (SizeCategory size: SizeCategory.values()) {
//            tokenSizes.put(size.name().toLowerCase(), size);
//        }
//
//        for (AnimalCategory animal: AnimalCategory.values()) {
//            tokenAnimals.put(animal.name().toLowerCase(), animal);
//        }
//
//        for (ProductCategory type: ProductCategory.values()) {
//            tokenTypes.put(type.name().toLowerCase(), type);
//        }
//
//        Scanner tokenScan = new Scanner(search);
//
//        ArrayList<ArrayList<Attribute>> attributes = new ArrayList<>();
//
//        ArrayList<Attribute> colors = new ArrayList<>();
//        attributes.add(colors);
//
//        ArrayList<Attribute> sizes = new ArrayList<>();
//        attributes.add(sizes);
//
//        ArrayList<Attribute> animals = new ArrayList<>();
//        attributes.add(animals);
//
//        ArrayList<ProductCategory> types = new ArrayList<>();
//
//
//        while (tokenScan.hasNext()){
//            String token = tokenScan.next();
//
//            // Add colors
//            if (tokenColors.containsKey(token.toLowerCase())) {
//                attributes.get(attributes.indexOf(colors)).add(tokenColors.get(token.toLowerCase()));
//            }
//
//            // Add sizes
//            if (tokenSizes.containsKey(token.toLowerCase())) {
//                attributes.get(attributes.indexOf(sizes)).add(tokenSizes.get(token.toLowerCase()));
//            }
//
//            // Add animals
//            if (tokenAnimals.containsKey(token.toLowerCase())) {
//                attributes.get(attributes.indexOf(animals)).add(tokenAnimals.get(token.toLowerCase()));
//            }
//
//            // Add product types
//            if (tokenTypes.containsKey(token.toLowerCase())) {
//                types.add(tokenTypes.get(token.toLowerCase()));
//            }
//        }
//
//        // Generate all possible attribute sets for the search terms
//        ArrayList<ArrayList<Attribute>> attributeSets = new ArrayList<>();
//        getAttributeSets(attributes, attributeSets, 0, new ArrayList<>());
//
//        // Search by attribute only if no type specified
//        if (types.isEmpty()) {
//            for (ArrayList<Attribute> attributeSet : attributeSets) {
//                searchResults.addAll(catalog.getByAtt(attributeSet));
//            }
//        }
//        else {
//            // Search by types and attributes if there are types specified
//            for (ProductCategory type : types) {
//                for (ArrayList<Attribute> attributeSet : attributeSets) {
//                    searchResults.addAll(catalog.getByAtt(type, attributeSet));
//                }
//            }
//        }
//
//        // Print the number of results
//        System.out.println("\tFound " + searchResults.size() + " results for the following query:");
//
//        System.out.println();
//
//        // Print the searched product categories
//        System.out.print("\tProducts: ");
//
//        if (types.isEmpty()) {
//            System.out.print("All");
//        }
//        else {
//            Iterator<ProductCategory> productCategoryIterator = types.iterator();
//            while (productCategoryIterator.hasNext()) {
//                System.out.print(productCategoryIterator.next());
//
//                if (productCategoryIterator.hasNext()) {
//                    System.out.print(", ");
//                }
//            }
//        }
//
//        System.out.println();
//
//        // Print the searched attributes
//        ArrayList<Attribute> uncategorizedAttributes = new ArrayList<>();
//
//        // Generate one list with all the attributes
//        uncategorizedAttributes.addAll(colors);
//        uncategorizedAttributes.addAll(sizes);
//        uncategorizedAttributes.addAll(animals);
//
//        System.out.print("\tAttributes: ");
//
//        if (uncategorizedAttributes.isEmpty()) {
//            System.out.print("Any");
//        }
//        else {
//            Iterator<Attribute> attributeIterator = uncategorizedAttributes.iterator();
//            while (attributeIterator.hasNext()) {
//                System.out.print(attributeIterator.next());
//
//                if (attributeIterator.hasNext()) {
//                    System.out.print(", ");
//                }
//            }
//        }
//
//        System.out.println();
//        System.out.println();
//
//        // Print search results
//        for (Product product : searchResults) {
//            System.out.println("\t" + product);
//        }
//
//        return searchResults;
//    }

    /**
     * Takes a list of Attribute sets and returns a list of all permutations of those attributes
     * @param attributeLists the list of attribute sets
     * @param result a list of all permutations of the attributes
     * @param attributeListIndex the index in the list of attribute sets
     * @param currentAttributeSet the attribute set to store the current permutation's attributes in
     * @return a list of all permutations of the attributes
     */
//    private static ArrayList<ArrayList<Attribute>> getAttributeSets(ArrayList<ArrayList<Attribute>> attributeLists, ArrayList<ArrayList<Attribute>> result,
//                                                                    int attributeListIndex, ArrayList<Attribute> currentAttributeSet) {
//        // If an attribute has been chosen for each category, add the current attribute set
//        if (attributeListIndex == attributeLists.size()) {
//            ArrayList<Attribute> resultSet = new ArrayList<>(currentAttributeSet);
//            result.add(resultSet);
//            return result;
//        }
//
//        // Get the attribute sets for each value of the current attribute
//        if (attributeLists.get(attributeListIndex).isEmpty()) {
//            getAttributeSets(attributeLists, result, attributeListIndex + 1, currentAttributeSet);
//        }
//        else {
//            for (int index = 0; index < attributeLists.get(attributeListIndex).size(); index++) {
//                // Add the current attribute
//                currentAttributeSet.add(attributeLists.get(attributeListIndex).get(index));
//
//                // Add the next attribute
//                getAttributeSets(attributeLists, result, attributeListIndex + 1, currentAttributeSet);
//
//                // Remove the last element from the current attribute set
//                currentAttributeSet.remove(currentAttributeSet.size() - 1);
//            }
//        }
//
//        return result;
//    }

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
        System.out.print("Enter product type: ");

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
                System.out.println("Invalid choice -- please select again.");

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
        System.out.print("Enter product color: ");

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
                System.out.println("Invalid choice -- please select again.");

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
        System.out.print("Enter product size: ");

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
                System.out.println("Invalid choice -- please select again.");

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
        System.out.print("Enter plush animal: ");

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
                System.out.println("Invalid choice -- please select again.");

                return getAnimal();
            }
        }
    }

    /**
     * Gets the days after Jan 1st 2023 a new product is listed
     * @return days after the minimum listing date
     */
    private static int getDaysAfterMinDay() {
        Calendar minDay = Calendar.getInstance();
        minDay.set(Calendar.YEAR, 2023);
        minDay.set(Calendar.DAY_OF_YEAR, 1);

        Date current = new Date();

        Temporal start = minDay.toInstant();
        Temporal end = current.toInstant();

        return (int) ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Gets the product price from an admin user
     * @return the chosen price
     */
    private static double getPrice() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a listing price: ");

        if (input.hasNextDouble()) {
            System.out.println();

            return input.nextDouble();
        }
        else {
            System.out.println();
            System.out.println("Invalid price -- please select again.");

            return getPrice();
        }
    }

    /**
     * Gets the product title from an admin user
     * @return the chosen title
     */
    private static String getTitle() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a product title: ");

        return input.nextLine();
    }
}
