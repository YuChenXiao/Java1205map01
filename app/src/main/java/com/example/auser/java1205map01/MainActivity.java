package com.example.auser.java1205map01;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    TextView tv2,tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv2=(TextView)findViewById(R.id.tv2);
        tv =(TextView)findViewById(R.id.textView);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null)
        {
// 取到手機地點後的程式碼
            tv2.setText("定位完成");
        }

    }
    

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        tv2.setText("定位失敗");
    }

    public void clickone(View view){
        //volley
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final StringRequest request = new StringRequest("https://maps.googleapis.com/maps/api/elevation/json?locations=39.7391536,-104.9847034&AIzaSyAW-e92tYIc7p-jz8L_jskypSGmzmIbi98",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("NET", response);
                        //gson開始>
                        Gson gson = new Gson();
                        Elevation ele =gson.fromJson(response,Elevation.class);
                        tv.setText(String.valueOf(ele.results[0].elevation));
                        //gson結束<

                        //json開始>
//                        try {
//                            JSONObject obj =new JSONObject(response);
//                            JSONArray array = obj.getJSONArray("results");
//                            JSONObject obj1 =array.getJSONObject(0);
//                            double result =obj1.getDouble("elevation");
//                            tv.setText(String.valueOf(result));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        //json結束<

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        queue.start();
    }
    //將經緯度變換.getFromLocation(lat,lng,顯示幾筆1);
    public void clicktwo(View v){
        Geocoder geocoder =new Geocoder(MainActivity.this);
        try {
            List<Address> list =geocoder.getFromLocation(24.9318495,121.1717214,1);
            Address addr =list.get(0);
            String str = addr.getAddressLine(0);
            String str2 = addr.getLocality();
//                            .getCountryName();  //台灣省
//                            .getAdminArea();    //台北市
//                            .getLocality();     //中正區
//                            .getThoroughfare(); //信陽街(包含路巷弄)
//                            .getFeatureName();  //會得到33(號)
//                            .getPostalCode();   //會得到100(郵遞區號)

            tv.setText(str);
            tv2.setText(str2);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}





