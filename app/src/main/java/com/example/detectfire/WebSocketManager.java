package com.example.detectfire;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketManager {
    private MainActivity mActivity;
    private static final String WS_URL = "ws://10.0.2.2:8000/ws/fire_detection/";
    private OkHttpClient client;
    private WebSocket webSocket;
    private String fireData;
    public WebSocketManager(MainActivity activity) {
        this.mActivity = activity;
        client = new OkHttpClient();
        connectWebSocket();
    }

    private void connectWebSocket() {
        Request request = new Request.Builder().url(WS_URL).build();
        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d("IAMMIA", "WebSocket Connection Opened");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("IAMMIA", "Received message: " + text);
                updateUI(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.d("IAMMIA", "Received bytes: " + bytes.hex());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("IAMMIA", "WebSocket Connection closing: onClosing" );
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d("IAMMIA", "WebSocket Connection Closed: " + code + ", " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                Log.e("IAMMIA", "WebSocket Connection Failed: " + t.getMessage());
                Log.e("IAMMIA", Log.getStackTraceString(t)); // In ra stack trace của lỗi
            }
        };
        webSocket = client.newWebSocket(request, listener);
    }
    private void updateUI(String text) {
        if (mActivity != null) {mActivity.onDataUpdated(text);}
    }
    public void sendMessage(String message) {
        webSocket.send(message);
    }
    public void closeWebSocket() {
        webSocket.close(1000, "Goodbye, World!");
    }
}
