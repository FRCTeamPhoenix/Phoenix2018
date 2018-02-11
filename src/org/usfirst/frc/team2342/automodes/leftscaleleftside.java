package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class leftscaleleftside extends CommandGroup {
	public leftscaleleftside(WestCoastTankDrive westCoast){
		addSequential(new DriveDistance(westCoast, 22));
		//turn 90 degrees to the right
	}
}
