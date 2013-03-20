package com.believersresource.passages.data;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;

import com.believersresource.passages.Utils;

public class AudioFiles extends ArrayList<AudioFile> {

	private static final long serialVersionUID = 1L;
	private static MediaPlayer bufferedMP;
	private static Activity mCurrentActivity;
	private static int mCurrentIndex=0;
	public static AudioFiles Current=null;
	
	
	public static AudioFiles load(int startVerseId, int endVerseId)
	{
		AudioFiles result=new AudioFiles();
		
		String url=Config.ApiUrl + "?action=listen&startVerseId=" + String.valueOf(startVerseId) + "&endVerseId=" + String.valueOf(endVerseId);
		String contents=Utils.getUrlContents(url);
		try{
			JSONArray items = new JSONArray(contents);
			for (int i=0;i<items.length();i++)
			{
				AudioFile file=new AudioFile();
				file.MP3=items.getJSONObject(i).getString("mp3");
				result.add(file);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void loadAndPlay(Activity context, int startVerseId, int endVerseId)
	{
		AudioFiles.Current=AudioFiles.load(startVerseId, endVerseId);
		AudioFiles.Current.play(context);
	}
	
	public void play(Activity context)
	{
		bufferedMP=null;
		mCurrentIndex=0;
		mCurrentActivity=context;
		play();
	}
	
	private void play()
	{
		try {
			
			MediaPlayer mp;
			if (bufferedMP!=null){
				mp=bufferedMP;
			} else {
				String url=Current.get(mCurrentIndex).MP3;
				mp = MediaPlayer.create(mCurrentActivity, Uri.parse(url));
			}
			
			
		    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mCurrentIndex<Current.size()-1)
					{
						mCurrentIndex++;
						play();
					}

				}
			});
		    		    
		    mp.start();
			if (mCurrentIndex<Current.size()-1)
			{
				bufferedMP = MediaPlayer.create(mCurrentActivity, Uri.parse(Current.get(mCurrentIndex+1).MP3));
			}
		} catch(Exception e)  { 
		} 
	}
	
}
