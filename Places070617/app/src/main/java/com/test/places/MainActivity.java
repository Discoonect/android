package com.test.places;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.places.adapter.RecyclerViewAdapter;
import com.test.places.model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//
public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Store> storeArrayList = new ArrayList<>();

    RequestQueue requestQueue;
    String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?language=ko&radius=20000";
    String key = "&key=AIzaSyB6MGXsA05A43EkIjfgv4RRh2zawJRjFPY";

    // GPS 좌표 가져오기 위한 LocationManager 멤버변수 선언.
    LocationManager locationManager;
    // 위치가 변경될 때마다, 처리해줄 리스너 멤버변수로 선언.
    LocationListener locationListener;

    String nextPageToken = "";
    String pageToken = "";

    double lat;
    double lng;

    boolean isFirst = true;

    String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = editSearch.getText().toString().trim();
                storeArrayList.clear();
                getNetworkData(lat, lng);

            }
        });
        recyclerView = findViewById(R.id.recyclerView);

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(lastPosition+1 == totalCount) {
                    //아이템 추가 ! 입맛에 맞게 설정하시면됩니다.
                    // nextPageToken , pageToken 이 2개를 멤버변수로 셋팅.
                    if(!nextPageToken.isEmpty() && !nextPageToken.equals(pageToken)){
                        pageToken = nextPageToken;

                        addNetworkData();
                    }
                }

            }
        });

        // 위치기반 서비스를 위해서, 안드로이드 시스템에 위치기반서비스 요청.
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("AAA", location.toString());
                lat = location.getLatitude();
                lng = location.getLongitude();

                // 네트워크로 구글 플레이스 api 호출.
                if(isFirst){
                    Log.i("AAA", "맨 처음 한번만 호출됨.");
                    isFirst = false;
                    getNetworkData(lat, lng);
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 유저한테, 이 앱은 위치기반 권한이 있어야 한다고 알려야 한다.
            // 유저가 권한 설정을 하고 나면, 처리해야 할 코드를 작성하기 위해서,
            // requestCode 값을 설정한다.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 0, locationListener);

    }

    private void addNetworkData() {
        String url = "";

        if(keyword.isEmpty()){
            url = baseUrl+ key +"&location="+lat+","+lng+"&pagetoken="+pageToken;
        }else{
            url = baseUrl+ key +"&location="+lat+","+lng+"&keyword="+keyword+"&pagetoken="+pageToken;
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Exception : 실행도중에 문제가 발생할 경우, catch 에서 처리할 수 있도록
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.isNull("next_page_token")) {
                                nextPageToken = jsonObject.getString("next_page_token");
                            }
                            JSONArray results = jsonObject.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++){
                                JSONObject item = results.getJSONObject(i);
                                JSONObject geometry = item.getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                double storeLat = location.getDouble("lat");
                                double storeLng = location.getDouble("lng");
                                String name = item.getString("name");
                                String addr = item.getString("vicinity");
                                Store store = new Store(name, addr, storeLat, storeLng);
                                storeArrayList.add(store);
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("AAA", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

    void getNetworkData(double lat, double lng) {
        String url = "";
        if(keyword.isEmpty()){
            url = baseUrl+ key +"&location="+lat+","+lng;
        }else{
            url = baseUrl+ key +"&location="+lat+","+lng+"&keyword="+keyword;
        }


        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Exception : 실행도중에 문제가 발생할 경우, catch 에서 처리할 수 있도록
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.isNull("next_page_token")) {
                                nextPageToken = jsonObject.getString("next_page_token");
                            }
                            JSONArray results = jsonObject.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++){
                                JSONObject item = results.getJSONObject(i);
                                JSONObject geometry = item.getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                double storeLat = location.getDouble("lat");
                                double storeLng = location.getDouble("lng");
                                String name = item.getString("name");
                                String addr = item.getString("vicinity");
                                Store store = new Store(name, addr, storeLat, storeLng);
                                storeArrayList.add(store);
                            }
                            adapter = new RecyclerViewAdapter(MainActivity.this,
                                    storeArrayList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("AAA", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0){
            if(ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000,   // 밀리세컨드,  1000 : 1초
                    0,   // 미터   10m
                    locationListener);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.menuMap){
            Intent i = new Intent(MainActivity.this,
                    MyMapsActivity.class);
            i.putExtra("lat", lat);
            i.putExtra("lng", lng);
            i.putExtra("storeList", storeArrayList);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}






