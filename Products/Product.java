package Products;// Represent a single product. This is the base class for all the product types

import Attributes.Attribute;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public abstract class Product {
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();        // Used to format the price
    private double price;           // price of the product
    private ProductCategory type;   // type of the product
    private String title;           // title of the product
    private Date listingDate;       // when the product first brought to the inventory
    private int daysAfterMinDay;    // How many days after Jan 1 2023 the product was listed
    private int id;                 // unique id
    private Attribute[] attributes; // Array of attributes


  /**
  * Constructs a Product
  * @param type a valid object of ProductCategory
  * @param id value used for identification
  * @param price cost of Product in dollars
  * @param title display name of Product
  */
  public Product(ProductCategory type, int id, double price, String title, Attribute[] attributes){
        this.price = price;
        this.type = type;
        this.title = title;
        this.id = id;
        this.attributes = attributes;

        // Generate random listing date sometime after Jan 1st, 2023
        Random random = new Random();
        Calendar date = Calendar.getInstance();
        date.setLenient(true);
        date.set(Calendar.YEAR, 2023);
        date.set(Calendar.DAY_OF_MONTH, (daysAfterMinDay = random.nextInt(1000)));
        this.listingDate = date.getTime();
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
     * Returns the number of days after Jan 1 2023 the product was listed
     * @return the number of days after the minimum date
     */
  protected int getDaysAfterMinDay() {
      return daysAfterMinDay;
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
   * Returns attributes of product
   * @return array of attributes
   */
  public Attribute[] getAttributes(){
      return attributes;
  }

  /**
   * string representation of Catalog
   * @return string representation of Catalog
   */
  public String toString(){
      String result = "";
      return  "Type: " + type + ", Id: " + id + ", Att: " + Arrays.toString(attributes) + ", Price: " + currencyFormatter.format(price) + ", Listing Date: " + listingDate;
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
