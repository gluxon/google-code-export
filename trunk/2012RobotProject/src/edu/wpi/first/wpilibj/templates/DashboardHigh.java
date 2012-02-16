package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class DashboardHigh
{
    Dashboard dashboardHigh;

    public DashboardHigh()
    {

    }

    public void updateDashboardHigh(Drivetrain drivetrain, double gyro, double ultrasonic, Joystick joystick)
    {
        // [frontLeftVictor],[frontRightVictor],[rearLeftVictor],[rearRightVictor],[Gyro],[Ultrasonic],[Joystick X],[Joystick Y],[Joystick Z]
        dashboardHigh = DriverStation.getInstance().getDashboardPackerHigh();

        dashboardHigh.addCluster();
        dashboardHigh.addArray();
        dashboardHigh.addDouble(drivetrain.getFrontLeft());
        dashboardHigh.addDouble(drivetrain.getFrontRight());
        dashboardHigh.addDouble(drivetrain.getRearLeft());
        dashboardHigh.addDouble(drivetrain.getRearRight());
        dashboardHigh.addDouble(gyro);
        dashboardHigh.addDouble(ultrasonic);
        dashboardHigh.addDouble(joystick.getX());
        dashboardHigh.addDouble(joystick.getY());
        dashboardHigh.addDouble(joystick.getZ());
        dashboardHigh.finalizeArray();
        dashboardHigh.finalizeCluster();
        
        dashboardHigh.commit();
        dashboardHigh.flush();
    }
}
