/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.communication.Semaphore;
import edu.wpi.first.wpilibj.communication.Semaphore.Options;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.Kinect.Point4;
import edu.wpi.first.wpilibj.Skeleton.Joint;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    //private double robotMove2, robotMove, robotSpeed = 0.5;
    private Kinect kinect;
    private Joystick xboxController;
    private float rightHandZ, leftHandZ;
    private Victor victorLeftFront, victorRightFront, victorLeftRear, victorRightRear;
    private Encoder encoderLeftFront, encoderRightFront, encoderLeftRear, encoderRightRear;
    private UltrasonicFHS ultrasonic;
    private Accelerometer accelerometer;
    private Watchdog watchdog;
    private TractionControl TC;
    private RobotDrive robotDrive;
    
    private AxisCamera axisCamera;
    
    private Dashboard dashboard;
    private Semaphore semaphore;
    private Semaphore.Options semaphoreOptions;
   
    
    
    private AnalogChannel m_analogChannel; //

    public void robotInit() 
    {
            axisCamera = AxisCamera.getInstance("10.1.78.11");
            xboxController = new Joystick(1);
            
            
            watchdog = Watchdog.getInstance();
            kinect = Kinect.getInstance();
            
            victorLeftFront = new Victor(1);
            victorLeftRear = new Victor(3);
            victorRightFront = new Victor(2);
            victorRightRear = new Victor(4);
            
            //ultrasonic = new UltrasonicFHS(1,7);
            //accelerometer = new Accelerometer(1,6);
            
            robotDrive = new RobotDrive(victorLeftFront, victorLeftRear, victorRightFront, victorRightRear);
            
            semaphoreOptions = new Semaphore.Options();

            semaphoreOptions = new Semaphore.Options();
            semaphore = new Semaphore(semaphoreOptions.setDeleteSafe(true));
            dashboard = Dashboard(semaphore);
            m_analogChannel = new AnalogChannel(7); //
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        if(kinect.getNumberOfPlayers() == 1)
        {
        rightHandZ = kinect.getSkeleton().GetHandRight().getZ();
        leftHandZ = kinect.getSkeleton().GetHandLeft().getZ();

        victorLeftFront.set(leftHandZ);
        victorLeftRear.set(leftHandZ);
        victorRightFront.set(-rightHandZ);
        victorRightRear.set(-rightHandZ);
        }
        else
        {
        
        victorLeftFront.set(0.0);
        victorLeftRear.set(0.0);
        victorRightFront.set(0.0);
        victorRightRear.set(0.0);
     
        }
        
        watchdog.feed();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        double robotSpin = -xboxController.getTwist();
        double robotMove = -xboxController.getY();
        double speed = 1.0;
        
        if(xboxController.getRawButton(4))
        {
            victorLeftFront.set(1.0);
            victorLeftRear.set(-1.0);
            victorRightFront.set(1.0);
            victorRightRear.set(-1.0);
        }
        else if(xboxController.getRawButton(3))
        {
            victorLeftFront.set(-1.0);
            victorLeftRear.set(1.0);
            victorRightFront.set(-1.0);
            victorRightRear.set(1.0);
        }
        else
        {
            victorLeftFront.set(-(robotSpin * speed) + (robotMove * speed));
            victorLeftRear.set(-(robotSpin * speed) + (robotMove * speed));
            victorRightFront.set(-(robotSpin * speed) - (robotMove * speed));
            victorRightRear.set(-(robotSpin * speed) - (robotMove * speed));
        }
        
        //System.out.println((int)ultrasonic.getRangeInches()+" Inches");
        //System.out.println(accelerometer.pidGet()+" Gs");
        System.out.println(m_analogChannel.getAverageVoltage());
        watchdog.feed();
    }
    
}
  