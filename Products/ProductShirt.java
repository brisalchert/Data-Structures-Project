package Products;
import Attributes.Attribute;

public class ProductShirt extends Product{

  public ProductShirt(int id, double price, String title, Attribute[] attributes) {
      super(ProductCategory.Shirt, id, price, title,attributes);
  }
}
