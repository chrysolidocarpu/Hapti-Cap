package com.company;
import java.lang.Math;

public class Trigonometry {
    //#R = diameter of earth
    //#6378137 Radius in meters
    //R = float(6378137)
    private static double R = 6378137;

    public Trigonometry(){}

    //calculate the distance between (latlon)coordinates A and B
    public double Distance(LatLon StartingPoint, LatLon EndingPoint)
    {
        // work with RADIANS
        // and precalculate some values
        double lat1 = StartingPoint.Latitude_Radian();
        double lat2 = EndingPoint.Latitude_Radian();
        double lon1 = StartingPoint.Longitude_Radian();
        double lon2 = EndingPoint.Longitude_Radian();
        double deltalat = (lat2 - lat1);
        double deltalon = (lon2 - lon1);

        //horrible math that gives me nightmares
        double a = Math.pow(Math.sin(deltalat/2),2)
                 + Math.cos(lat1)       * Math.cos(lat2)
                 * Math.pow( Math.sin(deltalon/2),2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        //calculate the distance in meters
        double distance = R * c;
        //we have the distance!!!!! now return it
        return distance;

    }

    public double Bearing(LatLon StartingPoint, LatLon EndingPoint ){
        double output;
        //work with RADIANS!!!!!!!
        double delta_lon = EndingPoint.Longitude_Radian() - StartingPoint.Longitude_Radian();

        // this calculation is designed to heat up the cpu....
        // or it somehow calculates a bearing with trigonometry;
        // one or the other.
        double initial_bearing = Math.atan2(Math.sin(delta_lon) * Math.cos(EndingPoint.Latitude_Radian())
                                            , (Math.cos(StartingPoint.Latitude_Radian()) * Math.sin(EndingPoint.Latitude_Radian())
                                            - ( Math.sin(StartingPoint.Latitude_Radian()) * Math.cos(EndingPoint.Latitude_Radian()) * Math.cos(delta_lon))));

        //convert this bearing from radians to degrees
        initial_bearing = Math.toDegrees(initial_bearing);

        // and create a compass heading. (0-359)
        output = (initial_bearing + 360) % 360;

        return output;
    }

    public LatLon Waypoint(LatLon StartingPoint, double distance, double bearing)
    {
        // create a new and empty dataset
        LatLon Output = new LatLon(0,0);
        //distance is in meters

        //bearing is in degrees so...
        //convert bearing into radians
        bearing = Math.toRadians(bearing);

        // the math begins.......
        double latitude = (Math.asin( (Math.sin(StartingPoint.Latitude_Radian()) * Math.cos(distance / R))
                                 + Math.cos(StartingPoint.Latitude_Radian())
                                 * Math.sin(distance / R)
                                 * Math.cos(bearing)));
        //convert back into degrees..
        latitude = Math.toDegrees(latitude);


        // math... more math, why more math? i just want the numbers to appear magically!!!!
        // also... this should give you lat/long coordinates based on a starting point, a bearing and a distance
        double longitude = StartingPoint.Longitude_Radian()
                    + Math.atan2( Math.sin(bearing) * Math.sin(distance /R) * Math.cos(StartingPoint.Latitude_Radian())
                    ,     Math.cos(distance /R)
                        - Math.sin(StartingPoint.Latitude_Radian())
                        * Math.sin( Math.asin(    Math.sin(StartingPoint.Latitude_Radian())
                                                * Math.cos(distance / R )
                                                + Math.cos(StartingPoint.Latitude_Radian())
                                                * Math.sin( distance / R)
                                                * Math.cos(bearing)
                        )));
        //also convert into degrees
        longitude = Math.toDegrees(longitude);


        //put the new coordinates in the dataset
        Output.Update(latitude, longitude);
        return Output;
    }

    public Triangle Tri(double diagonal_angle, double diagonal_length)
    {
        Triangle output = new Triangle(diagonal_angle, diagonal_length);
        return output;
    }
}

