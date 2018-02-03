package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.NetworkTableInterface;

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
	
	public static void updateTalon(WPI_TalonSRX talon){
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
	}
	
	public static void setPIDValues(PIDGains pid, int idx, int talonID){
		String talonTable = Constants.TALON_TABLE_LOCATION + "/"+talonID+"/"+idx;
		NetworkTableInterface.setValue(talonTable, "P", pid.p);
		NetworkTableInterface.setValue(talonTable, "I", pid.i);
		NetworkTableInterface.setValue(talonTable, "D", pid.d);
		NetworkTableInterface.setValue(talonTable, "RR", pid.rr);
		NetworkTableInterface.setValue(talonTable, "FF", pid.ff);
		NetworkTableInterface.setValue(talonTable, "IZone", pid.izone);
		//NetworkTableInterface.setValue(talonTable, "inverted", talon);
	}
}
