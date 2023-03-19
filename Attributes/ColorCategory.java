package Attributes;
import java.util.HashSet;

public enum ColorCategory implements Attribute {
    Red(new HashSet<Integer>()),
    Blue(new HashSet<Integer>()),
    Green(new HashSet<Integer>()),
    Purple(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * Constructs a Product type
     * @param set method to construct Product of type
     */
    ColorCategory(HashSet<Integer> set){
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
