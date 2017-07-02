package ru.magicwolf.shopassistant;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GmapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    Location userLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
    }

    List<Shop> getNearbyShops(Location userLocation) {
        GetShopsAsync getShopsAsync = new GetShopsAsync();
        try {
            Log.i("INFO", "Before the getShopsAsync");
            return getShopsAsync.execute(userLocation.getLatitude(), userLocation.getLongitude()).get(10000l, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            Log.i("INFO", "Error during getNearbyShops" + e.getMessage());
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showNearbyShopsMarkers(List<Shop> shops) {
        mMap.clear();
        if(shops != null){
            for (Shop shop : shops) {
                Log.i("INFO", "Making markers");
                showShopMarker(shop);
            }
        }
    }

    void showShopMarker(Shop shop) {
        LatLng shopPosition = new LatLng(shop.shopGeo.lat, shop.shopGeo.lng);
        Log.i("INFO", "Shop position" + shop.shopGeo.lat + " " + shop.shopGeo.lng);
        mMap.addMarker(new MarkerOptions().position(shopPosition).title(shop.shopName));
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            if(mMap != null ){
                Log.i("INFO", "myLocationChangeListener");
                List<Shop> nearbyShops = getNearbyShops(location);
                if(nearbyShops != null) {
                    Log.i("INFO", "Nearby shop number 0 is " + nearbyShops.get(0).shopName);
                }
                showNearbyShopsMarkers(nearbyShops);
            }
        }
    };
}
