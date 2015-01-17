package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Relay;

public class Mechanisms {
	
	private static Mechanisms INSTANCE = null;
	
	private Relay testspike;
	
	private Mechanisms() {
		testspike = new Relay(Params.port_test);
	}
	
	public static Mechanisms getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mechanisms();
        
        return INSTANCE;
    }
	
	public void test() {
		testspike.set(Relay.Value.kForward);
	if (Params.testing_mech){System.out.println("Testing forward");}
	}
}
