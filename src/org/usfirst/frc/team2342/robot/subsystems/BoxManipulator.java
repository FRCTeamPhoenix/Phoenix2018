package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class BoxManipulator extends Subsystem {
	private SmartTalon talon;
	
	public BoxManipulator(SmartTalon talon) {
		this.talon = talon;
	}
	
	public void openManipulator() {
		talon.goVoltage(0);
	}
	
	public void closeManipulat0or() {
		talon.goVoltage(0);
	}
	
	public void pullBox() {
		talon.goVoltage(0);
	}
	
	public void pushBox() {
		talon.goVoltage(0);
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
