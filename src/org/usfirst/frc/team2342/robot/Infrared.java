package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Infrared {
	private DigitalInput infraSensor;
	
	public Infrared(int dioPort){
		infraSensor = new DigitalInput(dioPort);
	}
	
	public boolean detectsObject(){
		//flip the output because false normally means detected
		return !infraSensor.get();
	}
}
