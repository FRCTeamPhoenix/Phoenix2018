package org.usfirst.frc.team2342.robot;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	/*
	 * ROBOT2 is A TEST FOR THE PNUMATIC BOX.  DON'T DELETE.
	 * THIS IS VERY IMPORTANT!!!
	 * 
	 * -Ben
	 */
	
	Solenoid x = new Solenoid(50, 0);
	Solenoid y = new Solenoid(50, 1);
	Compressor compressor = new Compressor (50);
	
	
    public Robot() {
    	System.out.println("RUNNING ROBOT2!!!");
    }
    
    public void teleopInit() {
    }
    
    public void teleopPeriodic() {
    }
    
    public void disabledInit() {
    	Scheduler.getInstance().removeAll();
    }
    
    public void autonomousInit() {
    }
    
    public void autonomousPeriodic(){
    	//Regulate Compressor
    	if (compressor.getPressureSwitchValue()) {
			compressor.stop();
		} else {
			compressor.start();
		}
    	
    	//Switch solenoids according to button
    	if (SmartDashboard.getBoolean("DB/Button 1", false)) {
    		x.set(false);
    		y.set(true);
    		SmartDashboard.putString("DB/String 1", "Solenoid Y");
    	} else {
    		y.set(false);
    		x.set(true);
    		SmartDashboard.putString("DB/String 1", "Solenoid Y");
    	}
    	
    	//Outputs values to dashboard
    	SmartDashboard.putString("DB/String 0", "PSV: " + compressor.getPressureSwitchValue());
    }
}
