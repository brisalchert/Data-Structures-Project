import DataStructure.Catalog;
import Products.ProductCategory;

public class main {
    public static void main(String[] args) {
        Catalog cat = new Catalog(10);
        cat.addProduct(ProductCategory.Hat, 1000, "Da Bomb Hat");
        cat.addProduct(ProductCategory.Plush, 100000, "Cat");
        System.out.println(cat.getCatalog());
    }
}
