package org.usfirst.frc.team3729.robot;

 
 
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Mechanisms {
	  private static Mechanisms INSTANCE = null;
	  
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
			
		}
	  
	  public void test(){
		  
	    }
	  	
	  public void stop(){
		
	  }

}
