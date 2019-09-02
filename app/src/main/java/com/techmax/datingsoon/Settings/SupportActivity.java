package com.techmax.datingsoon.Settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techmax.datingsoon.R;

import java.util.HashMap;
import java.util.Map;

public class SupportActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String currentUser;
    String profileUser;

    CheckBox checkBoxSupportDifficult;
    CheckBox checkBoxSupportFriends;
    CheckBox checkBoxSupportFreezes;
    CheckBox checkBoxSupportNotify;
    CheckBox checkBoxSupportEnjoy;
    EditText editTextSupportOther;
    Button buttonSupportButton;

    Toolbar toolbarSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbarSupport = findViewById(R.id.toolbarSupport);
        setSupportActionBar(toolbarSupport);
        getSupportActionBar().setTitle("Neep Support");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarSupport.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        currentUser = firebaseUser.getUid();
        profileUser = getIntent().getStringExtra("user_uid");


        checkBoxSupportDifficult = findViewById(R.id.checkBoxSupportDifficult);
        checkBoxSupportFriends = findViewById(R.id.checkBoxSupportFriends);
        checkBoxSupportFreezes = findViewById(R.id.checkBoxSupportFreezes);
        checkBoxSupportNotify = findViewById(R.id.checkBoxSupportNotify);
        checkBoxSupportEnjoy = findViewById(R.id.checkBoxSupportEnjoy);
        editTextSupportOther = findViewById(R.id.editTextSupportOther);
        buttonSupportButton = findViewById(R.id.buttonSupportButton);


        buttonSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> mapProfileUser = new HashMap<>();
                mapProfileUser.put("support_user", currentUser);
                mapProfileUser.put("support_date", Timestamp.now());
                if (checkBoxSupportDifficult.isChecked()) {
                    mapProfileUser.put("support_difficult", "This com.techmax.datingsoon.Main.Application is Difficult");
                }
                if (checkBoxSupportFriends.isChecked()) {
                    mapProfileUser.put("support_friends", "I can't find any friends");
                }
                if (checkBoxSupportFreezes.isChecked()) {
                    mapProfileUser.put("support_freezes", "This app is slow and freezes");
                }
                if (checkBoxSupportNotify.isChecked()) {
                    mapProfileUser.put("support_notify", "I dont like notifications");
                }
                if (checkBoxSupportEnjoy.isChecked()) {
                    mapProfileUser.put("support_enjoy", "I love and enjoy this app");
                }
                String stringSupportOther = editTextSupportOther.getText().toString();
                if (!stringSupportOther.equals("")) {
                    mapProfileUser.put("support_other", stringSupportOther);
                }

                if (mapProfileUser.size() > 2) {

                    firebaseFirestore.collection("support")
                            .add(mapProfileUser)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SupportActivity.this,
                                                "Your feedback is sent!", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SupportActivity.this,
                            "Please select support types to send", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
