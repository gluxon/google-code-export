/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.cldc.jna.BlockingFunction;
import com.sun.cldc.jna.NativeLibrary;
import com.sun.cldc.jna.Pointer;
import com.sun.cldc.jna.Structure;
import com.sun.cldc.jna.TaskExecutor;
import edu.wpi.first.wpilibj.image.*;

/**
 *
 * @author Programming
 */
public class RectangleVision// extends NIVision
{
    /*
    static final int ellipseMatchSize = 40;
    public double m_xPos;
    public double m_yPos;
    public double m_rotation;
    public double m_majorRadius;
    public double m_minorRadius;
    public double m_score;
    private Image asdf;
    
    private static final TaskExecutor taskExecutor = new TaskExecutor("nivision task");
    
    private static final BlockingFunction imaqDetectEllipsesFn =
            NativeLibrary.getDefaultInstance().getBlockingFunction("imaqDetectEllipses");
    static { imaqDetectEllipsesFn.setTaskExecutor(RectangleVision.taskExecutor); }
    private static Pointer numberOfEllipsesDetected = new Pointer(4);
    
    public static EllipseMatch[] detectEllipses(MonoImage image, EllipseDescriptor ellipseDescriptor,
            CurveOptions curveOptions, ShapeDetectionOptions shapeDetectionOptions,
            RegionOfInterest roi) throws NIVisionException {

        int curveOptionsPointer = 0;
        if (curveOptions != null)
            curveOptionsPointer = curveOptions.getPointer().address().toUWord().toPrimitive();
        int shapeDetectionOptionsPointer = 0;
        if (shapeDetectionOptions != null)
            shapeDetectionOptionsPointer = shapeDetectionOptions.getPointer().address().toUWord().toPrimitive();
        int roiPointer = 0;
        if (roi != null)
            roiPointer = roi.getPointer().address().toUWord().toPrimitive();

        int returnedAddress =
                imaqDetectEllipsesFn.call6(
                image.image.address().toUWord().toPrimitive(),
                ellipseDescriptor.getPointer().address().toUWord().toPrimitive(),
                curveOptionsPointer, shapeDetectionOptionsPointer,
                roiPointer,
                numberOfEllipsesDetected.address().toUWord().toPrimitive());

        try {
            NIVision.assertCleanStatus(returnedAddress);
        } catch (NIVisionException ex) {
            if (!ex.getMessage().equals("No error."))
                throw ex;
        }

        RectangleVision[] matches = RectangleVision.getMatchesFromMemory(returnedAddress, numberOfEllipsesDetected.getInt(0));
        NIVision.dispose(new Pointer(returnedAddress,0));
        return matches;
    }
    
    protected static RectangleVision[] getMatchesFromMemory(int address, int number) {
        if (address == 0) {
            return new RectangleVision[0];
        }

        RectangleVision[] toReturn = new RectangleVision[number];
        for (int i = 0; i < number; i++) {
            toReturn[i] = new RectangleVision(address + i * ellipseMatchSize, 1);
        }
        return toReturn;
    }
    
    public RectangleVision(int address, int a)
    {
        new EllipseMatchStructure(address);
        a = a+1;
    }
    
    private class EllipseMatchStructure extends Structure 
    {

        public EllipseMatchStructure(int address) {
            useMemory(new Pointer(address, ellipseMatchSize));
            read();
        }

        public void read() {
            m_xPos = backingNativeMemory.getFloat(0);
            m_yPos = backingNativeMemory.getFloat(4);
            m_rotation = backingNativeMemory.getDouble(8);
            m_majorRadius = backingNativeMemory.getDouble(16);
            m_minorRadius = backingNativeMemory.getDouble(24);
            m_score = backingNativeMemory.getDouble(32);

        }

        public void write() {
        }

        public int size() {
            return ellipseMatchSize;
        }
    }*/
    
}
