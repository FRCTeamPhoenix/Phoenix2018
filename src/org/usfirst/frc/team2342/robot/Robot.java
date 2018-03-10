package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.automodes.AutoGroup;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	PCMHandler PCM;
	WPI_TalonSRX talonFR;
	WPI_TalonSRX talonFL;
	WPI_TalonSRX talonBR;
	WPI_TalonSRX talonBL;
	WPI_TalonSRX talonCascade;
	WPI_TalonSRX talonIntakeRight;
	WPI_TalonSRX talonIntakeLeft;
	WPI_TalonSRX talonTip;
	Solenoid solenoidManipulator;
	WestCoastTankDrive westCoast;
	Joystick taranis;
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
		taranis = new Joystick(0);
		PCM = new PCMHandler(11);
		talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
		talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
		talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
		talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
		talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
		talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
		talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
		talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
		solenoidManipulator = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
		westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		XBOX = new Joystick(1);
		cascadeElevator = new CascadeElevator(talonCascade);
		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, solenoidManipulator);
		talonPID = new PIDGains();
		
		//		Camera indexes
		//		int[] indexes = {0, 1, 2};
		//
		//		//Start up cameras
		//		CameraControl cameras = new CameraControl(indexes, 640, 480);
		
		// initialize PID gains for west coast drive
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
		
		//Start up cameras
		@SuppressWarnings("unused")
		CameraControl cameras = new CameraControl(640, 480, 30);
	}

	public void teleopInit() {
		westCoast.debug = false;
		
		//initialize talons to brake mode and enable compressor
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		PCM.turnOn();

		//add joystick drive to the command queue
		Scheduler.getInstance().add(new DriveGamepad(taranis, westCoast));
		
		westCoast.setGyroControl(false);
		this.updatePID();

		talonTip.setSelectedSensorPosition(0, 0, 10);
	}

	public void teleopPeriodic() {
		//uncomment to load PID values from network tables/sliders
		//this.updatePID();
		
		//shift gears based on taranis input
		if (taranis.getRawButton(Constants.LOGITECH_LEFTBUMPER))
			westCoast.setLowGear();
		else if (taranis.getRawButton(Constants.LOGITECH_RIGHTBUMPER))
			westCoast.setHighGear();
		else
			westCoast.setNoGear();
		
		//pitch box manipulator according to XBOX input.
		//careful: hard talon limits are currently the only stop
		if(Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS)) > 0.1) {
			double speed = XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
			if(speed < 0)
				speed /= 2;
			else
				speed /= 7;
			talonTip.set(ControlMode.PercentOutput, speed);
		}
		else
			talonTip.set(ControlMode.PercentOutput, 0);
		
		//control cascade according to joystick input
		if (Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS)) > Constants.CASCADE_DEADZONE) {
			double s = XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			double max = s < 0 ? 1200 : 1000;
			cascadeElevator.setVelocity(s * max);
		}
		//control cascade from preset positions
		else if(XBOX.getRawButton(Constants.XBOX_X))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_B))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_Y))
			edu.wpi.first.wpilibj.command.Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
		else if(!cascadeElevator.isRunningPreset()) {
			//System.out.println("stopping it");
			cascadeElevator.setVelocity(0);
		}
		
		if(XBOX.getRawButton(Constants.XBOX_A)) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 1);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -1);
		}
		
		//dont hit these unless you know what youre doing.
		if(XBOX.getRawButton(7))
			talonCascade.setSelectedSensorPosition(Constants.UPPER_SENSOR_POSITION, 0, 10);
		else if(XBOX.getRawButton(8))
			talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION, 0, 10);
		
		//open and close box manipulator
		/*if(XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) || XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER))
			solenoidManipulator.set(true);
		else// if(XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER))
			solenoidManipulator.set(false);*/
		
		boolean solenoidButtons = taranis.getRawButton(7) || XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) || XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER);
		if(solenoidManipulator.get() != solenoidButtons)
			solenoidManipulator.set(solenoidButtons);
		
		//run intake wheels
		if(Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER)) > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0.5);
		}
		else if(Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER)) > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -0.5);
		} else {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
		}
		
		//run all queued actions
		Scheduler.getInstance().run();
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public void disabledInit() {
		//upon disabling, kill west coast and remove commands from Scheduler
		//westCoast.setVelocity(0.0d, 0.0d);
		//westCoast.zeroSensors();
		Scheduler.getInstance().removeAll();
	}

	public void autonomousInit() {
		/*talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);
		westCoast.updateGyroPID();
		System.out.println(westCoast.pidc.getP() + "   " + westCoast.pidc.getI() + "   " + westCoast.pidc.getD());
		FMS.init();
		
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
    	}*/
		/*Scheduler.getInstance().add(new DriveDistance(westCoast, 7));
		Scheduler.getInstance().add(new CascadePosition(cascadeElevator, 2*Constants.CASCADE_SWITCH, XBOX));
		Scheduler.getInstance().add(new PushBox(boxManipulator, XBOX));*/
		//Scheduler.getInstance().add(new TurnAngle(90, Constants.WESTCOAST_MAX_SPEED, westCoast));
		//Scheduler.getInstance().add(new AutoGroup(westCoast, cascadeElevator, XBOX));
		//boolean switchLeft = DriverStation.getInstance().getGameSpecificMessage().startsWith("L");
		//SmartDashboard.putString("DB/String 7", "Switch: " + DriverStation.getInstance().getGameSpecificMessage().substring(0, 1));
		Scheduler.getInstance().add(new AutoGroup(westCoast, cascadeElevator, boxManipulator, XBOX, SmartDashboard.getBoolean("DB/Button 1", false)));
	}

	public void autonomousPeriodic(){
		//this.updatePID();
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
		SmartDashboard.putString("DB/String 0", "Gyro: " + westCoast.pidc.getCurAngle());
		SmartDashboard.putString("DB/String 1", "Distance avg: " + westCoast.dpidc.getPositionAverage());
		SmartDashboard.putString("DB/String 2", "Gyro correction: " + westCoast.pidc.getCorrection());
		SmartDashboard.putString("DB/String 3", "Distance correction: " + westCoast.dpidc.getCorrection());
		
		try {
			Thread.sleep(25);
		} catch (InterruptedException e1) {
			//e1.printStackTrace();
		}
		
	}

	@Override
	public void testInit() {
		/*System.out.println("TEST MODE INIT");
		this.speed = SmartDashboard.getNumber("DB/Slider 3", 0);
		
		talonCascade.set(ControlMode.PercentOutput, XBOX.getRawAxis(3));
		westCoast.debug = true;
		this.updatePID();*/
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
		/*try {
			this.updatePID();
			Scheduler.getInstance().run();
			Thread.sleep(10);
		} catch(Exception e) {
			//DONOTHING
		}*/
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
		//System.out.println(talonTip.getSensorCollection().isFwdLimitSwitchClosed() + "    " + talonTip.getSensorCollection().isRevLimitSwitchClosed());
		cascadeElevator.setVelocity(0);
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
