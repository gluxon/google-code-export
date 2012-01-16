/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.Unsafe;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Kinect;
import edu.wpi.first.wpilibj.Kinect.Point4;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Skeleton.Joint;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Watchdog;
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
    private Joystick xboxController, xboxAuxController;
    private float rightHandZ, leftHandZ;
    private Victor victorLeftFront, victorRightFront, victorLeftRear, victorRightRear;
    private DigitalInput leftLine,centerLine,rightLine;
    private Ultrasonic ultrasonic;
    private Watchdog watchdog;
    private RobotDrive robotDrive;
    private AxisCamera axisCamera;
   

    public void robotInit() 
    {
            axisCamera = AxisCamera.getInstance("10.1.78.11");
            xboxController = new Joystick(1);
            xboxAuxController = new Joystick(2);
            
            watchdog = Watchdog.getInstance();
            kinect = Kinect.getInstance();
            
            victorLeftFront = new Victor(1);
            victorLeftRear = new Victor(3);
            victorRightFront = new Victor(2);
            victorRightRear = new Victor(4);
            
            leftLine = new DigitalInput(1);
            centerLine = new DigitalInput(2);
            rightLine = new DigitalInput(3);
            
            
            //ultrasonic = new Ultrasonic(1,1);
            
            robotDrive = new RobotDrive(victorLeftFront, victorLeftRear, victorRightFront, victorRightRear);
            
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
        System.out.println(leftLine.get()+":"+centerLine.get()+":" + rightLine.get() + ":" + ultrasonic.getRangeInches());
        }
        else
        {
        
        victorLeftFront.set(0.0);
        victorLeftRear.set(0.0);
        victorRightFront.set(0.0);
        victorRightRear.set(0.0);
        System.out.println(leftLine.get()+":"+centerLine.get()+":" + rightLine.get() + ":" + ultrasonic.getRangeInches());
        }
        
        watchdog.feed();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
       /* try 
        {
            axisCamera.writeResolution(AxisCamera.ResolutionT.k320x240);
            
            if(axisCamera.freshImage())
            {
            ColorImage a = axisCamera.getImage();
            BinaryImage b = a.thresholdRGB(200, 255, 200, 255, 200, 255);
            ParticleAnalysisReport c[] = b.getOrderedParticleAnalysisReports();
            
            for(int i = 0; i < c.length; i++)
            {
                System.out.println(i+" |X:"+ c[i].center_mass_x_normalized+" |Y:"+c[i].center_mass_y_normalized+" |A:"+c[i].particleArea);
            }
            
            //System.out.println(b.getNumberParticles());
            
            }
            
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }*/
        
        //robotDrive.mecanumDrive_Polar(xboxController.getY(), xboxController.getX(), xboxController.getTwist());
        
        double robotSpin = -xboxController.getTwist();
        double robotMove = -xboxController.getY();
        double speed = 1.0;
        
        
        
        if(xboxController.getRawButton(3))
        {
            victorLeftFront.set(1.0);
            victorLeftRear.set(-1.0);
            victorRightFront.set(1.0);
            victorRightRear.set(-1.0);
        }
        else if(xboxController.getRawButton(4))
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
        
        watchdog.feed();
    }
    
}
