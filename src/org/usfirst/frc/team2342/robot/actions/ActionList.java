package org.usfirst.frc.team2342.robot.actions;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team2342.robot.talons.SmartTalon;

public class ActionList{
	
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	private boolean emergancyStop = false;
	
	//Might be used for testing later
	private Scanner console;
	
	
	
	public ActionList(ArrayList<Action> actions){
		
		this.actions = actions;
		
	}
	
	/*
	//Testing constructor
	public ActionList(){
		
		this.actions = ;
		
	}*/
	
	//Execute all actions who's conditions have been satisfied
	public void execute(SmartTalon talon1, SmartTalon talon2, SmartTalon talon3, SmartTalon talon4){
		
		for(Action action: actions){
			
			if(!emergancyStop)
				action.run(actions, talon1, talon2, talon3, talon4);
			
			else
				action.stop(talon1, talon2, talon3, talon4);
			
			//DEBUGGING
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void stop(){
		
		emergancyStop = true;
	}
	
	public void start(){
		
		emergancyStop = false;
	}
	
	
}