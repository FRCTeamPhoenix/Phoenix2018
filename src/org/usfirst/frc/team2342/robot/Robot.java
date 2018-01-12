package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.util.NetworkTableInterface;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

    public Robot() {

    }

    @Override
    public void operatorControl() {
    	
    }

    @Override
    public void autonomous() {
    	while(isEnabled()){
	    	SmartDashboard.putString("DB/String 1", "oof");
	    	NetworkTableInterface.updateTable("test", "firstVar", "sup");
	    	//NetworkTableInterface.updateTable("test/nextlevel", "firstVar", 1);
	    	//NetworkTableInterface.updateTable("test/nextlevel/wow", "firstVar", "sup");
    	}
    }

    @Override
    public void test() {

    }
 
}
