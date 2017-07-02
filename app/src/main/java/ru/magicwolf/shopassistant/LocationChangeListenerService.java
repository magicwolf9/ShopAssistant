package ru.magicwolf.shopassistant;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LocationChangeListenerService extends Service {

    long MIN_TIME_BTW_UPDATES = 1000 * 60;
    float MIN_DISTANCE_CHANGE_FOR_UPDATES = 200;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setLocationUpdateListener();
        return Service.START_STICKY;
    }

    void setLocationUpdateListener() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("INFO", "Service update");
                List<Shop> newShops = getShopsInNewArea(location);
                makeNotificationsAboutShops(newShops);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BTW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    List<Shop> getShopsInNewArea(Location location){
        //TODO load shops list
        GetShopsAsync getShopsAsync = new GetShopsAsync();
        try {
            return getShopsAsync.execute(location.getLatitude(), location.getLongitude()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    void makeNotificationsAboutShops(List<Shop> shops){
        //TODO notify user about new shop
        Log.i("INFO", "Just notifying about" + shops.get(0).shopName);
        Intent resultIntent = new Intent(this, MainActivity.class);

        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        for(int i = 0; i < shops.size(); i++) {
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_menu_share)
                            .setContentTitle(shops.get(i).shopName + "недалеко от вас")
                            .setContentText(shops.get(i).shopName)
                            .setContentIntent(resultPendingIntent);

            mNotifyMgr.notify(i, mBuilder.build());
        }
    }

}
