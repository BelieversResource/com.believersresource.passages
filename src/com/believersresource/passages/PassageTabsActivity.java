package com.believersresource.passages;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.believersresource.passages.data.SearchResult;



public class PassageTabsActivity extends TabActivity {
	
	private final static String CONTENT_TAG="content";
	private final static String PASSAGES_TAG="passages";
	private final static String TOPICS_TAG="topics";
	
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.passage_tabs);

	    Resources res = getResources();
	    TabHost tabHost = getTabHost();
	    TabHost.TabSpec spec;
	    Intent intent;

	    intent = new Intent().setClass(this, PassageActivity.class);
	    spec = tabHost.newTabSpec(CONTENT_TAG).setIndicator("Content",res.getDrawable(R.drawable.ic_tab_content)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, RelatedPassagesActivity.class);
	    spec = tabHost.newTabSpec(PASSAGES_TAG).setIndicator("Passages",res.getDrawable(R.drawable.ic_tab_passages)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, RelatedTopicsActivity.class);
	    spec = tabHost.newTabSpec(TOPICS_TAG).setIndicator("Topics",res.getDrawable(R.drawable.tab_topics)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
	    	@Override
	    	public void onTabChanged(String tabId) {
	    		TextView pageNameText = (TextView) findViewById(R.id.pageNameText);
	            
	    	    if(tabId.equals(CONTENT_TAG)) pageNameText.setText(R.string.content);
	    	    if(tabId.equals(PASSAGES_TAG)) pageNameText.setText(R.string.related_passages);
	    	    if(tabId.equals(TOPICS_TAG)) pageNameText.setText(R.string.related_topics);
	    	}
	    });
	    
	    TextView pageNameText = (TextView) findViewById(R.id.pageNameText);
	    pageNameText.setText(R.string.content);
	    tabHost.setCurrentTab(0);
	    
	    TextView displayNameText = (TextView) findViewById(R.id.displayNameText);
        displayNameText.setText(SearchResult.Current.ResultPassage.DisplayName);
        
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});
        
	}
	
	
	private void search()
	{
		this.setResult(RESULT_OK);
		finish();
	}
	
}
