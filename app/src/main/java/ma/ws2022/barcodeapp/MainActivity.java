package ma.ws2022.barcodeapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.bson.types.ObjectId;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

public class MainActivity extends AppCompatActivity {

    private Button buttonScan;
    String historyQrCodes[] = {"Test eins", "Test zwei", "Test drei", "Test vier", "Test fünf", "Test sechs"};
    ListView listView;
    //Intent intent = getIntent();
    //String[] historyQrCodes = intent.getStringArrayExtra("qr_codes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Realm.init(this);
        //MongoDB AppID: mystitchapp-uhhvr
        AppConfiguration appConfig = new AppConfiguration.Builder("mystitchapp-uhhvr").build();
        App app = new App(appConfig);
        app.login(Credentials.anonymous());

        SyncConfiguration partitionBasedConfig = SyncConfiguration.defaultConfig(app.currentUser(), "Clifford");
        Realm realm = Realm.getInstance(partitionBasedConfig);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog dog = new Dog();
                dog.setId(new ObjectId());
                dog.setName("Clifford");
                dog.setAge(String.valueOf(10));
                realm.insert(dog);
            }
        });

        RealmResults<Dog> dogs = realm.where(Dog.class)
                .equalTo("name", "Clifford")
                .greaterThan("age", 5)
                .findAll();


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

