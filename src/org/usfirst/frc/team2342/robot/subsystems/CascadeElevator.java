package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class CascadeElevator extends Subsystem{
	private SmartTalon talon;

	public CascadeElevator(SmartTalon talon) {
		this.talon = talon;
	}
	
	public void moveUp(double inches) {
		talon.goVoltage(inches);
	}
	
	public void moveDown(double inches) {
		talon.goVoltage(-inches);
	}
	
	public void climbUp(double inches) {
		talon.goVoltage(inches);
	}
	
	public void climbDown(double inches) {
		talon.goVoltage(-inches);
	}
	
	@Override
	public void outputToSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		// TODO Auto-generated method stub
		
	}
	
}
