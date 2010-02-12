package edu.fhs.actuators;


public class TestPitchSmoothing 
{
    public static void main(String[] args)
    {
        PitchSmoothing smoother = new PitchSmoothing(2);
        final double newSpeed = smoother.gyroAutonomousAngleSpeedAdjust(0.5, true, 0);
        boolean test = ((newSpeed - 0.5) < .001);
        isValid(test, "speed must be double .5");
        //TODO 3 more fixed value tests to make sure our equation is sane
        //TODO This first test will probably throw division by 0 exception
        //TODO Put in a loop to exercise values from 0 to 45 and then print
        //  to system.out.  Graph it in excel.
        //System.out.println(x, y);
        //Where x is our angle in and y is our value out.
        //Note that these values are an ADJUSTMENT to speed.
        //Equation is speedOut = speedIn - (adjustment)
        //  where adjustment is (speedIn * angle/max angle)
    } 
    
    private static void isValid(boolean testValue, String message) {
        if (!testValue) {
            System.out.println(message);
        }
    }


}
