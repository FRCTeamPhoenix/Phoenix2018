package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CascadePosition extends Command {
	CascadeElevator cascade;
	private double position;
	Joystick gamepad;
	
    public CascadePosition(CascadeElevator cascade, double position, Joystick gamepad) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cascade);
    	this.cascade = cascade;
    	this.position = position;
    	this.gamepad = gamepad;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cascade.setRunningPreset(true);
    	cascade.goToPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Math.abs(cascade.talonCascade.getSelectedSensorPosition(0) - position * Constants.INCHES_TO_TICKS_CASCADE) < 500)
    		cascade.holdPosition();
    	else 
    		cascade.goToPosition(position);
    	System.out.println("AUTO: CASCADE POSITION");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(gamepad.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS)) > Constants.CASCADE_DEADZONE) ||
        		Math.abs(cascade.talonCascade.getSelectedSensorPosition(0) - position * Constants.INCHES_TO_TICKS_CASCADE) < 500;
    }

    // Called once after isFinished returns true
    protected void end() {
    	cascade.setRunningPreset(false);
    	cascade.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
