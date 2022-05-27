package rescueagents;

import rescueframework.AbstractRobotControl;
import rescueframework.RescueFramework;
import world.Cell;
import world.Path;
import world.Robot;
import world.RobotPercepcion;

import java.util.ArrayList;

import world.Box;

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



        // felderített sérültek listája
        ArrayList discoveredBoxes = percepcion.getDiscoveredInjureds();
        ArrayList box = new ArrayList<>();
        for(int i = 0; i < discoveredBoxes.size(); i++) {
            Box seged = (Box) discoveredBoxes.get(i);
            if(!seged.isSaved()) {
                box.add(discoveredBoxes.get(i));
            }
        }
        Path boxPath = percepcion.getShortestInjuredPath(robot.getLocation());
        //Default érték, hogy ne mozogjon.
        int step = -1;


        // Ha nincs nála sérült
        int sumHealth = 1;
        if (!robot.hasBox()) {

            // De lát sérültet, akkor felé mozog
            if (boxPath != null) {


                    Cell injLoc = boxPath.getFirstCell();
                    if (robot.getLocation().getX() == injLoc.getX() && robot.getLocation().getY() > injLoc.getY())
                        step = 0;
                    else if (robot.getLocation().getX() == injLoc.getX() && robot.getLocation().getY() < injLoc.getY())
                        step = 2;
                    else if (robot.getLocation().getX() < injLoc.getX() && robot.getLocation().getY() == injLoc.getY())
                        step = 1;
                    else if (robot.getLocation().getX() > injLoc.getX() && robot.getLocation().getY() == injLoc.getY())
                        step = 3;
                }

        } else {
            //Ha doboz van nála
            if (percepcion.getShortestExitPathR(robot.getLocation()) != null) {
                RescueFramework.log("Box type" + robot.getBox().getType());
                //Ha nála van a doboz, akkor csak kifelé kell mennie.
                if(robot.getBox().getType() == 1)
                {
                    if (robot.getLocation().getX() == percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() > percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getY())
                        step = 0;
                    else if (robot.getLocation().getX() == percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() < percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getY())
                        step = 2;
                    else if (robot.getLocation().getX() < percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() == percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getY())
                        step = 1;
                    else if (robot.getLocation().getX() > percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() == percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getY())
                        step = 3;
                }
                if(robot.getBox().getType() == 0)
                {
                    if (robot.getLocation().getX() == percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() > percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getY())
                        step = 0;
                    else if (robot.getLocation().getX() == percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() < percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getY())
                        step = 2;
                    else if (robot.getLocation().getX() < percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() == percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getY())
                        step = 1;
                    else if (robot.getLocation().getX() > percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() == percepcion.getShortestExitPathB(robot.getLocation()).getFirstCell().getY())
                        step = 3;
                }

            }
        }
// Ha semmi nem teljesült a fenti feltételekből, akkor megáll. (Felveszi a default értéket.)
        if (step == -1)
        {
            return 0;
        }

// Amúgy meg csinálja amire teljesült a feltétel.
        else
            return step;
    }
}