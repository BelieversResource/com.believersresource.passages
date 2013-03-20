package com.believersresource.passages;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.believersresource.passages.data.Passage;
import com.believersresource.passages.data.SearchResult;
import com.believersresource.passages.data.Topic;

public class NewPassageActivity extends ActivityBase {
	private SearchResult mSearchResult; 
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.new_passage);

	    TextView displayNameText = (TextView) findViewById(R.id.displayNameText);
	    
	    if (SearchResult.Current.ResultPassage!=null)
	    {
	    	displayNameText.setText(SearchResult.Current.ResultPassage.DisplayName);
	    } else {
	    	displayNameText.setText(SearchResult.Current.ResultTopic.Name);
	    }
        
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});

        Button lookupButton = (Button) findViewById(R.id.lookupButton);
        lookupButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {lookup();}});

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {add();}});
	}
	
	private void add()
	{
		
		if (SearchResult.Current.ResultPassage!=null)
		{
			//add a RelatedPassage
			Passage.addRelatedPassage("passage", SearchResult.Current.ResultPassage.Id, mSearchResult.ResultPassage.Id);
		} else {
			//add a RelatedTopic
			Topic.addRelatedTopic("passage", mSearchResult.ResultPassage.Id, SearchResult.Current.ResultTopic.Name);
		}
		SearchResult.Current=SearchResult.load(SearchResult.Current.SearchText);
		
		this.setResult(RESULT_OK);
		finish();
	}
	
	private void lookup()
	{
		LinearLayout passageHolder= (LinearLayout) findViewById(R.id.passageHolder);
		EditText referenceText=(EditText) findViewById(R.id.referenceText);
		String reference = referenceText.getText().toString();
		mSearchResult=SearchResult.load(reference);
		
		if (mSearchResult.ResultPassage==null)
		{
			passageHolder.setVisibility(View.GONE);
			AlertDialog dialog=new AlertDialog.Builder(this).create();
			dialog.setTitle("Invalid passage");
			dialog.setMessage("'" + reference + "' is not a valid passage.");
			dialog.show();
		} else {
			TextView bodyText = (TextView) findViewById(R.id.bodyText);
	        bodyText.setText(mSearchResult.ResultPassage.Body);
	        passageHolder.setVisibility(View.VISIBLE);
		}
		
		
	}
	
	private void search()
	{
		this.setResult(RESULT_OK);
		finish();
	}
	
	
}
