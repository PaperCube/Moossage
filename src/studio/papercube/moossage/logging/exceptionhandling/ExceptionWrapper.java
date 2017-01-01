package studio.papercube.moossage.logging.exceptionhandling;

import java.text.DateFormat;
import java.util.Date;

public class ExceptionWrapper {
	public final Exception e;
	int index=-1;
	String desc="";
	String time;
	public ExceptionWrapper(Exception e,int index){
		this(e);
		this.index=index;
	}
	
	public ExceptionWrapper(Exception e,int index,String description){
		this(e,index);
		desc=description;
	}
	
	public ExceptionWrapper(Exception e,String description){
		this(e);
		desc=description;
	}
	
	public ExceptionWrapper(Exception e){
		this.e=e;
		time=DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(new Date());
	}
	
	public Exception getException(){
		return e;
	}
	
	@Override
	public String toString(){
		String content = "at Index"+index+"\r\n";
		content += time +"\r\n";
		content += e.toString() + "\r\n";
		if(e.getStackTrace().length<=0) return content;
		for(StackTraceElement ste : e.getStackTrace()){
			content+= ste.toString()+"\r\n";
		}
		return content;
	}
	
	
}
