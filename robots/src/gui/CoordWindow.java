package gui;


import javax.swing.*;
import java.awt.*;

public class CoordWindow extends JInternalFrame
{
    private TextArea m_logContent;
    public CoordWindow()
    {
        super("Координаты робота", true, true, true, true);
        m_logContent = new TextArea("");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}