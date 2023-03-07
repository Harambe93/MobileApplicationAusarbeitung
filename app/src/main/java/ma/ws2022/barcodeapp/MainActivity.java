package ma.ws2022.barcodeapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button buttonScan;
    //private TextView historyTextfield;
    private TextView[] historyArray = new TextView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        historyArray[0] = findViewById(R.id.historyView1);
        historyArray[1] = findViewById(R.id.historyView2);
        historyArray[2] = findViewById(R.id.historyView3);
        historyArray[3] = findViewById(R.id.historyView4);
        historyArray[4] = findViewById(R.id.historyView5);
        buttonScan = findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QrCodeScannerActivity.class);
                startActivity(intent);
            }
        });
        Api.fillTextView(historyArray, MainActivity.this);
    }
}

