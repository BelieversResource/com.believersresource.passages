package com.believersresource.passages;

import java.util.ArrayList;

import com.believersresource.passages.data.SearchResult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsActivity extends ActivityBase {

	
	ArrayList<String> listItems;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings);
	    
	    final SettingsActivity activity=this;

	    ListView translationList = (ListView) findViewById(R.id.translationList);
	    
	    translationList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	int translationId=1;
            	
            	switch (position)
            	{
            		case 0:
            			translationId=3;
            			break;
            		case 1:
            			translationId=2;
            			break;
            		case 2:
            			translationId=1;
            			break;
            	}
            	
            	Utils.setTranslationId(activity, translationId);
            	//SearchResult.Current = SearchResult.load(SearchResult.Current.SearchText, activity);
        		Intent intent = new Intent();
            	activity.setResult(Activity.RESULT_OK, intent);
            	activity.finish();
            }
        });
	    
	    listItems=new ArrayList<String>();
	    listItems.add("ASV - American Standard Version");
	    listItems.add("KJV - King James Version");
	    listItems.add("WEB - World English Bible");
	    
	    
	    
	    
	    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems) {
	    	private LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);;
	    	
	    	@Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	    		
	    		if (convertView == null) {
	                //convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
	    			convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
	    			//convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent);
	            }
	    		
	    		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
	            tv.setTextColor(Color.parseColor("#000000"));
	            tv.setText(listItems.get(position));
	            
	            
	            return convertView;
	        }
	    };
	    translationList.setAdapter(adapter);
	    
	    
	    
	    //translationList.li
        //searchButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {search();}});
	    
	}
}
