package org.usfirst.frc.team2342.robot.commands;

import org.usfirst.frc.team2342.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandJoystickDrive extends Command{
	
	public CommandJoystickDrive(double x, double y) {
		requires(Robot.kDriveTrain);
		
	}
	protected void execute() {
		Robot.kDriveTrain.getInstance().setOpenLoop(left, right);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
