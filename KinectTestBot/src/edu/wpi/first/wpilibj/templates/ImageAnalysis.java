/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.BinaryImage;
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
    private final double BOTTOM_HEIGHT = 28.0/12; //height of bottom hoop in feet
    private final double MIDDLE_HEIGHT = 61.0/12; //height of middle hoop in feet
    private final double TOP_HEIGHT = 98.0/12; //height of top hoop in feet
    private final double HEIGHT = 3.0/2; // height of backboard rectangle tape in feet
    private final double WIDTH = 2; // width of backboard rectangle tape in feet
    //view angle for the axis camera M1011-w
    private final double ANGLE = 43.5;//actual angle is 47
   
    public ImageAnalysis(AxisCamera a)
    {
        axis = a;
    }
    public void updateImage() throws AxisCameraException, NIVisionException
    {

        try
        {
            if (axis.freshImage())
            {
                ColorImage image = axis.getImage();
                BinaryImage binaryImage = image.thresholdHSL(0,255,0,100,165,255).convexHull(true);
                if(binaryImage.getNumberParticles() > 15)
                    binaryImage = binaryImage.removeSmallObjects(true, binaryImage.getNumberParticles()-15);
                report = binaryImage.getOrderedParticleAnalysisReports();
                binaryImage.free();
                image.free();
                rectangle = findRectangles();
            }
        }
        catch (AxisCameraException ex)
        {
            ex.printStackTrace();
        }
   
    }
    public double getRectangleScore(int particle)
    {
        //gives a score as to how similar a particle is to a rectangle. Perfect rectangle gives 1
        return report[particle].particleArea / (report[particle].boundingRectHeight * report[particle].boundingRectWidth) ;
    }
    public double getAspectRatio(int particle)
    {
        //gives the ratio of the width of rectangle over height. Ideal ratio is 4/3
        return report[particle].boundingRectWidth / report[particle].boundingRectHeight;
    }

    //finds particles that are close to the rectangles that we are looking for
    public ParticleAnalysisReport[] findRectangles()
    {

        boolean[] a = new boolean[report.length];
        int count = 0;
       
        for(int i = 0; i < report.length; i++)
        {
            if(getRectangleScore(i) > .8)
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
        count = 0;
        for(int i = 0; i < report.length; i++)
        {
            if(a[i])
            {
                output[count] = report[i];
                count++;
            }
        }
        return output;

    }
    public int getRectangleLength()
    {
        if(rectangle == null)
            return -1;
        return rectangle.length;
    }

    public double getDistance(int particle) {
        //in feet
        //double thisvalue = HEIGHT / rectangle[particle].boundingRectHeight * rectangle[particle].boundingRectWidth;
       
        double FieldOfVision = HEIGHT / rectangle[particle].boundingRectHeight * rectangle[particle].imageWidth;
        double angle = MathUtils.acos(rectangle[particle].boundingRectWidth / axis.getResolution().width);
        double distance = Math.sqrt(MathUtils.pow((Math.sin(Math.PI/2-angle)/Math.sin(angle)*FieldOfVision),2)+MathUtils.pow(FieldOfVision, 2)); // Pythagorean Theorem/Distance Formula

        /*double a=(Math.sin(Math.PI/2-angle) / Math.sin(angle)*FieldOfVision);
        double b=FieldOfVision / 2;
        double angle_c = 911;
        double distance = Math.sqrt( MathUtils.pow(a,2) + MathUtils.pow(b,2) - 2 * a * b * Math.cos(angle_c) ); // Law of Cosines ^^ *better than above*
        */
        return distance;
    }

    public double getHeight(int particle) {
        //in feet assuming camera points straight ahead at all times
        double FieldOfVision = HEIGHT/rectangle[particle].boundingRectHeight*rectangle[particle].imageHeight;
        double height = FieldOfVision/2*rectangle[particle].center_mass_y_normalized + CAMERA_HEIGHT;
        return height;
    }
    public String getWhichTarget(int particle)
    {
        if(Math.abs(getHeight(particle)-TOP_HEIGHT) < 1)
            return "top";
        if(Math.abs(getHeight(particle)-MIDDLE_HEIGHT) < 1)
            return "middle";
        if(Math.abs(getHeight(particle)-BOTTOM_HEIGHT) < 1)
            return "bottom";

        return "-1";
    }
}