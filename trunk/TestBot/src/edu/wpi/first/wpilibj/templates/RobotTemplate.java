/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

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
    private double robotMove2, robotMove, XBOX_SENSITIVITY = 0.15, speed = 0.5;
    private Joystick xboxController, xboxAuxController;
    private Victor victorLeft, victorRight, victorLeft2, victorRight2;

    public void robotInit() 
    {
            xboxController = new Joystick(1);
            xboxAuxController = new Joystick(2);

            victorLeft = new Victor(1);
            victorLeft2 = new Victor(2);
            victorRight = new Victor(3);
            victorRight2 = new Victor(4);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {

        robotMove = xboxController.getY();
        robotMove2 = xboxAuxController.getY();

        victorLeft.set(robotMove2);
        victorLeft2.set(robotMove2);
        victorRight.set(-robotMove);
        victorRight2.set(-robotMove);

    }
    
}
