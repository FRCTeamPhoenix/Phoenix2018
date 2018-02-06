package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.util.NetworkTableInterface;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.ArrayList;

import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.robot.actions.*;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	Joystick gamepad = new Joystick(0);
	TalonSRX test = new TalonSRX(1);
	PCMHandler PCM = new PCMHandler(11);
	TalonSRX talonFR = new TalonSRX(1);
	TalonSRX talonFL = new TalonSRX(2);
	TalonSRX talonBR = new TalonSRX(3);
	TalonSRX talonBL = new TalonSRX(4);
	Joystick joystick1 = new Joystick(0);
	Joystick joystick2 = new Joystick(1);
	WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);

    @Override
    public void operatorControl() {

    	double y = joystick1.getY();
    	double y2 = joystick1.getY();
    	double speedv = 0.5;
    	while(isEnabled()){
    		y = joystick1.getY();
    		y2 = joystick2.getY();
    		
    		// high gear
    		if (joystick1.getRawButton(8))
    			westCoast.setHighGear();
    		
    		// low gear
    		if (joystick1.getRawButton(9))
    			westCoast.setLowGear();
    		
    		// no gear
    		if (joystick1.getRawButton(10))
    			westCoast.setNoGear();
    		
//    		SmartDashboard.putString("DB/String 3", "Left Encoder: " + westCoast.getLeftTicks());
//    		SmartDashboard.putString("DB/String 4", "Right Encoder: " + westCoast.getRightTicks());
    		SmartDashboard.putString("DB/String 5", "y: " + String.valueOf(y));
    		SmartDashboard.putString("DB/String 6", "y2: " + String.valueOf(y2));
	    	//teliopPeriodic    	
    		westCoast.setOpenLoop(0.5 * y, 0.5 * y2);
    		
    	}
    	
    }

    @Override
    public void autonomous() {
    	boolean isEnabled = isEnabled();
    	ArrayList<Action> actions = new ArrayList<Action>();
    	
//    	actions.add(new DriveAction(westCoast, 7.0d, 0.0d, 3000, "1", "Foward"));
//    	actions.add(new DriveAction(westCoast, 0.0d, 0.0d, 1000, "2", "Stop"));
    	
    	ActionList actionsL = null;
		try {
			actionsL = new ActionList(actions);
		} catch (DependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isEnabled = false;
		}
		
    	while(isEnabled && isAutonomous()) {
    		actionsL.execute();
    		SmartDashboard.putString("DB/String 1", "EMS: " + joystick1.getRawButton(1));
    		SmartDashboard.putString("DB/String 0", "Current ACTION: " + actionsL.getAction());
    		SmartDashboard.putString("DB/String 2", "IS DONE: " + !isEnabled);
    		
    		if (joystick1.getRawButton(1)) {
    			SmartDashboard.putString("DB/String 1", "EMS: " + joystick1.getRawButton(1));
    			System.out.println("Stopping all actions");
    			actionsL.stopAll();
    			isEnabled = false;
    		}
    		
    		else if (actionsL.isDone()) {
    			System.out.println("Stopping all actions");
    			actionsL.stopAll();
    			isEnabled = false;
    		}
    		
    		else
    			isEnabled = isEnabled();
    	}
    	SmartDashboard.putString("DB/String 2", "IS DONE: " + !isEnabled);
    }

    @Override
    public void test() {
    	westCoast.zeroSensors();
    	while(isEnabled()){
    		//westCoast.setVelocity(10.0d, 10.0d);
    		double angle = westCoast.pidc.getCurAngle();
    		System.out.println("Angle: " + String.valueOf(angle));
    		System.out.println("PID: " + String.valueOf(westCoast.pidc.getCorrection()));
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	westCoast.zeroSensors();
    	westCoast.setVelocity(0.0d, 0.0d);
    }
}
