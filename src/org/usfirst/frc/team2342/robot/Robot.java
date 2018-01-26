package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	ADIS16448_IMU imu;
	Joystick joystick1 = new Joystick(1);
	Joystick joystick2 = new Joystick(2);
	SmartTalon talon1 = new SmartTalon(1);
	SmartTalon talon2 = new SmartTalon(2, true, ControlMode.Current);
	SmartTalon talon3 = new SmartTalon(3);
	SmartTalon talon4 = new SmartTalon(4, true, ControlMode.Current);
	

	PCMHandler PCM;

    public Robot() {
    	imu = new ADIS16448_IMU();
    	PCM = new PCMHandler(5);
    }

    @Override
    public void operatorControl() {

    	double y = joystick1.getY();
    	//double y2 = joystick1.getRawAxis(3);
    	double speedv = 0.5;
    	imu.reset();
    	double startAngle = imu.getAngleX() + 180;
    	while(isEnabled()){
    		y = joystick1.getY();
    		//y2 = joystick2.getY();
    		
    		//SmartDashboard.putString("DB/String 1", "y2 (24): " + String.valueOf(y2));
    		
    		//talon1.goAt(speedv*y);
    		//talon2.goAt(-speedv*y2);
    		//talon3.goAt(speedv*y);
    		//talon4.goAt(-speedv*y2);
    	
    		//teliopInit
    	
    		//if (joystick1.getRawButton(1)){
    			//imu.calibrate();
    			//startAngle = imu.getAngleY();
    		//}
    		SmartDashboard.putString("DB/String 0", ""+imu.getAngleX());
    		
    		double angle = (imu.getAngleX()-startAngle)/2;
    		SmartDashboard.putString("DB/String 1", ""+angle);
    		talon1.goVoltage(y+(-angle * 0.03));
    		talon2.goVoltage(y+(angle * 0.03));
    		talon3.goVoltage(y+(-angle * 0.03));
    		talon4.goVoltage(y+(angle * 0.03));
    	
	    	//teliopPeriodic
	    	if (joystick1.getRawButton(1)) {
	    		imu.reset();
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
