
package org.usfirst.frc.team3729.robot;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {

    Input _input;
    Drive _drive;
    Mechanisms _mech;
    Timer auto_timer;
    CameraServer server;


    protected void disabled()
    {
    	System.out.println("in Disabled");
    	_drive.stop();
    	_mech.stop();
    }

    protected void robotInit()
    {
        _input = Input.getInstance();
        _drive = Drive.getInstance();
        _mech = Mechanisms.getInstance();
        auto_timer = new Timer();
        
        // Print banner
        System.out.println(" ______ ______ ______ ______\n|__    |      |__    |  __  |\n|__    |_     |    __|__    |\n|______| |____|______|______|\n");
        System.out.println("This robot complies with Asimov's Laws of Robotics:");
        System.out.println("\t~> 1. A robot may not injure a human being or,\n\t      through inaction, allow a human being to come to harm.");
        System.out.println("\t~> 2. A robot must obey the orders given to it by human beings,\n\t      except where such orders would conflict with the First Law.");
        System.out.println("\t~> 3. A robot must protect its own existence as long as\n\t      such protection does not conflict with the First or Second Laws.");
        
        server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous(){
        System.out.println("In auto");
        auto_timer.start();
        while (isEnabled()){
            System.out.println("auto_timer: " + auto_timer.get());
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl(){
        System.out.println("in OpControl");

        while (isEnabled())
        {
            // #### LIVE ROUTINES ####

            // Drive
        	if (_input.getButton(0, 1)) {
        		_drive.Hdrive(_input.getAxis(0,0) * Params.creep_speed, _input.getAxis(0,1) * Params.creep_speed, _input.getAxis(1,0));
        	} else if (_input.getButton(1, 1)) {
        		_drive.Hdrive(_input.getAxis(0,0), _input.getAxis(0,1), _input.getAxis(1,0) * Params.creep_speed);
        	} else if (_input.getButton(0, 3)) {
        		_drive.tank(-_input.getAxis(1,1) * .75, _input.getAxis(0,1) * .75);
        	} else if (_input.getButton(1, 3)) {
        		//SONAR ALIGN
        	}
        	else {
        	_drive.Hdrive(_input.getAxis(0,0), _input.getAxis(0,1), _input.getAxis(1,0));
//        	_drive.Quad(_input.getAxis(0,0), _input.getAxis(0,1), -_input.getAxis(1,0));
        	}
            
            //Mechanisms
            _mech.test();
            _mech.intake();
            _mech.arms();
            _mech.elevator();
            _mech.ejector();
            _mech.height();
            
            //Test Xbox
//            System.out.println("Xbox values: " + _input.test());
        }
    }
    public void test(){
    	while (isTest() && isEnabled()) {
    		LiveWindow.run();
    		Timer.delay(0.1);
    	}
    }
}
