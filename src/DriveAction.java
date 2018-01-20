import java.util.ArrayList;

public class DriveAction extends Action{
	
	private double distance = 0;
	private double speed = 0;
	
	private String name;
	
	private boolean running;
	
	public DriveAction(double distance, double speed, ArrayList<String> dependencies){
		
		super("drive", dependencies);
		
		this.distance = distance;
		this.speed = speed;

	}
	
	public void start(){
		
		//more stuff might go here in the future
		
		running = true;
		
	}
	
	public void run(ArrayList<Action> actions){
		
		if(!running && dependenceFufilled(actions)){
			
			start();
		}
		
		if(running){
			
			//TODO: Drive train move certain distance needs to be implemented
			
		}
	}
	
	public void stop(){
		
		running = false;
	}
	
	public void reset(){
		
		stop();
		super.reset();
	}
}