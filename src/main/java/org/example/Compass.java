package org.example;

public class Compass {

    private double bearing;

    public Compass(){}


    public double Wrap360(double bearing)
    {
        // convert
        double output = bearing % 360;
        return output;
    }
//
}
