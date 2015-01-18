package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;

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
	
	private Mechanisms() {
		arm0 = new Relay(Params.port_arm0);
		arm1 = new Relay(Params.port_arm1);
		intake = new Relay(Params.port_intake);
		heightadj = new Relay(Params.port_heightadj);
		
		elevator0 = new Talon(Params.port_elev0);
		elevator1 = new Talon(Params.port_elev1);
		ejector = new Talon(Params.port_eject);
		
		_input = new Input();
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void test() {
		
	}
	
	public void intake() {
		if (_input.getButton(2, 6)) {
			intake.set(Relay.Value.kReverse);
		} else if (_input.getButton(2, 5)) {
			intake.set(Relay.Value.kForward);
		} else {
			intake.set(Relay.Value.kOff);
		}
	}
	public void arms() {
		
	}
	public void elevator() {
		
	}
	public void ejector() {
		
	}
	public void height() {
		
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
