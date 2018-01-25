package org.usfirst.frc.team2342.robot;

import java.util.TimerTask;
import org.usfirst.frc.team2342.robot.Constants;

import edu.wpi.first.wpilibj.SerialPort;
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
		port = new SerialPort(Constants.SERIAL_BAUD_RATE, Constants.LIDAR_PORT);
		updater = new java.util.Timer();
		distances = new int[Constants.SLOW_AVERAGE_SIZE];
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
		return distance * Constants.CM_TO_INCHES;
	}
	
	public int getDistanceCm(){
		return distance;
	}
	
	//fast average (smaller sample size)
	public double getFDistanceIn(){
		//from cm to inches
		return faverage * Constants.CM_TO_INCHES;
	}
	
	public int getFDistanceCm(){
		return faverage;
	}
	
	//slow average
	public double getSDistanceIn(){
		//from cm to inches
		return saverage * Constants.CM_TO_INCHES;
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
		for(int i = 0; i<Constants.FAST_AVERAGE_SIZE; i++){
			locfaverage += distances[i];
		}
		faverage = locfaverage / Constants.FAST_AVERAGE_SIZE;
		
		int locsaverage = 0;
		for(int i = 0; i<Constants.SLOW_AVERAGE_SIZE; i++){
			locsaverage += distances[i];
		}
		saverage = locsaverage / Constants.SLOW_AVERAGE_SIZE;
		
		Timer.delay(0.005);
	}
	
	public void stop(){
		updater.cancel();
        updater = new java.util.Timer();
	}
}
