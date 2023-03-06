package ma.ws2022.barcodeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public final class Api {
    public static final String URL = "http://192.168.2.111:3000/items/last5";
    private Api(){}
    public static JSONArray json = null;
    public static String[] arrayHist;

   public static String[] getHistory(AppCompatActivity activity){
       RequestQueue queue = Volley.newRequestQueue(activity);

       JsonArrayRequest stringRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, URL, null, new com.android.volley.Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               Toast.makeText(activity, response.toString(), Toast.LENGTH_LONG).show();
               json = response;
           }
       }, new com.android.volley.Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(activity, "Oops" + error.toString(), Toast.LENGTH_LONG).show();
           }

       });
       queue.add(stringRequest);
       for (int i = 0; i<5; i++) {
           try {
               arrayHist[i] = json.getString(i);
           }catch (JSONException e){
               Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
           }
       }
       return arrayHist;
   }

    private static void sendJsonPostRequest(String codeData,AppCompatActivity activity ){

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
                            Toast.makeText(activity, "Erfolgreich hinzugefügt", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            Volley.newRequestQueue(activity.getApplicationContext()).
                    add(request);

        } catch(JSONException ex){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(ex.getMessage());
            builder.setTitle("EXCEPTION");
            builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    activity.finish();
                }
            });
            builder.show();
        }

    }
}

