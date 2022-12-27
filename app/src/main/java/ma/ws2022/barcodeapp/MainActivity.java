package ma.ws2022.barcodeapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private SurfaceView surfaceView;
    private Button buttonScan;
    private TextView textView;
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceView);
        buttonScan = findViewById(R.id.buttonScan);
        textView = findViewById(R.id.textView2);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }

            private void startCamera() {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                            @Override
                            public void release() {
                                Toast.makeText(MainActivity.this, "To prevent memory leaks, release the barcode detector", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void receiveDetections(Detector.Detections<Barcode> detections) {
                                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                                if (barcodes.size() != 0) {
                                    textView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView.setText(barcodes.valueAt(0).displayValue);
                                            Barcode qrCode = barcodes.valueAt(0);
                                            String qrCodeContent = qrCode.rawValue;
                                            Toast.makeText(MainActivity.this, qrCodeContent, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

    }

}