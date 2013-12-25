package retrieve;

import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 
 
public class MatchFinder { 
 
	public static Vector<String> findAllMatch(String sub, String str){
		Pattern pattern = Pattern.compile(sub);
		Matcher matcher = pattern.matcher(str);
		
		Vector<String> vector = new Vector<String>();
		while(matcher.find()){
			vector.add(matcher.group());
		}
		return vector;
	}

	public static String findOneMatch(String sub, String str){
		Pattern pattern = Pattern.compile(sub);
		Matcher matcher = pattern.matcher(str);
		
		if(matcher.find()){
			return (matcher.group());
		}
		
		return null;
	}
	
    public static void main(String[] args) { 
    	Scanner sc;
        while (true) { 
        	sc = new Scanner(System.in);
            Pattern pattern = Pattern.compile(sc.nextLine()); 
            Matcher matcher = pattern.matcher(sc.nextLine()); 

            while (matcher.find()) { 
//                System.out.println("I found the text " + matcher.group() +" starting at index " + matcher.start() +" " + 
//                        "and ending at index " + matcher.end()); 
            	System.out.println(matcher.group());
            } 
        } 
    } 
}