package Attributes;
import java.util.HashSet;

public enum SizeCategory implements Attribute {
    Small(new HashSet<Integer>()),
    Medium(new HashSet<Integer>()),
    Large(new HashSet<Integer>()),
    XLarge(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * sets a hashset for the size type
     * @param set hashset for size of type
     */
    SizeCategory(HashSet<Integer> set){
        this.set = set;
    }

    /**
     * returns the hashset for type
     * @return hashset of ids with this attribute
     */
    public HashSet<Integer> getSet(){
        return this.set;
    }
}
