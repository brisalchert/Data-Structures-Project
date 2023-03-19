package Attributes;
import java.util.HashSet;

public enum AnimalCategory implements Attribute {
    Cat(new HashSet<Integer>()),
    Dog(new HashSet<Integer>()),
    Seal(new HashSet<Integer>()),
    Rabbit(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * sets a hashset for the animal type
     * @param set hashset for animal of type
     */
    AnimalCategory(HashSet<Integer> set){
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
