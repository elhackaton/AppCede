package com.elhackaton.appcede.ui;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.elhackaton.appcede.R;
import com.elhackaton.appcede.app.reviews.Review;
import com.elhackaton.appcede.app.reviews.ReviewClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;

@EActivity (R.layout.map)
public class MapUI extends Activity implements OnInfoWindowClickListener {

	
	private int place;
	private int category;
	
	@FragmentById
	MapFragment map;
	
	@RestService
	ReviewClient client;
	

	HashMap<Marker, Review> markers;
	
	GoogleMap gmap;
	
	private LocationManager locationManager;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		place    = -1;
		category = -1;
		
		markers = new HashMap<Marker, Review>();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				
	}

	
	protected void onStart() {
		super.onStart();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, listener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
	}
	
	
	
	
	@AfterViews
	public void afterView() {
		gmap = map.getMap();
		gmap.setMyLocationEnabled(true);
		gmap.setOnInfoWindowClickListener(this);
		createMarkers();
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.map, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.review:
				startActivity(new Intent(this, NewReviewUI_.class));
				break;
	
			case R.id.all: category = -1; break;
			case R.id.movility: category = Review.MOVILIDAD; break;
			case R.id.intellectual: category = Review.INTELECTUAL; break;
			case R.id.auditory: category = Review.AUDITIVA; break;
			case R.id.visual: category = Review.VISUAL; break;
			case R.id.hotel: place = Review.HOTEL; break;
			case R.id.all_place: place = -1; break;
			case R.id.thorughfare: place = Review.VIA_PUBLICA; break;
			case R.id.admin: place = Review.ADMINISTRACION; break;
			case R.id.entertainment: place = Review.OCIO; break;
			case R.id.comerce: place = Review.COMERCIO; break;
			case R.id.hostelry: place = Review.HOSTELERIA; break; 
				
			default:
				break;
			}

		filter();
		
		return super.onOptionsItemSelected(item);
		
	}
	
	
	private void filter() {
		
		for (Marker m : markers.keySet()) {
			Review r  = markers.get(m);
			
			if ((category == -1 || category == r.getCategory()) && (place == -1 || place == r.getPlace()))
				m.setVisible(true);
			else
				m.setVisible(false);
			
		}
		
	}
	
	
		
	
	@Background
	void createMarkers() {		
		showInMaps(client.getReviews());
	}
	
	@UiThread
	void showInMaps(Review reviews[]) {


		for (Review r : reviews) {
			
			MarkerOptions m = new MarkerOptions()
							.title(r.getTitle())
							.snippet(r.getBody())
							.position(new LatLng(r.getLat(), r.getLng()))
							.icon(BitmapDescriptorFactory.fromResource(r.getIcon()));

			markers.put(gmap.addMarker(m), r);;
			
		}		
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
		public void onLocationChanged(Location l) {
			LatLng position = new LatLng(l.getLatitude(), l.getLongitude());
			gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
			locationManager.removeUpdates(listener);
			
		}
	};


	@Override
	public void onInfoWindowClick(Marker marker) {
		
		Review review = markers.get(marker);
		
		Intent intent = new Intent(this, ReviewUI_.class);
		intent.putExtra("id", review.getId());
		
		startActivity(intent);
		
	}
	
	
	
}
