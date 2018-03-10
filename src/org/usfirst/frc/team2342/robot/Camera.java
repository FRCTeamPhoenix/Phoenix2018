package org.usfirst.frc.team2342.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera{
	
	private UsbCamera camera;
    
	//private CvSink cvSink;
	
	public Camera(int resolutionX, int resolutionY, int cameraIndex, int fps, CameraServer server){
		
		/*camera = CameraServer.getInstance().startAutomaticCapture(cameraName, cameraIndex);
	    camera.setResolution(resolutionX, resolutionY);
	    camera.setFPS(60);*/
		camera = server.startAutomaticCapture(cameraIndex);

		camera.setResolution(resolutionX, resolutionY);
		camera.setFPS(fps);
		camera.setExposureAuto();
		
	    //cvSink = CameraServer.getInstance().getVideo(camera);
	}
	
	/*public UsbCamera getCamera(){
		return camera;
	}*/
}



/*public class Camera implements Runnable{

private UsbCamera camera;

private CvSink cvSink;
private CvSource outputStream;

private Mat source;
private Mat output;

public Camera(int resolutionX, int resolutionY, String cameraName, int cameraIndex, CvSource out){
	
	camera = CameraServer.getInstance().startAutomaticCapture(cameraName, cameraIndex);
    camera.setResolution(resolutionX, resolutionY);
    
    camera.setFPS(30);
    
    cvSink = CameraServer.getInstance().getVideo(camera);
    outputStream = out;
    
    
    source = new Mat(resolutionX, resolutionY, CvType.CV_32FC3);
    output = new Mat();
}

//Run the camera
public void run(){
    
    while(!Thread.interrupted()) {
    	cvSink.setEnabled(true);
        cvSink.grabFrame(source);
        outputStream.putFrame(source);
        //Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
    }
    System.out.println("im out here");
    cvSink.setEnabled(false);
}
}*/