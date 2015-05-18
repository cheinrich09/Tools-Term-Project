package main;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class MessageOutputStream extends OutputStream
{
	private MessageArea parent;
	public MessageOutputStream(MessageArea p)
	{
		parent = p;
	}
	@Override
	public void write(int b) throws IOException{
		parent.appendText(String.valueOf((char)b));
	}
}
//combined Console Output + Message Output
public class MessageArea extends JScrollPane {

	private JTextArea messageSpace;
	private Editor parent;
	public MessageOutputStream outStream;
	public MessageArea(Editor p) {
		super();
		outStream = new MessageOutputStream(this);
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
	}
	
	
	public void appendText(String text)
	{
		messageSpace.append(text);
		messageSpace.setCaretPosition(messageSpace.getDocument().getLength());
	}
	

}
