package DataStructure;

import Products.Product;
import Products.ProductCategory;
import java.util.HashMap;
import java.util.Random;

public class Catalog {

    private final int MAX_SIZE;
    private HashMap<Integer, Product> catalog;
    private int[] sizePerType = new int[ProductCategory.values().length]; //ordinal value represents index in array that stores
                                                                          //the size of the catalog for each type of product

    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize){
        this.MAX_SIZE = maxSize;
        this.catalog = new HashMap<>(MAX_SIZE);

        for(int i : sizePerType){ //fill with zeros
            i = 0;
        }
    }

    /**
     * Returns size of catalog
     * @return number of Products in catalog
     */
    public int getSize(){
        return catalog.size();
    }

    /**
     * Returns size of catalog for a certain type
     * @param type type of product
     * @return
     */
    public int getSize(ProductCategory type){
        return sizePerType[type.ordinal()];
    }

    /**
     * Adds a product to the catalog
     * @param type type of product
     * @param price price in dollars
     * @param title title representing the product
     */
    public void addProduct(ProductCategory type, double price, String title){
        Random random = new Random();
        int id = random.nextInt(MAX_SIZE);

        if(catalog.size() == 0)
            catalog.put(id, type.getConstructor().apply(id, price, title));
        else {
            while (catalog.containsKey(id)) {
                id = random.nextInt(MAX_SIZE);
            }
            catalog.put(id, type.getConstructor().apply(id, price, title));
        }
        sizePerType[type.ordinal()]++;
    }

    /**
     * Removes a product from the catalog and returns it
     * @param id id number of product to be removed
     * @return removed product
     */
    public Product removeProduct(int id){
        Product removedProd = null;
        if(catalog.containsKey(id)){
            removedProd = catalog.get(id);
            catalog.remove(id);
            sizePerType[removedProd.getType().ordinal()]--;
        }
     return removedProd;
    }

    /**
     * Returns the catalog for the shop
     * @return hashmap representing catalog for shop
     */
    public HashMap<Integer, Product> getCatalog(){
        return catalog;
    }
}
