package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;

import edu.wpi.first.wpilibj.command.Command;

public class CascadeHold extends Command {
	
	CascadeElevator cascade;
	
	public CascadeHold(CascadeElevator cascade) {
		this.cascade = cascade;
	}
	
	protected void initialize() {
		cascade.setVelocity(0);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
