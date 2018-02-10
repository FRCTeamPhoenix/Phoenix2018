package org.usfirst.frc.team2342.PIDLoops;

//import org.usfirst.frc.team2342.robot.talons.PIDGains;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// must implement the velocity PID control into the Gyro
public class GyroPIDController implements PIDSource, PIDOutput {
	private ADIS16448_IMU gyro;
	private double Kp = 0.0d;
	private double curAngle = 0.0d;
	private double targetAngle = 0.0d;
	private double correction = 0.0d;
	private PIDController pc;

	public GyroPIDController(double p) {		
		// Gyro and PIDController setup
		if (gyro == null)
			gyro = new ADIS16448_IMU();
		this.Kp = p;
		pc = new PIDController(this.Kp, 0.0d, 0.0d, 0.0d, this, this);
		pc.enable();
		reset();
	}
	//TODO make public for later
	public void reset() {
		//pc.setInputRange(-360, 360);
		//pc.setContinuous();
		pc.setP(this.Kp);
		pc.setOutputRange(-1, 1);
		pc.setAbsoluteTolerance(0.005);
		if (this.targetAngle == 0.0d) {
//			gyro.reset();
			pc.setSetpoint(this.targetAngle);
		}
		else {
			updateCurAngle();
			pc.setSetpoint(this.curAngle);
			this.targetAngle = this.curAngle;
		}		
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
		//this.curAngle = gyro.getAngleX() % 360;
		//this.curAngle = (this.curAngle < 0.0d) ? this.curAngle + 360.0d : this.curAngle;
		this.curAngle = gyro.getAngleX();
	}

	public double getCurAngle() {
		updateCurAngle();
		return curAngle;
	}
	
	public double getCorrection() {
		return this.correction;
	}
	
	public PIDController getPC() {
		return this.pc;
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

	@Override
	public double pidGet() {
		return getCurAngle();
	}

	@Override
	public void pidWrite(double output) {
		this.correction = output;
	}
}
