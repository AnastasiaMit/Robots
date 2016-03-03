package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

/**
 * ��� ��������� �������:
 * 1. ����� �������� ���� ���������� ������������ � ������ ��������. 
 * ������� ��������� ��� �� ����� ����� ������� ������� (��� ������ �������� ��������� �����).
 *
 */
//i'm useless
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(600,  600);
        addWindow(gameWindow);
       //newGame();

        JMenuBar bar = generateMenuBar();
        setJMenuBar(bar);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
/*    protected void newGame(){
    	GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(600,  600);
        addWindow(gameWindow);
    }
        */
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("�������� ��������");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    protected void exitFromProgram(){
    	System.exit(0);
    }

    
    protected JMenu createMenuDocument() {
   
        JMenu menu = new JMenu("Document");
        menu.setMnemonic(KeyEvent.VK_D);
        {
	        JMenuItem menuItem = new JMenuItem("New");
	        menuItem.setMnemonic(KeyEvent.VK_N);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_N, ActionEvent.ALT_MASK));
	        menuItem.setActionCommand("new");
	        menuItem.addActionListener((event) -> { 
	   		//newGame();
	   		//���-�� ������� ���������� ����
	        }); 
	        menu.add(menuItem);
        }
        {
	        JMenuItem menuItem = new JMenuItem("Quit");
	        menuItem.setMnemonic(KeyEvent.VK_Q);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
	        menuItem.setActionCommand("quit");
	        menuItem.addActionListener((event) -> { 
	   		 exitFromProgram();
	        }); 
	        menu.add(menuItem);
        }

        return menu;
}

    
    private JMenu createMenuLookFeel()
    {
    	JMenu lookAndFeelMenu = new JMenu("����� �����������");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "���������� ������� ����������� ����������");
        {
            JMenuItem systemLookAndFeel = new JMenuItem("��������� �����", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("������������� �����", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
        return lookAndFeelMenu;
    }
    
    
    protected JMenu createMenuTest()
    {
    	 JMenu testMenu = new JMenu("�����");
             	testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "�������� �������");
        {
            JMenuItem addLogMessageItem = new JMenuItem("��������� � ���", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("����� ������");
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }
    
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = createMenuLookFeel();
        menuBar.add(lookAndFeelMenu);
        
        JMenu testMenu = createMenuTest();
        menuBar.add(testMenu);
        
        JMenu programMenu = createMenuDocument();
        menuBar.add(programMenu);
 
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
