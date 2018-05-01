package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TiltPosition extends Command {
	BoxManipulator manip;
	private double position;
	
    public TiltPosition(BoxManipulator manip, double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(manip);
    	this.manip = manip;
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	manip.goToPositionTilt(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if (Math.abs(cascade.talonCascade.getSelectedSensorPosition(0) - position * Constants.INCHES_TO_TICKS_CASCADE) < 500)
    		cascade.holdPosition();
    	else */
    		manip.goToPositionTilt(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(manip.talonTip.getSelectedSensorPosition(0) + position) < 200;
    }

    // Called once after isFinished returns true
    protected void end() {
    	manip.stopTilt();
    	manip.lastPosition = -position;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
