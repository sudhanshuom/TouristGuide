package com.app.touristguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.app.touristguide.Adapters.CDCategoriesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class CityDetails extends AppCompatActivity {

    ArrayList<String> cat;
    ArrayList<Drawable> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        TextView cname = findViewById(R.id.cityname);
        final String lat =  getIntent().getExtras().getString("lat");
        final String lon = getIntent().getExtras().getString("lon");
        String name = getIntent().getExtras().getString("name");

        cname.setText(name);

        if(cat == null){
            cat = new ArrayList<>();
            images = new ArrayList<>();
            cat.add("Locations");
            cat.add("Hotels/Restaurant");

            images.add(getDrawable(R.drawable.locations));
            images.add(getDrawable(R.drawable.hotrest));
        }

        GridView gv = findViewById(R.id.categoriesgv);

        CDCategoriesAdapter cdCategoriesAdapter = new CDCategoriesAdapter(CityDetails.this, cat, images);

        gv.setAdapter(cdCategoriesAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent in = new Intent(CityDetails.this, Locations.class);
                        in.putExtra("lat", lat + "");
                        in.putExtra("lon", lon + "");
                        startActivity(in);
                        break;
                    case 1:
                        Intent inn = new Intent(CityDetails.this, Hotels.class);
                        inn.putExtra("lat", lat + "");
                        inn.putExtra("lon", lon + "");
                        startActivity(inn);
                        break;
                    case 2:
                        break;
                    default:
                }
            }
        });

    }
}
