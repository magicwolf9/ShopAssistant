package ru.magicwolf.shopassistant;

import com.google.android.gms.common.api.GoogleApiClient;

class MyLocationListener {
    private GoogleApiClient mGoogleApiClient;

    /*private Location getLocationDetails(Context mContext) {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage( *//* FragmentActivity *//*,
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        } *//* OnConnectionFailedListener *//*)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
        Location location = null;
        if (mGoogleApiClient != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("INFO", "Location Permission Denied");
                return null;
            }else {
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        }
        return location;
    }*/
}