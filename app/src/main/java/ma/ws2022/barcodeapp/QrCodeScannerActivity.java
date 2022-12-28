package ma.ws2022.barcodeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QrCodeScannerActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private TextView textView;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_scanner);

        surfaceView = findViewById(R.id.surfaceView);
        textView = findViewById(R.id.textView2);
        buttonBack = findViewById(R.id.buttonBack);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480)
                .build();


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QrCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        startCamera();


        }

    private void startCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(QrCodeScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(surfaceView.getHolder());
                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {
                        Toast.makeText(QrCodeScannerActivity.this, "To prevent memory leaks, release the barcode detector", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(QrCodeScannerActivity.this, qrCodeContent, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            } else {
                ActivityCompat.requestPermissions(QrCodeScannerActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

