package Attributes;
import java.util.HashSet;

public enum ColorCategory implements Attribute {
    Red(new HashSet<Integer>()),
    Blue(new HashSet<Integer>()),
    Green(new HashSet<Integer>()),
    Purple(new HashSet<Integer>());

    private HashSet<Integer> set;

    /**
     * sets a hashset for the color type
     * @param set hashset for color of type
     */
    ColorCategory(HashSet<Integer> set){
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
