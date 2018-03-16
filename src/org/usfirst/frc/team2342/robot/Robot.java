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
import org.usfirst.frc.team2342.json.*;
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
	JsonHandler json;
	TalonReader treader;

	public Robot() {
		//		gamepad = new Joystick(0);
		//		PCM = new PCMHandler(11);
		//		talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
		//		talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
		//		talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
		//		talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
		//		talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
		//		talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
		//		talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
		//		talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
		////		
		//		westCoast = new WestCoastTankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		//		joystickR = new Joystick(2);
		//		XBOX = new Joystick(1);
		//		cascadeElevator = new CascadeElevator(talonCascade);
		//		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, PCM);
		//		talonPID = new PIDGains();
		//camera0 = CameraServer.getInstance().startAutomaticCapture(0);
		//camera1 = CameraServer.getInstance().startAutomaticCapture(1);
		//server = CameraServer.getInstance().getServer();
		//server.setSource(camera0);

		// set TalonPid
		//		talonPID.p     = Constants.dtKp;
		//		talonPID.i     = Constants.dtKi;
		//		talonPID.d     = Constants.dtKd;
		//		talonPID.ff    = Constants.dtKff;
		//		talonPID.rr    = Constants.dtKrr;
		//		talonPID.izone = Constants.dtKizone;
		//		westCoast.updateTalonPID(0, talonPID);
	}

	@Override
	public void robotInit() {
		//		if(!cascadeElevator.lowerLimit.get())
		//			cascadeElevator.zeroSensors();

		//Start up cameras
		CameraControl cameras = new CameraControl(640, 480, 15);
	}

	public void teleopInit() {
		System.out.println("TELEOP MODE INIT");
	}

	public void teleopPeriodic() {
		//		DONOTHING
	}

	public void disabledInit() {
		//DONOTHING
	}

	public void autonomousInit() {
		json = new JsonHandler("/home/lvuser/config.json");
		treader = new TalonReader();
		try {
			if (json.getFileExisting()) {
				treader = json.read(treader.getClass());
			}
			else {
				json.write(treader);
			}
		}
		catch (Exception e) {
			//DONOTHING
		}
		Talon t = treader.talons.get(0);
		System.out.println(t.deviceNumber + "   " + t.maxForwardSpeed + "   " + t.maxReverseSpeed + "   " + t.mode);
	}

	public void autonomousPeriodic(){
		try {
			Talon t = treader.talons.get(0);
			System.out.println(t.deviceNumber + "   " + t.maxForwardSpeed + "   " + t.maxReverseSpeed + "   " + t.mode);
			//westCoast.setVelocity(-500, -500);
			Thread.sleep(100);
		}
		catch (Exception e) {
			//DONOTHING
		}
	}

	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
	}

	@Override
	public void testPeriodic() {

	}

	// updates the PID in gyro with the sliders or the networktables.
	public void updatePID() {

	}
}
