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
        ArrayList<ArrayList<Attribute>> attributeLists = new ArrayList<>();
        ArrayList<ArrayList<Attribute>> attributeSets = new ArrayList<>();
        LinkedList<Product> searchResults = new LinkedList<>();

        // Add all attribute lists
        ArrayList<Attribute> colorAttributes = new ArrayList<>(colors);
        attributeLists.add(colorAttributes);

        ArrayList<Attribute> sizeAttributes = new ArrayList<>(sizes);
        attributeLists.add(sizeAttributes);

        ArrayList<Attribute> animalAttributes = new ArrayList<>(animals);
        attributeLists.add(animalAttributes);

        // Create all attribute sets (0(1) since the categories are of constant size)
        getAttributeSets(attributeLists, attributeSets, 0, new ArrayList<>());

        System.out.println();
        System.out.println("####################################################################################################");
        System.out.println();

        // Add all products matching the search criteria (0(n * k), where n = number of products, k = number of attributes)
        for (ProductCategory productCategory : products) {
            for (ArrayList<Attribute> attributeSet : attributeSets) {
                searchResults.addAll(catalog.getByAtt(productCategory, attributeSet));
            }
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
        Iterator<ArrayList<Attribute>> attributeListIterator = attributeLists.iterator();
        while (attributeListIterator.hasNext()) {
            Iterator<Attribute> attributeIterator = attributeListIterator.next().iterator();
            while (attributeIterator.hasNext()) {
                System.out.print(attributeIterator.next());

                if (attributeIterator.hasNext()) {
                    System.out.print(", ");
                }
            }

            if (attributeListIterator.hasNext()) {
                System.out.print("\t");
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
                currentAttributeSet.remove(attributeListIndex);
            }
        }

        return result;
    }
}
