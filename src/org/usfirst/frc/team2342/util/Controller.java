package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.Joystick;

public class Controller {
boolean cascade0;
boolean cascadeSwitch;
boolean cascadeLowScale;
boolean cascadeHighScale;
int tipPOV;
boolean shootSwitch;
boolean openManip1;
boolean openManip2;
double rollersOut;
double rollersIn;
double cascadeMove;
double manipTip;
int preset;
Joystick conType;

	public Controller(int preset, Joystick conType) {
		this.preset = preset;
		this.conType = conType;
		if (preset == 1) {
			cascade0 = conType.getRawButton(Constants.XBOX_A);
			cascadeSwitch = conType.getRawButton(Constants.XBOX_B);
			cascadeLowScale = conType.getRawButton(Constants.XBOX_X);
			cascadeHighScale = conType.getRawButton(Constants.XBOX_Y);
			//tipPOV = conType.getPOV(Constants.XBOX_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getPOV();
			openManip1 = conType.getRawButton(Constants.XBOX_LEFTBUMPER);
			openManip2 = conType.getRawButton(Constants.XBOX_RIGHTBUMPER);
			rollersOut = (conType.getRawAxis(Constants.XBOX_LEFTTRIGGER));
			rollersIn = (conType.getRawAxis(Constants.XBOX_RIGHTTRIGGER));
			cascadeMove = conType.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			manipTip = conType.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
		}
		if (preset == 2) {
			cascade0 = conType.getRawButton(Constants.LOGITECH_A);
			cascadeSwitch = conType.getRawButton(Constants.LOGITECH_B);
			cascadeLowScale = conType.getRawButton(Constants.LOGITECH_X);
			cascadeHighScale = conType.getRawButton(Constants.LOGITECH_Y);
			//tipPOV = conType.getPOV(Constants.LOGITECH_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getRawButton(Constants.LOGITECH_X);
			openManip1 = conType.getRawButton(Constants.LOGITECH_X);
			openManip2 = conType.getRawButton(Constants.LOGITECH_X);
			rollersOut = conType.getRawButton(Constants.LOGITECH_LEFTTRIGGER) ? 1:0;
			rollersIn = conType.getRawButton(Constants.LOGITECH_RIGHTTRIGGER) ? 1:0;
			cascadeMove = conType.getRawAxis(Constants.LOGITECH_X);
			manipTip = conType.getRawAxis(Constants.LOGITECH_X);
		}
		if (preset == 3) {
			cascade0 = conType.getRawButton(Constants.XBOX_A);
			cascadeSwitch = conType.getRawButton(Constants.XBOX_B);
			cascadeLowScale = conType.getRawButton(Constants.XBOX_X);
			cascadeHighScale = conType.getRawButton(Constants.XBOX_Y);
			//tipPOV = conType.getPOV(Constants.XBOX_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getPOV();
			openManip1 = conType.getRawButton(Constants.XBOX_LEFTBUMPER);
			openManip2 = conType.getRawButton(Constants.XBOX_RIGHTBUMPER);
			//rollersOut = (conType.getRawAxis(Constants.XBOX_LEFTTRIGGER) > 0.1);
			//rollersIn = (conType.getRawAxis(Constants.XBOX_RIGHTTRIGGER) > 0.1);
			cascadeMove = conType.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			manipTip = conType.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
		}
	}

	public boolean isCascade0() {
		return cascade0;
	}

	public void setCascade0(boolean cascade0) {
		this.cascade0 = cascade0;
	}

	public boolean isCascadeSwitch() {
		return cascadeSwitch;
	}

	public void setCascadeSwitch(boolean cascadeSwitch) {
		this.cascadeSwitch = cascadeSwitch;
	}

	public boolean isCascadeLowScale() {
		return cascadeLowScale;
	}

	public void setCascadeLowScale(boolean cascadeLowScale) {
		this.cascadeLowScale = cascadeLowScale;
	}

	public boolean isCascadeHighScale() {
		return cascadeHighScale;
	}

	public void setCascadeHighScale(boolean cascadeHighScale) {
		this.cascadeHighScale = cascadeHighScale;
	}

	public int getTipPOV() {
		return tipPOV;
	}

	public void setTipPOV(int tipPOV) {
		this.tipPOV = tipPOV;
	}

	public boolean isShootSwitch() {
		return shootSwitch;
	}

	public void setShootSwitch(boolean shootSwitch) {
		this.shootSwitch = shootSwitch;
	}

	public boolean isOpenManip1() {
		return openManip1;
	}

	public void setOpenManip1(boolean openManip1) {
		this.openManip1 = openManip1;
	}

	public boolean isOpenManip2() {
		return openManip2;
	}

	public void setOpenManip2(boolean openManip2) {
		this.openManip2 = openManip2;
	}

	public double getRollersOut() {
		if (preset == 1) {
			return conType.getRawAxis(Constants.XBOX_LEFTTRIGGER);
		}
		return rollersOut;
	}

	public void setRollersOut(double rollersOut) {
		this.rollersOut = rollersOut;
	}

	public double getRollersIn() {
		if (preset == 1) {
			return conType.getRawAxis(Constants.XBOX_RIGHTTRIGGER);
		}
		return rollersIn;
	}

	public void setRollersIn(double rollersIn) {
		this.rollersIn = rollersIn;
	}

	public double getCascadeMove() {
		return cascadeMove;
	}

	public void setCascadeMove(double cascadeMove) {
		this.cascadeMove = cascadeMove;
	}

	public double getManipTip() {
		return manipTip;
	}

	public void setManipTip(double manipTip) {
		this.manipTip = manipTip;
	}
	
	public void update() {
		if (preset == 1) {
			cascade0 = conType.getRawButton(Constants.XBOX_A);
			cascadeSwitch = conType.getRawButton(Constants.XBOX_B);
			cascadeLowScale = conType.getRawButton(Constants.XBOX_X);
			cascadeHighScale = conType.getRawButton(Constants.XBOX_Y);
			tipPOV = conType.getPOV(Constants.XBOX_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getPOV();
			openManip1 = conType.getRawButton(Constants.XBOX_LEFTBUMPER);
			openManip2 = conType.getRawButton(Constants.XBOX_RIGHTBUMPER);
			rollersOut = (conType.getRawAxis(Constants.XBOX_LEFTTRIGGER));
			rollersIn = (conType.getRawAxis(Constants.XBOX_RIGHTTRIGGER));
			cascadeMove = conType.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			manipTip = conType.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
		}
		if (preset == 2) {
			cascade0 = conType.getRawButton(Constants.LOGITECH_A);
			cascadeSwitch = conType.getRawButton(Constants.LOGITECH_B);
			cascadeLowScale = conType.getRawButton(Constants.LOGITECH_X);
			cascadeHighScale = conType.getRawButton(Constants.LOGITECH_Y);
			tipPOV = conType.getPOV(Constants.LOGITECH_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getRawButton(Constants.LOGITECH_X);
			openManip1 = conType.getRawButton(Constants.LOGITECH_X);
			openManip2 = conType.getRawButton(Constants.LOGITECH_X);
			rollersOut = conType.getRawButton(Constants.LOGITECH_LEFTTRIGGER) ? 1:0;
			rollersIn = conType.getRawButton(Constants.LOGITECH_RIGHTTRIGGER) ? 1:0;
			cascadeMove = conType.getRawAxis(Constants.LOGITECH_X);
			manipTip = conType.getRawAxis(Constants.LOGITECH_X);
		}
		if (preset == 3) {
			cascade0 = conType.getRawButton(Constants.XBOX_A);
			cascadeSwitch = conType.getRawButton(Constants.XBOX_B);
			cascadeLowScale = conType.getRawButton(Constants.XBOX_X);
			cascadeHighScale = conType.getRawButton(Constants.XBOX_Y);
			tipPOV = conType.getPOV(Constants.XBOX_LEFTSTICK_YAXIS);
			//shootSwitch = conType.getPOV();
			openManip1 = conType.getRawButton(Constants.XBOX_LEFTBUMPER);
			openManip2 = conType.getRawButton(Constants.XBOX_RIGHTBUMPER);
			//rollersOut = (conType.getRawAxis(Constants.XBOX_LEFTTRIGGER));
			//rollersIn = (conType.getRawAxis(Constants.XBOX_RIGHTTRIGGER));
			cascadeMove = conType.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			manipTip = conType.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
		}
	}
}