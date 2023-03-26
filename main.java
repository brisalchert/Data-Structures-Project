import Attributes.AnimalCategory;
import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.Product;
import Products.ProductCategory;
import Products.SortCategory;
import TestScripts.TestingMethods;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(100);
        TestingMethods test = new TestingMethods(catalog);
        Scanner input = new Scanner(System.in);

        test.load(); //fill Catalog

        printHome(catalog, input);

    }

    public static void printHome(Catalog catalog, Scanner input) {
        System.out.println();
        System.out.println("####################################################################################################"); // 100 chars
        System.out.println();
        System.out.println("\tWelcome to the clothing store!");
        System.out.println("\tPlease enter an integer to select an action below:");
        System.out.println();
        System.out.println("\tSearch Products (0)\n\tLogin as Admin (1)");
        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
        System.out.print("Enter an integer: ");

        // Need to use nextLine() instead of nextInt() otherwise the scanner will malfunction
        int action = Integer.parseInt(input.nextLine());
        homeAction(catalog, action, input);
    }

    private static void homeAction(Catalog catalog, int action, Scanner input) {
        switch (action) {
            case 0 -> {
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                ArrayList<ProductCategory> products = chooseProducts(input);

                System.out.println();

                ArrayList<ColorCategory> colors = chooseColors(input);

                System.out.println();

                ArrayList<SizeCategory> sizes = chooseSizes(input);

                ArrayList<AnimalCategory> animals;

                // If the product is a plush, ask the user for animal category
                if (products.contains(ProductCategory.Plush)) {
                    System.out.println();

                    animals = chooseAnimals(input);
                }
                else {
                    animals = new ArrayList<>();
                }

                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();

                search(catalog, products, colors, sizes, animals);
            }
        }
    }

    private static void search(Catalog catalog, ArrayList<ProductCategory> products, ArrayList<ColorCategory> colors,
                               ArrayList<SizeCategory> sizes, ArrayList<AnimalCategory> animals) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        LinkedList<Product> searchResults = new LinkedList<>();

        // Add color attributes
        attributes.addAll(colors);

        // Add size attributes
        attributes.addAll(sizes);

        // Add animal attributes
        attributes.addAll(animals);

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();

        // Add all products matching the search criteria
        for (ProductCategory productCategory : products) {
            searchResults.addAll(catalog.getByAtt(productCategory, attributes));
        }

        System.out.println("\t" + searchResults.size() + " results for:");

        System.out.println();

        // Print searched product types
        System.out.print("\tProduct types: ");
        Iterator<ProductCategory> productCategoryIterator = products.iterator();
        while (productCategoryIterator.hasNext()) {
            System.out.print(productCategoryIterator.next());
            if (productCategoryIterator.hasNext()) {
                System.out.print(", ");
            }
        }

        System.out.println();

        // Print searched attributes
        System.out.print("\tAttributes: ");
        Iterator<Attribute> attributeIterator = attributes.iterator();
        while (attributeIterator.hasNext()) {
            System.out.print(attributeIterator.next());
            if (attributeIterator.hasNext()) {
                System.out.print(", ");
            }
        }

        System.out.println();
        System.out.println();

        // Print the search results
        for (Product product : searchResults) {
            System.out.println(product);
        }

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();
    }

    private static void searchAction(int action) {
        switch (action) {
            case 0 -> {
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();
                System.out.println("\tChoose a sort criterion below:");
                System.out.println();
                System.out.println("\tPrice Ascending (0)\n\tPrice Descending (1)\n\tListing Date Ascending (2)\n\tListing Date Descending (3)");
                System.out.println();
                System.out.println("####################################################################################################");
                System.out.println();
            }
        }
    }

    private static ArrayList<ProductCategory> chooseProducts(Scanner input) {
        ArrayList<ProductCategory> result = new ArrayList<>();
        int count;

        System.out.println("\tChoose product types:");
        System.out.println();

        count = 0;
        for (ProductCategory product : ProductCategory.values()) {
            System.out.print("\t" + product + " (" + count + ")");
            count++;
            if ((count + 1) <= ProductCategory.values().length) {
                System.out.print("\n");
            }
        }

        System.out.println();
        System.out.println();

        System.out.print("Enter integers (with spaces between): ");
        ArrayList<Integer> productList = new ArrayList<>();
        String indices = input.nextLine();
        getChoices(indices, productList);

        for (Integer product : productList) {
            result.add(ProductCategory.values()[product]);
        }

        // If no category is specified, select all
        if (result.isEmpty()) {
            result.addAll(Arrays.asList(ProductCategory.values()));
        }

        return result;
    }

    private static ArrayList<ColorCategory> chooseColors(Scanner input) {
        ArrayList<ColorCategory> result = new ArrayList<>();
        int count;

        System.out.println("\tChoose colors:");
        System.out.println();

        count = 0;
        for (ColorCategory color : ColorCategory.values()) {
            System.out.print("\t" + color + " (" + count + ")");
            count++;
            if ((count + 1) <= ColorCategory.values().length) {
                System.out.print("\n");
            }
        }

        System.out.println();
        System.out.println();

        System.out.print("Enter integers (with spaces between): ");
        ArrayList<Integer> colorList = new ArrayList<>();
        String indices = input.nextLine();
        getChoices(indices, colorList);

        for (Integer product : colorList) {
            result.add(ColorCategory.values()[product]);
        }

        return result;
    }

    private static ArrayList<SizeCategory> chooseSizes(Scanner input) {
        ArrayList<SizeCategory> result = new ArrayList<>();
        int count;

        System.out.println("\tChoose sizes:");
        System.out.println();

        count = 0;
        for (SizeCategory size : SizeCategory.values()) {
            System.out.print("\t" + size + " (" + count + ")");
            count++;
            if ((count + 1) <= SizeCategory.values().length) {
                System.out.print("\n");
            }
        }

        System.out.println();
        System.out.println();

        System.out.print("Enter integers (with spaces between): ");
        ArrayList<Integer> sizeList = new ArrayList<>();
        String indices = input.nextLine();
        getChoices(indices, sizeList);

        for (Integer product : sizeList) {
            result.add(SizeCategory.values()[product]);
        }

        return result;
    }

    private static ArrayList<AnimalCategory> chooseAnimals(Scanner input) {
        ArrayList<AnimalCategory> result = new ArrayList<>();
        int count;

        System.out.println("\tChoose plush animals:");
        System.out.println();

        count = 0;
        for (AnimalCategory animal : AnimalCategory.values()) {
            System.out.print("\t" + animal + " (" + count + ")");
            count++;
            if ((count + 1) <= AnimalCategory.values().length) {
                System.out.print("\n");
            }
        }

        System.out.println();
        System.out.println();

        System.out.print("Enter integers (with spaces between): ");
        ArrayList<Integer> animalList = new ArrayList<>();
        String indices = input.nextLine();
        getChoices(indices, animalList);

        for (Integer product : animalList) {
            result.add(AnimalCategory.values()[product]);
        }

        return result;
    }

    private static void getChoices(String input, ArrayList<Integer> list) {
        if (input.equals("")) {
            return;
        }

        String[] integers = input.split(" ");
        for (String integer : integers) {
            list.add(Integer.parseInt(integer));
        }
    }
}
