package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Mechanisms extends Thread {
	private static Mechanisms INSTANCE = null;
	
	private Relay arm0;
	private Relay arm1;
	private Relay intake;
	
	private Talon elevator0;
	private Talon elevator1;
	
	private DigitalInput limit_arm0out;
	private DigitalInput limit_arm1out;
	
	private Encoder encoder_arm0;
	private Encoder encoder_arm1;
	
	private boolean narrow_pinch;
	private int tote;
	
	Input _input;
	
	private Mechanisms() {
		arm0 = new Relay(Params.port_Spike_arm[0]);
		arm1 = new Relay(Params.port_Spike_arm[1]);
		intake = new Relay(Params.port_Spike_intake);
		
		elevator0 = new Talon(Params.port_Talon_elevator[0]);
		elevator1 = new Talon(Params.port_Talon_elevator[1]);

		limit_arm0out = new DigitalInput(Params.port_Limit_arm[0]);
		limit_arm1out = new DigitalInput(Params.port_Limit_arm[1]);
		
		encoder_arm0 = new Encoder(Params.port_Encoder_aChannel_arm[0], Params.port_Encoder_bChannel_arm[0]);
		encoder_arm1 = new Encoder(Params.port_Encoder_aChannel_arm[1], Params.port_Encoder_bChannel_arm[1]);
		
		narrow_pinch = false;
		tote = 0;
		
		_input = new Input();
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void run() {
		intake();
		arms();
		elevatorsimple(getTotes());
//		elevator(getTotes());
		//Dashboard Displays
		SmartDashboard.putBoolean("DB/LED 0", limit_arm0out.get());
		SmartDashboard.putBoolean("DB/LED 1", limit_arm1out.get());
		SmartDashboard.putNumber("DB/Slider 0", getTotes());
	}
	
	//Give out testing values
	public void test() { 
		System.out.println("Left Arm Limit: " + limit_arm0out.get());
		System.out.println("Right Arm Limit: " + limit_arm1out.get());
		System.out.println("Totes: " + getTotes());
		System.out.println("Left Arm Encoder: " + encoder_arm0.get());
		System.out.println("Right Arm Encoder: " + encoder_arm1.get());
		try {
			Thread.sleep(100); //Make testing values actually readable
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	//Controls belts on the arms
	private void intake() { 
		if (_input.getButton(2, 6)) { //Run the belts in
			intake.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("Intake going in");}
		} else if (_input.getButton(2, 5)) { //Run the belts out
			intake.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("Intake going out");}
		} else {
			intake.set(Relay.Value.kOff);
		}
	}
	//Pinching Mechanism for the arms
	private void arms() { 
		getPinch();
		if (_input.getAxis(2, 2) >= .75) { //Arms Seperate
			if (!limit_arm0out.get()) {
				arm0.set(Relay.Value.kForward);
			} if (!limit_arm1out.get()) {
				arm1.set(Relay.Value.kReverse);
			}
			if (Params.testing_mech){ System.out.println("arms out");}
		} else if (_input.getAxis(2, 3) >= .75) { //Arms Close
			if (narrow_pinch) {	
				if (encoder_arm0.get() < Params.position_arm[0][2]) {
					arm0.set(Relay.Value.kReverse);
				}
				if (encoder_arm1.get() < Params.position_arm[1][2]) {
					arm1.set(Relay.Value.kForward);
				}
				if (Params.testing_mech){ System.out.println("arms in");}
			} else if (!narrow_pinch) {
				if (encoder_arm0.get() < Params.position_arm[0][1]) {
					arm0.set(Relay.Value.kReverse);
				}
				if (encoder_arm1.get() < Params.position_arm[1][1]) {
					arm1.set(Relay.Value.kForward);
				}
				if (Params.testing_mech){ System.out.println("arms in");}
			}
		} else if (_input.getButton(2, 3) && !limit_arm0out.get()) { //Arms Shift Left
			arm0.set(Relay.Value.kForward);
			arm1.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms left");}
		} else if (_input.getButton(2, 2) && !limit_arm1out.get()) { //Arms Shift Right
			arm0.set(Relay.Value.kReverse);
			arm1.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("arms right");}
		} else {
			arm0.set(Relay.Value.kOff);
			arm1.set(Relay.Value.kOff);
		} 
	}
	private void getPinch() {
		if (limit_arm0out.get()) {
			encoder_arm0.reset();
		}
		if (limit_arm1out.get()) {
			encoder_arm1.reset();
		}
		if (_input.xbox.getPOV() == 90) {
			narrow_pinch = false;
			try {
				Thread.sleep(100); //Make testing values actually readable
			} catch (Exception e){
				System.out.println(e);
			}
		} else if (_input.xbox.getPOV() == 270) {
			narrow_pinch = true;
			try {
				Thread.sleep(100); //Make testing values actually readable
			} catch (Exception e){
				System.out.println(e);
			}
		}
	}
	
	private void elevatorsimple(int totes) {
//		if (_input.getAxis(2, 5) <= 0.0) {
//			elevator0.set(_input.getAxis(2,5) * Params.speed_creep);
//			elevator1.set(_input.getAxis(2,5) * Params.speed_creep);
//		} else {
			elevator0.set(_input.getAxis(2,5) * Params.speed_elevator[totes]);
			elevator1.set(_input.getAxis(2,5) * Params.speed_elevator[totes]);
//		}
	}

	private void elevator(int totes) {
		if (_input.getButton(2, 4)) {
			elevator0.set(-Params.speed_elevator[totes]);
			elevator1.set(-Params.speed_elevator[totes]);
			if (Params.testing_mech){ System.out.println("Elevator speed: " + (Params.speed_elevator[totes]));}
		} else if (_input.getButton(2, 1)) {
			elevator0.set(Params.speed_creep);
			elevator1.set(Params.speed_creep);
			if (Params.testing_mech){ System.out.println("Elevator speed: " + (Params.speed_creep));}
		}
	}
	private int getTotes() {
		if (_input.xbox.getPOV() == 0 && tote < 5) {
				tote++;
				try {
					Thread.sleep(150); 
				} catch (Exception e){
					System.out.println(e);
				}
		} else if (_input.xbox.getPOV() == 180) {
				tote = 0;
				try {
					Thread.sleep(150); 
				} catch (Exception e){
					System.out.println(e);
				}
		}
		return tote;
	}
	
	//STOP EVERYTHING
	public void stopmotors() { 
		arm0.set(Relay.Value.kOff);
		arm1.set(Relay.Value.kOff);
		intake.set(Relay.Value.kOff);
		elevator0.set(0.0);
		elevator1.set(0.0);
	}
}
