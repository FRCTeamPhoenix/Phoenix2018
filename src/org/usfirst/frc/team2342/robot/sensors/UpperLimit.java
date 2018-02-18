package org.usfirst.frc.team2342.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class UpperLimit {
	private DigitalInput upperLimit;
	
	public UpperLimit(int channel){
		upperLimit = new DigitalInput(channel);
	}
	
	public boolean detectsObject(){
		//flip the output because false normally means detected
		return !upperLimit.get();
	}
	public boolean atObject() {
		return upperLimit.isAnalogTrigger();
	}
}