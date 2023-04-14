package TestScripts;
import Attributes.Values;
import DataStructure.*;

import java.util.Arrays;
import java.util.Random;

public class TestingMethods {
    private Catalog catalog;
    int size = 0;
    Random random = new Random();
    final double MAX_PRICE = 1000;
    final int MAX_DATE = 1000;

    public TestingMethods(Catalog cat){
        catalog = cat;
        size = catalog.getSize();
    }

    public void load(){
        for(int i = 0; i < catalog.getMAX_SIZE();i++) {
            Values type = Values.values()[random.nextInt(0, 4)];
            double price = random.nextDouble(MAX_PRICE) + 1;
            int daysAfterMinDay = random.nextInt(MAX_DATE);
            Values size = Values.values()[random.nextInt(4, 8)];
            Values color = Values.values()[random.nextInt(8, 12)];
            Values animal = Values.values()[random.nextInt(12, 16)];

            Values[] attributes;
            if(type == Values.Plush) {
                attributes = new Values[]{size, color, animal};
            } else {
                attributes = new Values[]{size, color};
            }

            String title = "";

            for (int index = 0; index < attributes.length; index++) {
                title += attributes[index] + " ";
            }

            if (type != Values.Plush) {
                title += type;
            }

            catalog.addProduct(type, price, daysAfterMinDay, title, attributes);
        }
    }
}
