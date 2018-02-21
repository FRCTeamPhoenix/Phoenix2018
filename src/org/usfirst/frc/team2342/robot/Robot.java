package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.commands.PullBox;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

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
	Solenoid solenoid1 = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
	WestCoastTankDrive westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
	Joystick joystickR = new Joystick(2);
	Joystick joystickL = new Joystick(1);
	CascadeElevator cascadeElevator = new CascadeElevator(talonCascade);
	BoxManipulator boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, solenoid1);
	double speed = 0.0d;
	double tangle = 0.0d;

	public Robot() {
		//PCM.turnOn();
		//WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
		//WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
		//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
		//cascadeElevator = new CascadeElevator(talonCascade);
		
	}
	
	@Override
	public void robotInit() {
		if(!cascadeElevator.lowerLimit.get())
			cascadeElevator.zeroSensors();
	}

	public void teleopInit() {
		System.out.println("TELEOP MODE INIT");
		//PCM.turnOn();
		/*Command driveJoystick = new DriveGamepad(gamepad, westCoast);
		Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		westCoast.debug = true;*/
	}

	public void teleopPeriodic() {
		if (gamepad.getRawButton(5)) {
			PullBox pullBox = new PullBox(boxManipulator, gamepad);
			Scheduler.getInstance().add(pullBox);
		}
		
		if (gamepad.getRawButton(6)) {
			PushBox pushBox = new PushBox(boxManipulator, gamepad);
			Scheduler.getInstance().add(pushBox);
		}
		
		Scheduler.getInstance().run();
		//Drive with joystick control in velocity mode
		//westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		/*if (gamepad.getRawButton(5))
			westCoast.setLowGear();
		else if (gamepad.getRawButton(6))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		
		if (Math.abs(joystickL.getRawAxis(3)) > Constants.CASCADE_DEADZONE) {
			double s = joystickL.getRawAxis(3);
			double max = s < 0 ? 600 : 400;
			System.out.println(s);
			cascadeElevator.setVelocity(s * max);
		}
		else if(joystickL.getRawButton(1))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, joystickL));
		else if(joystickL.getRawButton(2))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, joystickL));
		else if(joystickL.getRawButton(3))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, joystickL));
		else if(joystickL.getRawButton(4))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, joystickL));
		else
			cascadeElevator.setVelocity(0);
		
		if(joystickL.getRawButton(5))
			solenoid1.set(true);
		else
			solenoid1.set(false);
		
		if(joystickL.getRawButton(7)) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -0.5);
		}
		else if(joystickL.getRawButton(8)) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0.5);
		} else {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
		}

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
		/*Scheduler.getInstance().run();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
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
		//westCoast.goArc(4, 90, -1.0d, -1.0d, false);
		/*westCoast.setGyroControl(true);
		westCoast.pidc.gyroReset();
		westCoast.zeroSensors();
		westCoast.debug = true;
		westCoast.turnSet(-90.0d);
		System.out.println(String.valueOf(westCoast.pidc.getTargetAngle()));
		westCoast.updatePID();
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);*/
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		/*westCoast.arcLoop(false);
		//Scheduler.getInstance().run();
		PCM.compressorRegulate();*/
		/*try {
			this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
			westCoast.updatePID();
			//TalonNWT.updateGyroPID(westCoast.pidc);
			westCoast.rotateAuto(-300 * speed);
//			System.out.println(String.valueOf(westCoast.pidc.getCorrection()));
			//SmartDashboard.putString("DB/String 1", String.valueOf(westCoast.pidc.getCorrection()));
//			System.out.println(talonCascade.getSelectedSensorPosition(0));
			Thread.sleep(100);
		}
		catch (Exception e) {
			
		}
		//cascadeElevator.outputToSmartDashboard();
		Scheduler.getInstance().add(new CascadePosition(cascadeElevator, 42, joystickL));*/
	}
	
	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
		westCoast.setGyroControl(true);
		westCoast.pidc.gyroReset();
		westCoast.zeroSensors();
		westCoast.debug = true;
		this.tangle = 90.0d;
		westCoast.turnSet(this.tangle);
		System.out.println(String.valueOf(westCoast.pidc.getTargetAngle()));
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		
		talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));

		//talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
	}

	@Override
	public void testPeriodic() {
		// Limit for the current velocity for the robot without cascade is 3000
		//talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
		
		if (Math.abs(joystickL.getRawAxis(3)) > Constants.CASCADE_DEADZONE) {
			double s = joystickL.getRawAxis(3);
			if(s < 0) s /= 2;
			talonCascade.set(ControlMode.PercentOutput,s);
		}
		try {
			westCoast.updatePID();
			if (!westCoast.reachAngle(tangle, westCoast.pidc.getCurAngle()))
				westCoast.rotateAuto(-300 * speed);
			else
				westCoast.setVelocity(0, 0);
			Thread.sleep(100);
		} catch(Exception e) {
			//DONOTHING
		}
		/*if (TalonNWT.isUpdatePID())
			westCoast.updatePID();
		try {
			TalonNWT.updateGyroPID(westCoast.pidc);
			westCoast.rotateAuto(-2000.0d);
			talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
		}
		catch (Exception e) {
			//NOTHING
		}
		
		System.out.println(cascadeElevator.lowerLimit.get() + "   " + cascadeElevator.upperLimit.get());*/
	}
}
