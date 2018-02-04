package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.commands.DriveForward;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	Joystick gamepad = new Joystick(0);
	PCMHandler PCM = new PCMHandler(11);
	TalonSRX talonFR = new TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
	TalonSRX talonFL = new TalonSRX(Constants.LEFT_MASTER_TALON_ID);
	TalonSRX talonBR = new TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
	TalonSRX talonBL = new TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
	WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
	Joystick joystickR = new Joystick(1);
	Joystick joystickL = new Joystick(2);

    public Robot() {
    	//TalonSRX talon1 = new TalonSRX(0);
    	//TalonSRX talon2 = new TalonSRX(1);
    	//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
    	//cascadeElevator = new CascadeElevator(talon1, talon2);
    }
    
    public void teleopInit() {
    	Command goForward = new DriveForward(20, westCoast);
    	Scheduler.getInstance().add(goForward);
  	
    }
    
    public void teleopPeriodic() {
    	Scheduler.getInstance().run();
    	//Drive with joystick control in velocity mode
		westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		/*if (gamepad.getRawButton(5))
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
		if (joystick1.getRawButton(1)) {
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
    
    public void autonomous() {
    	
    }
    
    public void test() {
    	PCM.turnOn();
    	
    }
}
