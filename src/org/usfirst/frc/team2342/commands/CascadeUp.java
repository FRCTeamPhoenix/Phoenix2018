package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class CascadeUp extends Command {
	CascadeElevator m_cascadeElevator;
	Joystick gamepad;

	public CascadeUp(Joystick gamepad, CascadeElevator m_cascadeElevator) {
		this.gamepad = gamepad;
		this.m_cascadeElevator = m_cascadeElevator;
	}
	
	protected void initialize() {
		m_cascadeElevator.setVelocity(-gamepad.getRawAxis(3));
	}
	
	protected void execute() {
		m_cascadeElevator.setVelocity(-gamepad.getRawAxis(3));
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		m_cascadeElevator.setVelocity(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}