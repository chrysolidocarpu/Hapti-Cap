package com.company;

public class Triangle
{
  /*
            schematic triangle  with numbered sides
                AC
                | \
                | \\
                | \\\
              A | \\\\  C
                | \\\\\
                | \\\\\\
                | \\\\\\\
                BA   B   CB

                the vertical side is A
                the horizontal side is B
                the diagonal is C
                the top point is inner angle AC
                the corner between vertical and horizontal AB
                the corner between horizontal and diagonal CB

                all angles are the inside angles of the triangle.
                we assume the angle between A and B to be 90 degrees.
         */

    private double length_a;
    private double length_b;
    private double length_c;
    private double angle_ac;
    private double angle_ba;
    private double angle_cb;

    public double getLengthA(){ return this.length_a;}
    public double getLengthB(){ return this.length_b;}
    public double getLengthC(){ return this.length_c;}
    public double getAC_angle(){ return this.angle_ac;}
    public double getBA_angle(){ return this.angle_ba;}
    public double getCB_angle(){ return this.angle_cb;}

    public Triangle(){
    }

    public Triangle(double ac_angle, double c_length)
    {
        setup(ac_angle,c_length);
    }
    public void Update(double ac_angle, double c_length)
    {
        setup(ac_angle,c_length);
    }

    private void setup(double ac_angle, double c_length)
    {
        this.angle_ac = ac_angle;
        this.angle_ba = 90;
        //cb_angle = abs(ac_angle - ba_angle)
        this.angle_cb = Math.abs(this.angle_ac - this.angle_ba);

        this.length_c = c_length;
        //   b_length =      cos(  radians          (cb_angle) ) * c_length
        this.length_b = Math.cos(Math.toRadians(this.angle_cb) ) * this.length_c;

        //   a_length = sqrt(          pow(c_length     ,2) -       pow(b_length     ,2) )
        this.length_a = Math.sqrt(Math.pow(this.length_c,2) - Math.pow(this.length_b, 2) );
    }

    public void printAside(){
        System.out.println("Triangle A-side: " + getLengthA());
    }

    public void printBside(){
        System.out.println("Triangle B-side: " + getLengthB());
    }

    public void printCside(){
        System.out.println("Triangle C-side: " + getLengthC());
    }

    public void printACangle(){
        System.out.println("Triangle angle AC: " + getAC_angle());
    }

    public void printBAangle(){
        System.out.println("Triangle angle BA: " + getBA_angle());
    }

    public void printCBangle(){
        System.out.println("Triangle angle CB: " + getCB_angle());
    }
}

