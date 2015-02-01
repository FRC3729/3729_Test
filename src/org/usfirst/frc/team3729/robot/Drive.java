package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogInput;

public class Drive {
	Input _input;
	
    private static Drive INSTANCE = null;
    
	private Talon leftMotor0;
    private Talon leftMotor1;
    private Talon rightMotor0;
    private Talon rightMotor1;
    private Talon centerMotor0;
    private Talon centerMotor1;
    
    private AnalogInput sonar0;
    private AnalogInput sonar1;
    
    private Drive() {
        leftMotor0 = new Talon(Params.port_l0);
        leftMotor1 = new Talon(Params.port_l1);
        rightMotor0 = new Talon(Params.port_r0);
        rightMotor1 = new Talon(Params.port_r1);
        centerMotor0 = new Talon(Params.port_c0);
        centerMotor1 = new Talon(Params.port_c1);
        
        sonar0 = new AnalogInput(Params.port_sonar0);
        sonar1 = new AnalogInput(Params.port_sonar1);
        
        _input = new Input();
    }
    
    public static Drive getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Drive();
        
        return INSTANCE;
    }
    
    //!Sonar Auto-Align
    public void align() {
    	if (sonar0.getValue() > sonar1.getValue()) {
    		this.tank(Params.creep_speed, 0.0);
    	} else if (sonar0.getValue() < sonar1.getValue()) {
    		this.tank(0.0, Params.creep_speed);
    	} else {
    		this.stop();
    	}
    }
    
    //Drive values for testing
    public void test() {
    	System.out.println("Sonar0: " + sonar0.getValue());
    	System.out.println("Sonar1: " + sonar1.getValue());
    	System.out.println("left : " + leftMotor0.get() + ", " + leftMotor1.get());
    	System.out.println("Right : " + rightMotor0.get() + ", " + rightMotor1.get());
    	System.out.println("Center : " + centerMotor0.get() + ", " + centerMotor1.get());
    }
    
    //Drive Modes
    //!Tank Drive
    public void tank(double left, double right) {
        leftMotor0.set(left);
        leftMotor1.set(left);
        rightMotor0.set(right);
        rightMotor1.set(right);
    }    
    //!Arcade Drive
    public void arcade(double x, double y) {
        double left = y-x;
        double right = y+x;
        left = Params.clamp(left, Params.MIN_SPEED, Params.MAX_SPEED);
        right = Params.clamp(right, Params.MIN_SPEED, Params.MAX_SPEED);        
        leftMotor0.set(-left);
        leftMotor1.set(-left);
        rightMotor0.set(right);
        rightMotor1.set(right);
    }
    //!H Drive
    public void Hdrive(double x, double y, double z) {
        centerMotor0.set(z);
        centerMotor1.set(z);
        
    	double left = y-x;
        double right = y+x;
        left = Params.clamp(left, -.95, .95);
        right = Params.clamp(right, -.95, .95);        
        leftMotor0.set(-left);
        leftMotor1.set(-left);
        rightMotor0.set(right);
        rightMotor1.set(right);
    }
    //!Quad Drive
    public void Quad(double x, double y, double z){
    	centerMotor0.set(x);
    	centerMotor1.set(x);
    	
    	if (y <= .1 && y >= -.1) {
    		this.tank(z * .75, z * .75);
    	} else if (z <= .1 && z >= -.1) {
    		this.tank(-y, y);
    	} else { 
    		this.arcade(z, y);
    		System.out.println("!!This code is completly untested!!");
    	}
    }
    //!Stopped
    public void stop() {
        leftMotor0.set(0.0);
        leftMotor1.set(0.0);
        rightMotor0.set(0.0);
        rightMotor1.set(0.0);
        centerMotor0.set(0.0);
        centerMotor1.set(0.0);
    }

}
