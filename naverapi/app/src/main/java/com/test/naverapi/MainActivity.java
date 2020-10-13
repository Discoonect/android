package com.test.naverapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.naverapi.adapter.RecyclerViewAdapter;
import com.test.naverapi.model.Results;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Results> resultsList = new ArrayList<>();

    RequestQueue requestQueue;
    String papagoUrl = "https://openapi.naver.com/v1/papago/n2mt";
    EditText editOriginal;
    RadioButton radio_en;
    RadioButton radio_jianti;
    RadioButton radio_fanti;
    RadioButton radio_tai;
    Button btn;
    String target="";
    RadioGroup radioGroup;
    TextView txt_kor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radio_en = findViewById(R.id.radio_en);
        radio_fanti = findViewById(R.id.radio_fanti);
        radio_jianti = findViewById(R.id.radio_jianti);
        radio_tai = findViewById(R.id.radio_tai);
        editOriginal = findViewById(R.id.editOriginal);
        radioGroup = findViewById(R.id.radioGroup);
        btn = findViewById(R.id.btn);
        txt_kor = findViewById(R.id.txt_kor);


        radio_en.setChecked(true);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        editOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOriginal.setText("");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest request = new StringRequest(Request.Method.
                        POST,
                        papagoUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                    Log.i("AAA", response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject message = jsonObject.getJSONObject("message");
                                        JSONObject result = message.getJSONObject("result");
                                        Log.i("AAA",result.toString());
                                        String translatedText = result.getString("translatedText");

                                        String kor = editOriginal.getText().toString().trim();

                                        Results results = new Results(translatedText,kor);
                                        Log.i("AAA",translatedText);

                                        resultsList.add(0,results);

                                        recyclerViewAdapter = new RecyclerViewAdapter(
                                                MainActivity.this,resultsList);
                                        recyclerView.setAdapter(recyclerViewAdapter);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("AAA",error.toString());
                            }
                        }
                )

                {
                    // 네이버 API 헤더 세팅부분
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                        params.put("X-Naver-Client-Id", "7ci_mcAqTX1HfUen2T0t");
                        params.put("X-Naver-Client-Secret","xIqhYhZFdg");
                        return params;
                    }

                    // 네이버 요청할 파라미터를 세팅

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        int checkedID = radioGroup.getCheckedRadioButtonId();

                        if(checkedID==R.id.radio_en){
                            target = "en";
                        }else if(checkedID==R.id.radio_jianti){
                            target = "zh-CN";
                        }else if(checkedID==R.id.radio_fanti){
                            target = "zh-TW";
                        }else if(checkedID==R.id.radio_tai){
                            target = "th";
                        }

                        String text = editOriginal.getText().toString().trim();


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("source","ko");
                        params.put("target",target);
                        params.put("text",text);
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });



    }
}
