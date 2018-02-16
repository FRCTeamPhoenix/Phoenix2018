package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CascadePosition extends Command {
	CascadeElevator cascade;
	private double position;
    public CascadePosition(CascadeElevator cascade, double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(cascade);
    	this.cascade = cascade;
    	this.position = position;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cascade.goToPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	cascade.goToPosition(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(cascade.talonCascade.getSelectedSensorPosition(0) - position) < 500;
    }

    // Called once after isFinished returns true
    protected void end() {
    	cascade.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
