package org.usfirst.frc.team2342.json;

import java.util.HashMap;

public class ControllerJson {
	
	public int joystickID = -1;

	public HashMap<String, Integer> buttons = new HashMap<String, Integer>();
	public HashMap<String, Integer> axis = new HashMap<String, Integer>();
	
	public ControllerJson() {
		
	}
}
