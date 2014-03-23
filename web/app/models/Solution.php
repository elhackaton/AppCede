<?php


class Solution extends Eloquent {

	protected $fillable = array('body','name','telephone','email');

    public function feedbacks()
    {
        return $this->belongsTo('Review');
    }  	
}