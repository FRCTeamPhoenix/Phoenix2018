package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends RobotBase {

	Joystick gamepad = new Joystick(0);
	TalonSRX test = new TalonSRX(1);
	PCMHandler PCM = new PCMHandler(11);
	TalonSRX talonFR = new TalonSRX(1);
	TalonSRX talonFL = new TalonSRX(2);
	TalonSRX talonBR = new TalonSRX(3);
	TalonSRX talonBL = new TalonSRX(4);
	WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);
	
	private static BoxManipulator boxManipulator;
	private static CascadeElevator cascadeElevator;

    public Robot() {
    	//TalonSRX talon1 = new TalonSRX(0);
    	//TalonSRX talon2 = new TalonSRX(1);
    	//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
    	//cascadeElevator = new CascadeElevator(talon1, talon2);
    }

    public void operatorControl() {

    	double r = joystickR.getRawAxis(1);
    	double l = joystickL.getRawAxis(1);
    	double speedv = 0.5;

    	
    	while(isEnabled()){
    		//Drive with joystick control in velocity mode
    		westCoast.setVelocity(100, 100);
    		westCoast.outputToSmartDashboard();
    		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
    		if (gamepad.getRawButton(5))
    			westCoast.setLowGear();
    		else if (gamepad.getRawButton(6))
    			westCoast.setHighGear();
    		else 
    			westCoast.setNoGear();
    		
    		//Sleep for 0.01s
    		try {
    		    Thread.sleep(100);
    		} catch(InterruptedException ex) {
    		    Thread.currentThread().interrupt();
    		}
    		//teliopInity
    		/*if (joystick1.getRawButton(1)) {
    			talon1.goDistance(0.25, 0.4);
    			talon2.goDistance(-0.25, 0.4);
    			talon3.goDistance(0.25, 0.4);
    			talon4.goDistance(-0.25, 0.4);
    		}*/
    		
	    		//teliopPeriodic
	    	if (joystickR.getRawButton(8)) {
	    		PCM.setHighGear(true);
	    		PCM.setLowGear(false);
	    	} else {
	    		PCM.setHighGear(false);
	    		PCM.setLowGear(true);
	    	}
    	
	    	PCM.compressorRegulate();
	    	
    	}
    }
    
    public void autonomous() {
    	
    }
    
    public void test() {
    	PCM.turnOn();
    	
    }
    
    @Override
	public void startCompetition() {
		// TODO Auto-generated method stub
		
	}
}
