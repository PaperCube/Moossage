package studio.papercube.moossage.logging.exceptionhandling;

import java.io.IOException;

/**
 * Created by imzhy on 2016/7/22.
 */
public class Report {
    /**
     * 仅仅保存错误信息，而不会向用户报告
     * @param e
     */
    public static void crashSaveOnly(Throwable... e) {
        try (CrashPrinter cp = new CrashPrinter()) {
            cp.print(new CrashReport(e).toString());
        } catch (IOException ioe) {
            ShowErrorInfo.toUser(new CrashReport(ioe).toString());
        }
    }

    /**
     * Report a crash to user
     * 会向用户展示错误信息，并保存错误相关信息。
     * @param throwables
     */
    public static void crashToUser(Throwable... throwables) {
        Report.crashSaveOnly(throwables);
        ShowErrorInfo.toUser(new CrashReport(throwables).toString());
    }
}
