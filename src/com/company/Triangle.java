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


    private double length_a;
    private double length_b;
    private double length_c;
    private double angle_ac;
    private double angle_ba;
    private double angle_cb;

    public double A_Length(){ return this.length_a;}
    public double B_Length(){ return this.length_b;}
    public double C_Length(){ return this.length_c;}
    public double AC_Angle(){ return this.angle_ac;}
    public double BA_Angle(){ return this.angle_ba;}
    public double CB_Angle(){ return this.angle_cb;}

}

