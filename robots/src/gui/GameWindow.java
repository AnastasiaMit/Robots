package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private VisualizerGame m_visualizer;
    public GameWindow() 
    {
        super("Новая игра", true, true, true, true);
        m_visualizer = new VisualizerGame();
        LookRobot observable = new LookRobot();
        observable.addObserver(m_visualizer);
        m_visualizer.run();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
