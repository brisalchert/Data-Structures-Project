package Products;
import Attributes.Attribute;

public class ProductPlush extends Product{

  public ProductPlush(int id, double price, int daysAfterMinDay, String title, Attribute[] attributes) {
      super(ProductCategory.Plush, id, price, daysAfterMinDay, title,attributes);
  }
}
