
package edu.tufts.cs.tripsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import io.fabric.sdk.android.Fabric;

/* The screen of logging in. */

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        txtEmailLogin = (EditText) findViewById(R.id.txtemaillogin);
        txtPwd = (EditText) findViewById(R.id.txtpasswordlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        this.setTitle("Log in");
    }

    public void btnUserLogin_Click(View v) {
        if (( !txtEmailLogin.getText().toString().equals("")) &&
                ( !txtPwd.getText().toString().equals(""))) {
            (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(),
                    txtPwd.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login successful",
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);
                            }
                            else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void btnRegistration_Click(View v) {
        Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

//    public void forceCrash(View view) {
//        throw new RuntimeException("This is a crash");
//    }
    
}
