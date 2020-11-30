/**
 * Represents an town as a node of a graph  
 * The Town class holds the name of the town and a list of adjacent towns, 
 * and other fields as desired, and the traditional methods (constructors, 
 * getters/setters, toString, etc.) 
 * It will implement the Comparable interface 
 * These are the minimum methods that are needed 
 * Please feel free to add as many methods as you need.
 * @author nanaa
 */
public class Town implements Comparable<Town>{
    
    /**
     * Town's name
     */
    private String name;
    
    /**
     * Constructor - Requires town's name.
     * @param name town's name
     */
    public Town(String name) {
        this.name = name;
    }
    
    /**
     * Copy constructor
     * @param templateTown an instance of Town
     */
    public Town(Town templateTown) {
        this(templateTown.name);
    }
    
    /**
     * Returns the town's name
     * @return town's name
     */
    public String getName() {
        return name;
    }
        
    /**
     * Compare to method between towns
     * @param o another town to be compared
     * @return 0 if names are equal, a positive or negative number 
     * if the names are not equal
     */
    @Override
    public int compareTo(Town o) {
        return this.name.compareTo(o.name);
    }
    
    /**
     * Equals method for towns
     * @param obj object to be compared
     * @return true if the town names are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        Town town = (Town) obj;
        return this.name.compareTo(town.name) == 0;
    }
    
    /**
     * Hash code for town
     * @return the hash code for the name of the town
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * String representation of town
     * @return the town name
     */
    @Override
    public String toString(){
        return name;
    }
}