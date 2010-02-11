/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.actuators;

/**
 *
 * @author Patt
 */
public class PitchSmoothing
{
    private final int smoothingFactor;

    public PitchSmoothing(int i) {
        this.smoothingFactor = i;
    }
    public double gyroAutonomousAngleSpeedAdjust(double angCutoff, double motorInput, boolean autonomous, double pitch)
    {
        double newSpeed = 0.0d;//The value to return
        if(autonomous)//This loops the code as long as the robot is enabled
        {
            if(motorInput < -0.2 && motorInput > 0.2)//This makes sure that the robot doesnt move when the input is 0.1 0.0 or -0.1
            {
                if(pitch / angCutoff >= 1) //If the angle of the robot gets to big it will automaticaly cut of the motors
                {
                        System.out.println("Angle of robot has passed the angle cutoff. The robot will now stop.");//Sends a message to console that tells the user that the angle limit has been reached or exceeded
                        newSpeed = 0.0;
                }
                else//If the input is not reached or excedded the angCutoff limiter
                {
                    newSpeed = motorInput - pitch / (angCutoff * smoothingFactor);//Automatically adjust's the speed based on the angle
                }
            }
        }
        else
        {
        System.out.println("The robot is not in autonomous mode!");
        newSpeed = 0.0;
        }
        return newSpeed;
    }
}
