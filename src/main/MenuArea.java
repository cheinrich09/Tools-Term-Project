package main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import filesystem.Project;

//create Actions
class CreateFileAction extends AbstractAction
{
	MenuArea menu;
	JFileChooser fc;
	CreateFileAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
		fc = new JFileChooser();
		//fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
	}
	@Override
	public void actionPerformed(ActionEvent e){
		int rVal = fc.showSaveDialog(menu);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			menu.parent.createFile(file);
		}
	}
}
class CreateProjectAction extends AbstractAction
{
	MenuArea menu;
	JFileChooser fc;
	CreateProjectAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		//int rVal = fc.showSaveDialog(menu);
		//if (rVal == JFileChooser.APPROVE_OPTION) {
		//	File file = fc.getSelectedFile();
		//System.out.println(file);
		//	menu.parent.createProject(file);
		//}
		menu.parent.createProject();
	}
}
//load Actions
class LoadFileAction extends AbstractAction
{
	JFileChooser fc;
	MenuArea menu;
	LoadFileAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
		fc = new JFileChooser();
		//fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int rVal = fc.showOpenDialog(menu);
		 if (rVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             System.out.println("Opening: " + file.getName());
             //System.out.println("Java?: " + file.getName().contains(".java"));
             menu.parent.loadFile(file);
		 }
		 else {
    		System.out.println("Load cancelled");
		 }
	}	
}

class LoadProjectAction extends LoadFileAction
{
	LoadProjectAction(MenuArea m, String name)
	{
		super(m, name);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int rVal = fc.showOpenDialog(menu);
		 if (rVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             System.out.println("Opening: " + file.getName());
            
             menu.parent.loadProject(file);
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
	@Override
	public void actionPerformed(ActionEvent e){
		menu.parent.saveFile();
	}
}
//create runtime configuration
/*
class RCAction extends AbstractAction
{
}
 */
class BuildAction extends AbstractAction
{
	MenuArea menu;
	BuildAction(MenuArea m, String name)
	{
		super(name);
		menu = m;
	}
	public void actionPerformed(ActionEvent e){
		menu.parent.buildProject();
	}
}


public class MenuArea extends JMenuBar implements KeyListener
{
	public Editor parent;
	private JMenu fileMenu;
	private JMenu runMenu;
	private boolean ctrl=false;
	private boolean s = false;
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
		fileMenu.add(new CreateFileAction(this, "Create File"));
		fileMenu.add(new CreateProjectAction(this, "Create Project"));
		fileMenu.add(new LoadFileAction(this, "Load File"));
		fileMenu.add(new LoadProjectAction(this, "Load Project"));
		fileMenu.add(new SaveAction(this, "Save File"));
		runMenu = new JMenu("Run");
		this.add(runMenu);
		runMenu.add(new BuildAction(this, "Build Project"));
		parent.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("trigger");
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			ctrl = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			s = true;
		}
		if(ctrl && s)
		{
			parent.saveFile();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			ctrl = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			s = false;
		}
	}
	
}
