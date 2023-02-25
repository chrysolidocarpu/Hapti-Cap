package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SupportFunctions {
    //class that hold functions for generic usage.
    public SupportFunctions(){}

    public double RoundHalfup (double input, int precision)
    {
        BigDecimal output = new BigDecimal(input).setScale(precision, RoundingMode.HALF_UP);
        return output.doubleValue();
    }
    public double RoundUp (double input, int precision)
    {
        BigDecimal output = new BigDecimal(input).setScale(precision, RoundingMode.UP);
        return output.doubleValue();
    }
    public double RoundDown (double input, int precision)
    {
        BigDecimal output = new BigDecimal(input).setScale(precision, RoundingMode.DOWN);
        return output.doubleValue();
    }

    //Compas bearing types (0-360) or (-180, 180)
    public double Wrapped360(double input, boolean roundhalfup)
    {

        double output = 999; // just set to 999 in case the user input something odd, then at least we know it went horribly wrong.

        if ((0<=input) && (input<360)) { output = input; } // no need to do anything, the bearing is within the 0-360 range
        if(input < 0 && input >= -180)
        {
            // okay, so a negative value of -0 to -180 i.e. -179 is actually a bearing of 181 therefor the 360 bearing is (-179 + 180 = 1) + 180;
            output = (input + 180) + 180;
        }
        // this should always be a value between 0 and 360. IF the value is 999 the user input was out of bounds.
        if(roundhalfup = true){ output = this.RoundHalfup(output,2); }

        return output;
    }

    public double Wrapped180(double input, boolean roundhalfup)
    {
        //Wrapped 180 compass degrees explained,
        // North is 0 and -0;
        // South is180 and -180
        // East is 90,
        // West is -90
        // All negative values are therefor "on the left hand side" off the compass
        // and the positive ones are "on the right hand side";
        //
        // note this function does not allow for inputs greater then 360 degrees or smaller then 0 degrees.

        double output = 999; // by default throw an error;

        if((input <= 180) && input >= 0) {output = input; }  // the input is within set range of 0 to 180, so do nothing!!
        if((input > 180) && (input <= 360))
        {
            // so the input is a 360 degree compass value,
            // i.e. 270; on the 180,180 scale the correct value would be -90;
            // so we calculate -180(due south) - (270 - 180), but since it is west we must get a negative value;
            // therefor. 90 - (2*90) = -90;
            output = -180 + (input - 180);
        }
        //if we set the rounding flag we round it down to 2 decimals.
        if(roundhalfup = true){ output = this.RoundHalfup(output,2); }
        // this should always be a value between -180 and 180. IF the value is 999 the user input was out of bounds.
        return input;
    }

    //
    public double DegreesToRadians(double degrees)
    {
        double radians = (degrees * Math.PI)/ 180;
        return this.RoundHalfup(radians,8);
    }
    public double RadiansToDegrees(double radians)
    {
        double degrees = 180 * (radians/Math.PI );
        return this.RoundHalfup(degrees,6);
    }

}

