package com.believersresource.passages.tasks;

import android.app.Activity;
import android.os.AsyncTask;

public class AsyncListen extends AsyncTask<String, Void, String> {
	public OnTaskComplete listener;
	
	@Override
	protected String doInBackground(String... verses) {
		int startVerseId=Integer.parseInt(verses[0]);
		int endVerseId=Integer.parseInt(verses[1]);
		com.believersresource.passages.data.AudioFiles.Current = com.believersresource.passages.data.AudioFiles.load(startVerseId, endVerseId);
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		listener.onTaskCompleted();
		
	}
}
