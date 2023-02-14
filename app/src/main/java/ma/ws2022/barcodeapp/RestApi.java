package ma.ws2022.barcodeapp;

import java.util.HashMap;
import java.util.Map;

public class RestApi {
    Map<Integer, String> dictionary = new HashMap<Integer, String>();
    public RestApi(){
        dictionary.put(244, "Apples");
        dictionary.put(345, "Bananas");
        dictionary.put(75, "Honey");
        dictionary.put(134, "Cornflakes");
        dictionary.put(834, "Milk");
        dictionary.put(247, "Bread");
        dictionary.put(127, "Flour");
        dictionary.put(645, "Pasta");
        dictionary.put(845, "Meat");
    }
    public String handleBarcode(String barcode){
        int keyValue = Integer.parseInt(barcode);
        if(dictionary.containsKey(keyValue)){
            return "Article: " + dictionary.get(keyValue);
        }
        return "There is no Article that matched this Code.";
    }
}
