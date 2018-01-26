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
