package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import edu.wpi.first.wpilibj.command.Command;

public class ManipPosition extends Command {
	BoxManipulator boxManip;
	double position;
	
	public ManipPosition(BoxManipulator boxManip, double position) {
		this.boxManip = boxManip;
		this.position = position;
	}
	
	protected void initialize() {
		boxManip.goToPosition(position);
	}
	
	protected void execute() {

	}

	protected boolean isFinished() {
		if(Math.abs(boxManip.talonTip.getSelectedSensorPosition(0) - position) < 100)
			return true;
			
		if (boxManip.talonTip.getSelectedSensorPosition(0) < -2000) {
			return true;
		}
		return false;
	}
	
	protected void end() {
		
	}
	
    protected void interrupted() {
    	end();
    }
}
