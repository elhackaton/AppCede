package com.elhackaton.appcede.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elhackaton.appcede.R;
import com.elhackaton.appcede.app.reviews.Review;
import com.elhackaton.appcede.app.reviews.ReviewClient;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

@EActivity(R.layout.newreview)
public class NewReviewUI extends Activity{
	
	private ProgressDialog dialog;

	
	@ViewById
	EditText place;
	
	@ViewById
	Spinner review;

	@ViewById
	Spinner category;
	
	@ViewById
	Spinner places;
	
	@ViewById
	EditText body;
	
		
	@RestService
	ReviewClient client;

	
	@ViewById
	Button ok;

	double lng;
	double lat;
	
	File file;
	String picture;
	
	

	
	
	private LocationManager locationManager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		dialog = new ProgressDialog(this);
		dialog.setCancelable(false);
		dialog.setTitle(R.string.sending);
		dialog.setMessage(getString(R.string.review_sending));
		
	}
	

	protected void onPause() {
		locationManager.removeUpdates(listener);
		super.onPause();
	}
	
	protected void onStart() {
		super.onStart();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, listener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
	}
	
	
	@Click(R.id.picture)
	protected void takePicture() {
		 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		 File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AppCede");
		 if (!dir.exists())
			 dir.mkdir();
		 
		 file = new File(dir, "appcede.jpg");

		 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
 	     startActivityForResult(intent, R.id.picture);
		    
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == R.id.picture && resultCode == RESULT_OK) 
	    	decode();
	    
	}

	
	public void decode() {
		Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        picture = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
	}
	
	
	
	
	@Click(R.id.ok)
	public void send() {
		dialog.show();
		sendReview();
	}
	
	
	@Background
	protected void sendReview() {
		Review r = new Review();
		r.setTitle(place.getText().toString());
		r.setBody(body.getText().toString());
		r.setCategory(category.getSelectedItemPosition());
		r.setLat(lat);
		r.setLng(lng);
		r.setPlace(places.getSelectedItemPosition());
		r.setReview(review.getSelectedItemPosition());
		r.setPicture(picture);
		
		try {
			client.newRewiew(r);
			closeDialog(R.string.review_send);
		} catch (Exception e) {
			e.printStackTrace();
			closeDialog(R.string.review_sending_error);
		}
		
	}
	
	@UiThread
	protected void closeDialog(int message) {
		dialog.dismiss();
		
		if (R.string.review == message)
			finish();
		else
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		}
		
		@Override
		public void onLocationChanged(Location location) {
			ok.setEnabled(true);
			lng = location.getLongitude();
			lat = location.getLatitude();
			
		}
	};
	
}
