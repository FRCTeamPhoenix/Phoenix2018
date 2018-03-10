package org.usfirst.frc.team2342.util;

import org.usfirst.frc.team2342.json.Talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/*
 * Generic smart talon class for use with devices
 */
public class SmartTalon extends WPI_TalonSRX {

	//basic talon information
	private int deviceNumber;
	private boolean inverted;
	private ControlMode mode ;
	private PIDGains pidGains;
	
	private double currentModeValue;
	
	//WPI_TalonSRX compared to TalonSRX for network table interface
	SmartTalon talon;
	
	public SmartTalon(Talon jsonTalon){
		this(jsonTalon.deviceNumber, jsonTalon.inverted);
		this.pidGains.setP(jsonTalon.velocityGains.p);
		this.pidGains.setI(jsonTalon.velocityGains.i);
		this.pidGains.setD(jsonTalon.velocityGains.d);
		this.pidGains.setRr(jsonTalon.velocityGains.rr);
		this.pidGains.setFf(jsonTalon.velocityGains.ff);
		this.pidGains.setIzone(jsonTalon.velocityGains.izone);
	}
	
	public SmartTalon(int deviceNumber){
		this(deviceNumber, false);
	}
	
	public SmartTalon(int deviceNumber,boolean inverted){
		this(deviceNumber, inverted, ControlMode.PercentOutput);
	}
	
	public SmartTalon(int deviceNumber,boolean inverted,ControlMode mode){
		this(deviceNumber, inverted, mode ,FeedbackDevice.CTRE_MagEncoder_Relative);
	}
	
	public SmartTalon(int deviceNumber, boolean inverted, ControlMode mode, FeedbackDevice feedback){
		super(deviceNumber);
		//set variables
		this.deviceNumber = deviceNumber;
		this.inverted = inverted;
		this.mode = mode;
		
		//create talon
		
		setFeedBackDevice(feedback);
		configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
		
		//set pid gains to default
		this.pidGains = new PIDGains(0.0,0.0,0.0,0.0,0.0,0);
		this.currentModeValue = 0.0;
		
		zeroEncoder();
	}
	
	//change device type
	public void setFeedBackDevice(FeedbackDevice feedback){
		configSelectedFeedbackSensor(feedback, 0, 0);
	}
	
	//set encoder position to 0
	public void zeroEncoder(){
		setSelectedSensorPosition(0, 0, 0);
	}
	
	//load in gains to selected talon
	public void loadGains(PIDGains talonPID){
		//load in 0 by default
		loadGains(talonPID, 0);
	}
	
	//load in gains to selected talon and idx
	public void loadGains(PIDGains talonPID, int slotIdx){
		this.pidGains = talonPID;
		config_kP(slotIdx, talonPID.getP(), 0);
		config_kI(slotIdx, talonPID.getI(), 0);
		config_kD(slotIdx, talonPID.getD(), 0);
		config_kF(slotIdx, talonPID.getFf(), 0);
		config_IntegralZone(slotIdx, talonPID.getIzone(), 0);
		configOpenloopRamp(talonPID.getRr(), 0);
	}
	
	//set the mode and its value
	public void setMode(ControlMode mode, double value){
		this.mode = mode;
		this.currentModeValue = value;
		set(mode, value);
	}
	
	//called with a pid controller in order to control speed over time i.e. distance
	public void ControlModeLoop(double controllerValue){
		//cap the controller value to 1.0 in case user doesn't understand proper usage
		if(controllerValue > 1.0)
			set(this.mode, this.currentModeValue * 1.0);
		else if(controllerValue < -1.0)
			set(this.mode, this.currentModeValue * -1.0);
		else
			set(this.mode, this.currentModeValue * controllerValue);
	}
	
	//output data regarding sensors to the network table
	public void WriteToNetworkTables(String talonTableName){
		String talonTable = talonTableName + "/"+getDeviceID();
		
		//set general values
		NetworkTableInterface.setValue(talonTable, "Inverted", getInverted());
		NetworkTableInterface.setValue(talonTable, "Percent Output", getMotorOutputPercent());
		NetworkTableInterface.setValue(talonTable, "Output Voltage", getMotorOutputVoltage());
		NetworkTableInterface.setValue(talonTable, "Bus Voltage", getBusVoltage());
		NetworkTableInterface.setValue(talonTable, "Output Current", getOutputCurrent());
		NetworkTableInterface.setValue(talonTable, "Position", getSelectedSensorPosition(0));
		NetworkTableInterface.setValue(talonTable, "Velocity", getSelectedSensorVelocity(0));
		NetworkTableInterface.setValue(talonTable, "Closed Loop Error", getClosedLoopError(0));
		NetworkTableInterface.setValue(talonTable, "Error Derivative", getErrorDerivative(0));
		NetworkTableInterface.setValue(talonTable, "Integral Acummulator", getIntegralAccumulator(0));	
	}
	
	//only needs to be called after changing pid
	private void updateNWTPid(String talonTableName){
		String talonTable = talonTableName + "/"+getDeviceID();
		
		//set pid values
		NetworkTableInterface.setValue(talonTable, "P", pidGains.getP());
		NetworkTableInterface.setValue(talonTable, "I", pidGains.getI());
		NetworkTableInterface.setValue(talonTable, "D", pidGains.getD());
		NetworkTableInterface.setValue(talonTable, "FF", pidGains.getFf());
		NetworkTableInterface.setValue(talonTable, "RR", pidGains.getRr());
		NetworkTableInterface.setValue(talonTable, "IZone", pidGains.getIzone());
	}
	
	//getters
	
}