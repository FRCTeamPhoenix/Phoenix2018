package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class rightscaleleft extends CommandGroup {
	public rightscaleleft(WestCoastTankDrive westCoast){
		//move forward 11 feet
		addSequential(new DriveDistance(westCoast, 11));
		//arc 90 degrees to the left with a radius of five feet
		addSequential(new DriveArc(westCoast, 5, 90, 1.0, 1.0, true));
		//move forward 5 feet
		addSequential(new DriveDistance(westCoast, 5));
		//arc 90 degrees to the right with a radius of 5 feet.
		addSequential(new DriveArc(westCoast, 5, 90, 1.0, 1.0, false));
	}
}
