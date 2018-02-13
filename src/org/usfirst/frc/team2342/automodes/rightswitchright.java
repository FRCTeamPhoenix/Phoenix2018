package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class rightswitchright extends CommandGroup {
	public rightswitchright(WestCoastTankDrive westCoast){
		//move forward 8 feet
		addSequential(new DriveDistance(westCoast, 8));
		//arc 90 degrees to the right with a radius of 2.5 feet
		addSequential(new DriveArc(westCoast, 2.5, 90, 1.0, 1.0, true));
	}
}
