package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hij doet het");


        LatLon NW= new LatLon( 50.65662777777778, 14.65810833333333);
        LatLon SE= new LatLon( 50.62995, 14.71714722222222);

        Trigonometry Trig = new Trigonometry();

        Map M = new Map(NW,SE,0);

    }
}