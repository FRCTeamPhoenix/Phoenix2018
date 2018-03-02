package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;

/*
 * By: Joshua Calzadillas
 * 
 * Command: Turn Angle
 * 
 * Parameters: TurnAngle(speed, angle, driveController);
 * 
 * Example: TurnAngle(1350, -90, westCoastTankDrive);
 * 
 * Turn angle allows the user make the robot rotate to a specific angle with a gyro PID loop controller. 
 * Doing so makes life easier for autonomous modes and maneuvers.
 * It utilizes the Tank Drive to set a specific velocity for the wheels to turn. (I will have a max speed cap later)...
 * The robot will stop turning when the gyro finally reaches the destination/target angle. * 
 */

public class TurnAngle extends Command {
	WestCoastTankDrive m_westCoast;
	private double cangle = 0.0d;
	private double angle = 0.0d;
	private double vel = 0.0d;
	private double deadZone = 1.0d;

	// Constructor for the command
	public TurnAngle(double velocity, double angle, WestCoastTankDrive westCoast){
		requires(westCoast);
		m_westCoast = westCoast;
		m_westCoast.setGyroControl(true);
		m_westCoast.pidc.gyroReset();
		this.angle = angle;
		this.cangle = westCoast.pidc.getCurAngle();
		this.vel = -velocity;
	}
	
	// Initialize the setup for the target angle
	protected void initialize(){
		if(m_westCoast.debug)
			System.out.println("turning angle "+angle+" degrees");
		m_westCoast.turnSet(this.angle);
	}
	
	@Override
	// run loop for the command
	protected void execute(){
		//SmartDashboard.putString("DB/String 0", ""+ String.valueOf(m_westCoast.pidc.calculateAE()));
		m_westCoast.rotateAuto(this.vel);
		//System.out.println(m_westCoast.pidc.getP() + "   " + m_westCoast.pidc.getI() + "   " + m_westCoast.pidc.getD());
		this.cangle = m_westCoast.pidc.getCurAngle();
	}

	@Override
	// Check to see if the gyro is done
	protected boolean isFinished() {
		double dist = (Math.abs(this.angle) - Math.abs(this.cangle));
		if (dist <= this.deadZone) {
			return true;
		}
		else
			return false;
	}
	
	@Override
	// if an accident happens
	protected void interrupted() {
		if(m_westCoast.debug)
			System.out.println("angle interrupted");
		end();
	}
	
	// stop the robot from turning when done
	protected void end() {
		//if(m_westCoast.debug)
			System.out.println("finished turn angle");
		m_westCoast.setVelocity(0, 0);
		m_westCoast.setGyroControl(false);
		m_westCoast.pidc.reset();
	}
}