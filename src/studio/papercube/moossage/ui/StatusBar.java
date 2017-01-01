package studio.papercube.moossage.ui;

import java.awt.Color;

import javax.swing.JLabel;

import studio.papercube.moossage.Main;

public class StatusBar {
	final JLabel f = Main.getMainFrame().labelStatusBar;
	public static final Color WARNING = Color.RED;
	public static final Color NORMAL = Color.BLACK;
	
	public StatusBar(){
		
	}
	
	@SuppressWarnings("unused")
	private StatusBar(long time)
	{
		
	}
	
	public void set(String str){
		f.setText(str);
		f.setToolTipText(str);
		f.setForeground(NORMAL);
	}
	
	public void set(String str, Color color){
		set(str);
		f.setForeground(color);
	}
}
