package com.example.sencerity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;

import java.io.IOException;

public class BarcodeScannerActivity extends AppCompatActivity {

    private SurfaceView cameraPreview;
    private BarcodeDetector detector;
    private CameraSource camSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        cameraPreview = findViewById(R.id.camera_preview);
        createCameraSource();

    }


    private void createCameraSource() {
        detector = new BarcodeDetector.Builder(this).build();
        camSource = new CameraSource.Builder(this, detector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();


        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
               if (ActivityCompat.checkSelfPermission(BarcodeScannerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //   ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                   Toast.makeText(BarcodeScannerActivity.this, "Enable camera permission in settings to use this feature.", Toast.LENGTH_LONG).show();

                   return;
                        }

                try {
                    camSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()>0){
                    Intent intent = new Intent();
                    intent.putExtra("barcode",barcodes.valueAt(0)); //get latest barcode
                    setResult(CommonStatusCodes.SUCCESS,intent);
                    finish();
                }
            }
        });


    }


}








//-------------------------------------------------------------
//Previous code for barcode scanner from image uploaded only
/*
        public FirebaseVisionBarcodeDetector detector;
        public ImageView barcodeImageVw;
        public Bitmap bitmap;
        public FirebaseVisionImage image;
        public static final String ERR="barcode_failure";
        public TextView barcodeTextView;
    */

/*
    public void barcodeManager() {

        barcodeImageVw = findViewById(R.id.barcodeImageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.barcode);
        barcodeImageVw.setImageBitmap(bitmap);

        image = FirebaseVisionImage.fromBitmap(bitmap);

        barcodeTextView=findViewById(R.id.textViewBarcodeInfo);

        detector = FirebaseVision.getInstance().getVisionBarcodeDetector();

        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        for(FirebaseVisionBarcode barcode: barcodes) {
                            String rawValue = barcode.getRawValue();
                            int valueType = barcode.getValueType();
                            barcodeTextView.append(rawValue);
                            barcodeTextView.append("======");
                            barcodeTextView.append(String.valueOf(valueType));



                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(ERR, "barcode scanner failed!", e);
                    }
                });

    }
    */


