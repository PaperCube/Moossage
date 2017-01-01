package studio.papercube.moossage.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Splash extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel labelLoadingInfo;
	private JButton buttonCancel;
	private JLabel lblPapercubeStudio;
	private JLabel labelMoossage;
	
	private Font fontLabelMoossage = new Font("微软雅黑", Font.PLAIN, 65);
	private Font fontDefault = new Font("微软雅黑", Font.PLAIN, 12);
	
	private int locationx,locationy;
	
	
	public Splash() {
		super("Moossage");
		
		getContentPane().setBackground(new Color(0, 139, 139));
		getContentPane().setLayout(null);
		
		labelMoossage = new JLabel("Moossage");
		labelMoossage.setForeground(new Color(255, 255, 255));
		labelMoossage.setBounds(11, 84, 490, 120);
		labelMoossage.setFont(fontLabelMoossage);
		labelMoossage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(labelMoossage);
		
		buttonCancel = new JButton("取消");
		buttonCancel.addActionListener(evt->System.exit(0));
		
		buttonCancel.setForeground(new Color(255, 248, 220));
		buttonCancel.setFont(fontDefault);
		buttonCancel.setBackground(new Color(255, 250, 205));
		buttonCancel.setBorderPainted(false);
		buttonCancel.setContentAreaFilled(false);
		buttonCancel.setBounds(435, 255, 67, 23);
		buttonCancel.setFocusable(false);
		getContentPane().add(buttonCancel);
		
		labelLoadingInfo = new JLabel("正在加载");
		labelLoadingInfo.setFont(fontDefault);
		labelLoadingInfo.setHorizontalAlignment(SwingConstants.LEFT);
		labelLoadingInfo.setForeground(new Color(255, 255, 255));
		labelLoadingInfo.setBounds(11, 259, 414, 15);
		getContentPane().add(labelLoadingInfo);
		
		lblPapercubeStudio = new JLabel("PaperCube Studio");
		lblPapercubeStudio.setFont(fontDefault);
		lblPapercubeStudio.setHorizontalAlignment(SwingConstants.LEFT);
		lblPapercubeStudio.setForeground(Color.WHITE);
		lblPapercubeStudio.setBounds(11, 10, 415, 15);
		getContentPane().add(lblPapercubeStudio);
		setUndecorated(true);
		setSize(512,288);
		
		/*
		 * Set location
		 */
		
		locationx= (Toolkit.getDefaultToolkit().getScreenSize().width - 512) / 2; 
        locationy = (Toolkit.getDefaultToolkit().getScreenSize().height - 288) / 2; 
        setLocation(locationx,locationy); 
        
        
	}
	
	public void display(){
		setVisible(true);
	}
	
	public void setInformation(String str){
		if(str==null || str.trim().isEmpty()){
			labelLoadingInfo.setText("正在加载");
		}
		else labelLoadingInfo.setText("正在加载 "+str);
	}
	
	public int getLocationx() {
		return locationx;
	}

	public int getLocationy() {
		return locationy;
	}

}

