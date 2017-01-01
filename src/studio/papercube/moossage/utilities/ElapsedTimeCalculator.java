package studio.papercube.moossage.utilities;

import static java.lang.System.*;

public class ElapsedTimeCalculator {
	long start;
	public ElapsedTimeCalculator() {
		start=currentTimeMillis();
	}
	
	public long calculate(Runnable r){
		r.run();
		return getNode();
	}
	
	public long getNode(){
		return currentTimeMillis()-start;
	}
}
