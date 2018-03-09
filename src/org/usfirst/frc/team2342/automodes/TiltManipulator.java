package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class TiltManipulator extends Command {

	BoxManipulator manip;
	long time;
	
	public TiltManipulator(BoxManipulator manip) {
		super();
		this.manip = manip;
	}

	protected void initialize() {
		time = System.currentTimeMillis();
	}
	
	protected void execute() {
		 manip.talonTip.set(ControlMode.PercentOutput, -0.2);
	}
	
	protected boolean isFinished() {
		return System.currentTimeMillis() - time > 100;
	}

	protected void end() {
		manip.talonTip.set(ControlMode.PercentOutput, 0);
	}

}
