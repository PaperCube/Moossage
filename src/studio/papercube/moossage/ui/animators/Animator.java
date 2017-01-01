package studio.papercube.moossage.ui.animators;

import studio.papercube.moossage.logging.exceptionhandling.CrashPrinter;
import studio.papercube.moossage.logging.exceptionhandling.Report;

public abstract class Animator {
	private int fps,duration=0;
	
	Animator(int fps){
		this.fps=fps;
	}
	
	public void setDuration(int durate){
		duration=durate;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public void tick(AnimationTickListener atl){
		Ticker t = new Ticker(atl,fps,duration);
		t.start();
	}
	
	protected String getName(){
		return getClass().getSimpleName();
	}
	
	private class Ticker implements Runnable{
		boolean running = false;
		AnimationTickListener lis;
		int fps,duration;
		
		Ticker(AnimationTickListener atl,int FPS,int DURATION){
			lis = atl;
			fps=FPS;
			duration=DURATION;
		}
		
		void start(){
			new Thread(this,getName()).start();
		}

		@Override
		public void run() {
			for(int i=1;i<=fps*duration/1000.0;i++){
				if(running) break;
				try {
					Thread.sleep((long)(1.0/fps*1000.0));
					lis.onTick((double)i/(fps*duration/1000.0));
				} catch (InterruptedException e) {
					Report.crashToUser(e);
				}
			}
		}
	}
}
