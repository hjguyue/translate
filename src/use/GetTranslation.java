package use;

import java.io.*;

import entity.Translation;

import retrieve.Retriever;

public class GetTranslation {
	private static BufferedReader reader;

	public static void main(String[] args) {
		Retriever.urlEntities.removeAllElements();
		int updateNumber = 1000000;
		
		try {
			// ...
			String urlPrefix = "http://dict.youdao.com/search?le=eng&q=";
			String urlSuffix = "&keyfrom=dict.index";
			reader = new BufferedReader(new FileReader("data/dataset/translation.txt"));
			String line = "";
			int original = 0;
			while ((line = reader.readLine()) != null) {
				original++;
			}
			System.out.println("original: " + original + " done!");
			reader.close();
			
			int offset = 0;
			reader = new BufferedReader(new FileReader("data/dataset/words.txt"));
			while ((line = reader.readLine()) != null) {
				offset++;
				if (offset < original) {
					continue;
				}
				if (offset - original > updateNumber) {
					break;
				}
				Translation translation = new Translation();
				translation.identity = line;
				translation.urlString = urlPrefix + line + urlSuffix;
				Retriever.urlEntities.add(translation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Retriever.setOutput("data/dataset/translation.txt");
		Retriever.retrieve();
	}


}
