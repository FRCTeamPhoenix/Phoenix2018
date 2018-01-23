package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);
	
	SmartTalon talonFR = new SmartTalon(1);
	SmartTalon talonFL = new SmartTalon(2);
	SmartTalon talonBR = new SmartTalon(3);
	SmartTalon talonBL = new SmartTalon(4);
	
	PCMHandler PCM;

    public Robot() {
    	PCM = new PCMHandler(11);
    }

    @Override
    public void operatorControl() {

    	double r = joystickR.getRawAxis(1);
    	double l = joystickL.getRawAxis(1);
    	double speedv = 0.5;
    	
    	while(isEnabled()){
    		r = joystickR.getRawAxis(1);
    		l = joystickL.getRawAxis(1);
    		
//    		talonFR.goAt(-speedv*r);
//    		talonFL.goAt(speedv*l);
//    		talonBR.goAt(-speedv*r);
//    		talonBL.goAt(speedv*l);
    		
//    		talonFR.goDistance(-0.25, 0.4);
//    		talonFL.goDistance(-0.25, 0.4);
//    		talonBR.goDistance(-0.25, 0.4);
//    		talonBL.goDistance(-0.25, 0.4);
    		
    		SmartDashboard.putString("DB/String 5", Double.toString(talonFR.getSelectedSensorPosition(0)));
    		
    	
    		//teliopInit
    	
    		/*if (joystick1.getRawButton(1)) {
    			talon1.goDistance(0.25, 0.4);
    			talon2.goDistance(-0.25, 0.4);
    			talon3.goDistance(0.25, 0.4);
    			talon4.goDistance(-0.25, 0.4);
    		}*/
    		
	    		//teliopPeriodic
	    	if (joystickR.getRawButton(8)) {
	    		PCM.setHighGear(true);
	    		PCM.setLowGear(false);
	    	} else {
	    		PCM.setHighGear(false);
	    		PCM.setLowGear(true);
	    	}
    	
	    	PCM.compressorRegulate();
	    	
    	}
    	
    }

    @Override
    public void autonomous() {
    	
    }

    @Override
    public void test() {
    	while(isEnabled()){
	    	talonFR.goVoltage(-0.4);
	    	talonFL.goVoltage(0.4);
	    	talonBR.goVoltage(-0.4);
	    	talonBL.goVoltage(0.4);
    	

	    	//NetworkTableInterface.setValue("test", "firstVar", "sup");
	    	//NetworkTableInterface.setValue("test/nextlevel", "firstVar", 1);
	    	//NetworkTableInterface.setValue("test/nextlevel/wow", "firstVar", "sup");
	    	//SmartDashboard.putString("DB/String 1", NetworkTableInterface.getString("test/nextlevel/wow", "firstVar"));
    	}

    }
}
