package com.believersresource.passages.data;

import java.net.URLEncoder;

import com.believersresource.passages.Utils;

public class Vote {
	public String ContentType;
	public int ContentId;
	public boolean Up;
	
	public static void castVote(String contentType, int contentId, boolean up)
	{
		Vote result=new Vote();
		result.ContentType=contentType;
		result.ContentId=contentId;
		result.Up=up;
		result.castVote();
	}
	
	private void castVote()
	{
		String url=Config.ApiUrl + "?action=vote&contentType=" + URLEncoder.encode(this.ContentType) + "&contentId=" + String.valueOf(this.ContentId);
		if (this.Up) url+="&up=1"; else url+="&up=0";
		Utils.getUrlContents(url);
	}
	

}
