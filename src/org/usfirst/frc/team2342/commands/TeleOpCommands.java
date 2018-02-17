package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2342.robot.sensors.Gyro;

public class TeleOpCommands extends CommandGroup {
	public TeleOpCommands(Joystick gamepad, CascadeElevator m_cascadeElevator, WestCoastTankDrive westCoast, double radius, double angle, double innerMultiplyer, double outerMultiplyer, boolean isLeftInner, double distance, int time, double speedRPS, Gyro gyro, double goalAngle) {
		addParallel(new CascadeUp(gamepad, m_cascadeElevator));
		addParallel(new DriveArc(westCoast, radius, angle, innerMultiplyer, outerMultiplyer, isLeftInner));
		addParallel(new DriveDistance(westCoast, distance));
		addParallel(new DriveForward(time, westCoast, speedRPS));
		addParallel(new DriveGamepad(gamepad, westCoast));
		addParallel(new TurnAngle(gyro, time, goalAngle, westCoast));
	}	
}

