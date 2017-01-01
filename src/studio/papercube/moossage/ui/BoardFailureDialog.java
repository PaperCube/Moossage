package studio.papercube.moossage.ui;

import static java.lang.String.format;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BoardFailureDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private final Font font = new Font("微软雅黑", Font.PLAIN, 12);
	private JCheckBox cbNoAskAgain;
	private JLabel msgCount;
	private JButton btnConfirm;
	/**
	 * Create the dialog.
	 */
	public BoardFailureDialog() {
		setSize(450, 183);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(null);
		
		JLabel msgTitle = new JLabel("循环过程中出现了读取剪贴板的错误");
		msgTitle.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		msgTitle.setForeground(new Color(30, 144, 255));
		msgTitle.setBounds(10, 10, 414, 24);
		contentPanel.add(msgTitle);
		
		JLabel msgInfo = new JLabel("<html>您可以通过提高两次消息之间的间隔，或者避免使用动态内容(比如内容中的当前刷屏次数)，来避免或减少这个问题的发生。<br>我们对于问题感到抱歉");
		msgInfo.setFont(font);
		msgInfo.setBounds(10, 50, 414, 49);
		contentPanel.add(msgInfo);
		{
			btnConfirm = new JButton("确定");
			btnConfirm.setBounds(353, 109, 71, 25);
			contentPanel.add(btnConfirm);
			btnConfirm.setActionCommand("Cancel");
			btnConfirm.setFont(font);
		}
		{
			msgCount = new JLabel("发生情况没有记录");
			msgCount.setBounds(10, 114, 137, 15);
			msgCount.setFont(font);
			contentPanel.add(msgCount);
		}
		
		cbNoAskAgain = new JCheckBox("不再提示");
		cbNoAskAgain.setFont(font);
		cbNoAskAgain.setBounds(244, 110, 103, 23);
		contentPanel.add(cbNoAskAgain);
		
		setVisible(true);
		
		btnConfirm.addActionListener(evt->this.dispose());
	}
	
	public void setCount(int i){
		msgCount.setText(format("发生了%d次",i));
	}
	
	public boolean neverAskAgain(){
		return btnConfirm.isSelected();
	}
}
