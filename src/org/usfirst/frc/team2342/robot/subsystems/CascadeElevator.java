package org.usfirst.frc.team2342.robot.subsystems;

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

	public DigitalInput lowerLimit;
	public DigitalInput upperLimit;
	
	public boolean runningPreset = false;

	public CascadeElevator(WPI_TalonSRX talonCascade) {
		this.talonCascade = talonCascade;
		this.talonCascade.setInverted(false);
		talonCascade.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talonCascade.setSensorPhase(SensorPhase);
		talonCascade.setInverted(InvertMotor);
		talonCascade.configNominalOutputForward(0, PidTimeOutMs);
		talonCascade.configNominalOutputReverse(0, PidTimeOutMs);
		talonCascade.configPeakOutputForward(1, PidTimeOutMs);
		talonCascade.configPeakOutputReverse(-1, PidTimeOutMs);
		talonCascade.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);

		talonCascade.config_kF(PidLoopIndex, 0.5, PidTimeOutMs);
		talonCascade.config_kP(PidLoopIndex, 0.02, PidTimeOutMs);
		talonCascade.config_kI(PidLoopIndex, 0.001, PidTimeOutMs);
		talonCascade.config_kD(PidLoopIndex, 50, PidTimeOutMs);

		talonCascade.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);

		lowerLimit = new DigitalInput(Constants.LOWER_LIMIT_SWITCH);
		upperLimit = new DigitalInput(Constants.UPPER_LIMIT_SWITCH);
	}

	public void goToPosition(double position) {
		double speed = -800;

		if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < position * Constants.INCHES_TO_TICKS_CASCADE) {
			speed *= -1;
		}

		setVelocity(speed);
	}

	public void holdPosition() {
		talonCascade.set(ControlMode.Velocity, 0);
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

		try {
			if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < Constants.UPPER_SENSOR_POSITION) {
				//System.out.println("ABOVE");
				speed = Math.max(speed, 0);	
			} 
			
			if (talonCascade.getSelectedSensorPosition(PidLoopIndex) > Constants.LOWER_SENSOR_POSITION) {
				//System.out.println("BELOW");
				speed = Math.min(speed, 0);
			}
		
			if (lowerLimit.get()) { // switches are NC so true if tripped
				//System.out.println("LOWER LIMIT REACHED");
				speed = Math.min(speed, 0);
				 talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION,
				 PidLoopIndex, PidTimeOutMs);
			}

			if (upperLimit.get()) {
				//System.out.println("UPPER LIMIT REACHED");
				speed = Math.max(speed, 0);
				// talonCascade.setSelectedSensorPosition(Constants.UPPER_SENSOR_POSITION,
				// PidLoopIndex, PidTimeOutMs);
			}
			
			/*System.out.println("preset: " + runningPreset + 
					" position: " + talonCascade.getSelectedSensorPosition(PidLoopIndex) + 
					" speed: " + speed + " llim: " + lowerLimit.get);*/

		} catch (Exception e) {

		}
		talonCascade.set(ControlMode.Velocity, speed);

	}

	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talonCascade.getMotorOutputPercent() * 100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talonCascade.getSelectedSensorPosition(0));
	}

	public void stop() {
		talonCascade.set(ControlMode.Current, 0.0);
	}

	public void zeroSensors() {
		talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION, PidLoopIndex, PidTimeOutMs);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}