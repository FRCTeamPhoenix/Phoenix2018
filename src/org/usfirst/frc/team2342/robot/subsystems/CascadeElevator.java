package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.sensors.LowerLimit;
import org.usfirst.frc.team2342.robot.sensors.UpperLimit;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CascadeElevator extends Subsystem {
	public WPI_TalonSRX talonCascade;

	public static final int BASE = 0;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	public static final int TOP = 3;

	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;

	private DigitalInput lowerLimit;
	private DigitalInput upperLimit;

	public CascadeElevator(WPI_TalonSRX talonCascade) {
		this.talonCascade = talonCascade;

		talonCascade.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talonCascade.setSensorPhase(SensorPhase);
		talonCascade.setInverted(InvertMotor);
		talonCascade.configNominalOutputForward(0, PidTimeOutMs);
		talonCascade.configNominalOutputReverse(0, PidTimeOutMs);
		talonCascade.configPeakOutputForward(1, PidTimeOutMs);
		talonCascade.configPeakOutputReverse(-1, PidTimeOutMs);
		talonCascade.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);

		talonCascade.config_kF(PidLoopIndex, 0.0, PidTimeOutMs);
		talonCascade.config_kP(PidLoopIndex, 0.1, PidTimeOutMs);
		talonCascade.config_kI(PidLoopIndex, 0.0, PidTimeOutMs);
		talonCascade.config_kD(PidLoopIndex, 0.0, PidTimeOutMs);

		talonCascade.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);

		lowerLimit = new DigitalInput(Constants.LOWER_LIMIT_SWITCH);

		upperLimit = new DigitalInput(Constants.UPPER_LIMIT_SWITCH);
	}

	public CascadeElevator() {

	}

	public void goToPosition(double position) {
		double speed = -0.4;

		if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < position) {
			speed *= -1;
		}
		
		setVelocity(speed);
	}
	public void holdPosition() {
		talonCascade.set(ControlMode.PercentOutput, 0.1);
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

		if (!lowerLimit.get()) {
			System.out.println("LOWER LIMIT REACHED");
			speed = -Math.abs(speed);
			talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION, PidLoopIndex, PidTimeOutMs);
		} else if (!upperLimit.get()) {
			System.out.println("UPPER LIMIT REACHED");
			speed = Math.abs(speed);
			talonCascade.setSelectedSensorPosition(Constants.UPPER_SENSOR_POSITION, PidLoopIndex, PidTimeOutMs);
		} else if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < Constants.UPPER_SENSOR_POSITION) {
			System.out.println("ABOVE");
			speed = Math.abs(speed);
		} else if (talonCascade.getSelectedSensorPosition(PidLoopIndex) > Constants.LOWER_SENSOR_POSITION) {
			System.out.println("BELOW");
			speed = -Math.abs(speed);
		}
		System.out.println(speed);
		talonCascade.set(ControlMode.PercentOutput, speed);

	}

	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talonCascade.getMotorOutputPercent() * 100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talonCascade.getSelectedSensorPosition(0));
	}

	public void stop() {
		talonCascade.set(ControlMode.Current, 0.0);
	}

	public void zeroSensors() {
		talonCascade.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}