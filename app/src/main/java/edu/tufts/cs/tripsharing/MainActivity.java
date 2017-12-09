package edu.tufts.cs.tripsharing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/* The screen of searching. */

public class MainActivity extends AppCompatActivity {

    private String placeId = "";

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
                // Get Info about the selected place.
                placeId = place.getId().toString();
            }

            @Override
            public void onError(Status status) {
                // Handle the error.
            }
        });
    }

    public void onClickSearch(View v) {
        Intent i = new Intent(MainActivity.this, Info.class);
        if (placeId != "") {
            i.putExtra("PlaceId", placeId);
            startActivity(i);
        }
    }
}
