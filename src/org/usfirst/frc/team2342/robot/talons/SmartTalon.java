package org.usfirst.frc.team2342.robot.talons;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SmartTalon extends WPI_TalonSRX {
    
    // put a minus sign in front of all setpoints,
    // used for reversed-polarity talons and devices
    private final boolean inverted;
    
    public SmartTalon(int deviceNumber) {
        this(deviceNumber, false, 0)
    }
    
    public SmartTalon(int deviceNumber, boolean inverted, int initialMode) {
        super(deviceNumber);
        this.inverted = inverted;
        // TODO set mode
    }
    
}
