package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveVoltageTime;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MiddleAuto extends CommandGroup {

	public MiddleAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		
		//Drive forward. Second paramater is drive time (ms) and third parameter is voltage (percentage 0.0 to 1.0).
		addSequential(new DriveVoltageTime(drive, 2000, 0.3));
		
		//Raise cascade to SWITCH HEIGHT.
		addSequential(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
		
		//This if statement does logic to determine if we should push a cube into the switch.
		//If DB/Button 1 is PRESSED, we will be on the LEFT side.
		//If DB/Button 1 is NOT PRESSED, we will be on the RIGHT side.
		//This should not need to be changed significantly.
		if(!(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' ^ SmartDashboard.getBoolean("DB/Button 1", false))) {
			
			//Push a box into the switch. The third parameter is the shooting power (percentage 0.0 to 1.0).
			addSequential(new PushBox(manip, gamepad, 0.5));
		}
		
	}
	
}
