<?php

class ReviewController extends \BaseController {

	/**
	 * Display a listing of the resource.
	 *
	 * @return Response
	 */
	public function index()
	{
		$reviews = Review::all();
		foreach ($reviews as $key => $value) {
			$value->number = count($value->solutions);
		}
		return $reviews;
	}

	/**
	 * Show the form for creating a new resource.
	 *
	 * @return Response
	 */
	public function create()
	{
		//
	}

	/**
	 * Store a newly created resource in storage.
	 *
	 * @return Response
	 */
	public function store()
	{
		$review = new Review;
		$review->title = Input::get('title');
		$review->body = Input::get('body');
		//$review->picture = Input::file('picture');
		$review->review = Input::get('review');
		$review->place = Input::get('place');
		$review->lat = Input::get('lat');
		$review->lng = Input::get('lng');
		$review->category = Input::get('category');
		$review->picture = Input::has('picture');
		$review->save();
		//Review::create(Input::all());

		File::put('img/review'.$review->id.".jpg", base64_decode(Input::get('picture')));


	}

	/**
	 * Display the specified resource.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function show($review)
	{
		//$review = Review::where('lat','LIKE',"%".substr($lat,0,8)."%")->where('lng','LIKE',"%".substr($lng,0,8)."%")->first();
		return View::make('review')->withReview($review)->withSolutions($review->solutions);
	}

	/**
	 * Show the form for editing the specified resource.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function edit($id)
	{
		//
	}

	/**
	 * Update the specified resource in storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function update($id)
	{
		//
	}

	/**
	 * Remove the specified resource from storage.
	 *
	 * @param  int  $id
	 * @return Response
	 */
	public function destroy($id)
	{
		//
	}

}