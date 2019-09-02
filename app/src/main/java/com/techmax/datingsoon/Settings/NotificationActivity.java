package com.techmax.datingsoon.Settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techmax.datingsoon.R;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {


    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String currentUser;

    Toolbar toolbarNotifyToolbar;


    Switch switchNotifyMatch;
    Switch switchNotifyChats;
    Switch switchNotifyLikes;
    Switch switchNotifySuper;
    Switch switchNotifyVisits;
    Switch switchNotifyFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = firebaseUser.getUid();


        toolbarNotifyToolbar = findViewById(R.id.toolbarNotifyToolbar);
        setSupportActionBar(toolbarNotifyToolbar);
        getSupportActionBar().setTitle("Notification Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarNotifyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchNotifyMatch = findViewById(R.id.switchNotifyMatch);
        switchNotifyChats = findViewById(R.id.switchNotifyChats);
        switchNotifyLikes = findViewById(R.id.switchNotifyLikes);
        switchNotifySuper = findViewById(R.id.switchNotifySuper);
        switchNotifyVisits = findViewById(R.id.switchNotifyVisits);
        switchNotifyFollow = findViewById(R.id.switchNotifyFollow);


        switchNotifyMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifyMatch,
                        "alert_match",
                        "Match notification enabled",
                        "Match notification disabled");
            }
        });
        switchNotifyChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifyChats,
                        "alert_chats",
                        "Chats notification enabled",
                        "Chats notification disabled");
            }
        });
        switchNotifyLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifyLikes,
                        "alert_likes",
                        "Likes notification enabled",
                        "Likes notification disabled");
            }
        });
        switchNotifySuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifySuper,
                        "alert_super",
                        "Super Likes notification enabled",
                        "Super Likes notification disabled");
            }
        });
        switchNotifyVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifyVisits,
                        "alert_visits",
                        "Visitor notification enabled",
                        "Visitor notification disabled");
            }
        });
        switchNotifyFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchNotifyFollow,
                        "alert_follows",
                        "Followers notification enabled",
                        "Followers notification disabled");
            }
        });

    }


    private void PrivacyProfile(Switch switchProfile, String switchString,
                                String switchToastOn, String switchToastOff){

        if (switchProfile.isChecked()) {

            Map<String, Object> mapUserProfile = new HashMap<>();
            mapUserProfile.put(switchString, "yes");
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(mapUserProfile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(NotificationActivity.this,
                                        switchToastOn, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {

            Map<String, Object> mapUserProfile = new HashMap<>();
            mapUserProfile.put(switchString, "no");
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(mapUserProfile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(NotificationActivity.this,
                                        switchToastOff, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        String currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("users")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();

                            String alert_match = documentSnapshot.getString("alert_match");
                            String alert_chats = documentSnapshot.getString("alert_chats");
                            String alert_likes = documentSnapshot.getString("alert_likes");
                            String alert_super = documentSnapshot.getString("alert_super");
                            String alert_visits = documentSnapshot.getString("alert_visits");
                            String alert_follows = documentSnapshot.getString("alert_follows");


                            if(alert_match != null ){
                                if(alert_match.equals("yes")){
                                    switchNotifyMatch.setChecked(true);
                                } else {
                                    switchNotifyMatch.setChecked(false);
                                }
                            }
                            if(alert_chats != null ){
                                if(alert_chats.equals("yes")){
                                    switchNotifyChats.setChecked(true);
                                } else {
                                    switchNotifyChats.setChecked(false);
                                }
                            }
                            if(alert_likes != null ){
                                if(alert_likes.equals("yes")){
                                    switchNotifyLikes.setChecked(true);
                                } else {
                                    switchNotifyLikes.setChecked(false);
                                }
                            }
                            if(alert_super != null ){
                                if(alert_super.equals("yes")){
                                    switchNotifySuper.setChecked(true);
                                } else {
                                    switchNotifySuper.setChecked(false);
                                }
                            }
                            if(alert_visits != null ){
                                if(alert_visits.equals("yes")){
                                    switchNotifyVisits.setChecked(true);
                                } else {
                                    switchNotifyVisits.setChecked(false);
                                }
                            }
                            if(alert_follows != null ){
                                if(alert_follows.equals("yes")){
                                    switchNotifyFollow.setChecked(true);
                                } else {
                                    switchNotifyFollow.setChecked(false);
                                }
                            }


                        }
                    }
                });
    }


}
