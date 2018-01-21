package org.usfirst.frc.team2342.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Timer;

public class Lidar {
	private SerialPort port;
	private int distance;
	private int faverage;
	private int saverage;
	private java.util.Timer updater;
	
	private int[] distances;
	
	public Lidar(){
		distance = 1;
		port = new SerialPort(115200, Port.kMXP);
		updater = new java.util.Timer();
		distances = new int[25];
		for(int i = 0; i < distances.length; i++){
			distances[i] = 0;
		}
		faverage = 1;
		saverage = 1;
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
	
	//fast average (smaller sample size)
	public double getFDistanceIn(){
		//from cm to inches
		return faverage * 0.393701;
	}
	
	public int getFDistanceCm(){
		return faverage;
	}
	
	//slow average
	public double getSDistanceIn(){
		//from cm to inches
		return saverage * 0.393701;
	}
	
	public int getSDistanceCm(){
		return saverage;
	}
	
	public void start(){
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	private int[] shiftRight(int[] list, int arg_distance)
	{
	   if (list.length < 2) return list;

	   int last = list[list.length - 1];

	   for(int i = list.length - 1; i > 0; i--) {
	      list[i] = list[i - 1];
	   }
	   list[0] = arg_distance;
	   return list;
	}
	
	private void update(){
		port.writeString("H");
		Timer.delay(0.04);
		String recv = port.readString();
		recv = recv.replaceAll("[^0-9.]", "");
		distance = Integer.parseInt(recv);
		distances = shiftRight(distances, distance);
		
		//calculate the averages
		int locfaverage = 0;
		for(int i = 0; i<10; i++){
			locfaverage += distances[i];
		}
		faverage = locfaverage / 10;
		
		int locsaverage = 0;
		for(int i = 0; i<25; i++){
			locsaverage += distances[i];
		}
		saverage = locsaverage / 25;
		
		Timer.delay(0.005);
	}
	
	public void stop(){
		updater.cancel();
        updater = new java.util.Timer();
	}
}
