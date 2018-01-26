package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class DriveAction extends Action{
	
	private double distance = 0;
	private double angle = 0;
	
	//Tag associated with the action for dependency detection
	private String tag;
	
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
	public void start(SmartTalon talon1, SmartTalon talon2, SmartTalon talon3, SmartTalon talon4){
		
		talon1.goVoltage(-this.distance);
    	talon2.goVoltage(this.distance);
    	talon3.goVoltage(-this.distance);
    	talon4.goVoltage(this.distance);
		
		System.out.println("started");
		
		super.start(talon1, talon2, talon3, talon4);
		
	}
	
	//Stop driving
	public void stop(SmartTalon talon1, SmartTalon talon2, SmartTalon talon3, SmartTalon talon4){
		
		talon1.goVoltage(0);
    	talon2.goVoltage(0);
    	talon3.goVoltage(0);
    	talon4.goVoltage(0);
		
		System.out.println("stoped");
		
		super.stop(talon1, talon2, talon3, talon4);
	}
}