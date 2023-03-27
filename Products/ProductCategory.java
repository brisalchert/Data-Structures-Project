package Products;
import Attributes.Attribute;

import java.util.HashMap;
import java.util.HashSet;

public enum ProductCategory {
  Shirt (ProductShirt::new),
  Hat   (ProductHat::new),
  Pant  (ProductPant::new),
  Plush (ProductPlush::new);

  private TypeConstructor constructor;

    /**
     * Constructs a Product type
     * @param constructor method to construct Product of type
     */
  ProductCategory(TypeConstructor constructor){
      this.constructor = constructor;
  }

    /**
     * returns the constructor method for type
     * @return constructor method for object of type
     */
  public TypeConstructor getConstructor(){
      return this.constructor;
  }

}
