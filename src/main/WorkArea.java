package main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WorkArea extends JScrollPane {
	
	private JTextArea workSpace;
	private Editor parent;
	private File currentFile;
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
		System.out.println("Load Text");
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
		System.out.println("Save Text");
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
	
}
