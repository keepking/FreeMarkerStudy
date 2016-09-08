package utils;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;


public class Gbk2UTF8Util {

	public static void translate(File f){
		if(!f.exists())
			return;
		if(f.isDirectory())
		{
			for(File file : f.listFiles()){
				translate(file);
			}
		}
		else
			dealFile(f);

	}

	public static void dealFile(File f){
		if(!f.exists())
			return;

		try {

			File cc = new File(f.getParent(),f.getName()+".bak");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cc),"UTF-8"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"gb2312"));
			String line = null;
			while((line = reader.readLine()) != null){

				URLEncoder.encode(line, "UTF-8");
				writer.write(line+"\n");
			}
			writer.close();
			reader.close();

			f.delete();
			cc.renameTo(f.getAbsoluteFile());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
