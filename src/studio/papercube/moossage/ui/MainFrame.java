package studio.papercube.moossage.ui;

import static studio.papercube.moossage.ui.console.Console.printUiDebugInfo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.IllegalFormatException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import studio.papercube.moossage.logging.exceptionhandling.MoossageException;
import studio.papercube.moossage.logging.exceptionhandling.Report;
import studio.papercube.moossage.logging.exceptionhandling.ShowErrorInfo;
import studio.papercube.moossage.looping.Looper;
import studio.papercube.moossage.LocalProperties;
import studio.papercube.moossage.looping.IteratorActionListener;
import studio.papercube.moossage.settings.SettingsProvider;
import studio.papercube.moossage.utilities.SuffixUtil;
import studio.papercube.moossage.utilities.SuffixUtil.InvalidSuffixException;

public class MainFrame extends JFrame implements ActionListener,IteratorActionListener {
	private static final long serialVersionUID = 1L;
	
	int times=0,sleepTime=0;
	int randomSleepOffset=0;
	
	String aboutThisProgram=String.format("Moossage %s(%s) by %s", LocalProperties.version, LocalProperties.build, LocalProperties.author);
	
	public JLabel labelStatusBar =  new JLabel("Tip：鼠标悬停控件上可以查看详细说明");
	
	JLabel liContent = new JLabel("输入内容");
	public JLabel lInfo = new JLabel("Ready");
	JLabel cTimes = new JLabel("循环次数");
	JLabel cSleep= new JLabel("循环间隔");
	JTextField iTimes= new JTextField(15);
	JTextField iSleep = new JTextField(10);
	JTextField iContent = new JTextField(20);
	JButton bStart = new JButton("开始");
	
	JPanel rowInfo = new JPanel();
	JPanel row1 = new JPanel();
	JPanel row2 = new JPanel();
	
	JCheckBox useCtrl = new JCheckBox("使用Ctrl+Enter");
	JCheckBox atOthers = new JCheckBox("at某人");
	JCheckBox useCurrentClipBoard = new JCheckBox("使用当前剪贴板");
	
	
	//JFrame mainwindow = new JFrame(String.format("Moossage %s (Build %s)",Properties.version,Properties.build)); //legacy
	
	Looper looper;
	
	SettingsProvider sp = SettingsProvider.getSettingsProvider();
	private final JPanel panel = new JPanel();
	private JMenuItem menuitemInsertCurrentLoopingTimes;
	private JMenuItem menuitemInsertTotalLoopingTimes;
	private JMenu menuInsertInOptions = new JMenu("插入到内容");
	
	/*
	 * This JComponent array contains all the user-interaction widgets except the button of starting and stopping
	 * When the iteration starts, all the widgets in this array will be disabled, and they will be re-enabled when
	 * iteration stops, prevent users from changing the status of the widgets
	 */
	private JMenu menuDebug;
	private JMenuItem menuItemInsertLineSeperator;
	private final JMenuItem menuItemRunGarbageCollection = new JMenuItem("进行垃圾回收");
	
	public MainFrame(){
		super(String.format("Moossage %s (Build %s)", LocalProperties.version, LocalProperties.build));
		
		System.out.println(aboutThisProgram);
		
		setFocusable(true);
		setLocationRelativeTo(null);
		setSize(500,221);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setFont(new Font("Alias", Font.PLAIN, 15));  
		
		bStart.setToolTipText("检查一遍，参数对了吗？如果是的，戳我");
		bStart.setBounds(5, 79, 93, 23);
		
		bStart.addActionListener(this);
		bStart.setActionCommand("start");
		row1.setToolTipText("<html>可以在后面附加k,m代表千和百万。<br>2k=2000, 3.111222m=311222");
		row1.setBounds(5, 38, 179, 31);
		row1.setLayout(null);
		cTimes.setBounds(0, 8, 48, 15);
		row1.add(cTimes);
		iTimes.setBounds(58, 5, 121, 21);
		row1.add(iTimes);
		row2.setToolTipText("<html><body>输入一个间隔区间，使用-分隔，每次的间隔时间将在这个区间内随机取值<br>或者直接使用一个数值<br><br>可以在后面附加s,m,h代表秒，分，时</body></html>");
		row2.setBounds(229, 38, 179, 31);
		row2.setLayout(null);
		cSleep.setBounds(0, 8, 48, 15);
		row2.add(cSleep);
		iSleep.setToolTipText("<html><body>输入一个间隔区间，使用-分隔，每次的间隔时间将在这个区间内随机取值<br>或者直接使用一个数值<br>可以在后面附加s,m,h代表秒，分，时</body></html>");
		iSleep.setBounds(58, 5, 121, 21);
		row2.add(iSleep);
		FlowLayout flowLayout = (FlowLayout) rowInfo.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		rowInfo.setToolTipText("");
		rowInfo.setBounds(5, 112, 479, 21);
		
		rowInfo.add(lInfo);
		getContentPane().setLayout(null);
		liContent.setToolTipText("<html><body>试试从菜单 \"选项-> 插入到内容\" 选项插入内容吧！</body></html>");
		liContent.setBounds(5, 13, 48, 15);
		
		getContentPane().add(liContent);
		iContent.setToolTipText("<html><body>试试从菜单 \"选项-> 插入到内容\" 选项插入内容吧！</body></html>");
		iContent.setBounds(63, 10, 421, 21);
		getContentPane().add(iContent);
		getContentPane().add(row1);
		getContentPane().add(row2);
		getContentPane().add(bStart);
		panel.setBounds(105, 75, 283, 23);
		
		getContentPane().add(panel);
		panel.setLayout(null);
		useCtrl.setBounds(0, 0, 109, 23);
		panel.add(useCtrl);
		atOthers.setToolTipText("<html><body>DOUBLE ENTER!!! <br>在内容里输入@号后面再加上你要at的那个人企鹅号的前几位<br>注意！AT的内容一定要放在文本的最后并且不可以有空格<br>这个功能会拖慢速度(20ms以上)并且有时太快或者反应慢 一部分at会不成功<br>仅限桌面版！</body></html>");
		atOthers.setBounds(111, 0, 61, 23);
		panel.add(atOthers);
		
		atOthers.addActionListener(this);
		atOthers.setActionCommand("atAction");
		useCurrentClipBoard.setToolTipText("使用当前剪贴板会直接读取剪贴板内容，事先复制好的图片甚至文件都可以刷哦~ 注意：不作死就不会死");
		useCurrentClipBoard.setBounds(174, 0, 109, 23);
		panel.add(useCurrentClipBoard);
		
		useCurrentClipBoard.addActionListener(this);
		useCurrentClipBoard.setActionCommand("clipmodeChanged");
		getContentPane().add(rowInfo);
		
		useCtrl.addActionListener(this);
		useCtrl.setActionCommand("ctrlModeChanged");
		useCtrl.setToolTipText("如果你的QQ使用Ctrl+enter发送，请勾我");
		
		labelStatusBar.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		labelStatusBar.setBounds(5, 143, 479, 15);
		getContentPane().add(labelStatusBar);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuOptions = new JMenu("选项");
		menuBar.add(menuOptions);
		
		menuOptions.add(menuInsertInOptions);
		
		menuitemInsertCurrentLoopingTimes = new JMenuItem("当前循环次数%1$s");
		menuInsertInOptions.add(menuitemInsertCurrentLoopingTimes);
		menuitemInsertCurrentLoopingTimes.addActionListener(evt->iContent.setText(iContent.getText()+"%1$s"));//Add an action listener
		
		menuitemInsertTotalLoopingTimes = new JMenuItem("总共循环次数%2$s");
		menuInsertInOptions.add(menuitemInsertTotalLoopingTimes);
		
		menuItemInsertLineSeperator = new JMenuItem("换行符%n");
		menuInsertInOptions.add(menuItemInsertLineSeperator);
		menuItemInsertLineSeperator.addActionListener(evt->iContent.setText(iContent.getText()+"%n"));
		
		menuitemInsertTotalLoopingTimes.addActionListener(evt->iContent.setText(iContent.getText()+"%2$s"));//add an action listener
		
		JMenu menuAdvanced = new JMenu("高级");
		menuOptions.add(menuAdvanced);
		
		menuDebug = new JMenu("Debug");
		menuAdvanced.add(menuDebug);
		
		JMenu menuDebugISettingsProvider = new JMenu("Settings");
		menuDebug.add(menuDebugISettingsProvider);
		
		JMenuItem mntmSaveImmediately = new JMenuItem("Save immediately");
		menuDebugISettingsProvider.add(mntmSaveImmediately);
		mntmSaveImmediately.addActionListener(evt->sp.save());
		
		JMenuItem mntmReloadSettings = new JMenuItem("Re-load settings");
		menuDebugISettingsProvider.add(mntmReloadSettings);
		
		menuDebug.add(menuItemRunGarbageCollection);
		mntmReloadSettings.addActionListener(evt->loadSettings());
		
		
		
		JMenu menuAbout = new JMenu("关于…");
		menuBar.add(menuAbout);
		
		JMenuItem menuItemAbout = new JMenuItem("关于...");
		menuItemAbout.setForeground(new Color(51, 153, 153));
		menuItemAbout.addActionListener(evt->{
			SwingUtilities.invokeLater(()->new AboutFrame(aboutThisProgram));
		});
		
		menuAbout.add(menuItemAbout);
		
		menuItemRunGarbageCollection.addActionListener(evt->System.gc());
		
		loadSettings();
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		printUiDebugInfo("Action Performed with ActionEvent %s",evt.getActionCommand());
		
		switch(evt.getActionCommand()){
		case "start":
			eventStartIteration(evt);
		break;
		case "stop":
			eventStopIteration(evt);
		break;
		case "atAction":
			eventAtModeChanged(evt);
		break;
		case "clipmodeChanged":
			eventClipmodeChanged(evt);
		break;
		case "ctrlModeChanged":
			sp.setProperty("useCtrl", String.valueOf(useCtrl.isSelected()));
		default:
			
		break;
		}
		
		sp.save();
	}

	@Override
	public void onStart() {
		setAlwaysOnTop(true);
		setFocusableWindowState(false);
		//if(getFocusOwner()==null)
	}
	
	@Override
	public void onDelay(int millisSeconds) {
		lInfo.setText(String.format("%d秒后开始,%s",millisSeconds,"定位光标到输入框"));
	}

	@Override
	public void onProcess(int now, int total, String str) {
		lInfo.setText(String.format("已完成%d次，共%d次，%.2f%%",now,total,(double)now/total*100d));
	}

	@Override
	public void onResult(String str) {
		onResult(str, 0);
	}
	@Override
	public void onResult(String str,int failureCount) {
		StringBuffer information = new StringBuffer();
		information.append(str); 
		
		if(failureCount !=0) information.append(String.format("(失败%d次)", failureCount));
		setFocusableWindowState(true);
		setAlwaysOnTop(false);
		updateUILoopingStatus(false,information.toString());
		requestFocus();
		
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
		
		if(failureCount!=0 && !Boolean.parseBoolean(sp.getProperty("neverAskAboutFailure", String.valueOf(false)))){
			BoardFailureDialog d = new BoardFailureDialog();
			d.setCount(failureCount);
			if(d.neverAskAgain()){
				sp.setProperty("neverAskAboutFailure", String.valueOf(true));
			}
		}
		
	}
	
	private void resolveSleepTime() throws IllegalStateException{
		if(iSleep.getText()==null || iSleep.getText().trim().isEmpty()){
			sleepTime=0;
			return;
		}
		
		String[] timeElements = iSleep.getText().split("-");
		switch(timeElements.length){
		case 1:
			sleepTime=SuffixUtil.toMillis(timeElements[0]);
		break;
		case 2:
			sleepTime=SuffixUtil.toMillis(timeElements[0]);
			randomSleepOffset=SuffixUtil.toMillis(timeElements[1])-sleepTime;
		break;
		default:
			throw new IllegalStateException("Invalid time"+iSleep.getText());
		}
		
	}
	
	/*
	 * Button events
	 */
	private void eventStartIteration(ActionEvent evt){
		try {
			new StatusBar().set(""); // clear status bar
			times=SuffixUtil.toTimes(iTimes.getText());
			
			try {
				resolveSleepTime();
			} catch(InvalidSuffixException e) {
				//ErrorHandler.toUser("不正确的间隔~");
				//return;
				throw e;
			}
			
			/** To prevent illegal input*/
			if(times<=0){
				ShowErrorInfo.toUser(String.format("指定的次数%s不可用",times));
				return;
			}
			
			try{
				String.format(iContent.getText(),0,times);
			}
			catch(IllegalFormatException e){
				ShowErrorInfo.toUser("不正确的格式化参数！你是从菜单里面插入的吗= =");
				return;
			}
			
			/* Warm tips */
			if(sleepTime<=100){
				new StatusBar().set("警告：过小的间隔可能导致QQ卡死等问题",Color.red);
			}
			
			looper = new Looper(iContent.getText(), times, sleepTime);
			
			looper.setAtOthersMode(atOthers.isSelected());
			looper.setUsingCurrentClipboard(useCurrentClipBoard.isSelected());
			
			looper.setActionListener(this);
			looper.setUsingControl(useCtrl.isSelected());
			looper.setRandomTimeOffset(randomSleepOffset);
			looper.go();
			
			/*
			 * Save properties of widgets
			 */
			sp.setProperty("times",iTimes.getText());
			sp.setProperty("sleeptime", iSleep.getText());
			sp.setProperty("text", iContent.getText());
			
			updateUILoopingStatus(true, null);
			
		} catch (NumberFormatException e) {
			ShowErrorInfo.toUser("你填写这样的次数是要让我作甚");
		} catch (IllegalStateException e){
			ShowErrorInfo.toUser(e,"Error","");
		} catch (Exception e){
			ShowErrorInfo.toUser(e,"计数君抽风中","未知的错误");
			Report.crashSaveOnly(new MoossageException("循环时出错",e));
			//ErrorReporter.printException(e);
		}
	}
	
	private void loadSettings(){
		useCtrl.setSelected(Boolean.parseBoolean(sp.getProperty("useCtrl","false")));
		iTimes.setText(sp.getProperty("times",""));
		iSleep.setText(sp.getProperty("sleeptime",""));
		iContent.setText(sp.getProperty("text", ""));
	}
	
	private void eventStopIteration(ActionEvent evt){
		looper.stop();
		/*
		 * Stop and recover everything
		 */
		//updateUILoopingStatus(false, null);
	}
	
	private void eventAtModeChanged(ActionEvent evt){
//		looper.setAtOthersMode(atOthers.isSelected());
	}
	
	private void eventClipmodeChanged(ActionEvent evt){
//		looper.setUsingCurrentClipboard(useCurrentClipBoard.isSelected());
		iContent.setEnabled(!useCurrentClipBoard.isSelected());
	}
	
	private void setWidgetsEnabled(boolean enable){
		JComponent[] disableNeededWidgets = new JComponent[]{iTimes,iSleep,iContent,menuInsertInOptions,useCtrl,atOthers,useCurrentClipBoard};
		/*
		 * Re-enable or disable all the widgets added into the array to
		 * allow/prevent users to(from) change(changing)
		 * the status of the widgets WHEN IT STOPS
		 */
		for(JComponent widget:disableNeededWidgets)
			widget.setEnabled(enable);
		
		if(!enable) return;
		
		/*
		 * Refresh the status of the "insert content" text field to prevent 
		 * that widget from being enabled incorrectly when use current clipboard
		 */
		eventClipmodeChanged(null); 
	}
	
	private void updateUILoopingStatus(boolean started,String result){
		if(started){
			bStart.setText("Break!");
			bStart.setActionCommand("stop");
			setWidgetsEnabled(false);
		}
		else{
			if(result!=null) lInfo.setText(result);
			bStart.setText("开始");
			bStart.setActionCommand("start");
			setWidgetsEnabled(true);
		}
	}
}
