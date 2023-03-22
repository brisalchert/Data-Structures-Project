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

        test.load();


        for (Product product : catalog.values()){
            System.out.println(product.toString());
        }


        System.out.println("##########################################################");


        for(Product product : catalog.getByAtt(ProductCategory.Hat ,attributes)){
            System.out.println(product.toString());
        }
    }
}
