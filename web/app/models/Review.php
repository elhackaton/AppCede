<?php


class Review extends Eloquent {

	protected $fillable = array('title','body','picture','review','place','category','lat','lng');
	protected $hidden = array('picture');

	public function solutions()
    {
        return $this->hasMany('Solution');
    }
}