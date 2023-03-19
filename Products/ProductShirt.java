package Products;

import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;

public class ProductShirt extends Product{

    Attribute[] attributes = new Attribute[2]; // Size, Color

  public ProductShirt(int id, double price, String title, Attribute[] attributes) {
      super(ProductCategory.Shirt, id, price, title);
      this.attributes[0] = (attributes[0]) instanceof SizeCategory ? ((SizeCategory) (this.attributes[0] = attributes[0])) : null; //Size
      this.attributes[1] = (attributes[1]) instanceof ColorCategory ? ((ColorCategory) (this.attributes[1] = attributes[1])) : null; //Color
  }

    @Override
    public Attribute[] getAttributes() {
        return attributes;
    }
}
