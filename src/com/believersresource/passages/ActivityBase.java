package com.believersresource.passages;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityBase extends FragmentActivity {
	public static final int FIRST_MENU_ITEM = Menu.FIRST + 2;
	private static final int SETTINGS_ID = Menu.FIRST;
	private static final int RATE_APP_ID = Menu.FIRST + 1;
	private static final int ACTIVITY_SETTINGS=100;
	private static final int ACTIVITY_RATE_APP=101;
	

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, SETTINGS_ID, 0, R.string.settings);
        menu.add(0, RATE_APP_ID, 0, R.string.rate_app);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId()) {
	        case SETTINGS_ID:
	        	editSettings();
	        	break;
	        case RATE_APP_ID:
	        	rateApp();
	        	break;
	    }
        return super.onMenuItemSelected(featureId, item);
    }
    
    
    private void editSettings()
    {
    	Intent i = new Intent(this, SettingsActivity.class);
		startActivityForResult(i, ACTIVITY_SETTINGS);
    }
    
    private void rateApp()
    {
    	Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.believersresource.passages"));
    	startActivityForResult(i, ACTIVITY_RATE_APP);
    }
	
}
