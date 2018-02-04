package org.usfirst.frc.team2342.PIDLoops;

//import org.usfirst.frc.team2342.robot.talons.PIDGains;

import com.analog.adis16448.frc.ADIS16448_IMU;

//import edu.wpi.first.wpilibj.PIDController;
//import edu.wpi.first.wpilibj.PIDOutput;
//import edu.wpi.first.wpilibj.PIDSource;

//import edu.wpi.first.wpilibj.command.PIDSubsystem;

//import edu.wpi.first.wpilibj.Timer;


// must implement the velocity pid controll into the gyro
public class GyroPIDController {
	private ADIS16448_IMU gyro = new ADIS16448_IMU();
	private double Kp = 0.0d;
	private double curAngle = 0.0d;
	private double targetAngle = 0.0d;
//	private PIDGains pg;
//	private PIDController pc;
	
	public GyroPIDController(double p, double tAngle) {
		// pid setup
//		this.pg.setP(p);
		
//		this.pg.setI(0.0d);
//		this.pg.setD(0.0d);
//		this.pg.setFf(0.0d);
//		this.pg.setIzone(0);
		
		// angle setup & declaration of proportional values
		this.Kp = p;
		this.targetAngle = tAngle;
//		pc = new PIDController(this.Kp, 0.0d, 0.0d, 0.0d, gyro, null);
//		pc.enable();
	}
	
	public double calculateAE() {
		updateCurAngle();
		return targetAngle - getCurAngle();
	}
	
	public double calculate() {
		double val = calculateAE();
		// take the percentage of the value and multiply it by the proportion constant
		if (targetAngle == 0.0d) {
			// translation over the unit circle will be added soon.
		}
		else {
			val = Math.abs(val) / targetAngle;
			val *= Kp;
		}
		return val;
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
	
	public void updateCurAngle() {
		this.curAngle = gyro.getAngle();
	}

	public double getCurAngle() {
		return curAngle;
	}
}
