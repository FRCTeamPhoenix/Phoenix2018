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

	public static final double TALON_RPS_TO_FPS = 1.57;

	public static final double JOYSTICK_DEADZONE = 0.025;

	// Scales the speed of velocity mode (in rps)
	public static final double WESTCOAST_MAX_SPEED = TALON_SPEED_RPS * 5.95d; // 2.5 origionally
	public static final double WESTCOAST_HALF_SPEED = WESTCOAST_MAX_SPEED * 0.5d;

	public static final int PCM_PORT = 11;
	
	public static final int LOWER_SENSOR_POSITION = 0;
	public static final int UPPER_SENSOR_POSITION = -28000;
	public static final int INCHES_TO_TICKS_CASCADE = -290;

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

	//limit switches
	public static final int LOWER_LIMIT_SWITCH = 0;
	public static final int UPPER_LIMIT_SWITCH = 1;

	//boxmanipulator talon constants
	public static final int TALON_CASCADE = 5;
	public static final int TALON_INTAKE_RIGHT = 9;
	public static final int TALON_INTAKE_LEFT = 10;
	public static final int TALON_TIP = 8;
	public static final int TALON_CLIMBER = 0;
	
	//positions of important cascade heights
	public static final int CASCADE_BASE = 0;
	public static final int CASCADE_SWITCH = 24;
	public static final int CASCADE_LOWER_SCALE = 72;
	public static final int CASCADE_UPPER_SCALE = 72;

	//solenoid constants
	public static final int PCM_CAN_ID = 11;
	public static final int PCM_SLOT_LOWGEAR = 0;
	public static final int PCM_SLOT_HIGHGEAR = 1;
	public static final int PCM_BOX_MANIPULATOR = 2;

	// PID for Gyro Forward
	public static final double Kp = 0.02d;
	public static final double Ki = 0.0d;
	public static final double Kd = 0.0d;
	
	// PID for Gyro TIP Turn In Place
	public static final double tKp = 0.1d;
	public static final double tKi = 0.001d;
	public static final double tKd = 0.0d;
}
