package ma.ws2022.barcodeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class CostumClassAdapter extends BaseAdapter {

    private String [] historyQrCodes;
    private Context context;
    LayoutInflater inflater;




    public CostumClassAdapter(Context ctx, String [] historyQrCodes){
        this.context = ctx;
        this.historyQrCodes = historyQrCodes;
        inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return historyQrCodes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_costum_listview, null);
        TextView textView = view.findViewById(R.id.textViewList);
        textView.setText(historyQrCodes[i]);

        return view;
    }
}
