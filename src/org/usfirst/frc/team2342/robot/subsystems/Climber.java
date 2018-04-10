package org.usfirst.frc.team2342.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

	WPI_TalonSRX winch;
	
	public Climber(WPI_TalonSRX winch) {
		this.winch = winch;
	}
	
	public void windUp() {
		winch.set(ControlMode.PercentOutput, 1.0);
	}
	
	public void windDown() {
		winch.set(ControlMode.PercentOutput, -1.0);
	}
	
	public void stop() {
		winch.set(ControlMode.PercentOutput, 0.0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
