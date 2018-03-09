package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
	WestCoastTankDrive m_westCoast;
	private double m_distance;
	
	public DriveDistance(WestCoastTankDrive westCoast, double distance) {
		//only one system can use westCoast
		requires(westCoast);
		m_westCoast = westCoast;
		m_distance = distance;
	}
	
	protected void initialize() {
		//called on command adding to scheduler
		//if(m_westCoast.debug)
			System.out.println("started distance: "+m_distance+" ft");
		//sets distance when created
		m_westCoast.goDistance(m_distance);
	}
	
	protected void execute() {
		//adjusts velocity values and called on scheduler.getInstance().run()
		m_westCoast.distanceLoop();
		System.out.println("AUTO: DRIVE DISTANCE");
	}
	
	public boolean isFinished(){
		//should the command terminate
    	return m_westCoast.isDistanceFinished();
    }
	
	protected void end() {
		//called at end of command
		//if(m_westCoast.debug)
			System.out.println("finished distance");
		//stop moving
		m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		//called if command stopped
		//if(m_westCoast.debug)
			System.out.println("distance interrupted");
		end();
	}
	
}
