package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.util.Constants;

public class TurnAngle {
	double turnAngle = 90;
	double radius = 36 * Constants.INCHES_TO_CM;
	double wheelBase = 23 * Constants.INCHES_TO_CM;
	double robotRadius = radius + wheelBase / 2;
	double velocityLeftInitial = 0;
	double velocityRightInitial = 0;
	double velocityLeftFinal = 0;
	double velocityRightFinal = 0;
}
