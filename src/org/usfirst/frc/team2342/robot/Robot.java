package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {

	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);
	
	WestCoastTankDrive westCoast = WestCoastTankDrive.getInstance();

    @Override
    public void operatorControl() {
    	westCoast.setDistance(5.0, 5.0);
    	while(isEnabled()){
    		//if (joystickR.getRawButton(1)) { 
    			//System.out.println("Setting for 8000 ticks");
    			//westCoast.setVelocity(joystickL.getRawAxis(1), joystickR.getRawAxis(1));
    		//}
    		westCoast.regulateCompressor();
    		try {
    		    Thread.sleep(10);
    		} catch(InterruptedException ex) {
    		    Thread.currentThread().interrupt();
    		}
    	}
    }

    @Override
    public void autonomous() {
    	
    }

    @Override
    public void test() {
    	
    }
}
