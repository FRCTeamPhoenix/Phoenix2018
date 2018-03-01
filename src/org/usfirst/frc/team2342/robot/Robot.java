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
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.FMS;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
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
	PCMHandler PCM;
	WPI_TalonSRX talonFR;
	WPI_TalonSRX talonFL;
	WPI_TalonSRX talonBR;
	WPI_TalonSRX talonBL;
	WPI_TalonSRX talonCascade;
	WPI_TalonSRX talonIntakeRight;
	WPI_TalonSRX talonIntakeLeft;
	WPI_TalonSRX talonTip;
	//Solenoid solenoidLowGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_LOWGEAR);
	//Solenoid solenoidHighGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_HIGHGEAR);
	Solenoid solenoid1;
	WestCoastTankDrive westCoast;
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

	public Robot() {
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
		//Solenoid solenoidLowGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_LOWGEAR);
		//Solenoid solenoidHighGear = new Solenoid(Constants.PCM_CAN_ID ,Constants.PCM_SLOT_HIGHGEAR);
		solenoid1 = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
		westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		joystickR = new Joystick(2);
		XBOX = new Joystick(1);
		cascadeElevator = new CascadeElevator(talonCascade);
		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, solenoid1);
		talonPID = new PIDGains();
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
		westCoast.updateTalonPID(0, talonPID);
	}

	@Override
	public void robotInit() {
		if(!cascadeElevator.lowerLimit.get())
			cascadeElevator.zeroSensors();
	}

	public void teleopInit() {
		westCoast.debug = false;
		talonPID.p     = Constants.dtKp;
		talonPID.i     = Constants.dtKi;
		talonPID.d     = Constants.dtKd;
		talonPID.ff    = Constants.dtKff;
		talonPID.rr    = 0;
		talonPID.izone = Constants.dtKizone;
		westCoast.updateTalonPID(0, talonPID);
		System.out.println("TELEOP MODE INIT");
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, westCoast);
		edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		this.updatePID();
		//		//Camera indexes
		//		int[] indexes = {0, 1, 2};
		//
		//		//Start up cameras
		//		CameraControl cameras = new CameraControl(indexes, 640, 480);

		//westCoast.debug = true;

		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonTip.setSelectedSensorPosition(0, 0, 10);
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
		edu.wpi.first.wpilibj.command.Scheduler.getInstance().run();
		//Drive with joystick control in velocity mode
		//westCoast.outputToSmartDashboard();
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		
		if(gamepad.getRawButton(1))
			server.setSource(camera0);
		if(gamepad.getRawButton(2))
			server.setSource(camera1);
		
		if (gamepad.getRawButton(Constants.LOGITECH_LEFTBUMPER))
			westCoast.setLowGear();
		else if (gamepad.getRawButton(Constants.LOGITECH_RIGHTBUMPER))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		
		if(Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS)) > 0.1) {
			double speed = XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
			if(speed < 0)
				speed /= 10;
			talonTip.set(ControlMode.PercentOutput, -XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS));
		}
		else
			talonTip.set(ControlMode.PercentOutput, 0);
		
		if (Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS)) > Constants.CASCADE_DEADZONE) {
			double s = XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			double max = s < 0 ? 600 : 400;
			System.out.println(s);
			
			cascadeElevator.setVelocity(s * max);
		}
		else if(XBOX.getRawButton(Constants.XBOX_A))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_X))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_B))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_Y))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
		else
			cascadeElevator.setVelocity(0);
		
		if(XBOX.getRawButton(Constants.XBOX_LEFTBUMPER))
			solenoid1.set(true);
		else if(XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER))
			solenoid1.set(false);
		
		if(Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER)) > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -0.5);
		}
		else if(Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER)) > 0.1) {
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
		edu.wpi.first.wpilibj.command.Scheduler.getInstance().removeAll();
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
		westCoast.updateTalonPID(0, talonPID);
		System.out.println("AUTOMODE INIT");
		//Command goForward = new DriveForward(20, westCoast, 6.0 * Constants.TALON_SPEED_RPS);
		//Scheduler.getInstance().add(goForward);
		//westCoast.goArc(8, 90, 0.425, 0.5, false);
		//DriveDistance driveDistance = new DriveDistance(westCoast, 8);
		//Scheduler.getInstance().add(driveDistance);
//		westCoast.goArc(4, 90, -1.0d, -1.0d, false);
		//westCoast.updatePID();
		FMS.init();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	//calculate auto mode
    	switch(FMS.getPosition()){
    	case 1:
    		if(FMS.scale()){
    			System.out.println("1;1");
    			//go for scale left side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
    			Scheduler.getInstance().add(new leftscaleleftside(westCoast));
    		}else if(FMS.teamSwitch()){
    			System.out.println("1;2");
    			//go for switch on left side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
    			Scheduler.getInstance().add(new leftswitchleft(westCoast));
    		}else{
    			System.out.println("1;3");
    			//go for switch on right side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
    			Scheduler.getInstance().add(new leftscalerightside(westCoast));
    		}
    		break;
    	case 2:
    		if(FMS.teamSwitch()){
    			System.out.println("2;1");
        		//middle to left side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
    			Scheduler.getInstance().add(new middleleftside(westCoast));
        	}else{
        		System.out.println("2;2");
        		//middle to right side
        		//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
        		Scheduler.getInstance().add(new middlerightside(westCoast));
        	}
    		break;
    	case 3:
    		if(!FMS.scale()){
    			System.out.println("3;1");
    			//go for scale right side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
    			Scheduler.getInstance().add(new rightscaleright(westCoast));
    		}else if(!FMS.teamSwitch()){
    			System.out.println("3;2");
    			//go for switch on right side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
    			Scheduler.getInstance().add(new rightswitchright(westCoast));
    		}else{
    			System.out.println("3;3");
    			//go for switch on left side
    			//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator,  Constants.CASCADE_UPPER_SCALE, XBOX));
    			Scheduler.getInstance().add(new rightscaleleft(westCoast));
    		}
    		break;
		default:
			System.out.println("Default called!");
			//just drive forward 10 ft if a glitch occurs
			Scheduler.getInstance().add(new DriveDistance(westCoast, 10));
			break;
    	}
		//Command cascadeGo = new CascadePosition(cascadeElevator, 35);
		//Scheduler.getInstance().add(cascadeGo);
		//edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new rightswitchright(westCoast));
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//westCoast.setGyroControl(true);
		//westCoast.pidc.gyroReset();
		//westCoast.zeroSensors();
		//westCoast.debug = false;
		//westCoast.turnSet(-90.0d);
		//this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		westCoast.debug = false;
		//westCoast.updateGyroPID();
		//double vel = -300 * speed;
		//westCoast.setVelocity(vel, vel);
		this.updatePID();
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		/*westCoast.arcLoop(false);
		//Scheduler.getInstance().run();
		PCM.compressorRegulate();*/
		this.updatePID();
		/*try {
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

		}*/
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		
		talonCascade.set(ControlMode.PercentOutput, XBOX.getRawAxis(3));
		westCoast.debug = true;
		this.updatePID();
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
			talonCascade.set(ControlMode.PercentOutput, XBOX.getRawAxis(3));
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
