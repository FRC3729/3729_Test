package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Mechanisms {
	
	Input _input;
	
	private static Mechanisms INSTANCE = null;
	
	private Relay arm0;
	private Relay arm1;
	private Relay intake;
	
	private Talon elevator0;
	private Talon elevator1;
	
	private DigitalInput limit_armsout;
	private DigitalInput limit_armsout_safety;
	
	private Encoder encoder_elevator;
	
	private boolean elevator;
	private boolean lvl_change;
	private int lvl;
	
	private Mechanisms() {
		arm0 = new Relay(Params.port_arm0);
		arm1 = new Relay(Params.port_arm1);
		intake = new Relay(Params.port_intake);
		
		elevator0 = new Talon(Params.port_elev0);
		elevator1 = new Talon(Params.port_elev1);

		limit_armsout = new DigitalInput(Params.port_armslimit);
		limit_armsout_safety = new DigitalInput(Params.port_armslimit_safety);
		
		encoder_elevator = new Encoder(Params.port_encoder_elevator_aChannel, Params.port_encoder_elevator_bChannel);
		
		_input = new Input();
		
		elevator = false;
		lvl_change = true;
		
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void test() {
		System.out.println("Limit: " + limit_armsout.get());
		System.out.println("Limit safety: " + limit_armsout_safety.get());
		System.out.println("Encoder: " + encoder_elevator.get());
	}
	
	public void setlvl(int level) {
		if (lvl_change) {
			lvl = level;
		} else { }
	}
	public int getlvl() {
		System.out.println("lvl: " + lvl);
		return lvl;
	}
	
	public void intake() {
		if (_input.getButton(2, 6)) {
			intake.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("Intake going in");}
		} else if (_input.getButton(2, 5)) {
			intake.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("Intake going out");}
		} else {
			intake.set(Relay.Value.kOff);
		}
	}
	public void arms() {
		if (_input.getAxis(2, 2) >= .75 && !limit_armsout.get() && !limit_armsout_safety.get()) {
			arm0.set(Relay.Value.kForward);
			arm1.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("arms out, limit: " + limit_armsout.get());}
		} else if (_input.getAxis(2, 3) >= .75) {
			arm0.set(Relay.Value.kReverse);
			arm1.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms in");}
		} else if (_input.getButton(2, 3)) {
			arm0.set(Relay.Value.kReverse);
			arm1.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("arms left");}
		} else if (_input.getButton(2, 2)) {
			arm0.set(Relay.Value.kForward);
			arm1.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms right");}
		} else {
			arm0.set(Relay.Value.kOff);
			arm1.set(Relay.Value.kOff);
		} 
	}
	public void elevator() {
		//Basic elevator code
		if (_input.getButton(2, 4)) {
			elevator0.set(Params.elevator_speed);
			elevator1.set(Params.elevator_speed);
			if (Params.testing_mech){ System.out.println("elevator up");}
		} else if (_input.getButton(2, 1)) {
			elevator0.set(-Params.elevator_speed);
			elevator1.set(-Params.elevator_speed);
			if (Params.testing_mech){ System.out.println("elevator down");}
		}		
		
		//Elevator Level control
		if (_input.getButton(0,11)) {
			//Move up a level
			setlvl(getlvl() + 1);
			elevator = true;
			lvl_change = false;
		} else if (_input.xbox.getPOV() == 180) {
			//Move down a level
			setlvl(getlvl() - 1);
			elevator = true;
			lvl_change = false;
		}
		setelevator();
	}
	
	private void setelevator() {
		if (elevator) {
			switch (getlvl()) {
				case -1:
					elevator0.set(0.0);
					elevator1.set(0.0);
					elevator = false;
					lvl_change = true;
				case 0: 
					if (encoder_elevator.get() < Params.level_0) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_0) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 1: 
					if (encoder_elevator.get() < Params.level_1) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_1) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 2: 
					if (encoder_elevator.get() < Params.level_2) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_2) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 3: 
					if (encoder_elevator.get() < Params.level_3) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_3) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 4: 
					if (encoder_elevator.get() < Params.level_4) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_4) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 5: 
					if (encoder_elevator.get() < Params.level_top) {
						elevator0.set(Params.elevator_speed);
						elevator1.set(Params.elevator_speed);
					} else if (encoder_elevator.get() > Params.level_top) {
						elevator0.set(-Params.elevator_speed);
						elevator1.set(-Params.elevator_speed);
					} else {
						elevator = false;
						lvl_change = true;
					}
				case 6:
					elevator0.set(0.0);
					elevator1.set(0.0);
					elevator = false;
					lvl_change = true;
			}
		} else {
			elevator0.set(0.0);
			elevator1.set(0.0);
		}
	}
	
	public void stop() {
		arm0.set(Relay.Value.kOff);
		arm1.set(Relay.Value.kOff);
		intake.set(Relay.Value.kOff);
		elevator0.set(0.0);
		elevator1.set(0.0);
	}
	
	
	
	
	
	
}
