package entity;

import java.io.IOException;

import retrieve.*;

public class Translation extends UrlEntity {

	public void handle() {
		
		String translation = "";
		
//		1. direct
		if(translation == null || translation.length() == 0){
			translation = MatchFinder.findOneMatch(
					"<div class=\"trans-container\">[\\s\\S]*?</ul>", content);
			if(translation != null){
				translation = translation.replaceAll("<div[\\s\\S]*?<li>", "");
				translation = translation.replaceAll("</li>[\\s\\S]*?<li>", ";");
				translation = translation.replaceAll("</li>[\\s\\S]*?</ul>", "");
				if (translation.length() > 200)
					translation = null;
			}
		}
		
//		2. translation from network
		if(translation == null || translation.length() == 0){
			translation = MatchFinder.findOneMatch(
					"wt-container[\\s\\S]*?</span>", content);
			if(translation != null){
				translation = translation.replaceAll("wt[\\s\\S]*?span>", "");
				translation = translation.replaceAll("</span>", "");
			}
		}
		
//		3. translation from YouDao
		if(translation == null || translation.length() == 0){
			translation = MatchFinder.findOneMatch(
					"trans-container[\\s\\S]*?<p class", content);
			if(translation != null){
				translation = translation.replaceAll("tr[\\s\\S]*?</p>", "");
				translation = translation.replaceAll("</p>[\\s\\S]*", "");
				translation = translation.replaceAll("<p>", "");
				if (translation.contains("<div class=\"title\">短语</div>"))
					translation = null;
			}
		}
		
//		4. phrase
		if(translation == null || translation.length() == 0){
			translation = MatchFinder.findOneMatch("<div class=\"title\">短语</div>[\\s\\S]*?</p>", content);
			if(translation != null){
				translation = translation.replaceAll("<div[\\s\\S]*?</span>", "");
				translation = translation.replaceAll("</p>", "");
			}
		}
		
		if (translation == null || translation.contains("class"))
			translation = "";
		
		translation = translation.replaceAll("[\\s]", "");
		translation = translation.trim();
		
		
		try {
			Retriever.writer.append(identity + "!@#@!" + translation + "\n");
			Retriever.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
