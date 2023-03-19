package Products;

import Attributes.Attribute;

public interface TypeConstructor {
    Product apply(int id, double price, String title, Attribute[] attributes);
}
