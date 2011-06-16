package edu.nfrc;

import edu.wpi.first.wpilibj.DigitalInput;

public class nLineSensor
{
    DigitalInput leftSensor;
    DigitalInput centerSensor;
    DigitalInput rightSensor;
    
    public nLineSensor(int leftChannel, int centerChannel, int rightChannel)
    {
        leftSensor = new DigitalInput(leftChannel);
        centerSensor = new DigitalInput(centerChannel);
        rightSensor = new DigitalInput(rightChannel);
    }

    public nLineSensor(int leftSlot, int leftChannel, int centerSlot, int centerChannel, int rightSlot, int rightChannel)
    {
        leftSensor = new DigitalInput(leftSlot, leftChannel);
        centerSensor = new DigitalInput(centerSlot, centerChannel);
        rightSensor = new DigitalInput(rightSlot, rightChannel);
    }

    public int getOutputInt()
    {
        int statusValue = 0;
        
        try
        {
            int leftLineValue = (leftSensor.get() ? 1 : 0);
            int centerLineValue = (centerSensor.get() ? 1 : 0);
            int rightLineValue = (rightSensor.get() ? 1 : 0);
            statusValue = leftLineValue * 100 + centerLineValue * 10 + rightLineValue;
        }
        catch(NullPointerException e){}

        return statusValue;
    }

    public boolean[] getOutputBoolean()
    {
        boolean[] output = new boolean[3];
        
        output[0] = leftSensor.get();
        output[1] = centerSensor.get();
        output[2] = rightSensor.get();

        return output;
    }
}
