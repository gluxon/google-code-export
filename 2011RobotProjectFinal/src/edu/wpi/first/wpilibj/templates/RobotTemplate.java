/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates; 

import edu.wpi.first.wpilibj.*;
import edu.fhs.input.XboxController;

public class RobotTemplate extends IterativeRobot
{
    private boolean miniIsDeployed = false;
    private double robotSpin, robotMove,auxSpin, auxMove, gripper, XBOX_SENSITIVITY = 0.15;

    private XboxController xboxAuxController, xboxDriveController;

    private Victor victorLeft, victorRight, victorLeft2, victorRight2, victorClaw;
    private Jaguar victorArm;

    private Solenoid miniBotSolenoid, miniBotSolenoidRelease;

    private DigitalInput upperArmLimiter;

    private DriverStationLCD driverStationLCD;
    private Watchdog watchDog;

    double speed = 0.5;

    public void robotInit()
    {
        try
        {
            xboxDriveController = new XboxController(1);
            xboxAuxController = new XboxController(2);
        
            victorLeft = new Victor(9);
            victorLeft2 = new Victor(8);
            victorRight = new Victor(7);
            victorRight2 = new Victor(6);
            victorArm = new Jaguar(5);
            victorClaw = new Victor(4);

            miniBotSolenoid = new Solenoid(8, 2);
            miniBotSolenoidRelease = new Solenoid(8, 1);

            upperArmLimiter = new DigitalInput(1);

            driverStationLCD = DriverStationLCD.getInstance();
            watchDog = Watchdog.getInstance();
        }
        catch(Exception e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kUser6, 2, "Initilization Error.");
        }

        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void disabledInit()
    {
        miniIsDeployed = false;
    }

    public void autonomousPeriodic()
    {
        //moveArm(1.0);
        //Timer.delay(1.5);
        //moveArm(0.0);
        watchDog.feed();
        driverStationLCD.updateLCD();
    }

    public void teleopPeriodic()
    {
        /** Main Driver Controls **/
        
        robotSpin = xboxDriveController.getRightToggleX();
        robotMove = xboxDriveController.getLeftToggleX(); //This should be y, but there is a mistake in the xboxcontroller class.
            //getLeftToggleX() and getLeftToggleY() are switched

        auxSpin = xboxAuxController.getThrottle();
        auxMove = xboxAuxController.getY();
        //double speed = 0.5;

        //gripper =
        //Main driver control, A is not pressed on aux controller
        if ((Math.abs(robotSpin) > XBOX_SENSITIVITY || Math.abs(robotMove) > XBOX_SENSITIVITY) && !xboxAuxController.getRawButton(2))
        {
            victorLeft.set(-(robotSpin * speed) + (robotMove * speed));
            victorLeft2.set(-(robotSpin * speed) + (robotMove * speed));
            victorRight.set(-(robotSpin * speed) - (robotMove * speed));
            victorRight2.set(-(robotSpin * speed) - (robotMove * speed));
        }
        
        //Aux driver control when A is pressed that overrides main driver if main is behaving erratically
        else if((Math.abs(auxSpin) > XBOX_SENSITIVITY || Math.abs(auxMove) > XBOX_SENSITIVITY) && xboxAuxController.getRawButton(2))
        {
            victorLeft.set(-(auxSpin * speed) + (auxMove * speed));
            victorLeft2.set(-(auxSpin * speed) + (auxMove * speed));
            victorRight.set(-(auxSpin * speed) - (auxMove * speed));
            victorRight2.set(-(auxSpin * speed) - (auxMove * speed));

        }
         
        else if (Math.abs(robotSpin) < XBOX_SENSITIVITY || Math.abs(robotMove) < XBOX_SENSITIVITY)
        {
            victorLeft.set(0);
            victorRight.set(0);
            victorLeft2.set(0);
            victorRight2.set(0);
        }
        
        /** Aux Driver Controls **/
        moveArm(-xboxAuxController.getZ()); //only aux controller can move arm

        moveGripper(xboxDriveController.getZ());
        //moveArm(-xboxDriveController.getZ()*0.5);
        //driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Control got through teleopPeriodic.");
        //deployMinibot(xboxAuxController.getRawButton(3),xboxAuxController.getRawButton(2));

        watchDog.feed();
        driverStationLCD.updateLCD();
    }
    
    public void moveGripper(double speed)
    {
        if((speed > XBOX_SENSITIVITY))
        {
            victorClaw.set(speed*0.4);
        }
        else if((speed < -XBOX_SENSITIVITY))
        {
            victorClaw.set(speed*0.4);
        }
        else
        {
            victorClaw.set(0.0);
        }
    }
    
    public void moveArm(double speed)
    {   
        if(upperArmLimiter.get() && (speed > XBOX_SENSITIVITY))
        {
            victorArm.set(speed);
        }
        else if((speed < -XBOX_SENSITIVITY))
        {
            victorArm.set(speed);
        }
        else
        {
            victorArm.set(0.0);
        }

    }
    
    public void deployMinibot(boolean input_activate, boolean input_release)
    {
        try
        {
            if (!miniIsDeployed && input_activate)
            {
                miniBotSolenoid.set(true);
                miniBotSolenoidRelease.set(false);
                miniIsDeployed = true;
            }
            if (miniIsDeployed && input_release)
            {
                miniBotSolenoid.set(false);
                miniBotSolenoidRelease.set(true);
                miniIsDeployed = false;
            }
        }
        catch(NullPointerException e)
        {
            driverStationLCD.println(DriverStationLCD.Line.kMain6, 2, "Yo dawg, dat minibot's wacked!  " + xboxAuxController.getRawButton(3));
        }
    }
}
