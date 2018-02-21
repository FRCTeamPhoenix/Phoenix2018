package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.automodes.leftscaleleftside;
import org.usfirst.frc.team2342.automodes.leftscalerightside;
import org.usfirst.frc.team2342.automodes.leftswitchleft;
import org.usfirst.frc.team2342.automodes.middleleftside;
import org.usfirst.frc.team2342.automodes.middlerightside;
import org.usfirst.frc.team2342.automodes.rightscaleleft;
import org.usfirst.frc.team2342.automodes.rightscaleright;
import org.usfirst.frc.team2342.automodes.rightswitchright;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.FMS;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	Joystick gamepad = new Joystick(0);
	PCMHandler PCM = new PCMHandler(11);
	WPI_TalonSRX talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
	WPI_TalonSRX talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
	WPI_TalonSRX talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
	WPI_TalonSRX talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
	WPI_TalonSRX talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
	WPI_TalonSRX talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
	WPI_TalonSRX talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
	WPI_TalonSRX talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
	//Solenoid solenoidLowGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_LOWGEAR);
	//Solenoid solenoidHighGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_HIGHGEAR);
	//Solenoid solenoid1 = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
	WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
	Joystick joystickR = new Joystick(2);
	Joystick joystickL = new Joystick(1);
	CascadeElevator cascadeElevator = new CascadeElevator(talonCascade);

	public Robot() {
		//PCM.turnOn();
		//WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
		//WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
		//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
		//cascadeElevator = new CascadeElevator(talonCascade);
	}

	public void teleopInit() {
		System.out.println("TELEOP MODE INIT");
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, westCoast);
		Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		westCoast.debug = true;
	}

	public void teleopPeriodic() {
		//Drive with joystick control in velocity mode
		westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		if (gamepad.getRawButton(5))
			westCoast.setLowGear();
		else if (gamepad.getRawButton(6))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		
		if(joystickL.getRawButton(1))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE));
		
		if(joystickL.getRawButton(2))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH));

		if(joystickL.getRawButton(3))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE));
		
		if(joystickL.getRawButton(4))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE));

		/*Scheduler.getInstance().run();
    	//Drive with joystick control in velocity mode
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
		if (joystick1.getRawButton(1)) {
			talon1.goDistance(0.25, 0.4);
			talon2.goDistance(-0.25, 0.4);
			talon3.goDistance(0.25, 0.4);
			talon4.goDistance(-0.25, 0.4);
		}

		// PCM.turnOn();
		// WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
		// WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
		// boxManipulator = new BoxManipulator(talon1, talon2, PCM);
		// cascadeElevator = new CascadeElevator(talon1, talon2);

		 */	
		try {
			Scheduler.getInstance().run();
			Thread.sleep(100);
		}
		catch (Exception e) {
			//TODO NOTHING
		}
	}

	public void disabledInit() {
		westCoast.setVelocity(0.0d, 0.0d);
		westCoast.zeroSensors();
		Scheduler.getInstance().removeAll();
	}

	public void autonomousInit() {
		System.out.println("AUTOMODE INIT");
		//Command goForward = new DriveForward(20, westCoast, 6.0 * Constants.TALON_SPEED_RPS);
		//Scheduler.getInstance().add(goForward);
		//westCoast.goArc(8, 90, 0.425, 0.5, false);
		//DriveDistance driveDistance = new DriveDistance(westCoast, 8);
		//Scheduler.getInstance().add(driveDistance);
//		westCoast.goArc(4, 90, -1.0d, -1.0d, false);
		//westCoast.updatePID();
		FMS.init();
    	//calculate auto mode
    	switch(FMS.getPosition()){
    	case 1:
    		if(FMS.scale()){
    			System.out.println("1;1");
    			//go for scale left side
    			Scheduler.getInstance().add(new leftscaleleftside(westCoast));
    		}else if(FMS.teamSwitch()){
    			System.out.println("1;2");
    			//go for switch on left side
    			Scheduler.getInstance().add(new leftswitchleft(westCoast));
    		}else{
    			System.out.println("1;3");
    			//go for switch on right side
    			Scheduler.getInstance().add(new leftscalerightside(westCoast));
    		}
    	case 2:
    		if(FMS.teamSwitch()){
    			System.out.println("2;1");
        		//middle to left side
    			Scheduler.getInstance().add(new middleleftside(westCoast));
        	}else{
        		System.out.println("2;2");
        		//middle to right side
        		Scheduler.getInstance().add(new middlerightside(westCoast));
        	}
    	case 3:
    		if(!FMS.scale()){
    			System.out.println("3;1");
    			//go for scale right side
    			Scheduler.getInstance().add(new rightscaleright(westCoast));
    		}else if(!FMS.teamSwitch()){
    			System.out.println("3;2");
    			//go for switch on right side
    			Scheduler.getInstance().add(new rightswitchright(westCoast));
    		}else{
    			System.out.println("3;3");
    			//go for switch on left side
    			Scheduler.getInstance().add(new rightscaleleft(westCoast));
    		}
		default:
			System.out.println("Default called!");
			//just drive forward 10 ft if a glitch occurs
			Scheduler.getInstance().add(new DriveDistance(westCoast, 10));
			break;
    	}
		//Command cascadeGo = new CascadePosition(cascadeElevator, 35);
		//Scheduler.getInstance().add(cascadeGo);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//westCoast.setGyroControl(true);
		//westCoast.pidc.gyroReset();
		//westCoast.zeroSensors();
		westCoast.debug = false;
		//westCoast.turnSet(-90.0d);
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		/*westCoast.arcLoop(false);
		//Scheduler.getInstance().run();
		PCM.compressorRegulate();*/
		/*westCoast.updatePID();
		try {
			TalonNWT.updateGyroPID(westCoast.pidc);
			//westCoast.rotateAuto(-2000.0d);
			//SmartDashboard.putString("DB/String 1", String.valueOf(westCoast.pidc.getCorrection()));
//			System.out.println(talonCascade.getSelectedSensorPosition(0));
			Thread.sleep(10);
		}
		catch (Exception e) {
			
		}*/
		//cascadeElevator.outputToSmartDashboard();
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
		westCoast.setGyroControl(true);
		westCoast.pidc.gyroReset();
		westCoast.zeroSensors();
		westCoast.debug = true;
		westCoast.turnSet(90.0d);		
		talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
	}

	@Override
	public void testPeriodic() {
		// Limit for the current velocity for the robot without cascade is 3000
		if (TalonNWT.isUpdatePID())
			westCoast.updatePID();
		try {
			TalonNWT.updateGyroPID(westCoast.pidc);
			westCoast.rotateAuto(-2000.0d);
			talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
		}
		catch (Exception e) {
			//NOTHING
		}
	}
}
