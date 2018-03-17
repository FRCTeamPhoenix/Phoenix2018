package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.automodes.SwitchAuto;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.commands.DriveVoltageTime;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.FMS;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
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
	
	TankDrive tankDrive;
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
		tankDrive = new TankDrive(PCM,talonFL,talonFR,talonBL,talonBR);
		westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		joystickR = new Joystick(2);
		XBOX = new Joystick(1);
		cascadeElevator = new CascadeElevator(talonCascade);
		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, PCM);
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
		
		//Start up cameras
		CameraControl cameras = new CameraControl(640, 480, 15);
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
		Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		this.updatePID();
		

		//westCoast.debug = true;

		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonTip.setSelectedSensorPosition(0, 0, 10);
	}

	public void teleopPeriodic() {
		
		//this.updatePID();
		Scheduler.getInstance().run();
		
		//Drive with joystick control in velocity mode
		//Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
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
			double max = s < 0 ? 800 : 600;
			
			cascadeElevator.setVelocity(s * max);
		}
		else if(XBOX.getRawButton(Constants.XBOX_A))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_B))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_X))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
		else if(XBOX.getRawButton(Constants.XBOX_Y))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
		else if(!cascadeElevator.runningPreset) {
			cascadeElevator.setVelocity(0);
			//System.out.println("setting 0 no preset");
		}
			
		
		if(XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) || XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER) || gamepad.getRawButton(7))
			boxManipulator.closeManipulator();
		else
			boxManipulator.openManipulator();
		
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
		} else {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
		}
	}

	public void disabledInit() {
		westCoast.setVelocity(0.0d, 0.0d);
		westCoast.zeroSensors();
		Scheduler.getInstance().removeAll();
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
		FMS.init();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Scheduler.getInstance().add(new SwitchAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
    	//calculate auto mode
    	/*switch(FMS.getPosition()){

    	switch(FMS.getPosition()){
>>>>>>> refs/remotes/origin/turn90
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
    		break;
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
    		break;
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
    		break;
		default:
			System.out.println("Default called!");
			//just drive forward 10 ft if a glitch occurs
			Scheduler.getInstance().add(new DriveDistance(westCoast, 10));
			break;
    	}
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//Scheduler.getInstance().add(new DriveVoltageTime(tankDrive,2000,0.5));
		westCoast.debug = false;

		this.updatePID();
		//TalonNWT.updateGyroPID(westCoast.pidc);
	}

	public void autonomousPeriodic(){
		this.updatePID();
		
		Scheduler.getInstance().run();
		
		try { Thread.sleep(25); }
		catch (Exception e) { }
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
		try {
			this.updatePID();
			Scheduler.getInstance().run();
			Thread.sleep(10);
		} catch(Exception e) {
			//DONOTHING
		}
		
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
