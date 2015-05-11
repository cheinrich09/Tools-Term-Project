package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class LoadAction extends AbstractAction
{
	JFileChooser fc;
	MenuArea menu;
	LoadAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if(name.compareTo("Load Directory")==0)
		{
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int rVal = fc.showOpenDialog(menu);
		 if (rVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             System.out.println("Opening: " + file.getName());
             if(fc.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY)
             {
            	 menu.loadProject(file);
             }
             else
             {
            	 menu.loadFile(file);
             }
		 }
		 else {
    		System.out.println("Open command cancelled by user.");
		 }
	}
	
}
class SaveAction extends AbstractAction
{
	MenuArea menu;
	SaveAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
	}
	public void actionPerformed(ActionEvent e){
		menu.saveFile();
	}
}
public class MenuArea extends JMenuBar
{
	private Editor parent;
	private JMenu fileMenu;
	private JMenu runMenu;
	public MenuArea(Editor p)
	{
		super();
		parent = p;
		init();
	}
	
	public void init()
	{
		fileMenu = new JMenu("File");
		this.add(fileMenu);
		fileMenu.add(new LoadAction(this, "Load File"));
		fileMenu.add(new LoadAction(this, "Load Directory"));
		fileMenu.add(new SaveAction(this, "Save File"));
		runMenu = new JMenu("Run");
		this.add(runMenu);
	}
	
	public void loadFile(File f)
	{
		parent.loadFile(f);
	}
	public void loadProject(File f)
	{
		parent.loadProject(f);
	}
	public void saveFile()
	{
		parent.saveFile();
	}
}
