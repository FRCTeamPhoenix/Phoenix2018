package org.usfirst.frc.team2342.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.usfirst.frc.team2342.PIDLoops.DistancePIDController;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.json.GyroPIDJson;
import org.usfirst.frc.team2342.json.GyroReader;
import org.usfirst.frc.team2342.json.JsonHandler;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.json.TalonReader;
import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.Climber;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
//import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.FMS;
import org.usfirst.frc.team2342.util.TalonNetworkTableController;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	Joystick gamepad;
	PCMHandler PCM;
	WPI_TalonSRX talonFR;
	WPI_TalonSRX talonFL;
	WPI_TalonSRX talonBR;
	WPI_TalonSRX talonBL;
	WPI_TalonSRX talonCascade;
	WPI_TalonSRX talonIntakeRight;
	WPI_TalonSRX talonIntakeLeft;
	WPI_TalonSRX talonTip;
	WPI_TalonSRX talonWinch;

	DistancePIDController pc;
	
	TankDrive tankDrive;
	//WestCoastTankDrive westCoast;
	Joystick joystickR;
	Joystick XBOX;
	CascadeElevator cascadeElevator;
	BoxManipulator boxManipulator;
	PIDGains talonPID;
	double speed = 0.0d;
	double tangle = 0.0d;
	UsbCamera camera0;
	UsbCamera camera1;
	VideoSink server;
	Climber climber;
	
	GyroPIDJson gpidjson;

	boolean intakeLowVoltage = false;
	boolean pressed8 = false;
	JsonHandler json;
	TalonReader treader;
	GyroReader greader;
	
	PrintStream output;
	PrintStream output2;
	SendableChooser<Command> autoChooser;
	Scanner input;
	Scanner input2;
	//0 = left
	//1 = mid
	//2 = right
	int autonomous_position = 0;

	public Robot() {
		
		Gyro.init();
		gamepad = new Joystick(0);
		PCM = new PCMHandler(11);
		talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
		talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
		talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
		talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
		talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
		talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
		talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
		talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
		tankDrive = new TankDrive(PCM,talonFL,talonFR,talonBL,talonBR);
		talonWinch = new WPI_TalonSRX(10);
		joystickR = new Joystick(2);
		XBOX = new Joystick(1);
		cascadeElevator = new CascadeElevator(talonCascade);
		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, PCM);
		talonPID = new PIDGains();
		climber = new Climber(talonWinch);
		//camera0 = CameraServer.getInstance().startAutomaticCapture(0);
		//camera1 = CameraServer.getInstance().startAutomaticCapture(1);
		//server = CameraServer.getInstance().getServer();
		//server.setSource(camera0);

		// set TalonPid
		talonPID.p     = Constants.dtKp;
		talonPID.i     = Constants.dtKi;
		talonPID.d     = Constants.dtKd;
		talonPID.ff    = Constants.dtKff;
		talonPID.rr    = Constants.dtKrr;
		talonPID.izone = Constants.dtKizone;
		//westCoast.updateTalonPID(0, talonPID);
		pc = new DistancePIDController();
		pc.init(talonPID.p, talonPID.i, talonPID.d, talonPID.ff, talonFL, talonFR);
		gpidjson = new GyroPIDJson();
		JsonHandler.readJson("gyropidr.json", gpidjson);
	}

	@Override
	public void robotInit() {
		final String defaultAuto = "Drive Forward";
		final String switchAuto = "Switch Auto";
		final String scaleAuto = "Left Scale Forward";
		String[] autoList = {defaultAuto, switchAuto, scaleAuto, "Test Auto", "Record",};
		
/*		NetworkTable table = NetworkTable.getTable("SmartDasboard");
		table.putStringArray();*/
		SmartDashboard.putStringArray("Auto List", autoList);
		
		if(!cascadeElevator.lowerLimit.get())
			cascadeElevator.zeroSensors();

		//Start up cameras
		@SuppressWarnings("unused")
		CameraControl cameras = new CameraControl(640, 480, 15);
		cascadeElevator.lastPosition = 0;
		
		updatePID();
		
		/*
		autoChooser = new SendableChooser<Command>();
		autoChooser.addDefault("Left Side Auto", new LeftSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		autoChooser.addObject("Right Side Auto", new RightSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		autoChooser.addObject("Center Auto", new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		SmartDashboard.putData("Select Autonomous Mode", autoChooser);
		*/
		
		//Gyro.init();
	
	}

	public void teleopInit() {
		talonPID.p     = Constants.dtKp;
		talonPID.i     = Constants.dtKi;
		talonPID.d     = Constants.dtKd;
		talonPID.ff    = Constants.dtKff;
		talonPID.rr    = 0;
		talonPID.izone = Constants.dtKizone;
		//westCoast.updateTalonPID(0, talonPID);
		System.out.println("TELEOP MODE INIT");
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, tankDrive);
		Scheduler.getInstance().add(driveJoystick);
		//////westCoast.setGyroControl(false);
		this.updatePID();
		
		//westCoast.debug = true;

		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonTip.setSelectedSensorPosition(0, 0, 10);
		
		if (!SmartDashboard.getString("DB/String 0", "").equals("")) {
			try {
				output = new PrintStream(new File("/home/lvuser/autos/" + SmartDashboard.getString("DB/String 0", "")));
				output2 = new PrintStream(new File("/home/lvuser/autos/" + SmartDashboard.getString("DB/String 0", "") + "A"));
				System.out.println("ddddddddd" + SmartDashboard.getString("DB/String 0", ""));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//cascadeElevator.lastPosition = 0;
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		long time = System.currentTimeMillis();
		//Drive with joystick control in velocity mode
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		if (gamepad.getRawButton(Constants.LOGITECH_LEFTBUMPER))
			tankDrive.setLowGear();
		else if (gamepad.getRawButton(Constants.LOGITECH_RIGHTBUMPER))
			tankDrive.setHighGear();
		else
			tankDrive.setNoGear();

		boolean p = XBOX.getRawButton(8);
		if(p && !pressed8) {
			intakeLowVoltage = !intakeLowVoltage;
			pressed8 = p;
		} else if(!p && pressed8)
			pressed8 = p;

		if(Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS)) > 0.1) {
			double speed = XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
			if(speed < 0)
				speed /= 10;
			talonTip.set(ControlMode.PercentOutput, -speed);
		}
		else
			talonTip.set(ControlMode.PercentOutput, 0);

		if (Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS)) > Constants.CASCADE_DEADZONE) {
			double s = XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			double max = s < 0 ? 1200 : 600;

			cascadeElevator.setVelocity(s * max);
			cascadeElevator.lastPosition = cascadeElevator.talonCascade.getSelectedSensorPosition(0);
			//System.out.println("last position = " + cascadeElevator.lastPosition);
		}
		else if(XBOX.getRawButton(Constants.XBOX_A))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_B))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_X))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_Y))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
		else if(XBOX.getRawButton(9))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_6INCH, XBOX));
		else if(!cascadeElevator.runningPreset) {
			if(Math.abs(cascadeElevator.talonCascade.getSelectedSensorPosition(0)) > 100 && !cascadeElevator.lowerLimit.get()) {
				cascadeElevator.talonCascade.selectProfileSlot(1, 0);
				cascadeElevator.talonCascade.set(ControlMode.Position, cascadeElevator.lastPosition);
				//cascadeElevator.setVelocity(0);
				//System.out.println("last position = " + cascadeElevator.lastPosition + " actual position = " + cascadeElevator.talonCascade.getSelectedSensorPosition(0));
			}
		}
		//System.out.println("lower: " + cascadeElevator.lowerLimit.get() + " upper: " + cascadeElevator.upperLimit.get());

	
		
//		if (Math.abs(gamepad.getRawAxis(3)) > 0.1) {
//			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, gamepad.getRawAxis(3));
//			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -gamepad.getRawAxis(3));
//		}

		if(XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) || XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER) || gamepad.getRawAxis(2) > 0.8 || gamepad.getRawAxis(3) > 0.8)
			boxManipulator.closeManipulator();
		else
			boxManipulator.openManipulator();
		
		
		if(XBOX.getPOV() == Constants.XBOX_DPAD_UP)
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BAR_OVER, XBOX));
		else if(XBOX.getPOV() == Constants.XBOX_DPAD_RIGHT)
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BAR_HOOK, XBOX));
		else if(XBOX.getPOV() == Constants.XBOX_DPAD_DOWN) 
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));

		double triggerL = XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER);
		double triggerR = XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER);

		if(triggerL > 0.9) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL);
		}
		if(triggerL > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL / 2);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL / 2);
		}
		else if(triggerR > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -triggerR * triggerR / 2);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, triggerR * triggerR / 2);
		}
		else if(intakeLowVoltage) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -0.1);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0.1);
		} else {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
		}
		
		try {
			Thread.sleep(2);
		} catch(Exception e) { }
		if (!SmartDashboard.getString("DB/String 0", "").equals("")) {
//			output.print(gamepad.getRawAxis(1) + " " );
//			output.print(gamepad.getRawAxis(5) + " ");
//			output.print(XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS) + " ");
//			output.print(XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER) + " ");
//			output.print(XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER) + " ");
//			/*output.print(gamepad.getRawButton(Constants.LOGITECH_LEFTBUMPER) + " ");
//			output.print(gamepad.getRawButton(Constants.LOGITECH_RIGHTBUMPER) + " ");*/
//			
//			output2.print(XBOX.getRawButton(Constants.XBOX_A) + " ");
//			output2.print(XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) + " ");
//			output2.print(XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER) + " ");
//			//output.print(gamepad.getRawAxis(2) + " ");
//			//output.print(gamepad.getRawAxis(3) + " ");
//			/*output.print(XBOX.getPOV() + " ");*/
//			output.println(); output2.println();
			output.print(talonFL.getSelectedSensorVelocity(0) + " ");
			output.print(talonFR.getSelectedSensorVelocity(0) + " ");
			output.println();
		}
		
		try {
			Thread.sleep(Math.max(0, 100 - (System.currentTimeMillis() - time)));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
		
		
		
			
		
		
		
	}

	public void disabledInit() {
		//westCoast.setVelocity(0.0d, 0.0d);
		//westCoast.zeroSensors();
		Scheduler.getInstance().removeAll();
		
		/*File folder = new File("home/lvuser/autos/");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }*/
	}

	public void autonomousInit() {
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);
		// set TalonPid
		talonPID.p     = Constants.dtKp;
		talonPID.i     = Constants.dtKi;
		talonPID.d     = Constants.dtKd;
		talonPID.ff    = Constants.dtKff;
		talonPID.rr    = Constants.dtKrr;
		talonPID.izone = Constants.dtKizone;
		//westCoast.updateTalonPID(0, talonPID);
		//westCoast.updateTalonPID(0, talonPID);
		System.out.println("AUTOMODE INIT");
		JsonHandler.readJson("gyropidr.json", gpidjson);
		System.out.println(String.valueOf(gpidjson.gyroPid.p));
		FMS.init();
		TalonNetworkTableController.pushTalon(talonFL);
		TalonNetworkTableController.pushTalon(talonFR);
		
		/*
		String AutonomousMode;
		AutonomousMode = SmartDashboard.getString("Auto Selector", "");
		if (AutonomousMode.equals("Drive Forward")) 
			Scheduler.getInstance().add(new DriveDistance2(tankDrive, 10));
		else if (AutonomousMode.equals("Switch Auto"))
			Scheduler.getInstance().add(new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Left Scale Forward"))
			Scheduler.getInstance().add(new LeftSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if(AutonomousMode.equals("Test Auto"))
			//Scheduler.getInstance().add(new MiddleAutoCorrected();
			Scheduler.getInstance().add(new MiddleAutoCorrected(tankDrive, cascadeElevator, boxManipulator, gamepad));*/
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		input = null;
		if (!(new File("/home/lvuser/autos/" + SmartDashboard.getString("DB/String 1", "")).exists())) {
			System.out.print("File doesnt exist");
			
		} else {
			
			try {
				input = new Scanner(new File("/home/lvuser/autos/" + SmartDashboard.getString("DB/String 1", "")));
				input2 = new Scanner(new File("/home/lvuser/autos/" + SmartDashboard.getString("DB/String 1", "") + "A"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				

		updatePID();
		//tankDrive.updateGyroPID(gpidjson.gyroPid);
		//GyroPIDController.setP(0.02);
	//	GyroPIDController.setI(0);
		//GyroPIDController.setD(0);
		//tankDrive.debug = true;
		
		//Scheduler.getInstance().add(new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		//Scheduler.getInstance().add(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, 90, tankDrive));
		/*if(SmartDashboard.getBoolean("DB/Button 1", false))
			Scheduler.getInstance().add(new LeftSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if(SmartDashboard.getBoolean("DB/Button 2", false)) {
			Scheduler.getInstance().add(new DriveDistance2(tankDrive, 23));
		}
		else
			Scheduler.getInstance().add(new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));*/
		//Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, gamepad));
		//tankDrive.setGyroControl(false);
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		//this.updatePID();
		//Gyro.update();
		//Scheduler.getInstance().run();
		long time = System.currentTimeMillis();
		try {
			Thread.sleep(2);
		} catch(Exception e) {}
		if(input != null && input.hasNext()) {
			double left = input.nextDouble();
			double right = input.nextDouble();
			talonFL.set(ControlMode.Velocity, left);
			talonFR.set(ControlMode.Velocity, right);
			TalonNWT.updateTalon(talonFL, left);
			TalonNWT.updateTalon(talonFR, right);
		}
//		if (input != null && input.hasNext()) {
//			
//			double left = input.nextDouble();
//			double right = input.nextDouble();
//			if (Math.abs(left)  < Constants.JOYSTICK_DEADZONE)
//				left = 0;
//			if (Math.abs(right) < Constants.JOYSTICK_DEADZONE)
//				right = 0;
//			tankDrive.setPercentage(-left, -right);
//				
//			/*if (input.nextBoolean())
//				tankDrive.setLowGear();
//			else if (input.nextBoolean())
//				tankDrive.setHighGear();
//			else
//				tankDrive.setNoGear();
//
//			
//			double i = input.nextDouble();
//			if(Math.abs(i) > 0.1) {
//				double speed = i;
//				if(speed < 0)
//					speed /= 10;
//				talonTip.set(ControlMode.PercentOutput, -speed);
//			}
//			else
//				talonTip.set(ControlMode.PercentOutput, 0);*/
//
//			double i = input.nextDouble();
//			if (Math.abs(i) > Constants.CASCADE_DEADZONE) {
//				double s = i;
//				double max = s < 0 ? 1200 : 600;
//
//				cascadeElevator.setVelocity(s * max);
//				cascadeElevator.lastPosition = cascadeElevator.talonCascade.getSelectedSensorPosition(0);
//				//System.out.println("last position = " + cascadeElevator.lastPosition);
//			}
//			else if(input2.nextBoolean())
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
//			/*else if(input.nextBoolean())
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
//			else if(input.nextBoolean())
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
//			else if(input.nextBoolean())
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
//			else if(input.nextBoolean())
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_6INCH, XBOX));*/
//			else if(!cascadeElevator.runningPreset) {
//				if(Math.abs(cascadeElevator.talonCascade.getSelectedSensorPosition(0)) > 100 && !cascadeElevator.lowerLimit.get()) {
//					cascadeElevator.talonCascade.selectProfileSlot(1, 0);
//					cascadeElevator.talonCascade.set(ControlMode.Position, cascadeElevator.lastPosition);
//					//cascadeElevator.setVelocity(0);
//					//System.out.println("last position = " + cascadeElevator.lastPosition + " actual position = " + cascadeElevator.talonCascade.getSelectedSensorPosition(0));
//				}
//			}
//			//System.out.println("lower: " + cascadeElevator.lowerLimit.get() + " upper: " + cascadeElevator.upperLimit.get());
//
//			
////			if (Math.abs(gamepad.getRawAxis(3)) > 0.1) {
////				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, gamepad.getRawAxis(3));
////				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -gamepad.getRawAxis(3));
////			}
//			
//			boolean a = input2.nextBoolean();
//			boolean b = input2.nextBoolean();
//			if(a || b)
//				boxManipulator.closeManipulator();
//			else
//				boxManipulator.openManipulator();
//			
//			/*
//			
//			i = input.nextDouble();
//			if(i == Constants.XBOX_DPAD_UP)
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BAR_OVER, XBOX));
//			else if(i == Constants.XBOX_DPAD_RIGHT)
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BAR_HOOK, XBOX));
//			else if(i == Constants.XBOX_DPAD_DOWN) 
//				Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
//			*/
//			double triggerL = input.nextDouble();
//			double triggerR = input.nextDouble();
//
//			if(triggerL > 0.9) {
//				boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL);
//				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL);
//			}
//			if(triggerL > 0.1) {
//				boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL / 2);
//				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL / 2);
//			}
//			else if(triggerR > 0.1) {
//				boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -triggerR * triggerR / 2);
//				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, triggerR * triggerR / 2);
//			}
//			else {
//				boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
//				boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
//			}
//			
//		} else if (input != null &&!input.hasNext()) {
//			System.out.println("End of File");
//		}
//
//		
//		//TalonNWT.updateGyroPID();
//		/*TalonNWT.updateTalon(talonFR);
//		TalonNWT.updateTalon(talonFL);
//		TalonNWT.updateTalon(talonBR);
//		TalonNWT.updateTalon(talonBL);*/
//		
//		try { Thread.sleep(2); }
//		catch (Exception e) { }
//		
//		//System.out.println("left @ " + talonFL.getSelectedSensorPosition(0) + " right @ " + talonFR.getSelectedSensorPosition(0));
//
//		/*if(!cascadeElevator.runningPreset) {
//			if(Math.abs(cascadeElevator.talonCascade.getSelectedSensorPosition(0)) > 100 && !cascadeElevator.lowerLimit.get()) {
//				cascadeElevator.talonCascade.selectProfileSlot(1, 0);
//				cascadeElevator.talonCascade.set(ControlMode.Position, cascadeElevator.lastPosition);
//			}
//			//System.out.println("setting 0 no preset");
//		}*/
//
//		//System.out.println(tankDrive.leftA.getSelectedSensorPosition(0));
		else {
			talonFR.set(ControlMode.PercentOutput, 0);
			talonFL.set(ControlMode.PercentOutput, 0);
			TalonNWT.updateTalon(talonFL, 0);
			TalonNWT.updateTalon(talonFR, 0);
		}
		try {
			Thread.sleep(Math.max(0, 100 - (System.currentTimeMillis() - time)));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
		updatePID();
		Gyro.reset();
		//talonCascade.set(ControlMode.PercentOutput, XBOX.getRawAxis(3));
		//tankDrive.debug = true;
		//this.updatePID(gpidjson.gyroPid);
		//tankDrive.updateGyroPID(gpidjson.gyroPid);
		System.out.println("going " + talonFR.getSelectedSensorVelocity(0) + " " + talonFL.getSelectedSensorVelocity(0));
		//Scheduler.getInstance().add(new DriveDistance2(tankDrive, 2));
		//this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
	}

	@Override
	public void testPeriodic() {
		try {
			Gyro.update();
			//System.out.println("going " + talonFR.getSelectedSensorVelocity(0) + " " + talonFL.getSelectedSensorVelocity(0));
			//Scheduler.getInstance().run();
			//tankDrive.setVelocity(Constants.WESTCOAST_HALF_SPEED, Constants.WESTCOAST_HALF_SPEED);
			Scheduler.getInstance().run();
			System.out.println("Gyro angle:   " + Gyro.angle());
			Thread.sleep(10);
		} catch(Exception e) {
			//DONOTHING
		}
	}

	// updates the PID in gyro with the sliders or the networktables.
	public void updatePID() {
		PIDGains p = new PIDGains();
		//TalonNWT.populateGyroPID(this.pidc);
		p.p = 0.15; //SmartDashboard.getNumber("DB/Slider 0", 0);
		p.i = 0.0015; //SmartDashboard.getNumber("DB/Slider 1", 0);
		p.d = 0; //SmartDashboard.getNumber("DB/Slider 2", 0);
		p.ff = 0.353; //SmartDashboard.getNumber("DB/Slider 3", 0);
		tankDrive.setPid(p);
		//westCoast.updateTalonPID(0, talonPID);
		SmartDashboard.putString("DB/String 6", String.valueOf(p.p));
		SmartDashboard.putString("DB/String 7", String.valueOf(p.i));
		SmartDashboard.putString("DB/String 8", String.valueOf(p.d));
		SmartDashboard.putString("DB/String 9", String.valueOf(p.ff));
	}

}
