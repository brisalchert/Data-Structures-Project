package Attributes;

import Attributes.Values;
import Products.Product;

public interface TypeConstructor {
    Product apply(int id, double price, int daysAfterMinDay, String title, Values[] attributes);
}
