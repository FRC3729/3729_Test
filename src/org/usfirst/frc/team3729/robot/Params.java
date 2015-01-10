package org.usfirst.frc.team3729.robot;

public class Params {
	//Port allocations
    //!Talons
    public static final int port_fl = 0;
    public static final int port_fr = 1;
    public static final int port_bl = 2;
    public static final int port_br = 3;
    //!Relays
    
    //Joystick adjustments
    //!Joy centers
    public static final double XCENTER = 0.0;
    public static final double YCENTER = 0.0;
    public static final double ZCENTER = -0.0;
    //!Joy max/min
    public static final double XMIN = -0.99;
    public static final double XMAX = 0.99;
    public static final double YMIN = -0.99;
    public static final double YMAX = 0.99;
    public static final double ZMIN = -0.99;
    public static final double ZMAX = 0.99;
    
    //!Exponential values
    public static final double XEXPO = 0.4;
    public static final double YEXPO = 0.4;
    public static final double ZEXPO = 0.4;
    
    //!Max/Min robot speeds
    public static final double MAX_SPEED = 0.85;
    public static final double MIN_SPEED = -0.85;
    
    //!Testing information
    public static final boolean testing_drive = false;
    public static final boolean testing_input = false;
    
}
