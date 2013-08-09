package com.zipper.zipcloset;

import java.util.ArrayList;
import java.util.List;


import android.util.Log;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.android.callback.KinveyListCallback;

public class GoToCloset extends Activity {
	private static final String KINVEY_KEY = "kid_PVAtuuzi2f";
	private static final String KINVEY_SECRET_KEY = "2cab4a07424945e981478fcfc02341af";

	private Client mkinveyClient;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_go_to_closet);
		
		// Initialize Kinvey
		Client mKinveyClient = new Client.Builder(
				  KINVEY_KEY, 
				  KINVEY_SECRET_KEY, 
				  this.getApplicationContext()).build();
		
		refreshList("favorites");
	}
		
// Fetch tag history
    private void refreshList(String type){
    	final ProgressDialog pd = ProgressDialog.show(GoToCloset.this, 
    			"", "Loading...", true);
    	mkinveyClient.appData("entityCollection", clothingEntity.class).get(new Query(), new KinveyListCallback<clothingEntity>() {
            @Override
    		public void onSuccess(final clothingEntity[] result) {
            	ArrayList<clothingEntity> arrayResults = new ArrayList<clothingEntity>(result);
				final ListView lv1 = (ListView) findViewById(R.id.custom_list);
				lv1.setAdapter(new CustomListAdapter(GoToCloset.this, arrayResults));
				lv1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> a, View v, int position, long id) {
						Object o = lv1.getItemAtPosition(position);
						clothingEntity closetData = (clothingEntity) o;
						Toast.makeText(GoToCloset.this, "Selected :" + " " + closetData,
								Toast.LENGTH_LONG).show();
						Intent itemScreen = new Intent(GoToCloset.this, ZipActivity.class);
						itemScreen.putExtra("nfcId", closetData.getId());
						startActivity(itemScreen);
					}
					});
				pd.dismiss();
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("GoToCloset", "AppData.get all Failure", error);
                Toast.makeText(GoToCloset.this, "Get All error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
	public void typeRefreshList(View v){
		String type = (String)v.getTag();
		refreshList(type);
		
	}
}


	/*
	private ArrayList<clothingEntity> getListData() {
		
		ArrayList<clothingEntity> results = new ArrayList<clothingEntity>();
		
		clothingEntity newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh5.ggpht.com/_hepKlJWopDg/TB-_WXikaYI/AAAAAAAAElI/715k4NvBM4w/s144-c/IMG_0075.JPG");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh4.ggpht.com/_4f1e_yo-zMQ/TCe5h9yN-TI/AAAAAAAAXqs/8X2fIjtKjmw/s144-c/IMG_1786.JPG");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh3.ggpht.com/_GEnSvSHk4iE/TDSfmyCfn0I/AAAAAAAAF8Y/cqmhEoxbwys/s144-c/_MG_3675.jpg");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh6.ggpht.com/_ZN5zQnkI67I/TCFFZaJHDnI/AAAAAAAABVk/YoUbDQHJRdo/s144-c/P9250508.JPG");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh4.ggpht.com/_XjNwVI0kmW8/TCOwNtzGheI/AAAAAAAAC84/SxFJhG7Scgo/s144-c/0014.jpg");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh6.ggpht.com/_Nsxc889y6hY/TBp7jfx-cgI/AAAAAAAAHAg/Rr7jX44r2Gc/s144-c/IMGP9775a.jpg");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh6.ggpht.com/_ZN5zQnkI67I/TCFFZaJHDnI/AAAAAAAABVk/YoUbDQHJRdo/s144-c/P9250508.JPG");
		results.add(newsData);

		newsData = new clothingEntity();
		newsData.setBrand("Polo");
		newsData.setPrice("$70.00");
		newsData.setImageUrl("http://lh6.ggpht.com/_ZN5zQnkI67I/TCFFZaJHDnI/AAAAAAAABVk/YoUbDQHJRdo/s144-c/P9250508.JPG");
		results.add(newsData);

		return results;
	}
	*/
