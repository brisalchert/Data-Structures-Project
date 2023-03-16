package Products;// Represent a single product. This is the base class for all the product types
import java.util.Date;

public abstract class Product {
    private double price;           // price of the product
    private ProductCategory type;   // type of the product
    private String title;           // title of the product
    private Date listingDate;       // when the product first brought to the inventory
    private int id;                 // unique id


  /**
  * Constructs a Product
  * @param type a valid object of ProductCategory
  * @param id value used for identification
  * @param price cost of Product in dollars
  * @param title display name of Product
  */
  public Product(ProductCategory type, int id, double price, String title){
        this.price = price;
        this.type = type;
        this.title = title;
        this.listingDate = new Date();
        this.id = id;
  }

  //accessors ------------------------------------------------------------

  /**
  * returns price of Product
  * @return price of Product in dollars
  */
  public double getPrice() {
    return price;
  }

  /**
   * returns title of Product
   * @return title of Product
   */
  public String getTitle() {
    return title;
  }

  /**
  * returns listingDate of Product
  * @return listingDate of Product
  */
  public Date getDate() {
    return listingDate;
  }

  /**
   * returns id of Product
   * @return id of Product
   */
  public int getId() {
        return id;
    }

  /**
   * returns type of Product
   * @return type of Product
   */
  public ProductCategory getType(){
      return type;
  }

  /**
   * string representation of Catalog
   * @return string representation of Catalog
   */
  public String toString(){
      return  "Type: " + type +",Title: " + title + ",Id: " + id;
  }

  //mutators ------------------------------------------------------------

 /**
  * sets price of Product
  * @param price in dollars
  */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * sets title of Product
   * @param title to be displayed
   */
  public void setTitle(String title) {
    this.title = title;
  }

}
