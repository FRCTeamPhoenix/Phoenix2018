package org.usfirst.frc.team2342.robot.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import edu.wpi.first.wpilibj.*;

public class GyroController implements PIDSource, PIDOutput {
	static ADIS16448_IMU imu;
	//public WestCoastTankDrive drivetrain;
	
	private double zeroAngle;
	public double correction; // TODO: use getters/setters
	
	private PIDController controller;
	
	public GyroController() {
		if (imu == null)
			imu = new ADIS16448_IMU();
		
		controller = new PIDController(0.01, 0.0, 0.0, this, this);
		controller.enable();
		
		reset();
	}
	
	public void reset() {
		zeroAngle = imu.getAngleX();
		controller.reset();
		controller.enable();
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
		return imu.getAngleX() - zeroAngle;
	}

	@Override
	public void pidWrite(double output) {
		correction = output;
	}
	
}
