package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

	SmartTalon talon = new SmartTalon(1);
	
    public Robot() {

    }

    @Override
    public void operatorControl() {

    	while(isEnabled()){
    		talon.goVoltage(0.3);
    	}
    	talon.goVoltage(0);
    	
    }

    @Override
    public void autonomous() {

    }

    @Override
    public void test() {

    	
    }
}
