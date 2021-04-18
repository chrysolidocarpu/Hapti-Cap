package com.company;

public class GenericHelpers {
    public GenericHelpers(){}

    public double Compass360Loop(double input, double offset)
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
