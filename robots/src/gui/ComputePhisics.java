package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Observable;

public class ComputePhisics {


    protected static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    protected static double angleTo(double fromX, double fromY, double toX, double toY) //ok
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected static double applyLimits(double value, double min, double max) //ok
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
    protected static double asNormalizedRadians(double angle) //ok
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }



    protected static int round(double value)
    {
        return (int)(value + 0.5);
    }





}
