package Attributes;
import java.util.HashSet;

public enum SizeCategory implements Attribute {
    Small(new HashSet<Integer>()),
    Medium(new HashSet<Integer>()),
    Large(new HashSet<Integer>()),
    XLarge(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * Constructs a Product type
     * @param set method to construct Product of type
     */
    SizeCategory(HashSet<Integer> set){
        this.set = set;
    }

    /**
     * returns the constructor method for type
     * @return set of ids with this attribute
     */
    public HashSet<Integer> getSet(){
        return this.set;
    }
}
