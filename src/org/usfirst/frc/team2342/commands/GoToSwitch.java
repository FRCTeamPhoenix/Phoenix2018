package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;

import edu.wpi.first.wpilibj.command.Command;

public class GoToSwitch extends Command {
	CascadeElevator m_cascade;
	
	public GoToSwitch (CascadeElevator cascade) {
		requires(cascade);
		m_cascade = cascade;
	}
	
	protected void initialize() {
		m_cascade.goToSwitch();
	}
	
	protected boolean isFinished() {
		return false;//CHANGE LATER
	}
	
	protected void end() {
		m_cascade.stop();
	}
	
	protected void interrupted() {
		end();
	}
	
}