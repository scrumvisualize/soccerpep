package com.mycompany.myfirstglapp;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 6/09/2016.
 */
public class ListPlayerActivity extends ListActivity {

    static final String[] PLAYER_LIST =
            { "Vinod", "Arun", "Haju", "Azhar", "Rajesh", "Simman"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new PlayerArrayAdapter(this, PLAYER_LIST));
        View headerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, null, false);
        getListView().addHeaderView(headerView);


    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextView textView = (TextView) findViewById(R.id.label);
        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position-1);
        //String text = textView.getText().toString();
        Toast.makeText(getApplicationContext(), selectedValue, Toast.LENGTH_SHORT).show();


    }




}
