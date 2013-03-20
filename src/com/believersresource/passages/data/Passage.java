package com.believersresource.passages.data;

import java.net.URLEncoder;
import org.json.JSONObject;
import com.believersresource.passages.Utils;

public class Passage {
	public int StartVerseId;
	public int EndVerseId;
	public int RelatedTopicId;
	public int RelatedPassageId;
	public String DisplayName;
	public String Body;
	public int Votes;
	public Passages RelatedPassages;
	public Topics RelatedTopics;
	public int Id;
	public Passage Context;
	
	public static Passage jsonDecode(JSONObject json)
	{
		Passage result=new Passage();
		try{
			result.StartVerseId = json.getInt("startVerseId");
			result.EndVerseId = json.getInt("endVerseId");
			result.DisplayName = json.getString("displayName");
			result.Body = json.getString("body");
			if (json.has("id")) result.Id=json.getInt("id"); else result.Id=0;
			if (json.has("context")) result.Context=Passage.jsonDecode(json.getJSONObject("context"));
			
			if (json.has("relatedTopicId")) result.RelatedTopicId=json.getInt("relatedTopicId"); else result.RelatedTopicId=0;
			if (json.has("relatedPassageId")) result.RelatedPassageId=json.getInt("relatedPassageId"); else result.RelatedPassageId=0;
			
			if (json.has("votes")) result.Votes = json.getInt("votes"); else result.Votes=0;
			if (json.has("relatedPassages")) result.RelatedPassages=Passages.loadFromJSON(json.getJSONArray("relatedPassages"));
			if (json.has("relatedTopics")) result.RelatedTopics=Topics.loadFromJSON(json.getJSONArray("relatedTopics"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	public static void addRelatedPassage(String contentType, int contentId, int passageId)
	{
		String url=Config.ApiUrl + "?action=addpassage&contentType=" + URLEncoder.encode(contentType) + "&contentId=" + String.valueOf(contentId) + "&passageId=" + String.valueOf(passageId);
		Utils.getUrlContents(url);
	}
	
	
	
}
