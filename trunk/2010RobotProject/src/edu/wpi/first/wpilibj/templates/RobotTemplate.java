/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.fhs.actuators.KickerControl;
import edu.fhs.actuators.Pneumatics;
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
AuxDriver auxDrive;
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
Relay re2;
Relay reMainPf;
Relay reMainPb;
Relay reGatePf;
Relay reGatePb;  //rename relays later according to usasge (elevator, base roller, etc.)
Ultrasonic ultra1;
DriverStationLCD dsout;
KickerControl kickerControl;
Pneumatics pneumatics = new Pneumatics();
PWM pressure = new PWM(12);
    /**    *
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
        rightFrontJag = new Jaguar(2)
                {
                  public void set(double d)
                    {
                        super.set(d * -1);
                    }
                };
         rightRearJag = new Jaguar(4)
                {
                  public void set(double d)
                    {
                        super.set(d * -1);
                    }
                };
        drive = new RobotDrive(leftFrontJag, leftRearJag, rightFrontJag, rightRearJag, 0.75);
        axisCamera1 = AxisCamera.getInstance();
//        try {
//            re1 = new Relay(3);
//            re2 = new Relay(4);
//            auxDrive = new AuxDriver(joy3);
//            ultra1 = new Ultrasonic(5,6);
//        } catch (IndexOutOfBoundsException e) {
//            System.err.println("Unable to init devices correctly!" );
//            e.printStackTrace();
//        }
        dsout = DriverStationLCD.getInstance();
        dsout.println(DriverStationLCD.Line.kMain6, 1, "kMain6 reporting in");
        dsout.println(DriverStationLCD.Line.kUser2, 1, "kUser2 reporting in");
        dsout.updateLCD();
        reMainPf = new Relay(5);
        reMainPb = new Relay(6);
        reGatePf = new Relay(7);
        reGatePb = new Relay(8);
        kickerControl.setRelay1Close(reGatePf);
        kickerControl.setRelay1Open(reGatePb);
        kickerControl.setRelay2Close(reMainPf);
        kickerControl.setRelay2Open(reMainPb);
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
//        auxDrive.operate();//Auxillary Driver
        try{
            image1 = axisCamera1.getImage();
        }
        catch(AxisCameraException cameraEx){
            cameraEx.printStackTrace();
        }
        catch(NIVisionException ve){
            ve.printStackTrace();
        }
        dsout.println(DriverStationLCD.Line.kMain6, 1, "kMain6 running");
        dsout.println(DriverStationLCD.Line.kUser2, 1, "kUser2 running");
        dsout.println(DriverStationLCD.Line.kUser3, 1, "LF " + leftFrontJag.get() + " LR " + leftRearJag.get() + " RF " + rightFrontJag.get() + " RR " + rightRearJag.get());
        dsout.println(DriverStationLCD.Line.kUser4, 1, "" + pressure.getRaw());
        dsout.updateLCD();
//        String distanceGivenByUltrasound =  Double.toString(ultra1.pidGet());
//        dsout.println(DriverStationLCD.Line.kUser2, 1, distanceGivenByUltrasound);
        //ultra1.pidGet()(String)
        if(joy2.getTrigger())
        {
             pneumatics.kick(kickerControl);
        }
    }
}
