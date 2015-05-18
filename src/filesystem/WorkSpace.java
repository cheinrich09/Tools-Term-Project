package filesystem;

import java.awt.Frame;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import main.Editor;

class CustomDialog extends JDialog
{
	private WorkSpace parent;
	public JTextArea selection;
	public JButton select;
	public JButton save;
	public JButton create;
	
	public CustomDialog(Frame aFrame, WorkSpace ws)
	{
		super(aFrame, true);
		parent = ws;
	}

}

public class WorkSpace {
	public File home;
	private Editor parent;
	
	public WorkSpace(Editor p)
	{
		//home = d;
		parent = p;
	}
	
	public void init()
	{
		CustomDialog wSpaceDialog = new CustomDialog(parent, this);
	}
}
