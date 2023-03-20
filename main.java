import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.Product;
import Products.ProductCategory;
import TestScripts.TestingMethods;

public class main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(1000);
        TestingMethods test = new TestingMethods(catalog);
        Attribute[] attributes = {SizeCategory.Medium, ColorCategory.Red};

        test.load();

        for (ProductCategory type : ProductCategory.values()) {
            for (Product product : type.getHashMap().values()){
                System.out.println(product.toString());
            }
        }

        System.out.println("##########################################################");


        for(Product product : catalog.getByAtt(attributes)){
            System.out.println(product.toString());
        }
    }
}
