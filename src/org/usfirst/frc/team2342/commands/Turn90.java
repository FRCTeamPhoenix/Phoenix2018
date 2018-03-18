package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn90 extends Command {

	TankDrive westCoast;	
	boolean direction;
	long startTime;
    public Turn90(TankDrive westCoast,boolean direction) {
    	this.westCoast = westCoast;
    	
    	this.direction = direction;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    	westCoast.setLowGear();
    	startTime = System.currentTimeMillis();
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = 0.4;
    	if (direction)
    		speed *= -1;
    	westCoast.setPercentage(speed, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > SmartDashboard.getNumber("DB/Slider 2", 2000);
    }

    // Called once after isFinished returns true
    protected void end() {
    	westCoast.setPercentage(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
