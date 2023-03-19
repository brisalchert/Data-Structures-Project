package Products;
import Attributes.AnimalCategory;
import Attributes.Attribute;
import Attributes.ColorCategory;
import Attributes.SizeCategory;

public class ProductPlush extends Product{

    Attribute[] attributes = new Attribute[3]; // Size, Color, Animal

  public ProductPlush(int id, double price, String title, Attribute[] attributes) {
      super(ProductCategory.Plush, id, price, title);
      this.attributes[0] = (attributes[0]) instanceof SizeCategory ? ((SizeCategory) (this.attributes[0] = attributes[0])) : null; //Size
      this.attributes[1] = (attributes[1]) instanceof ColorCategory ? ((ColorCategory) (this.attributes[1] = attributes[1])) : null; //Color
      this.attributes[2] = (attributes[2]) instanceof AnimalCategory ? ((AnimalCategory) (this.attributes[2] = attributes[2])) : null; //Animal
  }

    @Override
    public Attribute[] getAttributes() {
        return attributes;
    }
}
