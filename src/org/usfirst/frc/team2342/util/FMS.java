package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.DriverStation;

public class FMS {
	static String gameData = "LLL";
	
	public static void init(){
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}
	
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean teamSwitch(){
		return gameData.charAt(0) == 'L';
	}
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean scale(){
		return gameData.charAt(1) == 'L';
	}
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean enemySwitch(){
		return gameData.charAt(2) == 'L';
	}
}
