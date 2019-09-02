package com.techmax.datingsoon.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techmax.datingsoon.R;

import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    AuthCredential authCredential;
    String currentUser;

    Toolbar toolbarPasswordToolbar;

    ProgressDialog dialog;

    EditText editTextPasswordOld;
    EditText editTextPasswordNew;
    EditText editTextPasswordCheck;

    Button buttonPasswordUpdate;

    String user_email;
    String user_epass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = firebaseUser.getUid();

        editTextPasswordOld = findViewById(R.id.editTextPasswordOld);
        editTextPasswordNew = findViewById(R.id.editTextPasswordNew);
        editTextPasswordCheck = findViewById(R.id.editTextPasswordCheck);

        buttonPasswordUpdate = findViewById(R.id.buttonPasswordUpdate);

        toolbarPasswordToolbar = findViewById(R.id.toolbarPasswordToolbar);
        setSupportActionBar(toolbarPasswordToolbar);
        getSupportActionBar().setTitle("Password Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPasswordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        user_email = getIntent().getStringExtra("user_email");
        user_epass = getIntent().getStringExtra("user_epass");


        buttonPasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stringPasswordOld = editTextPasswordOld.getText().toString();
                String stringPasswordNew = editTextPasswordNew.getText().toString();
                String stringPasswordCheck = editTextPasswordCheck.getText().toString();


                if (stringPasswordOld.equals(user_epass)) {
                    if (!stringPasswordNew.equals(stringPasswordOld)) {
                        if (stringPasswordNew.length() >= 5) {
                            if (stringPasswordNew.equals(stringPasswordCheck)) {


                                dialog = new ProgressDialog(PasswordActivity.this);
                                dialog.setTitle("Please Wait");
                                dialog.setMessage("Updating Password...");
                                dialog.setCancelable(false);
                                dialog.show();


                                authCredential = EmailAuthProvider.getCredential(user_email, user_epass);

                                firebaseUser.reauthenticate(authCredential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    PasswordUpdate(stringPasswordNew);
                                                }

                                            }
                                        });

                            } else {

                                Toast.makeText(PasswordActivity.this,
                                        "New password and Confirm passwords do not match. please recheck it!",
                                        Toast.LENGTH_SHORT).show();


                            }

                        } else {

                            Toast.makeText(PasswordActivity.this,
                                    "Your new password should be atleast 6 characters. please recheck it!",
                                    Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(PasswordActivity.this,
                                "Your new password and old password are same. please recheck it!",
                                Toast.LENGTH_SHORT).show();


                    }

                } else {

                    Toast.makeText(PasswordActivity.this,
                            "Current password field does not match with your password. please recheck it!",
                            Toast.LENGTH_SHORT).show();


                }
            }
        });


    }

    private void PasswordUpdate(String password) {

        firebaseUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Map<String, Object> mapPassword = new HashMap<>();
                            mapPassword.put("user_epass", password);

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .update(mapPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Intent intent = new Intent(PasswordActivity.this, AccountActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);

                                                Toast.makeText(PasswordActivity.this,
                                                        "Password Updated Succesfully!",
                                                        Toast.LENGTH_SHORT).show();

                                                dialog.dismiss();

                                            }
                                        }
                                    });


                        } else {

                            Toast.makeText(PasswordActivity.this,
                                    "Password update failed! Please try again later!",
                                    Toast.LENGTH_SHORT).show();

                            dialog.dismiss();


                        }
                    }

                });


    }
}
