package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PCMHandler {
	Compressor compressor; 
	Solenoid highgearSol1 = new Solenoid(11,0);
	Solenoid lowgearSol1 = new Solenoid(11,1);
	Solenoid highgearSol2 = new Solenoid(11,2);
	Solenoid lowgearSol2 = new Solenoid(11,3);

	public PCMHandler(int port) {
		
		compressor = new Compressor(port);
		compressor.setClosedLoopControl(true);
	}
	
	public void turnOn(){
		compressor.setClosedLoopControl(true);
	}
	
	public void turnOff(){
		compressor.setClosedLoopControl(false);
	}
	
	public void setLowGear(boolean value) {
		lowgearSol1.set(value);
		lowgearSol2.set(value);
	}
	public void setHighGear(boolean value) {
		highgearSol1.set(value);
		highgearSol2.set(value);
	}
	
	
	public double getCurrent (){
		return compressor.getCompressorCurrent();
	}
	
	public void compressorRegulate () {
		SmartDashboard.putString("DB/String 4", ""+compressor.getPressureSwitchValue());
		/*if (compressor.getPressureSwitchValue()) {
			compressor.start();
		} else {
			compressor.stop();
		}*/
	}
	
}
