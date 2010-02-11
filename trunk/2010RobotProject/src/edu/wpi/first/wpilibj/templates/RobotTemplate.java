/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.fhs.actuators.KickerControl;
import edu.fhs.actuators.PitchSmoothing;
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
Relay solenoid1;
Relay solenoid2;
Relay solenoid3;  //rename relays later according to usasge (elevator, base roller, etc.)
Relay solenoid4;
Ultrasonic ultra1;
DriverStationLCD dsout;
KickerControl kickerControl = new KickerControl();
Pneumatics pneumatics = new Pneumatics();
AnalogChannel pressure;
Encoder encoder;
AnalogChannel ultrasonic;
Gyro gyro;
PitchSmoothing pitchAdj = new PitchSmoothing(2);
int delay = 20;
double ultraV;
    /**    *
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        solenoid1 = new Relay(5);
        solenoid1 = new Relay(6);
        solenoid1 = new Relay(7);
        solenoid1 = new Relay(8);
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
        gyro = new Gyro(1,9);
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
        dsout.updateLCD();
        try
        {
            solenoid1 = new Relay(5);
            solenoid2 = new Relay(6);
            solenoid3 = new Relay(7);
            kickerControl.setRelay1(solenoid1);
            kickerControl.setRelay2(solenoid2);
            kickerControl.setRelay3(solenoid3);
            kickerControl.setRelay4(solenoid4);
        }
        catch(NullPointerException n){
            System.out.println("ERROR: relays not connected");
        }
        pressure = new AnalogChannel(1,1);
        try
        {
            encoder = new Encoder(1,2);
        }
         catch(NullPointerException n)
         {
             System.out.println("ERROR: encoders not connected");
         }
        ultrasonic = new AnalogChannel(1,2);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        updateDashboard();
        leftFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(30.0, 0.5, isAutonomous(), gyro.getAngle()));
        rightFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(30.0, 0.5, isAutonomous(), gyro.getAngle()));
        leftRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(30.0, 0.5, isAutonomous(), gyro.getAngle()));
        rightRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(30.0, 0.5, isAutonomous(), gyro.getAngle()));
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        updateDashboard();
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
        dsout.println(DriverStationLCD.Line.kMain6, 1, "LF " + truncate(leftFrontJag.get()) + " LR " + truncate(leftRearJag.get()));
        dsout.println(DriverStationLCD.Line.kUser2, 1, "RF " + truncate(rightFrontJag.get()) + " RR " + truncate(rightRearJag.get()));
        dsout.println(DriverStationLCD.Line.kUser3, 1, "pressure: " + truncate((pressure.getAverageVoltage()*37.76-32.89)));
        if(delay == 20)
        {
            ultraV = ultrasonic.getAverageVoltage();
            delay = 0;
        }
        dsout.println(DriverStationLCD.Line.kUser3, 1, "U voltage: " + ultraV);
        delay++;
        //dsout.println(DriverStationLCD.Line.kUser3, 1, "U range: " + );
        dsout.updateLCD();
//        String distanceGivenByUltrasound =  Double.toString(ultra1.pidGet());
//        dsout.println(DriverStationLCD.Line.kUser2, 1, distanceGivenByUltrasound);
        //ultra1.pidGet()(String)
        if(joy2.getTrigger())
        {
             pneumatics.kick(kickerControl);
        } 
    }

    private double truncate(double rawDouble)
    {
        return ((double) ((int) (100 * rawDouble))) / 100;
    }

    void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 4;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 6;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            lowDashData.addByte(Solenoid.getAll());

            lowDashData.addDouble(999.123d);
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }
}
