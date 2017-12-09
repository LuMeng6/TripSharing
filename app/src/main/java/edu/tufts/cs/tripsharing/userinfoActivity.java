package edu.tufts.cs.tripsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/* The screen of user's information when signing up. */

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "AddToDatabase";

    private Button btnSave;
    private EditText uName,uContact;
    private String userID;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        this.setTitle("Information");

        btnSave = (Button) findViewById(R.id.save);
        uName = (EditText) findViewById(R.id.username);
        uContact = (EditText) findViewById(R.id.usercontact);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be usable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }
        };

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Submit pressed.");
                String name = uName.getText().toString();
                String contact = uContact.getText().toString();

                //handle the exception if the EditText fields are null
                if(!name.equals("") && !contact.equals("") ){
                    UserInformation userInformation = new UserInformation(name,contact);
                    myRef.child("users").child(userID).setValue(userInformation);
                    toastMessage("User's information has been saved.");
                    uName.setText("");
                    uContact.setText("");
                }else{
                    toastMessage("Please fill out all the fields");
                }
                if(!name.equals("") && !contact.equals("") ) {
                    Intent i = new Intent(UserInfoActivity.this, MainActivity.class);
                    startActivity(i);}
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
