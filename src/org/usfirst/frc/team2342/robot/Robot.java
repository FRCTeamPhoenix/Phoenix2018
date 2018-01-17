package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.talons.SmartTalon;



import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team2342.util.NetworkTableInterface;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);
	
	SmartTalon talonFR = new SmartTalon(1);
	SmartTalon talonFL = new SmartTalon(2);
	SmartTalon talonBR = new SmartTalon(3);
	SmartTalon talonBL = new SmartTalon(4);
	
	PCMHandler PCM;

    public Robot() {
    	PCM = new PCMHandler(5);
    }

    @Override
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
    	
    	
	    		//teliopPeriodic
	    	if (joystickR.getRawButton(8)) {
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
    	while(isEnabled()){

    		talonFR.goAt(0.3);
    		talonFL.goAt(-0.3);
    		talonBR.goAt(0.3);
    		talonBL.goAt(-0.3);
    		
    		
    	
    		talonFR.goAt(0);
    		talonFL.goAt(0);
    		talonBR.goAt(0);
    		talonBL.goAt(0);
    	

	    	NetworkTableInterface.setValue("test", "firstVar", "sup");
	    	NetworkTableInterface.setValue("test/nextlevel", "firstVar", 1);
	    	NetworkTableInterface.setValue("test/nextlevel/wow", "firstVar", "sup");
	    	SmartDashboard.putString("DB/String 1", NetworkTableInterface.getString("test/nextlevel/wow", "firstVar"));
    	}

    }
}
