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

    public double getRotation(){
        return rotation;
    }

    //return arbitrary bearing with offset
    public double getBearing(double degrees)
    {
        return Offset_Course_Calculator(degrees, rotation);
    }
    //return north,east,west,south with adjusted bearing
    public double getBearingNorth()
    {
        return getBearing(0);
    }
    public double getBearingSouth()
    {
        return getBearing(180);
    }
    public double getBearingEast()
    {
        return getBearing(90);
    }
    public double getBearingWest()
    {
        return getBearing(270);
    }

    // function to find the compass bearing when the map is rotated (map north not equal to real life north)
    // set the offset when creating the compass
    // it *should* be protected from inputting bad things(numbers)
    private double Offset_Course_Calculator(double inputDegrees, double offsetDegrees)
    {
        double degrees = inputDegrees % 360;
        double max = offsetDegrees + 360;
        double heading = offsetDegrees + degrees;
        if(heading < 0 )
            heading = max - Math.abs(heading - offsetDegrees);
        if(heading > 360 )
            heading = heading % 360;
        return heading;
    }
}