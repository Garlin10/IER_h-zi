package rescueagents;

import rescueframework.AbstractRobotControl;
import rescueframework.MainFrame;
import rescueframework.RescueFramework;
import world.Cell;
import world.Path;
import world.Robot;
import world.RobotPercepcion;

import jason.asSyntax.Literal;
import world.MapWrapper;

import java.util.ArrayList;

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
	
	private String color = null;
	
	public String getColor(){
		return color;
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
        ArrayList discoveredInjureds = percepcion.getDiscoveredInjureds();
        ArrayList injureds = new ArrayList<>();
        for(int i = 0; i < discoveredInjureds.size(); i++) {
            Injured seged = (Injured) discoveredInjureds.get(i);
            if(!seged.isSaved()) {
                injureds.add(discoveredInjureds.get(i));
            }
        }
        Path injuredPath = percepcion.getShortestInjuredPath(robot.getLocation());
        //Default érték, hogy ne mozogjon.
        int step = -1;

        // Ha nincs nála sérült
        int sumHealth = 1;
        if (!robot.hasInjured()) {

			color = "N";
			
            // De lát sérültet, akkor felé mozog
            if (injuredPath != null) {


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

        } else {
            //Ha sérült van nála
            if (percepcion.getShortestExitPathR(robot.getLocation()) != null) {
                RescueFramework.log("Box type" + robot.getInjured().getType());
                //Ha nála van a sérült, akkor csak kifelé kell mennie.
                if(robot.getInjured().getType() == 1)
                {
					color = "R";
                    if (robot.getLocation().getX() == percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getX() &&
                            robot.getLocation().getY() > percepcion.getShortestExitPathR(robot.getLocation()).getFirstCell().getY())
						{step = 0;}
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
                if(robot.getInjured().getType() == 0)
                {
					color = "B";
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
			MapWrapper.clearPerceptWrapper("robot" + getRobotID());
			MapWrapper.addPerceptWrapper("robot" + getRobotID(),Literal.parseLiteral("step" + step));
			String color = getColor();
			MapWrapper.addPerceptWrapper("robot" + getRobotID(),Literal.parseLiteral("boxType(" + color +")"));
            return 1;
        }

// Amúgy meg csinálja amire teljesült a feltétel.
        else
			{
				MapWrapper.clearPerceptWrapper("robot" + getRobotID());
				MapWrapper.addPerceptWrapper("robot" + getRobotID(),Literal.parseLiteral("step(" + step +")"));
				String color = getColor();
				MapWrapper.addPerceptWrapper("robot" + getRobotID(),Literal.parseLiteral("boxType(" + color +")"));
				return step;
			}
    }
	
}