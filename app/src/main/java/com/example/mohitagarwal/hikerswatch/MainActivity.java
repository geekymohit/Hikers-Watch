package com.example.mohitagarwal.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.mohitagarwal.hikerswatch.R.*;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    EditText editLatitude;
    EditText editLongitude;
    TextView Addresstextview;


    public void OnRequestPermissionsResult(int requestCode, @NonNull String[] permissions ,@NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            startListening();

        }

    }

    public void findaddress(View view){

        updateLocationInfo(Location );

    }

    public void startListening ()
    {

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }
    }

    public void    updateLocationInfo (Location location){

        Log.i("LocaitonInfo",location.toString());
        TextView laTtextview = (TextView)findViewById(id.textView2);
        TextView longtextview = (TextView)findViewById(id.textView3);
        TextView Accuracytextview = (TextView)findViewById(id.textView4);
        TextView Altitudetextview = (TextView)findViewById(id.textView5);
        Addresstextview = (TextView)findViewById(id.textView6);
        editLatitude = (EditText)findViewById(id.editText);
        editLongitude = (EditText)findViewById(id.editText2);
        EditText editAltitude = (EditText)findViewById(id.editText3);
        laTtextview.setText("Latitude : "+ location.getLatitude());
        longtextview.setText("Longitude " + location.getLongitude());
        Accuracytextview.setText("Accuracy " + location.getAccuracy());
        Altitudetextview.setText("Altitude " + location.getAltitude());
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {

            String address = "Could not find address";
            List<Address> addressList=geocoder.getFromLocation(Double.parseDouble(editLatitude.getText().toString()), Double.parseDouble(editLongitude.getText().toString()),1);
            if (addressList.size()>0 && addressList !=null) {

                Log.i("PlaceInfo", addressList.get(0).toString());
                address="Address : \n";
                if (addressList.get(0).getSubThoroughfare() != null){

                    address +=addressList.get(0).getSubThoroughfare()+"";


                }
                if (addressList.get(0).getThoroughfare() != null){

                    address +=addressList.get(0).getThoroughfare()+"\n";


                }
                if (addressList.get(0).getLocality() != null){

                    address +=addressList.get(0).getLocality()+"\n";


                }
                if (addressList.get(0).getPostalCode() != null){

                    address +=addressList.get(0).getPostalCode()+"\n";


                }
                if (addressList.get(0).getCountryName() != null){

                    address +=addressList.get(0).getCountryName()+"\n";


                }

            }

            Addresstextview.setText(address);

        } catch (IOException e) {

            e.printStackTrace();

        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT <23){

           startListening();

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);

        }else{

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,locationListener);
            Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){

                updateLocationInfo(location);

            }


        }
    }
}
