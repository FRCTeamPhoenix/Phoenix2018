package org.usfirst.frc.team2342.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class LowerLimit {
	private DigitalInput lowerLimit;
	
	public LowerLimit(int channel){
		lowerLimit = new DigitalInput(channel);
	}
	
	public boolean detectsObject(){
		//flip the output because false normally means detected
		return !lowerLimit.get();
	}
}