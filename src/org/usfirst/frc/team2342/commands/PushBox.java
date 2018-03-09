package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PushBox extends Command {
	long time;
	BoxManipulator manipulator;
	Joystick gamepad;
	
    public PushBox(BoxManipulator manipulator, Joystick gamepad) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(manipulator);
    	this.gamepad = gamepad;
    	this.manipulator = manipulator;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("AUTO: PUSH BOX");
    	manipulator.talonIntakeRight.set(ControlMode.PercentOutput, 1);
    	manipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - time > 2000;
    }

    // Called once after isFinished returns true
    protected void end() {
    	manipulator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
