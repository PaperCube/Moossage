package studio.papercube.moossage.ui.console;

import studio.papercube.moossage.looping.IteratorActionListener;


public final class Console implements IteratorActionListener {

	
	@Override
	public void onDelay(int millisSeconds) {
		printInfo("%d秒后开始",(int)Math.round(millisSeconds/1000));
	}

	@Override
	public void onProcess(int now, int total, String str) {
		//printInfo(null);
	}

	@Override
	public void onResult(String str) {
		printInfo(str);
	}
	
	
	//Static methods.
	public static void printUiDebugInfo(String str,Object ...args){
		System.out.println("<UI-Debug>"+String.format(str,args));
	}
	
	public static void printInfo(String str,Object ...args){
		System.out.println("<Console>"+String.format(str,args));
	}
	
	public static <T> void printInfo(T info){
		System.out.println(info);
	}

}
