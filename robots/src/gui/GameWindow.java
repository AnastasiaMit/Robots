package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private VisualizerGame m_visualizer;
    protected LookRobot robot;
    protected CoordWindow coordWin;
    public GameWindow() 
    {
        super("Новая игра", true, true, true, true);
        robot = new LookRobot();
        m_visualizer = new VisualizerGame();
        coordWin = new CoordWindow();
        robot.addObserver(coordWin);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }


    protected CoordWindow getCoordWin(){
        return coordWin;
    }

}
