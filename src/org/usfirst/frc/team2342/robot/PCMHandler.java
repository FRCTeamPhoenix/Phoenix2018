package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class PCMHandler {
	Compressor compressor; 
	Solenoid highgearSol = new Solenoid(5,0);
	Solenoid lowgearSol = new Solenoid(5,1);

	public PCMHandler(int port) {
		
		compressor = new Compressor(port);
		compressor.setClosedLoopControl(true);
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
	
}
