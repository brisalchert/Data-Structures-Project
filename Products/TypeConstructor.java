package Products;

public interface TypeConstructor {
    Product apply(long id, double price, String title);
}
