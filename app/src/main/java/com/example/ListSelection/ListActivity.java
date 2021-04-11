package com.example.ListSelection;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class ListActivity extends AppCompatActivity{

    private CitiesListView citiesListView;
    private OuterListAdaptor outerListAdaptor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_list_new);
        citiesListView = (CitiesListView) findViewById(R.id.dictionary);
        outerListAdaptor = new OuterListAdaptor(this,citiesListView,(TextView)findViewById(R.id.selectedItemTextView));
        citiesListView.setAdapter(outerListAdaptor);
        outerListAdaptor.setOuterListData(Arrays.asList(OuterList.GetItemColors()));
    }
}

