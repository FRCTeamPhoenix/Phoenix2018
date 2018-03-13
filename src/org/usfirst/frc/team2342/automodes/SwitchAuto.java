package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchAuto extends CommandGroup{
	public SwitchAuto(WestCoastTankDrive westCoast, CascadeElevator cascadeElevator, BoxManipulator boxManipulator, Joystick joystick) {
		addSequential(new DriveDistance(westCoast, 7));
		addSequential(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, joystick));
		addSequential(new PushBox(boxManipulator, joystick));
	}
}
