package org.usfirst.frc.team2342.robot.actions;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Scanner;

public class ActionList{
	
	private ArrayList<Action> actions;
	private ArrayList<String> names;
	
	public ActionList(ArrayList<Action> actions) throws DependencyException {
		this.actions = actions;
		names = new ArrayList<String>();
		for(Action action: actions){
			if(names.contains(action.name)) {
				throw new DependencyException();
			}
			names.add(action.name);
		}
		System.out.println(names);
		
		for(Action action : actions) {
			for(String dep : action.dependencies) {
				if(!names.contains(dep))
					throw new DependencyException();
			}
		}
	}
	
	//Execute all actions who's conditions have been satisfied
	public void execute() {
		int index = 0;
		for(Action action : actions) {
			boolean dependenciesMet = true;
			
			for(String dep : action.dependencies) {
				for(Action potentialDependency : actions) {
					if(!(potentialDependency.name.equals(dep) && potentialDependency.state == Action.State.FINISHED)) 
						dependenciesMet = false;
				}
			}
			
			System.out.println(action.name + " " + dependenciesMet);
			if(action.state == Action.State.FINISHED || !dependenciesMet)
				continue;
			
			if(action.state == Action.State.NOT_STARTED) {
				action.start();
				action.state = Action.State.IN_PROGRESS;
			}
			
			if(action.state == Action.State.IN_PROGRESS)
				action.run();
			
			if(action.isCompleted()) {
				action.stop();
				action.state = Action.State.FINISHED;
				continue;
			}
		}
	}	
}