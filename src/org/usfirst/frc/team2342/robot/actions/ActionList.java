package org.usfirst.frc.team2342.robot.actions;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Scanner;

public class ActionList {
	private ArrayList<Action> actions = new ArrayList<Action>();
	private ArrayList<String> names = new ArrayList<String>();
	private String curAction = "";
	
	public ActionList(ArrayList<Action> act) throws DependencyException {
		if ((act.size() > 0) || !act.isEmpty()) {
			this.actions = act;
			setup();
		}
		else {
			System.out.println("There are no actions currently Running.");
			System.out.println("If you wan't actions to run, setup the array within Robot.java");
			System.out.println("Otherwise, check to see if your finger is on the emergancy stop button.");
		}
	}
	
	private void setup() throws DependencyException {
		for (Action a : this.actions) {
			if (this.names.contains(a.name))
				throw new DependencyException();
			this.names.add(a.name);
			
			if (this.names.contains(a.dependencies))
				throw new DependencyException();
		}
		SmartDashboard.putString("DB/String 0", this.names.toString());
	}
	
	// stop all of the motors and the actions all at once.
	public void stopAll() {
		for (Action a : this.actions) {
			a.stop();
		}
	}
	
	// Check Dependencies
	public boolean checkDep(String dep, Action a) {
			if ((a.name == dep) && (a.state == Action.State.FINISHED))
				return true;
		return false;
	}
	
	public boolean isDone() {
		int amount = 0;
		for (Action a : this.actions)
			if (a.state == Action.State.FINISHED)
				amount++;
		return (amount == this.actions.size());
	}
	
	// get the current action
	public String getAction() {
		return this.curAction;
	}
	
	// Execute the Action List
	public void execute() {
		// start the execution of the actions
		boolean depMet = false;
		int index = 0;
		for (Action a : this.actions) {
			this.curAction = a.name;
			depMet = checkDep(a.dependencies, a);
			index = (this.actions.indexOf(a) > 0) ? this.actions.indexOf(a) : 0;
			
			if (a.state == Action.State.FINISHED || depMet)
				continue;
			
			else if ((index != 0) && (this.actions.get(index-1).state != Action.State.FINISHED) && (a.getClass().equals(this.actions.get(index-1).getClass())))
				continue;

			else if (a.state == Action.State.NOT_STARTED) {
					a.start();
					a.state = Action.State.IN_PROGRESS;
			}
			
			if (a.state == Action.State.IN_PROGRESS)
				a.run();
						
			if (a.isCompleted()) {
				a.stop();
				a.state = Action.State.FINISHED;
			}
		}
	}
}