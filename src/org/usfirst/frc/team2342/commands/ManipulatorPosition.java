package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManipulatorPosition extends Command {

	BoxManipulator boxManipulator;
	int position;
    public ManipulatorPosition(BoxManipulator boxManipulator, int degrees) {
        requires(boxManipulator);
        this.boxManipulator = boxManipulator;
        this.position = (int) (degrees * Constants.MANIP_DEGTOTICKS);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        System.out.println("constructor");
    }

    // Called once when the command executes
    protected void initialize() {
    	System.out.println("Init");
    	boxManipulator.goToPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Execute");
    	if (Math.abs(boxManipulator.talonTip.getSelectedSensorPosition(0) - position) < 100)
    		boxManipulator.holdPosition();
    	else 
    		boxManipulator.goToPosition(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("We're out");
    	boxManipulator.setTipToZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
