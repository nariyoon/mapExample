package com.nryoon.mapexample.kakao

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.naver.maps.geometry.LatLng
import com.nryoon.mapexample.R
import com.nryoon.mapexample.kakao.data.KakaoKeywordResult
import com.nryoon.mapexample.kakao.network.KakaoMapApi
import com.nryoon.mapexample.common.SearchResultItem
import com.nryoon.mapexample.common.SearchResultListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class KakaoMapActivity : AppCompatActivity(),
    SearchResultListFragment.OnSearchItemSelectedListener {
    private val mapFragment: KakaoMapFragment = KakaoMapFragment()
    private val searchFragment: SearchResultListFragment = SearchResultListFragment()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = "Kakao Map"
        }

        initFragments()
        searchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                searchView.clearFocus();

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun search(query: String?) {
        if (query.isNullOrEmpty()) {
            Toast.makeText(this, "검색어를 입력하여주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        showSearchFragment()

        val searchResultList = mutableListOf<SearchResultItem>()
        CoroutineScope(Dispatchers.IO).launch {
            val keywords = KakaoMapApi.getKeywordSearchData(query)
            keywords?.forEach {
                searchResultList.add(SearchResultItem(it.place_name, it.address_name, it.x.toDouble(), it.y.toDouble()))
            }

            val documents = KakaoMapApi.getAddressSearchData(query)
            documents?.forEach {
                searchResultList.add(SearchResultItem(it.road_address.address_name, it.address.address_name, it.x.toDouble(), it.y.toDouble()))
            }

            withContext(Dispatchers.Main) {
                searchFragment.updateItems(searchResultList)
            }
        }
    }

    override fun onBackPressed() {
        if(searchFragment.isVisible) {
            showMapFragment()
            searchView.clearFocus()
            searchView.setQuery("", false)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    private fun initFragments() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, mapFragment)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, searchFragment)
            .commit()

        showMapFragment()
    }

    private fun showMapFragment() {
        supportFragmentManager.beginTransaction()
            .hide(searchFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .show(mapFragment)
            .commit()
    }

    private fun showSearchFragment() {
        supportFragmentManager.beginTransaction()
            .show(searchFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .hide(mapFragment)
            .commit()
    }

    override fun onItemSelected(item: SearchResultItem) {
        showMapFragment()
        val x = item.mapx
        val y = item.mapy
        mapFragment.moveToLocation(LatLng(y, x), item.name)
    }
}