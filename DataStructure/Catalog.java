package DataStructure;

import Products.Product;
import Products.ProductCategory;
import java.util.HashMap;
import java.util.Random;

public class Catalog {

    private final int MAX_SIZE;
    HashMap<Integer, Product> map;
    int[] sizePerType = new int[ProductCategory.values().length];

    public Catalog(int maxSize){
        this.MAX_SIZE = maxSize;
        this.map = new HashMap<>(MAX_SIZE);
        //fill sizePerType with 0
        for(int i : sizePerType){
            i = 0;
        }
    }

    public void addProduct(ProductCategory type, double price, String title){
        Random random = new Random();
        int id = random.nextInt(MAX_SIZE);

        if(map.size() == 0)
            map.put(id, type.getConstructor().apply(id, price, title));
        else {
            while (map.containsKey(id)) {
                id = random.nextInt(MAX_SIZE);
            }
            map.put(id, type.getConstructor().apply(id, price, title));
        }
        sizePerType[type.ordinal()]++;
    }

    public Product removeProduct(int id){
        Product removedProd = null;
        if(map.containsKey(id)){
            removedProd = map.get(id);
            map.remove(id);
            sizePerType[removedProd.getType().ordinal()]--;
        }
     return removedProd;
    }

    public HashMap<Integer, Product> getCatalog(){
        return map;
    }


}
