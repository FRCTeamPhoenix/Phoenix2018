package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.commands.PullBox;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.json.PIDGains;

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

	Joystick gamepad;
	Joystick joystickR;
	Joystick joystickL;
	PCMHandler PCM;
	WPI_TalonSRX talonFR;
	WPI_TalonSRX talonFL;
	WPI_TalonSRX talonBR;
	WPI_TalonSRX talonBL;
	WPI_TalonSRX talonCascade;
	WPI_TalonSRX talonIntakeRight;
	WPI_TalonSRX talonIntakeLeft;
	WPI_TalonSRX talonTip;
	//Solenoid solenoidLowGear;
	//Solenoid solenoidHighGear;
	Solenoid solenoid1;
	WestCoastTankDrive westCoast;
	CascadeElevator cascadeElevator;
	BoxManipulator boxManipulator;
	PIDGains talonPID = new PIDGains();
	double speed = 0.0d;
	double tangle = 0.0d;

	public Robot() {
		this.gamepad = new Joystick(0);
		this.joystickR = new Joystick(2);
		this.joystickL = new Joystick(1);
		this.PCM = new PCMHandler(11);
		this.talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
		this.talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
		this.talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
		this.talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
		this.talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
		this.talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
		this.talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
		this.talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
		//this.solenoidLowGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_LOWGEAR);
		//this.solenoidHighGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_HIGHGEAR);
		this.solenoid1 = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
		this.westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		this.cascadeElevator = new CascadeElevator(talonCascade);
		this.boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, solenoid1);

		// set TalonPid
		talonPID.p     = Constants.dtKp;
		talonPID.i     = Constants.dtKi;
		talonPID.d     = Constants.dtKd;
		talonPID.ff    = Constants.dtKff;
		talonPID.rr    = Constants.dtKrr;
		talonPID.izone = Constants.dtKizone;
		westCoast.updateTalonPID(0, talonPID);
	}

	@Override
	public void robotInit() {
		if(!cascadeElevator.lowerLimit.get())
			cascadeElevator.zeroSensors();
	}

	public void teleopInit() {
		System.out.println("TELEOP MODE INIT");
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, westCoast);
		Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		this.updatePID();
		//		//Camera indexes
		//		int[] indexes = {0, 1, 2};
		//
		//		//Start up cameras
		//		CameraControl cameras = new CameraControl(indexes, 640, 480);

		//westCoast.debug = true;
	}

	public void teleopPeriodic() {
		//		if (gamepad.getRawButton(5)) {
		//			PullBox pullBox = new PullBox(boxManipulator, gamepad);
		//			Scheduler.getInstance().add(pullBox);
		//		}
		//		
		//		if (gamepad.getRawButton(6)) {
		//			PushBox pushBox = new PushBox(boxManipulator, gamepad);
		//			Scheduler.getInstance().add(pushBox);
		//		}
		this.updatePID();
		Scheduler.getInstance().run();
		//Drive with joystick control in velocity mode
		//westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		if (gamepad.getRawButton(7))
			westCoast.setLowGear();
		else if (gamepad.getRawButton(8))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		/*
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
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		westCoast.debug = true;
		//westCoast.updateGyroPID();
		double vel = -300 * speed;
		westCoast.setVelocity(vel, vel);
		this.updatePID();
		//		this.speed = Constants.tSpeed;
		//		Command tA = new TurnAngle(1000, -90, westCoast);
		//		System.out.println(String.valueOf(westCoast.pidc.getTargetAngle()));
		//		Scheduler.getInstance().add(tA);
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		/*westCoast.arcLoop(false);
		//Scheduler.getInstance().run();
		PCM.compressorRegulate();*/
		try {
			this.updatePID();
			double vel = -300 * speed;
			this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
			westCoast.setVelocity(vel, vel);
			//westCoast.updateGyroPID();
			//Scheduler.getInstance().run();
			//westCoast.updateGyroPID();

			//TalonNWT.updateGyroPID(westCoast.pidc);
			Thread.sleep(10);
		}
		catch (Exception e) {

		}
		//cascadeElevator.outputToSmartDashboard();
		//Scheduler.getInstance().add(new CascadePosition(cascadeElevator, 42, joystickL));*/
	}

	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		westCoast.debug = true;
		this.updatePID();
		//		this.speed = Constants.tSpeed;
		//		Command tA = new TurnAngle(-300 * speed, -90, westCoast);
		//		System.out.println(String.valueOf(westCoast.pidc.getTargetAngle()));
		//		Scheduler.getInstance().add(tA);
		//talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
		//talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));
	}

	@Override
	public void testPeriodic() {
		// Limit for the current velocity for the robot without cascade is 3000
		//talonCascade.set(ControlMode.PercentOutput, joystickL.getRawAxis(3));

		//		if (Math.abs(joystickL.getRawAxis(3)) > Constants.CASCADE_DEADZONE) {
		//			double s = joystickL.getRawAxis(3);
		//			if(s < 0) s /= 2;
		//			talonCascade.set(ControlMode.PercentOutput,s);
		//		}
		try {
			this.updatePID();
			Scheduler.getInstance().run();
			Thread.sleep(10);
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

	// updates the PID in gyro with the sliders or the networktables.
	public void updatePID() {
		//TalonNWT.populateGyroPID(this.pidc);
		this.talonPID.p = SmartDashboard.getNumber("DB/Slider 0", 0);
		this.talonPID.i = SmartDashboard.getNumber("DB/Slider 1", 0);
		//this.talonPID.d = SmartDashboard.getNumber("DB/Slider 2", 0);
		this.talonPID.ff = SmartDashboard.getNumber("DB/Slider 2", 0);
		westCoast.updateTalonPID(0, talonPID);
		SmartDashboard.putString("DB/String 6", String.valueOf(this.talonPID.p));
		SmartDashboard.putString("DB/String 7", String.valueOf(this.talonPID.i));
		SmartDashboard.putString("DB/String 8", String.valueOf(this.talonPID.d));
		SmartDashboard.putString("DB/String 9", String.valueOf(this.talonPID.ff));
	}
}
