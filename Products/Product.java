package Products;// Represent a single product. This is the base class for all the product types
import java.util.Date;
public abstract class Product {
    private double price; // price of the product
    private String title; // title of the product
    private Date listingDate; // when the product first brought to the inventory
    private long id; // unique id


  public Product(ProductCategory product, long id, double price, String title){
        this.price = price;
        this.title = title;
        this.listingDate = new Date();
        this.id = id;
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

  public long getId() {
        return id;
    }

  //mutators ------------------------------------------------------------

  public void setPrice(double price) {
    this.price = price;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
