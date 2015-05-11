package main;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class Editor extends JFrame implements Runnable
{	
	public int inset;
	public Dimension screenSize;
	//public JDesktopPane desktop;
	
	public FileArea fileWindow;
	public MessageArea messageWindow;
	public WorkArea workWindow;
	public MenuArea menuBar;
	public JSplitPane mainPane;
	public JSplitPane workPane;
	
	public Editor()
	{
		super("Java Editor");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		inset = 100;
		this.setBounds(inset, inset, screenSize.width-(inset*2), screenSize.height-(inset*2));
		//desktop = new JDesktopPane();
		//setContentPane(desktop);
		init();
	}
	
	public void init()
	{
		//create WorkArea
		workWindow = new WorkArea(this);
		workWindow.setVisible(true);
		messageWindow = new MessageArea(this);
		messageWindow.setVisible(true);
		fileWindow = new FileArea(this);
		fileWindow.setVisible(true);
		workPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, workWindow, messageWindow);
		workPane.setVisible(true);
		workPane.setResizeWeight(0.85);
		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileWindow, workPane);
		mainPane.setResizeWeight(0.15);
		mainPane.setVisible(true);
		menuBar = new MenuArea(this);
		this.setJMenuBar(menuBar);
	}
	protected void quit() {
		System.exit(0);
	}
	private void display()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		//Editor e = new Editor();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(800,600));
		this.getContentPane().add(mainPane);
		this.pack();
		workPane.setDividerLocation(0.8);
		mainPane.setDividerLocation(0.15);
		this.setVisible(true);
	}
	public void loadFile(File f)
	{
		System.out.println("Load File");
		workWindow.loadText(f); 
	}
	public void loadProject(File f)
	{
		System.out.println(f.getPath());
		fileWindow.load(f.getPath());
	}
	public void saveFile()
	{
		workWindow.saveText();
	}
    public void run() 
    {    
    	display();
    }
}

