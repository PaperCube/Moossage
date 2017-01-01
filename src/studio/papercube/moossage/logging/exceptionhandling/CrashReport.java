package studio.papercube.moossage.logging.exceptionhandling;

import studio.papercube.moossage.LocalProperties;
import studio.papercube.moossage.utilities.DateFormatter;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import static java.lang.System.getProperty;

/**
 * Created by imzhy on 2016/7/22.
 */
public class CrashReport implements Serializable {
    private StringWriter resultGenerator = new StringWriter();
    private PrintWriter targetWriter = new PrintWriter(resultGenerator);

    public CrashReport(Throwable... throwables) {
        printSoftwareInformation(targetWriter);
        for (Throwable throwable : throwables) {
            throwable.printStackTrace(targetWriter);
        }

        targetWriter.flush();
    }

    /**
     * 记录下软件和系统的信息。包含：
     * 崩溃时间，应用程序版本，系统名称和版本，JAVA版本和CLASS PATH
     *
     * @param printWriter
     */
    private void printSoftwareInformation(PrintWriter printWriter) {
        printWriter.println("********CRASH REPORT*********");
        printWriter.printf("********at %s **********\r\n", DateFormatter.getCurrent());

        printWriter.printf("Version %s", LocalProperties.version);
        printWriter.println();

        printWriter.println("OS name:" + getProperty("os.name"));
        printWriter.println("OS version:" + getProperty("os.version"));
        printWriter.println("Java version:" + getProperty("java.version"));
        printWriter.println("Class path:" + getProperty("java.class.path"));

    }

    @Override
    public String toString() {
        return resultGenerator.toString();
    }
}
