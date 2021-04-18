package com.company;

import java.lang.Math;

public class LatLon {

    // internal variables (set as DECIMALS) not radians
    private double _latitude;
    private double _longitude;

    //constructor
    //set coordinates in DECIMALS (not radians)
    public LatLon(){
        this._latitude = 0;
        this._latitude = 0;
    }
    public LatLon(double latitude, double longitude)
    {
        this._latitude = latitude;
        this._longitude = longitude;
    }
    //if for some reason you need to change the coordinates,
    // use update. input in DECIMALS (not radians)
    public void Update(double latitude, double longitude)
    {
        this._latitude = latitude;
        this._longitude = longitude;
    }


    //private function, convert decimal to radians
    private double Radians(double input)
    {
        //convert decimals to radians
        double output;
        output  = Math.toRadians(input);
        return output;
    }
    //private function, convert radians to decimal
    private double Degrees(double input)
    {
        //convert decimals to radians
        double output;
        output  = Math.toDegrees(input);
        return output;
    }


    // public/quick access to latitude or longitude
    public double Latitude_Decimal()  {return this._latitude; };
    public double Longitude_Decimal() {return this._longitude;};

    public double Latitude_Radian()  {return Radians(this._latitude); };
    public double Longitude_Radian() {return Radians(this._longitude);};


    // return latitude and longitude in an array
    public double[] LatLon_Decimal()
    {
        double[] output = new double[2];
        output[0] = this._latitude;
        output[1] = this._longitude;
        return output;
    }
    public double[] LatLon_Radians()
    {
        double[] output = new double[2];

        output[0] = this.Radians(this._latitude);
        output[1] = this.Radians(this._longitude);

        return output;
    }
}