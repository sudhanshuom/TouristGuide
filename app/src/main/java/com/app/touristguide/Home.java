package com.app.touristguide;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et = findViewById(R.id.cityet);

        String apiKey = getString(R.string.api_key);
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
//
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.FULLSCREEN, fields)
//                        .build(getApplicationContext());
//
//                startActivityForResult(intent, 1000);

                Intent in = new Intent(Home.this, CityDetails.class);

                in.putExtra("lat", "26.836961958047908");
                in.putExtra("lon", "80.94349543356367");
                in.putExtra("name", "Lucknow");

                startActivity(in);

            }
        });

//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("Selected", "Place: " + place.getName() + ", " + place.getLatLng());
//            }
//
//            @Override
//            public void onError(@NonNull Status status) {
//                Log.i("Error", "An error occurred: " + status);
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * This is called when the user has selected a location from the prediction box.
         * data contatins the place, extracted by getPlaceFromIntent() method.
         * use this place to get address and display
         * also get Latitude and longitude and put it into the map fragment LatLng holder.
         */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("result", "selected place: " + place.getName() + ", " + place.getLatLng());
                et.setText(place.getName());

                if(place.getLatLng() != null) {
                    Intent in = new Intent(Home.this, CityDetails.class);

                    in.putExtra("lat", place.getLatLng().latitude + "");
                    in.putExtra("lon", place.getLatLng().longitude + "");
                    in.putExtra("name", place.getName());

                    startActivity(in);
                }else{
                    Toast.makeText(Home.this, "Some Error occured", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
