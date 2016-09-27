package com.mycompany.myfirstglapp;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 6/09/2016.
 */
public class ListPlayerActivity extends ListActivity {

    Button button2;

    static final String[] PLAYER_LIST =
            { "Vinod", "Arun", "Haju", "Azhar", "Rajesh", "Simman"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        setListAdapter(new PlayerArrayAdapter(this, PLAYER_LIST));
        View headerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, null, false);
        getListView().addHeaderView(headerView);
        //listView.setAdapter(null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });



    }


}
