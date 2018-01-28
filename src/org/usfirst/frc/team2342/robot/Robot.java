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
    	while(isEnabled()){
    		westCoast.setVelocity(joystickL.getRawAxis(1), joystickR.getRawAxis(1));
    		westCoast.regulateCompressor();
    	}
    }

    @Override
    public void autonomous() {
    	
    }

    @Override
    public void test() {
    	
    }
}
