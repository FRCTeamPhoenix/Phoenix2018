package org.usfirst.frc.team2342.PIDLoops;

//import org.usfirst.frc.team2342.robot.talons.PIDGains;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// must implement the velocity PID control into the Gyro
public class GyroPIDController implements PIDOutput, PIDSource {
	private ADIS16448_IMU gyro;
	private double Kp = 0.0d;
	private double curAngle = 0.0d;
	private double targetAngle = 0.0d;
	private double correction = 0.0d;
	private PIDController pc;

	public GyroPIDController(double p) {		
		// angle setup & declaration of proportional values
		if (gyro == null)
			gyro = new ADIS16448_IMU();
		this.Kp = p;
		pc = new PIDController(this.Kp, 0.0d, 0.0d, 0.0d, gyro, this);
		reset();
	}
	//TODO make public for later
	private void reset() {
		updateCurAngle();
		pc.reset();
		pc.setContinuous(true);
		pc.setOutputRange(-1, 1);
		pc.setSetpoint(this.curAngle);
		this.targetAngle = this.curAngle;
		pc.enable();
	}
	
	// calculate angle error
	public double calculateAE() {
		return targetAngle - getCurAngle();
	}
	
	public void setTargetAngle(double ta) {
		this.targetAngle = ta;
	}
	
	public double getTargetAngle() {
		return targetAngle;
	}

	public double getP() {
		return Kp;
	}

	public void setP(double p) {
		this.Kp = p;
	}
	
	private void updateCurAngle() {
		this.curAngle = gyro.getAngleX();
	}

	public double getCurAngle() {
		updateCurAngle();
		return curAngle;
	}
	
	public double getCorrection() {
		return this.correction;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		return getCurAngle();
	}

	@Override
	public void pidWrite(double output) {
//		System.out.println("OUTPUT: " + String.valueOf(output));
		this.correction = output;
	}
}
