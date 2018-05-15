package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveGamepad extends Command {

	double leftVelocity = 0.0;
	double rightVelocity = 0.0;
	Joystick m_gamepad;
	TankDrive m_westCoast;
	CascadeElevator m_cascade;

	boolean cascadeSlowEnabled;
	boolean oneJoystick;
	
	public DriveGamepad(Joystick gamepad, TankDrive tankDrive, CascadeElevator cascade) {
		requires(tankDrive);
		//limit at max val
		m_gamepad = gamepad;
		m_cascade = cascade;
		cascadeSlowEnabled = true;
		
		m_westCoast = tankDrive;
		if(Math.abs(gamepad.getRawAxis(1)) > Constants.JOYSTICK_DEADZONE)
			leftVelocity = gamepad.getRawAxis(1);
		else
			leftVelocity = 0.0;

		if(Math.abs(gamepad.getRawAxis(5)) > Constants.JOYSTICK_DEADZONE)
			rightVelocity = gamepad.getRawAxis(5);
		else
			rightVelocity = 0.0;
	}

	protected void initialize() {
		m_westCoast.setPercentage(-leftVelocity, -rightVelocity);
	}

	@Override
	protected void execute(){
		double leftVelocity = 0.0;
		double rightVelocity = 0.0;
		double axis1 = m_gamepad.getRawAxis(0);
		double axis2 = m_gamepad.getRawAxis(1);

		if(m_gamepad.getRawButtonPressed(20))
			cascadeSlowEnabled = (!cascadeSlowEnabled);
		
		//get multiplier where 1.0 = all the way down and 0.2 is all the way up
		double axisMultiplier = 1.0;
		if(cascadeSlowEnabled)
			axisMultiplier -= 0.8 * Math.abs((float)m_cascade.talonCascade.getSelectedSensorPosition(0)/ (float)Constants.CASCADE_UPPER_SCALE);
		
		//do left right multiplier calculationss
		double x = axis1;
	
		double y = axis2;
		if(Math.abs(y) >= 0.1){
			
		}else{
			y = 0.0;
			x *= -1.0;
		}
		
		
		
		double L = 0;
		double R = 0;
		
		
		if(y>=0){L=y+x; R=y-x;}

		else {L= y-x; R=y+x;}

		double max = 0;
		max=Math.abs(L); if(max<Math.abs(R))max=Math.abs(R);

		if(max>1){L/=max; R/=max;}
		
		//		System.out.println(axis1);
		if(Math.abs(axis1) > Constants.JOYSTICK_DEADZONE || Math.abs(axis2) > Constants.JOYSTICK_DEADZONE){
			leftVelocity = L * axisMultiplier; // velocity maybe
			rightVelocity = R  * axisMultiplier; // velocity maybe
		}else{
			leftVelocity = 0;
			rightVelocity = 0;
		}
		
		m_westCoast.setPercentage(-leftVelocity, -rightVelocity);
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
