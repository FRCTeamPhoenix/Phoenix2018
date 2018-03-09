package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoGroup extends CommandGroup {

	public AutoGroup(WestCoastTankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick stick, boolean correctSide) {
		/*if(correctSide) {
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, stick));
		}
		addSequential(new DriveDistance(drive, 7));
		if(correctSide) {
			addParallel(new TiltManipulator(manip));
			addSequential(new PushBox(manip, stick));
		}*/
		addSequential(new DriveDistance(drive, 10));
		addSequential(new TurnAngle(Constants.WESTCOAST_HALF_SPEED, -90, drive));
		addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, stick));
		addSequential(new DriveDistance(drive, 3));
		addParallel(new TiltManipulator(manip));
		addSequential(new PushBox(manip, stick));
	}
	
}
