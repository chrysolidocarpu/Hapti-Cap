package org.example;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Objects;

public class Coordinate {
    public LatLon Latitude;
    public LatLon Longitude;
    public String Description;
    private static final double R = 6371e3; // Diameter of the GLOBE in meters (in this case Earth)

    private SupportFunctions support = new SupportFunctions();
    private static final String html_source = "https://www.movable-type.co.uk/scripts/latlong.html";

    public Coordinate()
    {
        this.Latitude = new LatLon(LatLon.Type.Latitude);
        this.Longitude = new LatLon(LatLon.Type.Longitude);
    }


    public double Forward_Azimuth(Coordinate target)
    { return this.Bearing(target); }
    public double Bearing(Coordinate target)
    {
        /*
        Bearing
        In general, your current heading will vary as you follow a great circle path (orthodrome);
        the final heading will differ from the initial heading by varying degrees according to distance and latitude
        (if you were to go from say 35°N,45°E (≈ Baghdad) to 35°N,135°E (≈ Osaka), you would start on a heading of 60° and end up on a heading of 120°!).

        This formula is for the initial bearing (sometimes referred to as forward azimuth) which if followed in a straight line along a great-circle arc
        will take you from the start point to the end point:1

        Formula: 	θ = atan2( sin Δλ ⋅ cos φ2 , cos φ1 ⋅ sin φ2 − sin φ1 ⋅ cos φ2 ⋅ cos Δλ )
           where 	φ1,λ1 is the start point, φ2,λ2 the end point (Δλ is the difference in longitude)

        JavaScript: (all angles in radians)
	        const y = Math.sin(λ2-λ1) * Math.cos(φ2);
            const x = Math.cos(φ1)*Math.sin(φ2) -
                      Math.sin(φ1)*Math.cos(φ2) *
                      Math.cos(λ2-λ1);
            const θ = Math.atan2(y, x);
            const brng = (θ*180/Math.PI + 360) % 360; // in degrees
         */

        double y =  Math.sin(target.Longitude.Radians()- this.Longitude.Radians()) * Math.cos(target.Latitude.Radians());
        double x =  (Math.cos(this.Latitude.Radians()) * Math.sin(target.Latitude.Radians())) -
                (Math.sin(this.Latitude.Radians()) * Math.cos(target.Latitude.Radians()) *
                        Math.cos(target.Longitude.Radians() - this.Longitude.Radians()));
        double theta = Math.atan2(y,x);

        return this.support.RoundHalfup(((theta*180/Math.PI + 360) % 360),6);  // bearing in degrees
    }
    public double Distance(Coordinate target)
    {
        /*Distance
        This uses the ‘haversine’ formula to calculate the great-circle distance between two points – that is,
        the shortest distance over the earth’s surface – giving an ‘as-the-crow-flies’ distance between the points (ignoring any hills they fly over, of course!).

        Haversine formula:
        	a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
            c = 2 ⋅ atan2( √a, √(1−a) )
            d = R ⋅ c

        where 	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
        note that angles need to be in radians to pass to trig functions!

        JavaScript:
        const R = 6371e3; // metres
        const φ1 = lat1 * Math.PI/180; // φ, λ in radians
        const φ2 = lat2 * Math.PI/180;
        const Δφ = (lat2-lat1) * Math.PI/180;
        const Δλ = (lon2-lon1) * Math.PI/180;
        const a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                  Math.cos(φ1) * Math.cos(φ2) *
                  Math.sin(Δλ/2) * Math.sin(Δλ/2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        const d = R * c; // in metres

        Note in these scripts, I generally use lat/lon for latitude/longitude in degrees,
        and φ/λ for latitude/longitude in radians –
        having found that mixing degrees & radians is often the easiest route to head-scratching bugs...
        */

        double Delta_Latitude_Radians = (target.Latitude.DD() - this.Latitude.DD()) * Math.PI/180;
        double Delta_Longitude_Radians = (target.Longitude.DD() - this.Longitude.DD()) * Math.PI/180;

        double a =  Math.sin(Delta_Latitude_Radians/2) *  Math.sin(Delta_Latitude_Radians/2) +
                Math.cos(this.Latitude.Radians()) * Math.cos(target.Latitude.Radians()) *
                        Math.sin(Delta_Longitude_Radians/2) * Math.sin(Delta_Longitude_Radians/2);
        double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double distance =  this.support.RoundHalfup((R * c),2); // distance is in meters, so 2 decimal points would be centimeters
        return distance;

    }



    public Coordinate Midpoint(Coordinate target, String description, boolean normalised)
    {
        /*
        This is the half-way point along a great circle path between the two points.1
        Formula: 	Bx = cos φ2 ⋅ cos Δλ
	                By = cos φ2 ⋅ sin Δλ
	                φm = atan2( sin φ1 + sin φ2, √(cos φ1 + Bx)² + By² )
	                λm = λ1 + atan2(By, cos(φ1)+Bx)
        JavaScript: (all angles in radians)
	        const Bx = Math.cos(φ2) * Math.cos(λ2-λ1);
            const By = Math.cos(φ2) * Math.sin(λ2-λ1);
            const φ3 = Math.atan2(Math.sin(φ1) + Math.sin(φ2),
                       Math.sqrt( (Math.cos(φ1)+Bx)*(Math.cos(φ1)+Bx) + By*By ) );
            const λ3 = λ1 + Math.atan2(By, Math.cos(φ1) + Bx);

        The longitude can be normalised to −180…+180 using (lon+540)%360-180
        Just as the initial bearing may vary from the final bearing, the midpoint may not be located half-way between latitudes/longitudes;
        the midpoint between 35°N,45°E and 35°N,135°E is around 45°N,90°E.
         φ/λ for latitude/longitude in radians
         */
        Coordinate midpoint = new Coordinate();
        midpoint.Description = description;

        double Bx = Math.cos(target.Latitude.Radians()) * Math.cos(target.Longitude.Radians() - this.Longitude.Radians() );
        double By = Math.cos(target.Latitude.Radians()) * Math.sin(target.Longitude.Radians() - this.Longitude.Radians() );
        double DLat = Math.atan2(Math.sin(this.Latitude.Radians()) + Math.sin(target.Latitude.Radians()),
                      Math.sqrt( (Math.cos(this.Latitude.Radians()) + Bx) * (Math.cos(this.Latitude.Radians())+ Bx)+ By*By) );
        double Dlon = this.Longitude.Radians() + Math.atan2( By, Math.cos(this.Latitude.Radians() + Bx));

        if(normalised == true)
        {
            Dlon = (Dlon +540)%360-180;
        }
        midpoint.Latitude.Set_Radians(DLat);
        midpoint.Longitude.Set_Radians(Dlon);

        return  midpoint;
    }

    public Coordinate Destination(double distance, double bearing, boolean normalised)
    {
        /*
        Given a start point, initial bearing, and distance, this will calculate the destination point and final bearing travelling along a
        (shortest distance) great circle arc.
        Destination point along great-circle given distance and bearing from start point

        Formula: 	φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
	                λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2 )
        where 	φ is latitude, λ is longitude, θ is the bearing (clockwise from north), δ is the angular distance d/R;
        d being the distance travelled, R the earth’s radius

        JavaScript: (all angles in radians)

        const φ2 = Math.asin( Math.sin(φ1)*Math.cos(d/R) +
                   Math.cos(φ1)*Math.sin(d/R)*Math.cos(brng) );
        const λ2 = λ1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(φ1),
                   Math.cos(d/R)-Math.sin(φ1)*Math.sin(φ2));

	    The longitude can be normalised to −180…+180 using (lon+540)%360-180
         */
        double latitude;
        double longitude;

        if(normalised == true )
        {
            longitude = (longitude + 540)%360-180;
        }

        Coordinate destination = new Coordinate();

        return destination;
    }


}

class LatLon
{
    public enum CompasSuffix {North, East, South, West, NA, Error}
    public enum Type {Latitude, Longitude}

    private double _degrees;
    private double _minutes;
    private double _seconds;
    private double _decimaldegrees;

    private LatLon.CompasSuffix _suffix;
    private LatLon.Type _type;

    public LatLon(LatLon.Type type) {
        this._degrees = 0;
        this._minutes = 0;
        this._seconds = 0;
        this._decimaldegrees = 0;
        this._suffix = LatLon.CompasSuffix.NA;
        this._type = type;
    }
    public double Degrees() { return _degrees; }

    public double Minutes() {
        return _minutes;
    }

    public double Seconds() {return RoundHalfup(_seconds,2); }

    //public double Radians() { return this.support.RoundHalfup(this.support.DegreesToRadians(this._decimaldegrees),6); }
    public LatLon.CompasSuffix Suffix() {
        return this._suffix;
    }

    public double DD() {
        return this._decimaldegrees;
    }

    public double Radians()
    {
        double radians = (_decimaldegrees * Math.PI)/ 180;
        radians = RoundHalfup(radians,8);
        return radians;
    }

    public String DMS(boolean annotations) {
        // the annotation to be added (or not)
        String _degree = "° ";
        String _minute = "' ";
        String _second = "\" ";

        String output = "";
        // logic...
        if (annotations == false) {
            output = this._degrees + " " + this._minutes + " " + this._seconds + " " + this._suffix.toString();
        } else {
            output = this._degrees + _degree + this._minutes + _minute + this._seconds + _second + " " + this._suffix.toString();
        }

        return output;
    }
    private double Positive_Only(double input, boolean AutoInverse) {
        // set default value to zero
        double output = 0;
        // if bigger then zero make output equal to input
        if (input >= 0) {
            output = input;
        }
        // if autoinverse is set and the input is smaller then zero inverse it
        if ((AutoInverse == true) && (input < 0)) {
            output = Inverse(input);
        }

        return output;

    }

    private double Inverse(double input) {
        // function to inverse a number  from positive to negative and vice versa
        // i.e. -1 becomes 1; 2 becomes -2
        input = input - (2 * input);
        return input;
    }

    // does the compass direction match the type (latitude or longitude)
    private boolean Sanity_Suffix(LatLon.CompasSuffix suffix) {
        boolean sane = false;
        // latitude means north south.
        if (this._type == LatLon.Type.Latitude) {
            if ((suffix == LatLon.CompasSuffix.North) || (suffix == LatLon.CompasSuffix.South)) {
                sane = true;
            }
        }
        if (this._type == LatLon.Type.Longitude) {
            if ((suffix == LatLon.CompasSuffix.West) || (suffix == LatLon.CompasSuffix.East)) {
                sane = true;
            }
        }

        return sane;
    }


    //
    // Setters (with logic)
    //
    public void Set_Radians(double radians)
    {
        double degrees = 180 * (radians/Math.PI );
        Set_DD( RoundHalfup(degrees,6) );
    }
    public void Set_DD(double decimaldegrees) {
        //calculate the degrees, minutes, seconds and compass suffix from given dd
        // note, a negative number means we are heading either west or south (depending on if this longitude ot latitude)

        // check if the input is a negative (for future reference) and inverse the value for calculation purposes if it is negative
        // (its easier to calculate using positive values)
        boolean isnegative = false;

        // well make it positive  if needed
        if (decimaldegrees < 0) {
            isnegative = true;
            decimaldegrees = Inverse(decimaldegrees);
        }

        double temp = 0;
        double d = Math.floor(decimaldegrees);
        temp = (decimaldegrees - d);
        double m = Math.floor((temp * 60));

        // source: https://www.calculatorsoup.com/calculators/conversions/convert-decimal-degrees-to-degrees-minutes-seconds.php
        double s = RoundHalfup( (((temp * 60) - m) * 60) ,6);

        // logic for compas suffix and to reset the dd value to its origional (negative) input state.

        if ((isnegative == true) && (this._type == LatLon.Type.Latitude)) {
            this._suffix = LatLon.CompasSuffix.South;
            this._decimaldegrees = Inverse(decimaldegrees);
        } //South, negative dd
        if ((isnegative == false) && (this._type == LatLon.Type.Latitude)) {

            this._suffix = LatLon.CompasSuffix.North;
            this._decimaldegrees = decimaldegrees;
        }

        if ((isnegative == true) && (this._type == LatLon.Type.Longitude)) {
            this._suffix = LatLon.CompasSuffix.West;
            this._decimaldegrees = Inverse(decimaldegrees);
        } //West, negative dd
        if ((isnegative == false) && (this._type == LatLon.Type.Longitude)) {
            this._suffix = LatLon.CompasSuffix.East;
            this._decimaldegrees = decimaldegrees;
        }// east is a negative dd value

        //set the dms values
        this._degrees = d;
        this._minutes = m;
        this._seconds = s;

        // round the decimal degrees to 6 figures
        this._decimaldegrees =  RoundHalfup(this._decimaldegrees,6);
    }

    public void Set_DMS(double degrees, double minutes, double seconds, LatLon.CompasSuffix suffix) {
        // calculate the decimal degrees from the given degrees, minutes, seconds and suffix
        // (but lets do a sanity check to see if the input is feasible first)
        if (Sanity_Suffix(suffix) == true) {
            // make sure we only input positive numbers
            degrees = Positive_Only(degrees, true);
            minutes = Positive_Only(minutes, true);
            seconds = Positive_Only(seconds, true);

            double dd;
            // convert dms to dd
            dd = degrees + (minutes / 60) + (seconds / 3600);
            dd = RoundHalfup(dd,6);
            // if we are heading west or south the decimal degree must be a negative
            if ((suffix == LatLon.CompasSuffix.West) || (suffix == LatLon.CompasSuffix.South)) {
                dd = Inverse(dd);
            }

            // well we did it.. all is sane and calculated, lets set the values.
            this._degrees = degrees;
            this._minutes = minutes;
            this._seconds = seconds;
            this._suffix = suffix;
            this._decimaldegrees = RoundHalfup(dd,6);
        }

        // okay, the input is insane (wrong/user error), lets set all values to zero and the suffix to Error.
        else {
            this._degrees = 0;
            this._minutes = 0;
            this._seconds = 0;
            this._suffix = LatLon.CompasSuffix.Error;
            this._decimaldegrees = 0;
        }

    }

    public double RoundHalfup (double input, int precision)
    {
        BigDecimal output = new BigDecimal(input).setScale(precision, RoundingMode.HALF_UP);
        return output.doubleValue();
    }
}




