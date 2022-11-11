package com.nryoon.mapexample

interface IThinQMap {
    fun loadStart()
    fun loadCompleted()

    fun geocodingCompleted()
    fun reverseGeocodingCompleted()
    fun searchCompleted()
}