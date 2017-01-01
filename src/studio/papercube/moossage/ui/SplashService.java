package studio.papercube.moossage.ui;

import java.util.ArrayList;
import java.util.List;

import studio.papercube.moossage.ui.animators.WindowLocationAcceleratedAnimator;
import studio.papercube.moossage.ui.animators.WindowTransparencyAnimator;

/**
 * Splash service : this class is used to do initialization works in background
 * @since build 106
 * @author PaperCube
 */
public class SplashService implements Runnable {

	private Splash splash = new Splash();
	
	private int startDelay = 0, finishDelay = 0;
	private boolean running = false;
	private Thread serviceThread;
	private List<Progress> progresses = new ArrayList<>();
	private Runnable actionOnFinish;
	
	public SplashService(){
		this(null);
	}
	public SplashService(Runnable actionOnFinish) {
		this.actionOnFinish=actionOnFinish;
	}
	
	public void setStartDelay(int millis){
		startDelay=millis;
	}
	
	public void setFinishDelay(int millis){
		finishDelay = millis;
	}
	
	public void addProgress(String name,Runnable r){
		if(running) return;
		progresses.add(new Progress(name,r));
	}
	
	public void execute(){
		serviceThread = new Thread(this);
		serviceThread.start();
	}
	
	public void cancel(){
		this.running = false;
	}
	
	public synchronized void interrupt() throws InterruptedException{
		if(running){
			this.running = false;
			this.serviceThread.interrupt();
		}
	}
	

	@Override
	public void run() {
		try {
			final int DURATION = 250;
			splash.display();
			
			WindowTransparencyAnimator wta = new WindowTransparencyAnimator(0.0f,1.0f);
			WindowLocationAcceleratedAnimator wla = 
					new WindowLocationAcceleratedAnimator(splash.getLocationx(),splash.getLocationx(),splash.getLocationy()-50,splash.getLocationy(),false);
			wla.setDuration(DURATION);
			wla.start(splash);
			wta.setDuration(DURATION);
			wta.start(splash);
			
			Thread.sleep(DURATION);
			
			splash.setOpacity(1.0f);//To prevent when the opacity is 0.9997 etc, but it is still a little transparent.
			
			Thread.sleep(startDelay);
			for(Progress progress : progresses){
				progress.run();
				splash.setInformation(progress.getName());
			}
			splash.setInformation("");
			
			Thread.sleep(finishDelay);
			
			WindowLocationAcceleratedAnimator wlaf = 
					new WindowLocationAcceleratedAnimator(splash.getLocationx(),splash.getLocationx(),splash.getLocationy(),splash.getLocationy()+50,true);
			WindowTransparencyAnimator wtaf = new WindowTransparencyAnimator(1.0f, 0.0f);
			
			wlaf.setDuration(DURATION);
			wlaf.start(splash);
			wtaf.setDuration(DURATION);
			wtaf.start(splash);
			
			Thread.sleep(DURATION);
			splash.dispose();
			
			if(actionOnFinish!=null) actionOnFinish.run();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inner class SplashService
	 */
	private class Progress implements Runnable {
		String name;
		Runnable r;
		Progress(String name, Runnable r){
			this.name = name;
			this.r=r;
		}
		
		String getName(){
			return name;
		}
		
		@Override
		public void run() {
			if(r!=null) r.run();
		}
	}

}
