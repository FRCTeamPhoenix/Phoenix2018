package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2342.robot.sensors.Gyro;

public class TeleOpCommands extends CommandGroup {
	public TeleOpCommands(Joystick gamepad, CascadeElevator m_cascadeElevator, WestCoastTankDrive westCoast) {
		addParallel(new CascadeUp(gamepad, m_cascadeElevator));
		addParallel(new DriveGamepad(gamepad, westCoast));
	}	
}

