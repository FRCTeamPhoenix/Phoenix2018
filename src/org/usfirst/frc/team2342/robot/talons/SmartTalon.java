package org.usfirst.frc.team2342.robot.talons;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;


public class SmartTalon extends WPI_TalonSRX implements PIDSource {
    
    // put a minus sign in front of all setpoints,
    // used for reversed-polarity talons and devices
    private boolean inverted;
    
    // maximum forward and reverse speeds
    private double maxForwardSpeed;
    private double maxReverseSpeed;
    
    // current mode
    private ControlMode mode;
    
    // PID gains for velocity and distance
    private PIDGains velocityGains;
    private PIDGains distanceGains;

	private PIDSourceType pidSource;
    
    public SmartTalon(int deviceNumber) {
        this(deviceNumber, false, ControlMode.Current);
    }
    
    public SmartTalon(int deviceNumber, boolean inverted, ControlMode initialMode) {
        this(deviceNumber, inverted, initialMode, FeedbackDevice.QuadEncoder);
    }
    
    public SmartTalon(int deviceNumber, boolean inverted, ControlMode initialMode, FeedbackDevice device) {
        super(deviceNumber);
        this.inverted = inverted;
        
        maxForwardSpeed = 1.0;
        maxReverseSpeed = 1.0;

        velocityGains = new PIDGains(0, 0, 0, 0, 0, 0);
        distanceGains = new PIDGains(0, 0, 0, 0, 0, 0);
        mode = initialMode;

        if (ControlMode.Current.equals(initialMode))
            setToVelocity();
        else if (ControlMode.Position.equals(initialMode))
            setToDistance();
        else if (ControlMode.Velocity.equals(initialMode))
            setToVelocity();
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

    
    //Go at a speed using velocity gains
     
    public void goAt(double speed) {
        speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;

        speed = (speed > 0) ? speed * maxForwardSpeed : speed * maxReverseSpeed;

        if (!ControlMode.Velocity.equals(mode)) {
            setToVelocity();
            set(ControlMode.Velocity,speed);
            mode = ControlMode.Velocity;
        }

        configPeakOutputForward(speed, 0);
        configPeakOutputReverse(speed, 0);

        if (!inverted)
            set(speed);
        else
            set(-speed);
    }

    // Go at a specific voltage, independent of all PID gains
     
    public void goVoltage(double speed) {
        speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;
        speed = (inverted) ? -speed : speed;

        if (!ControlMode.PercentOutput.equals(mode)) {
            set(ControlMode.PercentOutput, speed);
            mode = ControlMode.PercentOutput;
        }
        else {
            set(speed);
        }

        configPeakOutputForward(speed, 0);
        configPeakOutputReverse(speed, 0);
        
    }


    /*
     * Go a specific distance, using distance PID gains
     */
    public void goDistance(double distance, double speed) {
        
    	speed = (speed > 1) ? 1 : speed;
        speed = (speed < -1) ? -1 : speed;
        
        double setPoint = getSelectedSensorPosition(0) + distance;

        if (inverted)
            setPoint *= -1;
        
        if (!mode.equals(ControlMode.Position)) {
            setToDistance();
            set(ControlMode.Position, setPoint);
            mode = ControlMode.Position;
        }
        else
        	set(setPoint);
        
       
        configPeakOutputForward(speed, 0);
        configPeakOutputReverse(speed, 0);
        
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

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}

	@Override
	public double pidGet() {
		if (getPIDSourceType() == PIDSourceType.kDisplacement) {
			getSelectedSensorPosition(0);
		} else {
			getSelectedSensorVelocity(0);
		}
		return 0;
	}
}
