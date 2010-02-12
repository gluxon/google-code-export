package edu.fhs.actuators;

/**
 * @author Patt Cunningham / Adam Gresh / Brandon Bald / Jim Jakupco / Wess Stevens
 */
public class PitchSmoothing
{
    private final int smoothingFactor;//Initilizes the smoothing factor variable
    private static final double ANG_CUTOFF = 45.0; //Sets the maximum angle or the ANG_CUTOFF to 45 degrees

    public PitchSmoothing(int i) {
        this.smoothingFactor = i; //Creates the smoothing factor in pitch / (ANG_CUTOFF * smoothingFactor)
    }
    public double gyroAutonomousAngleSpeedAdjust(double motorInput, boolean autonomous, double pitch)
    {
        double newSpeed = 0.0d;//The value to return
        if(autonomous)//This loops the code as long as the robot is enabled
        {
            if(motorInput < -0.2 && motorInput > 0.2)//This makes sure that the robot doesnt move when the input is 0.1 0.0 or -0.1
            {
                if(pitch / ANG_CUTOFF >= 1) //If the angle of the robot gets to big it will automaticaly cut of the motors
                {
                        System.out.println("Angle of robot has passed the angle cutoff. The robot will now stop.");//Sends a message to console that tells the user that the angle limit has been reached or exceeded
                        newSpeed = 0.0;//Output's 0.0 for the motor if the angle cutoff gets exceeded
                }
                else//If the input is not reached or excedded the angCutoff limiter
                {
                    newSpeed = motorInput - pitch / (ANG_CUTOFF * smoothingFactor);//Automatically adjust's the speed based on the angle
                }
            }
        }
        else
        {
        System.out.println("The robot is not in autonomous mode!");//Prints out this into console if the robot is not in autonomous mode when executing the method
        newSpeed = 0.0;//Output's motor to 0.0 if the robot is not in autonomous mode
        }
        return newSpeed;//Return's the new modified speed
    }
}
