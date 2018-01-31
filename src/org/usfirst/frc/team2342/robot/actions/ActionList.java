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
	private boolean emergancyStop = false;
	private int buttonID = 1;
	private Joystick joystick = new Joystick(-1);
	
	public ActionList(ArrayList<Action> act, Joystick j) throws DependencyException {
		this.joystick = j;
		if ((act.size() > 0) || !act.isEmpty()) {
			this.actions = act;
			setup();
		}
		else {
			this.emergancyStop = true;
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
	
	// setup the button
	public void setButton(int btn) {
		this.buttonID = btn;
	}
	
	// check the button input
	public void buttonInput() {
		this.emergancyStop = this.joystick.getRawButton(buttonID);
	}
	
	// stop all of the motors and the actions all at once.
	public void stopAll() {
		for (Action a : this.actions) {
			a.stop();
		}
	}
	
	// set EMS
	public void setEMS(boolean mode) {
		this.emergancyStop = mode;
	}
	
	// get EMS
	public boolean getEMS() {
		return this.emergancyStop;
	}
	
	// Check Dependencies
	public boolean checkDep(String dep, Action a) {
			if ((a.name == dep) && (a.state == Action.State.FINISHED))
				return true;
		return false;
	}
	
	// Execute the Action List
	public void execute() {
		// start the execution of the actions
		boolean depMet = false;
		int index = 0;
		for (Action a : this.actions) {
			buttonInput();
			if (this.emergancyStop == true || this.joystick.getRawButton(this.buttonID)) {
				stopAll();
				break;
			}
			
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
			
			buttonInput();
			
			if (a.state == Action.State.IN_PROGRESS)
				a.run();
			
			if (a.isCompleted()) {
				a.stop();
				a.state = Action.State.FINISHED;
			}
			
			buttonInput();
		}
	}
}