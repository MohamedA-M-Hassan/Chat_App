package com.example.mamohamed.boogychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private AutoCompleteTextView mEmailView;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPassword = (EditText) findViewById(R.id.login_password);
       // Button loginButton = (Button) findViewById(R.id.login_sign_in_button);


        mPassword.setOnEditorActionListener((new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login_password || actionId == EditorInfo.IME_NULL){
                    attemptLogin();
                    return true;

                }
                return false;
            }
        }));


        mAuth = FirebaseAuth.getInstance();
    }

    // executed when login button pressed
    public void signInExistingUser(View v){

    attemptLogin();

    }

    // executed when rigester button pressed
    public void registerNewUser(View v){
        Intent intent = new Intent(this, com.example.mamohamed.boogychat.Register.class);
        finish();
        startActivity(intent);
    }


    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPassword.getText().toString();

        if (email.isEmpty())

            if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("FlashChat", "signInWithEmail() onComplete: " + task.isSuccessful());


                if (!task.isSuccessful()) {
                    Log.d("FlashChat", "Problem signing in: " + task.getException());
                    showErrorDialog("There was a problem signing in");
                } else {
                    Intent intent = new Intent(LoginActivity.this, BooGyMainChat.class);
                    finish();
                    startActivity(intent);
                }


            }
        });

    }




        // TODO: Show error on screen with an alert dialog
        private void showErrorDialog(String message) {

            new AlertDialog.Builder(this)
                    .setTitle("Oops")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


}