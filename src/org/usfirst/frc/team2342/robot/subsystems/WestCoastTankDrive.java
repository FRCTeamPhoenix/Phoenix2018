package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.robot.TalonNWT;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class WestCoastTankDrive extends Subsystem {
    
    private WPI_TalonSRX leftA, rightA, leftB, rightB;
    private PCMHandler m_PCM;
    public GyroPIDController pidc = new GyroPIDController(0.002d);
    private boolean gyroControll = true; 
    
    public WestCoastTankDrive(PCMHandler PCM, WPI_TalonSRX leftFR, WPI_TalonSRX rightFR, WPI_TalonSRX leftBA, WPI_TalonSRX rightBA) {
        /*Json config = JsonHelper.getConfig();*/
        m_PCM = PCM;
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
        leftA.configPeakOutputForward(Constants.WESTCOAST_MAX_SPEED, 0);
        leftA.configPeakOutputReverse(-Constants.WESTCOAST_MAX_SPEED, 0);
        rightA.configNominalOutputForward(0, 0);
        rightA.configNominalOutputReverse(0, 0);
        rightA.configPeakOutputForward(Constants.WESTCOAST_MAX_SPEED, 0);
        rightA.configPeakOutputReverse(-Constants.WESTCOAST_MAX_SPEED, 0);
        
        
        leftB.follow(leftA);
        rightB.follow(rightA);
        
        // TODO Temporary! Once JsonHelper works, we should use that.
        PIDGains fakeGains = new PIDGains();
        fakeGains.p = 0.13333;
        fakeGains.i = 0;
        fakeGains.d = 0.05;
        fakeGains.ff = 0.0; // Note: only velocity mode do we need ff
        fakeGains.izone = 0;
        
        // TODO are these the right indices of the talons?
/*        PIDGains leftVelocityGains = //config.talons.get(0).velocityGains;
        PIDGains leftDistanceGains = //config.talons.get(0).distanceGains;
        PIDGains rightVelocityGains = //config.talons.get(1).velocityGains;
        PIDGains rightDistanceGains = //config.talons.get(1).distanceGains;
        
        
        WestCoastTankDrive.loadGains(leftA, Constants.TALON_VELOCITY_SLOT_IDX, leftVelocityGains);
        WestCoastTankDrive.loadGains(leftA, Constants.TALON_DISTANCE_SLOT_IDX, leftDistanceGains);
        WestCoastTankDrive.loadGains(rightA, Constants.TALON_VELOCITY_SLOT_IDX, rightVelocityGains);
        WestCoastTankDrive.loadGains(rightA, Constants.TALON_DISTANCE_SLOT_IDX, rightDistanceGains);
        WestCoastTankDrive.loadGains(leftB, Constants.TALON_VELOCITY_SLOT_IDX, leftVelocityGains);
        WestCoastTankDrive.loadGains(leftB, Constants.TALON_DISTANCE_SLOT_IDX, leftDistanceGains);
        WestCoastTankDrive.loadGains(rightB, Constants.TALON_VELOCITY_SLOT_IDX, rightVelocityGains);
        WestCoastTankDrive.loadGains(rightB, Constants.TALON_DISTANCE_SLOT_IDX, rightDistanceGains);*/
        
        zeroSensors();
        
    }
    
    public void setOpenLoop(double left, double right) {
        leftA.set(ControlMode.PercentOutput, left);
        rightA.set(ControlMode.PercentOutput, right);
        leftB.set(ControlMode.PercentOutput, left);
        rightB.set(ControlMode.PercentOutput, right);
    }
    
    public void setVelocity(double left, double right) {
        if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
            leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
        }

        if (this.gyroControll != true) {
        	leftA.set(ControlMode.Velocity, left);
        	rightA.set(ControlMode.Velocity, right);
        }
        else {
        	leftA.set(ControlMode.Velocity, left * (1 + pidc.getCorrection()));
        	rightA.set(ControlMode.Velocity, right * (1 - pidc.getCorrection()));
        }
    }
    
    public void setDistance(double left, double right) {
       if (!leftA.getControlMode().equals(ControlMode.Position)) {
           leftA.selectProfileSlot(Constants.TALON_DISTANCE_SLOT_IDX, 0);
       }
       leftA.set(ControlMode.Position, left * Constants.TALON_TICKS_PER_REV);
       rightA.set(ControlMode.Position, right * Constants.TALON_TICKS_PER_REV);
    }
    
    public void outputToSmartDashboard() {
    	TalonNWT.updateTalon(leftA);
    	TalonNWT.updateTalon(leftB);
    	TalonNWT.updateTalon(rightA);
    	TalonNWT.updateTalon(rightB);
    }

    public void stop() {
        setOpenLoop(0, 0);
    }

    public void zeroSensors() {
        WestCoastTankDrive.zeroEncoders(leftA);
        WestCoastTankDrive.zeroEncoders(rightA);
        WestCoastTankDrive.zeroEncoders(leftB);
        WestCoastTankDrive.zeroEncoders(rightB);
    }

    public void registerEnabledLoops(Looper enabledLooper) {
        // TODO Auto-generated method stub
    }
    
    public void setHighGear() {
    	 m_PCM.setLowGear(false);
        m_PCM.setHighGear(true);
        m_PCM.compressorRegulate();
    }
    
    public void setLowGear() {
        m_PCM.setHighGear(false);
        m_PCM.setLowGear(true);
        m_PCM.compressorRegulate();
    }
    
    public void setNoGear() {
    	m_PCM.setHighGear(false);
    	m_PCM.setLowGear(false);
    	m_PCM.compressorRegulate();
    }
    
    public void zeroSnesors() {
    	this.pidc.reset();
    }
    
    private static void zeroEncoders(WPI_TalonSRX talon) {
        talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
    }
    
    private static void loadGains(WPI_TalonSRX talon, int slotIdx, PIDGains gains) {
        talon.config_kP(slotIdx, gains.p, 0);
        talon.config_kI(slotIdx, gains.i, 0);
        talon.config_kD(slotIdx, gains.d, 0);
        talon.config_kF(slotIdx, gains.ff, 0);
        talon.config_IntegralZone(slotIdx, gains.izone, 0);
        TalonNWT.setPIDValues(slotIdx, talon);
    }

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
