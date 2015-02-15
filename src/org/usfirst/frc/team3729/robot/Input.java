package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Input { 
    public final Joystick joy0;
    public final Joystick joy1;
    public final Joystick xbox;
    
    public Input() {
        joy0 = new Joystick(0);
        joy1 = new Joystick(1);
        xbox = new Joystick(2);
    }

    //!Testing values
    public void test() {
    	System.out.println("Joy0 X axis: " + getAxis(0, 0));
    	System.out.println("Joy0 Y axis: " + getAxis(0, 1));
    	System.out.println("Joy1 X axis: " + getAxis(1, 0));
    	System.out.println("Joy1 Y axis: " + getAxis(1, 1));
    	Timer.delay(.1);
    }
    
    //!Button control
    public boolean getButton(int joy, int buttonid) {
        switch (joy) {
            case 0:
                return joy0.getRawButton(buttonid);
            case 1:
                return joy1.getRawButton(buttonid);
            case 2:
            	return xbox.getRawButton(buttonid);
            	//A:1,B:2,X:3,Y:4,LB:5,RB:6,Back:7,Start:8,LS:9,RS:10
            default:
                return getButton(0, buttonid);
        }
    }
    //!Axis control
    public double getAxis(int joy, int axis) {
    	switch(joy) {
    		case 0: //X:0,Y:1,Z/Twist:2
    			double joy0_axis = joy0.getRawAxis(axis);
    			double joy0_axis_ = Params.clamp(joy0_axis, Params.speed_min, Params.speed_max);
    			double joy0axis_ = Params.ramp(joy0_axis, joy0_axis_, Params.increment_ramp);
    			return Params.expo(joy0axis_, Params.increment_expo);
    		case 1://X:0,Y:1,Z/Twist:2
    			double joy1_axis = joy1.getRawAxis(axis);
    			double joy1_axis_ = Params.clamp(joy1_axis, Params.speed_min, Params.speed_max);
    			double joy1axis_ = Params.ramp(joy1_axis, joy1_axis_, Params.increment_ramp);
    			return Params.expo(joy1axis_, Params.increment_expo);
    		case 2://LX:0,LY:1,LTrigger:2,RTrigger:3,RX:4,RY:5
    			return this.xbox.getRawAxis(axis);
    			//Triggers have values b/w 0 & 1
    		default:
    			return getAxis(0, axis);
    	}
    }
}
