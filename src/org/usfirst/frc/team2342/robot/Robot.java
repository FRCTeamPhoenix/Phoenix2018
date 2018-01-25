package org.usfirst.frc.team2342.robot;


import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;

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
    	ADIS16448_IMU imu = new ADIS16448_IMU();
    	Infrared infraredSensor = new Infrared();
    	//Lidar test = new Lidar();
    	//test.start();
    	while(isEnabled()){
    		imu.getAngleY();
    	}
    	//test.stop();
    }
}
