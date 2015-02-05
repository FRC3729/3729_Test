package org.usfirst.frc.team3729.robot;

 
 
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.Talon;

public class Mechanisms {
	  private static Mechanisms INSTANCE = null;
	  Encoder encoder1; //create instance
	  Talon motor1 = new Talon(0);
	 
	  
	  public static Mechanisms getInstance()
	    {		  
		 
		  if (INSTANCE == null)
	            INSTANCE = new Mechanisms();
	        
	        return INSTANCE;
	    }
	
	  public void intake(){
			
		}
	  
	  public void arms(){
			
		}
	  
	  public void elevator(){
//		  while (buttonY isPressed)       or     while (buttonY isPressed) &&  (encoder.get() != top) 
//		  {
//			motor.moveForward();
//		  		if (encoder.get() == top)
//		  			motor.stop();
//				  
//		  }
		  
//		  while (buttonA isPressed)       
//		  {
//			motor.moveBackwards();
//		  		if (encoder.get() == bottom)
//		  			motor.stop();
//				  
//		  }
//		  
//		  
//		  if (backbutton.isPressed())
//		  {
//		  		while(encoder.get() > 0)
//		  		{
//		  		motor.reverse();
//	  			}
//	  	  }
//		  
//		  if (input.getPOV == 0)
//		  {
//			  desiredPosition = encoder.getCurrentPosition() + 30;
//			  	
//			  while (encoder.getCurrentPosition() < desiredPosition)
//			  {
//				  motor.forward();
//			  }
//		  }
//		  
//		  if (input.getPOV == 180)
//		  {
//		  desiredPosition = encoder.getCurrentPosition() - 30;
//		  	
//		  while (encoder.getCurrentPosition() > desiredPosition)
//		  {
//			  motor.reverse();
//		  }
//	  }
//
//
//		  
			}
		  
	  
	  public void test(){
		  
	    }
	  	
	  public void stop(){
		
	  }

}
