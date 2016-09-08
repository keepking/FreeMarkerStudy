import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.rmi.StubNotFoundException;
import java.util.HashMap;
import java.util.Map;

import utils.FileUtil;

import com.alibaba.fastjson.JSON;

import fmpp.ProcessingException;
import fmpp.progresslisteners.ConsoleProgressListener;
import fmpp.setting.SettingException;
import fmpp.setting.Settings;
import fmpp.util.MiscUtil;
import freemarker.cache.FileTemplateLoader;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import beans.Config;


public class Main {

	public static void main(String[] args) throws IOException, TemplateException {

		//加载自己的项目配置
		Config config = getConfig();

		//生成fmpp配置文件
		createFmppConfig(config);

        //生成项目
		createProject();

		//处理App文件
		dealApp(config);
	}

	public static Config getConfig(){
		Config config = JSON.parseObject(FileUtil.readFile(new File("autoProject/config.json")),Config.class);
		return config;
	}

	public static void createFmppConfig(Config config) throws IOException, TemplateException{
		Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateLoader(new FileTemplateLoader(new File("autoProject/templates")));

        Template template = configuration.getTemplate("config.ftl");
        File outFile = new File("autoProject","config.fmpp");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
        Map map = new HashMap<String, Object>();
        map.put("name", config.type);
        map.put("dir", config.dir);
        template.process(map, out);
        out.close();
	}

	public static void createProject(){
		File cfgFile = new File("autoProject");
		// Shut FreeMarker logger up
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_NONE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Failed to disable FreeMarker logging.");
        }
        // Now comes the meat...
        try {
            Settings ss = new Settings(new File("autoProject"));
            ss.load(cfgFile);
            ss.addProgressListener(new ConsoleProgressListener());
            ss.execute();
            System.out.println("Done.");
        } catch (SettingException e) {
            System.err.println(MiscUtil.causeMessages(e));
            System.exit(-2);
        } catch (ProcessingException e) {
            System.err.println(MiscUtil.causeMessages(e));
            System.exit(-3);
        }
	}

	public static void dealApp(Config config) throws IOException{
		File appFile = new File(config.dir,"App.java");
		File targetDirFile = new File(config.dir,"app/src/main/java/"+config.applicationPackage.replace('.', '/'));
		targetDirFile.mkdirs();
		fmpp.util.FileUtil.copyFile(appFile, new File(targetDirFile.getAbsolutePath(),"App.java"));
		appFile.delete();
	}

}
