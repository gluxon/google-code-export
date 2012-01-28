package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Drivetrain 
{
    private Victor frontLeft, frontRight, rearLeft, rearRight;
    
    private Joystick joystick;
    
    public Drivetrain(int frontLeftN, int frontRightN, int rearLeftN, int rearRightN, Joystick joystick, final double speed)
    {
	frontLeft = new Victor(frontLeftN)
	{
	    public void set(double d)
            {
		super.set(d * speed);
            }
        };
	frontRight = new Victor(frontRightN)
	{
	    public void set(double d)
            {
		super.set(d * speed);
            }
        };
	rearLeft = new Victor(rearLeftN)
	{
	    public void set(double d)
            {
		super.set(d * speed);
            }
        };
	rearRight = new Victor(rearRightN)
	{
	    public void set(double d)
            {
		super.set(d * speed);
            }
        };
	
	this.joystick = joystick;
	
    }
    
    public void drive()
    {
	double robotSpin = -joystick.getTwist();
        double robotMove = -joystick.getY();
        double speed = 1.0;
        
        if(joystick.getRawButton(4))
        {
            frontLeft.set(1.0);
            rearLeft.set(-1.0);
            frontRight.set(1.0);
            rearRight.set(-1.0);
        }
        else if(joystick.getRawButton(3))
        {
            frontLeft.set(-1.0);
            rearLeft.set(1.0);
            frontRight.set(-1.0);
            rearRight.set(1.0);
        }
        else
        {
            frontLeft.set(-(robotSpin * speed) + (robotMove * speed));
            rearLeft.set(-(robotSpin * speed) + (robotMove * speed));
            frontRight.set(-(robotSpin * speed) - (robotMove * speed));
            rearRight.set(-(robotSpin * speed) - (robotMove * speed));
        }
    }
    
    public void frontLeftSet(double value)
    {
	frontLeft.set(value);
    }
    
    public void rearLeftSet(double value)
    {
	rearLeft.set(value);
    }
    
    public void frontRightSet(double value)
    {
	frontRight.set(value);
    }
    
    public void rearRightSet(double value)
    {
	rearRight.set(value);
    }
    
}
