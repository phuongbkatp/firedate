package com.techmax.datingsoon.Profile;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Extra.BlockClass;
import com.techmax.datingsoon.Extra.EventClass;
import com.techmax.datingsoon.Extra.ImageClass;
import com.techmax.datingsoon.Message.MessageActivity;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Settings.ReportActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import ss.com.bannerslider.Slider;


public class ProfileActivity extends AppCompatActivity {

    LinearLayout linearLayoutProfileViewAbout;
    LinearLayout linearLayoutProfileViewLooking;
    LinearLayout linearLayoutProfileViewSeeking;
    LinearLayout linearLayoutProfileViewMarital;
    LinearLayout linearLayoutProfileViewSexual;
    LinearLayout linearLayoutProfileViewHeight;
    LinearLayout linearLayoutProfileViewWeight;
    LinearLayout linearLayoutProfileViewAppearance;
    LinearLayout linearLayoutProfileViewFeature;
    LinearLayout linearLayoutProfileViewEthnicity;
    LinearLayout linearLayoutProfileViewBodytype;
    LinearLayout linearLayoutProfileViewBodyart;
    LinearLayout linearLayoutProfileViewEyecolor;
    LinearLayout linearLayoutProfileViewEyewear;
    LinearLayout linearLayoutProfileViewHaircolor;
    LinearLayout linearLayoutProfileViewStarsign;
    LinearLayout linearLayoutProfileViewJobtitle;
    LinearLayout linearLayoutProfileViewCompany;
    LinearLayout linearLayoutProfileViewIncome;
    LinearLayout linearLayoutProfileViewEducation;
    LinearLayout linearLayoutProfileViewLanguage;
    LinearLayout linearLayoutProfileViewReligion;
    LinearLayout linearLayoutProfileViewDrinking;
    LinearLayout linearLayoutProfileViewSmoking;
    LinearLayout linearLayoutProfileViewLiving;
    LinearLayout linearLayoutProfileViewRelocation;
    LinearLayout linearLayoutProfileViewInterests;
    LinearLayout linearLayoutProfileViewActivities;
    LinearLayout linearLayoutProfileViewMovies;
    LinearLayout linearLayoutProfileViewMusics;
    LinearLayout linearLayoutProfileViewTvshows;
    LinearLayout linearLayoutProfileViewSports;
    LinearLayout linearLayoutProfileViewBooks;


    TextView textViewProfileViewUsername;
    TextView textViewProfileViewBirthage;
    TextView textViewProfileViewLocation;
    TextView textViewProfileViewDistance;

    TextView textViewProfileViewAbout;
    TextView textViewProfileViewLooking;
    TextView textViewProfileViewSeeking;
    TextView textViewProfileViewMarital;
    TextView textViewProfileViewSexual;
    TextView textViewProfileViewHeight;
    TextView textViewProfileViewWeight;
    TextView textViewProfileViewAppearance;
    TextView textViewProfileViewFeature;
    TextView textViewProfileViewEthnicity;
    TextView textViewProfileViewBodytype;
    TextView textViewProfileViewBodyart;
    TextView textViewProfileViewEyecolor;
    TextView textViewProfileViewEyewear;
    TextView textViewProfileViewHaircolor;
    TextView textViewProfileViewStarsign;
    TextView textViewProfileViewJobtitle;
    TextView textViewProfileViewCompany;
    TextView textViewProfileViewIncome;
    TextView textViewProfileViewEducation;
    TextView textViewProfileViewLanguage;
    TextView textViewProfileViewReligion;
    TextView textViewProfileViewDrinking;
    TextView textViewProfileViewSmoking;
    TextView textViewProfileViewLiving;
    TextView textViewProfileViewRelocation;
    TextView textViewProfileViewInterests;
    TextView textViewProfileViewActivities;
    TextView textViewProfileViewMovies;
    TextView textViewProfileViewMusics;
    TextView textViewProfileViewTvshows;
    TextView textViewProfileViewSports;
    TextView textViewProfileViewBooks;

    Double latitudeCurrent;
    Double longitudeCurrent;
    Double latitudeChatUser;
    Double longitudeChatUser;
    String stringDistance;
    String genderCurrent;
    String imageCurrent;
    String verifiedCurrent;
    String premiumCurrent;
    String countryCurrent;

    String[] string_array_user_report;

    String user_cover_zero;
    String user_cover_one;
    String user_cover_two;
    String user_cover_three;
    String user_cover_four;
    String user_cover_five;
    String user_cover_six;
    String user_cover_seven;
    String user_cover_eight;

    FloatingActionButton floatingActionButtonChat;

    ImageView imageViewProfileImage;
    Toolbar toolbarProfile;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String profileUser;
    String currentUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    String user_cover;

    int caraoselTotalNumber = 1;

    private ArrayList<String> stringArrayCovers;

    private Menu menuMessage;


    CarouselView carouselView;

    private AdView adViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        linearLayoutProfileViewAbout = findViewById(R.id.linearLayoutProfileViewAbout);
        linearLayoutProfileViewLooking = findViewById(R.id.linearLayoutProfileViewLooking);
        linearLayoutProfileViewSeeking = findViewById(R.id.linearLayoutProfileViewSeeking);
        linearLayoutProfileViewMarital = findViewById(R.id.linearLayoutProfileViewMarital);
        linearLayoutProfileViewSexual = findViewById(R.id.linearLayoutProfileViewSexual);
        linearLayoutProfileViewHeight = findViewById(R.id.linearLayoutProfileViewHeight);
        linearLayoutProfileViewWeight = findViewById(R.id.linearLayoutProfileViewWeight);
        linearLayoutProfileViewAppearance = findViewById(R.id.linearLayoutProfileViewAppearance);
        linearLayoutProfileViewFeature = findViewById(R.id.linearLayoutProfileViewFeature);
        linearLayoutProfileViewEthnicity = findViewById(R.id.linearLayoutProfileViewEthnicity);
        linearLayoutProfileViewBodytype = findViewById(R.id.linearLayoutProfileViewBodytype);
        linearLayoutProfileViewBodyart = findViewById(R.id.linearLayoutProfileViewBodyart);
        linearLayoutProfileViewEyecolor = findViewById(R.id.linearLayoutProfileViewEyecolor);
        linearLayoutProfileViewEyewear = findViewById(R.id.linearLayoutProfileViewEyewear);
        linearLayoutProfileViewHaircolor = findViewById(R.id.linearLayoutProfileViewHaircolor);
        linearLayoutProfileViewStarsign = findViewById(R.id.linearLayoutProfileViewStarsign);
        linearLayoutProfileViewJobtitle = findViewById(R.id.linearLayoutProfileViewJobtitle);
        linearLayoutProfileViewCompany = findViewById(R.id.linearLayoutProfileViewCompany);
        linearLayoutProfileViewIncome = findViewById(R.id.linearLayoutProfileViewIncome);
        linearLayoutProfileViewEducation = findViewById(R.id.linearLayoutProfileViewEducation);
        linearLayoutProfileViewLanguage = findViewById(R.id.linearLayoutProfileViewLanguage);
        linearLayoutProfileViewReligion = findViewById(R.id.linearLayoutProfileViewReligion);
        linearLayoutProfileViewDrinking = findViewById(R.id.linearLayoutProfileViewDrinking);
        linearLayoutProfileViewSmoking = findViewById(R.id.linearLayoutProfileViewSmoking);
        linearLayoutProfileViewLiving = findViewById(R.id.linearLayoutProfileViewLiving);
        linearLayoutProfileViewRelocation = findViewById(R.id.linearLayoutProfileViewRelocation);
        linearLayoutProfileViewInterests = findViewById(R.id.linearLayoutProfileViewInterests);
        linearLayoutProfileViewActivities = findViewById(R.id.linearLayoutProfileViewActivities);
        linearLayoutProfileViewMovies = findViewById(R.id.linearLayoutProfileViewMovies);
        linearLayoutProfileViewMusics = findViewById(R.id.linearLayoutProfileViewMusics);
        linearLayoutProfileViewTvshows = findViewById(R.id.linearLayoutProfileViewTvshows);
        linearLayoutProfileViewSports = findViewById(R.id.linearLayoutProfileViewSports);
        linearLayoutProfileViewBooks = findViewById(R.id.linearLayoutProfileViewBooks);


        textViewProfileViewUsername = findViewById(R.id.textViewProfileViewUsername);
        textViewProfileViewBirthage = findViewById(R.id.textViewProfileViewBirthage);
        textViewProfileViewLocation = findViewById(R.id.textViewProfileViewLocation);
        textViewProfileViewDistance = findViewById(R.id.textViewProfileViewDistance);

        textViewProfileViewAbout = findViewById(R.id.textViewProfileViewAbout);
        textViewProfileViewLooking = findViewById(R.id.textViewProfileViewLooking);
        textViewProfileViewSeeking = findViewById(R.id.textViewProfileViewSeeking);
        textViewProfileViewMarital = findViewById(R.id.textViewProfileViewMarital);
        textViewProfileViewSexual = findViewById(R.id.textViewProfileViewSexual);
        textViewProfileViewHeight = findViewById(R.id.textViewProfileViewHeight);
        textViewProfileViewWeight = findViewById(R.id.textViewProfileViewWeight);
        textViewProfileViewAppearance = findViewById(R.id.textViewProfileViewAppearance);
        textViewProfileViewFeature = findViewById(R.id.textViewProfileViewFeature);
        textViewProfileViewEthnicity = findViewById(R.id.textViewProfileViewEthnicity);
        textViewProfileViewBodytype = findViewById(R.id.textViewProfileViewBodytype);
        textViewProfileViewBodyart = findViewById(R.id.textViewProfileViewBodyart);
        textViewProfileViewEyecolor = findViewById(R.id.textViewProfileViewEyecolor);
        textViewProfileViewEyewear = findViewById(R.id.textViewProfileViewEyewear);
        textViewProfileViewHaircolor = findViewById(R.id.textViewProfileViewHaircolor);
        textViewProfileViewStarsign = findViewById(R.id.textViewProfileViewStarsign);
        textViewProfileViewJobtitle = findViewById(R.id.textViewProfileViewJobtitle);
        textViewProfileViewCompany = findViewById(R.id.textViewProfileViewCompany);
        textViewProfileViewIncome = findViewById(R.id.textViewProfileViewIncome);
        textViewProfileViewEducation = findViewById(R.id.textViewProfileViewEducation);
        textViewProfileViewLanguage = findViewById(R.id.textViewProfileViewLanguage);
        textViewProfileViewReligion = findViewById(R.id.textViewProfileViewReligion);
        textViewProfileViewDrinking = findViewById(R.id.textViewProfileViewDrinking);
        textViewProfileViewSmoking = findViewById(R.id.textViewProfileViewSmoking);
        textViewProfileViewLiving = findViewById(R.id.textViewProfileViewLiving);
        textViewProfileViewRelocation = findViewById(R.id.textViewProfileViewRelocation);
        textViewProfileViewInterests = findViewById(R.id.textViewProfileViewInterests);
        textViewProfileViewActivities = findViewById(R.id.textViewProfileViewActivities);
        textViewProfileViewMovies = findViewById(R.id.textViewProfileViewMovies);
        textViewProfileViewMusics = findViewById(R.id.textViewProfileViewMusics);
        textViewProfileViewTvshows = findViewById(R.id.textViewProfileViewTvshows);
        textViewProfileViewSports = findViewById(R.id.textViewProfileViewSports);
        textViewProfileViewBooks = findViewById(R.id.textViewProfileViewBooks);

        floatingActionButtonChat = findViewById(R.id.floatingActionButtonChat);

        imageViewProfileImage = findViewById(R.id.imageViewProfileImage);

        stringArrayCovers = new ArrayList<>();


        string_array_user_report = getResources().getStringArray(R.array.string_array_user_report);


        toolbarProfile = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adViewProfile = findViewById(R.id.adViewProfile);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewProfile.loadAd(adRequest);


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setImageListener(imageListener);

        profileUser = getIntent().getStringExtra("user_uid");

        ImageClass imageClass = new ImageClass(this);


        Slider.init(imageClass);

        floatingActionButtonChat.bringToFront();

        floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                intent.putExtra("user_uid", profileUser);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        firebaseFirestore.collection("users")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot documentSnapshot = task.getResult();


                        user_cover_zero = documentSnapshot.getString("user_cover");
                        user_cover_one = documentSnapshot.getString("user_cover1");
                        user_cover_two = documentSnapshot.getString("user_cover2");
                        user_cover_three = documentSnapshot.getString("user_cover3");
                        user_cover_four = documentSnapshot.getString("user_cover4");
                        user_cover_five = documentSnapshot.getString("user_cover5");
                        user_cover_six = documentSnapshot.getString("user_cover6");
                        user_cover_seven = documentSnapshot.getString("user_cover7");
                        user_cover_eight = documentSnapshot.getString("user_cover8");

                        if (user_cover_one != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_two != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_three != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_four != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_five != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_six != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_seven != null) {
                            caraoselTotalNumber++;
                        }
                        if (user_cover_eight != null) {
                            caraoselTotalNumber++;
                        }

                        carouselView.setPageCount(caraoselTotalNumber);


                    }
                });

    }


    private void BlockUser() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUser = firebaseUser.getUid();


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("block")
                                    .document(profileUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileActivity.this,
                                                        "You have unblocked this user!", Toast.LENGTH_SHORT).show();

                                                BlockCheck();


                                            }
                                        }
                                    });
                        } else {
                            Map<String, Object> mapBlockUser = new HashMap<>();
                            mapBlockUser.put("user_block", profileUser);
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("block")
                                    .document(profileUser)
                                    .set(mapBlockUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileActivity.this,
                                                        "You have blocked this user!", Toast.LENGTH_SHORT).show();

                                                BlockCheck();

                                            }
                                        }
                                    });
                        }
                    }
                });

    }


    private void BlockCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Unblock User");
                        } else {
                            menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Block User");

                        }
                    }
                });


    }


    private void UnmatchUser() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("loves")
                .document(profileUser)
                .delete();
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("nopes")
                .document(profileUser)
                .delete();
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .document(profileUser)
                .delete();
        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("matches")
                .document(currentUser)
                .delete();
        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("likes")
                .document(currentUser)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this,
                                    "User Unmatched!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    private void FavoriteUser() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUser = firebaseUser.getUid();

        Map<String, Object> arrayFavoriteUser = new HashMap<>();
        arrayFavoriteUser.put("user_favorite", profileUser);
        arrayFavoriteUser.put("user_favorited", Timestamp.now());


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("favors")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("favors")
                                    .document(profileUser)
                                    .set(arrayFavoriteUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileActivity.this,
                                                        "User added in favorite", Toast.LENGTH_SHORT).show();

                                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);

                                            }
                                        }
                                    });
                        } else {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("favors")
                                    .document(profileUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileActivity.this,
                                                        "User removed from favorite", Toast.LENGTH_SHORT).show();

                                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);

                                            }
                                        }
                                    });
                        }
                    }
                });

    }


    private void FavoriteCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("favors")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            if (documentSnapshot.exists()) {
                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);

                            }
                        }
                    }
                });
    }


    private void UnmatchCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(true);

                        } else {
                            menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);

                        }
                    }
                });

    }


    private void EventSend() {
        EventBus.getDefault().post(new EventClass("Refresh"));
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, final ImageView imageView) {

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            if (position == 0) {
                StartSlider(position, imageView);
            }
            if (position == 1) {
                StartSlider(position, imageView);
            }
            if (position == 2) {
                StartSlider(position, imageView);
            }
            if (position == 3) {
                StartSlider(position, imageView);
            }
            if (position == 4) {
                StartSlider(position, imageView);
            }
            if (position == 5) {
                StartSlider(position, imageView);
            }
            if (position == 6) {
                StartSlider(position, imageView);
            }
            if (position == 7) {
                StartSlider(position, imageView);
            }
            if (position == 8) {
                StartSlider(position, imageView);
            }


        }
    };

    private void StartSlider(final int position, final ImageView imageView) {


            firebaseFirestore.collection("users")
                    .document(profileUser)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            if(documentSnapshot!=null) {

                                String user_cover_zero = documentSnapshot.getString("user_cover");
                                String user_cover_one = documentSnapshot.getString("user_cover1");
                                String user_cover_two = documentSnapshot.getString("user_cover2");
                                String user_cover_three = documentSnapshot.getString("user_cover3");
                                String user_cover_four = documentSnapshot.getString("user_cover4");
                                String user_cover_five = documentSnapshot.getString("user_cover5");
                                String user_cover_six = documentSnapshot.getString("user_cover6");
                                String user_cover_seven = documentSnapshot.getString("user_cover7");
                                String user_cover_eight = documentSnapshot.getString("user_cover8");

                                if (user_cover_zero != null) {

                                    stringArrayCovers.add(user_cover_zero);

                                }
                                if (user_cover_one != null) {

                                    stringArrayCovers.add(user_cover_one);

                                }
                                if (user_cover_two != null) {

                                    stringArrayCovers.add(user_cover_two);

                                }
                                if (user_cover_three != null) {

                                    stringArrayCovers.add(user_cover_three);

                                }
                                if (user_cover_four != null) {

                                    stringArrayCovers.add(user_cover_four);

                                }
                                if (user_cover_five != null) {

                                    stringArrayCovers.add(user_cover_five);

                                }
                                if (user_cover_six != null) {

                                    stringArrayCovers.add(user_cover_six);

                                }
                                if (user_cover_seven != null) {

                                    stringArrayCovers.add(user_cover_seven);

                                }
                                if (user_cover_eight != null) {

                                    stringArrayCovers.add(user_cover_eight);

                                }


                                if (position == 0) {
                                    user_cover = documentSnapshot.getString("user_cover");

                                    if (!user_cover.equals("cover")) {
                                        Picasso.get().load(user_cover).into(imageView);
                                    } else {
                                        Picasso.get().load(R.drawable.profile_image).into(imageView);
                                    }
                                } else {
                                    user_cover = stringArrayCovers.get(position);
                                    Picasso.get().load(user_cover).into(imageView);
                                }

                            }

                        }
                    });


    }

    private void VisitsUser() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("users")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String share_visit = task.getResult().getString("share_visits");

                            if (share_visit != null && share_visit.equals("no")) {

                            } else {
                                firebaseFirestore.collection("users")
                                        .document(profileUser)
                                        .collection("visits")
                                        .document(currentUser)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.getResult().exists()) {

                                                    Map<String, Object> mapBlockUser = new HashMap<>();
                                                    mapBlockUser.put("user_visitor", currentUser);
                                                    mapBlockUser.put("user_visited", Timestamp.now());
                                                    firebaseFirestore.collection("users")
                                                            .document(profileUser)
                                                            .collection("visits")
                                                            .document(currentUser)
                                                            .update(mapBlockUser);


                                                } else {

                                                    Map<String, Object> mapBlockUser = new HashMap<>();
                                                    mapBlockUser.put("user_visitor", currentUser);
                                                    mapBlockUser.put("user_visited", Timestamp.now());
                                                    firebaseFirestore.collection("users")
                                                            .document(profileUser)
                                                            .collection("visits")
                                                            .document(currentUser)
                                                            .set(mapBlockUser);
                                                }
                                            }
                                        });
                            }

                        }
                    }
                });


    }


    @Override
    protected void onStart() {
        super.onStart();


        profileUser = getIntent().getStringExtra("user_uid");


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();


        if (currentUser.equals(profileUser)) {

            floatingActionButtonChat.hide();

        } else {

            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .collection("matches")
                    .document(profileUser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult().exists()) {
                                BlockCheck();
                            }
                        }
                    });

            VisitsUser();


        }


        UserCurrent();
        UserProfile();
    }

    private void UserCurrent() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            latitudeCurrent = Double.valueOf(documentSnapshot.getString("user_latitude"));
                            longitudeCurrent = Double.valueOf(documentSnapshot.getString("user_longitude"));
                            genderCurrent = documentSnapshot.getString("user_gender");
                            imageCurrent = documentSnapshot.getString("user_image");
                            verifiedCurrent = documentSnapshot.getString("user_verified");
                            premiumCurrent = documentSnapshot.getString("user_premium");
                            countryCurrent = documentSnapshot.getString("user_country");


                            String matchCurrent = documentSnapshot.getString("show_match");


                            if (matchCurrent != null && matchCurrent.equals("yes")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        firebaseFirestore.collection("users")
                                                .document(currentUser)
                                                .collection("matches")
                                                .document(profileUser)
                                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                        if (documentSnapshot != null && documentSnapshot.exists()) {

                                                            Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                                                            intent.putExtra("user_uid", profileUser);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);

                                                        } else {
                                                            Toast.makeText(ProfileActivity.this,
                                                                    "Match mode on! Swipe cards to connect with this user!",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }
                                });
                            }


                        }
                    }
                });

        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("chats")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            UserChats("yes");
                        } else {
                            UserChats("no");

                        }
                    }
                });
    }


    private void UserChats(String stringChats) {

        firebaseFirestore.collection("users")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

                            String block_stranger = documentSnapshot.getString("block_stranger");

                            if (block_stranger != null && block_stranger.equals("yes") && stringChats.equals("no")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }
                });


    }

    private void UserProfile() {

        firebaseFirestore.collection("users")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

                            String user_name = documentSnapshot.getString("user_name");
                            String user_gender = documentSnapshot.getString("user_gender");
                            String user_birthday = documentSnapshot.getString("user_birthday");
                            String user_birthage = documentSnapshot.getString("user_birthage");
                            String user_image = documentSnapshot.getString("user_image");
                            String user_city = documentSnapshot.getString("user_city");
                            String user_state = documentSnapshot.getString("user_state");
                            String user_country = documentSnapshot.getString("user_country");

                            String user_about = documentSnapshot.getString("user_about");
                            String user_looking = documentSnapshot.getString("user_looking");
                            String user_seeking = documentSnapshot.getString("user_seeking");
                            String user_marital = documentSnapshot.getString("user_marital");
                            String user_sexual = documentSnapshot.getString("user_sexual");
                            String user_height = documentSnapshot.getString("user_height");
                            String user_weight = documentSnapshot.getString("user_weight");
                            String user_appearance = documentSnapshot.getString("user_appearance");
                            String user_feature = documentSnapshot.getString("user_feature");
                            String user_ethnicity = documentSnapshot.getString("user_ethnicity");
                            String user_bodytype = documentSnapshot.getString("user_bodytype");
                            String user_bodyart = documentSnapshot.getString("user_bodyart");
                            String user_eyecolor = documentSnapshot.getString("user_eyecolor");
                            String user_eyewear = documentSnapshot.getString("user_eyewear");
                            String user_haircolor = documentSnapshot.getString("user_haircolor");
                            String user_starsign = documentSnapshot.getString("user_starsign");
                            String user_jobtitle = documentSnapshot.getString("user_jobtitle");
                            String user_company = documentSnapshot.getString("user_company");
                            String user_income = documentSnapshot.getString("user_income");
                            String user_education = documentSnapshot.getString("user_education");
                            String user_language = documentSnapshot.getString("user_language");
                            String user_religion = documentSnapshot.getString("user_religion");
                            String user_drinking = documentSnapshot.getString("user_drinking");
                            String user_smoking = documentSnapshot.getString("user_smoking");
                            String user_living = documentSnapshot.getString("user_living");
                            String user_relocation = documentSnapshot.getString("user_relocation");
                            String user_interests = documentSnapshot.getString("user_interests");
                            String user_activities = documentSnapshot.getString("user_activities");
                            String user_movies = documentSnapshot.getString("user_movies");
                            String user_musics = documentSnapshot.getString("user_musics");
                            String user_tvshows = documentSnapshot.getString("user_tvshows");
                            String user_sports = documentSnapshot.getString("user_sports");
                            String user_books = documentSnapshot.getString("user_books");


                            String show_match = documentSnapshot.getString("show_match");

                            String share_location = documentSnapshot.getString("share_location");
                            String share_birthage = documentSnapshot.getString("share_birthage");

                            String block_genders = documentSnapshot.getString("block_genders");
                            String block_photos = documentSnapshot.getString("block_photos");

                            String allow_verified = documentSnapshot.getString("allow_verified");
                            String allow_premium = documentSnapshot.getString("allow_premium");
                            String allow_country = documentSnapshot.getString("allow_country");


                            if (show_match != null && show_match.equals("yes")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        firebaseFirestore.collection("users")
                                                .document(currentUser)
                                                .collection("matches")
                                                .document(profileUser)
                                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                        if (documentSnapshot != null && documentSnapshot.exists()) {

                                                            Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                                                            intent.putExtra("user_uid", profileUser);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);

                                                        } else {

                                                            Toast.makeText(ProfileActivity.this,
                                                                    "Match mode on! Swipe cards to connect with this user!",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }
                                });
                            }

                            if (block_genders != null && block_genders.equals("yes") && user_gender.equals(genderCurrent)) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            if (block_photos != null && block_photos.equals("yes") && imageCurrent.equals("image")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                            if (allow_verified != null && allow_verified.equals("yes") && verifiedCurrent.equals("no")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                            if (allow_premium != null && allow_premium.equals("yes") && premiumCurrent.equals("no")) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                            if (allow_country != null && allow_country.equals("yes") && !countryCurrent.equals(user_country)) {
                                floatingActionButtonChat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(ProfileActivity.this,
                                                "User has privacy restrictions enabled so you cant contact this user now!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                            String[] splitUserName = user_name.split(" ");

                            if (share_birthage != null && share_birthage.equals("no")) {
                                textViewProfileViewBirthage.setVisibility(View.GONE);
                                textViewProfileViewUsername.setText(splitUserName[0]);
                            } else {
                                textViewProfileViewUsername.setText(splitUserName[0] + ", ");
                            }

                            textViewProfileViewBirthage.setText(user_birthage);
                            textViewProfileViewDistance.setText("20 km away");

                            String user_location = user_city + ", " + user_state + ", " + user_country;
                            textViewProfileViewLocation.setText(user_location);

                            latitudeChatUser = Double.valueOf(documentSnapshot.getString("user_latitude"));
                            longitudeChatUser = Double.valueOf(documentSnapshot.getString("user_longitude"));
                            float floatDistance[] = new float[10];
                            Location.distanceBetween(latitudeCurrent, longitudeCurrent, latitudeChatUser, longitudeChatUser, floatDistance);
                            int intDistance = Math.round(floatDistance[0]);
                            if (intDistance >= 1000) {
                                stringDistance = String.valueOf(intDistance / 1000) + " km away";
                            } else {
                                stringDistance = "Less than 1 km";
                            }
                            textViewProfileViewDistance.setText(stringDistance);


                            //AgeUpdate(user_birthday, user_birthage);


                            if (user_about != null) {
                                textViewProfileViewAbout.setText(user_about);
                            } else linearLayoutProfileViewAbout.setVisibility(View.GONE);
                            if (user_looking != null) {
                                textViewProfileViewLooking.setText(user_looking);
                            } else linearLayoutProfileViewLooking.setVisibility(View.GONE);
                            if (user_seeking != null) {
                                textViewProfileViewSeeking.setText(user_seeking);
                            } else linearLayoutProfileViewSeeking.setVisibility(View.GONE);
                            if (user_marital != null) {
                                textViewProfileViewMarital.setText(user_marital);
                            } else linearLayoutProfileViewMarital.setVisibility(View.GONE);
                            if (user_sexual != null) {
                                textViewProfileViewSexual.setText(user_sexual);
                            } else linearLayoutProfileViewSexual.setVisibility(View.GONE);
                            if (user_height != null) {
                                textViewProfileViewHeight.setText(user_height);
                            } else linearLayoutProfileViewHeight.setVisibility(View.GONE);
                            if (user_weight != null) {
                                textViewProfileViewWeight.setText(user_weight);
                            } else linearLayoutProfileViewWeight.setVisibility(View.GONE);
                            if (user_appearance != null) {
                                textViewProfileViewAppearance.setText(user_appearance);
                            } else linearLayoutProfileViewAppearance.setVisibility(View.GONE);
                            if (user_feature != null) {
                                textViewProfileViewFeature.setText(user_feature);
                            } else linearLayoutProfileViewFeature.setVisibility(View.GONE);
                            if (user_ethnicity != null) {
                                textViewProfileViewEthnicity.setText(user_ethnicity);
                            } else linearLayoutProfileViewEthnicity.setVisibility(View.GONE);
                            if (user_bodytype != null) {
                                textViewProfileViewBodytype.setText(user_bodytype);
                            } else linearLayoutProfileViewBodytype.setVisibility(View.GONE);
                            if (user_bodyart != null) {
                                textViewProfileViewBodyart.setText(user_bodyart);
                            } else linearLayoutProfileViewBodyart.setVisibility(View.GONE);
                            if (user_eyecolor != null) {
                                textViewProfileViewEyecolor.setText(user_eyecolor);
                            } else linearLayoutProfileViewEyecolor.setVisibility(View.GONE);
                            if (user_eyewear != null) {
                                textViewProfileViewEyewear.setText(user_eyewear);
                            } else linearLayoutProfileViewEyewear.setVisibility(View.GONE);
                            if (user_haircolor != null) {
                                textViewProfileViewHaircolor.setText(user_haircolor);
                            } else linearLayoutProfileViewHaircolor.setVisibility(View.GONE);
                            if (user_starsign != null) {
                                textViewProfileViewStarsign.setText(user_starsign);
                            } else linearLayoutProfileViewStarsign.setVisibility(View.GONE);
                            if (user_jobtitle != null) {
                                textViewProfileViewJobtitle.setText(user_jobtitle);
                            } else linearLayoutProfileViewJobtitle.setVisibility(View.GONE);
                            if (user_company != null) {
                                textViewProfileViewCompany.setText(user_company);
                            } else linearLayoutProfileViewCompany.setVisibility(View.GONE);
                            if (user_income != null) {
                                textViewProfileViewIncome.setText(user_income);
                            } else linearLayoutProfileViewIncome.setVisibility(View.GONE);
                            if (user_education != null) {
                                textViewProfileViewEducation.setText(user_education);
                            } else linearLayoutProfileViewEducation.setVisibility(View.GONE);
                            if (user_language != null) {
                                textViewProfileViewLanguage.setText(user_language);
                            } else linearLayoutProfileViewLanguage.setVisibility(View.GONE);
                            if (user_religion != null) {
                                textViewProfileViewReligion.setText(user_religion);
                            } else linearLayoutProfileViewReligion.setVisibility(View.GONE);
                            if (user_drinking != null) {
                                textViewProfileViewDrinking.setText(user_drinking);
                            } else linearLayoutProfileViewDrinking.setVisibility(View.GONE);
                            if (user_smoking != null) {
                                textViewProfileViewSmoking.setText(user_smoking);
                            } else linearLayoutProfileViewSmoking.setVisibility(View.GONE);
                            if (user_living != null) {
                                textViewProfileViewLiving.setText(user_living);
                            } else linearLayoutProfileViewLiving.setVisibility(View.GONE);
                            if (user_relocation != null) {
                                textViewProfileViewRelocation.setText(user_relocation);
                            } else linearLayoutProfileViewRelocation.setVisibility(View.GONE);
                            if (user_interests != null) {
                                textViewProfileViewInterests.setText(user_interests);
                            } else linearLayoutProfileViewInterests.setVisibility(View.GONE);
                            if (user_activities != null) {
                                textViewProfileViewActivities.setText(user_activities);
                            } else linearLayoutProfileViewActivities.setVisibility(View.GONE);
                            if (user_movies != null) {
                                textViewProfileViewMovies.setText(user_movies);
                            } else linearLayoutProfileViewMovies.setVisibility(View.GONE);
                            if (user_musics != null) {
                                textViewProfileViewMusics.setText(user_musics);
                            } else linearLayoutProfileViewMusics.setVisibility(View.GONE);
                            if (user_tvshows != null) {
                                textViewProfileViewTvshows.setText(user_tvshows);
                            } else linearLayoutProfileViewTvshows.setVisibility(View.GONE);
                            if (user_sports != null) {
                                textViewProfileViewSports.setText(user_sports);
                            } else linearLayoutProfileViewSports.setVisibility(View.GONE);
                            if (user_books != null) {
                                textViewProfileViewBooks.setText(user_books);
                            } else linearLayoutProfileViewBooks.setVisibility(View.GONE);

                            if (share_location != null && share_location.equals("no")) {
                                textViewProfileViewLocation.setVisibility(View.GONE);
                                textViewProfileViewDistance.setVisibility(View.GONE);
                            }
                            if (share_birthage != null && share_birthage.equals("no")) {
                                textViewProfileViewBirthage.setVisibility(View.GONE);
                            }


                        }
                    }
                });


    }


    private void UserStatus(String status) {

        Map<String, Object> arrayUserStatus = new HashMap<>();
        arrayUserStatus.put("user_status", status);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .document(profileUser)
                .update(arrayUserStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserStatus("online");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (firebaseUser != null) {
            UserStatus("offline");
        } else {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        menuMessage = menu;


        if (currentUser.equals(profileUser)) {

            menuMessage.findItem(R.id.menuUserBlockUser).setVisible(false);
            menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);
            menuMessage.findItem(R.id.menuUserReportUser).setVisible(false);
            menuMessage.findItem(R.id.menuUserFavoriteUser).setVisible(false);

        } else {

            BlockCheck();
            FavoriteCheck();
            UnmatchCheck();

        }

        menuMessage.findItem(R.id.menuUserDeleteChat).setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        switch (item.getItemId()) {


            case (R.id.menuUserUnmatchUser):
                UnmatchUser();
                return true;


            case (R.id.menuUserBlockUser):
                BlockUser();
                return true;


            case (R.id.menuUserFavoriteUser):
                FavoriteUser();
                return true;

            case (R.id.menuUserReportUser):
                ReportUser();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();

        return super.onPrepareOptionsMenu(menu);
    }


    private void ReportUser() {

        Intent intent = new Intent(ProfileActivity.this, ReportActivity.class);
        intent.putExtra("user_uid", profileUser);
        startActivity(intent);


    }


    private void AgeUpdate(String userBirthday, String userBirthage) {

        int intCurrentBirthage = Integer.valueOf(AgeUser(userBirthday));
        int intRegisterBirthage = Integer.valueOf(userBirthage);

        if (intCurrentBirthage > intRegisterBirthage) {

            Map<String, Object> arrayAgeUpdate = new HashMap<>();
            arrayAgeUpdate.put("user_birthage", String.valueOf(intCurrentBirthage));

            firebaseFirestore.collection("users")
                    .document(profileUser)
                    .update(arrayAgeUpdate);
        }

    }


    private String AgeUser(String stringDateUser) {

        String[] arrayDateUser = stringDateUser.split("-");
        int day = Integer.valueOf(arrayDateUser[0]);
        int month = Integer.valueOf(arrayDateUser[1]);
        int year = Integer.valueOf(arrayDateUser[2]);


        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


}




