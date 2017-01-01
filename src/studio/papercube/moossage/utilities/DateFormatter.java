package studio.papercube.moossage.utilities;

import java.util.Date;

@Deprecated
public class DateFormatter {//TODO 毙了他
	public static String getCurrent(){
		Date n = new Date();
	    
	    return String.format("%tF_%tH-%tM-%tS",n,n,n,n);
	}
}
