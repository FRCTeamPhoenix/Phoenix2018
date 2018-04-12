package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TiltManipulator;
import org.usfirst.frc.team2342.commands.Turn90;
import org.usfirst.frc.team2342.commands.Wait;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAutoCorrected extends CommandGroup {

	public MiddleAutoCorrected(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		if(DriverStation.getInstance().getGameSpecificMessage().length() > 0 && DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			addSequential(new TiltManipulator(manip));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveArc(drive, 8.5, 0));
			addSequential(new PushBox(manip, gamepad, 0.5));
		} else {
			addSequential(new TiltManipulator(manip));
			addSequential(new DriveArc(drive, 1, 0));
			addSequential(new Wait(300));
			addSequential(new Turn90(drive, true));
			addSequential(new DriveArc(drive, 4, 0));
			addSequential(new Wait(300));
			addSequential(new Turn90(drive, false));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveArc(drive, 6.8, 0));
			addSequential(new PushBox(manip, gamepad, 0.5));
		}
	}
	
}
