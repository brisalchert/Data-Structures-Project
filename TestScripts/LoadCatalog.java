package TestScripts;

import Attributes.AnimalCategory;
import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;
import DataStructure.Catalog;
import Products.ProductCategory;
import java.util.Random;

public class LoadCatalog {
    Catalog catalog;
    int size = 0;
    Random random = new Random();

    public LoadCatalog(Catalog catalog){
        this.catalog = catalog;
        size = catalog.getSize();
    }

    public void load(){
        for(int i = 0; i < catalog.getMAX_SIZE();i++) {
            ProductCategory type = ProductCategory.values()[random.nextInt(ProductCategory.values().length)];
            double price = random.nextDouble(1000) + 1;
            Attribute size = SizeCategory.values()[random.nextInt(SizeCategory.values().length)];
            Attribute color = ColorCategory.values()[random.nextInt(ColorCategory.values().length)];
            Attribute animal = AnimalCategory.values()[random.nextInt(AnimalCategory.values().length)];

            if (type == ProductCategory.Plush) {
                Attribute[] attributes = {size, color, animal};
                catalog.addProduct(type, price, "", attributes);
            } else {
                Attribute[] attributes = {size, color};
                catalog.addProduct(type, price, "", attributes);
            }
        }
    }
}
