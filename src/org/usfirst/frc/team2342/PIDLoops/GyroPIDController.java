package org.usfirst.frc.team2342.PIDLoops;

import com.analog.adis16448.frc.ADIS16448_IMU;
//import edu.wpi.first.wpilibj.command.PIDSubsystem;

//import edu.wpi.first.wpilibj.Timer;

public class GyroPIDController {
	private ADIS16448_IMU gyro = new ADIS16448_IMU();
	private double p = 0.0d;
	private double i = 0.0d;
	private double d = 0.0d;
	private double curAngle = 0.0d;
	private double targetAngle = 0.0d;
	
	public GyroPIDController(double p, double tAngle) {
		this.p = p;
		this.targetAngle = tAngle;
	}
	
	public double calculateAE() {
		return this.targetAngle - getCurAngle();
	}
	
	public double calculate() {
		double val = calculateAE();
		return val;
	}
	
	public void setTargetAngle(double ta) {
		this.targetAngle = ta;
	}
	
	public double getTargetAngle() {
		return this.targetAngle;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getCurAngle() {
		this.curAngle = gyro.getAngle();
		return curAngle;
	}

	public void setCurAngle(double curAngle) {
		this.curAngle = curAngle;
	}
}
