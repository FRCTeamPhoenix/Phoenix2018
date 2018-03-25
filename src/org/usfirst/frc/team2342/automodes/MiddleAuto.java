package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.Turn90;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAuto extends CommandGroup {

	public MiddleAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		addSequential(new DriveDistance2(drive, 1));
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new Turn90(drive, true, 45));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance2(drive, 6));
			addSequential(new Turn90(drive, false, 35));
			addSequential(new DriveDistance2(drive, 2, Constants.WESTCOAST_HALF_SPEED / 2));
			addSequential(new PushBox(manip, gamepad, 0.5));
		} else {
			addSequential(new Turn90(drive, false, 55));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance2(drive, 6));
			addSequential(new Turn90(drive, true, 35));
			addSequential(new DriveDistance2(drive, 1, Constants.WESTCOAST_HALF_SPEED / 2));
			addSequential(new PushBox(manip, gamepad, 0.5));
		}
		addSequential(new DriveDistance2(drive, -2));
		addSequential(new CascadePosition(cascade, Constants.CASCADE_BASE, gamepad));
	}
	
}
