package ma.ws2022.barcodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public final class Api extends {
   private Api(){

   }

   public static String[] getHistory(){
       OkHttpClient client = new OkHttpClient();
       String url = "localhost:3000/item";
       RequestBody body = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
       Request request = new Request.Builder()
               .url(url)
               .post(get)
               .build();

       response = client.newCall(request).enqueue(new Callback() {
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
       return response
   }

   public static void postCode(String code) throws IOException{
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
   }
}

