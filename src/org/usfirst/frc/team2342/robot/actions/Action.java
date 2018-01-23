/*Base Action Class, not to be mistaken for usefull Action classes*/

package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

public class Action {
	
	//0 = not yet running
	//1 = in progress
	//2 = completed
	private static int currentState;

	//Tag associated with the action for dependency detection
	private static String tag = "";
	
	private static ArrayList<String> dependencies = new ArrayList<String>();
	
	public Action(String tag, ArrayList<String> dependencies){
		this.tag = tag;

		this.dependencies.addAll(dependencies);
		
		currentState = 0;
	}

	//Are the dependencies fulfilled?
	public boolean dependenceFufilled(ArrayList<Action> actions){
		
		int ammountFulfilled = 0;
		
		for(Action action : actions){
			
			for(String dependency : dependencies){
			
				if(action.gettag().equals(dependency)){
					
					ammountFulfilled++;
					
				}
			}
		}
		
		return (ammountFulfilled == dependencies.size());
	}
	
	//Start action
	public void start(){
		
		currentState = 1;
	}
	
	//See if action is done
	public boolean isCompleted()
	{
		return (currentState == 2);
	}
	
	//If the dependencies are fulfilled 
	public void run(ArrayList<Action> actions){
		
		if(!((currentState == 1) || isCompleted()) && dependenceFufilled(actions)){
			
			start();
		}
	}
	
	//Stop action
	public void stop(){
		
		currentState = 2;
	}
	
	//Reset the action so it can go again
	public void reset(){
		
		stop();
		
		currentState = 0;
	}
	
	//Get the action tag
	public String gettag(){
		
		return tag;
	}
}