package DataStructure;
import Attributes.Attribute;
import Products.Product;
import Products.ProductCategory;
import Products.SortCategory;

import java.util.*;

public class Catalog {
    private final int MAX_SIZE; //max size of catalog
    private final int MAX_ID; // Maximum id number for the catalog
    private HashMap<Integer, Product> catalog;
    private int[] sizePerType = new int[ProductCategory.values().length]; //size of type is stored at index equal to types ordinal

    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize){
        this.MAX_SIZE = maxSize;
        this.MAX_ID = maxSize * 2;
        this.catalog = new HashMap<Integer, Product>(MAX_SIZE);
        for(int i : sizePerType){
            sizePerType[i] = 0;
        }
    }

    /**
     * Returns maxsize of catalog
     * @return maxsize of catalog
     */
    public int getMAX_SIZE(){
        return MAX_SIZE;
    }

    /**
     * Returns size of catalog
     * @return number of Products in catalog
     */
    public  int getSize(){ //O(1)
        return catalog.size();
    }

    /**
     * Returns size of catalog for a certain type
     * @param type type of product
     * @return
     */
    public int getSize(ProductCategory type){ //O(1)
        return sizePerType[type.ordinal()];
    }

    /**
     * Adds a product to the catalog
     * @param type type of product
     * @param price price in dollars
     * @param title title representing the product
     */
    public void addProduct(ProductCategory type, double price, int daysAfterMinDay, String title, Attribute[] attributes){ //O(1)
        if(getSize() == MAX_SIZE){
            return;
        }
        Random random = new Random();
        int id = random.nextInt(MAX_ID);

        if (catalog.size() != 0) {
            while (catalog.containsKey(id)) {
                id = random.nextInt(MAX_ID);
            }
        }
        catalog.put(id, type.getConstructor().apply(id, price, daysAfterMinDay, title, attributes));
        for(Attribute attribute : attributes){ //add to Attribute sets
            attribute.getSet().add(id);
        }
        sizePerType[type.ordinal()]++;
    }

    /**
     * Removes a product from the catalog and returns it
     * @param id id number of product to be removed
     * @return removed product
     */
    public Product removeProduct(int id){ //O(1)
        Product removedProd = null;
        if(catalog.containsKey(id)){
            removedProd = catalog.get(id);
            sizePerType[removedProd.getType().ordinal()]--;
        }

        for(Attribute attribute : removedProd.getAttributes()){ //remove from Attribute sets
                attribute.getSet().remove(id);
        }
        return removedProd;
    }

    /**
     * Returns a list of the products type that have the attributes
     * @param attributes attributes to sort by
     * @param type type of product to sort by
     * @return list of product  matching the attributes of type
     */
    public LinkedList<Product> getByAtt(ProductCategory type, ArrayList<Attribute> attributes){ //0(n*k) where n is # of attributes, k is # of ids
        LinkedList<Product> results = new LinkedList<Product>();
        for(Integer id : catalog.keySet()){
            boolean match = true;
            if(catalog.get(id).getType() == type){
                for(Attribute attribute : attributes){
                    if(!attribute.getSet().contains(id)){
                        match = false;
                    }
                }
            }else
                match = false;

            if(match){
                results.add(catalog.get(id));
            }
        }
        return results;
    }

    /**
     * Returns a list of the products that have the attributes
     * @param attributes attributes to sort by
     * @return list of product matching the attributes
     */
    public LinkedList<Product> getByAtt(ArrayList<Attribute> attributes){ //0(n*k) where n is # of attributes, k is # of ids
        LinkedList<Product> results = new LinkedList<Product>();
        for(Integer id : catalog.keySet()){
            boolean match = true;
            for(Attribute attribute : attributes){
                if(!attribute.getSet().contains(id)){
                    match = false;
                }
            }

            if(match){
                results.add(get(id));
            }
        }
        return results;
    }

    /**
     * Gets the product with the matching id
     * @param id id of product to get
     * @return product with id equal to id
     */
    public Product get(Integer id){ //0(1)
        return catalog.get(id);
    }

    /**
     * returns iterable list of products in catalog
     * @return iterable list of products in catalog
     */
    public Collection<Product> values(){
        return catalog.values();
    }

    public LinkedList<Product> bucketSort(SortCategory sortCategory, LinkedList<Product> list) {
        int bucketSize = 100;
        ArrayList<LinkedList<Product>> buckets = new ArrayList<>();
        int max = (int)sortCategory.getMax(sortCategory) / bucketSize;
        LinkedList<Product> result = new LinkedList<>();

        // Check if list is trivially sorted
        if (list.size() < 2) {
            return list;
        }

        // Initialize the buckets each with range 5
        for (int bucketIndex = 0; bucketIndex < (max + 1); bucketIndex++) {
            buckets.add(new LinkedList<>());
        }

        // Put list items into their respective buckets
        for (Product product : list) {
            int index = (int)sortCategory.getValue(sortCategory, product) / bucketSize;
            buckets.get(index).add(product);
        }

        // Sort the buckets individually
        for (LinkedList<Product> bucket : buckets) {
            if (bucket.size() > 1) {
                insertionSortBucket(sortCategory, bucket);
            }
        }

        // Recombine the elements of the buckets
        for (LinkedList<Product> bucket : buckets) {
            result.addAll(bucket);
        }

        return result;
    }

    private void insertionSortBucket(SortCategory sortCategory, LinkedList<Product> list) {
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            int index = listIndex;
            while ((index >= 1) && sortCategory.compare(list.get(index), list.get(index - 1)) < 0) {
                swapBucketValues(list, list.get(index), list.get(index - 1));
                index--;
            }
        }
    }

    private void swapBucketValues(LinkedList<Product> list, Product prod1, Product prod2) {
        int prod2Index = list.indexOf(prod2);
        list.set(list.indexOf(prod1), prod2);
        list.set(prod2Index, prod1);
    }
}
