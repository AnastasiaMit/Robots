package gui;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CoordWindow extends JInternalFrame implements Observer
{
    protected TextArea m_robotPosX;
    protected TextArea m_robotPosY;
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

    public void update(Observable observable, Object arg)
    {
        List<Double> model = ( List<Double>)arg;
        TextArea x = new TextArea(model.get(0).toString());
        TextArea y = new TextArea(model.get(1).toString());
        this.m_robotPosX = x;
        this.m_robotPosY = y;
        m_robotPosX.invalidate();
        m_robotPosY.invalidate();

    }

}