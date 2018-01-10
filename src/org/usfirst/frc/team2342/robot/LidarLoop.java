package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.loops.Loop;
import org.usfirst.frc.team2342.util.CrashTracker;

public class LidarLoop implements Loop{

	@Override
	public void onStart(double timestamp) {
		// TODO Auto-generated method stub
		CrashTracker.logMarker("Starting");
	}

	@Override
	public void onLoop(double timestamp) {
		// TODO Auto-generated method stub
		CrashTracker.logMarker("Inside");
	}

	@Override
	public void onStop(double timestamp) {
		// TODO Auto-generated method stub
		CrashTracker.logMarker("Ending");
		
	}
	
	
}
