package Products;
import Attributes.Values;

public class ProductPants extends Product{

  public ProductPants(int id, double price, int daysAfterMinDay, String title, Values[] attributes) {
      super(Values.Pant, id, price, daysAfterMinDay, title,attributes);

  }

}
