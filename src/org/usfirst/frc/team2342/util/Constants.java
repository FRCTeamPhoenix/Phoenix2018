package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.SerialPort;

public class Constants {
    public static final int LEFT_MASTER_TALON_ID = 1;
    public static final int RIGHT_MASTER_TALON_ID = 2;
    public static final int LEFT_SLAVE_TALON_ID = 3;
    public static final int RIGHT_SLAVE_TALON_ID = 4;
    
    public static final int TALON_VELOCITY_SLOT_IDX = 0;
    public static final int TALON_DISTANCE_SLOT_IDX = 1;
    
    
    public static final double TALON_TICKS_PER_REV = 4096.0;
    public static final double TALON_SPEED_RPS = TALON_TICKS_PER_REV / 10;
	//public static final double TALON_RPM_TO_VELOCITY = 1;//TALON_TICKS_PER_REV / 600.0;
	
    public static final double JOYSTICK_DEADZONE = 0.1;
    
	// Scales the speed of velocity mode (in rps)
	public static final double WESTCOAST_MAX_SPEED = TALON_SPEED_RPS * 2.5;
	public static final double RPS_TO_CMPS = 47.8536;
    
    public static final int PCM_PORT = 11;
    
    //where on the smartdashboard talons go
    public static final String TALON_TABLE_LOCATION = "Talons";
    
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
