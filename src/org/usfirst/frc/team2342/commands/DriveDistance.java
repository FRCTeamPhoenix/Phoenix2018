package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
	WestCoastTankDrive m_westCoast;
	
	public DriveDistance(WestCoastTankDrive westCoast, double distance) {
		requires(westCoast);
		setTimeout(time);
		m_westCoast = westCoast;
	}
	
	protected void initialize() {
		m_westCoast.setVelocity();
	}
	
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
