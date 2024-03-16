package com.example.detectfire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    private WebSocketManager webSocketManager;
    private static TextView textView;
    private Button turn_off;
    private String status;
    private boolean isFire;
    private String name;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.fire_data);
        turn_off = findViewById(R.id.turn_off);

        webSocketManager = new WebSocketManager(this);

        turn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketManager.closeWebSocket();
                MainActivity.super.onDestroy();
                Toast.makeText(MainActivity.this, "Disconnected!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onDataUpdated(final String data){
        Log.d("IAMMIA", "Text");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(data);

                    isFire = jsonObject.getBoolean("fire");
                    name = jsonObject.getString("name");
                    address = jsonObject.getString("address");

                    textView.setText("Fire: " + isFire + "\n"
                            + "Name: " + name + "\n"
                            + "Address: " + address);
                } catch (JSONException e) {e.printStackTrace();}
            }
        });
    }
    @Override
    protected void onDestroy() {
        webSocketManager.closeWebSocket();
        super.onDestroy();
    }
}