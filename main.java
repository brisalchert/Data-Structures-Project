import Attributes.AnimalCategory;
import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.Product;
import Products.ProductCategory;
import Products.SortCategory;
import TestScripts.TestingMethods;

import java.nio.file.LinkPermission;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(100);
        TestingMethods test = new TestingMethods(catalog);
        Scanner input = new Scanner(System.in);

        test.load(); //fill Catalog

        printHome(catalog);
    }

    /**
     * Prints the home page of the clothing store
     * @param catalog the catalog of products in the store
     */
    public static void printHome(Catalog catalog) {
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("####################################################################################################"); // 100 chars
        System.out.println();
        System.out.println("\tWelcome to the clothing store!");
        System.out.println("\tPlease choose an action below:");
        System.out.println();
        System.out.println("\tSearch (Search the catalog)\n\tLogin (Login as an administrator)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("\tEnter an action: ");

        String action = input.nextLine();
        homeAction(catalog, action);
    }

    private static void homeAction(Catalog catalog, String action) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "search" -> {
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                System.out.print("\tEnter your search query: ");
                String search = input.nextLine();

                System.out.println();

                LinkedList<Product> searchResults = tokenizedSearch(search, catalog);

                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                System.out.println("\tPlease choose a new action below:");
                System.out.println();
                System.out.println("\tSort (Sort the list of searched items\n\tSearch (New query)\n\tHome (Return to the homepage)");
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();
                System.out.print("\tEnter an action: ");

                action = input.nextLine();
                searchAction(catalog, action);

            }
        }
    }

    /**
     * Performs the post-search action input by the user
     * @param catalog the catalog of products
     * @param action the user's chosen action
     */
    private static void searchAction(Catalog catalog, String action) {
        Scanner input = new Scanner(System.in);

        switch (action.toLowerCase()) {
            case "sort" -> {
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();
                System.out.println("\tChoose a sort criterion below:");
                System.out.println();
                System.out.println("\tPriceMin (Price Ascending)\n\tPriceMax (Price Descending)\n\tDateOld (Listing Date Ascending)\n\tDateNew (Listing Date Descending)");
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();
                System.out.print("\tEnter an action: ");

                action = input.nextLine();
            }
            case "search" -> {
                homeAction(catalog, "search");
            }
        }
    }

    /**
     * Tokenizes a search query and searches the catalog for those products, returning the list of products
     * @param search the search query
     * @param catalog the catalog of products
     * @return a list of products matching the search query
     */
    public static LinkedList<Product> tokenizedSearch(String search, Catalog catalog) {
        LinkedList<Product> searchResults = new LinkedList<>();
        HashMap<String, ColorCategory> tokenColors = new HashMap<>();
        HashMap<String, SizeCategory> tokenSizes = new HashMap<>();
        HashMap<String, AnimalCategory> tokenAnimals = new HashMap<>();
        HashMap<String, ProductCategory> tokenTypes = new HashMap<>();

        for (ColorCategory color: ColorCategory.values()) {
            tokenColors.put(color.name().toLowerCase(), color);
        }

        for (SizeCategory size: SizeCategory.values()) {
            tokenSizes.put(size.name().toLowerCase(), size);
        }

        for (AnimalCategory animal: AnimalCategory.values()) {
            tokenAnimals.put(animal.name().toLowerCase(), animal);
        }

        for (ProductCategory type: ProductCategory.values()) {
            tokenTypes.put(type.name().toLowerCase(), type);
        }

        Scanner tokenScan = new Scanner(search);

        ArrayList<ArrayList<Attribute>> attributes = new ArrayList<>();

        ArrayList<Attribute> colors = new ArrayList<>();
        attributes.add(colors);

        ArrayList<Attribute> sizes = new ArrayList<>();
        attributes.add(sizes);

        ArrayList<Attribute> animals = new ArrayList<>();
        attributes.add(animals);

        ArrayList<ProductCategory> types = new ArrayList<>();


        while (tokenScan.hasNext()){
            String token = tokenScan.next();

            // Add colors
            if (tokenColors.containsKey(token.toLowerCase())) {
                attributes.get(attributes.indexOf(colors)).add(tokenColors.get(token.toLowerCase()));
            }

            // Add sizes
            if (tokenSizes.containsKey(token.toLowerCase())) {
                attributes.get(attributes.indexOf(sizes)).add(tokenSizes.get(token.toLowerCase()));
            }

            // Add animals
            if (tokenAnimals.containsKey(token.toLowerCase())) {
                attributes.get(attributes.indexOf(animals)).add(tokenAnimals.get(token.toLowerCase()));
            }

            // Add product types
            if (tokenTypes.containsKey(token.toLowerCase())) {
                types.add(tokenTypes.get(token.toLowerCase()));
            }
        }

        // Generate all possible attribute sets for the search terms
        ArrayList<ArrayList<Attribute>> attributeSets = new ArrayList<>();
        getAttributeSets(attributes, attributeSets, 0, new ArrayList<>());

        // Search by attribute only if no type specified
        if (types.isEmpty()) {
            for (ArrayList<Attribute> attributeSet : attributeSets) {
                searchResults.addAll(catalog.getByAtt(attributeSet));
            }
        }
        else {
            // Search by types and attributes if there are types specified
            for (ProductCategory type : types) {
                for (ArrayList<Attribute> attributeSet : attributeSets) {
                    searchResults.addAll(catalog.getByAtt(type, attributeSet));
                }
            }
        }

        // Print the number of results
        System.out.println("\tFound " + searchResults.size() + " results for the following query:");

        System.out.println();

        // Print the searched product categories
        System.out.print("\tProducts: ");

        if (types.isEmpty()) {
            System.out.print("All");
        }
        else {
            Iterator<ProductCategory> productCategoryIterator = types.iterator();
            while (productCategoryIterator.hasNext()) {
                System.out.print(productCategoryIterator.next());

                if (productCategoryIterator.hasNext()) {
                    System.out.print(", ");
                }
            }
        }

        System.out.println();

        // Print the searched attributes
        ArrayList<Attribute> uncategorizedAttributes = new ArrayList<>();

        // Generate one list with all the attributes
        uncategorizedAttributes.addAll(colors);
        uncategorizedAttributes.addAll(sizes);
        uncategorizedAttributes.addAll(animals);

        System.out.print("\tAttributes: ");

        if (uncategorizedAttributes.isEmpty()) {
            System.out.print("Any");
        }
        else {
            Iterator<Attribute> attributeIterator = uncategorizedAttributes.iterator();
            while (attributeIterator.hasNext()) {
                System.out.print(attributeIterator.next());

                if (attributeIterator.hasNext()) {
                    System.out.print(", ");
                }
            }
        }

        System.out.println();
        System.out.println();

        // Print search results
        for (Product product : searchResults) {
            System.out.println("\t" + product);
        }

        return searchResults;
    }

    /**
     * Takes a list of Attribute sets and returns a list of all permutations of those attributes
     * @param attributeLists the list of attribute sets
     * @param result a list of all permutations of the attributes
     * @param attributeListIndex the index in the list of attribute sets
     * @param currentAttributeSet the attribute set to store the current permutation's attributes in
     * @return a list of all permutations of the attributes
     */
    private static ArrayList<ArrayList<Attribute>> getAttributeSets(ArrayList<ArrayList<Attribute>> attributeLists, ArrayList<ArrayList<Attribute>> result,
                                         int attributeListIndex, ArrayList<Attribute> currentAttributeSet) {
        // If an attribute has been chosen for each category, add the current attribute set
        if (attributeListIndex == attributeLists.size()) {
            ArrayList<Attribute> resultSet = new ArrayList<>(currentAttributeSet);
            result.add(resultSet);
            return result;
        }

        // Get the attribute sets for each value of the current attribute
        if (attributeLists.get(attributeListIndex).isEmpty()) {
            getAttributeSets(attributeLists, result, attributeListIndex + 1, currentAttributeSet);
        }
        else {
            for (int index = 0; index < attributeLists.get(attributeListIndex).size(); index++) {
                // Add the current attribute
                currentAttributeSet.add(attributeLists.get(attributeListIndex).get(index));

                // Add the next attribute
                getAttributeSets(attributeLists, result, attributeListIndex + 1, currentAttributeSet);

                // Remove the last element from the current attribute set
                currentAttributeSet.remove(currentAttributeSet.size() - 1);
            }
        }

        return result;
    }
}
