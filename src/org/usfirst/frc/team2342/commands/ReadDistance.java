package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.Lidar;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ReadDistance extends Command {

	Lidar lidar;
	
    public ReadDistance(Lidar lidar) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.lidar = lidar;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println(lidar.readDistance());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
