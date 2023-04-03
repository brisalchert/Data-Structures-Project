package Products;
import Attributes.Values;

public class ProductPlush extends Product{

  public ProductPlush(int id, double price, int daysAfterMinDay, String title, Values[] attributes) {
      super(Values.Plush, id, price, daysAfterMinDay, title,attributes);
  }
}
