package main;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		Editor myEditor = new Editor();
		SwingUtilities.invokeLater(myEditor);
	}

}
