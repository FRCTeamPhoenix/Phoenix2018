package org.usfirst.frc.team2342.robot.actions;

import java.util.ArrayList;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class StopAction extends Action{
	
	public StopAction(String tag, ArrayList<String> dependencies){
		
		super(tag, dependencies);
		
	}
	
	public void start(SmartTalon talon1, SmartTalon talon2, SmartTalon talon3, SmartTalon talon4){
		stop(talon1, talon2, talon3, talon4);
	}
	
	public void stop(SmartTalon talon1, SmartTalon talon2, SmartTalon talon3, SmartTalon talon4){
		talon1.goVoltage(0);
    	talon2.goVoltage(0);
    	talon3.goVoltage(0);
    	talon4.goVoltage(0);
	}
	
}