package Products;
import Attributes.Values;

public class ProductShirt extends Product{

  public ProductShirt(int id, double price, int daysAfterMinDay, String title, Values[] attributes) {
      super(Values.Shirt, id, price, daysAfterMinDay, title,attributes);
  }
}
