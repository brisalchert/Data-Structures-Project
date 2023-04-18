package DataStructure;
import Attributes.Values;
import Products.Product;
import Products.SortCategory;
import SearchMap.Proposition;
import SearchMap.PropositionTree;

import java.io.FileNotFoundException;
import java.util.*;

public class Catalog {
    private final int MAX_SIZE; //max size of catalog
    private final int MAX_ID; // Maximum id number for the catalog
    private HashMap<Integer, Product> catalog;
    private final PropositionTree searchMap = new PropositionTree();
    private final PropositionTree actionProp = new PropositionTree();


    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize) throws FileNotFoundException {
        this.MAX_SIZE = maxSize;
        this.MAX_ID = maxSize * 2;
        this.catalog = new HashMap<Integer, Product>(MAX_SIZE);
        for(Values v: Values.values()){
            searchMap.addCharacterPath(v.name().toLowerCase().toCharArray(), v);
        };
        for(Proposition p : Values.Actions.values()){
            actionProp.addCharacterPath(p.name().toLowerCase().toCharArray(), p);
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
     * returns the search map for the catalog
     * @return PropositionTree based on the catalogs Attributes
     */
    public PropositionTree getSearchMap(){
        return searchMap;
    }

    /**
     * returns the search map for the UI
     * @return PropositionTree based on UI actions
     */
    public PropositionTree getActionProp(){
        return actionProp;
    }

    /**
     * Returns size of catalog
     * @return number of Products in catalog
     */
    public int getSize(){ //O(1)
        return catalog.size();
    }

    /**
     * Returns size of catalog for a certain category
     * @param type type of product
     * @return size of catalog for a certain category
     */
    public int getSize(Values type){ //O(1)
        return type.getSet().size();
    }

    /**
     * Returns true if the given ID is in the catalog
     * @param id the ID to check for
     * @return true if the ID exists
     */
    public boolean containsID(int id) {
        return catalog.containsKey(id);
    }

    /**
     * Adds a product to the catalog
     * @param type type of product
     * @param price price in dollars
     * @param title title representing the product
     */
    public void addProduct(Values type, double price, int daysAfterMinDay, String title, Values[] attributes){ //O(1)
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
        type.getSet().add(id);
        for(Values attribute : attributes) { //add to corresponding attribute sets
            attribute.getSet().add(id);
        }
    }

    /**
     * Removes a product from the catalog and returns it
     * @param id id number of product to be removed
     * @return removed product
     */
    public Product removeProduct(int id){ //O(1)
        Product removedProd = null;
        if(!catalog.containsKey(id)){
            return removedProd;
        }
        removedProd = catalog.remove(id);

        removedProd.getType().getSet().remove(id);
        for(Values attribute : removedProd.getAttributes()){ //remove from Category sets
            attribute.getSet().remove(id);
        }
        return removedProd;
    }

    /**
     * Returns a list of the products that have the attributes
     * @param attributes attributes to sort by
     * @return list of product matching the attributes
     */
    public LinkedList<Product> getByAtt(ArrayList<Values> attributes){
        LinkedList<Product> results = new LinkedList<Product>();
        if(attributes.isEmpty()){
            results.addAll(catalog.values());
            return results;
        }
        boolean match = true;
        if(attributes.get(0).getCategory() == Values.Category.Product) {
            for(Integer id : attributes.get(0).getSet()){
                match = true;
                for (Values attribute : attributes) {
                    if (!attribute.getSet().contains(id)) {
                        match = false;
                        break;
                    }
                }
                if(!match){ continue;}
                results.add(get(id));
            }
        } else{
            for (Integer id : catalog.keySet()) {
                match = true;
                for (Values attribute : attributes) {
                    if (!attribute.getSet().contains(id)) {
                        match = false;
                        break;
                    }
                }
                if(!match){ continue;}
                results.add(get(id));
            }
        }
        return results;
    }

    public  ArrayList<ArrayList<Values>> searchQueries(Values.Category[] categories, Values[] s, int i, int arrayIndex, int valueIndex, ArrayList<ArrayList<Values>> queries) {
        for (int x = i; x < categories.length;x++) {
            if(valueIndex + 1 < categories[x].getSearchSet().size()) {
                searchQueries(categories, s , x, arrayIndex, valueIndex + 1, queries); //same category new value
            }
            if(valueIndex < categories[x].getSearchSet().size()) {
                s[arrayIndex] = categories[x].getSearchSet().get(valueIndex); //new category same value
            }
            arrayIndex = arrayIndex + 1;
            valueIndex = 0;

        }

        ArrayList<Values> query = new ArrayList<>();
        for(Values v : s){
            if(v != null){
                query.add(v);
            }
        }

        queries.add(query);

        return queries;
    };

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
        int bucketSize = 5;
        ArrayList<LinkedList<Product>> buckets = new ArrayList<>();
        int max = (int)sortCategory.getMax(sortCategory) / bucketSize;
        LinkedList<Product> result = new LinkedList<>();

        // Check if list is trivially sorted
        if (list.size() < 2) {
            return list;
        }

        // Initialize the buckets each with range bucketsize
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
