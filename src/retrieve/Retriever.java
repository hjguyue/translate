package retrieve;

import java.io.*;
import java.util.Vector;

public class Retriever {

	public static int threadMax = 100;	
	public static int threadNum = 0;	
	public static FileWriter writer;
	
	public static final Object threadLock = new Object();
	public static Vector<UrlEntity> urlEntities = new Vector<UrlEntity>();
	
	public static void setThreadMax(int num){
		threadMax = num;
	}
	
	public static void setOutput(String fileName){
		try {
			writer = new FileWriter(new File(fileName),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void retrieve(){
		if (urlEntities.size() == 0){
			System.out.println("zero urlEntities...");
			return;
		}
		UrlEntity entity = urlEntities.remove(urlEntities.size()-1);
		RetrieveThread retriever = new RetrieveThread(entity);
		retriever.start();
		Retriever.threadNum++;
	}
	
}
