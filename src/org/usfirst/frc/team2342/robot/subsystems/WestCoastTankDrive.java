package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.json.Json;
import org.usfirst.frc.team2342.json.JsonHelper;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.robot.TalonNWT;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class WestCoastTankDrive extends Subsystem {
    
    private TalonSRX leftA, rightA, leftB, rightB;
    private PCMHandler PCM;
    public GyroPIDController pidc;
    private boolean gyroControlled;
    
    public WestCoastTankDrive(PCMHandler PCM, TalonSRX leftFR, TalonSRX rightFR, TalonSRX leftBA, TalonSRX rightBA) {
        /*Json config = JsonHelper.getConfig();*/
        
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
        this.pidc = new GyroPIDController(0.02d);
        gyroControlled = true;
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
        
        leftA.set(ControlMode.Velocity, left * Constants.TALON_RPM_TO_VELOCITY * Constants.WESTCOAST_VELOCITY_RPM_SCALE);
        rightA.set(ControlMode.Velocity, right * Constants.TALON_RPM_TO_VELOCITY * Constants.WESTCOAST_VELOCITY_RPM_SCALE);
    }
    
    public void setVelocityGyro(double forward) {
        if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
            leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
        }
        
        //TODO: These constants are outdated: look on the subsystems branch
        forward *= Constants.TALON_RPM_TO_VELOCITY * Constants.WESTCOAST_VELOCITY_RPM_SCALE;
        
        leftA.set(ControlMode.Velocity, forward + pidc.getCorrection());
        rightA.set(ControlMode.Velocity, forward - pidc.getCorrection());
    }
    
    
    public void setDistance(double left, double right) {
       if (!leftA.getControlMode().equals(ControlMode.Position)) {
           leftA.selectProfileSlot(Constants.TALON_DISTANCE_SLOT_IDX, 0);
       }
       leftA.set(ControlMode.Position, left * Constants.TALON_TICKS_PER_REV);
       rightA.set(ControlMode.Position, right * Constants.TALON_TICKS_PER_REV);
    }
    
    @Override
    public void outputToSmartDashboard() {
    	TalonNWT.updateTalon(leftA);
    	TalonNWT.updateTalon(leftB);
    	TalonNWT.updateTalon(rightA);
    	TalonNWT.updateTalon(rightB);
    }

    @Override
    public void stop() {
        setOpenLoop(0, 0);
    }

    @Override
    public void zeroSensors() {
        WestCoastTankDrive.zeroEncoders(leftA);
        WestCoastTankDrive.zeroEncoders(rightA);
        WestCoastTankDrive.zeroEncoders(leftB);
        WestCoastTankDrive.zeroEncoders(rightB);
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        // TODO Auto-generated method stub
    }
    
    public void setHighGear() {
        PCM.setHighGear(true);
        PCM.setLowGear(false);
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
    
    private static void zeroEncoders(TalonSRX talon) {
        talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
    }
    
    private static void loadGains(TalonSRX talon, int slotIdx, PIDGains gains) {
        talon.config_kP(slotIdx, gains.p, 0);
        talon.config_kI(slotIdx, gains.i, 0);
        talon.config_kD(slotIdx, gains.d, 0);
        talon.config_kF(slotIdx, gains.ff, 0);
        talon.config_IntegralZone(slotIdx, gains.izone, 0);
        TalonNWT.setPIDValues(slotIdx, talon);
    }

}