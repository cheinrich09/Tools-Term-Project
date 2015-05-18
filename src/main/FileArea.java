package main;

import java.util.List;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class NodeFilter implements FileFilter
{
	NodeFilter()
	{
		
	}
	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		/*String path = pathname.getAbsolutePath();
		String type = path.substring(path.lastIndexOf("."));
		if(type.compareTo(".java")==0)
		{
			return true;
		}*/
		return true;
	}
	
}
class Node extends File
{
	public Node(String dir)
	{
		super(dir);
	}
	public Node(Node parent, String child)
	{
		super(parent, child);
	}
	
	@Override
	public String toString()
	{
		return(getName().length() == 0 ? getPath() : getName());
	}
}


class FileTreeModel implements TreeModel
{
	public Node root;
	public NodeFilter filter;
	public FileTreeModel(String dir)
	{
		root = new Node(dir);
		filter = new NodeFilter();
	}
	
	@Override
	public Object getChild(Object parent, int index) {
		Node p = (Node) parent;
		List<File> children = getChildren(p);
		return new Node(p, children.get(index).getName());
	}
	public List<File> getChildren(File parent)
	{
		List<File> children = Arrays.asList(parent.listFiles(filter));
		return children;
	}
	@Override
	public int getChildCount(Object parent) {
		Node p = (Node) parent;
		if(p == null || !p.isDirectory() || p.listFiles(filter) ==null)
		{
			return 0;
		}
		return p.listFiles(filter).length;
	}
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Node p = (Node)parent;
		Node c = (Node)child;
		List<File> children = getChildren(p);
		return children.indexOf(c);
	}
	@Override
	public Object getRoot() {
		return root;
	}
	@Override
	public boolean isLeaf(Object node) {
		return(getChildCount(node)==0);
	}
	
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
	}
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
	}
}

public class FileArea extends JScrollPane {
	
	private Editor parent;
	public JTree fileTree;
	
	public FileArea(Editor p) 
	{
		super();
		parent = p;
		init();
	}
	public void init()
	{
		fileTree = new JTree();
		fileTree.setVisible(false);
		fileTree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				Node selected = (Node) fileTree.getLastSelectedPathComponent();
				if(selected.isFile())
				{
					parent.loadFile(selected);
				}
			}
		});
		this.setViewportView(fileTree);
	}
	public void load(String dir)
	{
		fileTree.setModel(new FileTreeModel(dir));
		fileTree.setVisible(true);
	}
	public void close()
	{
		fileTree = new JTree();
		fileTree.setVisible(false);
	}

}
