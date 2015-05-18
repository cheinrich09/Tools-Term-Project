package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import filesystem.Project;
import filesystem.WorkSpace;

class DiagList implements DiagnosticListener<JavaFileObject>
{
	@Override
	public void report(Diagnostic<? extends JavaFileObject> d) 
	{
		System.out.println("Line: " + d.getLineNumber());
		System.out.println("Code: " + d.getCode());
		System.out.println(d.getMessage(Locale.ENGLISH));
		System.out.println("Source: " + d.getSource());
		//System.out.println("")
	}
}
public class Editor extends JFrame implements Runnable
{	
	public int inset;
	public WorkSpace home;
	public Dimension screenSize;
	public Project proj;
	//public JDesktopPane desktop;
	//public File currentDirectory
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
		//inset = 100;
		//this.setBounds(inset, inset, screenSize.width-(inset*2), screenSize.height-(inset*2));
		init();
	}
	
	public void init()
	{
		//create WorkArea
		workWindow = new WorkArea(this);
		workWindow.setVisible(true);
		messageWindow = new MessageArea(this);
		messageWindow.setVisible(true);
		proj = new Project(this);
		PrintStream p = new PrintStream(messageWindow.outStream);
		System.setOut(p);
		System.setErr(p);
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
		this.setMinimumSize(new Dimension(1024, 768));
		this.getContentPane().add(mainPane);
		this.pack();
		workPane.setDividerLocation(0.8);
		mainPane.setDividerLocation(0.15);
		this.setVisible(true);
	}
	public void loadFile(File f)
	{
		//System.out.println("Load File");
		workWindow.loadText(f); 
	}
	public void loadProject(File f) 
	{
		//System.out.println(f.getPath());
		proj.load(f.getName(), f);
		fileWindow.load(f.getPath());
	}
	public void saveFile()
	{
		System.out.println("trigger");
		workWindow.saveText();
	}
	public void createFile()
	{
		workWindow.clearText();
	}
    public void run() 
    {    
    	display();
    }
    /*public void buildProject()
    {
    	if(workWindow.currentFile==null)
    	{
    		if(!workWindow.saveTextAs())
    		{
    			System.out.println("Must save before compile");
    			return;
    		}
    	}
    	//perhaps put save dialog for changes
    	DiagList d = new DiagList();
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    	if(compiler == null)
    	{
    		System.out.println("No Compiler");
    	}
    	else
    	{
    		System.out.println("Compiler found");
    		//StandardJavaFileManager fileManager = compiler.getStandardFileManager(d, Locale.ENGLISH, null);
    		//Iterable o = Arrays.asList("-d", out);
    		//String classes = 
    		//Iterable<? extends JavaFileObject> f = Arrays.asList(workWindow.currentFile);
    		//JavaCompiler.CompilationTask t 	= compiler.getTask(null, fileManager, d, 
    				//null, null, fileManager.getJavaFileObjects(workWindow.currentFile));
    		//boolean result = t.call();
    		int compResult = compiler.run(System.in, System.out, System.err, workWindow.currentFile.getAbsolutePath());
    		if(compResult == 0)
    		{
    			System.out.println("Compiled Successfully");
    			try
    			{
    				
    				//Class<?> mainC = Class.forName(workWindow.currentFile.getName());
    				//System.out.println(workWindow.currentFile);
    				/*System.out.println("Main class Loaded");
    				Method mainM = mainC.getDeclaredMethod("main", String[].class);
    				System.out.println("Main method found");
    				String[] params = null;
    				mainM.invoke(null, (Object)params);*/
    			/*}
    			catch( Exception e ){
    				System.out.println("Couldn't load or execute main class");
    				System.out.println( e );
    				e.printStackTrace();
    			}
    		}
    		else
    		{
    			System.out.println("failed compilation");
    		}
    		//
    		//System.out.println(compResult);
    	}
    	//
    }*/
    public void buildProject()
    {	
    	
    	//List<String> optionList = new ArrayList<String>();
    	//optionList.addAll(Arrays.asList("-classpath", System.getProperty("java.class.path"))); 
    	//for(int i = 0; i < optionList.size(); i++)
    	//{
    	//	System.out.println(optionList.get(i));
    	//}
    	if(proj.isLoaded())
    	{
    		/*if(workWindow.currentFile==null)
        	{
        		if(!workWindow.saveTextAs())
        		{
        			System.out.println("Must save before compile");
        			return;
        		}
        	}*/
    		//Process p = 
    		String main = "Main.java";
    		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    		File[] children = proj.location.listFiles();
    		File src = new File("Null");
    		//List<File> files = new List<File>();
    		for (int i = 0; i<children.length; i++)
    		{
    			if(children[i].getName().compareTo("src")==0)
    			{
    				src = children[i];
    			}
    		}
    		if(src.getName().compareTo("Null")==0)
    		{
    			System.out.println("Invalid project, no src folder");
    			return;
    		}
    		File[] packages = src.listFiles();
    		ArrayList<File> files = new ArrayList<File>();
    		for(int p = 0; p < packages.length; p++)
    		{
    			if(packages[p].getName().toLowerCase().contains(".java"))
    			{
    				files.add(packages[p]);
    			}
    			else
    			{
    				File[] temp = packages[p].listFiles();
    				for (int f = 0; f < temp.length; f++)
    				{
    					if(temp[f].getName().toLowerCase().contains(".java"))
    					{
    						files.add(temp[f]);
    					}
    				}
    			}
    		}
    		
    		for(int i = 0; i < files.size(); i++)
    		{
    			System.out.println(files.get(i).getName());
    			/*try {
					ProcessBuilder pb = Runtime.getRuntime().exec("javac "+files.get(i).getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
    			File bin = new File(proj.location, "bin");
    			bin.mkdir();
    			ProcessBuilder pb = new ProcessBuilder("javac", "-d", bin.getAbsolutePath(), files.get(i).getAbsolutePath());
    			try {
					pb.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    	}
    }
    
    
    public void createFile(File file)
    {
    	try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void createPackage()
    {
    	
    }
    public void createProject()
    {
		if(proj.create())
		{
	    	boolean create = proj.location.mkdir();
	    	if (create)
	    	{
	    		try {
	    			File _classpath = new File(proj.location, ".classpath");
	    			_classpath.createNewFile();
	    			File _project = new File(proj.location, ".project");
	    			_project.createNewFile();
	    			File src = new File(proj.location, "src");
	    			src.mkdir();
	    			File _src = new File(src, ".src");
					_src.createNewFile();
					//File bin = new File(proj.location, "bin");
	    			//bin.mkdir();
	    			//File _bin = new File(bin, ".bin");
	    			//_bin.createNewFile();
	    			//System.out.println(proj.location.isDirectory());
	    			loadProject(proj.location);
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	else
	    	{
	    		System.out.println("Project already exists");
	    	}
		}
    }
}

