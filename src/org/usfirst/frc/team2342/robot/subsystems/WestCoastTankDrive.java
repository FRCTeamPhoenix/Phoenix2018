package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.json.Json;
import org.usfirst.frc.team2342.json.JsonHelper;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.loops.Looper;
import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class WestCoastTankDrive extends Subsystem {
    
    private static WestCoastTankDrive mInstance = new WestCoastTankDrive();
    private WPI_TalonSRX leftMaster, rightMaster, leftSlave, rightSlave;
    private PCMHandler PCM;
    
    //Json config = JsonHelper.getConfig();
    
    public static WestCoastTankDrive getInstance() {
        return mInstance;
    }
    
    private WestCoastTankDrive() {
        leftMaster = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
        leftSlave = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
        rightMaster = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
        rightSlave = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
        
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);
        
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_DISTANCE_SLOT_IDX, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_DISTANCE_SLOT_IDX, 0);
        
        leftMaster.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
        rightMaster.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
        
        // If the talons run indefinitely, the sensors may be reading in the wrong direction,
        // in which case the sensor phase should be inverted.
        leftMaster.setSensorPhase(false);
        rightMaster.setSensorPhase(false);
        
        // Invert the appropriate talons
        leftMaster.setInverted(false);
        rightMaster.setInverted(true);
        
        // Constrain the speed of all talons to [-max, max]
        leftMaster.configNominalOutputForward(0, 0);
        leftMaster.configNominalOutputReverse(0, 0);
        leftMaster.configPeakOutputForward(Constants.WESTCOAST_MAX_SPEED, 0);
        leftMaster.configPeakOutputReverse(-Constants.WESTCOAST_MAX_SPEED, 0);
        rightMaster.configNominalOutputForward(0, 0);
        rightMaster.configNominalOutputReverse(0, 0);
        rightMaster.configPeakOutputForward(Constants.WESTCOAST_MAX_SPEED, 0);
        rightMaster.configPeakOutputReverse(-Constants.WESTCOAST_MAX_SPEED, 0);
        
        // TODO Temporary! Once JsonHelper works, we should use that.
        PIDGains fakeGains = new PIDGains();
        fakeGains.p = 0.13333;
        fakeGains.i = 0;
        fakeGains.d = 0.05;
        fakeGains.ff = 0.0; // Note: only velocity mode do we need ff
        fakeGains.izone = 0;
        
        // TODO are these the right indices of the talons?
        PIDGains leftVelocityGains = fakeGains;//config.talons.get(0).velocityGains;
        PIDGains leftDistanceGains = fakeGains;//config.talons.get(0).distanceGains;
        PIDGains rightVelocityGains = fakeGains;//config.talons.get(1).velocityGains;
        PIDGains rightDistanceGains = fakeGains;//config.talons.get(1).distanceGains;
        
        
        WestCoastTankDrive.loadGains(leftMaster, Constants.TALON_VELOCITY_SLOT_IDX, leftVelocityGains);
        WestCoastTankDrive.loadGains(leftMaster, Constants.TALON_DISTANCE_SLOT_IDX, leftDistanceGains);
        WestCoastTankDrive.loadGains(rightMaster, Constants.TALON_VELOCITY_SLOT_IDX, rightVelocityGains);
        WestCoastTankDrive.loadGains(rightMaster, Constants.TALON_DISTANCE_SLOT_IDX, rightDistanceGains);
        
        PCM = new PCMHandler(Constants.PCM_PORT);
        
        zeroSensors();
        
    }
    
    public int getLeftTicks() {
    	return this.leftMaster.getActiveTrajectoryPosition();    	
    }
    
    public int getRightTicks() {
    	return this.rightMaster.getActiveTrajectoryPosition();    	
    }
    
    public void setOpenLoop(double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }
    
    public void setVelocity(double left, double right) {
        if (!leftMaster.getControlMode().equals(ControlMode.Velocity)) {
            leftMaster.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
        }
        leftMaster.set(ControlMode.Velocity, left * Constants.TALON_RPM_TO_VELOCITY * Constants.WESTCOAST_VELOCITY_RPM_SCALE);
        rightMaster.set(ControlMode.Velocity, right * Constants.TALON_RPM_TO_VELOCITY * Constants.WESTCOAST_VELOCITY_RPM_SCALE);
    }
    
    public void setDistance(double left, double right) {
       if (!leftMaster.getControlMode().equals(ControlMode.Position)) {
           leftMaster.selectProfileSlot(Constants.TALON_DISTANCE_SLOT_IDX, 0);
       }
       leftMaster.set(ControlMode.Position, -left * Constants.TALON_CONVERSION_TO_FEET);
       rightMaster.set(ControlMode.Position, -right * Constants.TALON_CONVERSION_TO_FEET);
    }
    
    @Override
    public void outputToSmartDashboard() {
        // TODO Auto-generated method stub
    }

    @Override
    public void stop() {
        setOpenLoop(0, 0);
    }

    @Override
    public void zeroSensors() {
        WestCoastTankDrive.zeroEncoders(leftMaster);
        WestCoastTankDrive.zeroEncoders(rightMaster);
        WestCoastTankDrive.zeroEncoders(leftSlave);
        WestCoastTankDrive.zeroEncoders(rightSlave);
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
    
    private static void zeroEncoders(WPI_TalonSRX talon) {
        talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
        talon.setSelectedSensorPosition(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
    }
    
    private static void loadGains(WPI_TalonSRX talon, int slotIdx, PIDGains gains) {
        talon.config_kP(slotIdx, gains.p, 0);
        talon.config_kI(slotIdx, gains.i, 0);
        talon.config_kD(slotIdx, gains.d, 0);
        talon.config_kF(slotIdx, gains.ff, 0);
        talon.config_IntegralZone(slotIdx, gains.izone, 0);
    }

}