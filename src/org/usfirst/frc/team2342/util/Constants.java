package org.usfirst.frc.team2342.util;

public class Constants {
    public static final int LEFT_MASTER_TALON_ID = 1;
    public static final int RIGHT_MASTER_TALON_ID = 2;
    public static final int LEFT_SLAVE_TALON_ID = 3;
    public static final int RIGHT_SLAVE_TALON_ID = 4;
    
    public static final int TALON_VELOCITY_SLOT_IDX = 0;
    public static final int TALON_DISTANCE_SLOT_IDX = 0;
    
    /* 
	 * Convert RPM to units/100ms.
	 * 4096 Units/Rev * RPM / 600 100ms/min in either direction:
	 * velocity setpoint is in units/100ms
	 */
	public static final double TALON_RPM_TO_VELOCITY = 4096.0 / 600.0;
	
	// Scales the speed of velocity mode
	public static final double WESTCOAST_VELOCITY_RPM_SCALE = 500.0;
    
    public static final int PCM_PORT = 11;
    
}
