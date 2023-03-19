package DataStructure;
import Attributes.Attribute;
import Products.Product;
import Products.ProductCategory;
import java.util.*;

public class Catalog {

    private final int MAX_SIZE; //max size of catalog
    private HashSet<Integer> ids; //ids in catalog

    /**
     * Constructs a Catalog
     * @param maxSize maximum size of catalog
     */
    public Catalog(int maxSize){
        this.MAX_SIZE = maxSize;
        this.ids = new HashSet<Integer>(MAX_SIZE);
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
    public  int getSize(){
        int result = 0;
        for(ProductCategory type: ProductCategory.values()){
            result += type.getHashMap().size();
        }
        return result;
    }

    /**
     * Returns size of catalog for a certain type
     * @param type type of product
     * @return
     */
    public int getSize(ProductCategory type){
        return type.getHashMap().size();
    }

    /**
     * Adds a product to the catalog
     * @param type type of product
     * @param price price in dollars
     * @param title title representing the product
     */
    public void addProduct(ProductCategory type, double price, String title, Attribute[] attributes){
        if(getSize() == MAX_SIZE){
            return;
        }
        Random random = new Random();
        int id = random.nextInt(MAX_SIZE*2);

        if (ids != null) {
            while (ids.contains(id)) {
                id = random.nextInt(MAX_SIZE * 2);
            }
        }
        ids.add(id);
        type.getHashMap().put(id, type.getConstructor().apply(id, price, title, attributes));
        for(Attribute attribute : attributes){ //add to Attribute sets
            attribute.getSet().add(id);
        }
    }

    /**
     * Removes a product from the catalog and returns it
     * @param id id number of product to be removed
     * @return removed product
     */
    public Product removeProduct(int id){
        Product removedProd = null;
        if(ids.contains(id)){
            for(ProductCategory type: ProductCategory.values()){//remove from product set of id type
                if(type.getHashMap().containsKey(id)){
                    removedProd = type.getHashMap().get(id);
                }
            }

            for(Attribute attribute : removedProd.getAttributes()){ //remove from Attribute sets
                attribute.getSet().remove(id);
            }
            ids.remove(id);
        }
     return removedProd;
    }

    /**
     * Returns a list of the products type that have the attributes
     * @param attributes attributes to sort by
     * @param type type of product to sort by
     * @return list of product  matching the attributes of type
     */
    public LinkedList<Product> getByAtt(ProductCategory type, Attribute[] attributes){
        LinkedList<Product> results = new LinkedList<Product>();
        for(Integer id : type.getHashMap().keySet()){
            boolean match = true;
            for(Attribute attribute : attributes){
                if(!attribute.getSet().contains(id)){
                    match = false;
                }
            }

            if(match){
                results.add(type.getHashMap().get(id));
            }
        }
        return results;
    }

   //need method that returns iterator of the whole catalog without having to copy the product type into a set

}
