package org.usfirst.frc.team2342.robot.sensors;

import org.usfirst.frc.team2342.robot.Constants;

import edu.wpi.first.wpilibj.DigitalInput;

public class Infrared {
	private DigitalInput infraSensor;
	
	public Infrared(){
		infraSensor = new DigitalInput(Constants.INFRARED_PORT);
	}
	
	public boolean detectsObject(){
		//flip the output because false normally means detected
		return !infraSensor.get();
	}
}
