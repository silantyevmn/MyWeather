package silantyevmn.ru.weather.utils.myLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import silantyevmn.ru.weather.database.city.CityEntity;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by silan on 15.07.2018.
 */

public class MyLocation {
    private onLocationChanged listener;
    private static MyLocation myLocation;

    public static void init() {
        if(myLocation==null){
            myLocation=new MyLocation();
        }
    }

    public static MyLocation getInstance(){
        return myLocation;
    }

    public interface onLocationChanged{
        void onLocationChanged(CityEntity cityEntity);
    }
    public void initListener(onLocationChanged listener){
        this.listener=listener;
    }

    public void requestLocation(Context context, final CityEntity cityEntity) {
        // Если пермиссии все-таки нет - просто выйдем, приложение не имеет смысла
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // Получим наиболее подходящий провайдер геолокации по критериям
        // Но можно и самому назначать, какой провайдер использовать
        // В основном это LocationManager.GPS_PROVIDER или
        //       LocationManager.NETWORK_PROVIDER
        // Но может быть и LocationManager.PASSIVE_PROVIDER (когда координаты уже кто-то
        //        недавно получил)
        String provider = locationManager.getBestProvider(criteria, true);
        //String provider = LocationManager.NETWORK_PROVIDER;
        if (provider != null) {
            //Будем получать геоположение через каждые 1 секунда или каждые 1000 метров
            locationManager.requestLocationUpdates(provider, 1000, 1000, new
                    LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (cityEntity.getLatitude() == 0 && cityEntity.getLongitude() == 0) {
                                //Широта
                                cityEntity.setLatitude(location.getLatitude());
                                //Долгота
                                cityEntity.setLongitude(location.getLongitude());
                                listener.onLocationChanged(cityEntity);
                            }
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
                    });
        }

    }
}
