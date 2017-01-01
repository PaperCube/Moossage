package studio.papercube.moossage.nativesupport;

import studio.papercube.moossage.looping.BoardFailureException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyPress {
	private static Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
	// shift+ 按键
    public static void keyPressWithShift(Robot r, int key) {
            r.keyPress(KeyEvent.VK_SHIFT);
            r.keyPress(key);
            r.keyRelease(key);
            r.keyRelease(KeyEvent.VK_SHIFT);
            //r.delay(10);
    }

    // ctrl+ 按键
    public static void keyPressWithCtrl(Robot r, int key) {
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(key);
            r.keyRelease(key);
            r.keyRelease(KeyEvent.VK_CONTROL);
            //r.delay(10);
    }

    // alt+ 按键
    public static void keyPressWithAlt(Robot r, int key) {
            r.keyPress(KeyEvent.VK_ALT);
            r.keyPress(key);
            r.keyRelease(key);
            r.keyRelease(KeyEvent.VK_ALT);
            //r.delay(10);
    }
    //打印出字符串
    public static void keyPressString(Robot r, String str) {
            setClipBoard(str);
            paste(r);
    }
    
    //单个 按键
    public static void keyPress(Robot r,int key){
            r.keyPress(key);
            r.keyRelease(key);
           // r.delay(10);
    }
    
    public static void setClipBoard(String str) {
    	try{
            Transferable tText = new StringSelection(str);
    		clip.setContents(tText, null); //设置剪切板内容
    	}catch(IllegalStateException e){
    		e.printStackTrace();
    		throw new BoardFailureException(e);
    	}
    	
    }
    
    public static void paste(Robot r){
    	keyPressWithCtrl(r, KeyEvent.VK_V);//粘贴
        r.delay(5);
    }
    
    public static String getClipboard() throws UnsupportedFlavorException, IOException{
    	return clip.isDataFlavorAvailable(DataFlavor.stringFlavor)?(String)clip.getData(DataFlavor.stringFlavor):"Non-String Object";
    }
}
