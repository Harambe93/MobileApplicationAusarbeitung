package ma.ws2022.barcodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.android.volley.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.widget.ListView;

public class QrCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonBack;
    private Button buttonStart;

    //String[] historyQrCodes;
    //ListView listView;


    RestApi api = new RestApi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_scanner);

        buttonBack = findViewById(R.id.buttonBack);
        buttonStart = findViewById(R.id.buttonStart);

        //listView = findViewById(R.id.costumListView);
        //CostumClassAdapter costumClassAdapter = new CostumClassAdapter(getApplicationContext(),historyQrCodes);
        //listView.setAdapter(costumClassAdapter);
        //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //@Override
            //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("COSTUM_LIST_VIEW", "CLICK ON ITEM NUBER ::" + i );
            //}
        //});



        buttonStart.setOnClickListener(this);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QrCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setMessage(api.handleBarcode(result.getContents()));
                builder.setMessage(result.getContents());
                builder.setTitle("Scan Result");
                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                });
                /*
                builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                        startActivity(intent);
                    }
                });
                */

                builder.setNeutralButton("Add to History", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // historyQrCodes[0] = result.getContents();
                        RequestQueue queue = Volley.newRequestQueue(QrCodeScannerActivity.this);
                        String url = "http://192.168.2.111:3000/items";

                        //String url = "https://api.wheretheiss.at/v1/satellites/25544";
                        JsonArrayRequest stringRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Toast.makeText(QrCodeScannerActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                            }
                                }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(QrCodeScannerActivity.this, "Oops" + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

// Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }
                });


                builder.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void post(String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "localhost:3000/items";
        RequestBody body = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error getting data from the database", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle success
                String responseData = response.body().string();
                Toast.makeText(getApplicationContext(), responseData, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

