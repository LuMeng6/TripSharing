package edu.tufts.cs.tripsharing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/* The screen of the list of people who want to go to one place. */

public class ShowDetail extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference Users;
    Context context;
    String place_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        place_id = getIntent().getExtras().getString("PlaceId");
        setContentView(R.layout.activity_show_detail);
        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        final DatabaseReference user = myRef.child("users").
                child(firebaseAuth.getCurrentUser().getUid().toString());
        Users = myRef.child("Place").child(place_id).child("users");
        this.setTitle("Find Your Partner");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> userNames = new ArrayList<>();
                List<String> userContact = new ArrayList<>();
                for (DataSnapshot user : dataSnapshot.child("Place").
                        child(place_id).child("users").getChildren()) {
                    String userId = user.getKey().toString();
                    String contact;
                    if (dataSnapshot.child("users").child(userId).child("contact").getValue() == null)
                        contact = "Call My Name";
                    else
                        contact = dataSnapshot.child("users").child(userId).child("contact").
                                getValue().toString();
                    userNames.add(user.getValue().toString());
                    userContact.add(contact);
                }
                String[] users = new String[userNames.size()];
                String[] contacts = new String[userContact.size()];
                for(int i = 0; i < users.length; i++) {
                    users[i] = userNames.get(i);
                    contacts[i] = userContact.get(i);
                }
                CustomListAdapter2 listAdapter = new CustomListAdapter2(context, users, contacts);
                ListView userListView = (ListView) findViewById(R.id.userListView);
                userListView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("name").getValue() == null)
                            Users.child(firebaseAuth.getCurrentUser().getUid().toString()).
                                    setValue("Anonymity");
                        else
                            Users.child(firebaseAuth.getCurrentUser().getUid().toString()).
                                    setValue(dataSnapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    public void alert(String s) {
        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void back_to_search(View v) {
        Intent i = new Intent(ShowDetail.this, MainActivity.class);
        startActivity(i);
    }
}
