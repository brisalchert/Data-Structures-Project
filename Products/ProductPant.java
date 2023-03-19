package Products;
import Attributes.Attribute;

public class ProductPant extends Product{

  public ProductPant(int id, double price, String title, Attribute[] attributes) {
      super(ProductCategory.Pant, id, price, title,attributes);

  }

}
