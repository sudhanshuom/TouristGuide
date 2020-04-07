package com.app.touristguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.app.touristguide.Adapters.HotelsAdapter;
import com.app.touristguide.Adapters.LocationsAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class Hotels extends AppCompatActivity {

    ArrayList<String> locations;
    ArrayList<String> star;
    ArrayList<String> description;
    ArrayList<String> distance;
    int maxDistance = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        final GridView gv = findViewById(R.id.hotgv);
        locations = new ArrayList<>();
        star = new ArrayList<>();
        description = new ArrayList<>();
        distance = new ArrayList<>();

        String lat = getIntent().getExtras().getString("lat");
        String lon = getIntent().getExtras().getString("lon");

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Please Wait...", true);
        dialog.setCancelable(false);
        dialog.show();

        Ion.with(Hotels.this)
                .load("https://www.triposo.com/api/20190906/local_highlights.json?" +
                        "tag_labels=hotels|poitype-Hotel_casino|poitype-Restaurant&max_distance=50000&latitude="+lat+"&longitude="+lon)
                .addHeader("X-Triposo-Account", "O304RC0P")
                .addHeader("X-Triposo-Token", "p03ouu9r2xmw3dgxztjem7ztlro4h4uz")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if(result != null){
                            Log.e("ionresult", result + "");

                            JsonArray res = result.get("results").getAsJsonArray();
                            JsonArray pois = res.get(0).getAsJsonObject().get("pois").getAsJsonArray();
                            for(int i = 0; i < pois.size(); i++){
                                locations.add(pois.get(i).getAsJsonObject().get("name").getAsString());
                                description.add(pois.get(i).getAsJsonObject().get("snippet").getAsString());
                                distance.add(String.format("%.1f",
                                            Double.parseDouble(pois.get(i).getAsJsonObject().get("distance").getAsString()) / (double) 1000));
                                String score = pois.get(i).getAsJsonObject().get("score").getAsString();
                                String sc = "";
                                int len = 0;
                                for(char c : score.toCharArray()){
                                    if(len >= 3)
                                        break;

                                    sc += c+"";
                                    len++;
                                }
                                star.add(sc);
                            }
                            HotelsAdapter cdc = new HotelsAdapter(Hotels.this, locations, star, description, distance);

                            gv.setAdapter(cdc);

                            dialog.cancel();

                        }
                        if(e != null){
                            dialog.cancel();
                            Log.e("ion error", e.toString());
                        }
                    }
                });
    }
}
