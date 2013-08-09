package com.zipper.zipcloset;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.kinvey.android.Client;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.callback.*;
import com.kinvey.java.Query;

public class TagsHistoryActivity extends Activity {
	
	private static final String KINVEY_KEY = "kid_PVAtuuzi2f";
	private static final String KINVEY_SECRET_KEY = "2cab4a07424945e981478fcfc02341af";
	
	private Client mkinveyClient;
	@SuppressWarnings("unused")
	private ListView tagsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_history);
		tagsList = (ListView) findViewById(R.id.tags_listview);
        
		// Initialize Kinvey
		Client mkinveyClient = new Client.Builder(
				  KINVEY_KEY, 
				  KINVEY_SECRET_KEY, 
				  this.getApplicationContext()).build();
        
        refreshList("tags");
      
    }

	// Fetch tag history
    private void refreshList(String type){
    	final ProgressDialog pd = ProgressDialog.show(TagsHistoryActivity.this, 
    			"", "Loading...", true);
     	mkinveyClient.appData("entityCollection", clothingEntity.class).get(new Query(), new KinveyListCallback<clothingEntity>() {
            @Override
    		public void onSuccess(final clothingEntity[] result) {
				ArrayList<clothingEntity> arrayResults = new ArrayList<clothingEntity>(result);
				final ListView lv1 = (ListView) findViewById(R.id.tags_listview);
				lv1.setAdapter(new CustomListAdapter(TagsHistoryActivity.this, arrayResults));
				lv1.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Object o = lv1.getItemAtPosition(position);
						clothingEntity closetData = (clothingEntity) o;
						Toast.makeText(TagsHistoryActivity.this, "Selected :" + " " + closetData,
								Toast.LENGTH_LONG).show();
						Intent itemScreen = new Intent(TagsHistoryActivity.this, ZipActivity.class);
						itemScreen.putExtra("nfcId", closetData.getId());
						startActivity(itemScreen);
					}
					});
				pd.dismiss();
			}
			
			@Override
            public void onFailure(Throwable error) {
                Log.e("Tapped", "Received error response", error);
                pd.dismiss();
            }
		});
    }
	public void typeRefreshList(View v){
		String type = (String)v.getTag();
		refreshList(type);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tags_history, menu);
		return true;
	}

}
