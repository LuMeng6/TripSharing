package edu.tufts.cs.tripsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/* The screen of welcome page after logging in (the secondary functionality, not completed). */

public class ProfileActivity extends AppCompatActivity {

    private TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvEmail = (TextView) findViewById(R.id.txtemailregistration);
        tvEmail.setText(getIntent().getExtras().getString("Email"));
    }

    public void btnInfo_Click(View v) {
        Intent i = new Intent(ProfileActivity.this, UserInfoActivity.class);
        startActivity(i);
    }
}
