package com.company;
import java.lang.Math;

public class Trigonometry {
    //#R = diameter of earth
    //#6378137 Radius in meters
    //R = float(6378137)
    private static double R = 6378137;
    public Trigonometry(){}

    public Trigonometry(double radius){
        R = radius;
    }

    //calculate the distance between (latLon)coordinates A and B
    public double getDistance(LatLon startingPoint, LatLon endingPoint)
    {
        // work with RADIANS
        // and precalculate some values
        double lat1 = startingPoint.getLatitude_Radian();
        double lat2 = endingPoint.getLatitude_Radian();
        double lon1 = startingPoint.getLongitude_Radian();
        double lon2 = endingPoint.getLongitude_Radian();
        double deltaLat= (lat2 - lat1);
        double deltaLon = (lon2 - lon1);

        //horrible math that gives me nightmares
        double a = Math.pow(Math.sin(deltaLat/2),2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.pow( Math.sin(deltaLon/2),2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        //calculate the distance in meters
        //we have the distance!!!!! now return it
        return R * c;
    }

    public double getBearing(LatLon StartingPoint, LatLon EndingPoint ){
        //work with RADIANS!!!!!!!
        double delta_lon = EndingPoint.getLongitude_Radian() - StartingPoint.getLongitude_Radian();

        // this calculation is designed to heat up the cpu....
        // or it somehow calculates a bearing with trigonometry;
        // one or the other.
        double initial_bearing = Math.atan2(Math.sin(delta_lon) * Math.cos(EndingPoint.getLatitude_Radian())
                                            , (Math.cos(StartingPoint.getLatitude_Radian()) * Math.sin(EndingPoint.getLatitude_Radian())
                                            - ( Math.sin(StartingPoint.getLatitude_Radian()) * Math.cos(EndingPoint.getLatitude_Radian()) * Math.cos(delta_lon))));

        //convert this bearing from radians to degrees
        initial_bearing = Math.toDegrees(initial_bearing);
        // and create a compass heading. (0-359)
        return (initial_bearing + 360) % 360;
    }

    public LatLon getWaypointCoordinates(LatLon StartingPoint, double distance, double bearing)
    {
        // create a new and empty dataset
        LatLon latLonOutput = new LatLon(0,0);
        //distance is in meters

        //bearing is in degrees so...
        //convert bearing into radians
        double radianBearing = Math.toRadians(bearing);

        // the math begins.......
        double radianLatitude = (Math.asin( (Math.sin(StartingPoint.getLatitude_Radian()) * Math.cos(distance / R))
                                 + Math.cos(StartingPoint.getLatitude_Radian())
                                 * Math.sin(distance / R)
                                 * Math.cos(radianBearing)));
        //convert back into degrees..
        double latitude = Math.toDegrees(radianLatitude);

        // math... more math, why more math? i just want the numbers to appear magically!!!!
        // also... this should give you lat/long coordinates based on a starting point, a bearing and a distance
        double radianLongitude = StartingPoint.getLongitude_Radian()
                    + Math.atan2( Math.sin(radianBearing) * Math.sin(distance /R) * Math.cos(StartingPoint.getLatitude_Radian())
                    ,     Math.cos(distance /R)
                        - Math.sin(StartingPoint.getLatitude_Radian())
                        * Math.sin( Math.asin(    Math.sin(StartingPoint.getLatitude_Radian())
                                                * Math.cos(distance / R )
                                                + Math.cos(StartingPoint.getLatitude_Radian())
                                                * Math.sin( distance / R)
                                                * Math.cos(radianBearing)
                        )));
        //also convert into degrees
        double longitude = Math.toDegrees(radianLongitude);

        //put the new coordinates in the dataset
        latLonOutput.Update(latitude, longitude);
        return latLonOutput;
    }

    public Triangle getTriangle(double diagonal_angle, double diagonal_length)
    {
        return new Triangle(diagonal_angle, diagonal_length);
    }
}

