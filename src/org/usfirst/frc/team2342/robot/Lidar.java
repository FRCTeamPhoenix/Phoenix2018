package org.usfirst.frc.team2342.robot;

import edu.wpi.first.wpilibj.Counter;

import java.nio.ByteBuffer;
import java.util.*;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lidar {
	private int m_mode;
	private double m_distance;
	private double m_fastAverage;
	private double m_slowAverage;
	private double m_distances[] = new double[10];
	private byte m_status;
	private I2C m_I2C;
	private Counter m_monitorPin;
	private DigitalOutput m_triggerPin;
	private int m_counter;

	public void run() 
	{
		double distance;
		byte[] distanceArray = new byte[2];
		m_I2C.write(0x00, 0x04);
	    try {
			Thread.sleep((long)0.05);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ByteBuffer distanceRegister_1st = ByteBuffer.allocate(0x8f);
		//Read and Write don't work, use WriteBulk and ReadOnly whenever you
		//use I2C
		    m_I2C.writeBulk(distanceRegister_1st, 0x01);
		    m_I2C.readOnly(distanceArray,2);
		    distance = ((distanceArray[0] << 8) + distanceArray[1]) / 2.54;
		    
		    if(distance > 200) return;

		    m_distance = distance;

		    double average = 0;
		    for(int i=0; i<10; i++)
		       average += (m_distances[i] / 10);

		    if(Math.abs(average - distance) < 5)
		    {
		       m_counter = (1 + m_counter) % 10;
		       m_distances[m_counter] = distance;
		    }

		    double fast = 5.0;
		    m_fastAverage = (fast * m_fastAverage + m_distance) / (fast + 1.0);
		    double slow = 25.0;
		    m_slowAverage = (slow * m_slowAverage + m_distance) / (slow + 1.0);

		    SmartDashboard.putNumber("Lidar Distance", m_distance);
		}
		

	public double getM_distance() 
	{
		return m_distance;
	}

	public double getM_fastAverage() 
	{
		return m_fastAverage;
	}

	public double getM_slowAverage()
	{
		return m_slowAverage;
	}

	public byte getM_status() 
	{
		return m_status;
	}

	public double getAverage() 
	{
		double average = 0;
		for (int i = 0; i < 10; i++)
			average += (m_distances[i] / 10);
		return average;
	}

}