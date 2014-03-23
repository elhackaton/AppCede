package com.elhackaton.appcede.ui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.elhackaton.appcede.R;
import com.elhackaton.appcede.app.reviews.Review;
import com.elhackaton.appcede.app.reviews.ReviewClient;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.review)
public class ReviewUI extends Activity{


	@RestService
	ReviewClient client;
	
	@ViewById
	TextView title;
	
	@ViewById
	ImageView image;
	
	@ViewById
	TextView address;
	
	
	@AfterViews
	void afterView() {
		int id = getIntent().getIntExtra("id", 0);
		loadReview(id);
	}
	
	
	@Background
	void loadReview(int id) {
		
		Review review   = client.getReview(id);
		byte[] data 	= Base64.decode(review.getPicture(), Base64.DEFAULT);
		Bitmap bitmap 	= BitmapFactory.decodeByteArray(data, 0, data.length); 
		
		String address  = new String();
		Geocoder geocoder =  new Geocoder(this, Locale.getDefault());
		
		try {
			List<Address> addresses  = geocoder.getFromLocation(review.getLng(), review.getLng(), 1);
			address += addresses.get(0).getAddressLine(0) + "\n";
			address += addresses.get(0).getAddressLine(1) + "\n";
			address = addresses.get(0).getAddressLine(2);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setReview(review, bitmap, address);
		
		
	}
	
	@UiThread
	void setReview(Review review, Bitmap bitmap, String address) {
		
		title.setText(review.getTitle());
		image.setImageBitmap(bitmap);
		this.address.setText(address);
		
		
		
		
	}
	
}
