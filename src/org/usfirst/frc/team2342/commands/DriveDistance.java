package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
	WestCoastTankDrive m_westCoast;
	private double m_distance;
	
	public DriveDistance(WestCoastTankDrive westCoast, double distance) {
		requires(westCoast);
		m_westCoast = westCoast;
		m_distance = distance;
	}
	
	protected void initialize() {
		m_westCoast.goDistance(m_distance);
	}
	
	protected void execute() {
		m_westCoast.distanceLoop();
	}
	
	public boolean isFinished(){
    	return m_westCoast.isFinished();
    }
	
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
