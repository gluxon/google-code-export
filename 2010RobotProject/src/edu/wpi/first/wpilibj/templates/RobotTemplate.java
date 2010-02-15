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
private AuxDriver auxDrive;
private Joystick joy1;
private Joystick joy2;
private Joystick joy3;
private Jaguar leftFrontJag;
private Jaguar rightFrontJag;
private Jaguar leftRearJag;
private Jaguar rightRearJag;
private RobotDrive drive;
private ColorImage image1;
private AxisCamera axisCamera1;
private Relay re1;
private Relay re2;
private Relay compressorRelay;
private DigitalInput regulator;
private Solenoid solenoid1;
private Solenoid solenoid2;
private Solenoid solenoid3;  //rename relays later according to usasge (elevator, base roller, etc.)
private Solenoid solenoid4;
private DriverStationLCD dsout;
private KickerControl kickerControl = new KickerControl();
private Pneumatics pneumatics = new Pneumatics();
private AnalogChannel pressure;
private Encoder encoder;
private AnalogChannel ultrasonic1;
private AnalogChannel ultrasonic2;
private AnalogChannel ultrasonic3;
private AnalogChannel ultrasonic4;
private Gyro gyro;
private PitchSmoothing pitchAdj = new PitchSmoothing(2);
private int delay = 20;
private double ultraV;
private double psi;
private int joy1Angle = 0;
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
        drive = new RobotDrive(leftFrontJag, leftRearJag, rightFrontJag, rightRearJag, 1);
        try
        {
        compressorRelay = new Relay(1, 8);
        regulator = new DigitalInput(1);
        }
        catch(NullPointerException n)
        {
            System.out.println("ERROR: compressor/regulator not connected");
        }
        if(compressorRelay != null && regulator != null)
        {
            if(regulator.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }
        /*
        gyro = new Gyro(1,9);
        axisCamera1 = AxisCamera.getInstance();
        try {
            re1 = new Relay(3);
            re2 = new Relay(4);
            auxDrive = new AuxDriver(joy3);
            ultra1 = new Ultrasonic(5,6);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Unable to init devices correctly!" );
            e.printStackTrace();
        }
         * */
        dsout = DriverStationLCD.getInstance();
        dsout.updateLCD();
        try
        {
            solenoid1 = new Solenoid(8,5);
            solenoid1 = new Solenoid(8,6);
            solenoid1 = new Solenoid(8,7);
            solenoid1 = new Solenoid(8,8);
            kickerControl.setSolenoid1(solenoid1);
            kickerControl.setSolenoid2(solenoid2);
            kickerControl.setSolenoid3(solenoid3);
            kickerControl.setSolenoid4(solenoid4);
        }
        catch(NullPointerException n){
            System.out.println("ERROR: solenoids not connected");
        }
        try
        {
            pressure = new AnalogChannel(1,1);
            encoder = new Encoder(1,2);
            ultrasonic1 = new AnalogChannel(1,3);
            ultrasonic2 = new AnalogChannel(1,4);
            ultrasonic3 = new AnalogChannel(1,5);
            ultrasonic4 = new AnalogChannel(1,6);
        }
         catch(NullPointerException n)
         {
             System.out.println("ERROR: sensors not connected");
         }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        if(compressorRelay != null && regulator != null)
        {
            if(regulator.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }
        leftFrontJag.set(0);
        rightFrontJag.set(0);
        leftRearJag.set(0);
        rightRearJag.set(0);
        /*
        double inputSpeed = 0.5;
        updateDashboard();
        leftFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        rightFrontJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        leftRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        rightRearJag.set(pitchAdj.gyroAutonomousAngleSpeedAdjust(inputSpeed, isAutonomous(), gyro.getAngle()));
        */
         }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        if(compressorRelay != null && regulator != null)
        {
            if(regulator.get()){compressorRelay.set(Relay.Value.kOn);}
            else {compressorRelay.set(Relay.Value.kOff);}
        }
        if(joy1.getRawButton(3))
        {
        drive.holonomicDrive(1, 0, 0);//Omni Drive
        }
        else if(joy1.getRawButton(2))
        {
        leftFrontJag.set(1);
        rightFrontJag.set(1);
        leftRearJag.set(1);
        rightRearJag.set(1);
        }
        else
        {
            if(joy1.getRawButton(7)){joy1Angle = 270;}
            else if(joy1.getRawButton(8)){joy1Angle = 90;}
            else if(!joy1.getRawButton(7) && !joy1.getRawButton(8)){joy1Angle = 0;}
            drive.holonomicDrive(-joy1.getY(), joy1Angle,joy1.getThrottle());//Omni Drive
        }
        //drive.tankDrive(joy1.getY(),joy2.getY());//TankDrive
//        auxDrive.operate();//Auxillary Driver
        /*
        try{
            image1 = axisCamera1.getImage();
        }
        catch(AxisCameraException cameraEx){
            cameraEx.printStackTrace();
        }
        catch(NIVisionException ve){
            ve.printStackTrace();
        }
         * */
        dsout.println(DriverStationLCD.Line.kMain6, 1, "LF " + truncate(leftFrontJag.get()) + " LR " + truncate(leftRearJag.get()));
        dsout.println(DriverStationLCD.Line.kUser2, 1, "RF " + truncate(rightFrontJag.get()) + " RR " + truncate(rightRearJag.get()));
        psi = pressure.getAverageVoltage()*37.76-32.89;
        dsout.println(DriverStationLCD.Line.kUser3, 1, "pressure: " + truncate(psi));
        if(ultrasonic1 != null)
        {
            if(delay == 20)
            {
                ultraV = ultrasonic1.getAverageVoltage();
                delay = 0;
            }
            dsout.println(DriverStationLCD.Line.kUser4, 1, "U voltage: " + ultraV);
            delay++;
        }
        /*
        dsout.println(DriverStationLCD.Line.kUser3, 1, "U range: " + );
        String distanceGivenByUltrasound =  Double.toString(ultrasonic.pidGet());
        dsout.println(DriverStationLCD.Line.kUser2, 1, distanceGivenByUltrasound);
        ultrasonic.pidGet()(String)
         * */
        if(joy2.getTrigger())
        {
             pneumatics.kick(kickerControl);
        }
        dsout.updateLCD();
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
