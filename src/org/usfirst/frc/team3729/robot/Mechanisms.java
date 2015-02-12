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
	private DigitalInput limit_encoder_reset;
	
	private Encoder encoder_elevator;
	
	private boolean elevator;
	private int lvl;
	
	Input _input;
	
	private Mechanisms() {
		arm0 = new Relay(Params.port_Spike_arm[0]);
		arm1 = new Relay(Params.port_Spike_arm[1]);
		intake = new Relay(Params.port_Spike_intake);
		
		elevator0 = new Talon(Params.port_Talon_elevator[0]);
		elevator1 = new Talon(Params.port_Talon_elevator[1]);

		limit_arm0out = new DigitalInput(Params.port_Limit_arm[0]);
		limit_arm1out = new DigitalInput(Params.port_Limit_arm[1]);
		limit_encoder_reset = new DigitalInput(Params.port_Limit_encoder_reset);
		
		encoder_elevator = new Encoder(Params.port_Encoder_elevator_aChannel, Params.port_Encoder_elevator_bChannel);
		
		_input = new Input();
		
		elevator = false;
		
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void run() {
		intake();
		arms();
		setelevator(getelevator());
		//Dashboard Displays
		SmartDashboard.putBoolean("DB/LED 0", !elevator); //Display whether the Elevator is in position
		SmartDashboard.putNumber("DB/Slider 0", lvl); //Display the Elevator's current level
	}
	
	//Give out testing values
	public void test() { 
		System.out.println("Left Arm Limit: " + limit_arm0out.get());
		System.out.println("Right Arm Limit: " + limit_arm1out.get());
		System.out.println("Encoder: " + encoder_elevator.get());
		System.out.println("Elevator level: " + lvl);
		try {
			Thread.sleep(100); //Make testing values actually readable
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	//Controls belts on the arms
	public void intake() { 
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
	public void arms() { 
		if (_input.getAxis(2, 2) >= .75) { //Arms Seperate
			if (!limit_arm0out.get()) {
				arm0.set(Relay.Value.kForward);
			} if (!limit_arm1out.get()) {
				arm1.set(Relay.Value.kReverse);
			}
			if (Params.testing_mech){ System.out.println("arms out");}
		} else if (_input.getAxis(2, 3) >= .75) { //Arms Close
			arm0.set(Relay.Value.kReverse);
			arm1.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms in");}
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
	
	public void setlvl(int level) {
		if (!elevator) {
			if (lvl > 0 && lvl < 5) { //Make sure it is a valid level
				lvl = level;
			} else {}
		} else {}
	}

	public int getelevator() { 		
		//!Reset encoder to keep values accurate
		if (limit_encoder_reset.get()) {
			encoder_elevator.reset();
		}
		//!Elevator Level control
		if (_input.xbox.getPOV() == 0) { //Move up a level
			if (!elevator) {
				if (lvl > 0 && lvl < 5) {
					lvl++;
				}
			}
			elevator = true;
		} else if (_input.xbox.getPOV() == 180) { //Move down a level
			if (!elevator) {
				if (lvl > 0 && lvl < 5) {
					lvl--;
				}
			}
			elevator = true;
		}
		return lvl;
	}
	//Move the Elevator
	private void setelevator(int level) {
		//!Manual Elevator adjust
		if (_input.getButton(2, 4)) { //Elevator up
			elevator0.set(Params.speed_elevator);
			elevator1.set(Params.speed_elevator);
			if (Params.testing_mech){ System.out.println("elevator up");}
		} else if (_input.getButton(2, 1)) { //Elevator Down
			elevator0.set(-Params.speed_elevator);
			elevator1.set(-Params.speed_elevator);
			if (Params.testing_mech){ System.out.println("elevator down");}
		}
		//!Elevator Level adjust
		if (elevator) {
			if (encoder_elevator.get() < Params.level_elevator[level]) { //If below, go Up
				elevator0.set(Params.speed_elevator);
				elevator1.set(Params.speed_elevator);
			} else if (encoder_elevator.get() > Params.level_elevator[level]) {//If above, go Down
				elevator0.set(-Params.speed_elevator);
				elevator1.set(-Params.speed_elevator);
			} else {
				elevator0.set(0.0);
				elevator1.set(0.0);
				elevator = false;
			}
		} else {
			elevator0.set(0.0);
			elevator1.set(0.0);
		}
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
