package com.company;

public class Main {
    public static void main(String[] args) {
	// write your code here
        Trigonometry trig = new Trigonometry();
        System.out.println("hij doet het");

        LatLon latLonNW= new LatLon( 50.65662777777778, 14.65810833333333);
        LatLon latLonSE= new LatLon( 50.62995, 14.71714722222222);

        Map m = new Map(latLonNW,latLonSE,10.56);
        //50.627084460892895 14.653398365765753

        Map m2 = new Map(latLonNW,latLonSE,0);
        //50.62703632671463 14.662014534909735

        System.out.println("Triangle tests:");
        Triangle triangle = trig.getTriangle(34.3,25);
        triangle.printAside();
        triangle.printBside();
        triangle.printCside();
        triangle.printACangle();
        triangle.printBAangle();
        triangle.printCBangle();

        System.out.println("Map test:");
        m.printLatitudeAndLongitude();
        m2.printLatitudeAndLongitude();
    }
}