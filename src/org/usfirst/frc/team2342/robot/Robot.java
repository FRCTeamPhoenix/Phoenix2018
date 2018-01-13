package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	Joystick joystick1 = new Joystick(0);
	Joystick joystick2 = new Joystick(1);
	SmartTalon talon1 = new SmartTalon(1);
	SmartTalon talon2 = new SmartTalon(2);
	SmartTalon talon3 = new SmartTalon(3);
	SmartTalon talon4 = new SmartTalon(4);
	
    public Robot() {

    }

    @Override
    public void operatorControl() {
    	double y = joystick1.getY();
    	double y2 = joystick2.getY();
    	double speedv = 0.5;
    	while(isEnabled()){
    		y = joystick1.getY();
    		y2 = joystick2.getY();
    		talon1.goVoltage(-speedv*y);
    		talon2.goVoltage(speedv*y2);
    		talon3.goVoltage(-speedv*y);
    		talon4.goVoltage(speedv*y2);
    	}
    	talon1.goVoltage(0);
    	talon2.goVoltage(0);
    	talon3.goVoltage(0);
    	talon4.goVoltage(0);
    }

    @Override
    public void autonomous() {

    }

    @Override
    public void test() {
    	while(isEnabled()){
    		talon1.goVoltage(0.3);
    		talon2.goVoltage(-0.3);
    		talon3.goVoltage(0.3);
    		talon4.goVoltage(-0.3);
    	}
    	talon1.goVoltage(0);
    	talon2.goVoltage(0);
    	talon3.goVoltage(0);
    	talon4.goVoltage(0);
    	
    }
}
