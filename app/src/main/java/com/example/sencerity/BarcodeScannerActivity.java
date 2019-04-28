package com.example.sencerity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.util.List;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class BarcodeScannerActivity extends AppCompatActivity {

    public FirebaseVisionBarcodeDetector detector;
    public ImageView barcodeImageVw;
    public Bitmap bitmap;
    public FirebaseVisionImage image;
    public static final String ERR="barcode_failure";
    public TextView barcodeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        barcodeManager();
    }
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

}
