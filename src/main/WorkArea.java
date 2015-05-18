package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;



import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//save as dialog (in case no file to save to)
public class WorkArea extends JScrollPane {
	
	private JTextArea workSpace;
	private Editor parent;
	public File currentFile;
	public WorkArea(Editor p) {
		super();
		parent = p;
		init();
	}


	private void init()
	{
		workSpace = new JTextArea();
		workSpace.setEditable(true);
		//workSpace.setLineWrap(true);
		//workSpace.setWrapStyleWord(true);
		workSpace.setVisible(true);
		this.setViewportView(workSpace);
	}
	
	public void loadText(File f)
	{
		workSpace.setText(null);
		//System.out.println("Load Text");
		try{
			BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
			String l = "";
			while ((l = br.readLine())!=null)
			{
				workSpace.append(l);
				workSpace.append(System.getProperty("line.separator"));
			}
			currentFile=f;
			br.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
	}
	
	public void saveText()
	{
		if(currentFile != null)
		{
			System.out.println("Save Unsaved");
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
				workSpace.write(bw);
				bw.close();
			}
			catch (Exception exc)
			{
				exc.printStackTrace();
			}
		}
		else
		{
			saveTextAs();
		}
	}
	public boolean saveTextAs()
	{
		System.out.println("Save New File Dialog");
		//file chooser save dialog
		JFileChooser fc = new JFileChooser();
		int val = fc.showOpenDialog(fc);
		if (val == JFileChooser.APPROVE_OPTION)
		{
			currentFile = fc.getSelectedFile();
			saveText();
			return true;
		}
		else
		{
			return false;
		}
	}
	public void clearText()
	{
		//check if user wants to save currently unsaved work
		if(workSpace.getText()!=null)
		{
			//saveDialog
		}
		workSpace.setText(null);
		currentFile = null;
	}
}
