package org.usfirst.frc.team2342.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SmartTalon extends WPI_TalonSRX {
    
    // put a minus sign in front of all setpoints,
    // used for reversed-polarity talons and devices
    private boolean inverted;
    
    // maximum forward and reverse speeds
    private double maxForwardSpeed;
    private double maxReverseSpeed;

    /*
     * current mode, offset by MODE_OFFSET 0: VOLTAGE MODE 1: POSITION MODE 2:
     * VELOCITY MODE
     */
    private int mode;
    
    // PID gains for velocity and distance
    private PIDGains velocityGains;
    private PIDGains distanceGains;
    
    public SmartTalon(int deviceNumber) {
        this(deviceNumber, false, 0);
    }
    
    public SmartTalon(int deviceNumber, boolean inverted, int initialMode) {
        this(deviceNumber, inverted, initialMode, FeedbackDevice.QuadEncoder);
    }
    
    public SmartTalon(int deviceNumber, boolean inverted, int initialMode, FeedbackDevice device) {
        super(deviceNumber);
        this.inverted = inverted;
        configSelectedFeedbackSensor(device, 0, 0);
    }
    
    
    // TODO first argument? slotidx?
    private void setToVelocity() {
        config_kP(0, velocityGains.getP(), 0);
        config_kI(0, velocityGains.getI(), 0);
        config_kD(0, velocityGains.getD(), 0);
        config_IntegralZone(0, velocityGains.getIzone(), 0);
        config_kF(0, velocityGains.getFf(), 0);
        configOpenloopRamp(velocityGains.getRr(), 0);
    }

    private void setToDistance() {
        config_kP(0, distanceGains.getP(), 0);
        config_kI(0, distanceGains.getI(), 0);
        config_kD(0, distanceGains.getD(), 0);
        config_IntegralZone(0, distanceGains.getIzone(), 0);
        config_kF(0, distanceGains.getFf(), 0);
        configOpenloopRamp(distanceGains.getRr(), 0);
    }

    /*
     * Go at a speed using velocity gains
     */
    public void goAt(double speed) {
        speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;

        speed = (speed > 0) ? speed * maxForwardSpeed : speed * maxReverseSpeed;

        if (mode != TalonControlMode.Speed.getValue()) {
            setToVelocity();
            changeControlMode(TalonControlMode.Speed);
            mode = TalonControlMode.Speed.getValue();
        }

        configMaxOutputVoltage(12);

        if (!inverted)
            setSetpoint(speed);
        else
            setSetpoint(-speed);
    }

    /*
     * Go at a specific voltage, independent of all PID gains
     */
    public void goVoltage(double speed) {
        speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;
        speed = (inverted) ? -speed : speed;

        if (mode != ControlMode.PercentOutput.value) {
            set(ControlMode.PercentOutput, speed);
            mode = ControlMode.PercentOutput.value;
        }

        configMaxOutputVoltage(12);
        
    }

    /*
     * Go a specific distance, using distance PID gains
     */
    public void goDistance(double distance, double speed) {
        speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;

        double setPoint = getPosition() + distance;

        if (mode != TalonControlMode.Position.getValue()) {
            setToDistance();
            changeControlMode(TalonControlMode.Position);
            mode = TalonControlMode.Position.getValue();
        }

        configMaxOutputVoltage(12 * speed);

        if (!inverted)
            setSetpoint(setPoint);
        else
            setSetpoint(-setPoint);
    }

    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public double getMaxForwardSpeed() {
        return maxForwardSpeed;
    }

    public void setMaxForwardSpeed(double maxForwardSpeed) {
        this.maxForwardSpeed = maxForwardSpeed;
    }

    public double getMaxReverseSpeed() {
        return maxReverseSpeed;
    }

    public void setMaxReverseSpeed(double maxReverseSpeed) {
        this.maxReverseSpeed = maxReverseSpeed;
    }

    public PIDGains getVelocityGains() {
        return velocityGains;
    }

    public void setVelocityGains(PIDGains velocityGains) {
        this.velocityGains = velocityGains;
    }

    public PIDGains getDistanceGains() {
        return distanceGains;
    }

    public void setDistanceGains(PIDGains distanceGains) {
        this.distanceGains = distanceGains;
    }

}
