package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hij doet het");


        LatLon NW= new LatLon( 50.65662777777778, 14.65810833333333);
        LatLon SE= new LatLon( 50.62995, 14.71714722222222);

        Trigonometry Trig = new Trigonometry();

       // System.out.println("lat1, lat2, lon1, lon2");
       // System.out.println(NW.Latitude_Radian());
       // System.out.println(SE.Latitude_Radian());
       // System.out.println(NW.Longitude_Radian());
       // System.out.println(SE.Longitude_Radian());


        double test = Trig.Distance(NW,SE);
       // System.out.println(test);


        test = Trig.Bearing(NW,SE);

        LatLon W = new LatLon(0,0);

        W = Trig.Waypoint(NW,500,125);
        //System.out.println(W.Latitude_Decimal());
        //System.out.println(W.Longitude_Decimal());

        //Triangle T = Trig.Tri(45,100);

        //System.out.println(T.A_Length());
        //System.out.println(T.B_Length());
        //System.out.println(T.C_Length());
        //System.out.println(T.AC_Angle());
        //System.out.println(T.BA_Angle());
        //System.out.println(T.CB_Angle());

        Map M = new Map(NW,SE,0);

    }
}