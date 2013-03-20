package com.believersresource.passages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class RelatedPassagesActivity extends ActivityBase {
	private static final int ADD_PASSAGE_ID = ActivityBase.FIRST_MENU_ITEM;
	private static final int ACTIVITY_NEW_PASSAGE=1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.related_passages);
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

}
