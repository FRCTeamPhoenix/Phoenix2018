package org.usfirst.frc.team2342.PIDLoops;

public class Velocity {
	private int initial = 0;
	private long finalT = 0;
	private int id = 0;
	
	public Velocity(int i, long f, int id) {
		this.initial = i;
		this.finalT = f;
		this.id = (id <= 1) ? id : 1; 
	}
	
	/*
	 * 0 - left motor
	 * 1 - right motor
	 */
	public int vel() {
		if (this.id == 1) {
			
		}
	}
	
}
