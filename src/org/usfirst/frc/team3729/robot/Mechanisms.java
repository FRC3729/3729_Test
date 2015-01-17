package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;

public class Mechanisms {
	
	Input _input;
	
	private static Mechanisms INSTANCE = null;
	
	private Relay testspike;
	
	private Mechanisms() {
		testspike = new Relay(Params.port_test);
		_input = new Input();
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void test() {
		if (_input.getButton(0, 1)) {
        	testspike.set(Relay.Value.kForward);
        	if (Params.testing_mech){System.out.println("Testing Forward");}
        } else if (_input.getButton(1, 1)) {
        	testspike.set(Relay.Value.kReverse);
        	if (Params.testing_mech){System.out.println("Testing Reverse");}
        }else {
        	testspike.set(Relay.Value.kOff);
        }
	}

}
