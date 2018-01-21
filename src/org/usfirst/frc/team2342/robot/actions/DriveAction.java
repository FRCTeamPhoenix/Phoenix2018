package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

public class DriveAction extends Action{
	
	private double distance = 0;
	private double angle = 0;
	
	private String name;
	
	private boolean running;
	
	public DriveAction(double distance, double angle, ArrayList<String> dependencies){
		
		super("drive", dependencies);
		
		this.distance = distance;
		this.angle = angle;
		
	}
	
	//Start driving a certain distance
	public void start(){
		
		//TODO: Drive train: move certain distance at the given angle
		
		super.start();
		
	}
	
	//Stop driving
	public void stop(){
		
		//TODO: Drive train stop
		
		super.stop();
	}
}