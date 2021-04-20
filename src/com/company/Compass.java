package com.company;

public class Compass {
    private double rotation;
    //constructors
    // compass, returns a bearing between 0-359

    public Compass(double rotation)
    {
        this.rotation = rotation;
    }
    public Compass()
    {
        this.rotation = 0;
    }

    //if for some reason you want to adjust the rotation, update.
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    //return arbitrary bearing with offset
    public double Bearing(double degrees)
    {
        return Offset_Course_Calculator(degrees, rotation);
    }
    //return north,east,west,south with adjusted bearing
    public double getBearingNorth()
    {
        return Bearing(0);
    }
    public double getBearingSouth()
    {
        return Bearing(180);
    }
    public double getBearingEast()
    {
        return Bearing(90);
    }
    public double getBearingWest()
    {
        return Bearing(270);
    }

    // function to find the compass bearing when the map is rotated (map north not equal to real life north)
    // set the offset when creating the compass
    // it *should* be protected from inputting bad things(numbers)
    private double Offset_Course_Calculator(double degrees, double offset)
    {
        degrees = degrees % 360;
        double max = offset + 360;
        double output = offset + degrees;
        if(output < 0 ){  output= max - Math.abs(output - offset); }
        if(output > 360 ){  output= output % 360;   }
        return output;
    }
}