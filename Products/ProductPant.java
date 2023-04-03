package Products;
import Attributes.Values;

public class ProductPant extends Product{

  public ProductPant(int id, double price, int daysAfterMinDay, String title, Values[] attributes) {
      super(Values.Pants, id, price, daysAfterMinDay, title,attributes);

  }

}
