package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Talon;

public class Drive {
	Input _input;
	
    private static Drive INSTANCE = null;
    
	private Talon leftMotor0;
    private Talon leftMotor1;
    private Talon rightMotor0;
    private Talon rightMotor1;
    private Talon centerMotor0;
    private Talon centerMotor1;
    
    private Drive() {
        leftMotor0 = new Talon(Params.port_fl);
        leftMotor1 = new Talon(Params.port_bl);
        rightMotor0 = new Talon(Params.port_fr);
        rightMotor1 = new Talon(Params.port_br);
        centerMotor0 = new Talon(Params.port_c0);
        centerMotor1 = new Talon(Params.port_c1);
        
        _input = new Input();
    }
    
    public static Drive getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Drive();
        
        return INSTANCE;
    }
    
    public void tank(double left, double right) {
        leftMotor0.set(left);
        leftMotor1.set(left);
        rightMotor0.set(right);
        rightMotor1.set(right);
        if(Params.testing_drive){System.out.println("left: " + left);}
        if(Params.testing_drive){System.out.println("right: " + right);}
    }
    
    public void arcade(double x, double y) {
        
//        if ((y <= 0.1 && y > 0) || (y >= -0.1 && y < 0)) {
//            this.tank(-x * 0.75, x * 0.75);
//        } else {
            double left = y-x;
            double right = y+x;
            left = Params.clamp(left, Params.MIN_SPEED, Params.MAX_SPEED);
            right = Params.clamp(right, Params.MIN_SPEED, Params.MAX_SPEED);        
            leftMotor0.set(-left);
            leftMotor1.set(-left);
            rightMotor0.set(right);
            rightMotor1.set(right);
            if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
//        }
    }
    
    public void Hdrive(double x, double y, double z) {
        centerMotor0.set(z);
        centerMotor1.set(z);
        if (Params.testing_drive){System.out.println("Center: " + z);}
        
    	double left = y-x;
        double right = y+x;
        left = Params.clamp(left, -.95, .95);
        right = Params.clamp(right, -.95, .95);        
        leftMotor0.set(-left);
        leftMotor1.set(-left);
        rightMotor0.set(right);
        rightMotor1.set(right);
        if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
    }
    
    public void Quad(double x, double y, double z){
    	centerMotor0.set(x);
    	centerMotor1.set(x);
    	if (Params.testing_drive){System.out.println("Center: " + x);}
    	
    	if (y <= .1 && y >= -.1) {
    		this.tank(z * .75, z * .75);
    	} else if (z <= .1 && z >= -.1) {
    		this.tank(-y, y);
    	} else { 
    		this.arcade(z, y);
    		System.out.println("!!We are still working on this portion of the code!!");
    	}
    }
    
    public void stop() {
        leftMotor0.set(0.0);
        leftMotor1.set(0.0);
        rightMotor0.set(0.0);
        rightMotor1.set(0.0);
        centerMotor0.set(0.0);
        centerMotor1.set(0.0);
    }

}
