package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command {
	WestCoastTankDrive m_westCoast;
	
	public DriveForward(int time, WestCoastTankDrive westCoast) {
		requires(westCoast);
		setTimeout(time);
		m_westCoast = westCoast;
	}
	
	protected void initialize() {
		m_westCoast.setVelocity(4096.0/10.0, 4096.0/10.0);
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
