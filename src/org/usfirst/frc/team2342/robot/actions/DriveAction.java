package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveAction extends Action {
	
	private double distance = 0;
	private double angle = 0;
	private long startTime;
	private int time;
	private WestCoastTankDrive westCoast;
	
	//Tag associated with the action for dependency detection	
	//Constructor with a tag taken in for naming
	public DriveAction(WestCoastTankDrive wc, double distance, double angle, int time, String tag, String dep) {
		super(tag, dep);
		this.westCoast = wc;
		this.distance = distance;
		this.angle = angle;
		this.time = time;
	}
	
	public boolean isCompleted() {
		return (System.currentTimeMillis() - this.startTime >= (long) time);
	}
	
	public void start() {
		westCoast.zeroSensors();
		//westCoast.setDistance(this.distance, this.distance);
		this.startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		//westCoast.setDistance(0.0d, 0.0d);
	}
	
	public void run() {
		// DO NOTHING BUT KEEP RUNNING
	}
	
}