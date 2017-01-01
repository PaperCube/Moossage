package studio.papercube.moossage.settings;

import studio.papercube.moossage.LocalProperties;
import studio.papercube.moossage.logging.exceptionhandling.MoossageException;
import studio.papercube.moossage.logging.exceptionhandling.Report;
import studio.papercube.moossage.utilities.DateFormatter;

import java.io.*;
import java.util.Properties;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

@SuppressWarnings("serial")
public class SettingsProvider extends Properties {
    static SettingsProvider sp = new SettingsProvider();

    OutputStream out;
    InputStream in;

    private SettingsProvider() {
        try {
            configFile().getParentFile().mkdirs();
            System.out.println(configFile());

            if(!configFile().exists()){
                save();//首先保存，抑制读取空文件产生的错误
            }

            in = new FileInputStream(configFile());
			
			/*
			 * there will be a exception thrown if the file is 
			 * empty. Ignore the exception and it won't appear
			 * the second time as long as any property changed.
			 */
            try {
                loadFromXML(in);
            } catch (Exception e) {
                Report.crashSaveOnly(new MoossageException("Unable to load properties from XML"));
            }
            in.close();
        } catch (Exception e) {
            Report.crashToUser(new MoossageException("Unable to create Settings Provider",e));
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            out = new FileOutputStream(configFile());
            storeToXML(out, "Last edited at " + DateFormatter.getCurrent());
            out.close();
        } catch (Exception e) {
            Report.crashToUser(e);
            e.printStackTrace();
        }
    }

    public static SettingsProvider getSettingsProvider() {
        return sp;
    }

    private File configFile() {
//        String filePath;
//        String jarPath = System.getProperty("java.class.path").split(";")[0];
//        String directoryPath = "";
//
//        File f = new File(jarPath);
//        if (f.isDirectory()) {
//            directoryPath = f.getAbsolutePath();
//        } else directoryPath = f.getParent();
//
//        filePath = String.format("%s\\Moossage_res\\MoossageConfiguation.xml", directoryPath);
//
//        return filePath;

        return new File(
                LocalProperties.resourceFolder().getAbsolutePath()+String.format("/config.xml")
        );
    }

    public boolean getBoolean(String str, String defaultv) {
        return parseBoolean(getProperty(str, defaultv));
    }

    public int getInt(String str, String defv) {
        return parseInt(getProperty(str, defv));
    }


}
