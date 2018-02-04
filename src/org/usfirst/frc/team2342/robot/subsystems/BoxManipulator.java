package org.usfirst.frc.team2342.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BoxManipulator extends Subsystem {
	private TalonSRX talon1;
	private TalonSRX talon2;
	private Solenoid solenoid1;
	private Solenoid solenoid2;
	
	public static final int PULL = 0;
	public static final int PUSH = 1;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	public BoxManipulator(TalonSRX talon1, TalonSRX talon2, Solenoid solenoid1, Solenoid solenoid2) {
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
		//Need equivalent for solenoids
		
		this.solenoid1 = solenoid1;
		this.solenoid2 = solenoid2;
	}
	
	public BoxManipulator() {
		this.talon1 = new TalonSRX(0);
		this.talon2 = new TalonSRX(1);
		this.solenoid1 = new Solenoid(0,0);
		this.solenoid2 = new Solenoid(0,1);
	}
	
	public void closeManipulator() {
		this.solenoid1.set(true);
		this.solenoid2.set(true);
	}
	
	public void openManipulator() {
		this.solenoid1.set(false);
		this.solenoid2.set(false);
	}
	
	public void goToPosition(double position) {
		talon1.set(ControlMode.Position, position);
		talon2.follow(talon1);
	}
	
	public void pullBox() {
		goToPosition(PULL);
	}
	
	public void pushBox() {
		goToPosition(PUSH);
	}
	
	/*public void distance() {
		
	}*/
	
	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talon1.getMotorOutputPercent()*100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talon1.getSelectedSensorPosition(0));
		//Need equivalent for solenoids
	}

	public void stop() {
		talon1.set(ControlMode.Current, 0.0);
		this.solenoid1.set(false);
		this.solenoid2.set(false);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}