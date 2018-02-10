package org.usfirst.frc.team2342.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PCMHandler extends Subsystem {
	
	public Compressor compressor = new Compressor(50);
	
	Solenoid highGear = new Solenoid(11, 1);
	Solenoid lowGear = new Solenoid(11, 0);
	
	public void on() {
		compressor.start();
	}
	
	public void off() {
		compressor.stop();
	}
	
	public void setHighGear() {
		lowGear.set(false);
		highGear.set(true);
	}
	
	public void setLowGear() {
		highGear.set(false);
		lowGear.set(true);
	}
	
	public void setNoGear() {
		highGear.set(false);
		lowGear.set(false);
	}
	
	@Override
	public void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}
