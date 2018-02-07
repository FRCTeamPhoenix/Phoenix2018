package org.usfirst.frc.team2342.robot;


import org.usfirst.frc.team2342.commands.CascadeUp;
import org.usfirst.frc.team2342.commands.DriveForward;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	Joystick gamepad = new Joystick(3);
	//PCMHandler PCM = new PCMHandler(11);
	WPI_TalonSRX talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
	WPI_TalonSRX talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
	WPI_TalonSRX talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
	WPI_TalonSRX talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
	//WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
	TalonSRX talon1 = new TalonSRX(1);
	CascadeElevator cascadeElevator = new CascadeElevator(talon1);
	Joystick joystickR = new Joystick(2);
	Joystick joystickL = new Joystick(1);

    public Robot() {
    	//PCM.turnOn();
    	//WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
    	//WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
    	//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
    	cascadeElevator = new CascadeElevator(talon1);
    }
    
    public void teleopInit() {
    	//Command driveJoystick = new DriveGamepad(gamepad, westCoast);
    	Command driveJoystick2 = new CascadeUp(gamepad, cascadeElevator);
    	//Scheduler.getInstance().add(driveJoystick);
    	Scheduler.getInstance().add(driveJoystick2);
    }
    
    public void teleopPeriodic() {
    	SmartDashboard.putString("DB/String 0", ""+gamepad.getRawAxis(1));
    	SmartDashboard.putString("DB/String 1", ""+gamepad.getRawAxis(3));
    	SmartDashboard.putString("DB/String 2", ""+gamepad.getRawButton(5));
    	SmartDashboard.putString("DB/String 3", ""+gamepad.getRawButton(6));
    	Scheduler.getInstance().run();
    	/*//Drive with joystick control in velocity mode
		westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		if (gamepad.getRawButton(5))
			westCoast.setLowGear();
		else if (gamepad.getRawButton(6))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		//Sleep for 0.01s
		/*try {
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
		}
		
    	
    	PCM.compressorRegulate(); */
    }
    
    public void disabledInit() {
    	Scheduler.getInstance().removeAll();
    }
    
    public void autonomousInit() {
    	//Command goForward = new DriveForward(20, westCoast, 6.0 * Constants.TALON_SPEED_RPS);
    	//Scheduler.getInstance().add(goForward);
    }
    
    public void autonomousPeriodic(){
    	Scheduler.getInstance().run();
    	//PCM.compressorRegulate();
    	//westCoast.outputToSmartDashboard();
    }
}
