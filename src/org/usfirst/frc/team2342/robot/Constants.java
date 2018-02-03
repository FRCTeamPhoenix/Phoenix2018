package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class Constants {
	
	
	//sensor ports
	public static final SerialPort.Port LIDAR_PORT = SerialPort.Port.kMXP;
	//DIO port for infrared
	public static final int INFRARED_PORT = 9;
	
	//sensor stuff
	public static final int SERIAL_BAUD_RATE = 115200;
	
	//lidar averages
	public static final int SLOW_AVERAGE_SIZE = 25;
	public static final int FAST_AVERAGE_SIZE = 10;
	
	
	//cm to inches
	public static final double CM_TO_INCHES = 0.393701;
	
	//inches to cm
	public static final double INCHES_TO_CM = 2.54;
}
