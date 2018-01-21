package org.usfirst.frc.team2342.robot.actions;

import java.util.ArrayList;

public abstract class Action {
	public State state;

	public String name = "";
	
	public ArrayList<String> dependencies;
	
	public abstract void onStart();
	public abstract void onStop();
	public abstract void run();
	public abstract boolean isCompleted();
	
	enum State{
		NOT_STARTED,
		IN_PROGRESS,
		FINISHED
	}
}
