package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Drivetrain
{
    private Victor frontLeft, frontRight, rearLeft, rearRight;

    private Joystick joystick;

    public Drivetrain(int frontLeftN, int frontRightN, int rearLeftN, int rearRightN, Joystick joystick, final double speed) {

		frontLeft = new Victor(frontLeftN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };
		frontRight = new Victor(frontRightN) {
			public void set(double d) {
				super.set(-1*d * speed);
            }
        };
		rearLeft = new Victor(rearLeftN) {
			public void set(double d) {
				super.set(d * speed);
            }
        };
		rearRight = new Victor(rearRightN) {
			public void set(double d) {
				super.set(-1*d * speed);
            }
        };

		this.joystick = joystick;

    }

    public double getFrontLeft()
    {
        return this.frontLeft.get();
    }

    public double getFrontRight()
    {
        return this.frontRight.get();
    }

    public double getRearLeft()
    {
        return this.rearLeft.get();
    }

    public double getRearRight()
    {
        return this.rearRight.get();
    }

    public void drive()
    {
		double robotX = -joystick.getX();
        double robotY = -joystick.getY();
		double robotZ = -joystick.getTwist();

        double speed = 1.0;

        frontLeft.set(-(robotZ * speed) + (robotY * speed) + (robotX * speed));
        rearLeft.set(-(robotZ * speed) + (robotY * speed) - (robotX * speed));
        frontRight.set(-(robotZ * speed) - (robotY * speed) + (robotX * speed));
        rearRight.set(-(robotZ * speed) - (robotY * speed) - (robotX * speed));


		/*
		double robotSpin = -joystick.getTwist();
        double robotMove = -joystick.getY();
        double speed = 1.0;

        if(joystick.getRawButton(2))
        {
            speed = 0.2;
        }

        if(joystick.getRawButton(4))
        {
            frontLeft.set(-speed);
            rearLeft.set(speed);
            frontRight.set(-speed);
            rearRight.set(speed);
        }
        else if(joystick.getRawButton(3))
        {
            frontLeft.set(speed);
            rearLeft.set(-speed);
            frontRight.set(speed);
            rearRight.set(-speed);
        }
        else
        {
            frontLeft.set(-(robotSpin * speed) + (robotMove * speed));
            rearLeft.set(-(robotSpin * speed) + (robotMove * speed));
            frontRight.set(-(robotSpin * speed) - (robotMove * speed));
            rearRight.set(-(robotSpin * speed) - (robotMove * speed));
        }
		* */

    }

    public void frontLeftSet(double value) {
		frontLeft.set(value);
    }

    public void rearLeftSet(double value) {
		rearLeft.set(value);
    }

    public void frontRightSet(double value) {
		frontRight.set(value);
    }

    public void rearRightSet(double value) {
		rearRight.set(value);
    }

	public void setDiminishedSpeed(double fraction)
	{
		frontLeft.set(getFrontLeft() * fraction);
        rearLeft.set(getRearLeft() * fraction);
        frontRight.set(getFrontRight() * fraction);
        rearRight.set(getRearRight() * fraction);
	}

}
