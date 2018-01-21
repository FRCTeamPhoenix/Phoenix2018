package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

public class Action {
	
	//0 = not yet running
	//1 = in progress
	//2 = completed
	private static int currentState;

	private static String name = "";
	
	private static ArrayList<String> dependencies;
	
	private boolean running;
	
	public Action(String name, ArrayList<String> dependencies){
		this.name = name;
		
		this.dependencies.addAll(dependencies);
		
		currentState = 0;
		
		running = false;
	}

	//Are the dependencies fulfilled?
	public boolean dependenceFufilled(ArrayList<Action> actions){
		
		int ammountFulfilled = 0;
		
		for(Action action : actions){
			
			for(String dependency : dependencies){
			
				if(action.getName().equals(dependency)){
					
					ammountFulfilled++;
					
				}
			}
		}
		
		return (ammountFulfilled == dependencies.size());
	}
	
	//
	public void start(){
		
		running = true;
	}
	
	//
	public boolean isCompleted()
	{
		return (currentState == 2);
	}
	
	//
	public void run(ArrayList<Action> actions){
		
		if(!running && dependenceFufilled(actions)){
			
			start();
		}
	}
	
	//
	public void stop(){
		
		running = false;
	}
	
	//
	public void reset(){
		
		stop();
		
		currentState = 0;
	}
	
	//
	public String getName(){
		
		return name;
	}
}