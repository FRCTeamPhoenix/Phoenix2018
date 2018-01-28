package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.util.NetworkTableInterface;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	
	Joystick joystick1 = new Joystick(1);
	Joystick joystick2 = new Joystick(2);
	Gyro imu;

	PCMHandler PCM;

    public Robot() {
    	imu = new Gyro();
    	imu.init();
    	PCM = new PCMHandler(5);
    	PCM.turnOn();
    }

    @Override
    public void operatorControl() {

    	double y = joystick1.getY();
    	//double y2 = joystick1.getRawAxis(3);
    	double speedv = 0.5;
    	PCM.turnOn(); 
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
	    	//teliopPeriodic
	    	
    	
    	}
    	
    }

    @Override
    public void autonomous() {
    	
    }

    @Override
    public void test() {
    	PCM.turnOn();
    	imu.reset();
    	imu.setGoal();
    	while(isEnabled()){
    		NetworkTableInterface.setValue("Sensors/Gyro", "distanceFromGoal", imu.angleFromGoal());
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }
}
