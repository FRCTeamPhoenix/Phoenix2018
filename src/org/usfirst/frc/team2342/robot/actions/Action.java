/*Base Action Class, not to be mistaken for usefull Action classes*/

package org.usfirst.frc.team2342.robot.actions;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;

public abstract class Action {
	public State state;

	public String name = "";
	
	public ArrayList<String> dependencies;
	
	public abstract void start();
	public abstract void stop();
	public abstract void run();
	public abstract boolean isCompleted();
	
	public Action(String name, ArrayList<String> dependencies) {
		state = State.NOT_STARTED;
		this.name = name;
		this.dependencies = dependencies;
	}
	
	enum State{
		NOT_STARTED,
		IN_PROGRESS,
		FINISHED
	}
}