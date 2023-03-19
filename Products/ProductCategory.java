package Products;
import java.util.HashMap;

public enum ProductCategory {
  Shirt (ProductShirt::new, new HashMap<Integer, Product>()),
  Hat   (ProductHat::new, new HashMap<Integer, Product>()),
  Pant  (ProductPant::new, new HashMap<Integer, Product>()),
  Plush (ProductPlush::new, new HashMap<Integer, Product>());

  private TypeConstructor constructor;
  private HashMap<Integer, Product> hashMap;

    /**
     * Constructs a Product type
     * @param constructor method to construct Product of type
     */
  ProductCategory(TypeConstructor constructor, HashMap<Integer, Product> hashMap ){
      this.constructor = constructor;
      this.hashMap = hashMap;
  }

    /**
     * returns the constructor method for type
     * @return constructor method for object of type
     */
  public TypeConstructor getConstructor(){
      return this.constructor;
  }

  public HashMap<Integer, Product> getHashMap() {
      return hashMap;
  }
}
