package com.example.permissiontest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.permissiontest.views.RequestPermissionsDlg;

public class MainActivity extends AppCompatActivity {

    private RequestPermissionsDlg mRequestPermissionDlg;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.record_request_permissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestPermissionDlg = new RequestPermissionsDlg(MainActivity.this);
                mRequestPermissionDlg.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
    }
}
