package main;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//combined Console Output + Message Output
public class MessageArea extends JScrollPane {

	private JTextArea messageSpace;
	private Editor parent;
	public MessageArea(Editor p) {
		super();
		parent = p;
		init();
	}
	private void init()
	{
		messageSpace = new JTextArea();
		messageSpace.setEditable(false);
		messageSpace.setLineWrap(true);
		messageSpace.setWrapStyleWord(true);
		messageSpace.setVisible(true);
		this.setViewportView(messageSpace);
		/*messageSpace = new JTextArea((width-2*offset),(height-2*offset));
		messageSpace.setEditable(false);
		messageSpace.setVisible(true);
		messageSpace.setLineWrap(true);
		messageSpace.setWrapStyleWord(true);
		this.add(messageSpace);*/
	}

}
