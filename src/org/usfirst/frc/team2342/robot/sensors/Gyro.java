package org.usfirst.frc.team2342.robot.sensors;

import com.analog.adis16448.frc.ADIS16448_IMU;

public class Gyro {
	static ADIS16448_IMU imu;
	
	private double goalAngle;
	
	public Gyro(){
		goalAngle = 0.0;
	}
	
	public void init(){
		imu = new ADIS16448_IMU();
	}
	
	/*
	 * sets current position to 0 degrees*/
	public void reset(){
		imu.reset();
	}
	
	public void setGoal(){
		goalAngle = imu.getAngleX();
	}
	
	public void setGoal(double offset){
		goalAngle = imu.getAngleX() + offset;
	}
	
	public double angleFromGoal(){
		return goalAngle - imu.getAngleX();
	}
	
	public double angle(){
		return imu.getAngleX();
	}
}
