package org.usfirst.frc.team2342.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.opencv.core.CvType;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */

public class Robot extends IterativeRobot {

	/*Joystick gamepad = new Joystick(0);
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
	Joystick joystickL = new Joystick(1);*/

	public Robot() {
		//PCM.turnOn();
		//WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
		//WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
		//boxManipulator = new BoxManipulator(talon1, talon2, PCM);
		//cascadeElevator = new CascadeElevator(talon1, talon2);
	}

	
	public void robotInit(){
		
		int[] indexes = {0};
		System.out.println("hi ho");
		CameraControl cameras = new CameraControl(indexes, 640, 480);
		System.out.println("hi ho hi ho");
		//cameras.run();
		System.out.println("its off to work we go");
	}
	
	public void teleopInit() {
		/*System.out.println("TELEOP MODE INIT");
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, westCoast);
		Scheduler.getInstance().add(driveJoystick);
		westCoast.setGyroControl(false);
		westCoast.debug = false;*/
		
		/*new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Stuff", 0);
            camera.setResolution(640, 480);
            
            //CameraServer.getInstance().startAutomaticCapture(name, path);
            
            CvSink cvSink = CameraServer.getInstance().getVideo(camera);
            CvSource outputStream = CameraServer.getInstance().putVideo("BLAH", 640, 480);
            
            Mat source = new Mat(640, 480, CvType.CV_32FC3);
            Mat output = new Mat();
            
            while(!Thread.interrupted()) {
            	cvSink.setEnabled(true);
                cvSink.grabFrame(source);
                outputStream.putFrame(output);
                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
                
            }
        }).start();
		
		new Thread(() -> {
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("USB Camera 1", 1);
            camera.setResolution(640, 480);
            
            CvSink cvSink = CameraServer.getInstance().getVideo(camera);
            CvSource outputStream = CameraServer.getInstance().putVideo("Blur Current2", 1280, 960);
            
            Mat source = new Mat(320, 240, CvType.CV_32FC3);
            Mat output = new Mat();
            
            while(!Thread.interrupted()) {
            	cvSink.setEnabled(true);
                cvSink.grabFrame(source);
                outputStream.putFrame(output);
                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
                
            }
        }).start();*/
		
		
		/*int[] indexes = {0, 1, 2};
		CameraControl cameras = new CameraControl(indexes, 640, 480);
		
		cameras.run();*/
		
		//try{Thread.sleep(30000);}
		//catch(InterruptedException e){}
		
		//cameras.stop();
		
		/*System.out.println("hi");
		cameras.switchTo(1);
		
		try{Thread.sleep(10000);}
		catch(InterruptedException e){}
		
		System.out.println("hi");
		cameras.switchTo(2);
		
		try{Thread.sleep(10000);}
		catch(InterruptedException e){}
		
		System.out.println("hi2");
		cameras.switchTo(0);
		
		try{Thread.sleep(10000);}
		catch(InterruptedException e){}
		
		cameras.stop();*/
		
	}
    
    public void teleopPeriodic() {
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
		}*/

		// PCM.turnOn();
		// WPI_TalonSRX talon1 = new WPI_TalonSRX(0);
		// WPI_TalonSRX talon2 = new WPI_TalonSRX(1);
		// boxManipulator = new BoxManipulator(talon1, talon2, PCM);
		// cascadeElevator = new CascadeElevator(talon1, talon2);*/
	}
    
	public void disabledInit() {
		/*westCoast.setVelocity(0.0d, 0.0d);
		westCoast.zeroSensors();
		Scheduler.getInstance().removeAll();*/
	}

	public void autonomousInit() {
		/*//Command goForward = new DriveForward(20, westCoast, 6.0 * Constants.TALON_SPEED_RPS);
		//Scheduler.getInstance().add(goForward);
		westCoast.goArc(8, 90, 0.425, 0.5, false);
		//DriveDistance driveDistance = new DriveDistance(westCoast, 8);
		//Scheduler.getInstance().add(driveDistance);*/
	}

	public void autonomousPeriodic(){
		/*westCoast.arcLoop(false);
		//Scheduler.getInstance().run();
		PCM.compressorRegulate();
		westCoast.outputToSmartDashboard();*/
	}

	@Override
	public void testInit() {
		/*System.out.println("TEST MODE INIT");
		westCoast.pidc.getGyro().reset();
		westCoast.setGyroControl(true);
		westCoast.zeroSensors();
		westCoast.debug = true;*/
	}

	@Override
	public void testPeriodic() {
		/*// Limit for the current velocity for the robot without cascade is 3000
		try {
			westCoast.updatePID();
			westCoast.setVelocity(-1500, -1500); // test velocity
			westCoast.outputToSmartDashboard();  // update network tables
		}
		catch (Exception e) {
			//NOTHING
		}*/
	}
}
