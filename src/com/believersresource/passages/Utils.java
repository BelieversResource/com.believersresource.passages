package com.believersresource.passages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.SharedPreferences;

public class Utils {

	public static int translationId=1;
	
	public static String getUrlContents(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) { builder.append(line); }
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static void setTranslationId(Activity activity, int id)
	{
		SharedPreferences settings = activity.getSharedPreferences("PassagesPreferences", Activity.MODE_PRIVATE);  
		SharedPreferences.Editor prefEditor = settings.edit();  
		prefEditor.putInt("translationId", id);  
		prefEditor.commit();
		translationId=id;
	}
	
	public static int getTranslationId(Activity activity)
	{
		
		SharedPreferences settings = activity.getSharedPreferences("PassagesPreferences", Activity.MODE_PRIVATE);
		translationId=settings.getInt("translationId", 1);
		return translationId;
	}
	
}
