package org.usfirst.frc.team2342.robot;


import com.analog.adis16448.frc.ADIS16448_IMU;

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
    	ADIS16448_IMU imu = new ADIS16448_IMU();
    	Infrared infraredSensor = new Infrared(9);
    	//Lidar test = new Lidar();
    	//test.start();
    	while(isEnabled()){
    		//SmartDashboard.putString("DB/String 0", ""+test.getDistanceIn());
    		//SmartDashboard.putString("DB/String 1", ""+test.getFDistanceIn());
    		//SmartDashboard.putString("DB/String 2", ""+test.getSDistanceIn());
    		
    		
    		
    		SmartDashboard.putString("DB/String 0", ""+infraredSensor.detectsObject());
    		
    		SmartDashboard.putString("DB/String 3", ""+imu.getAngleX());
    		SmartDashboard.putString("DB/String 4", ""+imu.getAngleY());
    		SmartDashboard.putString("DB/String 5", ""+imu.getAngleZ());
    		
    		//SmartDashboard.putNumber("DB/String 6", "Accel-X"+imu.getAccelX());
    		//SmartDashboard.putNumber("DB/String 7", "Accel-Y"+imu.getAccelY());
    		//SmartDashboard.putNumber("DB/String 8", "Accel-Z"+imu.getAccelZ());
    		
    		SmartDashboard.putString("DB/String 6", "Pitch"+imu.getPitch());
    		SmartDashboard.putString("DB/String 7", "Roll"+imu.getRoll());
    		SmartDashboard.putString("DB/String 8", "Yaw"+imu.getYaw());
    		
    		//SmartDashboard.putNumber("Pressure: ", imu.getBarometricPressure());
    		//SmartDashboard.putNumber("Temperature: ", imu.getTemperature());
    	}
    	//test.stop();
    }
}
