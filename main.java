import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.Product;
import Products.ProductCategory;
import TestScripts.TestingMethods;

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

        for(Product product : catalog.getByAtt(ProductCategory.Shirt ,attributes)){ //print out searched products
            System.out.println(product.toString());
        }
        System.out.println(catalog.getSize(ProductCategory.Shirt));
    }
}
