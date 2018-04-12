package org.usfirst.frc.team2342.automodes;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class AutoRecorder extends Command {

	private ArrayList<Double> rightList;
	private ArrayList<Double> leftList;
	private ArrayList<Boolean> pushList;
	
	int index = 0;
	
	TankDrive drive;
	Box
	
	public AutoRecorder(TankDrive drive, BoxManipulator boxManipulator, CascadeElevator cascadeElevator) {
		this.drive = drive;
		rightList = new ArrayList<Double>();
		leftList = new ArrayList<Double>();
		pushList = new ArrayList<Boolean>();
		Scanner input = null;
		try {
			 input = new Scanner(new File("/home/lvuser/auto_data.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (input.hasNext()) {
			leftList.add(input.nextDouble());
			rightList.add(input.nextDouble());
			pushList.add(input.nextBoolean());
		}
	}

	protected void initialize() {
		index = 0;
	}
	
	protected void execute() {
		drive.setPercentage(leftList.get(index), rightList.get(index));
		if(pushList.get(index)) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -0.5);
		}
		index++;
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return index >= leftList.size() - 1;
	}
	
	protected void end() {
		drive.setPercentage(0, 0);
	}
	
	
}
