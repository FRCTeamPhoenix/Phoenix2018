package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.PIDLoops.DistancePIDController;
import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.PIDLoops.SingleTalonDistancePIDController;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.robot.TalonNWT;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WestCoastTankDrive extends Subsystem {
    
    private WPI_TalonSRX leftA, rightA, leftB, rightB;
    private PCMHandler m_PCM;
    public DistancePIDController dpidc;
    public GyroPIDController pidc = new GyroPIDController(0.002d);
    public SingleTalonDistancePIDController leftpidc;
    public SingleTalonDistancePIDController rightpidc;
    private boolean gyroControl = false;
    
    public WestCoastTankDrive(PCMHandler PCM, WPI_TalonSRX leftFR, WPI_TalonSRX rightFR, WPI_TalonSRX leftBA, WPI_TalonSRX rightBA) {
        /*Json config = JsonHelpe.getConfig();*/
        m_PCM = PCM;
    	leftA = leftFR;
    	rightA = rightFR;
    	leftB = leftBA;
    	rightB = rightBA;
    	dpidc = new DistancePIDController(0.0008d, 0.0d, 0.0d, 0.0d, leftA, rightA);
    	leftpidc = new SingleTalonDistancePIDController(0.00095d, 0.0d, 0.0d, 0.0d, leftA);
    	rightpidc = new SingleTalonDistancePIDController(0.00095d, 0.0d, 0.0d, 0.0d, rightA);
        
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

        if (this.gyroControl != true) {
        	leftA.set(ControlMode.Velocity, left);
        	rightA.set(ControlMode.Velocity, right);
        }
        else {
        	leftA.set(ControlMode.Velocity, left * (1 + pidc.getCorrection()));
        	rightA.set(ControlMode.Velocity, right * (1 - pidc.getCorrection()));
        }
    }
    
    public void goDistance(double distanceInFeet){
    	double distance = distanceInFeet/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
		dpidc.setGoal(distance);
    	if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
	        leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
	    }
    	 
    	leftA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
     	rightA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
    }
    
    public void distanceLoop(){
    	if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
	        leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
	    }
    	
    	leftA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
     	rightA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
    }
    
    public boolean isDistanceFinished(){
    	return (dpidc.pidGet() > dpidc.getGoal()*1.05 && dpidc.pidGet() < dpidc.getGoal()*0.95);
    }
    
    private double innerSpeed = 0.0d;
    private double outerSpeed = 0.0d;
    
    public void goArc(double radius, double degrees, double outerMultiplyer, double innerMultiplyer, boolean isLeftInner){
    	double circumfrence = 2 * radius * Math.PI;
    	double innerCircumfrence = 2 * (radius - (11.0/12.0)) * Math.PI;
    	double outerCircumfrence = 2 * (radius + (11.0/12.0)) * Math.PI;
    	
    	double degreeMultiplyer = degrees / 360;
    	innerSpeed = Constants.WESTCOAST_MAX_SPEED * (innerCircumfrence/outerCircumfrence) * innerMultiplyer;
    	outerSpeed = Constants.WESTCOAST_MAX_SPEED * (outerCircumfrence/innerCircumfrence) * outerMultiplyer;
    	double distance = circumfrence/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
		dpidc.setGoal(distance * degreeMultiplyer);
    	if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
	        leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
	    }
    	 
    	//leftA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
     	//rightA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
    }
    
    public void arcLoop(boolean isLeftInner){
    	if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
	        leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
	    }
    	if(isLeftInner){
    		leftA.set(ControlMode.Velocity, innerSpeed * dpidc.getCorrection());
    		rightA.set(ControlMode.Velocity, outerSpeed * dpidc.getCorrection());
    	}else{
    		rightA.set(ControlMode.Velocity, innerSpeed * dpidc.getCorrection());
    		leftA.set(ControlMode.Velocity, outerSpeed * dpidc.getCorrection());
    	}
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
