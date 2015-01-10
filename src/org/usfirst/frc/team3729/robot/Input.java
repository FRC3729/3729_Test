package org.usfirst.frc.team3729.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Input {
    private static Input INSTANCE = null;
    
    public final Joystick joy1;
    public final Joystick joy2;

    private Input()
    {
        this.joy1 = new Joystick(0);
        this.joy2 = new Joystick(1);
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

    public double getY(){
        double y = normalize(joy1.getAxis(Joystick.AxisType.kY) - Params.YCENTER, Params.YMIN, Params.YMAX);
        double _y = reduceSpeed(y);
        double y_ = ramp(y, _y, 0.1);
        double y_out = expo(y_, Params.YEXPO);
        if(Params.testing_input){System.out.println("y: " + y_out);}
        return y_out;
    }
    public double getX(){
        double x = normalize(joy1.getAxis(Joystick.AxisType.kX) - Params.XCENTER, Params.XMIN, Params.XMAX);
        double _x = reduceSpeed(x);
        double x_ = ramp(x, _x, 0.1);
        double x_out = expo(x_, Params.XEXPO);
        if(Params.testing_input){System.out.println("x: " + x_out);}
        return x_out;
    }
    public double getZ(){
        double z = normalize(joy2.getAxis(Joystick.AxisType.kY) - Params.ZCENTER, Params.ZMIN, Params.ZMAX);
        double _z = reduceSpeed(z);
        double z_ = (ramp(z, _z, 0.1) * -1.0);
        double z_out = expo(z_, Params.ZEXPO);
        if(Params.testing_input){System.out.println("z: " + z_out);}
        return z_out;     
    }
    
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

    // Normalize input values to -1.0 to 1.0
    private double normalize(double joyVal, double min, double max){
        double retVal = 0.0;
        if (joyVal < 0.0)
            retVal = Math.abs(joyVal) / min;
        else if (joyVal > 0.0)
            retVal = Math.abs(joyVal) / max;
        if (retVal < -1.0)
            retVal = -1.0;
        else if (retVal > 1.0)
            retVal = 1.0;
        return retVal;
        //return joyVal;
    }
    private double expo(double x, double a) {
        return (a * (x * x * x) + (1 - a) * x);
    
    }
}
