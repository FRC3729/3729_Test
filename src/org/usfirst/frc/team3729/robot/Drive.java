package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Drive {
	Input _input;
	
    private static Drive INSTANCE = null;
    
	private Talon leftMotor0;
    private Talon leftMotor1;
    private Talon rightMotor0;
    private Talon rightMotor1;
    private Talon centerMotor0;
    private Talon centerMotor1;
    
    private Ultrasonic sonar0;
    private Ultrasonic sonar1;
    
    private Drive() {
        leftMotor0 = new Talon(Params.port_fl);
        leftMotor1 = new Talon(Params.port_bl);
        rightMotor0 = new Talon(Params.port_fr);
        rightMotor1 = new Talon(Params.port_br);
        centerMotor0 = new Talon(Params.port_c0);
        centerMotor1 = new Talon(Params.port_c1);
        
        sonar0 = new Ultrasonic(Params.port_sonar0_out, Params.port_sonar0_in, Ultrasonic.Unit.kInches);
        sonar1 = new Ultrasonic(Params.port_sonar1_out, Params.port_sonar1_in);
        sonar0.setEnabled(true);
        
        _input = new Input();
    }
    
    public static Drive getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Drive();
        
        return INSTANCE;
    }
    
    public void align() {
    	Timer.delay(.2);
//    	sonar0.setAutomaticMode(true);
//    	sonar0.ping();
    	System.out.println("Sonar0: " + sonar0.isRangeValid());
//    	if (sonar0.getRangeInches() > sonar1.getRangeInches()) {
//    		this.tank(Params.creep_speed, 0.0);
//    	} else if (sonar0.getRangeInches() < sonar1.getRangeInches()) {
//    		this.tank(0.0, Params.creep_speed);
//    	} else {
//    		this.stop();
//    	}
    }
    
    public void test() {
    	System.out.println("Sonar0: " + sonar0.getRangeInches());
    	System.out.println("Sonar1: " + sonar1.getRangeInches());
    	System.out.println("left0 : " + leftMotor0.get());
    	System.out.println("left1 : " + leftMotor1.get());
    	System.out.println("right0 : " + rightMotor0.get());
    	System.out.println("right1 : " + rightMotor1.get());
    	System.out.println("center0 : " + centerMotor0.get());
    	System.out.println("center1 : " + centerMotor1.get());
    }
    
    public void tank(double left, double right) {
        leftMotor0.set(left);
        leftMotor1.set(left);
        rightMotor0.set(right);
        rightMotor1.set(right);
//        if(Params.testing_drive){System.out.println("left: " + left);}
//        if(Params.testing_drive){System.out.println("right: " + right);}
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
//            if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
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
//        if (Params.testing_drive){System.out.println("Left: " + left + "Right: " + right + " .");}
    }
    public void Quad(double x, double y, double z){
    	centerMotor0.set(x);
    	centerMotor1.set(x);
//    	if (Params.testing_drive){System.out.println("Center: " + x);}
    	
    	if (y <= .1 && y >= -.1) {
    		this.tank(z * .75, z * .75);
    	} else if (z <= .1 && z >= -.1) {
    		this.tank(-y, y);
    	} else { 
    		this.arcade(z, y);
    		System.out.println("!!This code is completly untested!!");
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
