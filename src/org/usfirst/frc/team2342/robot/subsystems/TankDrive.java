package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TankDrive {

	private WPI_TalonSRX leftA, rightA, leftB, rightB;
	private PCMHandler PCM;
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	
	public TankDrive(PCMHandler PCM, WPI_TalonSRX leftFR, WPI_TalonSRX rightFR, WPI_TalonSRX leftBA, WPI_TalonSRX rightBA) {
		
		this.PCM = PCM;
		leftA = leftFR;
		rightA = rightFR;
		leftB = leftBA;
		rightB = rightBA;
		leftA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);
		rightA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);

		leftA.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
		rightA.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
	
		// If the talons run indefinitely, the sensors may be reading in the wrong direction,
		// in which case the sensor phase should be inverted.
		leftA.setSensorPhase(false);
		rightA.setSensorPhase(false);

		// Invert the appropriate talons
		leftA.setInverted(false);
		rightA.setInverted(true);
		leftB.setInverted(false);
		rightB.setInverted(true);

		// Constrain the speed of all talons to [-max, max]
		leftA.configNominalOutputForward(0, 0);
		leftA.configNominalOutputReverse(0, 0);
		leftA.configPeakOutputForward(1.0, 0);
		leftA.configPeakOutputReverse(-1.0, 0);
		rightA.configNominalOutputForward(0, 0);
		rightA.configNominalOutputReverse(0, 0);
		rightA.configPeakOutputForward(1.0, 0);
		rightA.configPeakOutputReverse(-1.0, 0);
	
		leftA.config_kF(PidLoopIndex, 0.5, PidTimeOutMs);
		leftA.config_kP(PidLoopIndex, 0.02, PidTimeOutMs);
		leftA.config_kI(PidLoopIndex, 0.001, PidTimeOutMs);
		leftA.config_kD(PidLoopIndex, 50, PidTimeOutMs);
		leftB.follow(leftA);
		rightB.follow(rightA);
		zeroSensors(); 
	}
	static void zeroEncoders(WPI_TalonSRX talon) {
		talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
	}
	
	public void zeroSensors() {
		leftA.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
		leftB.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
		rightA.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
		rightB.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
	}
	
	public void setPercentage(double left,double right) {
		leftA.set(ControlMode.PercentOutput, left);
		rightA.set(ControlMode.PercentOutput,right);
	}
	
	public void setHighGear() {
		PCM.setLowGear(false);
		PCM.setHighGear(true);
		PCM.compressorRegulate();
	}

	public void setLowGear() {
		PCM.setHighGear(false);
		PCM.setLowGear(true);
		PCM.compressorRegulate();
	}

	public void setNoGear() {
		PCM.setHighGear(false);
		PCM.setLowGear(false);
		PCM.compressorRegulate();
	}
}
