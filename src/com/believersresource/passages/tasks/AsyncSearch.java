package com.believersresource.passages.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.believersresource.passages.data.SearchResult;

import android.os.AsyncTask;

public class AsyncSearch extends AsyncTask<String, Void, String> {
	public OnTaskComplete listener;
	
	@Override
	protected String doInBackground(String... terms) {
		SearchResult.Current = com.believersresource.passages.data.SearchResult.load(terms[0]);
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		listener.onTaskCompleted();
	}
}
