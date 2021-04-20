package com.company;

public class Map {
    private final Compass compass = new Compass();
    private LatLon center;
    private final Trigonometry trigonometry = new Trigonometry();
    private final Triangle triangle = new Triangle();

    public Map(LatLon northWest, LatLon southEast, double rotation)
    {
        setCompassRotation(rotation);
        setTriangleData(northWest,southEast);
        setLatLonCenter(northWest,triangle,compass);
    }

    private void setCompassRotation(double rotation){
        compass.setRotation(rotation);
    }

    public Compass getCompass(){
        return compass;
    }

    private Trigonometry getTrigonometry(){
        return trigonometry;
    }

    private void setTriangleData(LatLon northWest, LatLon southEast){
        triangle.Update(trigonometry.getBearing(northWest,southEast), trigonometry.getDistance(northWest,southEast));
    }

    private Triangle getTriangle(){
        return triangle;
    }

    private void setLatLonCenter(LatLon northWest, Triangle triangle, Compass compass){
        center = trigonometry.getWaypointCoordinates(northWest, (triangle.getLengthC() / 2), compass.Bearing(triangle.getAC_angle()));
    }

    private LatLon getLatLonCenter(){
        return center;
    }

    public void printLatitudeAndLongitude(){
        LatLon latLon = trigonometry.getWaypointCoordinates(center, (triangle.getLengthC() /2 ), (compass.getBearingWest() -45 ));
        System.out.println(latLon.getLatitude_Decimal() + " " + latLon.getLongitude_Decimal());
    }
}
