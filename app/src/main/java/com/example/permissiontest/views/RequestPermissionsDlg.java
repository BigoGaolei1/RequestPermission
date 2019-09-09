package com.example.permissiontest.views;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import com.example.permissiontest.R;

public class RequestPermissionsDlg extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "RequestPermissionDlg";

    private AppCompatActivity mActivity;
    private Dialog mDialog;
    private TextView mBackTv;
    private ImageView mAllowCameraIv;
    private TextView mAllowCameraTv;
    private ImageView mAllowAudioIv;
    private TextView mAllowAudioTv;
    private ImageView mAllowStorageIv;
    private TextView mAllowStorageTv;

    private boolean mHasCameraPermission = false;
    private boolean mHasAudioPermission = false;
    private boolean mHasStoragePermission = false;
    private boolean mHasPaused = false;

    public RequestPermissionsDlg(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = new Dialog(mActivity, R.style.Dialog_Fullscreen);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_record_permission);
        init();
        return mDialog;
    }

    public void show() {
        if (mActivity == null) {
            return;
        }
        show(mActivity.getSupportFragmentManager(), TAG);
    }

    private void init() {
        mBackTv = mDialog.findViewById(R.id.tv_close);
        mAllowCameraIv = mDialog.findViewById(R.id.iv_camera);
        mAllowCameraTv = mDialog.findViewById(R.id.tv_allow_camera);
        mAllowAudioIv = mDialog.findViewById(R.id.iv_microphone);
        mAllowAudioTv = mDialog.findViewById(R.id.tv_allow_microphone);
        mAllowStorageIv = mDialog.findViewById(R.id.iv_storage);
        mAllowStorageTv = mDialog.findViewById(R.id.tv_allow_storage);

        mBackTv.setOnClickListener(this);
        mAllowCameraTv.setOnClickListener(this);
        mAllowAudioTv.setOnClickListener(this);
        mAllowStorageTv.setOnClickListener(this);

        updateViews(mActivity);

        needPermission(113,
                new String[] {
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");

        for (int i = 0; i < permissions.length; ++i) {
            Log.d(TAG, "checkPermissions: " + permissions[i]);
        }

        for (int i = 0; i < grantResults.length; ++i) {
            Log.d(TAG, "grantResults: " + grantResults[i]);
        }
        updateViews(mActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHasPaused) {
            mHasPaused = false;
            updateViews(mActivity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHasPaused = true;
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
        if (!mHasCameraPermission
                && !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
            gotoAppSetting();
        } else {
            needPermission(113, new String[] {android.Manifest.permission.CAMERA});
        }
    }

    private void handleMicroPhoneClick() {
        if (!mHasAudioPermission
                && !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.RECORD_AUDIO)) {
            gotoAppSetting();
        } else {
            needPermission(113, new String[] {Manifest.permission.RECORD_AUDIO});
        }
    }

    private void handleStorageClick() {
        if (!mHasStoragePermission
                && !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            gotoAppSetting();
        } else {
            needPermission(113, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    private void needPermission(int requestCode, String[] permissions) {
        requestPermissions(permissions, requestCode);
    }

    private void updateViews(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mHasCameraPermission = true;
            mAllowCameraIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_permissions_completion));
            mAllowCameraTv.setAlpha(0.5f);
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            mHasAudioPermission = true;
            mAllowAudioIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_permissions_completion));
            mAllowAudioTv.setAlpha(0.5f);
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mHasStoragePermission = true;
            mAllowStorageIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_permissions_completion));
            mAllowStorageTv.setAlpha(0.5f);
        }

        if (mHasCameraPermission && mHasAudioPermission && mHasStoragePermission) {
            dismiss();
        }
    }

    private void gotoAppSetting() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(android.net.Uri.fromParts("package", getActivity().getPackageName(), null));
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException an) {
            Log.w(TAG, "startActivity error.", an);
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException e) {
                Log.e(TAG, "startActivity error " + e.getMessage());
            }
        }
    }
}