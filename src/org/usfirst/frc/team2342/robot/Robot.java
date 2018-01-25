package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    	
    	PCM = new PCMHandler(5);
    }

    @Override
    public void operatorControl() {

    	double y = joystick1.getY();
    	double y2 = joystick1.getRawAxis(3);
    	double speedv = 0.5;
    	while(isEnabled()){
    		//y = joystick1.getY();
    		//y2 = joystick2.getY();
    		
    		SmartDashboard.putString("DB/String 0", "t1: " + String.valueOf(talon1.getSelectedSensorPosition(0)));
    		SmartDashboard.putString("DB/String 1", "t2: " + String.valueOf(talon2.getSelectedSensorPosition(0)));
    		SmartDashboard.putString("DB/String 2", "t3: " + String.valueOf(talon3.getSelectedSensorPosition(0)));
    		SmartDashboard.putString("DB/String 3", "t4: " + String.valueOf(talon4.getSelectedSensorPosition(0)));

    		
    		//SmartDashboard.putString("DB/String 1", "y2 (24): " + String.valueOf(y2));
    		
    		//talon1.goAt(speedv*y);
    		//talon2.goAt(-speedv*y2);
    		//talon3.goAt(speedv*y);
    		//talon4.goAt(-speedv*y2);
    	
    		//teliopInit
    	
    		if (joystick1.getRawButton(1)) {
    			talon1.goDistance(0.25, 0.4);
    			talon2.goDistance(-0.25, 0.4);
    			talon3.goDistance(0.25, 0.4);
    			talon4.goDistance(-0.25, 0.4);
    		}
    	
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

    }
}
