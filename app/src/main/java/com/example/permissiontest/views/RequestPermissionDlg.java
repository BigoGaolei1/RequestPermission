package com.example.permissiontest.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.permissiontest.R;

import java.util.Timer;
import java.util.TimerTask;

public class RequestPermissionDlg extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "RequestPermissionDlg";

    private Dialog mDialog;
    private TextView mBackTv;
    private TextView mAllowCameraTv;
    private TextView mAllowMicroPhoneTv;
    private TextView mAllowStorageTv;
    private PermissionsReqResultListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.Dialog_Fullscreen);
        mDialog.setContentView(R.layout.layout_record_permission);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        return mDialog;
    }

    public void setItemClickListener(PermissionsReqResultListener listener) {
        mListener = listener;
    }

    public void show(AppCompatActivity activity) {
        if (activity == null || mDialog.isShowing()) {
            return;
        }
        RequestPermissionDlg requestPermissionDlg = new RequestPermissionDlg();
        requestPermissionDlg.show(activity.getSupportFragmentManager(), TAG);
    }

    private void init() {
        mBackTv = mDialog.findViewById(R.id.tv_close);
        mAllowCameraTv = mDialog.findViewById(R.id.tv_allow_camera);
        mAllowMicroPhoneTv = mDialog.findViewById(R.id.tv_allow_microphone);
        mAllowStorageTv = mDialog.findViewById(R.id.tv_allow_storage);

        mBackTv.setOnClickListener(this);
        mAllowCameraTv.setOnClickListener(this);
        mAllowMicroPhoneTv.setOnClickListener(this);
        mAllowStorageTv.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_close :
                dismiss();
                break;
            case R.id.tv_allow_camera:
                handleCameraClick();
                break;
            case R.id.tv_allow_microphone:
                handleMicroPhoneClick();
                break;
            case R.id.tv_allow_storage:
                handleStorageClick();
                break;
            default:
        }
    }

    private void handleCameraClick() {
        needPermission(getActivity(), 113,
                new String[] {
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
    }

    private void handleMicroPhoneClick() {
        Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
        final Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", packageURI);
        intent.putExtra("extra_pkgname", getActivity().getPackageName());
        startActivity(intent);
    }

    private void handleStorageClick() {

    }

    private void needPermission(Activity activity, int requestCode, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 113) {

        }
        isAllPermissionsGranted(requestCode, permissions, grantResults);
        if (mListener != null) {
            mListener.CheckPermissions(requestCode, permissions, grantResults);
        }
    }

    private boolean isAllPermissionsGranted(int requestCode, String[] permissions, int[] grantResults) {
        for (int i=0;i<grantResults.length;i++) {

        }

        return false;
    }

    interface PermissionsReqResultListener {
        void CheckPermissions(int requestCode, String[] permissions, int[] grantResults);
    }
}
