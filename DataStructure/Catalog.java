package DataStructure;
import Attributes.Attribute;
import Products.Product;
import Products.ProductCategory;
import java.util.*;

public class Catalog {
    private final int MAX_SIZE; //max size of catalog
    private HashMap<Integer, Product> catalog;
    private int[] sizePerType = new int[ProductCategory.values().length]; //size of type is stored at index equal to types ordinal

    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize){
        this.MAX_SIZE = maxSize;
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
    public void addProduct(ProductCategory type, double price, String title, Attribute[] attributes){ //O(1)
        if(getSize() == MAX_SIZE){
            return;
        }
        Random random = new Random();
        int id = random.nextInt(MAX_SIZE*2);

        if (catalog.size() != 0) {
            while (catalog.containsKey(id)) {
                id = random.nextInt(MAX_SIZE * 2);
            }
        }
        catalog.put(id, type.getConstructor().apply(id, price, title, attributes));
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
    public LinkedList<Product> getByAtt(ProductCategory type, Attribute[] attributes){ //0(n*k) where n is # of attributes, k is # of ids
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
    public LinkedList<Product> getByAtt(Attribute[] attributes){ //0(n*k) where n is # of attributes, k is # of ids
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

}
