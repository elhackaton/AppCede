package com.elhackaton.appcede.app.reviews;

import java.io.File;

import org.springframework.core.io.FileSystemResource;

import com.elhackaton.appcede.R;


public class Review {
	
	private String title;
	private String body;

	private double lat;
	private double lng;
	
	private int place;
	private int category;
	
	private int review;
	
	private int id;
	
	private String picture;
	

	public Review() {
		super();
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}

	public boolean isReview() {
		return review == 1;
	}


	public void setReview(int review) {
		this.review = review;
	}

	
	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}


	public String getPicture() {
		return "hola";
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	
	public int getIcon() {
		
		switch (category) {
			case Review.AUDITIVA:
				return isReview() ? R.drawable.gota_verde_auditivo : R.drawable.gota_roja_auditivo;
			case Review.MOVILIDAD:
				return isReview() ? R.drawable.gota_verde_movilidad : R.drawable.gota_roja_movilidad;
			case Review.VISUAL:
				return isReview() ? R.drawable.gota_verde_visual : R.drawable.gota_roja_visual;	
			case Review.INTELECTUAL:
				return isReview() ? R.drawable.gota_verde_intelectual : R.drawable.gota_roja_intelectual;

		}
		
		return 0;
	}
	
	
	public static final int HOSTELERIA 		= 0;
	public static final int HOTEL 			= 1;
	public static final int COMERCIO 		= 2;
	public static final int VIA_PUBLICA 	= 3;
	public static final int ADMINISTRACION 	= 4;
	public static final int OCIO			= 5;
	public static final int TRANSPORTE		= 6;
	
	public static final int MOVILIDAD		= 0;
	public static final int AUDITIVA		= 1;
	public static final int VISUAL			= 2;
	public static final int INTELECTUAL		= 3;
	

}
