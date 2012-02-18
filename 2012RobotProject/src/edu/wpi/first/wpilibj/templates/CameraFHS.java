package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class CameraFHS
{
    private ImageAnalysis analysis;
    private AxisCamera axis;
    private Drivetrain drivetrain;

    public CameraFHS(Drivetrain drivetrain, ImageAnalysis image)
    {
		axis = AxisCamera.getInstance();
		this.drivetrain = drivetrain;
		analysis = image;
    }

    public void centerOnFirstTarget() throws AxisCameraException, NIVisionException
    {
		analysis.updateImage(130);

		ParticleAnalysisReport[] report = analysis.getRectangles();

		double xNormal;

		if(report.length > 0)
		{
			xNormal = report[0].center_mass_x_normalized;

			System.out.println(xNormal);

			drivetrain.frontLeftSet(xNormal*0.3);
			drivetrain.frontRightSet(xNormal*0.3);
			drivetrain.rearLeftSet(xNormal*0.3);
			drivetrain.rearRightSet(xNormal*0.3);
		}
		else
		{
			System.out.println("No Valid Targets Found!");
			drivetrain.frontLeftSet(0.0);
			drivetrain.frontRightSet(0.0);
			drivetrain.rearLeftSet(0.0);
			drivetrain.rearRightSet(0.0);
		}

    }
		public void centerOnTarget(int target,int lum) throws AxisCameraException, NIVisionException//0:bottom 1:middle(either) 2:top
    {
		analysis.updateImage(lum);

		ParticleAnalysisReport report = analysis.findTarget(target);

		double xNormal;

		if(report != null)
		{
			xNormal = report.center_mass_x_normalized;

			System.out.println(xNormal);

			drivetrain.frontLeftSet(xNormal*0.3);
			drivetrain.frontRightSet(xNormal*0.3);
			drivetrain.rearLeftSet(xNormal*0.3);
			drivetrain.rearRightSet(xNormal*0.3);
		}
		else
		{
			System.out.println("Target Not Found!");
			drivetrain.frontLeftSet(0.0);
			drivetrain.frontRightSet(0.0);
			drivetrain.rearLeftSet(0.0);
			drivetrain.rearRightSet(0.0);
		}

    }

}
