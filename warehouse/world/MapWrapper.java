package world;

import jason.asSyntax.Literal;
import rescueframework.RescueFramework;
import java.util.logging.Logger;

//import java.Thread.sleep;

public class MapWrapper extends jason.environment.Environment{
	
	private static Logger logger = Logger.getLogger("jasonTeamSimLocal.mas2j." + MapWrapper.class.getName());
	public static MapWrapper self;
	//RescueFramework rf;
	
	public MapWrapper(){
		self = this;
		init();
	}
	
	public void init(){
		RescueFramework.main(null);
		//rf = new RescueFramework();
	}
	
	public static void addPerceptWrapper(String agent, Literal percept){
		self.addPercept(agent, percept);
	}
	
	public static void removePerceptWrapper(String agent, Literal percept){
		self.removePercept(agent, percept);
	}
	public static void clearPerceptWrapper(String agent){
		self.clearPercepts(agent);
	}
	
	public void createAgentWrapper(int num){
		try{
			getEnvironmentInfraTier().getRuntimeServices().createAgent(
			"robot" + num,
			"robot.asl",
			null,null,null,null,null);
		}
		catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	public void killAgentWrapper(int count){
		try{
			for (int i = 0; i < count; i++){
				getEnvironmentInfraTier().getRuntimeServices().killAgent(
				"robot" + i,"robot" + i, 1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}