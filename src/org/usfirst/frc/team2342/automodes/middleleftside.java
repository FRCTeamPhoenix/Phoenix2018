package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class middleleftside extends CommandGroup {
	public middleleftside(WestCoastTankDrive westCoast){
		addSequential(new DriveArc(westCoast, 3, 90, 1.0, 1.1, true));
		addSequential(new DriveDistance(westCoast, 1));
		addSequential(new DriveArc(westCoast, 5, 90, 1.0, 1.0, false));
	}
}
