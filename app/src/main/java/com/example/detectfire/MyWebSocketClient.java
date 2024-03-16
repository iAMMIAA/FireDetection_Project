package com.example.detectfire;

import android.util.Log;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MyWebSocketClient extends WebSocketListener {
    private static final String TAG = "MyWebSocketClient";
    private WebSocket webSocket;

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        // Khi kết nối WebSocket được mở
        Log.d("IAMMIA", "WebSocket Connection Opened");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // Khi nhận được dữ liệu từ server
        Log.d("IAMMIA", "Received message: " + text);
        // Xử lý dữ liệu ở đây
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        // Khi nhận được dữ liệu từ server dưới dạng byte
        Log.d("IAMMIA", "Received bytes: " + bytes.hex());
        // Xử lý dữ liệu ở đây
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
        // Khi có lỗi xảy ra
        Log.d("IAMMIA", "WebSocket Connection Failed: " + t.getMessage());
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        // Khi kết nối WebSocket được đóng
        Log.d("IAMMIA", "WebSocket Connection Closed: " + code + ", " + reason);
    }

    // Các phương thức khác để quản lý kết nối WebSocket
}
