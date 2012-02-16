package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Victor;

public class Tower 
{
    private Victor bottomShooterMotor, topShooterMotor, ballElevatorMotor, ballIntakeMotor;
    
    public Tower(int bottomShooterMotorN, int topShooterMotorN, int ballElevatorMotorN, int ballIntakeMotorN)
    {
        bottomShooterMotor = new Victor(bottomShooterMotorN);
        topShooterMotor = new Victor(topShooterMotorN);
        ballElevatorMotor = new Victor(ballElevatorMotorN);
        ballIntakeMotor = new Victor(ballIntakeMotorN);
    }
    
    public Victor getBottomShooterMotor()
    {
        return bottomShooterMotor;
    }
    
    public Victor getTopShooterMotor()
    {
        return topShooterMotor;
    }
    
    public Victor getBallElevatorMotor()
    {
        return ballElevatorMotor;
    }
    
    public Victor getBallIntakeMotor()
    {
        return ballIntakeMotor;
    }
    
    public void setBottomShooterMotor(double input)
    {
        bottomShooterMotor.set(input);
    }
    
    public void setTopShooterMotor(double input)
    {
        topShooterMotor.set(input);
    }
    
    public void setBallElevator(double input)
    {
        ballElevatorMotor.set(input);
    }
    
    public void setBallIntakeMotor(double input)
    {
        ballIntakeMotor.set(input);
    }
    
    public void setShooterMotors(double input)
    {
        setBottomShooterMotor(input);
        setTopShooterMotor(-input);
    }
}
