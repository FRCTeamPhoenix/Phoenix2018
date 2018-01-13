package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	PCMHandler PCM;
	Joystick stick;
    public Robot() {
PCM = new PCMHandler(5);
stick = new Joystick(0);
    }

    @Override
    public void operatorControl() {
    	//teliopInit
    	
    	while (isEnabled()) {
    		//teliopPeriodic
    	if (stick.getRawButton(8)) {
    		PCM.setHighGear(true);
    		PCM.setLowGear(false);
    	}
    	else {
    		PCM.setHighGear(false);
    		PCM.setLowGear(true);
    	}
    	}
    }

    @Override
    public void autonomous() {

    }

    @Override
    public void test() {

    }
}
