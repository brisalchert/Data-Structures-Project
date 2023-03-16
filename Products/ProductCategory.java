package Products;

public enum ProductCategory {
  Shirt (ProductShirt::new),
  Hat (ProductHat::new),
  Pant (ProductPant::new),
  Plush (ProductPlush::new);

  private TypeConstructor constructor;

  ProductCategory(TypeConstructor constructor){
      this.constructor = constructor;
  }
}
