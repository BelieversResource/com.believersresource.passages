package com.believersresource.passages.data;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;

import com.believersresource.passages.Utils;

public class SearchResult {
	public String ResultType="";
	public Passage ResultPassage=null;
	public Topic ResultTopic=null; 
	public static final String KEY_SEARCH_TEXT = "SearchText";
	public static SearchResult Current=null;
	public String SearchText;
	
	public Passages getRelatedPassages()
	{
		if (this.ResultPassage!=null) return this.ResultPassage.RelatedPassages;
		else return this.ResultTopic.RelatedPassages;
	}
	
	public Topics getRelatedTopics()
	{
		return this.ResultPassage.RelatedTopics;
	}
	
	
	public static SearchResult load(String term)
	{
		SearchResult result=new SearchResult();
		result.SearchText=term;
		
		String url=Config.ApiUrl + "?action=search&translationId=" + String.valueOf(Utils.translationId) + "&term=" + URLEncoder.encode(term);
		String contents=Utils.getUrlContents(url);
		try{
			JSONObject root = new JSONObject(contents);
			
			if (root.has("passage"))
			{
				JSONObject passage=root.getJSONObject("passage");
				result.ResultType = "passage";
				result.ResultPassage = Passage.jsonDecode(passage);
			} else if (root.has("topic"))
			{
				JSONObject topic=root.getJSONObject("topic");
				result.ResultType = "topic";
				result.ResultTopic = Topic.jsonDecode(topic);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
