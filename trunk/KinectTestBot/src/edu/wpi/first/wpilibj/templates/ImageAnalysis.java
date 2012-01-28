/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author Programming
 */
public class ImageAnalysis 
{
    private AxisCamera axis;
    private ParticleAnalysisReport[] report;   
    private ParticleAnalysisReport[] rectangle;
    private final double CAMERA_HEIGHT = 2; //height of camera in feet
    private final double HEIGHT = 3/2; // height of backboard rectangle tape in feet
    private final double WIDTH = 2; // width of backboard rectangle tape in feet
    //view angle for the axis camera M1011-w
    private final double ANGLE = 43.5;//actual angle is 47
    
    public ImageAnalysis(AxisCamera a)
    {
        axis = a;
    }
    public void updateImage() throws AxisCameraException, NIVisionException
    {
	try {
            if(axis.freshImage())
            {
                ColorImage image = axis.getImage();
                report = image.thresholdHSL(0, 255, 0, 45, 200, 255).getOrderedParticleAnalysisReports();
                image.free();
            }
	} catch (AxisCameraException ex) {
	    ex.printStackTrace();
	}
        try
        {
        if(report.length > 0)
            findRectangles();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public double getRectangleScore(int particle)
    {
        //gives a score as to how close a particle is to a rectangle. Perfect rectangle gives 1
        return report[particle].particleArea/(report[particle].boundingRectHeight*report[particle].boundingRectWidth) ;
    }
    public double getAspectRatio(int particle)
    {
        //gives the ratio of the width of rectangle over height. Ideal ratio is 4/3
        return report[particle].boundingRectWidth / report[particle].boundingRectHeight;
    }
    
    //finds particles that are close to the rectangles that we are looking for
    public void findRectangles()
    {
        
        boolean[] a = new boolean[report.length];
        int count = 0;
        
        for(int i = 0; i < report.length; i++)
        {
            if(report[i].particleArea > 100)
            {
                a[i] = true;
                count++;
            }
            else
            {
                a[i] = false;
            }
        }
	ParticleAnalysisReport[] output = new ParticleAnalysisReport[count];
        for(int i = 0; i < report.length; i++)
        {
            if(a[i])
                output[i] = report[i];
        }
        rectangle = output;
	
    }
    public ParticleAnalysisReport[] getValidTargets()
    {
	return rectangle;
    }
    
    public double getDistance(int particle)
    {
        //in feet
        double FieldOfVision = HEIGHT/rectangle[particle].boundingRectHeight*rectangle[particle].imageWidth;
	double angle = MathUtils.acos(rectangle[particle].boundingRectWidth/axis.getResolution().width);
        double distance = Math.sqrt(MathUtils.pow((Math.sin(90-angle)/Math.sin(angle)*FieldOfVision),2)+MathUtils.pow(FieldOfVision, 2));
        return distance;
    }
    public double getHeight(int particle)
    {
        //in feet
        double FieldOfVision = HEIGHT/rectangle[particle].boundingRectHeight*rectangle[particle].imageHeight;
	double height = FieldOfVision/2*rectangle[particle].center_mass_y_normalized + CAMERA_HEIGHT;
	return height;
    }
}
