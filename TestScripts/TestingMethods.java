package TestScripts;

import Attributes.Values;
import DataStructure.Catalog;
import java.util.Random;

public class TestingMethods {
    Catalog catalog;
    int size = 0;
    Random random = new Random();
    final double MAX_PRICE = 1000;
    final int MAX_DATE = 1000;

    public TestingMethods(Catalog catalog){
        this.catalog = catalog;
        size = catalog.getSize();
    }

    public void load(){
        for(int i = 0; i < catalog.getMAX_SIZE();i++) {
            Values type = Values.values()[random.nextInt(0, 3)];
            double price = random.nextDouble(MAX_PRICE) + 1;
            int daysAfterMinDay = random.nextInt(MAX_DATE);
            Values size = Values.values()[random.nextInt(4, 7)];
            Values color = Values.values()[random.nextInt(8, 11)];
            Values animal = Values.values()[random.nextInt(12, 15)];

            Values[] attributes;
            if(type == Values.Plush) {
                attributes = new Values[]{size, color, animal};
            } else {
                attributes = new Values[]{size, color};
            }
            catalog.addProduct(type, price, daysAfterMinDay, "", attributes);
        }
    }
}
