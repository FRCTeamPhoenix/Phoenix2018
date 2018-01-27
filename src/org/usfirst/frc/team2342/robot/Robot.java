package org.usfirst.frc.team2342.robot;


import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team2342.robot.actions.Action;
import org.usfirst.frc.team2342.robot.actions.ActionList;
import org.usfirst.frc.team2342.robot.actions.DependencyException;
import org.usfirst.frc.team2342.robot.actions.DriveAction;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	Joystick joystick1 = new Joystick(1);
	Joystick joystick2 = new Joystick(2);
	SmartTalon talon1 = new SmartTalon(1);
	SmartTalon talon2 = new SmartTalon(2);
	SmartTalon talon3 = new SmartTalon(3);
	SmartTalon talon4 = new SmartTalon(4);
	

	PCMHandler PCM;

    public Robot() {
    	PCM = new PCMHandler(11);
    }

    @Override
    public void operatorControl() {

    	double y = joystick1.getY();
    	double y2 = joystick1.getRawAxis(3);
    	double speedv = 0.5;
    	while(isEnabled()){
    		y = joystick1.getY();
    		y2 = joystick2.getY();
    		
    		//SmartDashboard.putString("DB/String 0", "t1: " + String.valueOf(talon1.getSelectedSensorPosition(0)));
    		//SmartDashboard.putString("DB/String 1", "t2: " + String.valueOf(talon2.getSelectedSensorPosition(0)));
    		//SmartDashboard.putString("DB/String 2", "t3: " + String.valueOf(talon3.getSelectedSensorPosition(0)));
    		//SmartDashboard.putString("DB/String 3", "t4: " + String.valueOf(talon4.getSelectedSensorPosition(0)));

    		
    		//SmartDashboard.putString("DB/String 1", "y2 (24): " + String.valueOf(y2));
    		
    		talon1.goAt(speedv*y);
    		talon2.goAt(-speedv*y2);
    		talon3.goAt(speedv*y);
    		talon4.goAt(-speedv*y2);
    	
    		//teliopInit
    	
    		/*if (joystick1.getRawButton(1)) {
    			talon1.goDistance(0.25, 0.4);
    			talon2.goDistance(-0.25, 0.4);
    			talon3.goDistance(0.25, 0.4);
    			talon4.goDistance(-0.25, 0.4);
    		}*/
    	
	    	//teliopPeriodic
	    	if (joystick1.getRawButton(8)) {
	    		PCM.setHighGear(true);
	    		PCM.setLowGear(false);
	    	} else {
	    		PCM.setHighGear(false);
	    		PCM.setLowGear(true);
	    	}
    	
    	}
    	
    }

    @Override
    public void autonomous() {
    	ArrayList<Action> actions = new ArrayList<Action>();
    	ArrayList<String> a1 = new ArrayList<String>();
    	a1.add("drive1");
    	actions.add(new DriveAction(0.4,3000,new ArrayList<String>(),talon1,talon2,talon3,talon4,"drive1"));
    	actions.add(new DriveAction(-0.4,3000,a1,talon1,talon2,talon3,talon4,"drive2"));
    	ActionList actionList = null;
    	try {
			actionList = new ActionList(actions);
		} catch (DependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	while(isEnabled())
    		actionList.execute();
    }

    @Override
    public void test() {
    	while(isEnabled()){
    		//talon1.goAt(0.3);
    		//talon2.goAt(-0.3);
    		//talon3.goAt(0.3);
    		//talon4.goAt(-0.3);
    	
	    	talon1.goVoltage(-0.4);
	    	talon2.goVoltage(0.4);
	    	talon3.goVoltage(-0.4);
	    	talon4.goVoltage(0.4);
    	

	    	//NetworkTableInterface.setValue("test", "firstVar", "sup");
	    	//NetworkTableInterface.setValue("test/nextlevel", "firstVar", 1);
	    	//NetworkTableInterface.setValue("test/nextlevel/wow", "firstVar", "sup");
	    	//SmartDashboard.putString("DB/String 1", NetworkTableInterface.getString("test/nextlevel/wow", "firstVar"));
    	}

    }
}
