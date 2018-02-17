package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class InitBoxManipulator extends Command {

	BoxManipulator boxManipulator;
    public InitBoxManipulator(BoxManipulator boxManipulator) {
        super();
        requires(boxManipulator);
        this.boxManipulator = boxManipulator;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	boxManipulator.initialize();
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
    	boxManipulator.setTipToZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
