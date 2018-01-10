package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.loops.Looper;

import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends SampleRobot {
	private Looper looper;
	private LidarLoop lidarloop;
	
    public Robot() {
    	looper = new Looper();
    	lidarloop = new LidarLoop();
    	looper.register(lidarloop);
    	looper.start();
    }

    @Override
    public void operatorControl() {

    }

    @Override
    public void autonomous() {

    }

    @Override
    public void test() {

    }
}
