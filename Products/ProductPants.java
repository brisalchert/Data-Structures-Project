package Products;
import Attributes.Attribute;

public class ProductPants extends Product{

  public ProductPants(int id, double price, int daysAfterMinDay, String title, Attribute[] attributes) {
      super(ProductCategory.Pants, id, price, daysAfterMinDay, title,attributes);

  }

}
