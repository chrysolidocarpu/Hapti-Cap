package org.example;

public class Main {

    /*
    		<Placemark>
			<name>North West </name>
			<LookAt>
				<longitude>14.657694</longitude>
				<latitude>50.656633</latitude>
				<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>
			</LookAt>
			<Point>
				<gx:drawOrder>1</gx:drawOrder>
				<coordinates>14.657694,50.656633,0</coordinates>
			</Point>
		</Placemark>
     */



    public static void main(String[] args) {
        Coordinate NorthWest = new Coordinate();
        Coordinate SouthEast = new Coordinate();
        Coordinate Test = new Coordinate();
        Coordinate Test1 = new Coordinate();

        NorthWest.Latitude.Set_DD(50.656633);
        NorthWest.Longitude.Set_DD(14.657694);

        SouthEast.Latitude.Set_DD(50.629909);
        SouthEast.Longitude.Set_DD(14.717431);

        double bearing = NorthWest.Bearing(SouthEast);
        double distance = NorthWest.Distance(SouthEast);
        double halfdistance = distance /2;

        Test = NorthWest.Midpoint(SouthEast, "Test Descriptor", false );
        Test1 = NorthWest.Midpoint(SouthEast, "new test", true);


        String kml = "<Placemark>\n" +
                "\t\t\t<name>"+Test1.Description+"</name>\n" +
                "\t\t\t<LookAt>\n" +
                "\t\t\t\t<longitude>"+
                Test1.Longitude.DD() +
                "</longitude>\n" +
                "\t\t\t\t<latitude>"+
                Test1.Latitude.DD() +
                "</latitude>\n" +
                "\t\t\t\t<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>\n" +
                "\t\t\t</LookAt>\n" +
                "\t\t\t<Point>\n" +
                "\t\t\t\t<gx:drawOrder>1</gx:drawOrder>\n" +
                "\t\t\t\t<coordinates>"+
                Test1.Longitude.DD() + ", " + Test1.Latitude.DD() + ",0 </coordinates>\n" +
                "\t\t\t</Point>\n" +
                "\t\t</Placemark>";



        System.out.println(kml);
    }
}