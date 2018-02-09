package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDifferential extends Command {
	WestCoastTankDrive m_westCoast;
	double velocityLeft;
	double velocityRight;
	
	public DriveDifferential(int time, WestCoastTankDrive westCoast, double velocityLeft, double velocityRight) {
		requires(westCoast);
		setTimeout(time);
		m_westCoast = westCoast;
		//limit at max val
		this.velocityLeft = velocityLeft;
		this.velocityRight = velocityRight;
	}
	
	protected void initialize() {
		if (Math.abs(velocityLeft * Constants.TALON_SPEED_RPS) > Constants.WESTCOAST_MAX_SPEED) {
			System.out.println("Velocity Left is greater than maximum.");
			return ;
		}
		if (Math.abs(velocityRight * Constants.TALON_SPEED_RPS) > Constants.WESTCOAST_MAX_SPEED) {
			System.out.println("Velocity Right is greater than maximum.");
			return ;
		}
		m_westCoast.setVelocity(Constants.TALON_SPEED_RPS * velocityLeft, Constants.TALON_SPEED_RPS * velocityRight);
	}
	
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	@Override
	protected void interrupted() {
		end();
	}
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
}
