package filesystem;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Editor;

class ProjectDialog extends JDialog implements ActionListener, PropertyChangeListener
{
	public JFileChooser fc;
	private Project parent;
	private String nError;
	private String dError;
	private String send;
	private String end;
	private JOptionPane optionPane;
	public JTextField projectName;
	private JTextField projectPDir;
	private JButton pDirButton;
	private JButton confirm;
	private JButton cancel;
	public boolean valid;
	
	public ProjectDialog(Project p)
	{
		super(p.parent, true);
		parent = p;
		nError = "*Please enter Project Name*";
		dError = "*Please select Project Location*";
		send = "send";
		end = "end";
		String pName = "Project Name:";
		projectName = new JTextField();
		String pDir = "Select Directory:";
		projectPDir = new JTextField();
		projectPDir.setEditable(false);
		pDirButton = new JButton("Select Directory to create Project in");
		pDirButton.addActionListener(this);
		confirm = new JButton("confirm");
		confirm.addActionListener(this);
		cancel = new JButton("cancel");
		cancel.addActionListener(this);
		Object[] objects = {pName, projectName, pDir, projectPDir, pDirButton};
		Object[] options = {confirm, cancel};
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		optionPane = new JOptionPane(objects, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
		this.setSize(600,200);
		setContentPane(optionPane);
		
		
		//Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Register an event handler that puts the text into the option pane.
        projectPDir.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
        
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == pDirButton)
		{
			int rVal = fc.showOpenDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
	             File file = fc.getSelectedFile();
	             projectPDir.setText(file.getAbsolutePath());
			}
			//System.out.println(projectName.getText());
		}
		else if(e.getSource() == confirm)
		{
			boolean name = false;
			boolean path = false;
			if(projectName.getText().compareTo("")==0)
			{
				projectName.setText(nError);
			}
			else if (projectName.getText().compareTo(nError)!=0)
			{
				name = true;
			}
			if(projectPDir.getText().compareTo("")==0)
			{
				projectPDir.setText(dError);
			}
			else if (projectPDir.getText().compareTo(dError)!=0)
			{
				path = true;
			}
			if(name && path)
			{
				optionPane.setValue(send);
			}
		}
		else if(e.getSource() == cancel)
		{
			optionPane.setValue(end);
			//this.setVisible(false);
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String prop = evt.getPropertyName();
		
		if(isVisible() && evt.getSource() == optionPane && 
				(JOptionPane.VALUE_PROPERTY.equals(prop)||JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
		{
			Object val = optionPane.getValue();
			
			if (val == JOptionPane.UNINITIALIZED_VALUE)
			{
				return;
			}
			
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
			
			if (send.equals(val))
			{
				valid = true;
				this.setVisible(false);
			}
			else
			{
				valid = false;
				this.setVisible(false);
			}
			
		}
	}
}
public class Project
{
	public Editor parent;
	public String name;
	public File location;
	
	public Project(Editor p)
	{
		parent = p;
	}
	public boolean create()
	{
		ProjectDialog p = new ProjectDialog(this);
		p.setVisible(true);
		if(p.valid)
		{
			name = p.projectName.getText();
			File home = p.fc.getSelectedFile();
			location = new File(home, name);	
		}
		return p.valid;
	}
	public void load(String n, File l)
	{
		name = n;
		location = l;
	}
	public boolean isLoaded()
	{
		if(location != null)
		{
			return true;
		}
		return false;
	}
}
