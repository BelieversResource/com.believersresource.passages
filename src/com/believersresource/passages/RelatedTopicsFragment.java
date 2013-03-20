package com.believersresource.passages;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.believersresource.passages.data.SearchResult;
import com.believersresource.passages.data.Topic;
import com.believersresource.passages.data.Vote;

public class RelatedTopicsFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.topic_list, container, false);
        
        ListView topicList = (ListView) result.findViewById(R.id.topicList);
        
        
        topicList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	String searchTerm=SearchResult.Current.getRelatedTopics().get(position).Name;
            	Bundle bundle = new Bundle();
            	bundle.putString(SearchResult.KEY_SEARCH_TEXT, searchTerm);
        		Intent intent = new Intent();
            	intent.putExtras(bundle);
            	Activity activity=getActivity();
            	activity.getParent().setResult(Activity.RESULT_OK, intent);
            	activity.getParent().finish();
            }
        });
        
        topicList.setAdapter(new TopicAdapter(this.getActivity()));

        return result;
    }
	
	private static class TopicAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public TopicAdapter(Context context) { mInflater = LayoutInflater.from(context); }
        public int getCount() { return SearchResult.Current.ResultPassage.RelatedTopics.size(); }

        public Object getItem(int position) { return position; }
        public long getItemId(int position) { return position; }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final int mPosition=position;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.topic_row, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else holder = (ViewHolder) convertView.getTag();

            Topic t = SearchResult.Current.ResultPassage.RelatedTopics.get(position);
            holder.name.setText(t.Name);
            
            ImageButton voteDown = (ImageButton) convertView.findViewById(R.id.voteDown);
            voteDown.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {voteDown(view, mPosition);}});
	    	voteDown.setFocusable(false); //This line is necessary because focusable in XML doesn't work with ImageButtons
	    	
	    	ImageButton voteUp = (ImageButton) convertView.findViewById(R.id.voteUp);
	    	voteUp.setOnClickListener(new View.OnClickListener() {public void onClick(View view) {voteUp(view, mPosition);}});
	    	voteUp.setFocusable(false); //This line is necessary because focusable in XML doesn't work with ImageButtons
            
            return convertView;
        }
        
        private void voteUp(View view, int position)
        {
        	int relatedTopicId = SearchResult.Current.getRelatedTopics().get(position).RelatedTopicId;
        	
        	ImageButton voteUp = (ImageButton)view;
        	ImageButton voteDown = (ImageButton) ((View)view.getParent()).findViewById(R.id.voteDown);
        	voteUp.setImageResource(R.drawable.vote_up_selected);
        	voteDown.setImageResource(R.drawable.vote_down);
        	
        	Vote.castVote("relatedtopic", relatedTopicId, true);
        }
        
        private void voteDown(View view, int position)
        {
        	int relatedTopicId = SearchResult.Current.getRelatedTopics().get(position).RelatedTopicId;
        	ImageButton voteDown = (ImageButton)view;
        	ImageButton voteUp = (ImageButton) ((View)view.getParent()).findViewById(R.id.voteUp);
        	voteDown.setImageResource(R.drawable.vote_down_selected);
        	voteUp.setImageResource(R.drawable.vote_up);
        	Vote.castVote("relatedtopic", relatedTopicId, false);
        }
        

        static class ViewHolder {
            TextView name;
        }
    }
	
	
}
