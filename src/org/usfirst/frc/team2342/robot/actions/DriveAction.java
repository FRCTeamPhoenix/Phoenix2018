package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

public class DriveAction extends Action{
	
	private double distance = 0;
	private double angle = 0;
	
	private String tag;
	
	private boolean running;
	
	//Constructor with a tag taken in for naming
	public DriveAction(double distance, double angle, String tag, ArrayList<String> dependencies){
		
		super(tag, dependencies);
		
		this.distance = distance;
		this.angle = angle;
		
	}
	
	//Constructor with tagNumber for naming the action
	public DriveAction(double distance, double angle, int tagNumber, ArrayList<String> dependencies){
		
		super("drive" + tagNumber, dependencies);
		
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