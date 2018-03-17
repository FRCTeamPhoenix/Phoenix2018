package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.DriveForward;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.SwapGears;
import org.usfirst.frc.team2342.commands.Turn90;
import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleAuto extends CommandGroup {

	public ScaleAuto(PCMHandler pcm, WestCoastTankDrive westCoast, CascadeElevator cascadeElevator, BoxManipulator boxManipulator, Joystick joystick) {
		addSequential(new SwapGears(pcm, true));
		addSequential(new DriveForward(2,westCoast, 100));
		addSequential(new SwapGears(pcm, false));
	
		//addSequential(new Turn90(westCoast,"right"));
		addSequential(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, joystick));
		addSequential(new PushBox(boxManipulator, joystick));
	}

}