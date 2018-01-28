package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PCMHandler {
	Compressor compressor; 
	Solenoid highgearSol = new Solenoid(11,0);
	Solenoid lowgearSol = new Solenoid(11,1);

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
		lowgearSol.set(value);
		
	}
	public void setHighGear(boolean value) {
		highgearSol.set(value);
	}
	
	
	public double getPressure (){
		return compressor.getCompressorCurrent();
	}
	
	public void compressorRegulate () {
		if (compressor.getPressureSwitchValue()) {
			compressor.start();
		} else {
			compressor.stop();
		}
	}
	
}