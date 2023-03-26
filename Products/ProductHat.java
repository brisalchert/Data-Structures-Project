package Products;
import Attributes.*;

public class ProductHat extends Product{

  public ProductHat(int id, double price, int daysAfterMinDay, String title, Attribute[] attributes ) {
    super(ProductCategory.Hat, id, price, daysAfterMinDay, title, attributes);
  }
}
