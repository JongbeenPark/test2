package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://dailypi2.duckdns.org/api/";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyNDgxODExZGYxYTE0NjZmYWViOTRkYjkxZTc2MTM3OSIsImlhdCI6MTY4NTI3MDI4MSwiZXhwIjoyMDAwNjMwMjgxfQ.fS10KcebQcHR9sR4pzcPtOqXk5g5MbTrS1w2YzYaALM";
    private static final String ENTITY = "light.kocom_room2_light1";

    HA_API ha_api;
    Button btnOn;
    Button btnOff;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ha_api = retrofit.create(HA_API.class);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLight(true);
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLight(false);
            }
        });
    }

    private void toggleLight(boolean turnOn) {
        String action = turnOn ? "on" : "off";
        LightRequest request = new LightRequest(ENTITY);

        Call<Void> call = ha_api.toggleLight("Bearer " + TOKEN, action, request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "전등" + (turnOn ? "켜짐" : "꺼짐"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "요청실패", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "통신오류", Toast.LENGTH_LONG).show();
            }
        });
    }
}