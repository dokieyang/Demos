package com.dyang.demos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dyang.demos.animation.AnimationActivity;
import com.dyang.demos.hardware.ShakeDetectorActivity;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private ActionBar mActionBar;

	private DemoAdapter mAdapter;
	
	
	private ListView mListView;
	
	
	static CharSequence[] titles = DemoApplication.getContext().getResources().getStringArray(R.array.demos_item_title);
	static CharSequence[] comments = DemoApplication.getContext().getResources().getStringArray(R.array.demo_item_comment);
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActionBar = getActionBar();
		mActionBar.setHomeButtonEnabled(false);
		
		 List<HashMap<String, Object>> data = getData();
         mAdapter = new DemoAdapter(this ,data);
         mListView = getListView();
         mListView.setAdapter(mAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
        onListItemClick(position);
    }
    
    
	private void onListItemClick(int position) {
		Intent intent = null ;
		switch (position) {
		case 0:
			intent = new Intent(this, ShakeDetectorActivity.class);
			break;
		case 1:
			intent = new Intent(this, AnimationActivity.class);

			break;

		default:
			break;
		}
		if( intent != null){
			startActivity(intent);
		}
	}
    
    private List<HashMap<String, Object>> getData(){
    	
    	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
    	
    	CharSequence[] titles = DemoApplication.getContext().getResources().getStringArray(R.array.demos_item_title);
    	CharSequence[] comments = DemoApplication.getContext().getResources().getStringArray(R.array.demo_item_comment);
    	
    	HashMap<String, Object>  map;
    	for(int i = 0; i < titles.length; i++){
    		map = new HashMap<String, Object>();
    		map.put(Const.KEY_TITLE, titles[i]);
    		map.put(Const.KEY_COMMENT, comments[i]);
    		data.add(map);
    	}
    	
    	
    	return data;
    }
    
	
	
	class DemoAdapter extends BaseAdapter{

    	
    	private LayoutInflater mInflater;
    	List<HashMap<String, Object>> mData; 
    	
    	public DemoAdapter(Context ctx , List<HashMap<String, Object>> data){
    		mInflater = LayoutInflater.from(ctx);
    		mData = data;
    	}
    	
    	
		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.listview_item, null);
				
				holder = new ViewHolder();
				holder.mTitleTV = (TextView) convertView.findViewById(R.id.title);
				holder.mCommentTV = (TextView) convertView.findViewById(R.id.comments);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.mTitleTV.setText(mData.get(position).get(Const.KEY_TITLE).toString());
			holder.mCommentTV.setText(mData.get(position).get(Const.KEY_COMMENT).toString());
			
			return convertView;
		}
		
		 class ViewHolder{
			TextView  mTitleTV;
			TextView  mCommentTV;
			
		}
    	
    }
}
