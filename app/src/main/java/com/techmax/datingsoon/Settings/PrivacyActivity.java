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

public class PrivacyActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String currentUser;

    Toolbar toolbarPrivacyToolbar;

    Switch switchPrivacyLocation;
    Switch switchPrivacyBirthage;
    Switch switchPrivacyVisits;
    Switch switchPrivacyChats;
    Switch switchPrivacyGender;
    Switch switchPrivacyPhoto;
    Switch switchPrivacyVerify;
    Switch switchPrivacyPremium;
    Switch switchPrivacyCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_activity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = firebaseUser.getUid();

        toolbarPrivacyToolbar = findViewById(R.id.toolbarPrivacyToolbar);
        setSupportActionBar(toolbarPrivacyToolbar);
        getSupportActionBar().setTitle("Privacy Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarPrivacyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchPrivacyLocation = findViewById(R.id.switchPrivacyLocation);
        switchPrivacyBirthage = findViewById(R.id.switchPrivacyBirthage);
        switchPrivacyVisits = findViewById(R.id.switchPrivacyVisits);
        switchPrivacyChats = findViewById(R.id.switchPrivacyChats);
        switchPrivacyGender = findViewById(R.id.switchPrivacyGender);
        switchPrivacyPhoto = findViewById(R.id.switchPrivacyPhoto);
        switchPrivacyVerify = findViewById(R.id.switchPrivacyVerify);
        switchPrivacyPremium = findViewById(R.id.switchPrivacyPremium);
        switchPrivacyCountry = findViewById(R.id.switchPrivacyCountry);


        switchPrivacyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyLocation,
                        "share_location",
                        "Location sharing enabled",
                        "Location sharing disabled");
            }
        });

        switchPrivacyBirthage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyBirthage,
                        "share_birthage",
                        "you are showing your age",
                        "your age is hidden now");
            }
        });

        switchPrivacyVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyVisits,
                        "share_visits",
                        "Your visits will be now logged",
                        "Stalker face on!");
            }
        });

        switchPrivacyChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyChats,
                        "block_stranger",
                        "This will keep strangers away!",
                        "Everyone can send you chats now");
            }
        });

        switchPrivacyGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyGender,
                        "block_genders",
                        "Same gender wont disturb you now",
                        "All genders can contact you now");
            }
        });

        switchPrivacyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyPhoto,
                        "block_photos",
                        "People without photos blocked",
                        "People without photos unblocked");
            }
        });

        switchPrivacyVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyVerify,
                        "allow_verified",
                        "Non verified members blocked",
                        "Non verified members unblocked");
            }
        });

        switchPrivacyPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyPremium,
                        "allow_premium",
                        "Only Vips can contact you now",
                        "Every membership levels can contact you now");
            }
        });

        switchPrivacyCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyProfile(switchPrivacyCountry,
                        "allow_country",
                        "Only people from your country can contact you now",
                        "People from every country can contact you now");
            }
        });


    }


    private void PrivacyProfile(Switch switchProfile, String switchString,
                                String switchToastOn, String switchToastOff) {

        if (switchProfile.isChecked()) {

            Map<String, Object> mapUserProfile = new HashMap<>();
            mapUserProfile.put(switchString, "yes");
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(mapUserProfile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PrivacyActivity.this,
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
                            if (task.isSuccessful()) {
                                Toast.makeText(PrivacyActivity.this,
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

                            String share_location = documentSnapshot.getString("share_location");
                            String share_birthage = documentSnapshot.getString("share_birthage");
                            String share_visits = documentSnapshot.getString("share_visits");
                            String block_stranger = documentSnapshot.getString("block_stranger");
                            String block_genders = documentSnapshot.getString("block_genders");
                            String block_photos = documentSnapshot.getString("block_photos");
                            String allow_verified = documentSnapshot.getString("allow_verified");
                            String allow_premium = documentSnapshot.getString("allow_premium");
                            String allow_country = documentSnapshot.getString("allow_country");


                            if (share_location != null) {
                                if (share_location.equals("yes")) {
                                    switchPrivacyLocation.setChecked(true);
                                } else {
                                    switchPrivacyLocation.setChecked(false);
                                }
                            }
                            if (share_birthage != null) {
                                if (share_birthage.equals("yes")) {
                                    switchPrivacyBirthage.setChecked(true);
                                } else {
                                    switchPrivacyBirthage.setChecked(false);
                                }
                            }
                            if (share_visits != null) {
                                if (share_visits.equals("yes")) {
                                    switchPrivacyVisits.setChecked(true);
                                } else {
                                    switchPrivacyVisits.setChecked(false);
                                }
                            }
                            if (block_stranger != null) {
                                if (block_stranger.equals("yes")) {
                                    switchPrivacyChats.setChecked(true);
                                } else {
                                    switchPrivacyChats.setChecked(false);
                                }
                            }
                            if (block_genders != null) {
                                if (block_genders.equals("yes")) {
                                    switchPrivacyGender.setChecked(true);
                                } else {
                                    switchPrivacyGender.setChecked(false);
                                }
                            }
                            if (block_photos != null) {
                                if (block_photos.equals("yes")) {
                                    switchPrivacyPhoto.setChecked(true);
                                } else {
                                    switchPrivacyPhoto.setChecked(false);
                                }
                            }
                            if (allow_verified != null) {
                                if (allow_verified.equals("yes")) {
                                    switchPrivacyVerify.setChecked(true);
                                } else {
                                    switchPrivacyVerify.setChecked(false);
                                }
                            }
                            if (allow_premium != null) {
                                if (allow_premium.equals("yes")) {
                                    switchPrivacyPremium.setChecked(true);
                                } else {
                                    switchPrivacyPremium.setChecked(false);
                                }
                            }
                            if (allow_country != null) {
                                if (allow_country.equals("yes")) {
                                    switchPrivacyCountry.setChecked(true);
                                } else {
                                    switchPrivacyCountry.setChecked(false);
                                }
                            }


                        }
                    }
                });
    }
}
