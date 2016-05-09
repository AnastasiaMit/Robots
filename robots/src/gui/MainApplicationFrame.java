package gui;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;
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


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public String iconBig;
    public boolean iconGame;
    public boolean iconLog;
    public boolean iconCoord;
    protected Rectangle positionGameWindow = new Rectangle();
    protected Rectangle positionLogWindow = new Rectangle();
    protected Rectangle positionBigWindow = new Rectangle();
    protected Rectangle positionCoordWindow = new Rectangle();

    protected LogWindow logWin;
    protected CoordWindow coordWindow;
    protected GameWindow gameWin;
    protected boolean deserial;

    //change


    public MainApplicationFrame() {

        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent arg0) {
                iconBig = frame__windowStateChanged(arg0);

            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                positionBigWindow = getBounds();
            }
        });

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
        this.logWin = logWindow;

        CoordWindow coordWindow = new CoordWindow();
        coordWindow.setSize(300, 600);
        this.coordWindow = coordWindow;
        addWindow(coordWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(600, 600);
        this.gameWin = gameWindow;
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        deserialisation();
        if (deserial) {
            setBounds(positionBigWindow);
            logWindow.setBounds(positionLogWindow);
            gameWindow.setBounds(positionGameWindow);
            if (iconBig == "ICONIFIED") {
                setState(Frame.ICONIFIED);
            }

            if (iconGame) {
                try {
                        gameWindow.setIcon(true);
                } catch (PropertyVetoException e) {}
            }

            if (iconLog) {
                try {
                    logWindow.setIcon(true);
                } catch (PropertyVetoException e) {}
            }
            if (iconCoord) {
                try {
                    coordWindow.setIcon(true);
                } catch (PropertyVetoException e) {}
            }

        }

    }
    public String frame__windowStateChanged(WindowEvent e) {
        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
            return ("ICONIFIED");
        } else if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            return ("MAXIMIZED_BOTH");
        }
        return "";
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void exitWindow() {
        Object[] options = {"Да", "Нет"};
        int sel = JOptionPane.showOptionDialog(null, "Вы уверены, что хотите выйти?",
                "Выход", JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (sel == 0) {
            serialisation();
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    protected void writePositions(){
        this.positionLogWindow = logWin.getBounds();
        this.positionGameWindow = gameWin.getBounds();
        this.positionCoordWindow = coordWindow.getBounds();
    }

    public void serialisation() {
        writePositions();
        String positions = positionBigWindow.x + " " + positionBigWindow.y + " " + positionBigWindow.width + " " + positionBigWindow.height + "\r\n" +
                positionGameWindow.x + " " + positionGameWindow.y + " " + positionGameWindow.width + " " + positionGameWindow.height + "\r\n" +
                positionLogWindow.x + " " + positionLogWindow.y + " " + positionLogWindow.width + " " + positionLogWindow.height + "\r\n" +
                positionCoordWindow.x + " " + positionCoordWindow.y + " " + positionCoordWindow.width + " " + positionCoordWindow.height;
        String serialString = iconBig + "\r\n" + gameWin.isIcon() + "\r\n" + logWin.isIcon() + "\r\n" + coordWindow.isIcon() + "\r\n" + positions;
        Serialisation ser = new Serialisation(serialString);
        ser.SaveToFile();
    }

    public void deserialisation(){
        Deserialisation deserial = new Deserialisation();
        if (deserial.findFile()){
            deserial.readfromFile();
            iconBig = deserial.iconBig;
            iconGame = deserial.iconGame;
            iconLog = deserial.iconLog;
            iconCoord = deserial.iconCoord;
            positionBigWindow = deserial.bigWindow;
            positionGameWindow = deserial.gameWindow;
            positionLogWindow = deserial.logWindow;
            positionCoordWindow = deserial.coordWindow;
            this.deserial = true;

        }
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    
    protected JMenu createMenuDocument() {
   
        JMenu menu = new JMenu("Игра");
        menu.setMnemonic(KeyEvent.VK_D);
        {
	        JMenuItem menuItem = new JMenuItem("Новая игра");
	        menuItem.setMnemonic(KeyEvent.VK_N);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                    KeyEvent.VK_N, ActionEvent.ALT_MASK));
	        menuItem.addActionListener((event) -> {
	        }); 
	        menu.add(menuItem);
        }
        {
	        JMenuItem menuItem = new JMenuItem("Выйти");
	        menuItem.setMnemonic(KeyEvent.VK_Q);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
	        menuItem.addActionListener((event) -> {
              exitWindow();
	        }); 
	        menu.add(menuItem);
        }
        return menu;
}

    private JMenu createMenuLookFeel()
    {
    	JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
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
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
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
