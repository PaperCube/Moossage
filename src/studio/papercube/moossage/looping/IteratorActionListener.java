package studio.papercube.moossage.looping;

public interface IteratorActionListener {
	public default void onStart(){
		
	}
	public void onDelay(int millisSeconds);
	public void onProcess(int now,int total,String str);
	public default void onResult(String str) {
	}
	
	public default void onResult(String str,int boardFailureCount){
	}
}
