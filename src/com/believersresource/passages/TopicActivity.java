package com.believersresource.passages;

import com.believersresource.passages.data.SearchResult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TopicActivity extends ActivityBase {
	private static final int ADD_PASSAGE_ID = Menu.FIRST;
	private static final int ACTIVITY_NEW_PASSAGE=1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);
        TextView displayNameText = (TextView) findViewById(R.id.displayNameText);
        displayNameText.setText(SearchResult.Current.ResultTopic.Name);
        
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ADD_PASSAGE_ID, 0, R.string.add_passage);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case ADD_PASSAGE_ID:
            	addPassage();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    
    private void addPassage()
    {
    	Intent i = new Intent(this, NewPassageActivity.class);
		startActivityForResult(i, ACTIVITY_NEW_PASSAGE);
    }
	
	
	private void search()
	{
		this.setResult(RESULT_OK);
		finish();
	}
}
