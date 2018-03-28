package com.example.johnny.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {
    EditText idinput;
    EditText fnameinput;
    EditText lnameinput;
    EditText dobinput;
    EditText genderinput;
    EditText emailinput;


    Button submitbtn;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //following two line enable the use of network on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        idinput = (EditText) findViewById(R.id.IDinput);
        fnameinput = (EditText) findViewById(R.id.nameInput);
        lnameinput  = (EditText) findViewById(R.id.editText);
        dobinput  = (EditText) findViewById(R.id.editText2);
        genderinput  = (EditText) findViewById(R.id.editText3);
        emailinput  = (EditText) findViewById(R.id.editText4);

        submitbtn = (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.resultview);




        submitbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                //add new json object with variable inside
                Map<String, String> jsonObject = new HashMap<String, String>();
                jsonObject.put("id",idinput.getText().toString());
                jsonObject.put("fname", fnameinput.getText().toString());
                jsonObject.put("lname", lnameinput.getText().toString());
                jsonObject.put("dob",dobinput.getText().toString());
                jsonObject.put("gender",genderinput.getText().toString());
                jsonObject.put("email",emailinput.getText().toString());
                Toast.makeText(MainActivity.this, "Upload Succeed! Input Information: " + jsonObject.toString(), Toast.LENGTH_LONG).show();
                JSONObject parameter = new JSONObject(jsonObject);

                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(JSON, parameter.toString());
                Request request = new Request.Builder()
                        .url("https://johnnyuusqlspringboot.cfapps.io/Users")
                        .post(body)
                        .addHeader("content-type", "application/json; charset=utf-8")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("response", call.request().body().toString());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("response", response.body().string());
                    }
                });




            }



        });

    }
}
