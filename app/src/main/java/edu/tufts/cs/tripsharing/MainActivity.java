package edu.tufts.cs.tripsharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class MainActivity extends AppCompatActivity {

    private String placeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        this.setTitle("TripSharing");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO:Get info about the selected place.
                placeId = place.getId().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO:Handle the error.
            }
        });

    }
    public void onClickSearch(View v) {
        // in this function you need get the place id from Meng lu
        // transmit this place id to info activity

        // You need transfer the bitmap you get into the info class

        Intent i = new Intent(MainActivity.this, info.class);
        i.putExtra("PlaceId", placeId);
        //Ask Menglu how to pass the bitmaps she get in this activity to info activity


        startActivity(i);
    }

}
