package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.PCMHandler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class BoxManipulator extends Subsystem {
	private TalonSRX talon1;
	private TalonSRX talon2;
	private PCMHandler PCM;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	public BoxManipulator(TalonSRX talon1, TalonSRX talon2, PCMHandler PCM) {
		this.talon1 = talon1;
		this.talon2 = talon2;
		this.talon2.follow(talon1);
		this.PCM = PCM;
	}
	
	public void closeManipulator(double current) {
		
	}
	
	public void openManipulator(double current) {
		/*PCM.drivePiston();*/
	}
	
	public void pneumatic() {
		
	}
	
	public void distance() {
		
	}
	
	@Override
	public void outputToSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		talon1.set(ControlMode.Current, 0.0);
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
