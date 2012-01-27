/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author Programming
 */
public class ImageAnalysis {
    AxisCamera axis;
    ParticleAnalysisReport[] report;   
    ParticleAnalysisReport[] rectangle;
    final double CAMERA_HEIGHT = 3; //height of camera in feet
    final double HEIGHT = 3/2; // height of backboard rectangle tape in feet
    final double WIDTH = 2; // width of backboard rectangle tape in feet
    //view angle for the axis camera M1011-w
    final double ANGLE = 43.5;//actual angle is 47
    
    public ImageAnalysis(AxisCamera a) throws AxisCameraException, NIVisionException
    {
        try {
            axis = a;
            report = a.getImage().thresholdHSL(136, 182, 45, 255, 116, 255).getOrderedParticleAnalysisReports();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        }
        rectangle = getValidTargets();
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
    
    //returns particles that are close to the rectangles that we are looking for
    public ParticleAnalysisReport[] getValidTargets()
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
        return output;
    }
    
    public double getDistance(int particle)
    {
        //in feet
        double FieldOfVision = HEIGHT/rectangle[particle].boundingRectHeight*rectangle[particle].imageWidth;
	double angle = MathUtils.acos(rectangle[particle].boundingRectWidth/axis.getResolution().width);
        double distance = Math.sqrt(MathUtils.pow((Math.sin(90-angle)/Math.sin(angle)*FieldOfVision),2)+MathUtils.pow(FieldOfVision, 2));
        return distance;
    }
}
