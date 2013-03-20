package com.believersresource.passages;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.believersresource.passages.data.SearchResult;

public class MainActivity extends ActivityBase implements com.believersresource.passages.tasks.OnTaskComplete {
	
	private static final int ACTIVITY_PASSAGE=0;
	private static final int ACTIVITY_TOPIC=1;
	private String searchTerm;
	ProgressDialog dialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});
        
        Utils.getTranslationId(this); //load static variable from saved settings
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    	if (intent!=null)
    	{
	    	Bundle extras = intent.getExtras();
	    	EditText searchText=(EditText) findViewById(R.id.searchText);
	    	switch(requestCode) {
	    	case ACTIVITY_PASSAGE:
	    		searchText.setText(extras.getString(SearchResult.KEY_SEARCH_TEXT));
	    		search();    		
	    	    break;
	    	case ACTIVITY_TOPIC:
	    		searchText.setText(extras.getString(SearchResult.KEY_SEARCH_TEXT));
	    		search();    		
	    	    break;
	    	}
    	}
    }
    
    @Override
    public void onTaskCompleted()
    {
    	dialog.hide();
    	if (SearchResult.Current.ResultPassage!=null)
    	{
    		Intent i = new Intent(this, PassageTabsActivity.class);
    		startActivityForResult(i, ACTIVITY_PASSAGE);
    	} else if (SearchResult.Current.ResultTopic!=null) {
    		Intent i = new Intent(this, TopicActivity.class);
    		startActivityForResult(i, ACTIVITY_TOPIC);
    	} else {
			AlertDialog dialog=new AlertDialog.Builder(this).create();
			dialog.setTitle("Invalid passage or topic");
			dialog.setMessage("'" + searchTerm + "' is not a valid passage or topic we are currently tracking.  Please check your spelling and Internet connection and try again.");
			dialog.show();
    	}
    	
    }
    
    
    private void search()
    {
    	EditText searchText=(EditText) findViewById(R.id.searchText);
    	searchTerm = searchText.getText().toString();
    	com.believersresource.passages.tasks.AsyncSearch aSearch=new com.believersresource.passages.tasks.AsyncSearch();
    	aSearch.listener=this;
    	aSearch.execute( new String[] {searchTerm} );
    	
    	dialog = ProgressDialog.show(this, "",  "Loading '" + searchTerm + "' ...", true);
    	
    }
    
    
    
    
}