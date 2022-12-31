package ma.ws2022.barcodeapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Button buttonScan;
    String historyQrCodes[] = {"Test eins", "Test zwei", "Test drei", "Test vier", "Test fünf", "Test sechs"};
    ListView listView;
    //Intent intent = getIntent();
    //String[] historyQrCodes = intent.getStringArrayExtra("qr_codes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonScan = findViewById(R.id.buttonScan);
        listView = findViewById(R.id.costumListView);

        CostumClassAdapter costumClassAdapter = new CostumClassAdapter(getApplicationContext(),historyQrCodes);
        listView.setAdapter(costumClassAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("COSTUM_LIST_VIEW", "CLICK ON ITEM NUBER ::" + i );
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QrCodeScannerActivity.class);
                startActivity(intent);
            }

        });
    }
}

