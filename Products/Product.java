package Products;// Represent a single product. This is the base class for all the product types
import Attributes.Values;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public abstract class Product {
    private double price;                                                               // price of the product
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();        // Used to format the price
    private Values type;                                                                // type of the product
    private String title;                                                               // title of the product
    private Date listingDate;                                                           // when the product first brought to the inventory
    private int daysAfterMinDay;                                                        // How many days after Jan 1 2020 the product was listed
    private int id;                                                                     // unique id
    private Values[] attributes;                                                        // Array of attributes


  /**
  * Constructs a Product
  * @param type a valid object of ProductCategory
  * @param id value used for identification
  * @param price cost of Product in dollars
  * @param title display name of Product
  * @param daysAfterMinDay days after Jan 1 2020 the Product was listed
  */
  public Product(Values type, int id, double price, int daysAfterMinDay, String title, Values[] attributes){
        this.price = price;
        this.type = type;
        this.title = title;
        this.id = id;
        this.attributes = attributes;

        // Set listingDate
        Calendar date = Calendar.getInstance();
        date.setLenient(true);
        date.set(Calendar.YEAR, 2020);
        date.set(Calendar.MONTH, Calendar.JANUARY);
        date.set(Calendar.DAY_OF_MONTH, (this.daysAfterMinDay = daysAfterMinDay));
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
     * Returns the number of days after Jan 1 2020 the product was listed
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
  public Values getType(){
      return type;
  }

  /**
   * Returns attributes of product
   * @return array of attributes
   */
  public Values[] getAttributes(){
      return attributes;
  }

  /**
   * string representation of Catalog
   * @return string representation of Catalog
   */
  public String toString() {
      String datePattern = "MM/dd/yyyy";
      SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
      final int MAX_TITLE_LENGTH = 24;
      final int MAX_ATTRIBUTE_LENGTH = 28;
      final int MAX_TYPE_LENGTH = 12;
      final int MAX_PRICE_LENGTH = 12;
      final int MAX_ID_LENGTH = 7;
      String modifiedTitle = title;
      String modifiedType = String.valueOf(type);
      String modifiedAttributes = Arrays.toString(attributes);
      String modifiedPrice = currencyFormatter.format(price);
      String modifiedID = String.valueOf(id);

      // Make title standard length
      while (modifiedTitle.length() < MAX_TITLE_LENGTH) {
          modifiedTitle += " ";
      }

      // Make type standard length
      while (modifiedType.length() < MAX_TYPE_LENGTH) {
          modifiedType += " ";
      }

      // Make attributes standard length
      while (modifiedAttributes.length() < MAX_ATTRIBUTE_LENGTH) {
          modifiedAttributes += " ";
      }

      // Make price standard length
      while (modifiedPrice.length() < MAX_PRICE_LENGTH) {
          modifiedPrice += " ";
      }

      // Make id standard length
      while (modifiedID.length() < MAX_ID_LENGTH) {
          modifiedID += " ";
      }

      return modifiedID + modifiedTitle + modifiedType + modifiedAttributes + modifiedPrice + dateFormat.format(listingDate);
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
