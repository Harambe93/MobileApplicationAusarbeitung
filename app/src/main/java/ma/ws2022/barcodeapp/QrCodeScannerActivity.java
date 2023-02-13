package ma.ws2022.barcodeapp;

<<<<<<< Updated upstream
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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

<<<<<<< Updated upstream
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
=======
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class QrCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {
>>>>>>> Stashed changes
    private Button buttonBack;
    private Button buttoStart;


    @SuppressLint("MissingInflatedId")
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
                String Text = "GET IT TO WORK";
                addStuff(Text);
                Intent intent = new Intent(QrCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttoStart = findViewById(R.id.buttonStart);

        buttoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCamera();
            }
        });



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
<<<<<<< Updated upstream
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
=======
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Add to Database", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Text = "I hope this works";
                        addStuff(Text);
>>>>>>> Stashed changes
                    }
                });
            } else {
                ActivityCompat.requestPermissions(QrCodeScannerActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

<<<<<<< Updated upstream
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
=======
    /*
    public void post(String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "localhost:3000/items";
        RequestBody body = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
>>>>>>> Stashed changes
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

     */
    public void addStuff(String data){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Void> call = api.sendData(jsonObject.toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully added data to the Database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error getting data from the database" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });

    }
}

