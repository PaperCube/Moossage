package studio.papercube.moossage.logging.exceptionhandling;

import javax.swing.*;

public final class ShowErrorInfo {
	private boolean redirectUserOutputToConsole = false;

	public static void toUser(Exception e, String title, String message) {
		e.printStackTrace();
		toUser(title, message + "\n" + e.toString());
	}

	public static void toUser(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}

	public static void toUser(String message) {
		toUser("错误", message);
	}

	public static void toUser(Exception e) {
		e.printStackTrace();
		toUser(e.toString());
	}


}
