package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Drivetrain
{
    private Victor frontLeft, frontRight, rearLeft, rearRight;

    private Joystick joystick;

    double robotX, robotY, robotZ, speed;

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

    public double getFrontLeft() //Gets the front left motor speed
    {
        return this.frontLeft.get();
    }

    public double getFrontRight() //Gets the front right motor speed
    {
        return this.frontRight.get();
    }

    public double getRearLeft() //Gets the rear left motor speed
    {
        return this.rearLeft.get();
    }

    public double getRearRight() //Gets the rear right motor speed
    {
        return this.rearRight.get();
    }

    public void drive() //Drives the robot
    {
        robotX = -joystick.getX();
        robotY = -joystick.getY();
		robotZ = -joystick.getTwist();

        speed = 1.0;
		if (joystick.getRawButton(2)) {
			speed = 0.5;
		}
        frontLeft.set(-(robotZ * speed) + (robotY * speed) - (robotX * speed));
        rearLeft.set(-(robotZ * speed) + (robotY * speed) + (robotX * speed));
        frontRight.set(-(robotZ * speed) - (robotY * speed) - (robotX * speed));
        rearRight.set(-(robotZ * speed) - (robotY * speed) + (robotX * speed));
    }

    public void frontLeftSet(double value) //Sets the front left Victor
    {
        frontLeft.set(value);
    }

    public void rearLeftSet(double value) //Sets the rear left Victor
    {
    	rearLeft.set(value);
    }

    public void frontRightSet(double value) //Sets the front right Victor
    {
	frontRight.set(value);
    }

    public void rearRightSet(double value) //Sets the rear right Victor
    {
	rearRight.set(value);
    }

    public void setDiminishedSpeed(double fraction) //Sets the robot speed
    {
    	frontLeft.set(getFrontLeft() * fraction);
        rearLeft.set(getRearLeft() * fraction);
        frontRight.set(getFrontRight() * fraction);
        rearRight.set(getRearRight() * fraction);
    }
}