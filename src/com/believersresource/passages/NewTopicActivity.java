package com.believersresource.passages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.believersresource.passages.data.SearchResult;
import com.believersresource.passages.data.Topic;

public class NewTopicActivity extends ActivityBase {
	SearchResult mSearchResult;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_topic);

	    TextView displayNameText = (TextView) findViewById(R.id.displayNameText);
        displayNameText.setText(SearchResult.Current.ResultPassage.DisplayName);
        
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});
        
        
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {add();}});
	}
	
	private void add()
	{
		EditText topicText=(EditText) findViewById(R.id.topicText);
		String topic = topicText.getText().toString();
		Pattern p=Pattern.compile("^[a-zA-Z]{3,99}$");
		Matcher m = p.matcher(topic);
		if (m.matches())
		{
			add(topic);
		} else {
			AlertDialog dialog=new AlertDialog.Builder(this).create();
			dialog.setTitle("Invalid topic");
			dialog.setMessage("Only single-word topics of at least 3 characters are allowed.");
			dialog.show();
		}
	}
	
	private void add(String topic)
	{
		mSearchResult=SearchResult.load(topic);
		if (mSearchResult.ResultTopic==null)
		{
			AlertDialog dialog=new AlertDialog.Builder(this).create();
			dialog.setTitle("New Topic");
			dialog.setMessage("We are not currently tracking the topic '" + topic + "' for any passages.  Please verify your spelling.  Are you sure you wish to add it?");
			
			dialog.setButton("Yes", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) { addVerified(); }
			});
			
			dialog.setButton2("No", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
				
			dialog.show();
		} else { 
			addVerified(); 
		}
	}
	
	private void addVerified()
	{
		EditText topicText=(EditText) findViewById(R.id.topicText);
		String topic = topicText.getText().toString();
		Topic.addRelatedTopic("passage", SearchResult.Current.ResultPassage.Id, topic);
		
		//Re-execute the search to load the new results
		SearchResult.Current=SearchResult.load(SearchResult.Current.SearchText);
		
		this.setResult(RESULT_OK);
		finish();
	}
	

	
	
	private void search()
	{
		this.setResult(RESULT_OK);
		finish();
	}
	
}
