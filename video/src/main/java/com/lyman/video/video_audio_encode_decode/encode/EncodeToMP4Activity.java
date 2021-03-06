package com.lyman.video.video_audio_encode_decode.encode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lyman.video.R;
import com.lyman.video.video_encode_decode.encode.camera.SimpleCameraPreview;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class EncodeToMP4Activity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_RECORD_AUDIO = 2;
    private CameraPreview mPreview;
    private Button mRecordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode_yuvto_h2642);
        mRecordBtn = (Button) findViewById(R.id.record_btn);
        requestPermissions();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            initCameraView();
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCameraView();
                } else {
                    Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
            case REQUEST_RECORD_AUDIO:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "权限请求失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    private void initCameraView() {
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }


    public void toggleVideo(View view) {
        if (mPreview.toggleVideo()) {
            mRecordBtn.setText("停止录制视频");
        } else {
            mRecordBtn.setText("开始录制视频");
        }
    }
}
