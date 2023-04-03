package Attributes;
import java.util.ArrayList;
import java.util.HashSet;
import Products.*;

public enum Values implements Category {
    //Size 0-3
    Shirt (new HashSet<Integer>(), Category.Product, ProductShirt::new),
    Hat   (new HashSet<Integer>(), Category.Product, ProductHat::new),
    Pants  (new HashSet<Integer>(), Category.Product, ProductPant::new),
    Plush (new HashSet<Integer>(), Category.Product, ProductPlush::new),

    //Size 4-7
    Small(new HashSet<Integer>(), Category.Size),
    Medium(new HashSet<Integer>(), Category.Size),
    Large(new HashSet<Integer>(), Category.Size),
    XLarge(new HashSet<Integer>(), Category.Size),

    //Color 8-11
    Red(new HashSet<Integer>(), Category.Color),
    Blue(new HashSet<Integer>(), Category.Color),
    Green(new HashSet<Integer>(), Category.Color),
    Purple(new HashSet<Integer>(), Category.Color),

    //Animal 12-15
    Cat(new HashSet<Integer>(), Category.Animal),
    Dog(new HashSet<Integer>(), Category.Animal),
    Seal(new HashSet<Integer>(), Category.Animal),
    Rabbit(new HashSet<Integer>(), Category.Animal);

    private HashSet<Integer> set;
    private Category category;
    private TypeConstructor constructor;

    /**
     * sets a hashset for the size type
     * @param set hashset for size of type
     */
    Values(HashSet<Integer> set, Category type){
        this.set = set;
        this.category = type;
    }

    /**
     * sets a hashset for the size type
     * @param set hashset for size of type
     */
    Values(HashSet<Integer> set, Category type, TypeConstructor constructor){
        this.set = set;
        this.category = type;
        this.constructor = constructor;
    }

    /**
     * returns the hashset for type
     * @return hashset of ids with this attribute
     */
    public HashSet<Integer> getSet(){
        return this.set;
    }

    /**
     * returns the hashset for type
     * @return hashset of ids with this attribute
     */
    public Values.Category getCategory(){
        return this.category;
    }

    /**
     * returns the constructor method for type
     * @return constructor method for object of type
     */
    public TypeConstructor getConstructor(){
        if(this.category == Category.Product){
            return this.constructor;
        }
        return null;
    }

    public enum Category  implements Attributes.Category {
        Product(new ArrayList<Values>()),
        Size(new ArrayList<Values>()),
        Color(new ArrayList<Values>()),
        Animal(new ArrayList<Values>());

        private ArrayList<Values> set;

        /**
         * sets a hashset for the size type
         * @param set hashset for size of type
         */
        Category(ArrayList<Values> set){
            this.set = set;
        }

        /**
         * returns the hashset for type
         * @return hashset of ids with this attribute
         */
        public ArrayList<Values> getSearchSet(){
            return this.set;
        }
    }
};
