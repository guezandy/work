package com.zipper.zipcloset;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.*;

public class Kinvey {
	private static final String KINVEY_KEY = "kid_PVAtuuzi2f";
	private static final String KINVEY_SECRET_KEY = "2cab4a07424945e981478fcfc02341af";
//check this out!
	private Client mkinveyClient;
	// Fetch tag history
    private void refreshList(String type, final Context context, final ListView lv){
    	final ProgressDialog pd = ProgressDialog.show(context, 
    			"", "Loading...", true);
//once the gotocloset is updated its simple copy and paste!!   	
        mkinveyClient.mappeddata(type).fetch(clothingEntity.class, new ListCallback<clothingEntity>() {

			@Override
			public void onSuccess(final List<clothingEntity> results) {
				ArrayList<clothingEntity> arrayResults = new ArrayList<clothingEntity>(results);
				lv.setAdapter(new CustomListAdapter(context, arrayResults));
				lv.setOnItemClickListener(new OnItemClickListener() {
//Andrew!!! change the on item click listener to link to you item page!
//closetData is the entity so pass that to your item page and you should be good to go.
					
					@Override
					public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Object o = lv.getItemAtPosition(position);
						clothingEntity closetData = (clothingEntity) o;
						Toast.makeText(context, "Selected :" + " " + closetData,
								Toast.LENGTH_LONG).show();
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
	public void typeRefreshList(View v, final Context context, final ListView lv){
		String type = (String)v.getTag();
		refreshList(type, context, lv);
		
	}
}
