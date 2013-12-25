package use;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class WordPrepare {

	public static TreeSet<String> words = new TreeSet<String>();
	public static PrintStream printer;
	
	public static void main(String[] args) throws Exception{

		printer = new PrintStream("data/dataset/words.txt");
		String tempString;
		
		Scanner scanner = new Scanner(new File("data/dataset/fid_to_name.txt"));
		while (scanner.hasNext()) {
			tempString = scanner.next();
			String strs[] = tempString.split("/");
			for(String str:strs){
				if (!isNumeric(str) && str.length() >= 3)
					words.add(str);
			}
		}

		scanner = new Scanner(new File("data/dataset/typerelationship.txt"));
		while (scanner.hasNext()) {
			tempString = scanner.next();
			String strs[] = tempString.split("/");
			for(String str:strs){
				if (!isNumeric(str) && str.length() >= 3)
					words.add(str);
			}
		}
		
		scanner = new Scanner(new File("data/dataset/fidins.txt"));
		int offset = 0;
		while (scanner.hasNext()) {
			tempString = scanner.next();
			if (!isNumeric(tempString) && tempString.length() <= 100 && tempString.length() >= 3)
				words.add(tempString);
			
			offset++;
			if (offset % 100000 == 0) 
				System.out.println(offset);
//			if (offset > 1000000) 
//				break;
		}
		
		for(String word:words){
			word = word.replace("\"", "");
			printer.println(word);
		}
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

}
