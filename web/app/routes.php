<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/

Route::get('/', function()
{
	//return View::make('hello');

	return View::make('map');
});



Route::group(array('prefix' => 'api'), function()
{


	Route::model('reviews', 'Review');
	Route::resource('reviews', 'ReviewController');

	Route::get('image/{reviews}', function($review)
	{
		return Response::make(base64_decode($review->image), 200, array('content-type' => 'image/jpg'));
	});

});

Route::get('/reviews/{reviews}', array('uses'=>'ReviewController@show' , 'as'=>'reviews.show'));

Route::model('solutions', 'Solution');
Route::resource('reviews.solutions', 'SolutionController');	

Route::get('image/(:any)', function($image)
{
    // Use $image to get the image from the database or whatever, then return the images data.

    return $image;
});