package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Talon;

public class Drive {
	
    private static Drive INSTANCE = null;
    
	private Talon leftMotor1;
    private Talon leftMotor2;
    private Talon rightMotor1;
    private Talon rightMotor2;
    private Talon centerMotor;
//    private double x_prev, y_prev, z_prev;
    
    private Drive() {
        leftMotor1 = new Talon(Params.port_fl);
        leftMotor2 = new Talon(Params.port_bl);
        rightMotor1 = new Talon(Params.port_fr);
        rightMotor2 = new Talon(Params.port_br);
        centerMotor = new Talon(Params.port_c);
//        x_prev = y_prev = z_prev = 0.0;
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
    
    public void arcade(double x, double y) {
        
        if ((y <= 0.1 && y > 0) || (y >= -0.1 && y < 0)) {
            this.tank(-x * 0.75, x * 0.75);
        } else {
            double left = y-x;
            double right = y+x;
            left = Input.clamp(left, -1.0, 1.0);
            right = Input.clamp(right, -1.0, 1.0);        
            leftMotor1.set(-left);
            leftMotor2.set(-left);
            rightMotor1.set(right);
            rightMotor2.set(right);
            if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
        }
    }
    
    public void Hdrive(double x, double y, double z) {
        centerMotor.set(-z);
        if (Params.testing_drive){System.out.println("Center: " + z);}
        
    	double left = y-x;
        double right = y+x;
        left = Input.clamp(left, -1.0, 1.0);
        right = Input.clamp(right, -1.0, 1.0);        
        leftMotor1.set(-left);
        leftMotor2.set(-left);
        rightMotor1.set(right);
        rightMotor2.set(right);
        if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
    }
    
    public void Quad(double x, double y, double z){
    	centerMotor.set(x);
    	if (Params.testing_drive){System.out.println("Center: " + x);}
    	
    	if (y <= .1 && y >= -.1) {
    		this.tank(z * .75, z * .75);
    	} else if (z <= .1 && z >= -.1) {
    		this.tank(-y, y);
    	} else {
    		
    		System.out.println("!!We are still working on this portion of the code!!");
    	}
    }
    
    public void stop() {
        leftMotor1.set(0.0);
        leftMotor2.set(0.0);
        rightMotor1.set(0.0);
        rightMotor2.set(0.0);
        centerMotor.set(0.0);
    }

}
