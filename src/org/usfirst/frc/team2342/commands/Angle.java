package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Angle extends Command {

	private TankDrive tankDrive;
	private double angle;
	private double currentAngle;
	private long start;
	private boolean direction;
	
    public Angle(TankDrive tankDrive, double angle, boolean direction) {
    	this.direction = direction;
    	
    	this.tankDrive = tankDrive;
    	this.angle = angle;
    		
    	requires(tankDrive);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	start = System.currentTimeMillis(); 
    	if(System.currentTimeMillis() - start == 0)
    		currentAngle = 0;
    	else
    		currentAngle = 850 / (System.currentTimeMillis() - start) * angle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(System.currentTimeMillis() - start == 0)
    		currentAngle = 0;
    	if (System.currentTimeMillis() - start > 850)
    		currentAngle = angle;
    	else
	    	currentAngle = 850 / (System.currentTimeMillis() - start) * angle;
    	int speed = 1;
    	if (direction)
    		speed *= -1;
	    	
	    tankDrive.setVelocity( speed * -Constants.WESTCOAST_HALF_SPEED * ( 1 - Constants.ARC_CONSTANT * (currentAngle - Gyro.angle())),  speed * Constants.WESTCOAST_HALF_SPEED);
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis() - start > 1000;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

