package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForward extends Command {
	WestCoastTankDrive m_westCoast;
	double velocity;
	
	public DriveForward(int time, WestCoastTankDrive westCoast, double speedRPS) {
		requires(westCoast);
		setTimeout(time);
		m_westCoast = westCoast;
		//limit at max val
		velocity = Math.min(speedRPS, Constants.WESTCOAST_MAX_SPEED);
	}
	
	protected void initialize() {
		m_westCoast.setVelocity(velocity, velocity);
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
