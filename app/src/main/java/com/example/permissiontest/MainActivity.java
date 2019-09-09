package com.example.permissiontest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.permissiontest.views.RequestPermissionsDlg;

public class MainActivity extends AppCompatActivity {

    private RequestPermissionsDlg mRequestPermissionDlg;

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
}
