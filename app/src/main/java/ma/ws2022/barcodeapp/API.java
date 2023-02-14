package ma.ws2022.barcodeapp;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {
    @FormUrlEncoded
    @POST("/items")
    Call<Void> sendData(
            @Field("data") String data
    );
}
