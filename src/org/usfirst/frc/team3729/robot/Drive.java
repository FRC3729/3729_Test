package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Talon;

public class Drive {
    private static Drive INSTANCE = null;
    
	Talon leftMotor1;
    Talon leftMotor2;
    Talon rightMotor1;
    Talon rightMotor2;
    
    private Drive() {
        leftMotor1 = new Talon(Params.port_fl);
        leftMotor2 = new Talon(Params.port_bl);
        rightMotor1 = new Talon(Params.port_fr);
        rightMotor2 = new Talon(Params.port_br);   
    }
    
    public static Drive getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Drive();
        
        return INSTANCE;
    }
    
    public void tank(double left, double right) {
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);
        if(Params.testing_drive){System.out.println("left: " + left);}
        if(Params.testing_drive){System.out.println("right: " + right);}
    }
    
    public void stop() {
        leftMotor1.set(0.0);
        leftMotor2.set(0.0);
        rightMotor1.set(0.0);
        rightMotor2.set(0.0);
    }

}
