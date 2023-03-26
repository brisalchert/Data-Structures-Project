package Products;

import Attributes.Attribute;

public interface TypeConstructor {
    Product apply(int id, double price, int daysAfterMinDay, String title, Attribute[] attributes);
}
