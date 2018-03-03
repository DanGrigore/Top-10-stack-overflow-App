package com.example.grigo.ab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView lst;
    String[] names = {"Dan", "Andrei", "Denisa", "Alexandra", "George", "Alexandra doamna noastra Iisus"};
    Integer[] imageId = {R.drawable.poza2016,R.drawable.poza2016,R.drawable.poza2016,R.drawable.poza2016,R.drawable.poza2016,R.drawable.poza2016};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = (ListView) findViewById(R.id.listview);
        CustomListView customListView = new CustomListView(this, names, imageId);
        lst.setAdapter(customListView);
    }

}
