package com.nryoon.mapexample.naver

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.nryoon.mapexample.R
import com.nryoon.mapexample.naver.network.NaverApi
import com.nryoon.mapexample.common.SearchResultItem
import com.nryoon.mapexample.common.SearchResultListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback, SearchResultListFragment.OnSearchItemSelectedListener {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }

    private lateinit var map: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var searchView: SearchView
    private val marker = Marker()
    private val mapFragment: MapFragment = MapFragment.newInstance(NaverMapOptions().locationButtonEnabled(true))
    private val searchFragment = SearchResultListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = "Naver Map"
        }

        initFragments()
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

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

        marker.map = null
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

    private fun search(query: String?) {
        if (query.isNullOrEmpty()) {
            Toast.makeText(this, "검색어를 입력하여주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        showSearchFragment()

        val searchResultList = mutableListOf<SearchResultItem>()
        CoroutineScope(Dispatchers.IO).launch {
            val searchItems = NaverApi.getNaverSearchResultItems(query)
            searchItems?.forEach {
                val latLng = Tm128(it.mapx.toDouble(), it.mapy.toDouble()).toLatLng()
                searchResultList.add(SearchResultItem(Html.fromHtml(it.title).toString(), it.address, latLng.latitude, latLng.longitude))
            }

            val mapResult = NaverApi.getNaverMapAddresses(query)
            mapResult?.forEach {
                searchResultList.add(SearchResultItem(it.roadAddress, it.jibunAddress, it.y.toDouble(), it.x.toDouble()))
            }

            withContext(Dispatchers.Main) {
                searchFragment.updateItems(searchResultList)
            }
        }
    }

    override fun onBackPressed() {
        if (searchFragment.isVisible) {
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                map.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap
        naverMap.locationSource = locationSource
        locationSource.isCompassEnabled = true
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    override fun onItemSelected(item: SearchResultItem) {
        showMapFragment()

        val latLng = LatLng(item.mapx, item.mapy)
        marker.position = latLng
        marker.map = map
        val cameraUpdate = CameraUpdate.scrollTo(latLng)
            .animate(CameraAnimation.Fly)
        map.moveCamera(cameraUpdate)

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
}

