package com.example.permissiontest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.permissiontest.views.RequestPermissionDlg;

public class MainActivity extends AppCompatActivity {

    private RequestPermissionDlg mRequestPermissionDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.record_request_permissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestPermissionDlg = new RequestPermissionDlg(MainActivity.this);
                mRequestPermissionDlg.show();
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        mRequestPermissionDlg.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
