/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
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
    private Encoder encoderLeftFront, encoderRightFront, encoderLeftRear, encoderRightRear, encoder;
    private UltrasonicFHS ultrasonic;
    private Accelerometer accelerometer;
    private Gyro gyro;
    private Watchdog watchdog;
    private TractionControl TC;
    private RobotDrive robotDrive;
    
    private AxisCamera axisCamera;
    
    private Dashboard dashboard;
    
    //in meters
    private final double DISTANCE_PER_PULSE = .196*Math.PI/1440; //1440 according to guy on Delphi stie
    
    
    private AnalogChannel m_analogChannel; //

    public void robotInit() 
    {
            axisCamera = AxisCamera.getInstance("10.1.78.11");
            xboxController = new Joystick(1);
            
            gyro = new Gyro(1,1);
            //accelerometer = new Accelerometer(1);
            
            watchdog = Watchdog.getInstance();
            kinect = Kinect.getInstance();
            
            victorLeftFront = new Victor(1);
            victorLeftRear = new Victor(3);
            victorRightFront = new Victor(2);
            victorRightRear = new Victor(4);
            
            encoder = new Encoder(6,7);
            encoder.start();
            encoder.setDistancePerPulse(DISTANCE_PER_PULSE);
            //ultrasonic = new UltrasonicFHS(1,7);
            //accelerometer = new Accelerometer(1,6);
            
            robotDrive = new RobotDrive(victorLeftFront, victorLeftRear, victorRightFront, victorRightRear);
        
            m_analogChannel = new AnalogChannel(7); //
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        if (kinect.getNumberOfPlayers() == 1) {
            rightHandZ = kinect.getSkeleton().GetHandRight().getZ();
            leftHandZ = kinect.getSkeleton().GetHandLeft().getZ();

            victorLeftFront.set(leftHandZ);
            victorLeftRear.set(leftHandZ);
            victorRightFront.set(-rightHandZ);
            victorRightRear.set(-rightHandZ);
        }
        else {
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
        //System.out.println(m_analogChannel.getAverageVoltage());
         System.out.println(encoder.getDistance());
        
        //System.out.println(gyro.getAngle());
        watchdog.feed();
    }
    
    /*public void updateDashboardHigh(double joyStickX, double gyroAngle, double gyroRate, double targetX, Target[] targets) {
        Dashboard highDashData = DriverStation.getInstance().getDashboardPackerHigh();
        highDashData.addCluster(); // wire (2 elements)
        highDashData.addCluster(); // tracking data
        highDashData.addDouble(joyStickX); // Joystick X
        highDashData.addDouble(((gyroAngle + 360.0 + 180.0) % 360.0) - 180.0); // angle
        highDashData.addDouble(gyroRate); // angular rate
        highDashData.addDouble(targetX); // other X
        highDashData.finalizeCluster();
        highDashData.addCluster(); // target Info (2 elements)
        highDashData.addArray();
                //TODO Limit what we send back - we don't display targets at dash,
                //  do we really need to send anything at all?
        for (int i = 0; i < targets.length; i++) {
            highDashData.addCluster(); // targets

            double targetScore = 0;
            double m_xPos = 0;
            double m_xMax = 1; //this becomes a divisor
            double m_yPos = 0;
            if (targets != null & targets[i] != null) {
                targetScore = targets[i].m_score;
                m_xPos = targets[i].m_xPos;
                m_xMax = targets[i].m_xMax;
                m_yPos = targets[i].m_yPos;
            }
            highDashData.addDouble(targetScore); // target score
            highDashData.addCluster(); // Circle Description (5 elements)
            highDashData.addCluster(); // Position (2 elements)
            highDashData.addFloat((float) (m_xPos / m_xMax)); // X
            highDashData.addFloat((float) m_yPos); // Y
            highDashData.finalizeCluster();
            if (targets == null || targets[i] == null) {
                highDashData.addDouble(0d); // Angle
                highDashData.addDouble(0d); // Major Radius
                highDashData.addDouble(0d); // Minor Radius
                highDashData.addDouble(0d); // Raw score
            } else {
                highDashData.addDouble(targets[i].m_rotation); // Angle
                highDashData.addDouble(targets[i].m_majorRadius); // Major Radius
                highDashData.addDouble(targets[i].m_minorRadius); // Minor Radius
                highDashData.addDouble(targets[i].m_rawScore); // Raw score
            }
            highDashData.finalizeCluster(); // Position
        }
        highDashData.finalizeCluster(); // targets
        highDashData.finalizeArray();
        //highDashData.addInt((int) time.getTime());
        highDashData.finalizeCluster(); // target Info
        highDashData.finalizeCluster(); // wire
        highDashData.commit();
    }*/

    
}
  