package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.NetworkTableInterface;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonNWT {
	private static long initial = 0;
	
	//compaction for bandwidth errors
	private static void setMaskOnLong(int position, boolean value){
		long mask = (long)Math.pow(2, position);
		if(!value){
			mask = (~mask);
			initial = initial & mask;
		}else{
			initial = mask | initial;
		}
	}
	
	private static boolean getMaskOnLong(int position){
		long mask = (long)Math.pow(2, position);
		return (initial & mask) != 0;
	}
	
	public static boolean isUpdatePID() {
		int id = 100;
		String boolTable = Constants.TALON_TABLE_LOCATION + "/" + id;
		return NetworkTableInterface.getBoolean(boolTable, "UpdatePID");
	}
	
	public static void populateGyroPID(GyroPIDController gyro) {
		int id = 100;
		String pidTable = Constants.TALON_TABLE_LOCATION + "/" + id + "/pid-0";
//		String angleTable = Constants.TALON_TABLE_LOCATION + "/" + id + "/Angle";
//		String errorTable = Constants.TALON_TABLE_LOCATION + "/" + id + "/Error";
		gyro.setP(NetworkTableInterface.getDouble(pidTable, "/P"));
		gyro.setI(NetworkTableInterface.getDouble(pidTable, "/I"));
		gyro.setD(NetworkTableInterface.getDouble(pidTable, "/D"));
	}
	
	public static void updateGyroPID(GyroPIDController gyro) {
		int id = 100;
		String talonTable = Constants.TALON_TABLE_LOCATION + "/" + id;
		NetworkTableInterface.setValue(talonTable, "P", gyro.getP());
		NetworkTableInterface.setValue(talonTable, "I", gyro.getI());
		NetworkTableInterface.setValue(talonTable, "D", gyro.getD());
		NetworkTableInterface.setValue(talonTable, "Current Angle", gyro.getCurAngle());
		NetworkTableInterface.setValue(talonTable, "Angle Error", gyro.calculateAE());
	}
	
	public static void updateTalon(TalonSRX talon){
		String talonTable = Constants.TALON_TABLE_LOCATION + "/"+talon.getDeviceID();
		
		NetworkTableInterface.setValue(talonTable, "Inverted", talon.getInverted());
		NetworkTableInterface.setValue(talonTable, "Percent Output", talon.getMotorOutputPercent());
		NetworkTableInterface.setValue(talonTable, "Output Voltage", talon.getMotorOutputVoltage());
		NetworkTableInterface.setValue(talonTable, "Bus Voltage", talon.getBusVoltage());
		NetworkTableInterface.setValue(talonTable, "Output Current", talon.getOutputCurrent());
		NetworkTableInterface.setValue(talonTable, "Position", talon.getSelectedSensorPosition(0));
		NetworkTableInterface.setValue(talonTable, "Velocity", talon.getSelectedSensorVelocity(0));
		NetworkTableInterface.setValue(talonTable, "Closed Loop Error", talon.getClosedLoopError(0));
		NetworkTableInterface.setValue(talonTable, "Error Derivative", talon.getErrorDerivative(0));
		NetworkTableInterface.setValue(talonTable, "Integral Acummulator", talon.getIntegralAccumulator(0));
		//NetworkTableInterface.setValue(talonTable, "Forward Limit Closed", talon.);
		//NetworkTableInterface.setValue(talonTable, "Reverse Limit Closed", talon.);
		//NetworkTableInterface.setValue(talonTable, "inverted", talon);
		//Assumes usage of 2 PID loops
		setPIDValues(Constants.TALON_VELOCITY_SLOT_IDX, talon);
		setPIDValues(Constants.TALON_DISTANCE_SLOT_IDX, talon);
	}
	
	public static void setPIDValues(int idx, TalonSRX talon){
		String talonTable = Constants.TALON_TABLE_LOCATION + "/"+talon.getDeviceID()+"/pid:"+idx;
		NetworkTableInterface.setValue(talonTable, "P", talon.configGetParameter(ParamEnum.eProfileParamSlot_P,idx, 0));
		NetworkTableInterface.setValue(talonTable, "I", talon.configGetParameter(ParamEnum.eProfileParamSlot_I, idx, 0));
		NetworkTableInterface.setValue(talonTable, "D", talon.configGetParameter(ParamEnum.eProfileParamSlot_D, idx, 0));
		NetworkTableInterface.setValue(talonTable, "FF", talon.configGetParameter(ParamEnum.eProfileParamSlot_F, idx, 0));
		NetworkTableInterface.setValue(talonTable, "IZone", talon.configGetParameter(ParamEnum.eProfileParamSlot_IZone, idx, 0));
		NetworkTableInterface.getDouble(subtablePath, varName)
		//NetworkTableInterface.setValue(talonTable, "inverted", talon);
	}
	
	public static void getPIDValue(int idx, TalonSRX talon) {
}
