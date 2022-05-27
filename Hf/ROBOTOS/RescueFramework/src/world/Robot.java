package world;

import rescueagents.RobotControl;
import rescueframework.AbstractRobotControl;
import rescueframework.RescueFramework;

/**
 * Robot on the map
 */
public class Robot {
    // Location of the robot
    private Cell location;
    
    // The injured being carried by the robot
    private Box box = null;
    
    // The control object of th robot
    private AbstractRobotControl control;
    
    
    /**
     * Default constructor
     * 
     * @param startCell     The start cell of the robot
     * @param percepcion    Percepcion of the robot
     */
    public Robot(Cell startCell, RobotPercepcion percepcion) {
        location = startCell;
        control = new RobotControl(this, percepcion);
    }
    
    /**
     * Return the robot location
     * @return      The robot location
     */
    public Cell getLocation() {
        return location;
    }
    
    /**
     * Set the location of the robot
     * @param newLocation       The new location of the robot
     */
    public void setCell(Cell newLocation) {
        location = newLocation;
    }
    
    /**
     * Return true if the robot is currently carrying an injured
     * 
     * @return      True if the robot is carrying an injured
     */
    public boolean hasBox() {
        return box != null;
    }
    
    /**
     * Pick up an injured if the robot does not carries any
     */
    public void pickupInjured() {
        if (box == null) {
            box = location.getInjured();
            RescueFramework.log("Picking up box: "+ box.toString());
            location.setInjured(null);
        } else {
            RescueFramework.log("Unable to pick up box: already has one.");
        }
    }
    
    /**
     * Drop the injured if the robot carries one
     * 
     * @return      Return the injured that is dropped
     */
    public Box dropInjured() {
        RescueFramework.log("Dropping box "+ box.toString()+" at "+location.toString());
        Box result = box;
        box = null;
        return result;
    }
    
    /**
     * Return the injured being carried by the robot
     * 
     * @return  The injured being carried by the robot
     */
    public Box getBox() {
        return box;
    }
    
    /**
     * Call the AbstractRobotControl to decide the next step of the robot
     * 
     * @return      Stepping direction of the robot or NULL to stay in place
     */
    public Integer step() {
        if (control == null) return null;
        return control.step();
    }
}
