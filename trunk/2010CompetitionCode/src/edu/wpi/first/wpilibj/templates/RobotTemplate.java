/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.fhs.input.AuxDriver;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot
{
AuxDriver ADrive;
Joystick joy1;
Joystick joy2;
Joystick joy3;
Jaguar leftFrontJag;
Jaguar rightFrontJag;
Jaguar leftRearJag;
Jaguar rightRearJag;
RobotDrive drive;
ColorImage image1;
AxisCamera axisCamera1;
Relay re1;
Relay re2;//rename relays later according to usasge (elevator, base roller, etc.)
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        joy1 = new Joystick(1);
        joy2 = new Joystick(2);
        joy3 = new Joystick(3);
        leftFrontJag = new Jaguar(1);
        leftRearJag = new Jaguar(3);
        rightFrontJag = new Jaguar(2);
        rightRearJag = new Jaguar(4);
        drive = new RobotDrive(leftFrontJag, leftRearJag, rightFrontJag, rightRearJag);
        axisCamera1 = AxisCamera.getInstance();
        re1 = new Relay(3);
        re2 = new Relay(4);
        ADrive = new AuxDriver(joy3);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        leftFrontJag.set(-1);
        rightFrontJag.set(-1);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        drive.holonomicDrive(joy1.getMagnitude(), joy1.getDirectionDegrees(),joy1.getTwist());//Omni Drive
        //drive.tankDrive(joy1.getY(),joy2.getY());//TankDrive
        ADrive.operate();//Auxillary Driver
        try{image1 = axisCamera1.getImage();}
        catch(AxisCameraException A){}
        catch(NIVisionException N){}
        
    }
}
