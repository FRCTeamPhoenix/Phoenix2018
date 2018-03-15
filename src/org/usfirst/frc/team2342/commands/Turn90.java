package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn90 extends Command {

	WestCoastTankDrive westCoast;
	String direction;
	long startTime;
    public Turn90(WestCoastTankDrive westCoast,String direction) {
    	requires(westCoast);
    	this.westCoast = westCoast;
    	if (!direction.equalsIgnoreCase("left") && !direction.equalsIgnoreCase("right"))
    		throw new IllegalArgumentException("invalid direction");
    	this.direction = direction;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	int speed = 500;
    	if (direction.equalsIgnoreCase("left"))
    		speed *= -1;
    	startTime = System.currentTimeMillis();
    	westCoast.setVelocity(-speed, speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > SmartDashboard.getNumber("DB/Slider 2", 2000);
    }

    // Called once after isFinished returns true
    protected void end() {
    	westCoast.setVelocity(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
