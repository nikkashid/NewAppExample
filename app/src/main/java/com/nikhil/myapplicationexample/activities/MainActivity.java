package com.nikhil.myapplicationexample.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nikhil.myapplicationexample.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import data.ResponsePojo;

public class MainActivity extends AppCompatActivity {

    TextView tv_showData;
    Button btn_getData;

    RequestQueue queue;
    String URL = "https://access.myct.co.in/getAllCategory";

    ResponsePojo responsePojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btn_getData.setOnClickListener(v -> getData());

    }

    private void getData() {

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, response -> {
            tv_showData.setText(response);

            parseResponse(response);
        }, error -> {
            Log.d("Volley error : ", error.toString());
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data
                    JSONObject obj = new JSONObject(res);
                    Log.d("Volley Error  : ", obj.toString());
                    tv_showData.setText(obj.toString());
                } catch (UnsupportedEncodingException | JSONException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                } // returned data is not JSONObject?

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("string", "eyJjdCI6InJkZll2SDd0WGtvZVJ2Y3I3RTZSb1k5YmVSakt5OHY3d25YMkliZlhrbDFMeGxCcTBoYWZwdG9RUnBJcTBJU2hmZnRINjVqUjNNVlFQQ0R5Vks5Zk1BPT0iLCJpdiI6IjZhZTZlZjgyMDM2ZGZjZGQ2MmU2ODRlZjQ4MTNmNzMxIiwicyI6IjZjNGQ2NWM4YWQxOTdiNDgifQ==");
                return headers;
            }
        };
        queue.add(request);

    }

    private void parseResponse(String stringResponse) {

        try {

            Gson gson = new Gson();
            responsePojo = gson.fromJson(stringResponse, ResponsePojo.class);
            Log.d("TAG", "parseResponse: print first element to check the output " + responsePojo.result.get(0));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void initView() {
        btn_getData = findViewById(R.id.btn_getData);
        tv_showData = findViewById(R.id.tv_showData);
    }
}