package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Arc extends Command {

	private TankDrive tankDrive;
	private double angle;
	private double currentAngle;
	private double distance;
    public Arc(TankDrive tankDrive,double distance, double angle) {
    	this.tankDrive = tankDrive;
    	this.angle = angle;
    	this.distance = distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
    	requires(tankDrive);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tankDrive.zeroSensors();
    	currentAngle = distance / tankDrive.leftA.getSelectedSensorPosition(0) * angle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = distance / tankDrive.leftA.getSelectedSensorPosition(0) * angle;
    	
    	tankDrive.setVelocity(-Constants.WESTCOAST_HALF_SPEED * ( 1 - .1 * (currentAngle)), -Constants.WESTCOAST_HALF_SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0) + tankDrive.rightA.getSelectedSensorPosition(0)) / 2 + distance) < 300;
    }

    // Called once after isFinished returns true
    protected void end() {
    	tankDrive.setVelocity(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
