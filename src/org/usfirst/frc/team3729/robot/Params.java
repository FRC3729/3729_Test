package org.usfirst.frc.team3729.robot;

public class Params {
	//Port allocations
    //!Talons -PWM-
    public static final int[] port_Talon_left = {0,2};
    public static final int[] port_Talon_right = {1,3};
    public static final int[] port_Talon_center = {4,5};
    public static final int[] port_Talon_elevator = {6,7};
    //!Relays -Relay-
    public static final int[] port_Spike_arm = {0,1};
    public static final int port_Spike_intake = 2;
    //!Limit Switches -Digital I/O-
    public static final int[] port_Limit_arm = {0,1};
    //!Sonar -Digital I/O-
    public static final int[] port_Sonar_in = {2,3};
    public static final int[] port_Sonar_out = {4,5};
    //!Encoders -Digital I/O
    public static final int[] port_Encoder_aChannel_arm = {6,7};
    public static final int[] port_Encoder_bChannel_arm = {8,9};
    
    //Adjustments
    //! Increment at which we ramp output from the axes
    public static final double increment_ramp = 0.05;   
    //!Exponential values
    public static final double increment_expo = 0.3;
    //!Max/Min robot speeds
    public static final double speed_max = 0.85;
    public static final double speed_min = -0.85;
    public static final double speed_creep = .25;
    
    //!Testing information
    public static final boolean testing_drive = false;
    public static final boolean testing_input = false;
    public static final boolean testing_mech = true;
    
    //Useful Methods
    //!Slow the change of a value
    public static double ramp(double desired, double current, double increment) {
    	double output = current + (desired * increment);
    	return output;
    }
    //!Exponential Driving
    public static double expo(double x, double a) {
        return (a * (x * x * x) + (1 - a) * x);
    }
    //!Clamp values
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;
    }
    
}
