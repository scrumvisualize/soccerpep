package com.mycompany.myfirstglapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 6/09/2016.
 */
public class PlayerArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public PlayerArrayAdapter(Context context, String[] values) {
        super(context, R.layout.list_players, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_players, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("Vinod")) {
            imageView.setImageResource(R.drawable.vinod_1);
        } else if (s.equals("Arun")) {
            imageView.setImageResource(R.drawable.arun_2);
        } else if (s.equals("Haju")) {
            imageView.setImageResource(R.drawable.haju_3);
        } else if (s.equals("Azhar")) {
            imageView.setImageResource(R.drawable.azhar_4);
        } else if (s.equals("Rajesh")) {
            imageView.setImageResource(R.drawable.rajesh_5);
        } else if (s.equals("Simman")) {
            imageView.setImageResource(R.drawable.simman_6);
        }else {
            imageView.setImageResource(R.drawable.pep);
        }

        return rowView;
    }
}