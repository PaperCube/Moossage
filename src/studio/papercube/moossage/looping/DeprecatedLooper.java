package studio.papercube.moossage.looping;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import studio.papercube.moossage.logging.exceptionhandling.CrashPrinter;
import studio.papercube.moossage.utilities.ElapsedTimeCalculator;

import static studio.papercube.moossage.nativesupport.KeyPress.*;

@Deprecated
@SuppressWarnings("all")
public class DeprecatedLooper {
	private String content;
	private int times=0,sleepTime=0,offset;
	
	private boolean useControl,atOthersmode,useCurrentClipboard;
	
	

	private int startDelaySec = 5;
	
	private List<IteratorActionListener> listeners = new ArrayList<>();
	
	public DeprecatedLooper(String str,int times,int sleepTime){
		/*
		 * Make sure that the main string has a default value
		 */
		if(str==null || str.isEmpty())
			content="第%d次，共%d次";
		else
			content=str;
		
		this.times=times;
		this.sleepTime=sleepTime;
	}
	
	public void addListener(IteratorActionListener ial){
		listeners.add(Objects.requireNonNull(ial));
	}
	
	
	public boolean isUseControl() {
		return useControl;
	}

	public void setUseControl(boolean useControl) {
		this.useControl = useControl;
	}

	public boolean isAtOthersmode() {
		return atOthersmode;
	}

	public void setAtOthersmode(boolean atOthersmode) {
		this.atOthersmode = atOthersmode;
	}

	public boolean isUseCurrentClipboard() {
		return useCurrentClipboard;
	}

	public void setUseCurrentClipboard(boolean useCurrentClipboard) {
		this.useCurrentClipboard = useCurrentClipboard;
	}

	public int getStartDelaySec() {
		return startDelaySec;
	}

	public void setStartDelaySec(int startDelaySec) {
		this.startDelaySec = startDelaySec;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	private class DaemonOperator extends Thread{
		int times,sleepTime,offset,startDelay;
		Robot r;
		boolean running=false;
		volatile int currentTime=0;
		volatile int failureCount = 0;
		private ArrayList<Exception> errorList = new ArrayList<>();
		int availableCount = 0;

		List<IteratorActionListener> actionListeners;
		
		public DaemonOperator(int times,int sleepTime,int offset) {
			this.times=times;
			this.sleepTime=sleepTime;
			this.offset=offset;
		}
		
		void cancel(){
			try{
				running=false;
				interrupt();
			}catch(Exception e){
				
			}
		}
		
		@Override
		public synchronized void start() {
			super.start();
			running=true;
		}
		
		//for(IteratorActionListener ial:actionListeners)

		@Override
		public void run() {
			try {
				r=new Robot();
				for(int i=startDelay;i>0;i--){
					if(!running)return;
					for(IteratorActionListener ial:actionListeners)
						ial.onDelay(i);
					Thread.sleep(1000);
				}
				
				ElapsedTimeCalculator timecal = new ElapsedTimeCalculator();
				
				for(;currentTime<=times;currentTime++){
					if(!running)break;
					boolean updateClipboardRequired = String.format(content,currentTime,times).equals(String.format(content,currentTime+1,times));
					if(!useCurrentClipboard && !updateClipboardRequired){
						try{
							setClipBoard(String.format(content,currentTime,times));
						}catch(BoardFailureException e){
							failureCount++;
							continue;
						}
					
					}
					
					paste(r);
					
					for(IteratorActionListener ial:actionListeners)
						ial.onProcess(currentTime, times, content);
					
					if(atOthersmode){
						Thread.sleep(75);
						if(useControl){
							keyPressWithCtrl(r,KeyEvent.VK_ENTER);
						}
						else{
							keyPress(r,KeyEvent.VK_ENTER);
						}
					}
					
					if(useControl){
						keyPressWithCtrl(r,KeyEvent.VK_ENTER);
					}
					else{
						keyPress(r,KeyEvent.VK_ENTER);
					}
					
					Thread.sleep((long) (Math.random()*offset + sleepTime));
				}
				
				
				
			} catch (Exception e) {
//				CrashPrinter.post("Unknown Error "+e.toString(), e);
				return;
			}
		}
		
		

		public void setActionlisteners(List<IteratorActionListener> actionlisteners) {
			this.actionListeners = actionlisteners;
		}
		
		public void setStartDelay(int startDelay) {
			this.startDelay = startDelay;
		}
	}
}
