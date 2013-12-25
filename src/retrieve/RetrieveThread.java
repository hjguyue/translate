package retrieve;

import java.io.*;
import java.net.*;

public class RetrieveThread extends Thread{

	UrlEntity urlEntity;
	
	public RetrieveThread(UrlEntity entity){
		urlEntity = entity;
	}
	
	public void run(){
		
		try {
			urlEntity.urlString = urlEntity.urlString.replaceAll("\"", "");
			URL url = new URL(urlEntity.urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-cn; rv:1.9.2.12)");
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String temp = "";
			String totalString = "";
			while((temp = reader.readLine()) != null){
				totalString += temp + "\n";
			}
			urlEntity.content = totalString;
			urlEntity.handle();
			
			synchronized (Retriever.threadLock) {
				Retriever.threadNum--;
				while(Retriever.threadNum < Retriever.threadMax && Retriever.urlEntities.size() > 0){
					UrlEntity entity = Retriever.urlEntities.remove(Retriever.urlEntities.size()-1);
					RetrieveThread retriever = new RetrieveThread(entity);
					retriever.start();
					Retriever.threadNum++;
					if(Retriever.urlEntities.size() % 100 == 0)
						System.out.println(Retriever.urlEntities.size() + "remain");
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.err.println(urlEntity.urlString);
		}
	}
	
}
