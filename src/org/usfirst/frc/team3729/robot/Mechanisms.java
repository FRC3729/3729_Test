package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Mechanisms extends Thread {	
	private Relay[] arm;
	private Relay intake;
	
	private Talon elevator0;
	private Talon elevator1;
	
	private DigitalInput[] limit_armout;
	
	private Encoder encoder_arm[];
	
	private boolean narrow_pinch = false;
	private boolean[] position_pinch = {false, false};
	private int tote = 0;
	
	Input _input;
	
	public Mechanisms() {
		arm[0] = new Relay(Params.port_Spike_arm[0]);
		arm[1] = new Relay(Params.port_Spike_arm[1]);
		intake = new Relay(Params.port_Spike_intake);
		
		elevator0 = new Talon(Params.port_Talon_elevator[0]);
		elevator1 = new Talon(Params.port_Talon_elevator[1]);

		limit_armout[0] = new DigitalInput(Params.port_Limit_arm[0]);
		limit_armout[1] = new DigitalInput(Params.port_Limit_arm[1]);
		
		encoder_arm[0] = new Encoder(Params.port_Encoder_aChannel_arm[0], Params.port_Encoder_bChannel_arm[0]);
		encoder_arm[1] = new Encoder(Params.port_Encoder_aChannel_arm[1], Params.port_Encoder_bChannel_arm[1]);
	}
	
	public void run() {
		intake();
		setarms();
		elevatorsimple(getTotes());
		if (!Params.testing_mech){elevator(getTotes());}
		//Dashboard Displays
		SmartDashboard.putBoolean("DB/Button 0", getPinch());
		SmartDashboard.putBoolean("DB/LED 0", position_pinch[0] && position_pinch[1]);
		SmartDashboard.putBoolean("DB/LED 1", limit_armout[0].get());
		SmartDashboard.putBoolean("DB/LED 2", limit_armout[1].get());
		SmartDashboard.putNumber("DB/Slider 0", getTotes());
	}
	
	//Give out testing values
	public void test() { 
		System.out.println("Left Arm Limit: " + limit_armout[0].get());
		System.out.println("Right Arm Limit: " + limit_armout[1].get());
		System.out.println("Totes: " + getTotes());
		System.out.println("Left Arm Encoder: " + encoder_arm[0].get());
		System.out.println("Right Arm Encoder: " + encoder_arm[1].get());
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
	private void setarms() { 
		if (_input.getAxis(2, 2) >= .75) { //Arms Seperate
			arms(0,-1);
			arms(1,-1);
			if (Params.testing_mech){ System.out.println("arms out");}
		} else if (_input.getAxis(2, 3) >= .75) { //Arms Close
			arms(0,1);
			arms(1,1);
			if (Params.testing_mech){ System.out.println("arms in");}
		} else if (_input.getButton(2, 3) && !limit_armout[0].get()) { //Arms Shift Left
			arm[0].set(Relay.Value.kForward);
			arm[1].set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms left");}
		} else if (_input.getButton(2, 2) && !limit_armout[1].get()) { //Arms Shift Right
			arm[0].set(Relay.Value.kReverse);
			arm[1].set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("arms right");}
		} else {
			arms(0,0);
			arms(1,0);
		} 
	}
	private void elevatorsimple(int totes) {
		if (_input.getAxis(2, 5) <= 0.0) {
			elevator0.set(_input.getAxis(2,5) * Params.speed_elevator[totes]);
			elevator1.set(_input.getAxis(2,5) * Params.speed_elevator[totes]);
		} else {
			elevator0.set(_input.getAxis(2,5) * Params.speed_creep);
			elevator1.set(_input.getAxis(2,5) * Params.speed_creep);
		}
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
	
	//Pinching Mechanism for the arms

	private void arms(int side, int state) {
		if (limit_armout[side].get()) {
			encoder_arm[side].reset();
		}
		switch (state) {
		case -1:
			if (!limit_armout[side].get()) {
				arm[side].set(Relay.Value.kReverse);
			}
			break;
		case 0:
			arm[side].set(Relay.Value.kOff);
			break;
		case 1:
			if (getPinch() && encoder_arm[side].get() < Params.position_arm[side][1]) {
				arm[side].set(Relay.Value.kForward);
			} else if (!getPinch() && encoder_arm[side].get() < Params.position_arm[side][0]) {
				arm[side].set(Relay.Value.kForward);
			} else {
				arm[side].set(Relay.Value.kOff);
				position_pinch[1] = true;
			}
			break;
		}
	}
	private boolean getPinch() {
		if (_input.xbox.getPOV() == 90) {
			narrow_pinch = false;
			try {
				Thread.sleep(150); //Make testing values actually readable
			} catch (Exception e){
				System.out.println(e);
			}
		} else if (_input.xbox.getPOV() == 270) {
			narrow_pinch = true;
			try {
				Thread.sleep(150); //Make testing values actually readable
			} catch (Exception e){
				System.out.println(e);
			}
		}
		return narrow_pinch;
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
		arm[0].set(Relay.Value.kOff);
		arm[1].set(Relay.Value.kOff);
		intake.set(Relay.Value.kOff);
		elevator0.set(0.0);
		elevator1.set(0.0);
	}
}
