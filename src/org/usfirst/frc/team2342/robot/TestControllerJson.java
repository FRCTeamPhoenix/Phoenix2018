package org.usfirst.frc.team2342.robot;

import java.util.HashMap;

import org.usfirst.frc.team2342.json.ControllerJson;

public class TestControllerJson {

	public static void main(String[] args) {
		
		ControllerJson controller1 = new ControllerJson();
		
		controller1.joystickID = 1;
		controller1.buttons = new HashMap<String, Integer>();
		
		controller1.buttons.put("A", 1);
		controller1.buttons.put("B", 2);
		controller1.buttons.put("X", 3);
		controller1.buttons.put("Y", 4);
		controller1.buttons.put("LB", 5);
		controller1.buttons.put("RB", 6);
		controller1.buttons.put("BACK", 7);
		controller1.buttons.put("START", 8);
		controller1.buttons.put("LS", 9);
		controller1.buttons.put("RS", 10);
		
		controller1.axis.put("LSX", 1);
		controller1.axis.put("LSY", 2);
		controller1.axis.put("LT", 3);
		controller1.axis.put("RT", 4);
		controller1.axis.put("RSX", 5);
		controller1.axis.put("RSY", 6);
		
		
		
	}

}
