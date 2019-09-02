package com.techmax.datingsoon.Profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.R;
import com.synnapps.carouselview.CarouselView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileEditActivity extends AppCompatActivity {

    public static final int GALLERY_IMAGE_COVER3 = 3;
    public static final int GALLERY_IMAGE_IMAGE = 2;

    LinearLayout linearLayoutProfileEditLooking;
    LinearLayout linearLayoutProfileEditSeeking;
    LinearLayout linearLayoutProfileEditMarital;
    LinearLayout linearLayoutProfileEditSexual;
    LinearLayout linearLayoutProfileEditHeight;
    LinearLayout linearLayoutProfileEditWeight;
    LinearLayout linearLayoutProfileEditAppearance;
    LinearLayout linearLayoutProfileEditFeature;
    LinearLayout linearLayoutProfileEditEthnicity;
    LinearLayout linearLayoutProfileEditBodytype;
    LinearLayout linearLayoutProfileEditBodyart;
    LinearLayout linearLayoutProfileEditEyecolor;
    LinearLayout linearLayoutProfileEditEyewear;
    LinearLayout linearLayoutProfileEditHaircolor;
    LinearLayout linearLayoutProfileEditStarsign;
    LinearLayout linearLayoutProfileEditJobtitle;
    LinearLayout linearLayoutProfileEditCompany;
    LinearLayout linearLayoutProfileEditIncome;
    LinearLayout linearLayoutProfileEditEducation;
    LinearLayout linearLayoutProfileEditLanguage;
    LinearLayout linearLayoutProfileEditReligion;
    LinearLayout linearLayoutProfileEditDrinking;
    LinearLayout linearLayoutProfileEditSmoking;
    LinearLayout linearLayoutProfileEditLiving;
    LinearLayout linearLayoutProfileEditRelocation;
    LinearLayout linearLayoutProfileEditInterests;
    LinearLayout linearLayoutProfileEditActivities;
    LinearLayout linearLayoutProfileEditMovies;
    LinearLayout linearLayoutProfileEditMusics;
    LinearLayout linearLayoutProfileEditTvshows;
    LinearLayout linearLayoutProfileEditSports;
    LinearLayout linearLayoutProfileEditBooks;


    TextView textViewProfileEditAbout;
    TextView textViewProfileEditLooking;
    TextView textViewProfileEditSeeking;
    TextView textViewProfileEditMarital;
    TextView textViewProfileEditSexual;
    TextView textViewProfileEditHeight;
    TextView textViewProfileEditWeight;
    TextView textViewProfileEditAppearance;
    TextView textViewProfileEditFeature;
    TextView textViewProfileEditEthnicity;
    TextView textViewProfileEditBodytype;
    TextView textViewProfileEditBodyart;
    TextView textViewProfileEditEyecolor;
    TextView textViewProfileEditEyewear;
    TextView textViewProfileEditHaircolor;
    TextView textViewProfileEditStarsign;
    TextView textViewProfileEditJobtitle;
    TextView textViewProfileEditCompany;
    TextView textViewProfileEditIncome;
    TextView textViewProfileEditEducation;
    TextView textViewProfileEditLanguage;
    TextView textViewProfileEditReligion;
    TextView textViewProfileEditDrinking;
    TextView textViewProfileEditSmoking;
    TextView textViewProfileEditLiving;
    TextView textViewProfileEditRelocation;
    TextView textViewProfileEditInterests;
    TextView textViewProfileEditActivities;
    TextView textViewProfileEditMovies;
    TextView textViewProfileEditMusics;
    TextView textViewProfileEditTvshows;
    TextView textViewProfileEditSports;
    TextView textViewProfileEditBooks;


    String[] string_array_user_looking;
    String[] string_array_user_seeking;
    String[] string_array_user_marital;
    String[] string_array_user_sexual;
    String[] string_array_user_appearance;
    String[] string_array_user_feature;
    String[] string_array_user_ethnicity;
    String[] string_array_user_bodytype;
    String[] string_array_user_bodyart;
    String[] string_array_user_eyecolor;
    String[] string_array_user_eyewear;
    String[] string_array_user_haircolor;
    String[] string_array_user_starsign;
    String[] string_array_user_income;
    String[] string_array_user_education;
    String[] string_array_user_language;
    String[] string_array_user_religion;
    String[] string_array_user_drinking;
    String[] string_array_user_smoking;
    String[] string_array_user_living;
    String[] string_array_user_relocation;


    RoundedImageView imageViewProfileEditCoverZero;
    CircleImageView imageViewProfileEditCoverZeroAdd;
    CircleImageView imageViewProfileEditCoverZeroRemove;

    RoundedImageView imageViewProfileEditCoverOne;
    CircleImageView imageViewProfileEditCoverOneAdd;
    CircleImageView imageViewProfileEditCoverOneRemove;

    RoundedImageView imageViewProfileEditCoverTwo;
    CircleImageView imageViewProfileEditCoverTwoAdd;
    CircleImageView imageViewProfileEditCoverTwoRemove;

    RoundedImageView imageViewProfileEditCoverThree;
    CircleImageView imageViewProfileEditCoverThreeAdd;
    CircleImageView imageViewProfileEditCoverThreeRemove;

    RoundedImageView imageViewProfileEditCoverFour;
    CircleImageView imageViewProfileEditCoverFourAdd;
    CircleImageView imageViewProfileEditCoverFourRemove;

    RoundedImageView imageViewProfileEditCoverFive;
    CircleImageView imageViewProfileEditCoverFiveAdd;
    CircleImageView imageViewProfileEditCoverFiveRemove;

    RoundedImageView imageViewProfileEditCoverSix;
    CircleImageView imageViewProfileEditCoverSixAdd;
    CircleImageView imageViewProfileEditCoverSixRemove;

    RoundedImageView imageViewProfileEditCoverSeven;
    CircleImageView imageViewProfileEditCoverSevenAdd;
    CircleImageView imageViewProfileEditCoverSevenRemove;

    RoundedImageView imageViewProfileEditCoverEight;
    CircleImageView imageViewProfileEditCoverEightAdd;
    CircleImageView imageViewProfileEditCoverEightRemove;


    String ImageUploadNumber;

    ProgressDialog dialog;
    ImageView imageViewProfileImage;
    Toolbar toolbarProfileEditToolbar;
    String stringSeekbarMinimum;
    String stringSeekbarMaximum;
    Bitmap bitmapThumb;
    Bitmap bitmapImage;
    Bitmap bitmapCover;
    String stringThumb;
    String stringImage;
    String stringCover;
    TextView toolbarTextViewUserNames;
    TextView toolbarTextViewUserStatus;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser = firebaseUser.getUid();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private TextView textViewProfileEditGender;
    private TextView textViewProfileEditAge;
    private TextView textViewProfileEditCity;
    private TextView textViewProfileEditState;
    private TextView textViewProfileEditCountry;


    private ArrayList<String> stringArrayCovers = new ArrayList<>();

    ArrayList<Uri> firebaseCovers = new ArrayList<>();

    CarouselView carouselView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_activity);

        stringArrayCovers = new ArrayList<>();

        toolbarProfileEditToolbar = findViewById(R.id.toolbarProfileEditToolbar);
        setSupportActionBar(toolbarProfileEditToolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProfileEditToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageViewProfileImage = findViewById(R.id.imageViewProfileImage);

        //textViewProfileEditGender = (TextView) findViewById(R.id.textViewProfileEditGender);
        //textViewProfileEditCity = (TextView) findViewById(R.id.textViewProfileEditCity);
        //textViewProfileEditState = (TextView) findViewById(R.id.textViewProfileEditState);
        // textViewProfileEditCountry = (TextView) findViewById(R.id.textViewProfileEditCountry);

        linearLayoutProfileEditLooking = findViewById(R.id.linearLayoutProfileEditLooking);
        linearLayoutProfileEditSeeking = findViewById(R.id.linearLayoutProfileEditSeeking);
        linearLayoutProfileEditMarital = findViewById(R.id.linearLayoutProfileEditMarital);
        linearLayoutProfileEditSexual = findViewById(R.id.linearLayoutProfileEditSexual);
        linearLayoutProfileEditHeight = findViewById(R.id.linearLayoutProfileEditHeight);
        linearLayoutProfileEditWeight = findViewById(R.id.linearLayoutProfileEditWeight);
        linearLayoutProfileEditAppearance = findViewById(R.id.linearLayoutProfileEditAppearance);
        linearLayoutProfileEditFeature = findViewById(R.id.linearLayoutProfileEditFeature);
        linearLayoutProfileEditEthnicity = findViewById(R.id.linearLayoutProfileEditEthnicity);
        linearLayoutProfileEditBodytype = findViewById(R.id.linearLayoutProfileEditBodytype);
        linearLayoutProfileEditBodyart = findViewById(R.id.linearLayoutProfileEditBodyart);
        linearLayoutProfileEditEyecolor = findViewById(R.id.linearLayoutProfileEditEyecolor);
        linearLayoutProfileEditEyewear = findViewById(R.id.linearLayoutProfileEditEyewear);
        linearLayoutProfileEditHaircolor = findViewById(R.id.linearLayoutProfileEditHaircolor);
        linearLayoutProfileEditStarsign = findViewById(R.id.linearLayoutProfileEditStarsign);
        linearLayoutProfileEditJobtitle = findViewById(R.id.linearLayoutProfileEditJobtitle);
        linearLayoutProfileEditCompany = findViewById(R.id.linearLayoutProfileEditCompany);
        linearLayoutProfileEditIncome = findViewById(R.id.linearLayoutProfileEditIncome);
        linearLayoutProfileEditEducation = findViewById(R.id.linearLayoutProfileEditEducation);
        linearLayoutProfileEditLanguage = findViewById(R.id.linearLayoutProfileEditLanguage);
        linearLayoutProfileEditReligion = findViewById(R.id.linearLayoutProfileEditReligion);
        linearLayoutProfileEditDrinking = findViewById(R.id.linearLayoutProfileEditDrinking);
        linearLayoutProfileEditSmoking = findViewById(R.id.linearLayoutProfileEditSmoking);
        linearLayoutProfileEditLiving = findViewById(R.id.linearLayoutProfileEditLiving);
        linearLayoutProfileEditRelocation = findViewById(R.id.linearLayoutProfileEditRelocation);
        linearLayoutProfileEditInterests = findViewById(R.id.linearLayoutProfileEditInterests);
        linearLayoutProfileEditActivities = findViewById(R.id.linearLayoutProfileEditActivities);
        linearLayoutProfileEditMovies = findViewById(R.id.linearLayoutProfileEditMovies);
        linearLayoutProfileEditMusics = findViewById(R.id.linearLayoutProfileEditMusics);
        linearLayoutProfileEditTvshows = findViewById(R.id.linearLayoutProfileEditTvshows);
        linearLayoutProfileEditSports = findViewById(R.id.linearLayoutProfileEditSports);
        linearLayoutProfileEditBooks = findViewById(R.id.linearLayoutProfileEditBooks);

        textViewProfileEditAbout = findViewById(R.id.textViewProfileEditAbout);
        textViewProfileEditLooking = findViewById(R.id.textViewProfileEditLooking);
        textViewProfileEditSeeking = findViewById(R.id.textViewProfileEditSeeking);
        textViewProfileEditMarital = findViewById(R.id.textViewProfileEditMarital);
        textViewProfileEditSexual = findViewById(R.id.textViewProfileEditSexual);
        textViewProfileEditHeight = findViewById(R.id.textViewProfileEditHeight);
        textViewProfileEditWeight = findViewById(R.id.textViewProfileEditWeight);
        textViewProfileEditAppearance = findViewById(R.id.textViewProfileEditAppearance);
        textViewProfileEditFeature = findViewById(R.id.textViewProfileEditFeature);
        textViewProfileEditEthnicity = findViewById(R.id.textViewProfileEditEthnicity);
        textViewProfileEditBodytype = findViewById(R.id.textViewProfileEditBodytype);
        textViewProfileEditBodyart = findViewById(R.id.textViewProfileEditBodyart);
        textViewProfileEditEyecolor = findViewById(R.id.textViewProfileEditEyecolor);
        textViewProfileEditEyewear = findViewById(R.id.textViewProfileEditEyewear);
        textViewProfileEditHaircolor = findViewById(R.id.textViewProfileEditHaircolor);
        textViewProfileEditStarsign = findViewById(R.id.textViewProfileEditStarsign);
        textViewProfileEditJobtitle = findViewById(R.id.textViewProfileEditJobtitle);
        textViewProfileEditCompany = findViewById(R.id.textViewProfileEditCompany);
        textViewProfileEditIncome = findViewById(R.id.textViewProfileEditIncome);
        textViewProfileEditEducation = findViewById(R.id.textViewProfileEditEducation);
        textViewProfileEditLanguage = findViewById(R.id.textViewProfileEditLanguage);
        textViewProfileEditReligion = findViewById(R.id.textViewProfileEditReligion);
        textViewProfileEditDrinking = findViewById(R.id.textViewProfileEditDrinking);
        textViewProfileEditSmoking = findViewById(R.id.textViewProfileEditSmoking);
        textViewProfileEditLiving = findViewById(R.id.textViewProfileEditLiving);
        textViewProfileEditRelocation = findViewById(R.id.textViewProfileEditRelocation);
        textViewProfileEditInterests = findViewById(R.id.textViewProfileEditInterests);
        textViewProfileEditActivities = findViewById(R.id.textViewProfileEditActivities);
        textViewProfileEditMovies = findViewById(R.id.textViewProfileEditMovies);
        textViewProfileEditMusics = findViewById(R.id.textViewProfileEditMusics);
        textViewProfileEditTvshows = findViewById(R.id.textViewProfileEditTvshows);
        textViewProfileEditSports = findViewById(R.id.textViewProfileEditSports);
        textViewProfileEditBooks = findViewById(R.id.textViewProfileEditBooks);

        string_array_user_looking = getResources().getStringArray(R.array.string_array_user_looking);
        string_array_user_seeking = getResources().getStringArray(R.array.string_array_user_seeking);
        string_array_user_marital = getResources().getStringArray(R.array.string_array_user_marital);
        string_array_user_sexual = getResources().getStringArray(R.array.string_array_user_sexual);
        string_array_user_appearance = getResources().getStringArray(R.array.string_array_user_appearance);
        string_array_user_feature = getResources().getStringArray(R.array.string_array_user_feature);
        string_array_user_ethnicity = getResources().getStringArray(R.array.string_array_user_ethnicity);
        string_array_user_bodytype = getResources().getStringArray(R.array.string_array_user_bodytype);
        string_array_user_bodyart = getResources().getStringArray(R.array.string_array_user_bodyart);
        string_array_user_eyecolor = getResources().getStringArray(R.array.string_array_user_eyecolor);
        string_array_user_eyewear = getResources().getStringArray(R.array.string_array_user_eyewear);
        string_array_user_haircolor = getResources().getStringArray(R.array.string_array_user_haircolor);
        string_array_user_starsign = getResources().getStringArray(R.array.string_array_user_starsign);
        string_array_user_income = getResources().getStringArray(R.array.string_array_user_income);
        string_array_user_education = getResources().getStringArray(R.array.string_array_user_education);
        string_array_user_language = getResources().getStringArray(R.array.string_array_user_language);
        string_array_user_religion = getResources().getStringArray(R.array.string_array_user_religion);
        string_array_user_drinking = getResources().getStringArray(R.array.string_array_user_drinking);
        string_array_user_smoking = getResources().getStringArray(R.array.string_array_user_smoking);
        string_array_user_living = getResources().getStringArray(R.array.string_array_user_living);
        string_array_user_relocation = getResources().getStringArray(R.array.string_array_user_relocation);

        imageViewProfileEditCoverZero = findViewById(R.id.imageViewProfileEditCoverZero);
        imageViewProfileEditCoverZeroAdd = findViewById(R.id.imageViewProfileEditCoverZeroAdd);
        imageViewProfileEditCoverZeroRemove = findViewById(R.id.imageViewProfileEditCoverZeroRemove);

        imageViewProfileEditCoverOne = findViewById(R.id.imageViewProfileEditCoverOne);
        imageViewProfileEditCoverOneAdd = findViewById(R.id.imageViewProfileEditCoverOneAdd);
        imageViewProfileEditCoverOneRemove = findViewById(R.id.imageViewProfileEditCoverOneRemove);

        imageViewProfileEditCoverTwo = findViewById(R.id.imageViewProfileEditCoverTwo);
        imageViewProfileEditCoverTwoAdd = findViewById(R.id.imageViewProfileEditCoverTwoAdd);
        imageViewProfileEditCoverTwoRemove = findViewById(R.id.imageViewProfileEditCoverTwoRemove);

        imageViewProfileEditCoverThree = findViewById(R.id.imageViewProfileEditCoverThree);
        imageViewProfileEditCoverThreeAdd = findViewById(R.id.imageViewProfileEditCoverThreeAdd);
        imageViewProfileEditCoverThreeRemove = findViewById(R.id.imageViewProfileEditCoverThreeRemove);

        imageViewProfileEditCoverFour = findViewById(R.id.imageViewProfileEditCoverFour);
        imageViewProfileEditCoverFourAdd = findViewById(R.id.imageViewProfileEditCoverFourAdd);
        imageViewProfileEditCoverFourRemove = findViewById(R.id.imageViewProfileEditCoverFourRemove);

        imageViewProfileEditCoverFive = findViewById(R.id.imageViewProfileEditCoverFive);
        imageViewProfileEditCoverFiveAdd = findViewById(R.id.imageViewProfileEditCoverFiveAdd);
        imageViewProfileEditCoverFiveRemove = findViewById(R.id.imageViewProfileEditCoverFiveRemove);

        imageViewProfileEditCoverSix = findViewById(R.id.imageViewProfileEditCoverSix);
        imageViewProfileEditCoverSixAdd = findViewById(R.id.imageViewProfileEditCoverSixAdd);
        imageViewProfileEditCoverSixRemove = findViewById(R.id.imageViewProfileEditCoverSixRemove);

        imageViewProfileEditCoverSeven = findViewById(R.id.imageViewProfileEditCoverSeven);
        imageViewProfileEditCoverSevenAdd = findViewById(R.id.imageViewProfileEditCoverSevenAdd);
        imageViewProfileEditCoverSevenRemove = findViewById(R.id.imageViewProfileEditCoverSevenRemove);

        imageViewProfileEditCoverEight = findViewById(R.id.imageViewProfileEditCoverEight);
        imageViewProfileEditCoverEightAdd = findViewById(R.id.imageViewProfileEditCoverEightAdd);
        imageViewProfileEditCoverEightRemove = findViewById(R.id.imageViewProfileEditCoverEightRemove);


        imageViewProfileEditCoverZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("0");
            }
        });
        imageViewProfileEditCoverZeroAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("0");
            }
        });
        imageViewProfileEditCoverZeroRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("0", imageViewProfileEditCoverZero,
                        imageViewProfileEditCoverZeroAdd, imageViewProfileEditCoverZeroRemove);
            }
        });

        imageViewProfileEditCoverOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("1");
            }
        });
        imageViewProfileEditCoverOneAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("1");
            }
        });
        imageViewProfileEditCoverOneRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("1", imageViewProfileEditCoverOne,
                        imageViewProfileEditCoverOneAdd, imageViewProfileEditCoverOneRemove);
            }
        });

        imageViewProfileEditCoverTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("2");
            }
        });
        imageViewProfileEditCoverTwoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("2");
            }
        });
        imageViewProfileEditCoverTwoRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("2", imageViewProfileEditCoverTwo,
                        imageViewProfileEditCoverTwoAdd, imageViewProfileEditCoverTwoRemove);
            }
        });

        imageViewProfileEditCoverThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("3");
            }
        });
        imageViewProfileEditCoverThreeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("3");
            }
        });
        imageViewProfileEditCoverThreeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("3", imageViewProfileEditCoverThree,
                        imageViewProfileEditCoverThreeAdd, imageViewProfileEditCoverThreeRemove);
            }
        });

        imageViewProfileEditCoverFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("4");
            }
        });
        imageViewProfileEditCoverFourAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("4");
            }
        });
        imageViewProfileEditCoverFourRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("4", imageViewProfileEditCoverFour,
                        imageViewProfileEditCoverFourAdd, imageViewProfileEditCoverFourRemove);
            }
        });

        imageViewProfileEditCoverFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("5");
            }
        });
        imageViewProfileEditCoverFiveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("5");
            }
        });
        imageViewProfileEditCoverFiveRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("5", imageViewProfileEditCoverFive,
                        imageViewProfileEditCoverFiveAdd, imageViewProfileEditCoverFiveRemove);
            }
        });

        imageViewProfileEditCoverSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("6");
            }
        });
        imageViewProfileEditCoverSixAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("6");
            }
        });
        imageViewProfileEditCoverSixRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("6", imageViewProfileEditCoverSix,
                        imageViewProfileEditCoverSixAdd, imageViewProfileEditCoverSixRemove);
            }
        });

        imageViewProfileEditCoverSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("7");
            }
        });
        imageViewProfileEditCoverSevenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("7");
            }
        });
        imageViewProfileEditCoverSevenRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("7", imageViewProfileEditCoverSeven,
                        imageViewProfileEditCoverSevenAdd, imageViewProfileEditCoverSevenRemove);
            }
        });

        imageViewProfileEditCoverEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("8");
            }
        });
        imageViewProfileEditCoverEightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickUploadCover("8");
            }
        });
        imageViewProfileEditCoverEightRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageClickRemoveCover("8", imageViewProfileEditCoverEight,
                        imageViewProfileEditCoverEightAdd, imageViewProfileEditCoverEightRemove);
            }
        });


        textViewProfileEditAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditAbout,
                        "user_about", "About Me", 6);
            }
        });
        linearLayoutProfileEditLooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_looking, textViewProfileEditLooking,
                        "user_looking", "I am looking for");
            }
        });
        linearLayoutProfileEditSeeking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_seeking, textViewProfileEditSeeking,
                        "user_seeking", "I am seeking for");
            }
        });
        linearLayoutProfileEditMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_marital, textViewProfileEditMarital,
                        "user_marital", "Select your Relationship");
            }
        });
        linearLayoutProfileEditSexual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_sexual, textViewProfileEditSexual,
                        "user_sexual", "Select your Sexual");
            }
        });
        linearLayoutProfileEditHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogSeekbar(textViewProfileEditHeight,
                        "user_height", "Select your Height", 140, 240);
            }
        });
        linearLayoutProfileEditWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogSeekbar(textViewProfileEditWeight,
                        "user_weight", "Select your Weight", 40, 160);

            }
        });
        linearLayoutProfileEditAppearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_appearance, textViewProfileEditAppearance,
                        "user_appearance", "Select your Appearance");
            }
        });
        linearLayoutProfileEditFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_feature, textViewProfileEditFeature,
                        "user_feature", "Select your Feature");
            }
        });
        linearLayoutProfileEditEthnicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_ethnicity, textViewProfileEditEthnicity,
                        "user_ethnicity", "Select your Ethnicity");
            }
        });
        linearLayoutProfileEditBodytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_bodytype, textViewProfileEditBodytype,
                        "user_bodytype", "Select your Bodytype");
            }
        });
        linearLayoutProfileEditBodyart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_bodyart, textViewProfileEditBodyart,
                        "user_bodyart", "Select your Bodyart");
            }
        });
        linearLayoutProfileEditEyecolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_eyecolor, textViewProfileEditEyecolor,
                        "user_eyecolor", "Select your Eyecolor");
            }
        });
        linearLayoutProfileEditEyewear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_eyewear, textViewProfileEditEyewear,
                        "user_eyewear", "Select your Eyewear");
            }
        });
        linearLayoutProfileEditHaircolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_haircolor, textViewProfileEditHaircolor,
                        "user_haircolor", "Select your Haircolor");
            }
        });
        linearLayoutProfileEditStarsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_starsign, textViewProfileEditStarsign,
                        "user_starsign", "Select your Starsign");
            }
        });
        linearLayoutProfileEditJobtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditJobtitle,
                        "user_jobtitle", "Select your Jobtitle", 1);
            }
        });
        linearLayoutProfileEditCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditCompany,
                        "user_company", "Select your Company", 1);

            }
        });
        linearLayoutProfileEditIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_income, textViewProfileEditIncome,
                        "user_income", "Select your Income");
            }
        });
        linearLayoutProfileEditEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_education, textViewProfileEditEducation,
                        "user_education", "Select your Education");
            }
        });
        linearLayoutProfileEditLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogCheck(string_array_user_language, textViewProfileEditLanguage,
                        "user_language", "Select your Language");
            }
        });
        linearLayoutProfileEditReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_religion, textViewProfileEditReligion,
                        "user_religion", "Select your Religion");
            }
        });
        linearLayoutProfileEditDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_drinking, textViewProfileEditDrinking,
                        "user_drinking", "Select your Drinking");
            }
        });
        linearLayoutProfileEditSmoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_smoking, textViewProfileEditSmoking,
                        "user_smoking", "Select your Smoking");
            }
        });
        linearLayoutProfileEditLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_living, textViewProfileEditLiving,
                        "user_living", "Select your Living");
            }
        });
        linearLayoutProfileEditRelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_relocation, textViewProfileEditRelocation,
                        "user_relocation", "Select your Relocation");
            }
        });
        textViewProfileEditInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditInterests,
                        "user_interests", "I am interests for", 6);
            }
        });
        textViewProfileEditActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditActivities,
                        "user_activities", "I am activities for", 6);
            }
        });
        textViewProfileEditMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditMovies,
                        "user_movies", "I am movies for", 6);
            }
        });
        textViewProfileEditMusics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditMusics,
                        "user_musics", "I am musics for", 6);
            }
        });
        textViewProfileEditTvshows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditTvshows,
                        "user_tvshows", "I am tvshows for", 6);
            }
        });
        textViewProfileEditSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditSports,
                        "user_sports", "I am sports for", 6);
            }
        });
        textViewProfileEditBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewProfileEditBooks,
                        "user_books", "I am books for", 6);
            }
        });


    }


    private void ImageClickActiveCovers(String stringCover, String stringCoverImage) {


        if (stringCover.equals("0")) {

            imageViewProfileEditCoverZero.setClickable(false);
            imageViewProfileEditCoverZeroAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverZeroRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverZero);

        } else if (stringCover.equals("1")) {

            imageViewProfileEditCoverOne.setClickable(false);
            imageViewProfileEditCoverOneAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverOneRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverOne);

        } else if (stringCover.equals("2")) {

            imageViewProfileEditCoverTwo.setClickable(false);
            imageViewProfileEditCoverTwoAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverTwoRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverTwo);

        } else if (stringCover.equals("3")) {

            imageViewProfileEditCoverThree.setClickable(false);
            imageViewProfileEditCoverThreeAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverThreeRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverThree);

        } else if (stringCover.equals("4")) {

            imageViewProfileEditCoverFour.setClickable(false);
            imageViewProfileEditCoverFourAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverFourRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverFour);

        } else if (stringCover.equals("5")) {

            imageViewProfileEditCoverFive.setClickable(false);
            imageViewProfileEditCoverFiveAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverFiveRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverFive);

        } else if (stringCover.equals("6")) {

            imageViewProfileEditCoverSix.setClickable(false);
            imageViewProfileEditCoverSixAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverSixRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverSix);


        } else if (stringCover.equals("7")) {

            imageViewProfileEditCoverSeven.setClickable(false);
            imageViewProfileEditCoverSevenAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverSevenRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverSeven);

        } else if (stringCover.equals("8")) {

            imageViewProfileEditCoverEight.setClickable(false);
            imageViewProfileEditCoverEightAdd.setVisibility(View.GONE);
            imageViewProfileEditCoverEightRemove.setVisibility(View.VISIBLE);
            Picasso.get().load(stringCoverImage).into(imageViewProfileEditCoverEight);

        }


    }


    private void ImageClickRemoveCover(String stringCover,
                                       RoundedImageView imageView,
                                       CircleImageView imageViewAdd,
                                       CircleImageView imageViewRemove) {


        if (stringCover.equals("0")) {

            String coverName = currentUser + "user_cover";
            Map<String, Object> mapCover = new HashMap<>();
            mapCover.put("user_cover", "cover");
            mapCover.put("user_image", "image");
            mapCover.put("user_thumb", "thumb");

            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(mapCover)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                firebaseFirestore.collection("feeds").document(coverName).delete();

                                imageView.setClickable(true);
                                imageViewAdd.setVisibility(View.VISIBLE);
                                imageViewRemove.setVisibility(View.GONE);
                                Picasso.get().load(R.drawable.profile_image).into(imageView);
                            }
                        }
                    });

        } else {

            String coverName = currentUser + "user_cover" + stringCover;
            Map<String, Object> mapCover = new HashMap<>();
            mapCover.put("user_cover" + stringCover, FieldValue.delete());

            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(mapCover)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                firebaseFirestore.collection("feeds").document(coverName).delete();

                                imageView.setClickable(true);
                                imageViewAdd.setVisibility(View.VISIBLE);
                                imageViewRemove.setVisibility(View.GONE);
                                Picasso.get().load(R.drawable.profile_image).into(imageView);
                            }
                        }
                    });

        }
    }


    public void ImageClickUploadCover(String imageNumber) {

        ImageUploadNumber = imageNumber;

        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"),
                GALLERY_IMAGE_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_IMAGE_IMAGE && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                final Uri resultUri = result.getUri();
                File fileThumb = new File(resultUri.getPath());
                String filePath = fileThumb.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUser = firebaseUser.getUid();

                // int carouselPosition = carouselView.getCurrentItem();
                //String carouselNumber = String.valueOf(carouselPosition);

                if (ImageUploadNumber.equals("0")) {

                    String currentCover = "cover_";
                    ThumbUpload(currentUser, bitmap, currentCover, ImageUploadNumber);

                } else {
                    String currentCover = "cover" + ImageUploadNumber + "_";
                    CoverUpload(currentUser, bitmap, currentCover, ImageUploadNumber);

                }


                dialog = new ProgressDialog(ProfileEditActivity.this);
                dialog.setTitle("Please Wait");
                dialog.setMessage("Uploading Photo...");
                dialog.setCancelable(false);
                dialog.show();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void ThumbUpload(final String currentUser, final Bitmap bitmap, final String currentCover, final String carouselNumber) {

        bitmapThumb = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        ByteArrayOutputStream baosThumb = new ByteArrayOutputStream();
        bitmapThumb.compress(Bitmap.CompressFormat.JPEG, 75, baosThumb);
        byte[] byteThumb = baosThumb.toByteArray();

        final StorageReference storageThumb = storageReference
                .child(currentUser)
                .child("images")
                .child("user")
                .child("thumb_" + currentUser + ".jpg");
        storageThumb.putBytes(byteThumb)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            storageThumb.getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri uriThumb = task.getResult();
                                            stringThumb = uriThumb.toString();

                                            Map<String, Object> mapThumb = new HashMap<>();
                                            mapThumb.put("user_thumb", stringThumb);

                                            firebaseFirestore.collection("users")
                                                    .document(currentUser)
                                                    .update(mapThumb)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                ImageUpload(currentUser, bitmap, currentCover, carouselNumber);
                                                            } else {
                                                                Toast.makeText(ProfileEditActivity.this,
                                                                        "Something went wrong. Please try again!",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });
    }


    private void ImageUpload(final String currentUser, final Bitmap bitmap, final String currentCover, final String carouselNumber) {


        bitmapImage = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        ByteArrayOutputStream baosImage = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 75, baosImage);
        byte[] byteImage = baosImage.toByteArray();

        final StorageReference storageImage = storageReference
                .child(currentUser)
                .child("images")
                .child("user")
                .child("image_" + currentUser + ".jpg");
        storageImage.putBytes(byteImage)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage) {
                        if (taskImage.isSuccessful()) {

                            storageImage.getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri uriImage = task.getResult();
                                            stringImage = uriImage.toString();

                                            Map<String, Object> mapImage = new HashMap<>();
                                            mapImage.put("user_image", stringImage);

                                            firebaseFirestore.collection("users")
                                                    .document(currentUser)
                                                    .update(mapImage)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                CoverUpload(currentUser, bitmap, currentCover, carouselNumber);
                                                            } else {
                                                                Toast.makeText(ProfileEditActivity.this,
                                                                        "Something went wrong. Please try again!",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });

    }

    private void CoverUpload(final String currentUser, final Bitmap bitmap, final String currentCover, final String carouselNumber) {

        bitmapCover = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
        ByteArrayOutputStream baosCover = new ByteArrayOutputStream();
        bitmapCover.compress(Bitmap.CompressFormat.JPEG, 75, baosCover);
        byte[] byteCover = baosCover.toByteArray();


        final StorageReference storageCover = storageReference
                .child(currentUser)
                .child("images")
                .child("user")
                .child(currentCover + currentUser + ".jpg");
        storageCover.putBytes(byteCover)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage) {
                        if (taskImage.isSuccessful()) {

                            storageCover.getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            final Uri uriCover = task.getResult();
                                            stringCover = uriCover.toString();

                                            String userCoverNumber;

                                            if (carouselNumber.equals("0")) {
                                                userCoverNumber = "user_cover";
                                            } else {
                                                userCoverNumber = "user_cover" + carouselNumber;
                                            }


                                            Map<String, Object> mapCover = new HashMap<>();
                                            mapCover.put(userCoverNumber, stringCover);

                                            String stringFeedId = currentUser + userCoverNumber;

                                            firebaseFirestore.collection("users")
                                                    .document(currentUser)
                                                    .update(mapCover)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {

                                                                Map<String, Object> feedCover = new HashMap<>();
                                                                feedCover.put("feed_date", Timestamp.now());
                                                                feedCover.put("feed_user", currentUser);
                                                                feedCover.put("feed_cover", stringCover);
                                                                feedCover.put("feed_like", "0");
                                                                feedCover.put("feed_uid", stringFeedId);

                                                                firebaseFirestore.collection("users")
                                                                        .document(currentUser)
                                                                        .get()
                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    String show_feeds = task.getResult().getString("show_feeds");
                                                                                    if (show_feeds.equals("yes")) {
                                                                                        feedCover.put("feed_show", "yes");


                                                                                        firebaseFirestore.collection("feeds")
                                                                                                .document(stringFeedId)
                                                                                                .set(feedCover)
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {

                                                                                                            Toast.makeText(ProfileEditActivity.this,
                                                                                                                    "Photo uploaded successfully!!",
                                                                                                                    Toast.LENGTH_SHORT).show();

                                                                                                            ImageClickActiveCovers(carouselNumber, stringCover);
                                                                                                            dialog.dismiss();

                                                                                                        } else {

                                                                                                            Toast.makeText(ProfileEditActivity.this,
                                                                                                                    "Something went wrong. Please try again!",
                                                                                                                    Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    } else {
                                                                                        feedCover.put("feed_show", "no");

                                                                                        String stringFeedId = currentUser + userCoverNumber;

                                                                                        firebaseFirestore.collection("feeds")
                                                                                                .document(stringFeedId)
                                                                                                .set(feedCover)
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {

                                                                                                            Toast.makeText(ProfileEditActivity.this,
                                                                                                                    "Photo uploaded successfully!!",
                                                                                                                    Toast.LENGTH_SHORT).show();

                                                                                                            ImageClickActiveCovers(carouselNumber, stringCover);
                                                                                                            dialog.dismiss();

                                                                                                        } else {

                                                                                                            Toast.makeText(ProfileEditActivity.this,
                                                                                                                    "Something went wrong. Please try again!",
                                                                                                                    Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }

                                                                                }
                                                                            }
                                                                        });

                                                            } else {
                                                                Toast.makeText(ProfileEditActivity.this,
                                                                        "Something went wrong. Please try again!",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });


    }


    @Override
    protected void onStart() {
        super.onStart();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = firebaseUser.getUid();


        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

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


                            String user_cover_zero = documentSnapshot.getString("user_cover");
                            String user_cover_one = documentSnapshot.getString("user_cover1");
                            String user_cover_two = documentSnapshot.getString("user_cover2");
                            String user_cover_three = documentSnapshot.getString("user_cover3");
                            String user_cover_four = documentSnapshot.getString("user_cover4");
                            String user_cover_five = documentSnapshot.getString("user_cover5");
                            String user_cover_six = documentSnapshot.getString("user_cover6");
                            String user_cover_seven = documentSnapshot.getString("user_cover7");
                            String user_cover_eight = documentSnapshot.getString("user_cover8");


                            if (user_cover_zero.equals("cover")) {
                                imageViewProfileEditCoverZero.setImageResource(R.drawable.profile_image);
                            } else {

                                Picasso.get().load(user_cover_zero).into(imageViewProfileEditCoverZero);
                                imageViewProfileEditCoverZero.setClickable(false);
                                imageViewProfileEditCoverZeroAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverZeroRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_one != null) {
                                Picasso.get().load(user_cover_one).into(imageViewProfileEditCoverOne);
                                imageViewProfileEditCoverOne.setClickable(false);
                                imageViewProfileEditCoverOneAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverOneRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_two != null) {

                                Picasso.get().load(user_cover_two).into(imageViewProfileEditCoverTwo);
                                imageViewProfileEditCoverTwo.setClickable(false);
                                imageViewProfileEditCoverTwoAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverTwoRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_three != null) {

                                Picasso.get().load(user_cover_three).into(imageViewProfileEditCoverThree);
                                imageViewProfileEditCoverThree.setClickable(false);
                                imageViewProfileEditCoverThreeAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverThreeRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_four != null) {

                                Picasso.get().load(user_cover_four).into(imageViewProfileEditCoverFour);
                                imageViewProfileEditCoverFour.setClickable(false);
                                imageViewProfileEditCoverFourAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverFourRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_five != null) {

                                Picasso.get().load(user_cover_five).into(imageViewProfileEditCoverFive);
                                imageViewProfileEditCoverFive.setClickable(false);
                                imageViewProfileEditCoverFiveAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverFiveRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_six != null) {

                                Picasso.get().load(user_cover_six).into(imageViewProfileEditCoverSix);
                                imageViewProfileEditCoverSix.setClickable(false);
                                imageViewProfileEditCoverSixAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverSixRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_seven != null) {

                                Picasso.get().load(user_cover_seven).into(imageViewProfileEditCoverSeven);
                                imageViewProfileEditCoverSeven.setClickable(false);
                                imageViewProfileEditCoverSevenAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverSevenRemove.setVisibility(View.VISIBLE);

                            }
                            if (user_cover_eight != null) {

                                Picasso.get().load(user_cover_eight).into(imageViewProfileEditCoverEight);
                                imageViewProfileEditCoverEight.setClickable(false);
                                imageViewProfileEditCoverEightAdd.setVisibility(View.GONE);
                                imageViewProfileEditCoverEightRemove.setVisibility(View.VISIBLE);

                            }


                            if (user_about != null) {
                                textViewProfileEditAbout.setText(user_about);
                            }
                            if (user_looking != null) {
                                textViewProfileEditLooking.setText(user_looking);
                            }
                            if (user_seeking != null) {
                                textViewProfileEditSeeking.setText(user_seeking);
                            }
                            if (user_marital != null) {
                                textViewProfileEditMarital.setText(user_marital);
                            }
                            if (user_sexual != null) {
                                textViewProfileEditSexual.setText(user_sexual);
                            }
                            if (user_height != null) {
                                textViewProfileEditHeight.setText(user_height);
                            }
                            if (user_weight != null) {
                                textViewProfileEditWeight.setText(user_weight);
                            }
                            if (user_appearance != null) {
                                textViewProfileEditAppearance.setText(user_appearance);
                            }
                            if (user_feature != null) {
                                textViewProfileEditFeature.setText(user_feature);
                            }
                            if (user_ethnicity != null) {
                                textViewProfileEditEthnicity.setText(user_ethnicity);
                            }
                            if (user_bodytype != null) {
                                textViewProfileEditBodytype.setText(user_bodytype);
                            }
                            if (user_bodyart != null) {
                                textViewProfileEditBodyart.setText(user_bodyart);
                            }
                            if (user_eyecolor != null) {
                                textViewProfileEditEyecolor.setText(user_eyecolor);
                            }
                            if (user_eyewear != null) {
                                textViewProfileEditEyewear.setText(user_eyewear);
                            }
                            if (user_haircolor != null) {
                                textViewProfileEditHaircolor.setText(user_haircolor);
                            }
                            if (user_starsign != null) {
                                textViewProfileEditStarsign.setText(user_starsign);
                            }
                            if (user_jobtitle != null) {
                                textViewProfileEditJobtitle.setText(user_jobtitle);
                            }
                            if (user_company != null) {
                                textViewProfileEditCompany.setText(user_company);
                            }
                            if (user_income != null) {
                                textViewProfileEditIncome.setText(user_income);
                            }
                            if (user_education != null) {
                                textViewProfileEditEducation.setText(user_education);
                            }
                            if (user_language != null) {
                                textViewProfileEditLanguage.setText(user_language);
                            }
                            if (user_religion != null) {
                                textViewProfileEditReligion.setText(user_religion);
                            }
                            if (user_drinking != null) {
                                textViewProfileEditDrinking.setText(user_drinking);
                            }
                            if (user_smoking != null) {
                                textViewProfileEditSmoking.setText(user_smoking);
                            }
                            if (user_living != null) {
                                textViewProfileEditLiving.setText(user_living);
                            }
                            if (user_relocation != null) {
                                textViewProfileEditRelocation.setText(user_relocation);
                            }
                            if (user_interests != null) {
                                textViewProfileEditInterests.setText(user_interests);
                            }
                            if (user_activities != null) {
                                textViewProfileEditActivities.setText(user_activities);
                            }
                            if (user_movies != null) {
                                textViewProfileEditMovies.setText(user_movies);
                            }
                            if (user_musics != null) {
                                textViewProfileEditMusics.setText(user_musics);
                            }
                            if (user_tvshows != null) {
                                textViewProfileEditTvshows.setText(user_tvshows);
                            }
                            if (user_sports != null) {
                                textViewProfileEditSports.setText(user_sports);
                            }
                            if (user_books != null) {
                                textViewProfileEditBooks.setText(user_books);
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
                .document(currentUser)
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


    private void ProfileDialogCheck(
            final String[] dialogArray,
            final TextView dialogTextView,
            final String dialogUser,
            final String dialogTitle) {


        final ArrayList<Integer> arrayIntegers = new ArrayList<>();

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        //AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ProfileEditActivity.this, R.style.AlertDialogCustom));

        builder.setTitle(dialogTitle);
        // builder.setCancelable(false);

        String dialogTextString = dialogTextView.getText().toString();
        boolean[] dialogChecked = new boolean[dialogArray.length];

        for (int i = 0; i < dialogArray.length; i++) {
            if (dialogTextString.contains(dialogArray[i])) {
                dialogChecked[i] = true;
                arrayIntegers.add(i);

            } else {
                dialogChecked[i] = false;
            }
        }


        // add a checkbox list
        //boolean[] dialogChecked = {true, false, false, true, false};
        //final boolean[] dialogChecked = new boolean[dialogArray.length];
        builder.setMultiChoiceItems(dialogArray, dialogChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked) {
                    arrayIntegers.add(which);
                } else {
                    arrayIntegers.remove((Integer.valueOf(which)));
                }
            }
        });


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < arrayIntegers.size(); i++) {
                    item = item + dialogArray[arrayIntegers.get(i)];
                    if (i != arrayIntegers.size() - 1) {
                        item = item + ", ";
                    }
                }


                if (item == "") {
                    dialogTextView.setText("Add");
                    Toast.makeText(ProfileEditActivity.this,
                            "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    dialogTextView.setText(item);
                    ProfileUpdate(dialogUser, item);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        //final boolean[] dialogchecks = dialogChecked;

        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < dialogChecked.length; i++) {
                    dialogTextView.setText("Add");
                    dialogChecked[i] = false;
                    arrayIntegers.clear();
                    ProfileDelete(dialogUser);
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void ProfileUpdate(final String user_key, final String user_value) {

        final Map<String, Object> mapProfileUpdate = new HashMap<>();
        mapProfileUpdate.put(user_key, user_value);

        firebaseFirestore.collection("users")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .update(mapProfileUpdate);
                        } else {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .set(mapProfileUpdate);
                        }
                    }
                });
    }

    private void ProfileDelete(final String user_key) {

        final Map<String, Object> mapProfileDelete = new HashMap<>();
        mapProfileDelete.put(user_key, FieldValue.delete());

        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(mapProfileDelete);
    }


    private void ProfileDialogRadio(
            final String[] dialogArray,
            final TextView dialogTextView,
            final String dialogUser,
            final String dialogTitle) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder.setTitle(dialogTitle);
        //builder.setCancelable(false);

        String dialogTextString = dialogTextView.getText().toString();
        int checkedPosition = new ArrayList<String>(Arrays.asList(dialogArray)).indexOf(dialogTextString);

        // add a radio button list
        int checkedItem = checkedPosition; // cow
        builder.setSingleChoiceItems(dialogArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int selectedWhich = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                if (selectedWhich != -1) {
                    dialogTextView.setText(dialogArray[selectedWhich]);
                    ProfileUpdate(dialogUser, dialogArray[selectedWhich]);
                } else {
                    Toast.makeText(ProfileEditActivity.this,
                            "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogTextView.setText("Add");
                ProfileDelete(dialogUser);
            }
        });

        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ProfileDialogInput(
            final TextView dialogTextView,
            final String dialogUser,
            final String dialogTitle,
            final int dialogLines) {


        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder.setTitle(dialogTitle);
        // builder.setCancelable(false);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_input, null);
        builder.setView(customLayout);

        EditText editTextAboutMe = customLayout.findViewById(R.id.editTextProfileEditInput);
        editTextAboutMe.setMinLines(dialogLines);

        String dialogTextString = dialogTextView.getText().toString();
        if (!dialogTextString.equals("Add")) {
            editTextAboutMe.setText(dialogTextString);
        }


        // add a button
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity

                String stringEditTextAboutMe = editTextAboutMe.getText().toString();

                if (!stringEditTextAboutMe.equals("")) {
                    dialogTextView.setText(stringEditTextAboutMe);
                    ProfileUpdate(dialogUser, stringEditTextAboutMe);
                } else {
                    Toast.makeText(ProfileEditActivity.this,
                            "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogTextView.setText("Add");
                ProfileDelete(dialogUser);
            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void ProfileDialogSeekbar(
            final TextView dialogTextView,
            final String dialogUser,
            final String dialogTitle,
            final int dialogMin,
            final int dialogMax) {


        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditActivity.this);
        builder.setTitle(dialogTitle);
        // builder.setCancelable(false);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_seekbar, null);
        builder.setView(customLayout);


        TextView textViewProfileEditSeekbarLeft = customLayout.findViewById(R.id.textViewProfileEditSeekbarLeft);
        TextView textViewProfileEditSeekbarRight = customLayout.findViewById(R.id.textViewProfileEditSeekbarRight);
        CrystalSeekbar seekbarProfileEditSeekbarSlider = customLayout.findViewById(R.id.seekbarProfileEditSeekbarSlider);
        seekbarProfileEditSeekbarSlider.setMinValue(dialogMin);
        seekbarProfileEditSeekbarSlider.setMaxValue(dialogMax);


        String dialogTextString = dialogTextView.getText().toString();

        if (!dialogTextString.equals("Add")) {

            String[] splitDialogTextView = new String[10];
            int valueDialogTextView;

            if (dialogUser.equals("user_height")) {
                splitDialogTextView = dialogTextString.split("cm");
            }
            if (dialogUser.equals("user_weight")) {
                splitDialogTextView = dialogTextString.split("kg");
            }

            valueDialogTextView = Integer.valueOf(splitDialogTextView[0]);
            seekbarProfileEditSeekbarSlider.setMinStartValue(valueDialogTextView);
            seekbarProfileEditSeekbarSlider.apply();
        }


        seekbarProfileEditSeekbarSlider.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {

                if (dialogUser.equals("user_height")) {
                    String stringSeekbarLeft = String.valueOf(value);
                    textViewProfileEditSeekbarLeft.setText(stringSeekbarLeft + "cm");

                    double doubleFeet = Double.valueOf(stringSeekbarLeft) * 0.3937 / 12;
                    String stringFeet = String.format("%.2f", doubleFeet);
                    String[] splitFeet = stringFeet.split("\\.");
                    String finalFeet = splitFeet[0] + "ft ";

                    double doubleInch = Double.valueOf("." + splitFeet[1]) * 12;
                    int intInch = (int) doubleInch;
                    String finalInch = String.valueOf(intInch) + "in";

                    String Height = finalFeet + finalInch;
                    textViewProfileEditSeekbarRight.setText(Height);
                }
                if (dialogUser.equals("user_weight")) {
                    String stringSeekbarLeft = String.valueOf(value);
                    textViewProfileEditSeekbarLeft.setText(stringSeekbarLeft + "kg");

                    double doublePound = Double.valueOf(stringSeekbarLeft) * 2.2046;
                    int intPound = (int) doublePound;
                    String finalPound = String.valueOf(intPound) + "lb";

                    textViewProfileEditSeekbarRight.setText(finalPound);
                }

            }
        });


        // add a button
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity

                String stringEditTextLeft = textViewProfileEditSeekbarLeft.getText().toString();
                String stringEditTextRight = textViewProfileEditSeekbarRight.getText().toString();

                String stringFinalAdd = stringEditTextLeft + " (" + stringEditTextRight + ")";
                dialogTextView.setText(stringFinalAdd);
                ProfileUpdate(dialogUser, stringFinalAdd);


            }
        });

        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogTextView.setText("Add");
                ProfileDelete(dialogUser);
            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void AgeUpdate(String userBirthday, String userBirthage) {

        int intCurrentBirthage = Integer.valueOf(AgeUser(userBirthday));
        int intRegisterBirthage = Integer.valueOf(userBirthage);

        if (intCurrentBirthage > intRegisterBirthage) {

            Map<String, Object> arrayAgeUpdate = new HashMap<>();
            arrayAgeUpdate.put("user_birthage", String.valueOf(intCurrentBirthage));

            firebaseFirestore.collection("users")
                    .document(currentUser)
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




