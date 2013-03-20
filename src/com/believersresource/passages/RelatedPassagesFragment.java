package com.believersresource.passages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.believersresource.passages.data.AudioFiles;
import com.believersresource.passages.data.Passage;
import com.believersresource.passages.data.Passages;
import com.believersresource.passages.data.SearchResult;
import com.believersresource.passages.data.Vote;

public class RelatedPassagesFragment extends Fragment implements com.believersresource.passages.tasks.OnTaskComplete  {
	private Passages mRelatedPassages;
	private ProgressDialog dialog;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.passage_list, container, false);
        
        mRelatedPassages=SearchResult.Current.getRelatedPassages();

        ExpandableListView passageList = (ExpandableListView) result.findViewById(R.id.passageList);
        
        passageList.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            	
            	String searchTerm=mRelatedPassages.get(groupPosition).DisplayName;
            	
        		Bundle bundle = new Bundle();
            	bundle.putString(SearchResult.KEY_SEARCH_TEXT, searchTerm);
        		Intent intent = new Intent();
            	intent.putExtras(bundle);
            	Activity activity=getActivity();
            	if (activity.getParent()==null)
            	{
            		activity.setResult(Activity.RESULT_OK, intent);
	            	activity.finish();
            	} else {
	            	activity.getParent().setResult(Activity.RESULT_OK, intent);
	            	activity.getParent().finish();
            	}
            	return false;
            }
        });
        
        passageList.setAdapter(new MyExpandableListAdapter(this.getActivity()));

        return result;
    }
	
	private void listen(Passage passage)
	{
		com.believersresource.passages.tasks.AsyncListen aListen=new com.believersresource.passages.tasks.AsyncListen();
		aListen.listener=this;
		aListen.execute( new String[] {String.valueOf(passage.StartVerseId), String.valueOf(passage.EndVerseId)} );
    	dialog = ProgressDialog.show( this.getActivity(), "",  "Loading audio ...", true);
	}
	
	@Override
    public void onTaskCompleted()
    {
    	dialog.hide();
    	com.believersresource.passages.data.AudioFiles.Current.play(this.getActivity());
    }
	
	
	
	
	
	

	
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		private LayoutInflater mInflater;
		private Activity mActivity;
		
        private void voteUp(View view, int position)
        {
        	Passage p=SearchResult.Current.getRelatedPassages().get(position);
        	
        	ImageButton voteUp = (ImageButton)view;
        	ImageButton voteDown = (ImageButton) ((View)view.getParent()).findViewById(R.id.voteDown);
        	voteUp.setImageResource(R.drawable.vote_up_selected);
        	voteDown.setImageResource(R.drawable.vote_down);
        	if (p.RelatedTopicId>0)
        	{
        		Vote.castVote("relatedtopic", p.RelatedTopicId, true);
        	} else {
        		Vote.castVote("relatedpassage", p.RelatedPassageId, true);
        	}
        }
        
        private void voteDown(View view, int position)
        {
        	Passage p=SearchResult.Current.getRelatedPassages().get(position);
        	
        	ImageButton voteDown = (ImageButton)view;
        	ImageButton voteUp = (ImageButton) ((View)view.getParent()).findViewById(R.id.voteUp);
        	voteDown.setImageResource(R.drawable.vote_down_selected);
        	voteUp.setImageResource(R.drawable.vote_up);
        	
        	if (p.RelatedTopicId>0)
        	{
        		Vote.castVote("relatedtopic", p.RelatedTopicId, false);
        	} else {
        		Vote.castVote("relatedpassage", p.RelatedPassageId, false);
        	}
        }

		
		public MyExpandableListAdapter(Context context) { mInflater = LayoutInflater.from(context); mActivity=(Activity)context;}
		
	    public Object getChild(int groupPosition, int childPosition) {
	    	return mRelatedPassages.get(groupPosition).Body;
	    }

	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }

	    public int getChildrenCount(int groupPosition) {
	        return 1;
	    }


	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	    	final int mGroupPosition=groupPosition;
	    	
	        View result = (View) mInflater.inflate(R.layout.passage_row, null);
	    	TextView textView = (TextView) result.findViewById(R.id.passageBodyText);
	    	textView.setText(getChild(groupPosition, childPosition).toString());
	    	
	    	ImageButton passageListenButton = (ImageButton) result.findViewById(R.id.passageListenButton);
	    	passageListenButton.setFocusable(false); //This line is necessary because focusable in XML doesn't work with ImageButtons
	    	passageListenButton.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {
	    		Passage p = mRelatedPassages.get(mGroupPosition);
	    		//AudioFiles.loadAndPlay(mActivity, p.StartVerseId, p.EndVerseId);
	    		listen(p);
	    	}});
	    	
	    	return result;
	    }

	    public Object getGroup(int groupPosition) {
	    	return mRelatedPassages.get(groupPosition).DisplayName;
	    }

	    public int getGroupCount() {
	        return mRelatedPassages.size();
	    }

	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	        View result = (View) mInflater.inflate(R.layout.passage_group, null);
	        final int mPosition=groupPosition;

	    	TextView textView = (TextView) result.findViewById(R.id.groupLabel);
	    	textView.setText(mRelatedPassages.get(groupPosition).DisplayName);
	    	
	    	ImageButton voteDown = (ImageButton) result.findViewById(R.id.voteDown);
            voteDown.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {voteDown(view, mPosition);}});
	    	voteDown.setFocusable(false); //This line is necessary because focusable in XML doesn't work with ImageButtons
	    	
	    	ImageButton voteUp = (ImageButton) result.findViewById(R.id.voteUp);
	    	voteUp.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {voteUp(view, mPosition);}});
	    	voteUp.setFocusable(false); //This line is necessary because focusable in XML doesn't work with ImageButtons
	    	
	    	return result;
	    }

	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }

	    public boolean hasStableIds() {
	        return true;
	    }

    }
	
	
}
