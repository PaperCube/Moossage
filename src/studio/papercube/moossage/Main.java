package studio.papercube.moossage;

import java.awt.Font;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import studio.papercube.moossage.logging.exceptionhandling.CrashPrinter;
import studio.papercube.moossage.logging.exceptionhandling.Report;
import studio.papercube.moossage.settings.SettingsProvider;
import studio.papercube.moossage.logging.exceptionhandling.ShowErrorInfo;
import studio.papercube.moossage.ui.MainFrame;
import studio.papercube.moossage.ui.SplashService;

public class Main {
	/**
	 * This class is the first class when the program starts
	 * @author PaperCube imzhy@hotmail.com
	 * @since 1.8
	 */
	static MainFrame mainframe;
	static SettingsProvider sp = SettingsProvider.getSettingsProvider();
	public static void main(String[] args){
		/*
		 * This code field contains test codes
		 * In normal situation, there should not be any codes in release version
		 */
		{
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			sp.save();
		}));
		
		try{
			SwingUtilities.invokeLater(()->{
				SplashService ss = new SplashService(()->{
					mainframe.setVisible(true);
				});
				ss.addProgress("Look and feel", StaticLoaders.ui);
				ss.addProgress("检查可用性",StaticLoaders.appInvoke);
				ss.setStartDelay(10);
				ss.setFinishDelay(10);
				ss.execute();
			});
		}catch(Exception e){
			Report.crashToUser(e);
		}
	}
	
	
	/*Sources from Internet
	 * This part is used to initialize global fonts
	 * 
	 * @param font Font that you want to use
	 */
	private static void InitGlobalFont(Font font) { 
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys();keys.hasMoreElements(); ) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	public static MainFrame getMainFrame(){
		return mainframe; 
	}
	
	/*
	 * Code collections of loadings
	 */
	static class StaticLoaders{
		public static final Runnable ui = ()->{
			/*Initialize global fonts and set the look and feel to windows default*/
			
			try{
				InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 12));  
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				ToolTipManager.sharedInstance().setDismissDelay(20000);
			}
			catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
				ShowErrorInfo.toUser(e.toString());
			}
		};
		
		public static final Runnable checkAccess = ()->{
			
		};
		
		
		public static final Runnable what = () ->{
			
		};
		
		public static final Runnable appInvoke = ()->{
			/* DO YOU ACCEPT OUR TERMS? NO, YOU DON'T? OK, DO NOT USE THIS PROGRAM! */
			
			if(Boolean.parseBoolean(sp.getProperty("acceptTerms", "false"))){
				mainframe = new studio.papercube.moossage.ui.MainFrame();
			}
			else if(JOptionPane.showConfirmDialog(null,"使用此软件意味着您自行承担因使用导致的一切责任和后果，你想要继续吗？","免责声明",
					JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
				mainframe = new studio.papercube.moossage.ui.MainFrame();
				sp.setProperty("acceptTerms", "true");
			}
			else{
				System.exit(0);
			}
			
			sp.save();
		};
	}
}




