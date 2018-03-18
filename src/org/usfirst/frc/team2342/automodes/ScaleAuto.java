package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadeHold;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveVoltageTime;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TiltManipulator;
import org.usfirst.frc.team2342.commands.Turn90;
import org.usfirst.frc.team2342.commands.Wait;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleAuto extends CommandGroup {

	public ScaleAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		
		
		addParallel(new CascadePosition(cascade, Constants.CASCADE_UPPER_SCALE, gamepad));
		addSequential(new DriveVoltageTime(drive, 3000, 1));
		addParallel(new CascadeHold(cascade));
		addSequential(new Wait(2000));
		addSequential(new Turn90(drive, false));
		//addSequential(new Wait(500));
		addSequential(new TiltManipulator(manip));
		addSequential(new PushBox(manip, gamepad));
	}

}
