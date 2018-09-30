package studio.papercube.moossage;

import java.io.File;
import java.time.LocalDateTime;

public final class LocalProperties {
	public final static String version = "v1.2 Preview 5";
	public final static String author = "PaperCube";
	public final static int build = 137;
	public static File resourceFolder(){
		return new File("Moossage");
	}
	public static String currentTimeSpecialized(){
		LocalDateTime now = LocalDateTime.now();
		return String.format("%d-%d-%d_%d-%d-%d", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());
	}
}
