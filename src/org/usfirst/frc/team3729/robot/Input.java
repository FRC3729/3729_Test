package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Input {
    private static Input INSTANCE = null;
    
    public final Joystick joy0;
    public final Joystick joy1;
    public final Joystick xbox;

    public Input()
    {
        this.joy0 = new Joystick(0);
        this.joy1 = new Joystick(1);
        this.xbox = new Joystick(2);
    }
    
    public static Input getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new Input();
        
        return INSTANCE;
    }
    
    public double reduceSpeed(double speed){
        if (speed < Params.MIN_SPEED)
            return Params.MIN_SPEED;
        else if (speed > Params.MAX_SPEED)
            return Params.MAX_SPEED;
        else
            return speed;
    }
        
    //!Button control
    public boolean getButton(int joy, int buttonid) {
        switch (joy) {
            case 0:
                return this.joy0.getRawButton(buttonid);
            case 1:
                return this.joy1.getRawButton(buttonid);
            case 2:
            	return this.xbox.getRawButton(buttonid);
            	//A:1,B:2,X:3,Y:4,LB:5,RB:6,Back:7,Start:8,LS:9,RS:10
            default:
                return getButton(0, buttonid);
        }
    }
    //!Axis control
    public double getAxis(int joy, int axis) {
    	switch(joy) {
    		case 0: //X:0,Y:1,Z/Twist:2
    			double _axis0 = this.joy0.getRawAxis(axis);
    			double _axis0_ = Params.reduceSpeed(_axis0);
    			double axis0_ = Params.ramp(_axis0, _axis0_, Params.ramp_increment);
    			return Params.expo(axis0_, Params.expo);
    		case 1://X:0,Y:1,Z/Twist:2
    			double _axis1 = this.joy1.getRawAxis(axis);
    			double _axis1_ = Params.reduceSpeed(_axis1);
    			double axis1_ = Params.ramp(_axis1, _axis1_, Params.ramp_increment);
    			return Params.expo(axis1_, Params.expo);
    		case 2://LX:0,LY:1,LTrigger:2,RTrigger:3,RX:4,RY:5
    			return this.xbox.getRawAxis(axis);
    			//Triggers have value b/w 0 & 1
    		default:
    			return getAxis(0, axis);
    	}
    }
}
