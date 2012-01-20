package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import com.sun.squawk.util.Arrays;

public class TractionControl 
{
    private Victor frontLeft;
    private Victor frontRight;
    private Victor rearLeft;
    private Victor rearRight;
    private Encoder frontLeftEncoder;
    private Encoder frontRightEncoder;
    private Encoder rearLeftEncoder;
    private Encoder rearRightEncoder;
    private Joystick joystick;
    private final double TOLERANCE = .05;
    
    public TractionControl(Victor frontLeft, Victor frontRight, Victor rearLeft, Victor rearRight, Joystick joystick, Encoder frontLeftEncoder, Encoder frontRightEncoder,Encoder rearLeftEncoder, Encoder rearRightEncoder)
    {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.joystick = joystick;
        this.frontLeftEncoder = frontLeftEncoder;
        this.frontRightEncoder = frontRightEncoder;
        this.rearLeftEncoder = rearLeftEncoder;
        this.rearRightEncoder = rearRightEncoder;
    }
    
    public void tractionControl()
    {
        double a = frontLeftEncoder.getRate();
        double b = frontRightEncoder.getRate();
        double c = rearLeftEncoder.getRate();
        double d = rearRightEncoder.getRate();
        double[] encoders = {a,b,c,d};      
        Victor[] victors = {frontLeft,frontRight,rearLeft,rearRight};
        
        int max = findMax(encoders);
        for(int i = 0; i < 4; i++)
        {
            if(encoders[max] - encoders[i] > TOLERANCE)
            {
                victors[i].set(victors[i].get()*encoders[max]/encoders[i]);
            }
        }
        
    }
    
    public int findMax(double[] array)
    {
        int max = 0;
        for(int i = 0; i < array.length; i++)
        {
            if(Math.abs(array[max]) < Math.abs(array[i]))
            {
                max = i;
            }
        }
        return max;
    }
}
