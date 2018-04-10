package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.sensors.RIOduino;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lidar extends Subsystem {
	
	RIOduino rio;
	
	public Lidar () {
		rio = new RIOduino();
	}
	
	public void start() {
		rio.start();
	}
	
	public void stop() {
		rio.stop();
	}
	
	public double readDistance() {
		return rio.getDistanceIn();
	}

	@Override
	protected void initDefaultCommand() {
		
	}

}
