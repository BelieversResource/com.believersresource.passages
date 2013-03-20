package com.believersresource.passages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.believersresource.passages.data.AudioFiles;
import com.believersresource.passages.data.Passage;
import com.believersresource.passages.data.SearchResult;

public class PassageActivity extends ActivityBase implements com.believersresource.passages.tasks.OnTaskComplete {

	private static final int ACTIVITY_RELATED_PASSAGES=0;
	private static final int ACTIVITY_RELATED_TOPICS=1;
	private ProgressDialog dialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passage);
        
        
        TextView bodyText = (TextView) findViewById(R.id.bodyText);
        ImageButton listenButton = (ImageButton) findViewById(R.id.listenButton);
        
        TextView contextReference = (TextView) findViewById(R.id.contextReference);
        TextView contextBody = (TextView) findViewById(R.id.contextBody);
        LinearLayout contextHolder = (LinearLayout) findViewById(R.id.contextHolder);
        ImageButton passageListenButton = (ImageButton) findViewById(R.id.passageListenButton);
        
        
        bodyText.setText(SearchResult.Current.ResultPassage.Body);
        
        if (SearchResult.Current.ResultPassage.Context!=null)
        {
        	contextHolder.setVisibility(View.VISIBLE);
        	contextReference.setText(SearchResult.Current.ResultPassage.Context.DisplayName);
        	contextBody.setText(SearchResult.Current.ResultPassage.Context.Body);
        	passageListenButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {listen(SearchResult.Current.ResultPassage.Context);}});
        }

        
        listenButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {listen(SearchResult.Current.ResultPassage);}});
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	super.onActivityResult(requestCode, resultCode, intent);
    	if (intent!=null)
    	{
	    	Bundle extras = intent.getExtras();
	    	
	    	switch(requestCode) {
	    	case ACTIVITY_RELATED_PASSAGES:
	    	case ACTIVITY_RELATED_TOPICS:
	    		returnSearchTerm(extras.getString(SearchResult.KEY_SEARCH_TEXT));
	    	    break;
	    	}
    	}
        
    }
	 
	private void returnSearchTerm(String searchTerm)
	{
		Bundle bundle = new Bundle();
    	bundle.putString(SearchResult.KEY_SEARCH_TEXT, searchTerm);
		Intent intent = new Intent();
    	intent.putExtras(bundle);
    	setResult(RESULT_OK, intent);
    	finish();
	}
	
	

	private void listen(Passage passage)
	{
		com.believersresource.passages.tasks.AsyncListen aListen=new com.believersresource.passages.tasks.AsyncListen();
		aListen.listener=this;
		aListen.execute( new String[] {String.valueOf(passage.StartVerseId), String.valueOf(passage.EndVerseId)} );
    	dialog = ProgressDialog.show(this, "",  "Loading audio ...", true);
	}
	
	@Override
    public void onTaskCompleted()
    {
    	dialog.hide();
    	com.believersresource.passages.data.AudioFiles.Current.play(this);
    }
	
	
	
	
}
