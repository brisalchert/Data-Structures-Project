package Attributes;
import java.util.HashSet;
public interface Attribute {
    /**
     * Returns the set of the ids with this attribute
     * @return ids of products with attribute
     */
    public HashSet<Integer> getSet();

    public String name();
}
