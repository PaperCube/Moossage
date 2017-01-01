package studio.papercube.moossage.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AboutFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public AboutFrame(String aboutThisProgram) {
		setSize(552, 470);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("关于这个程序");
		getContentPane().setLayout(null);
		
		ImageIcon logo = new ImageIcon("./res/logo.png");

		JLabel lblAbout = new JLabel("about");
		lblAbout.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbout.setBounds(30, 308, 482, 29);
		
		getContentPane().add(lblAbout);
		lblAbout.setText(aboutThisProgram);

		JLabel lblnbug = new JLabel(
				"<html><body>感谢方吧的各位吧友，以及一切支持我的人。这款应用的诞生离不开你们的帮助<br>欢迎反馈BUG，建议，以及你的新点子<br>感谢1421246841为Moossage设计的Logo</body></html>");
		lblnbug.setHorizontalAlignment(SwingConstants.CENTER);
		lblnbug.setBounds(30, 347, 472, 55);
		getContentPane().add(lblnbug);
		
		JLabel lblNewLabel = new JLabel("警告：使用这个软件带来的一切后果请自行承担！别说我没警告你");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(27, 400, 472, 34);
		getContentPane().add(lblNewLabel);
		JLabel labelLogo = new JLabel(logo);
		labelLogo.setBounds(20, 10, 512, 288);
		getContentPane().add(labelLogo);
	}
}
