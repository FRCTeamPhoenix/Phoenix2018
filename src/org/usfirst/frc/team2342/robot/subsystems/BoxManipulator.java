package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BoxManipulator extends Subsystem {
	public WPI_TalonSRX talonIntakeRight;
	public WPI_TalonSRX talonIntakeLeft;
	public WPI_TalonSRX talonTip;
	private Solenoid solenoid1;
	
	public static final int PULL = 0;
	public static final int PUSH = 1;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	
	public BoxManipulator(WPI_TalonSRX talonIntakeRight, WPI_TalonSRX talonIntakeLeft, WPI_TalonSRX talonTip, Solenoid solenoid1) {
		this.talonIntakeRight = talonIntakeRight;
		this.talonIntakeLeft = talonIntakeLeft;
		this.talonTip = talonTip;
		
		talonIntakeRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talonIntakeRight.setSensorPhase(SensorPhase);
		talonIntakeRight.setInverted(InvertMotor);
		talonIntakeRight.configNominalOutputForward(0, PidTimeOutMs);
		talonIntakeRight.configNominalOutputReverse(0, PidTimeOutMs);
		talonIntakeRight.configPeakOutputForward(1, PidTimeOutMs);
		talonIntakeRight.configPeakOutputReverse(-1, PidTimeOutMs);
		talonIntakeRight.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);
		
		talonIntakeRight.config_kF(PidLoopIndex, 0.0, PidTimeOutMs);
		talonIntakeRight.config_kP(PidLoopIndex, 0.1, PidTimeOutMs);
		talonIntakeRight.config_kI(PidLoopIndex, 0.0, PidTimeOutMs);
		talonIntakeRight.config_kD(PidLoopIndex, 0.0, PidTimeOutMs);
		
		talonIntakeRight.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		talonIntakeLeft.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		this.talonIntakeLeft.follow(this.talonIntakeRight);
		//Need equivalent for solenoids
		
	}
	
	public BoxManipulator() {
		
	}
	
	public void initialize() {
		talonTip.set(ControlMode.PercentOutput, 0.4);
	}
	
	public void setTipToZero() {
		talonTip.set(ControlMode.PercentOutput, 0.0);
	}
	
	public void closeManipulator() {
		this.solenoid1.set(true);
	}
	
	public void openManipulator() {
		this.solenoid1.set(false);
	}
	
	public void goToPosition(double position) {
		double speed = -0.3;

		if (talonTip.getSelectedSensorPosition(PidLoopIndex) < position * Constants.INCHES_TO_TICKS_CASCADE) {
			speed *= -1;
		}

		setTiltVelocity(speed);
	}
	
	public void holdPosition() {
		talonTip.set(ControlMode.Velocity, 0);
	}
	
	public void pullBox() {
		goToPosition(PULL);
	}
	
	public void pushBox() {
		goToPosition(PUSH);
	}
	
	public void setTiltVelocity(double speed) {

		if (talonTip.getSelectedSensorPosition(PidLoopIndex) < 0) {
			System.out.println("ABOVE");
			speed = Math.max(speed, 0);	
		} 
			
		if (talonTip.getSensorCollection().isFwdLimitSwitchClosed()) {
			System.out.println("UPPER LIMIT REACHED");
				talonTip.setSelectedSensorPosition(0,
				PidLoopIndex, PidTimeOutMs);
		}
		talonTip.set(ControlMode.Velocity, -speed);
	}
	
	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talonIntakeRight.getMotorOutputPercent()*100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talonIntakeRight.getSelectedSensorPosition(0));
		//Need equivalent for solenoids
	}
	
	public boolean atUpperLimit() {
		return talonTip.getSensorCollection().isRevLimitSwitchClosed();
	}
	
	public void end(int t) {
		talonTip.set(ControlMode.PercentOutput, 0.4);
	}

	public void stop() {
		talonIntakeRight.set(ControlMode.Current, 0.0);
		this.solenoid1.set(false);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}