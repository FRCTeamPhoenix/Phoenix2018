package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class DriveAction extends Action {
	
	private double speed;
	private int time;
	
	private String name;
	
	private boolean running;
	
	private long startTime;
	
	SmartTalon t1, t2, t3, t4;
	
	public DriveAction(double speed, int time, ArrayList<String> dependencies, SmartTalon t1, SmartTalon t2, SmartTalon t3, SmartTalon t4, String tag){
		
		super(tag, dependencies);
		
		this.speed = speed;
		this.time = time;
		
		this.t1 = t1;
		this.t2 = t2;
		this.t3 = t3;
		this.t4 = t4;
	}
	
	public boolean isCompleted() {
		return System.currentTimeMillis() - startTime > (long) time;
	}

	@Override
	public void onStart() {
		//TODO: Drive train: move certain distance at the given angle				
		t1.goVoltage(-speed);
		t2.goVoltage(speed);
		t3.goVoltage(-speed);
		t4.goVoltage(speed);
		this.startTime = System.currentTimeMillis();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		t1.goVoltage(0);
		t2.goVoltage(0);
		t3.goVoltage(0);
		t4.goVoltage(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}