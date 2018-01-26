package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CascadeElevator extends Subsystem{
	private TalonSRX talon1;
	private TalonSRX talon2;
	
	public static final int BASE = 0;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	public static final int TOP = 3;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;

	public CascadeElevator(TalonSRX talon1, TalonSRX talon2) {
		this.talon1 = talon1;
		this.talon2 = talon2;
		
		talon1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talon1.setSensorPhase(SensorPhase);
		talon1.setInverted(InvertMotor);
		talon1.configNominalOutputForward(0, PidTimeOutMs);
		talon1.configNominalOutputReverse(0, PidTimeOutMs);
		talon1.configPeakOutputForward(1, PidTimeOutMs);
		talon1.configPeakOutputReverse(-1, PidTimeOutMs);
		talon1.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);
		
		talon1.config_kF(PidLoopIndex, 0.0, PidTimeOutMs);
		talon1.config_kP(PidLoopIndex, 0.1, PidTimeOutMs);
		talon1.config_kI(PidLoopIndex, 0.0, PidTimeOutMs);
		talon1.config_kD(PidLoopIndex, 0.0, PidTimeOutMs);
		
		talon1.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		talon2.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		this.talon2.follow(this.talon1);
	}
	
	public CascadeElevator() {
		this.talon1 = new TalonSRX(0);
		this.talon2 = new TalonSRX(1);
	}
	public void goToPosition(double position) {
		talon1.set(ControlMode.Position, position);
		talon2.follow(talon1);
	}
	
	public void goToBase() {
		goToPosition(BASE);
	}
	
	public void goToSwitch() {
		goToPosition(SWITCH);
	}
	
	public void goToScale() {
		goToPosition(SCALE);
	}
	
	public void goToTop() {
		goToPosition(TOP);
	}
	
	@Override
	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talon1.getMotorOutputPercent()*100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talon1.getSelectedSensorPosition(0));
	}

	@Override
	public void stop() {
		talon1.set(ControlMode.Current, 0.0);
	}

	@Override
	public void zeroSensors() {
		talon1.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		talon2.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
	}

	@Override
	public void registerEnabledLoops(Looper enabledLooper) {
		// TODO Auto-generated method stub
		
	}
	
}
