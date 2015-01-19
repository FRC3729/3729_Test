package org.usfirst.frc.team3729.robot;

public class Params {
	//Port allocations
    //!Talons
    public static final int port_fl = 0;
    public static final int port_fr = 1;
    public static final int port_bl = 2;
    public static final int port_br = 3;
    public static final int port_c0 = 4;
    public static final int port_c1 = 5;
    public static final int port_elev0 = 6;
    public static final int port_elev1 = 7;
    public static final int port_eject = 8;
    //!Relays
    public static final int port_arm0 = 0;
    public static final int port_arm1 = 1;
    public static final int port_intake = 2;
    public static final int port_heightadj = 3;
    //!Limit Switches
    public static final int port_armslimit = 0;
    public static final int port_armslimit_safety = 1;
    public static final int port_limit_slide_forward = 2;
	public static final int port_limit_slide_reverse = 3;
	public static final int port_limit_heightadj_up = 4;
	public static final int port_limit_heightadj_down = 5;
    
    //Adjustments
    //! Increment at which we ramp output from the axes
    public static final double ramp_increment = 0.2;   
    //!Exponential values
    public static final double expo = 0.4;
    //!Max/Min robot speeds
    public static final double MAX_SPEED = 0.85;
    public static final double MIN_SPEED = -0.85;
    public static final double creep_speed = .25;
    public static final double elevator_speed = .55;
    
    //!Testing information
    public static final boolean testing_drive = false;
    public static final boolean testing_input = false;
    public static final boolean testing_mech = false;
    
    //Useful Methods
    //! Limit Robot Speed
    public static double reduceSpeed(double speed){
        if (speed < Params.MIN_SPEED)
            return Params.MIN_SPEED;
        else if (speed > Params.MAX_SPEED)
            return Params.MAX_SPEED;
        else
            return speed;
    }
    //! Prevent Gear Grinding
    public static double ramp(double desired_output, double current_output, double increment) {
        if (desired_output <= .1 && desired_output >= -.1) {
            increment /= 2;
        }
        if (desired_output < current_output) {
            return (current_output - increment) < 0.01 && (current_output - increment) > -0.01 ? 0 : current_output - increment;
        } else if (desired_output > current_output) {
            return (current_output + increment) < 0.01 && (current_output + increment) > -0.01 ? 0 : current_output + increment;
        } else {
            return current_output < 0.01 && current_output > -0.01 ? 0 : current_output;
        }
    }
    //! Exponential Driving
    public static double expo(double x, double a) {
        return (a * (x * x * x) + (1 - a) * x);
    }
    //! Clamp values
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }
    
}
