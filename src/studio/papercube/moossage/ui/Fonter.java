package studio.papercube.moossage.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Fonter {
	private String[] fontList;
	public static Fonter fonter = new Fonter();
	private final String[] preferredFontList = new String []{"微软雅黑","微软雅黑 Light"};
	private String initializedFontName;
	
	private Fonter(){
		fontList=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	}
	
	public Font getPreferredFont(){
		if(getPreferredFontName()==null) return null;
		return new Font(getPreferredFontName(), Font.PLAIN, 12);
	}
	
	public String getPreferredFontName(){
		if(initializedFontName==null){
			for(String preferredFontName:preferredFontList){
				for(String fontName:fontList){
					if(preferredFontName.equals(fontName)) return preferredFontName;
				}
			}
			return null;
		}
		
		return initializedFontName;
	}
	
	public static Fonter sharedInstance(){
		return fonter;
	}
}
