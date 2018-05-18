package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TurnAngle extends Command {

	private TankDrive drive;
	private double angle;
	private ADIS16448_IMU imu;
	
	public TurnAngle(TankDrive drive, ADIS16448_IMU imu, double angle) {
		this.drive = drive;
		this.angle = angle;
		this.imu = imu;
	}
	
	@Override
	protected void initialize() {
		imu.reset();
	}
	
	protected void execute() {
		System.out.println(angle + " .... " + imu.getAngleX());
		if(imu.getAngleX() > angle) {
			drive.setVelocity(-Constants.WESTCOAST_HALF_SPEED, Constants.WESTCOAST_HALF_SPEED);
		} else {
			drive.setVelocity(Constants.WESTCOAST_HALF_SPEED, -Constants.WESTCOAST_HALF_SPEED);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(imu.getAngleX() - angle) < 15;
	}
	
	protected void end() {
		drive.setVelocity(0, 0);
	}

}
