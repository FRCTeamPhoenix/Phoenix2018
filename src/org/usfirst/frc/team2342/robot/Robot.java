package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.talons.SmartTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends RobotBase {

	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);
	
	SmartTalon talonFR = new SmartTalon(1);
	SmartTalon talonFL = new SmartTalon(2);
	SmartTalon talonBR = new SmartTalon(3);
	SmartTalon talonBL = new SmartTalon(4);
	
	PCMHandler PCM;
	
	private static BoxManipulator boxManipulator;
	private static CascadeElevator cascadeElevator;

    public Robot() {
    	PCM = new PCMHandler(11);
    	SmartTalon talon = new SmartTalon(0);
    	SmartTalon talon2 = new SmartTalon(1);
    	boxManipulator = new BoxManipulator(talon);
    	cascadeElevator = new CascadeElevator(talon2);
    }

    public void operatorControl() {

    	double r = joystickR.getRawAxis(1);
    	double l = joystickL.getRawAxis(1);
    	double speedv = 0.5;
    	
    	while(isEnabled()){
    		r = joystickR.getRawAxis(1);
    		l = joystickL.getRawAxis(1);
    		
    		talonFR.goAt(-speedv*r);
    		talonFL.goAt(speedv*l);
    		talonBR.goAt(-speedv*r);
    		talonBL.goAt(speedv*l);
    		
    		
    	
    		//teliopInit
    	
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
    	while(isEnabled()){
	    	talonFR.goVoltage(-0.4);
	    	talonFL.goVoltage(0.4);
	    	talonBR.goVoltage(-0.4);
	    	talonBL.goVoltage(0.4);
    	

	    	//NetworkTableInterface.setValue("test", "firstVar", "sup");
	    	//NetworkTableInterface.setValue("test/nextlevel", "firstVar", 1);
	    	//NetworkTableInterface.setValue("test/nextlevel/wow", "firstVar", "sup");
	    	//SmartDashboard.putString("DB/String 1", NetworkTableInterface.getString("test/nextlevel/wow", "firstVar"));
    	}

    }

	@Override
	public void startCompetition() {
		// TODO Auto-generated method stub
		
	}
}
