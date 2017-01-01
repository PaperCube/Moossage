package studio.papercube.moossage.ui;

import static java.lang.String.format;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FatalErrorWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel labelCrashReportPath;
	private JButton buttonExit;
	private JCheckBox cbIgnoreAll;
	private JLabel labelDescriptions;

	private FatalErrorWindow(){
		super("Fatal Error");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(490,228);
		setLocationRelativeTo(null);
		
		JLabel labelText = new JLabel("<html><body>我们已经生成了错误报告，请将错误报告发送至开发者，来帮助我们排除故障<br>你可以将它发送至imzhy@hotmail.com来反馈这个问题</body></html>");
		labelText.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		labelText.setBounds(10, 44, 463, 47);
		getContentPane().add(labelText);
		
		labelCrashReportPath = new JLabel();
		labelCrashReportPath.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		labelCrashReportPath.setText("错误报告未生成");
		labelCrashReportPath.setBounds(10, 125, 463, 23);
		getContentPane().add(labelCrashReportPath);
		
		buttonExit = new JButton("确定");
		buttonExit.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		buttonExit.setBounds(380, 158, 93, 23);
		getContentPane().add(buttonExit);
		
		JLabel label = new JLabel("很抱歉，发生了一个未知的错误");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label.setForeground(new Color(255, 69, 0));
		label.setBounds(10, 10, 463, 24);
		getContentPane().add(label);
		
		cbIgnoreAll = new JCheckBox("忽略一切问题并且不要再通知我（不建议）");
		cbIgnoreAll.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		cbIgnoreAll.setBounds(10, 154, 364, 23);
		//getContentPane().add(cbIgnoreAll);
		
		labelDescriptions = new JLabel("没有描述");
		labelDescriptions.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		labelDescriptions.setBounds(10, 101, 463, 15);
		getContentPane().add(labelDescriptions);
		
		buttonExit.addActionListener(evt->this.dispose());
		
	}
	
	public FatalErrorWindow(String path,String ...infos) {
		/*
		 * 
		 */
		this();
		
		String text=format("<html><body>错误报告位于 <br>%s</body><html>",path);
		
		StringBuffer infogenerator = new StringBuffer();
		
		for(String info:infos){
			infogenerator.append(info);
		}
		
		if(!infogenerator.toString().isEmpty()) labelDescriptions.setText(infogenerator.toString());
		
		labelCrashReportPath.setText(text);
		labelCrashReportPath.setToolTipText(text);
		
	}
}
