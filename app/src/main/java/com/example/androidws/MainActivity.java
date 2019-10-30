package com.example.androidws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvResult ;
    Button btn;
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPass);
        //final String url ="https://api.github.com/users/Evin1-/repos";
        //final String url="https://reqres.in/api/login";
        final String url="https://192.168.43.55:5433";
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String e= email.getText().toString();
                String p= pass.getText().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("email",e);
                    json.put("password",p);
                }catch (JSONException error){
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

                tvResult = findViewById(R.id.tvResult);
                OkHttpClient client=new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json.toString()))
                        .build();
                Toast.makeText(MainActivity.this, json.toString(), Toast.LENGTH_LONG).show();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResult.setText(myResponse);
                        }
                    });


                        }

                    }
                });
            }
        });
    }

}
