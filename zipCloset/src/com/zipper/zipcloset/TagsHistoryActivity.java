package com.zipper.zipcloset;

import com.kinvey.android.Client;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class TagsHistoryActivity extends KinveyActivity {
	private static final String KINVEY_KEY = "kid_PVAtuuzi2f";
	private static final String KINVEY_SECRET_KEY = "2cab4a07424945e981478fcfc02341af";
	
	@SuppressWarnings("unused")
	private ListView tagsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_history);
		Client kinveyClient = new Client.Builder(KINVEY_KEY, KINVEY_SECRET_KEY, this).build();
		ListView lv = (ListView) findViewById(R.id.tags_listview);
		getCollection(KINVEY_TAGS_KEY, KINVEY_LISTVIEW_CASE , lv,kinveyClient);
    }




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tags_history, menu);
		return true;
	}

}
