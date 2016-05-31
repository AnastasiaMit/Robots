package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;

public class CoordWindow extends JInternalFrame
{
    private double m_robotPosX;
    private double m_robotPosY;
    private JLabel label;


    public CoordWindow()
    {
        super("Координаты робота", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        label = new JLabel("X: " + m_robotPosX + " Y: " + m_robotPosY, JLabel.LEFT);
        panel.add(label, BorderLayout.CENTER);
        getContentPane().add(panel);
        setSize(100, 50);
        pack();
    }

    public void update(LookRobot robot) {

        java.util.List<Double> model = robot.getModel();
        this.m_robotPosX = model.get(0);
        this.m_robotPosY = model.get(1);

    }

}