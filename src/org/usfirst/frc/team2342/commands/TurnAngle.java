package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {
	WestCoastTankDrive m_westCoast;
	double angle = 0.0d;
	double vel = 0.0d;
	double deadZone = 5.0d;
	
	public TurnAngle(double velocity, double angle, WestCoastTankDrive westCoast){
		requires(westCoast);
		this.angle = angle;
		m_westCoast = westCoast;
	}
	
	protected void initialize(){
		m_westCoast.pidc.gyroReset();
		m_westCoast.turnSet(this.angle);
	}
	@Override
	protected void execute(){
		//SmartDashboard.putString("DB/String 0", ""+ String.valueOf(m_westCoast.pidc.calculateAE()));
		m_westCoast.rotateAuto(this.vel);
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(m_westCoast.pidc.calculateAE()) < deadZone;
	}
	@Override
	protected void interrupted() {
		end();
	}
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
}