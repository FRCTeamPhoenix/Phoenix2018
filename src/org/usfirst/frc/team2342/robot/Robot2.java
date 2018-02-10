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

public class Robot2 extends IterativeRobot {

	/*
	 * ROBOT2 is A TEST FOR THE PNUMATIC BOX.  DON'T DELETE.
	 * THIS IS VERY IMPORTANT!!!
	 * 
	 * -Ben
	 */
	
	Solenoid x = new Solenoid(50, 0);
	Solenoid y = new Solenoid(50, 1);
	Compressor compressor = new Compressor (50);
	
	
    public Robot2() {
    	//PCM.turnOn();
    	//WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
    	//WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
    	//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
    	//cascadeElevator = new CascadeElevator(talon1, talon2);
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
			compressor.start();
		} else {
			compressor.stop();
		}
    	
    	//Switch solenoids according to button
    	if (SmartDashboard.getBoolean("DB/Button 1", false)) {
    		x.set(false);
    		y.set(true);
    	} else {
    		y.set(false);
    		x.set(true);
    	}
    }
}
