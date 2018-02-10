package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.sensors.LowerLimit;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CascadeElevator extends Subsystem {
	private TalonSRX talon1;
	
	public static final int BASE = 0;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	public static final int TOP = 3;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	private LowerLimit lowerLimit;

	public CascadeElevator(TalonSRX talon1) {
		this.talon1 = talon1;
		
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
		
		lowerLimit = new LowerLimit(Constants.LOWER_LIMIT_SWITCH);
	}
	
	public CascadeElevator() {
		this.talon1 = new TalonSRX(1);
	}
	
	public void goToPosition(double position) {
		talon1.set(ControlMode.Position, position);
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
	
	public void setVelocity(double speed) {
		talon1.set(ControlMode.PercentOutput, speed);
		System.out.println(speed);
		if (lowerLimit.detectsObject()) {
			talon1.set(ControlMode.PercentOutput, Math.sqrt(speed));
		}
		else {
			talon1.set(ControlMode.Current, 0.0);
		}
	}
	
	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talon1.getMotorOutputPercent()*100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talon1.getSelectedSensorPosition(0));
	}

	public void stop() {
		talon1.set(ControlMode.Current, 0.0);
	}

	public void zeroSensors() {
		talon1.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
	}

	public void registerEnabledLoops(Looper enabledLooper) {
		// TODO Auto-generated method stub
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
}