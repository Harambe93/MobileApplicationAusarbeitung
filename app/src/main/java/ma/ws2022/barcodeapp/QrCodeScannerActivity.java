package ma.ws2022.barcodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
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

import android.widget.ListView;

public class QrCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonBack;
    private Button buttonStart;
    public final String URL = "http://192.168.2.111:3000/items";

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
                builder.setMessage(result.getContents());
                builder.setTitle("Scan Result");
                /*
                builder.setPositiveButton("Scan again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                    }
                });
                */
                builder.setPositiveButton("Add to History", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendJsonPostRequest(result.getContents());
                        /*
                        RequestQueue queue = Volley.newRequestQueue(QrCodeScannerActivity.this);
                        String url = "http://192.168.2.111:3000/items";

                        JsonArrayRequest stringRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONArray>() {
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
                        queue.add(stringRequest);

                         */
                    }
                });

                if(URLUtil.isValidUrl(String.valueOf(Uri.parse(result.getContents())))){
                    builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                            startActivity(intent);
                        }
                    });
                }


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

    private void sendJsonPostRequest(String codeData){

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("data", codeData);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL,
                    jsonParams,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(QrCodeScannerActivity.this, "Erfolgreich hinzugefügt", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(QrCodeScannerActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            Volley.newRequestQueue(getApplicationContext()).
                    add(request);

        } catch(JSONException ex){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(ex.getMessage());
            builder.setTitle("EXCEPTION");
            builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();
        }

    }
}

