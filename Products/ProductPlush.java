package Products;
import Attributes.Attribute;

public class ProductPlush extends Product{

  public ProductPlush(int id, double price, String title, Attribute[] attributes) {
      super(ProductCategory.Plush, id, price, title,attributes);
  }
}
