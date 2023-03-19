package Attributes;
import java.util.HashSet;

public enum AnimalCategory implements Attribute {
    Cat(new HashSet<Integer>()),
    Dog(new HashSet<Integer>()),
    Seal(new HashSet<Integer>()),
    Rabbit(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * Constructs a Product type
     * @param set method to construct Product of type
     */
    AnimalCategory(HashSet<Integer> set){
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
