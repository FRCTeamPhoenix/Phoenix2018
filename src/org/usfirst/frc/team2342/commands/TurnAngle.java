package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {
	WestCoastTankDrive m_westCoast;
	private double cangle = 0.0d;
	private double angle = 0.0d;
	private double vel = 0.0d;
	private double deadZone = 1.0d;

	public TurnAngle(double velocity, double angle, WestCoastTankDrive westCoast){
		requires(westCoast);
		this.angle = angle;
		m_westCoast = westCoast;
		this.cangle = westCoast.pidc.getCurAngle();
		this.vel = velocity;
	}

	protected void initialize(){
		m_westCoast.pidc.gyroReset();
		m_westCoast.turnSet(this.angle);
	}
	@Override
	protected void execute(){
		//SmartDashboard.putString("DB/String 0", ""+ String.valueOf(m_westCoast.pidc.calculateAE()));
		m_westCoast.rotateAuto(this.vel);
		this.cangle = m_westCoast.pidc.getCurAngle();
	}

	@Override
	// Check to see if the gyro is done
	protected boolean isFinished() {
		if ((Math.abs(this.angle) - Math.abs(cangle) >= this.deadZone))
			return true;
		return false;
	}
	@Override
	protected void interrupted() {
		end();
	}
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
}