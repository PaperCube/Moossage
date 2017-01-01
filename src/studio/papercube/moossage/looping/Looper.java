package studio.papercube.moossage.looping;

import static studio.papercube.moossage.nativesupport.KeyPress.keyPress;
import static studio.papercube.moossage.nativesupport.KeyPress.keyPressWithCtrl;
import static studio.papercube.moossage.nativesupport.KeyPress.paste;
import static studio.papercube.moossage.nativesupport.KeyPress.setClipBoard;

import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import studio.papercube.moossage.logging.exceptionhandling.*;
import studio.papercube.moossage.utilities.ElapsedTimeCalculator;
import studio.papercube.moossage.ui.StatusBar;

public final class Looper implements Runnable {
	private String userContent;
	private String convertedMessage;
	private String lastMessage = new String();
	
	private ElapsedTimeCalculator cal;
	
	private int usedTime=0;
	private int totalTimes,sleepTime;
	private int randomTimeOffset = 0;
	private volatile int currentIndex;
	private int availableCount = 0;
	private int boardFailureCount = 0;
	private final int DELAY_SECONDS=3;
	private final int THREAD_SLEEP_INTERVAL_UNIT = 100;
	
	private Thread mainthread;
	
	private IteratorActionListener listener;
	
	private boolean isRunning = false;
	private boolean atOthersMode = false;
	private boolean useCurrentClipboard = false;
	private boolean useCtrl;
	
	private Robot r;
	
	private ArrayList<ExceptionWrapper> errorList = new ArrayList<>();
	
	private KeyBoardOperator opr = new KeyBoardOperator();
	
	//private CountDownLatch latch = new CountDownLatch(1);
	//Constructors and Initializations
	public Looper(String str,int times,int sleepTime){
		setPreferences(str,times,sleepTime);
	}
	
	public void setPreferences(String str,int times,int sleepTime){
		if(str==null || str.trim().isEmpty()){
			userContent="第%d次，共%d次";
		}
		else 
			userContent=str;
		
		
		isRunning = true;
		this.totalTimes=times;
		this.sleepTime=sleepTime;
		mainthread = new Thread(this);
		mainthread.setName("Iterator");
	}
	
	
	public void go(){
		if(totalTimes<=0){
			Report.crashToUser(new IllegalStateException("意外的接收了无效的次数"));
			return;
		}
		mainthread.start();
	}
	
	private void iterate(){
		listener.onStart();
		try {
			for(int i=DELAY_SECONDS;i>0;i--){
				listener.onDelay(i);
				Thread.sleep(1000);
				if(!isRunning) return;
			}
		} catch (InterruptedException e) {
			Report.crashToUser(new MoossageException("间隔中断", e));
		}
		
		try {
			r = new Robot();
			cal = new ElapsedTimeCalculator();
			listener.onProcess(0, totalTimes, null);
			
			opr.run();
		} catch (Exception e) {
			ShowErrorInfo.toUser(e,"WTF",new ExceptionWrapper(e, -1).toString());
			Report.crashSaveOnly(new MoossageException("意外停止，未知问题",e));
			//ErrorReporter.printException(e);
		}
		
	}
	
	@Override
	public void run() {
		if(userContent==null) throw new IllegalStateException("Iterator preferences not set correctly");
		iterate();
	}
	
	public void stop(){
		isRunning=false;
		publishResult();
		//mainthread.interrupt();
	}
	
	private void refreshBoardIfRequire(String actuality,String test){
		if(!actuality.equals(test)){
			setClipBoard(actuality);
		}
	}
	
	//Executed on both user stops it and it finishes
	private void publishResult(){
		usedTime = cal != null ? (int) cal.getNode() : 0;
		if(availableCount!=0)
			listener.onResult(String.format("循环完成，共用时%d毫秒，平均%d毫秒一次",usedTime,(int)(usedTime/availableCount)),boardFailureCount + errorList.size());
		else
			listener.onResult("未循环",0);
		
		if(!errorList.isEmpty()){
			new StatusBar().set("循环过程中发生错误，错误报告已经生成到/Moossage/CrashReport目录下",Color.RED);
			//ErrorReporter.printError(errorList);
			
			Report.crashSaveOnly((Throwable [])errorList.stream().map(ExceptionWrapper::getException).toArray());
		}
	}
	/*
	 * Functions
	 * atOthersMode:press enter twice
	 * use current clip board
	 */
	public void setAtOthersMode(boolean value){
		atOthersMode=value;
	}
	
	public void setUsingCurrentClipboard(boolean value){
		useCurrentClipboard=value;
	}
	
	public void setUsingControl(boolean flag){
		useCtrl=flag;
	}
	
	public void setUserContent(String v){
		userContent=v;
	}
	
	public void setRandomTimeOffset(int millis){
		if(millis<0) throw new IllegalStateException(String.format("The given offset %d is illegal",millis));
		randomTimeOffset=millis;
	}
	
	public void setActionListener(IteratorActionListener lis){
		listener = Objects.requireNonNull(lis);
	}
	
	private class KeyBoardOperator{
		KeyBoardOperator(){
		}
		
		public void run() throws InterruptedException{
			for(currentIndex=1;currentIndex<=totalTimes;currentIndex++){
				try {
					long thisInterval = (long) (Math.random()*randomTimeOffset + sleepTime); //calculate how long the thread will sleep in this loop period.
					
					StateCheck:
						while (true) {
							if (!isRunning) return;	//if it is not running, break all.
							for (int i = 1; i <= thisInterval / THREAD_SLEEP_INTERVAL_UNIT; i++) {
								Thread.sleep(THREAD_SLEEP_INTERVAL_UNIT);
							} //Check if it needs to break every 100 mills seconds
							Thread.sleep(thisInterval % THREAD_SLEEP_INTERVAL_UNIT);
							break StateCheck;
						}
					
					convertedMessage=String.format(userContent, currentIndex,totalTimes);
					
					if(!useCurrentClipboard){
						try{
							refreshBoardIfRequire(convertedMessage,lastMessage);
						}catch(BoardFailureException e){
							boardFailureCount++;
							continue;
						}
					}
					
					paste(r);
					
					lastMessage = convertedMessage;
					
					listener.onProcess(currentIndex,totalTimes,convertedMessage);
					
					/*
					 * If it's in atOthersMode, the key ENTER will be pressed twice.
					 */
					if(atOthersMode){
						Thread.sleep(75);
						if(useCtrl){
							keyPressWithCtrl(r,KeyEvent.VK_ENTER);
						}
						else{
							keyPress(r,KeyEvent.VK_ENTER);
						}
					}
					
					if(useCtrl){
						keyPressWithCtrl(r,KeyEvent.VK_ENTER);
					}
					else{
						keyPress(r,KeyEvent.VK_ENTER);
					}
					
					availableCount++;
					
					
					
				} catch (InterruptedException e){
					Report.crashToUser(e);
				} catch (Exception e) {
					errorList.add(new ExceptionWrapper(e,availableCount));
					e.printStackTrace();
					continue;
				}
			}
			publishResult();
		}
	}
	
}



