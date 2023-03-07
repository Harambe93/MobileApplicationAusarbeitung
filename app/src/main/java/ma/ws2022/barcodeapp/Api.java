package ma.ws2022.barcodeapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;
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
    public static final String URL_GET = "http://192.168.2.111:3000/items/last5";
    public static final String URL_POST = "http://192.168.2.111:3000/items/";
    private Api(){}

    public static void fillTextView(TextView textView, Activity activity){
        String[] arrayHist = new String[0];
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_GET, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String name;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject row = null;
                            try {
                                row = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                name = row.getString("data");
                                textView.append(name+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });
        queue.add(request);
    }

    public static void sendJsonPostRequest(String codeData,Activity activity ){

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("data", codeData);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    URL_POST,
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
