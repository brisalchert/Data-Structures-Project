package TestScripts;

import Attributes.AnimalCategory;
import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.ProductCategory;
import java.util.Random;

public class TestingMethods {
    Catalog catalog;
    int size = 0;
    Random random = new Random();
    final double MAX_PRICE = 1000;

    public TestingMethods(Catalog catalog){
        this.catalog = catalog;
        size = catalog.getSize();
    }

    public void load(){
        for(int i = 0; i < catalog.getMAX_SIZE();i++) {
            ProductCategory type = ProductCategory.values()[random.nextInt(ProductCategory.values().length)];
            double price = random.nextDouble(MAX_PRICE) + 1;
            Attribute size = SizeCategory.values()[random.nextInt(SizeCategory.values().length)];
            Attribute color = ColorCategory.values()[random.nextInt(ColorCategory.values().length)];
            Attribute animal = AnimalCategory.values()[random.nextInt(AnimalCategory.values().length)];

            Attribute[] attributes;
            if(type == ProductCategory.Plush) {
                attributes = new Attribute[]{size, color, animal};
            } else {
                attributes = new Attribute[]{size, color};
            }
            catalog.addProduct(type, price, "", attributes);
        }
    }
}
