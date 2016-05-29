package gui;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;



public class VisualizerGame extends JPanel  implements Observer
{
    volatile int targetPositionX;
    volatile int targetPositionY;
    volatile double  robotPositionX;
    volatile double robotPositionY;
    volatile double robotDirection;
    volatile int robotCenterX;
    volatile int robotCenterY;


    LookRobot robot = new LookRobot();

    private final Timer m_timer = initTimer();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    protected void run(){

        m_timer.schedule(new TimerTask()
    {
        @Override
        public void run() {
            onRedrawEvent();}
    }, 0, 50);


        m_timer.schedule(new TimerTask()
    {
        @Override
        public void run()
        {
            robot.updateModel(targetPositionX, targetPositionY);
        }
    }, 0, 10);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                setTargetPosition(e.getPoint());
//                robot.updateModel(targetPositionX, targetPositionY);
                repaint();
            }
        });
        setDoubleBuffered(true);
}

    protected void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
        robot.updateModel(targetPositionX, targetPositionY);
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }


    @Override
    public void update(Observable observable, Object arg)
    {
        System.out.print(1);
//        robot = (LookRobot) observable;
        List<Double> model = robot.getModel();
        this.robotPositionX = model.get(0);
        this.robotPositionY = model.get(1);
        this.robotDirection = model.get(2);
        this.robotCenterX = ComputePhisics.round(robotPositionX);
        this.robotCenterY = ComputePhisics.round(robotPositionY);
    }


    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {

        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }


    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }


    @Override
    public void paint(Graphics g)
    {
//        System.out.print(robotPositionX);
//        System.out.print(robotPositionY);
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawRobot(g2d, ComputePhisics.round(robotPositionX), ComputePhisics.round(robotPositionY), robotDirection);
        drawTarget(g2d, targetPositionX, targetPositionY);
    }

}
