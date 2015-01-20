package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;

public class Mechanisms {
	
	Input _input;
	
	private static Mechanisms INSTANCE = null;
	
	private Relay arm0;
	private Relay arm1;
	private Relay intake;
	private Relay heightadj;
	
	private Talon elevator0;
	private Talon elevator1;
	private Talon ejector;
	
	private DigitalInput limit_armsout;
	private DigitalInput limit_armsout_safety;
	private DigitalInput limit_slide_forward;
	private DigitalInput limit_slide_reverse;
	private DigitalInput limit_heightadj_up;
	private DigitalInput limit_heightadj_down;
	
	private Mechanisms() {
		arm0 = new Relay(Params.port_arm0);
		arm1 = new Relay(Params.port_arm1);
		intake = new Relay(Params.port_intake);
		heightadj = new Relay(Params.port_heightadj);
		
		elevator0 = new Talon(Params.port_elev0);
		elevator1 = new Talon(Params.port_elev1);
		ejector = new Talon(Params.port_eject);

		limit_armsout = new DigitalInput(Params.port_armslimit);
		limit_armsout_safety = new DigitalInput(Params.port_armslimit_safety);
		limit_slide_forward = new DigitalInput(Params.port_limit_slide_forward);
		limit_slide_reverse = new DigitalInput(Params.port_limit_slide_reverse);
		limit_heightadj_up = new DigitalInput(Params.port_limit_heightadj_up);
		limit_heightadj_down = new DigitalInput(Params.port_limit_heightadj_down);
		
		_input = new Input();
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void test() {
//		System.out.println("Limit: " + limit_armsout.get());
//		System.out.println("Limit safety: " + limit_armsout_safety.get());
		
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
		if (_input.getAxis(2, 2) >= .75 && limit_armsout.get() && limit_armsout_safety.get()) {
			arm0.set(Relay.Value.kForward);
			arm1.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("arms out, limit: " + !limit_armsout.get());}
		} else if (_input.getAxis(2, 3) >= .75) {
			arm0.set(Relay.Value.kReverse);
			arm1.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("arms in");}
		} else {
			arm0.set(Relay.Value.kOff);
			arm1.set(Relay.Value.kOff);
		}
	}
	public void elevator() {
		//basic elevator code
		/*
		 *	if (elevator0.encoder.get() > elevator1.encoder.get()) {
		 * 		elevator0.set(Params.elevator_speed_low);
		 * 		elevator1.set(Params.elevator_speed);
		 * 	} else if (elevator0.encoder.get() < elevator0.encoder.get()) {
		 * 		elevator0.set(Params.elevator_speed);
		 * 		elevator1.set(Params.elevator_speed_low);
		 * 	} else {
		 * 		elevator0.set(Params.elevator_speed);
		 * 		elevator1.set(Params.elevator_speed);
		 * 	}
		 * 
		 */
		if (_input.getButton(2, 4)) {
			elevator0.set(Params.elevator_speed);
			elevator1.set(Params.elevator_speed);
			if (Params.testing_mech){ System.out.println("elevator up");}
		} else if (_input.getButton(2, 1)) {
			elevator0.set(-Params.elevator_speed);
			elevator1.set(-Params.elevator_speed);
			if (Params.testing_mech){ System.out.println("elevator down");}
		}
	}
	public void ejector() {
		//HOW IN THE HELL AM I GOING TO DO THIS ACCELERATION NONSENSE???
		//non accelerated version
		if (_input.getAxis(2, 5) <= -.75 && limit_slide_forward.get()) {
			ejector.set(.5);
			if (Params.testing_mech){ System.out.println("Ejector pushing");}
		} else if (_input.getAxis(2, 5) >= .75 && limit_slide_reverse.get()) {
			ejector.set(-.5);
			if (Params.testing_mech){ System.out.println("Ejector resetting");}
		} else {
			ejector.set(0.0);
		}
	}
	public void height() {
		if (_input.getAxis(2, 1) >= .75 && limit_heightadj_down.get()) {
			heightadj.set(Relay.Value.kReverse);
			if (Params.testing_mech){ System.out.println("height going down");}
		} else if (_input.getAxis(2, 1) <= -.75 && limit_heightadj_up.get()) {
			heightadj.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("height going up");}
		} else if (!limit_heightadj_up.get() && !limit_slide_reverse.get()) {
			heightadj.set(Relay.Value.kForward);
			if (Params.testing_mech){ System.out.println("height going up");}
		} else {
			heightadj.set(Relay.Value.kOff);
		}
	}
	public void stop() {
		arm0.set(Relay.Value.kOff);
		arm1.set(Relay.Value.kOff);
		intake.set(Relay.Value.kOff);
		heightadj.set(Relay.Value.kOff);
		elevator0.set(0.0);
		elevator1.set(0.0);
		ejector.set(0.0);
	}
	
	
	
	
	
	
}
