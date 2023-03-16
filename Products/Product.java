package Products;// Represent a single product. This is the base class for all the product types
import java.util.Date;
public abstract class Product {
  public double price; // price of the product
  public String title; // title of the product
  public Date listingDate; // when the product first brought to the inventory

  public Product(double price, ProductCategory product){
    this.price = price;
    this.title = product.toString();
    this.listingDate = new Date();
  }

  //accessors ------------------------------------------------------------

  public double getPrice() {
    return price;
  }

  public String getTitle() {
    return title;
  }

  public Date getDate() {
    return listingDate;
  }

  //mutators ------------------------------------------------------------

  public void setPrice(double price) {
    this.price = price;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
