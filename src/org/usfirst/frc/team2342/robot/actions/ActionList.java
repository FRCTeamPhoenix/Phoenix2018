package org.usfirst.frc.team2342.robot.actions;
import java.util.*;

public class ActionList{
	
	private ArrayList<Action> actions;
	
	private boolean emergancyStop = false;
	
	public ActionList(){
		
	}
	
	//Execute all actions who's conditions have been satisfied
	public void execute(){
		
		for(Action action: actions){
			
			if(action.currentState == Action.State.finished)
				continue;
			else{
				if(action.currentState == Action.State.notStarted) {
					action.onStart();
					action.currentState = Action.State.inProgress;
				}
				else{
					action.run();
				}
				if(action.isCompleted()) {
					action.onStop();
					action.currentState = Action.State.finished;
				}
			}
		}
	}
	
	public void stop(){
		
		emergancyStop = true;
	}
	
	public void start(){
		
		emergancyStop = false;
	}
	
	
}