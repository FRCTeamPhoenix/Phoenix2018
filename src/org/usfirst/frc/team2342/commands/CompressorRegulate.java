package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.PCMHandler;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CompressorRegulate extends Command {

	PCMHandler m_pcm;
	
	public CompressorRegulate (PCMHandler pcm) {
		m_pcm = pcm;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putString("DB/String 0", "" + m_pcm.compressor.getPressureSwitchValue());
		
		/*
		 * NOTES ABOUT getPressureSwitchVaule:
		 * says returns true when pressure is low.  THIS IS A FALSE STATEMENT
		 * returns false when pressure is low.
		 */
		if(m_pcm.compressor.getPressureSwitchValue()) {
			m_pcm.off();
		} else {
			m_pcm.on();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
	
}
