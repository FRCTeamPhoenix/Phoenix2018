package org.usfirst.frc.team2342.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.first.wpilibj.CameraServer;


public class CameraControl{
	
	private Camera[] cameras;
	
	UsbCameraInfo[] cameraInfo;
	CameraServer cameraServer;
	
	/*private int currentCamera;
	
	private CvSink in;
	
	private CvSource out;
	
	private Mat source;*/
	
	
	
	//Initialize all the cameras
	public CameraControl(int resolutionX, int resolutionY, int fps){
		
		//If one output ever works
		/*out = CameraServer.getInstance().putVideo("CameraMain", resolutionX, resolutionY);
		out.setFPS(30);*/
		
		//source = new Mat(resolutionX, resolutionY, CvType.CV_32FC3);
		
		/*cameras = new Camera[cameraIndexes.length];
		
		for(int i = 0; i < cameraIndexes.length; i++){
			
			cameras[i] = new Camera(resolutionX, resolutionY, ("Camera " + i), cameraIndexes[i]);
		}*/
		
		cameraServer = CameraServer.getInstance();
		cameraInfo = UsbCamera.enumerateUsbCameras();
		cameras = new Camera[cameraInfo.length];
		for (int i = 0; i < cameras.length; i++) {
			cameras[i] = new Camera(resolutionX, resolutionY, i, fps, cameraServer);
		}
		
	}
	
	/*public void switchTo(int i){
		currentCamera = i;
		in.setSource(cameras[i].getCamera());
	}*/
	
	/*public void run(){
		
		new Thread(() -> {
			
			in.setEnabled(true);
			
	        while(!Thread.interrupted()) {
	        	
	            in.grabFrame(source);
	            
	            //out.putFrame(source);
	        }
	        
	        System.out.println("im out here");
	        in.setEnabled(false);
	        
		}).start();
	}*/
}


/*public class CameraControl{

private Thread[] cameras;

private int currentCamera;

//Initialize all the cameras
public CameraControl(int[] cameraIndexes,  int resolutionX, int resolutionY){
	
	//CvSource out = CameraServer.getInstance().putVideo("Stuff", resolutionX, resolutionY);
	//out.setFPS(30);
	
	CvSink in =
	
	
	cameras = new Thread[cameraIndexes.length];
	
	for(int i = 0; i < cameraIndexes.length; i++){
		
		Runnable camera = new Camera(resolutionX, resolutionY, ("Camera " + i), cameraIndexes[i], out);
		Thread thread = new Thread(camera);
		cameras[i] = thread;
	}
	
	currentCamera = 0;
	cameras[0].start();
}


//Stop the current camera
public void stop(){
	cameras[currentCamera].interrupt();
}

//Switch to another camera
public void switchTo(int i){
	
	stop();
	
	currentCamera = i;
	
	cameras[i].start();
}

}*/