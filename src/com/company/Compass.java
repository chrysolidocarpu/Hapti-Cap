package com.company;

public class Compass {
    private double _rotation;

    //constructors
    // compass, returns a bearing between 0-359
    public Compass(double rotation)
    {
        this._rotation = rotation;
    }
    public Compass()
    {
        this._rotation = 0;
    }

    //if for some reason you want to adjust the rotation, update.
    public  void Update(double offset)
    {
        this._rotation = offset;
    }

    //return arbitrary bearing with offset
    public double Bearing(double input)
    {
        double output;
        output = Offset_Course_Calculator(input, this._rotation);
        return output;
    }
    //return north,east,west,south with adjusted bearing
    public double North()
    {
        return Bearing(0);
    }
    public double South()
    {
        return Bearing(180);
    }
    public double East()
    {
        return Bearing(90);
    }
    public double West()
    {
        return Bearing(270);
    }

    // function to find the compass bearing when the map is rotated (map north not equal to real life north)
    // set the offset when creating the compass
    // it *should* be protected from inputting bad things(numbers)
    private double Offset_Course_Calculator(double input, double offset)
    {
        double output = 0;
        input = input % 360;

        double min = offset;
        double max = min + 360;

        output = min + input;
        if(output < 0 ){  output= max - Math.abs(output - min); }
        if(output > 360 ){  output= output % 360;   }

        return  output;
    }

}
