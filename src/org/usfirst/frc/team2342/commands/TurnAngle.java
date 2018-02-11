package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnAngle extends Command {
	WestCoastTankDrive m_westCoast;
	Gyro m_gyro;
	double angleDeadzone = 6.0;
	double acceptableDeadzone = 1.0;
	
	public TurnAngle(Gyro gyro, int time, double goalAngle, WestCoastTankDrive westCoast){
		requires(westCoast);
		m_gyro = gyro;
		m_gyro.setGoal(goalAngle);
		m_westCoast = westCoast;
	}
	
	protected void initialize(){
		if(m_gyro.angleFromGoal() > angleDeadzone){
			m_westCoast.setVelocity(Constants.WESTCOAST_MAX_SPEED, -Constants.WESTCOAST_MAX_SPEED);
		}else if (m_gyro.angleFromGoal() < -angleDeadzone){
			m_westCoast.setVelocity(-Constants.WESTCOAST_MAX_SPEED, Constants.WESTCOAST_MAX_SPEED);
		}else if(m_gyro.angleFromGoal() > acceptableDeadzone){
			m_westCoast.setVelocity(Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5, -Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5);
		}else if (m_gyro.angleFromGoal() < -acceptableDeadzone){
			m_westCoast.setVelocity(-Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5, Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5);
		}
	}
	@Override
	protected void execute(){
		SmartDashboard.putString("DB/String 0", ""+m_gyro.angleFromGoal());
		if(m_gyro.angleFromGoal() > angleDeadzone){
			m_westCoast.setVelocity(Constants.WESTCOAST_MAX_SPEED, -Constants.WESTCOAST_MAX_SPEED);
		}else if (m_gyro.angleFromGoal() < -angleDeadzone){
			m_westCoast.setVelocity(-Constants.WESTCOAST_MAX_SPEED, Constants.WESTCOAST_MAX_SPEED);
		}else if(m_gyro.angleFromGoal() > acceptableDeadzone){
			m_westCoast.setVelocity(Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5, -Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5);
		}else if (m_gyro.angleFromGoal() < -acceptableDeadzone){
			m_westCoast.setVelocity(-Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5, Constants.TALON_SPEED_RPS*(m_gyro.angleFromGoal()/angleDeadzone)*0.5);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(m_gyro.angleFromGoal()) < acceptableDeadzone;
	}
	@Override
	protected void interrupted() {
		end();
	}
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
}