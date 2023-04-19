package TestScripts;
import Attributes.Values;
import DataStructure.*;
import Products.SortCategory;

import java.util.Arrays;
import java.util.Random;

public class TestingMethods {
    private Catalog catalog;
    int size = 0;
    Random random = new Random();
    final double MAX_PRICE = 1000;
    final int MAX_DATE = (int) SortCategory.getMax(SortCategory.DateOldToNew);

    public TestingMethods(Catalog cat){
        catalog = cat;
        size = catalog.getSize();
    }

    /**
     * Loads the catalog to its original maximum capacity with random products
     * @return the time taken to fill the catalog
     */
    public long load(){
        long startTime;
        long endTime;

        startTime = System.nanoTime();

        for(int i = 0; i < catalog.getMAX_SIZE();i++) {
            addRandomProduct();
        }

        endTime = System.nanoTime();

        return endTime - startTime;
    }

    /**
     * Adds in bulk a specified number of random products to the catalog
     * @param numberOfProducts the number of products to add
     * @throws IllegalArgumentException
     */
    public void addBulk(int numberOfProducts) {
        try {
            if (numberOfProducts > 1000) {
                throw new IllegalArgumentException();
            }

            long startTime;
            long endTime;
            long microseconds;

            startTime = System.nanoTime();

            for(int i = 0; i < numberOfProducts;i++) {
                addRandomProduct();
            }

            endTime = System.nanoTime();

            microseconds = (endTime - startTime) / 1000;

            System.out.println("\tAdded " + numberOfProducts + " products to the catalog in " + microseconds + " microseconds.");
            System.out.println();
        }
        catch (IllegalArgumentException overflow) {
            System.out.println("\tCould not add " + numberOfProducts + " products: quantity too large.");
            System.out.println();
        }
    }

    private void addRandomProduct() {
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
