package world;

import java.util.Random;

/**
 * Class representing injured people on the map
 */
public class Box {

    // True if the injured have already been transported to an exit cell
    private boolean saved = false;
    // True if the injured is already discovered by a robot
    private boolean discovered = false;
    // Cell location of the injured
    private Cell location = null;
    // Static ID value of the next injured
    private static int nextID = 1;
    // ID of the injured object
    private int id;

    private int type;

    /**
     * Returns the location cell of the injured
     * @return      The location cell of the injured
     */
    public Cell getLocation() {
        return location;
    }

    /**
     * Set the location cell of the injured
     * @param location      The location cell of the injured
     */
    public void setLocation(Cell location) {
        this.location = location;
    }
    
    
    /**
     * Default constructor
     * 
     * @param health        Health level of the new injured
     */
    public Box(int health) {

        type = new Random().nextInt(2);
        // Generate unique ID
        id = nextID;
        nextID++;
    }
    
    public int getType(){
        return type;
    }
    

    /**
     * Mark the injured as saved
     */
    public void setSaved() {
        saved = true;
    }
    
    /**
     * Returns true if the injured is already saved
     * 
     * @return      True if the injured is already saved
     */
    public boolean isSaved() {
        return saved;
    }
    
    /**
     * Returns the health level in percents
     * @return      The health level in percents
     */

    
    /**
     * Returns true if the injured is alredy discovered
     * @return      True if the injured is alredy discovered
     */
    public boolean isDiscovered() {
        return discovered;
    }
    
    /**
     * Returns true if the injured is still alive
     * @return      True if the injured is still alive
     */

    
    /**
     * Return the injured ID and health level in a string
     * @return      The injured ID and health level in a string 
     */
    public String toString() {
        return "ID="+id;
    }
    
    /**
     * Mark the injured as discovered
     * @param discovered        Set true to mark the injured as discovered
     */
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
    
}
