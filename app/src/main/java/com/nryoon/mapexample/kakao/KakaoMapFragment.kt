package com.nryoon.mapexample.kakao

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.nryoon.mapexample.R
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class KakaoMapFragment: Fragment() {
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 100
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }
    private lateinit var mapView: MapView
    private lateinit var marker: MapPOIItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kakao_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = MapView(activity)
        val mapViewContainer = view.findViewById<ViewGroup>(R.id.map_view)
        mapViewContainer.addView(mapView)

        marker = MapPOIItem()
        marker.tag = 0;
        marker.markerType = MapPOIItem.MarkerType.BluePin;
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin;

        activity?.let {
            val lm: LocationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(it, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
                return
            }

            val currentLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            currentLocation?.let {
                moveToLocation(LatLng(currentLocation.latitude, currentLocation.longitude), "현재 위치")
            }
        }
    }

    fun moveToLocation(location: LatLng, description: String) {
        val latitude = location.latitude
        val longitude = location.longitude
        val nowPosition = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        mapView.setMapCenterPoint(nowPosition, true)
        marker.mapPoint = nowPosition
        marker.itemName = description

        mapView.addPOIItem(marker)
    }
}