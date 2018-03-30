package org.usfirst.frc.team2342.robot.sensors;

import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DigitalInput;

public class Infrared {
	static DigitalInput limit = null;
	public static void init() {
		if(limit != null) {
			limit = new DigitalInput(Constants.INFRARED_SENSOR);
		}
	}
	public static boolean get() {
		if(limit != null) {
			return limit.get();
		}
		else
			return false;
	}
}
