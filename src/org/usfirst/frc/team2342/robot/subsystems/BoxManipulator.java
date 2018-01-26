package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.PCMHandler;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class BoxManipulator extends Subsystem {
	private TalonSRX talon1;
	private TalonSRX talon2;
	private PCMHandler PCM1;
	private PCMHandler PCM2;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	public BoxManipulator(TalonSRX talon1, TalonSRX talon2) {
		this.talon1 = talon1;
		this.talon2 = talon2;
		this.talon2.follow(talon1);
	}
	
	public void openManipulator() {
		talon1.goVoltage(0);
	}
	
	public void closeManipulat0or() {
		talon1.goVoltage(0);
	}
	
	public void pullBox() {
		talon1.goVoltage(0);
	}
	
	public void pushBox() {
		talon1.goVoltage(0);
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
