package com.company;

import java.lang.Math;

public class LatLon {

    // internal variables (set as DECIMALS) not radians
    private double latitude;
    private double longitude;

    //constructor
    //set coordinates in DECIMALS (not radians)
    public LatLon(){
        latitude = 0;
        latitude = 0;
    }
    public LatLon(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // public/quick access to latitude or longitude
    public double getLatitude_Decimal(){
        return latitude;
    }

    public double getLongitude_Decimal(){
        return longitude;
    }

    public double getLatitude_Radian(){
        return convertDegreesToRadians(latitude);
    }

    public double getLongitude_Radian(){
        return convertDegreesToRadians(longitude);
    }

    //if for some reason you need to change the coordinates,
    // use update. input in DECIMALS (not radians)
    public void Update(double latitudeInDecimal, double longitudeInDecimal)
    {
        latitude = latitudeInDecimal;
        longitude = longitudeInDecimal;
    }

    //private function, convert decimal to radians
    private double convertDegreesToRadians(double degrees)
    {
        //convert decimals to radians
       return Math.toRadians(degrees);

    }
    //private function, convert radians to decimal
    private double convertRadiansToDegrees(double radians)
    {
        //convert decimals to radians
        return Math.toDegrees(radians);
    }

    // return latitude and longitude in an array
    public double[] getLatLon_Decimal()
    {
        double[] output = new double[2];
        output[0] = latitude;
        output[1] = longitude;
        return output;
    }
    public double[] getLatLon_Radians()
    {
        double[] output = new double[2];
        output[0] = convertDegreesToRadians(latitude);
        output[1] = convertDegreesToRadians(longitude);
        return output;
    }
}