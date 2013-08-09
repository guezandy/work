package com.zipper.zipcloset;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.kinvey.android.Client;
import com.kinvey.android.callback.*;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ZipActivity extends Activity {
	private static final String KINVEY_KEY = "kid_PVAtuuzi2f";
	private static final String KINVEY_SECRET_KEY = "2cab4a07424945e981478fcfc02341af";
	
	private Client mkinveyClient;
	private NfcAdapter mNfcAdapter;

	private Button mEnableWriteButton;
	private Button mPurchaseButton;
	private Button mFavoriteButton;
	private EditText mTextField;
	private ProgressBar mProgressBar;
	private TextView idText;
	private TextView brand;
	private TextView price;
	private ImageView mImageView;
	
	private Intent browserIntent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zip);

		mTextField = (EditText) findViewById(R.id.nfcWriteString);
		
		idText = (TextView) findViewById(R.id.textViewId);
		brand = (TextView) findViewById(R.id.textViewBrand);
		price = (TextView) findViewById(R.id.textViewPrice);

		mProgressBar = (ProgressBar) findViewById(R.id.pbWriteToTag);
		mProgressBar.setVisibility(View.GONE);
		
		mPurchaseButton = (Button) findViewById(R.id.purchaseButton);
		mFavoriteButton = (Button) findViewById(R.id.favoriteButton);
		mEnableWriteButton = (Button) findViewById(R.id.writeToTagButton);
		
		mEnableWriteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTagWriteReady(!isWriteReady);
				mProgressBar.setVisibility(isWriteReady ? View.VISIBLE : View.GONE);
			}
		});

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mNfcAdapter == null) {
			Toast.makeText(this, "Sorry, NFC is not available on this device", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		// Initialize Kinvey
		Client mKinveyClient = new Client.Builder(
				  KINVEY_KEY, 
				  KINVEY_SECRET_KEY, 
				  this.getApplicationContext()).build();
	}

	private boolean isWriteReady = false;

	/**
	 * Enable this activity to write to a tag
	 * 
	 * @param isWriteReady
	 */
	public void setTagWriteReady(boolean isWriteReady) {
		this.isWriteReady = isWriteReady;
		if (isWriteReady) {
			IntentFilter[] writeTagFilters = new IntentFilter[] { new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED) };
			mNfcAdapter.enableForegroundDispatch(ZipActivity.this, NfcUtils.getPendingIntent(ZipActivity.this),
					writeTagFilters, null);
		} else {
			// Disable dispatch if not writing tags
			mNfcAdapter.disableForegroundDispatch(ZipActivity.this);
		}
		mProgressBar.setVisibility(isWriteReady ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onNewIntent(Intent intent) {
		// onResume gets called after this to handle the intent
		setIntent(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isWriteReady && NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
			processWriteIntent(getIntent());
			System.out.println("Crashing in here");
		} else if (!isWriteReady
				&& (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction()) || NfcAdapter.ACTION_NDEF_DISCOVERED
						.equals(getIntent().getAction()))) {
			mTextField.setVisibility(View.GONE);
			mEnableWriteButton.setVisibility(View.GONE);
			System.out.println("Crashing in here");
			processReadIntent(getIntent());
		} else if(getIntent().getStringExtra("nfcId").equals("n")) {
			
		} else {
			if(!(getIntent().getStringExtra("nfcId").equals(null))) {
			System.out.println("crashing in here3");
				mTextField.setVisibility(View.GONE);
				mEnableWriteButton.setVisibility(View.GONE);
				Intent itemScreen = getIntent();
				String nfcIdNum = itemScreen.getStringExtra("nfcId");
				System.out.println(nfcIdNum);
				update(nfcIdNum);
			}
			else {}
		}
	}

	private static final String MIME_TYPE = "application/com.tapped.nfc.tag";

	/**
	 * Write to an NFC tag; reacting to an intent generated from foreground
	 * dispatch requesting a write
	 * 
	 * @param intent
	 */
	public void processWriteIntent(Intent intent) {
		if (isWriteReady && NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {

			Tag detectedTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

			String tagWriteMessage = mTextField.getText().toString();
			byte[] payload = new String(tagWriteMessage).getBytes();

			if (detectedTag != null && NfcUtils.writeTag(
					NfcUtils.createMessage(MIME_TYPE, payload), detectedTag)) {
				
				Toast.makeText(this, "Wrote '" + tagWriteMessage + "' to a tag!", 
						Toast.LENGTH_LONG).show();
				setTagWriteReady(false);
			} else {
				Toast.makeText(this, "Write failed. Please try again.", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void processReadIntent(Intent intent) {
		List<NdefMessage> intentMessages = NfcUtils.getMessagesFromIntent(intent);
		List<String> payloadStrings = new ArrayList<String>(intentMessages.size());

		for (NdefMessage message : intentMessages) {
			for (NdefRecord record : message.getRecords()) {
				byte[] payload = record.getPayload();
				String payloadString = new String(payload);

				if (!TextUtils.isEmpty(payloadString))
					payloadStrings.add(payloadString);
			}
		}

		if (!payloadStrings.isEmpty()) {
			String content =  TextUtils.join(",", payloadStrings);
			Toast.makeText(ZipActivity.this, "Read from tag: " + content,
					Toast.LENGTH_LONG).show();
			System.out.println(content);
			
			update(content);
		}
	}
	
	private void update(final String id) {
		System.out.println(id);
		final ProgressDialog pd = ProgressDialog.show(ZipActivity.this, 
    			"", "Loading...", true);
    	
        // Fetch tag history
    	
		mkinveyClient.appData("entityCollection", clothingEntity.class).get(new Query(), new KinveyListCallback<clothingEntity>() {
            @Override
    		public void onSuccess(final clothingEntity[] result) {
				String entityid;
				for(final clothingEntity entity: result) {
					entityid = entity.getId();
					System.out.println("comparing stuff: " +id.compareToIgnoreCase(entityid));
					if(id.equals(entityid)) {
						saveTagToX(entity, "tags");
						System.out.println("Inside if statement" +entity.getId());  
						idText.setText("Nfc Id: " +entity.getId());
						price.setText("Price: "+entity.getPrice());
						brand.setText("Brand: "+entity.getBrand());
						mPurchaseButton.setOnClickListener(new OnClickListener() {
								
							@Override
							public void onClick(View v) {
								browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.getPurchaseUrl()));
								startActivity(browserIntent);
							}
						});
						mFavoriteButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								saveTagToX(entity, "favorites");
								Toast.makeText(ZipActivity.this, "Added to Favorites", Toast.LENGTH_LONG).show();
								}
						});
						mImageView = (ImageView) findViewById(R.id.itemImage);
						if(mImageView != null) {
							new ImageDownloaderTask(mImageView).execute(entity.getImageUrl());
						}
						break;
					}
					else  {}
				}
				pd.dismiss();
			}
			
			@Override
            public void onFailure(Throwable error) {
                Log.e("ZipCloset", "Received error response", error);
                pd.dismiss();
            }
		});
	}

	
	private void saveTagToX(clothingEntity tag, String collection){
		
		mkinveyClient.appData(collection, clothingEntity.class).save(tag, new KinveyClientCallback<clothingEntity>() {
            @Override
            public void onSuccess(clothingEntity result) {
          
                Toast.makeText(ZipActivity.this,"Saved to Closet", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Throwable error) {
                
                Log.e("ZipActivity", "AppData.save Failure", error);
                Toast.makeText(ZipActivity.this, "Save All error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
	}
}
