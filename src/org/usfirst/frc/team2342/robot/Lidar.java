package org.usfirst.frc.team2342.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;

public class Lidar {
	private SerialPort port;
	private int distance;
	private java.util.Timer updater;
	
	public Lidar(){
		distance = 1;
		port = new SerialPort(115200, Port.kMXP);
		updater = new java.util.Timer();
	}

	private class LIDARUpdater extends TimerTask {
        public void run() {
            update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public double getDistanceIn(){
		//from cm to inches
		return distance * 0.393701;
	}
	
	public int getDistanceCm(){
		return distance;
	}
	
	public void start(){
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	private void update(){
		String recv = port.readString();
		recv = recv.replaceAll("[^0-9.]", "");
		distance = Integer.parseInt(recv);
		Timer.delay(0.005);
	}
	
	public void stop(){
		updater.cancel();
        updater = new java.util.Timer();
	}
}
