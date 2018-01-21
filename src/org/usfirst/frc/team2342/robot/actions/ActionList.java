package org.usfirst.frc.team2342.robot.actions;
import java.util.*;

public class ActionList{
	
	private ArrayList<Action> actions;
	private ArrayList<String> names;
	
	private boolean emergancyStop = false;
	
	public ActionList(ArrayList<Action> actions) throws DependencyException{
		this.actions = actions;
		names = new ArrayList<String>();
		for(Action action: actions){
			if(names.contains(action.name)) {
				throw new DependencyException();
			}
			names.add(action.name);
		}
	}
	
	//Execute all actions who's conditions have been satisfied
	public void execute() throws DependencyException{
		
		for(Action action: actions){
			
			if(action.currentState == Action.State.finished)
				continue;
			boolean dependenciesMet = true;
			for(String dep: action.dependencies){
				if(!names.contains(dep)){
					throw new DependencyException();
				}
				for(Action actionID: actions) {
					if(actionID.name.equals(dep) && actionID.currentState == Action.State.finished){
						
					}
					else {
						dependenciesMet = false;
					}
				}
			}
			if(!dependenciesMet) {
				continue;
			}
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

	
	public void stop(){
		
		emergancyStop = true;
	}
	
	public void start(){
		
		emergancyStop = false;
	}
	
	
}