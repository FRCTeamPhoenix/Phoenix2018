package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

public class HomeCascade extends Command {
	CascadeElevator m_cascadeElevator;
	DigitalInput m_lowerSoftLimit;
	
	State m_state;
	
	enum State {
		down,
		upUpper,
		upLower,
		stopped;
	}
	
	public HomeCascade(CascadeElevator m_cascadeElevator, DigitalInput m_lowerSoftLimit) {
		requires(m_cascadeElevator);
		this.m_cascadeElevator = m_cascadeElevator;
		this.m_lowerSoftLimit = m_lowerSoftLimit;
	}
	
	protected void initialize() {
		m_cascadeElevator.setVelocity(Constants.CASCADE_HOMING_SPEED);
		m_state = State.down;
	}
	
	protected void execute() {
		if (m_state == State.down) {
			m_cascadeElevator.setVelocity(Constants.CASCADE_HOMING_SPEED);
			if (m_cascadeElevator.isRevLimitSwitchClosed()) {
				m_state = State.upUpper;
			}
		}
		else if (m_state == State.upUpper) {
			m_cascadeElevator.setVelocity(-Constants.CASCADE_HOMING_SPEED);
			if (m_lowerSoftLimit.isAnalogTrigger()) {
				m_state = State.upLower;
			}
		}
		else if (m_state == State.upLower) {
			m_cascadeElevator.setVelocity(-Constants.CASCADE_HOMING_SPEED);
			if (m_cascadeElevator.isFwdLimitSwitchClosed()) {
				m_state = State.stopped;
			}
		}
	}
	
	protected boolean isFinished() {
		if (m_state == State.stopped) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void end() {
		m_cascadeElevator.setVelocity(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
