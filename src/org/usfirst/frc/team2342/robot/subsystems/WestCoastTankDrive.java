package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.loops.Looper;

public class WestCoastTankDrive extends Subsystem {
    
    private static WestCoastTankDrive mInstance = new WestCoastTankDrive();
    
    public static WestCoastTankDrive getInstance() {
        return mInstance;
    }
    
    private WestCoastTankDrive() {
        // TODO initialization here
    }
    
    @Override
    public void outputToSmartDashboard() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerEnabledLoops(Looper enabledLooper) {
        // TODO Auto-generated method stub
        
    }

}
