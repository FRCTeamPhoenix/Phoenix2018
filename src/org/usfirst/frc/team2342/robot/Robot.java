package org.usfirst.frc.team2342.robot;

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

    }

    @Override
    public void test() {
    	Lidar test = new Lidar();
    	test.start();
    	while(isEnabled()){
    		SmartDashboard.putString("DB/String 0", ""+test.getDistanceIn());
    	}
    	test.stop();
    }
}
