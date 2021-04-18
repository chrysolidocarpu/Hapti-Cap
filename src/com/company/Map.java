package com.company;

public class Map {
    private LatLon _NorthWest = new LatLon();
    private LatLon _NorthEast = new LatLon();
    private LatLon _SouthWest = new LatLon();
    private LatLon _SouthEast = new LatLon();

    private LatLon _Center = new LatLon();

    private LatLon _NorthMid = new LatLon();
    private LatLon _SouthMid = new LatLon();
    private LatLon _WestMid = new LatLon();
    private LatLon _EastMid = new LatLon();

    private double _Rotation;
    private Compass _Compass = new Compass(0);


    public Map(LatLon northwest, LatLon southeast, double rotation)
    {
        this._NorthWest = northwest;
        this._SouthEast = southeast;
        this._Compass.Update(rotation);



        LatLon tmp = new LatLon();

        //get the angles and distances of the triangle (so we can calculate the remaining points on the map
        Trigonometry Trig = new Trigonometry();

        Triangle Tri = new Triangle(Trig.Bearing(northwest,southeast),Trig.Distance(northwest,southeast) );

        //map center
        this._Center = Trig.Waypoint(this._NorthWest, (Tri.C_Length()/2), this._Compass.Bearing(Tri.AC_Angle()));

        LatLon test = Trig.Waypoint(this._Center, (Tri.C_Length() /2 ), (this._Compass.West() -45 ));
        this._SouthWest = Trig.Waypoint(this._NorthWest, (Tri.A_Length()), this._Compass.South());

        System.out.println(test.Latitude_Decimal() + " " + test.Longitude_Decimal());




    }




}
