import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.Product;
import Products.ProductCategory;
import Products.SortCategory;
import TestScripts.TestingMethods;
import java.util.LinkedList;

public class main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(100);
        TestingMethods test = new TestingMethods(catalog);
        Attribute[] attributes = {};

        test.load(); //fill Catalog

        for (Product product : catalog.values()){ //print out catalog
            System.out.println(product.toString());
        }

        System.out.println("##########################################################");

        LinkedList<Product> searchList = catalog.getByAtt(ProductCategory.Shirt ,attributes);

        for(Product product : searchList){ //print out searched products
            System.out.println(product.toString());
        }
        System.out.println(catalog.getSize(ProductCategory.Shirt));

        System.out.println("##########################################################");

        System.out.println();
        System.out.println("Searched Products from New to Old:");
        System.out.println();

        for (Product product : catalog.bucketSort(SortCategory.DateNewToOld, searchList)) {
            System.out.println(product);
        }

        System.out.println("##########################################################");

        System.out.println();
        System.out.println("Searched Products from Old to New:");
        System.out.println();

        for (Product product : catalog.bucketSort(SortCategory.DateOldToNew, searchList)) {
            System.out.println(product);
        }

        System.out.println("##########################################################");

        System.out.println();
        System.out.println("Searched Products from Most Expensive to Cheapest:");
        System.out.println();

        for (Product product : catalog.bucketSort(SortCategory.PriceExpensiveToCheap, searchList)) {
            System.out.println(product);
        }

        System.out.println("##########################################################");

        System.out.println();
        System.out.println("Searched Products from Most Cheapest to Most Expensive:");
        System.out.println();

        for (Product product : catalog.bucketSort(SortCategory.PriceCheapToExpensive, searchList)) {
            System.out.println(product);
        }
    }
}
