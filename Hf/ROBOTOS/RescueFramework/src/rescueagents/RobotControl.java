package rescueagents;

import rescueframework.AbstractRobotControl;
import world.Cell;
import world.Path;
import world.Robot;
import world.RobotPercepcion;

import java.util.ArrayList;

import world.AStarSearch;
import world.Injured;

/**
 * RobotControl class to implement custom robot control strategies
 */
public class RobotControl extends AbstractRobotControl {
    /**
     * Default constructor saving world robot object and percepcion
     *
     * @param robot The robot object in the world
     * @param percepcion Percepcion of all robots
     */
    public RobotControl(Robot robot, RobotPercepcion percepcion) {
        super(robot, percepcion);
    }

    /**
     * Custom step strategy of the robot, implement your robot control here!
     *
     * @return Return NULL for staying in place, 0 = step up, 1 = step right, 2 =
     * step down, 3 = step left
     */
    @SuppressWarnings("null")
    public Integer step() {
        // By default the robot stays in place
        // legrövidebb út a legközelebbi sérülthöz
        Path injuredPath = percepcion.getShortestInjuredPath(robot.getLocation());

        // felderített sérültek listája
        ArrayList discoveredInjureds = percepcion.getDiscoveredInjureds();
        ArrayList injureds = new ArrayList<>();
        for(int i = 0; i < discoveredInjureds.size(); i++) {
            Injured seged = (Injured) discoveredInjureds.get(i);
            if(!seged.isSaved()) {
                injureds.add(discoveredInjureds.get(i));
            }
        }
        /*
        //A legkisebb még megmenthető életű sérült
        Injured minHealthInjured = null;
        // felfedezett sérültek sum élete
        int sumHealth = 0;
        // alapertelmezett az elso, ha van
        if(injureds.size() > 0) {
            Injured seged = (Injured) injureds.get(0);
            if(injureds.get(0) != null && seged.isAlive()) {
                minHealthInjured = seged;
                sumHealth += seged.getHealth();
            }
        }
        Injured seged;
        for(int i = 1; i < injureds.size(); i++) {
            seged = (Injured) injureds.get(i);
            if(minHealthInjured == null) {
                minHealthInjured = seged;
            }
            //Akkor cseréli a legsérültebbet, ha van nála olyan rosszabb állapotú, aki él, és még kiér vele.
            else if(minHealthInjured.getHealth() > seged.getHealth() && seged.isAlive() && (minHealthInjured.getHealth() <= percepcion.getShortestExitPath(robot.getLocation()).getLength())) {
                minHealthInjured = seged;
            }
            sumHealth += seged.getHealth();
        }
        //A legkevesebb életűhöz vezető út
        Path minHealthInjuredPath = null;
        if(minHealthInjured != null && minHealthInjured.getLocation() != null) {
            minHealthInjuredPath = AStarSearch.search(robot.getLocation(),minHealthInjured.getLocation(), -1);
            System.out.println("Min health: " + minHealthInjured.getHealth() + "SUM: " + sumHealth);
        }*/

        //Default érték, hogy ne mozogjon.
        int step = -1;
        /*
        if(minHealthInjuredPath != null) {
            //A legsérültebbre állítjuk
            injuredPath = minHealthInjuredPath;
        }*/
        // Ha nincs nála sérült
        int sumHealth = 1;
        if (!robot.hasInjured()) {
            // De lát sérültet, akkor felé mozog
            if (injuredPath != null) {
                if(sumHealth == 0 && percepcion.getShortestUnknownPath(robot.getLocation()) != null) {
                    Cell unkLoc = percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell();
                    if (robot.getLocation().getX() == unkLoc.getX() && robot.getLocation().getY() > unkLoc.getY())
                        step = 0;
                    if (robot.getLocation().getX() == unkLoc.getX() && robot.getLocation().getY() < unkLoc.getY())
                        step = 2;
                    if (robot.getLocation().getX() < unkLoc.getX() && robot.getLocation().getY() == unkLoc.getY())
                        step = 1;
                    if (robot.getLocation().getX() > unkLoc.getX() && robot.getLocation().getY() == unkLoc.getY())
                        step = 3;
                } else {
                    Cell injLoc = injuredPath.getFirstCell();
                    if (robot.getLocation().getX() == injLoc.getX() && robot.getLocation().getY() > injLoc.getY())
                        step = 0;
                    else if (robot.getLocation().getX() == injLoc.getX() && robot.getLocation().getY() < injLoc.getY())
                        step = 2;
                    else if (robot.getLocation().getX() < injLoc.getX() && robot.getLocation().getY() == injLoc.getY())
                        step = 1;
                    else if (robot.getLocation().getX() > injLoc.getX() && robot.getLocation().getY() == injLoc.getY())
                        step = 3;
                }
            } else if (percepcion.getShortestUnknownPath(robot.getLocation()) != null) { // szürke mező felé
                //Felfedezetlen terület felé mozog
                if (robot.getLocation().getX() == percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() > percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getY())
                    step = 0;
                if (robot.getLocation().getX() == percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() < percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getY())
                    step = 2;
                if (robot.getLocation().getX() < percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() == percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getY())
                    step = 1;
                if (robot.getLocation().getX() > percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() == percepcion.getShortestUnknownPath(robot.getLocation()).getFirstCell().getY())
                    step = 3;
            }
        } else {
            //Ha sérült van nála
            if (percepcion.getShortestExitPath(robot.getLocation()) != null) {
                //Ha nála van a sérült, akkor csak kifelé kell mennie.
                if (robot.getLocation().getX() == percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() > percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getY())
                    step = 0;
                else if (robot.getLocation().getX() == percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() < percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getY())
                    step = 2;
                else if (robot.getLocation().getX() < percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() == percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getY())
                    step = 1;
                else if (robot.getLocation().getX() > percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getX() &&
                        robot.getLocation().getY() == percepcion.getShortestExitPath(robot.getLocation()).getFirstCell().getY())
                    step = 3;
            }
        }
// Ha semmi nem teljesült a fenti feltételekből, akkor megáll. (Felveszi a default értéket.)
        if (step == -1)
            return null;
// Amúgy meg csinálja amire teljesült a feltétel.
        else
            return step;
    }
}