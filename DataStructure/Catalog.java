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
    private final HashMap<Integer, Product> catalog;
    private final PropositionTree searchProp = new PropositionTree();
    private final PropositionTree actionProp = new PropositionTree();


    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize) {
        this.MAX_SIZE = maxSize;
        this.MAX_ID = maxSize * 2;
        this.catalog = new HashMap<Integer, Product>(MAX_SIZE);

        for(Values v: Values.values()){
            searchProp.addCharacterPath(v.name().toLowerCase().toCharArray(), v);
        };
        for(Proposition p : Values.Actions.values()){
            actionProp.addCharacterPath(p.name().toLowerCase().toCharArray(), p);
        }

    }

    //##############################GETTERS AND SETTERS#################################################################

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
    public PropositionTree getSearchProp(){
        return searchProp;
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

    //##############################CATALOG MANIPULATION################################################################

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
        int id = random.nextInt(MAX_ID); //set a random id

        if (catalog.size() != 0) { //make sure that id is not a copy in system
            while (catalog.containsKey(id)) {
                id = random.nextInt(MAX_ID);
            }
        }
        catalog.put(id, type.getConstructor().apply(id, price, daysAfterMinDay, title, attributes));
        type.getSet().add(id);
        for(Values attribute : attributes) { //add to corresponding value sets
            attribute.getSet().add(id);
        }
    }

    /**
     * Removes a product from the catalog and returns it
     * @param id id number of product to be removed
     * @return removed product
     */
    public Product removeProduct(int id){ //O(1) runtime
        Product removedProd = null;
        if(!catalog.containsKey(id)){ //make sure id can be removed
            return removedProd;
        }
        removedProd = catalog.remove(id); //remove the id

        removedProd.getType().getSet().remove(id);
        for(Values attribute : removedProd.getAttributes()){ //remove from Category sets
            attribute.getSet().remove(id);
        }
        return removedProd;
    }

    //##############################CATALOG SEARCHING################################################################

    /**
     * Returns a list of the products that have the attributes
     * @param attributes attributes to sort by
     * @return list of product matching the attributes
     */
    public LinkedList<Product> getByAtt(ArrayList<Values> attributes){//O(n) runtime
        LinkedList<Product> results = new LinkedList<Product>();
        if(attributes.isEmpty()){ //if nothing is searched
            return results;
        }
        boolean match = true;
        if(attributes.get(0).getCategory() == Values.Category.Product) { //if a product type is included in the search only use product set
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
            for (Integer id : catalog.keySet()) { //look ar all ids in the system that match description
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

    /**
     * Produces all valid search permutations based on the searched tokens
     * @param categories possible search category
     * @param v array of values
     * @param i current category
     * @param arrayIndex index of the array being edited
     * @param valueIndex index of the value being added
     * @param queries the array list of all permutations of query's
     * @return list of possible search quires
     */
    public  ArrayList<ArrayList<Values>> searchQueries(Values.Category[] categories, Values[] v, int i, int arrayIndex, int valueIndex, ArrayList<ArrayList<Values>> queries) {
        for (int x = i; x < categories.length;x++) { //border conditions are met?
            if(valueIndex + 1 < categories[x].getSearchSet().size()) {
                searchQueries(categories, v , x, arrayIndex, valueIndex + 1, queries); //same category new value
            }
            if(valueIndex < categories[x].getSearchSet().size()) {//border conditions are met?
                v[arrayIndex] = categories[x].getSearchSet().get(valueIndex); //new category same value
            }
            arrayIndex = arrayIndex + 1;
            valueIndex = 0;

        }

        ArrayList<Values> query = new ArrayList<>(); //create query
        for(Values value : v){
            if(value != null){
                query.add(value);
            }
        }

        queries.add(query); //add possible query

        return queries;
    };

    //##############################CATALOG SORTING#####################################################################

    /**
     *
     * @param sortCategory
     * @param list
     * @return
     */
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

    /**
     * Sorts a bucket using Insertion Sort
     * @param sortCategory the sorting criterion given by the user
     * @param list the bucket
     */
    private void insertionSortBucket(SortCategory sortCategory, LinkedList<Product> list) {
        // Iterate over each item in the bucket
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            int index = listIndex;
            // Continue to swap item lower in the bucket until it is sorted
            while ((index >= 1) && sortCategory.compare(list.get(index), list.get(index - 1)) < 0) {
                swapBucketValues(list, list.get(index), list.get(index - 1));
                index--;
            }
        }
    }

    /**
     * Swaps two bucket values using a temp variable
     * @param list the bucket
     * @param prod1 the first product
     * @param prod2 the second product
     */
    private void swapBucketValues(LinkedList<Product> list, Product prod1, Product prod2) {
        int prod2Index = list.indexOf(prod2);
        list.set(list.indexOf(prod1), prod2);
        list.set(prod2Index, prod1);
    }
}
