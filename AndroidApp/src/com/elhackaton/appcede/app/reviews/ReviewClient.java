package com.elhackaton.appcede.app.reviews;


import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;

@Rest(rootUrl="http://192.168.1.40",  converters = { FormHttpMessageConverter.class, GsonHttpMessageConverter.class })
public interface ReviewClient {

	
	@Post("/api/reviews")
	public void newRewiew(Review review);
	
	@Get("/api/reviews")
	public Review[] getReviews();
	
	
	@Get("/api/reviews/{id}")
	public Review getReview(int id);
	
	
}
