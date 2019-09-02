package com.techmax.datingsoon.Start;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Main.MainActivity;
import com.techmax.datingsoon.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class StartActivity extends AppCompatActivity {

    Button buttonStartFacebook;
    Button buttonStartEmail;

    ImageView imageStart;

    ProgressDialog dialog;

    private CallbackManager mCallbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    ArrayList<String> arrayListFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.start_activity);

        arrayListFacebook = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        buttonStartFacebook = findViewById(R.id.buttonStartFacebook);
        buttonStartEmail = findViewById(R.id.buttonStartEmail);
        imageStart = findViewById(R.id.imageStart);


        firebaseFirestore.collection("admin")
                .document("settings")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if(documentSnapshot!=null){
                            String start_image = documentSnapshot.getString("start_image");
                            Picasso.get().load(start_image).placeholder(R.drawable.gradient).into(imageStart);
                        }
                    }
                });

        buttonStartEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });




        mCallbackManager = CallbackManager.Factory.create();
        buttonStartFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FacebookSignIn();

                dialog = new ProgressDialog(StartActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }


    private void FacebookSignIn() {

        LoginManager.getInstance().logInWithReadPermissions(StartActivity.this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Toast.makeText(StartActivity.this, "Login Sucsess!", Toast.LENGTH_SHORT).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // Toast.makeText(StartActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        // Toast.makeText(StartActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }


        );

    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String firebaseId = task.getResult().getUser().getUid();
                            String facebookName = task.getResult().getUser().getDisplayName();
                            String facebookEmail = task.getResult().getUser().getEmail();
                            String facebookPhoto = task.getResult().getUser().getPhotoUrl().toString();

                            firebaseFirestore.collection("users")
                                    .document(firebaseId)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (!task.getResult().exists()) {

                                                FirestoreRegister(firebaseId, facebookName, facebookEmail, facebookPhoto);

                                            } else {

                                                WelcomePage();

                                            }
                                        }
                                    });

                        } else {

                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), "User with Email id already exists. Login with email to link this account",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void FirestoreRegister(String firebaseId, String userName, String userEmail, String userPhoto) {

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("user_uid", firebaseId);
        userProfile.put("user_email", userEmail);
        userProfile.put("user_epass", ""); //null (IMPORTANT - DO NOT EDIT THIS FIELD)
        userProfile.put("user_name", userName);
        userProfile.put("user_gender", "Male"); //Dummy
        userProfile.put("user_birthday", "01/01/1990"); //Dummy
        userProfile.put("user_birthage", "25"); //Dummy
        userProfile.put("user_city", "Mumbai"); //Dummy
        userProfile.put("user_state", "Maharashtra"); //Dummy
        userProfile.put("user_country", "India"); //Dummy
        userProfile.put("user_location", "Bandra"); //Dummy
        userProfile.put("user_thumb", userPhoto);
        userProfile.put("user_image", userPhoto);
        userProfile.put("user_cover", userPhoto);
        userProfile.put("user_status", "offline");
        userProfile.put("user_looking", "Man"); //Dummy
        userProfile.put("user_about", "Hi! Everybody I am newbie here.");
        userProfile.put("user_latitude", "19.075983"); //Dummy
        userProfile.put("user_longitude", "72.877655"); //Dummy
        userProfile.put("user_online", Timestamp.now());
        userProfile.put("user_joined", Timestamp.now());
        userProfile.put("user_verified", "yes");
        userProfile.put("user_facebook", "yes");
        userProfile.put("user_dummy", "yes");


        firebaseFirestore.collection("users")
                .document(firebaseId)
                .set(userProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            RemindPage(firebaseId);

                        }
                    }
                });
    }

    private void WelcomePage() {

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void RemindPage(String firebaseId) {

        Intent intent = new Intent(StartActivity.this, RemindActivity.class);
        intent.putExtra("user_uid", firebaseId);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
