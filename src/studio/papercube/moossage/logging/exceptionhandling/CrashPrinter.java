package studio.papercube.moossage.logging.exceptionhandling;

import studio.papercube.moossage.LocalProperties;

import static java.lang.System.getProperty;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CrashPrinter extends PrintStream{
	CrashPrinter() throws IOException {
		super(logFile());
	}

	private static File logFile() throws IOException{
		File resFolder = LocalProperties.resourceFolder();
		File logFile = new File(
				resFolder.getAbsolutePath() + String.format("/Crashes/Moossage崩溃日志 - %s.txt", LocalProperties.currentTimeSpecialized() )
		);
		logFile.getParentFile().mkdirs();
		return logFile;
	}
}
