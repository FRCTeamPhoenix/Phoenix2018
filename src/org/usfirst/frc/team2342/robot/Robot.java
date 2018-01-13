package org.usfirst.frc.team342.robot;


import org.usfirst.frc.team2342.robot.talons.SmartTalon;



import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team2342.util.NetworkTableInterface;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

	Joystick joystick1 = new Joystick(0);
	Joystick joystick2 = new Joystick(1);
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
    		y = joystick1.getY();
    		y2 = joystick1.getRawAxis(3);
    		talon1.goVoltage(-speedv*y);
    		talon2.goVoltage(speedv*y2);
    		talon3.goVoltage(-speedv*y);
    		talon4.goVoltage(speedv*y2);
    	
    	//teliopInit
    	
    	
	    		//teliopPeriodic
	    	if (joystick1.getRawButton(8)) {
	    		PCM.setHighGear(true);
	    		PCM.setLowGear(false);
	    	}
	    	else {
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

    		talon1.goVoltage(0.3);
    		talon2.goVoltage(-0.3);
    		talon3.goVoltage(0.3);
    		talon4.goVoltage(-0.3);
    	
    	talon1.goVoltage(0);
    	talon2.goVoltage(0);
    	talon3.goVoltage(0);
    	talon4.goVoltage(0);
    	

	    	NetworkTableInterface.setValue("test", "firstVar", "sup");
	    	NetworkTableInterface.setValue("test/nextlevel", "firstVar", 1);
	    	NetworkTableInterface.setValue("test/nextlevel/wow", "firstVar", "sup");
	    	SmartDashboard.putString("DB/String 1", NetworkTableInterface.getString("test/nextlevel/wow", "firstVar"));
    	}

    }
}
