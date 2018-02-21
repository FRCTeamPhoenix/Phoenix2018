package org.usfirst.frc.team2342.PIDLoops;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/*
 * 
 */

// must implement the velocity PID control into the Gyro
public class GyroPIDController implements PIDSource, PIDOutput {
	private ADIS16448_IMU gyro; 		// gyro instance
//	private double Kp = 0.0d;   		// P value of PID
//	private double Ki = 0.0d;   		// I value of PID
//	private double Kd = 0.0d;   		// D value of PID
	private double curAngle = 0.0d;     // Current angle
	private double targetAngle = 0.0d;  // Target angle
	private double correction = 0.0d;   // PID Correction
	private PIDController pc;           // PID Controller


	// CONSTRUCTOR
	public GyroPIDController(double p, double i, double d) {		
		// Gyro and PIDController setup
		if (gyro == null)
			gyro = new ADIS16448_IMU();
//		this.Kp = p;
//		this.Ki = i;
//		this.Kd = d;
		pc = new PIDController(p, i, d, 0.0d, this, this);
		pc.disable();
		reset();
	}

	// reset the options for PID Controller
	public void reset() {
		pc.reset();
		pc.setOutputRange(-1, 1);
	}
	
	// update the angle and the target thing
	public void updateAngle(double angle) {
		this.targetAngle = angle;
		this.reset();
		pc.setSetpoint(this.targetAngle);
		pc.enable();
	}

	// get the p value
	public double getP() {
		return pc.getP();
	}

	// set the p value
	public void setP(double p) {
		pc.setP(p);
	}

	// get the i value
	public double getI() {
		return pc.getI();
	}

	// set the i value
	public void setI(double i) {
		pc.setI(i);
	}

	// get the d value
	public double getD() {
		return pc.getD();
	}

	// set the d value
	public void setD(double d) {
		pc.setD(d);;
	}

	// calculate angle error
	public double calculateAE() {
		return targetAngle - getCurAngle();
	}

	// set the target angle
	public void setTargetAngle(double ta) {
		this.targetAngle = ta;
	}

	// return the gyro for test purposes
	public ADIS16448_IMU getGyro() {
		return this.gyro;
	}

	// return target angle
	public double getTargetAngle() {
		return targetAngle;
	}

	/* 
	 * Update the current angle
	 * Angle measures:
	 * right is negative
	 * left is positive
	 */
	private void updateCurAngle() {
		//this.curAngle = gyro.getAngleX() % 360;
		//this.curAngle = (this.curAngle < 0.0d) ? this.curAngle + 360.0d : this.curAngle;
		this.curAngle = gyro.getAngleX();
	}

	// get the current angle
	public double getCurAngle() {
		updateCurAngle();
		return curAngle;
	}

	// get the correction from PID LOOP
	public double getCorrection() {
		return this.correction;
	}

	// get the PID Controller
	public PIDController getPC() {
		return this.pc;
	}
	
	public void gyroReset() {
		this.gyro.reset();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		this.gyro.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		//return PIDSourceType.kDisplacement;
		return this.gyro.getPIDSourceType();
	}

	// IMPORTANT
	// PID Controller get the error value
	@Override
	public double pidGet() {
		return getCurAngle();
	}

	// IMPORTANT
	// PID Controller set the correction value
	@Override
	public void pidWrite(double output) {
		this.correction = output;
	}
}
