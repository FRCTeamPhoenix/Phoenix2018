package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveGamepad extends Command {

	double leftVelocity = 0.0;
	double rightVelocity = 0.0;
	Joystick m_gamepad;
	WestCoastTankDrive m_westCoast;
	
	
	public DriveGamepad(Joystick gamepad, WestCoastTankDrive westCoast) {
		requires(westCoast);
		//limit at max val
		m_gamepad = gamepad;
		
		m_westCoast = westCoast;
		if(Math.abs(gamepad.getRawAxis(1)) > Constants.JOYSTICK_DEADZONE)
				leftVelocity = gamepad.getRawAxis(1) * Constants.WESTCOAST_MAX_SPEED;
		else
			leftVelocity = 0.0;
		
		if(Math.abs(gamepad.getRawAxis(3)) > Constants.JOYSTICK_DEADZONE)
			rightVelocity = gamepad.getRawAxis(3) * Constants.WESTCOAST_MAX_SPEED;
		else
			rightVelocity = 0.0;
	}
	
	protected void initialize() {
		m_westCoast.setVelocity(leftVelocity, rightVelocity);
	}
	
	@Override
	protected void execute(){
		if(Math.abs(m_gamepad.getRawAxis(1)) > Constants.JOYSTICK_DEADZONE)
			leftVelocity = m_gamepad.getRawAxis(1) * Constants.WESTCOAST_MAX_SPEED ;
		else
			leftVelocity = 0.0;
		
		if(Math.abs(m_gamepad.getRawAxis(3)) > Constants.JOYSTICK_DEADZONE)
			rightVelocity = m_gamepad.getRawAxis(3) * Constants.WESTCOAST_MAX_SPEED;
		else
			rightVelocity = 0.0;
		m_westCoast.setVelocity(leftVelocity, rightVelocity);
	}
	
	protected boolean isFinished(){
		return false;
	}
	
	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		end();
	}

}
