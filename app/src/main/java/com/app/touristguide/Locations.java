package com.app.touristguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.app.touristguide.Adapters.CDCategoriesAdapter;
import com.app.touristguide.Adapters.LocationsAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class Locations extends AppCompatActivity {

    ArrayList<String> locations;
    ArrayList<String> desc;
    ArrayList<String> image;
    int maxDistance = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        final RecyclerView gv = findViewById(R.id.locgv);
        locations = new ArrayList<>();
        desc = new ArrayList<>();
        image = new ArrayList<>();

        String lat = getIntent().getExtras().getString("lat");
        String lon = getIntent().getExtras().getString("lon");

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please Wait...", true);
        dialog.setCancelable(false);
        dialog.show();

        Ion.with(Locations.this)
                .load("https://www.triposo.com/api/20190906/local_highlights.json?" +
                        "tag_labels=poitype-Sight|sightseeing&max_distance=50000&latitude="+lat+"&longitude="+lon)
                .addHeader("X-Triposo-Account", "O304RC0P")
                .addHeader("X-Triposo-Token", "p03ouu9r2xmw3dgxztjem7ztlro4h4uz")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if(result != null){
                            //Log.e("ionresult", result + "");

                            JsonArray res = result.get("results").getAsJsonArray();
                            JsonArray pois = res.get(0).getAsJsonObject().get("pois").getAsJsonArray();
                            for(int i = 0; i < pois.size(); i++){
                                locations.add(pois.get(i).getAsJsonObject().get("name").getAsString());
                                desc.add(pois.get(i).getAsJsonObject().get("snippet").getAsString());
                                image.add(pois.get(i)
                                        .getAsJsonObject()
                                        .get("images")
                                        .getAsJsonArray()
                                        .get(0)
                                        .getAsJsonObject()
                                        .get("source_url")
                                        .getAsString()
                                );
                            }
                            Log.e("ionresult", locations + "");
                            LocationsAdapter cdc = new LocationsAdapter(Locations.this, locations, desc, image);
                            gv.setLayoutManager(new LinearLayoutManager(Locations.this));
                            gv.setAdapter(cdc);

                            dialog.cancel();

                        }
                        if(e != null){
                            dialog.cancel();
                            Log.e("ion error", e.toString());
                        }
                    }
                });

//        if(locations == null){
//
//            locations.add("Locations");
//            locations.add("Hotels");
//            locations.add("Restaurant");
//            locations.add("Restaurant");
//            locations.add("Restaurant");
//            locations.add("About Us");
//            locations.add("About Us");
//        }


    }
}
