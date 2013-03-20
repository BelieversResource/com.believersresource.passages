package com.believersresource.passages.data;

import java.net.URLEncoder;

import org.json.JSONObject;

import com.believersresource.passages.Utils;

public class Topic {
	public String Name;
	public int Votes;
	public Passages RelatedPassages;
	public int RelatedTopicId;
	public int Id;
	
	public static Topic jsonDecode(JSONObject json)
	{
		Topic result=new Topic();
		try{
			result.Name = json.getString("name");
			if (json.has("id")) result.Id=json.getInt("id"); else result.Id=0;
			if (json.has("relatedTopicId")) result.RelatedTopicId=json.getInt("relatedTopicId"); else result.RelatedTopicId=0;
			if (json.has("votes")) result.Votes = json.getInt("votes"); else result.Votes=0;
			if (json.has("relatedPassages")) result.RelatedPassages=Passages.loadFromJSON(json.getJSONArray("relatedPassages"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void addRelatedTopic(String contentType, int contentId, String topic)
	{
		String url=Config.ApiUrl + "?action=addtopic&contentType=" + URLEncoder.encode(contentType) + "&contentId=" + String.valueOf(contentId) + "&topic=" + URLEncoder.encode(topic);
		Utils.getUrlContents(url);
	}
}
