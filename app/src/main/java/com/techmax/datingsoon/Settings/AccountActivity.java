package com.techmax.datingsoon.Settings;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.techmax.datingsoon.Extra.DateClass;
import com.techmax.datingsoon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String currentUser;

    Toolbar toolbarAccountUserToolbar;

    LinearLayout linearLayoutAccountUserUsername;
    LinearLayout linearLayoutAccountUserBirthday;
    LinearLayout linearLayoutAccountUserGender;
    LinearLayout linearLayoutAccountUserCity;
    LinearLayout linearLayoutAccountUserState;
    LinearLayout linearLayoutAccountUserCountry;
    LinearLayout linearLayoutAccountUserEmail;
    LinearLayout linearLayoutAccountUserMobile;
    LinearLayout linearLayoutAccountUserPassword;

    TextView textViewAccountUserUsername;
    TextView textViewAccountUserBirthday;
    TextView textViewAccountUserGender;
    TextView textViewAccountUserCity;
    TextView textViewAccountUserState;
    TextView textViewAccountUserCountry;
    TextView textViewAccountUserEmail;
    TextView textViewAccountUserMobile;


    String[] string_array_user_gender;

    String user_email;
    String user_epass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();

        linearLayoutAccountUserUsername = findViewById(R.id.linearLayoutAccountUserUsername);
        linearLayoutAccountUserBirthday = findViewById(R.id.linearLayoutAccountUserBirthday);
        linearLayoutAccountUserGender = findViewById(R.id.linearLayoutAccountUserGender);
        linearLayoutAccountUserCity = findViewById(R.id.linearLayoutAccountUserCity);
        linearLayoutAccountUserState = findViewById(R.id.linearLayoutAccountUserState);
        linearLayoutAccountUserCountry = findViewById(R.id.linearLayoutAccountUserCountry);
        linearLayoutAccountUserEmail = findViewById(R.id.linearLayoutAccountUserEmail);
        linearLayoutAccountUserMobile = findViewById(R.id.linearLayoutAccountUserMobile);
        linearLayoutAccountUserPassword = findViewById(R.id.linearLayoutAccountUserPassword);

        textViewAccountUserUsername = findViewById(R.id.textViewAccountUserUsername);
        textViewAccountUserBirthday = findViewById(R.id.textViewAccountUserBirthday);
        textViewAccountUserGender = findViewById(R.id.textViewAccountUserGender);
        textViewAccountUserCity = findViewById(R.id.textViewAccountUserCity);
        textViewAccountUserState = findViewById(R.id.textViewAccountUserState);
        textViewAccountUserCountry = findViewById(R.id.textViewAccountUserCountry);
        textViewAccountUserEmail = findViewById(R.id.textViewAccountUserEmail);
        textViewAccountUserMobile = findViewById(R.id.textViewAccountUserMobile);

        string_array_user_gender = getResources().getStringArray(R.array.string_array_user_gender);

        toolbarAccountUserToolbar = findViewById(R.id.toolbarAccountUserToolbar);
        setSupportActionBar(toolbarAccountUserToolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAccountUserToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linearLayoutAccountUserUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogInput(textViewAccountUserUsername,
                        "user_name", "First Name", 1);
            }
        });

        linearLayoutAccountUserGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialogRadio(string_array_user_gender, textViewAccountUserGender,
                        "user_gender", "Select your Gender");
            }
        });

        linearLayoutAccountUserBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateClass();
                datePicker.show(getSupportFragmentManager(), "Date Picker");

            }
        });


        linearLayoutAccountUserMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileDialogInput(textViewAccountUserMobile,
                        "user_mobile", "Mobile Number", 1);
            }
        });

        linearLayoutAccountUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AccountActivity.this, PasswordActivity.class);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_epass", user_epass);
                startActivity(intent);
            }
        });

    }


    private void ProfileDialogRadio(
            final String[] dialogArray,
            final TextView dialogTextView,
            final String dialogUser,
            final String dialogTitle) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
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
                dialogTextView.setText(dialogArray[selectedWhich]);
                ProfileUpdate(dialogUser, dialogArray[selectedWhich]);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle(dialogTitle);
        // builder.setCancelable(false);


        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_input, null);
        builder.setView(customLayout);

        EditText editTextAboutMe = customLayout.findViewById(R.id.editTextProfileEditInput);
        editTextAboutMe.setMinLines(dialogLines);

        String dialogTextString = dialogTextView.getText().toString();
        editTextAboutMe.setText(dialogTextString);


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
                    Toast.makeText(AccountActivity.this,
                            "Sorry! Could not save empty fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    private void ProfileUpdate(final String user_key, final String user_value) {

        final Map<String, Object> mapProfileUpdate = new HashMap<>();
        mapProfileUpdate.put(user_key, user_value);

        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(mapProfileUpdate);
    }


    @Override
    protected void onStart() {
        super.onStart();


        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

                            String user_name = documentSnapshot.getString("user_name");
                            String user_birthday = documentSnapshot.getString("user_birthday");
                            String user_birthage = documentSnapshot.getString("user_birthage");
                            String user_gender = documentSnapshot.getString("user_gender");

                            String user_city = documentSnapshot.getString("user_city");
                            String user_state = documentSnapshot.getString("user_state");
                            String user_country = documentSnapshot.getString("user_country");

                            user_email = documentSnapshot.getString("user_email");
                            user_epass = documentSnapshot.getString("user_epass");
                            String user_mobile = documentSnapshot.getString("user_mobile");


                            textViewAccountUserUsername.setText(user_name);
                            textViewAccountUserBirthday.setText("(" + user_birthage + "yrs) " + user_birthday);
                            textViewAccountUserGender.setText(user_gender);
                            textViewAccountUserCity.setText(user_city);
                            textViewAccountUserState.setText(user_state);
                            textViewAccountUserCountry.setText(user_country);
                            textViewAccountUserEmail.setText(user_email);
                            textViewAccountUserMobile.setText(user_mobile);


                        }
                    }
                });

    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        String strDate = format.format(calendar.getTime());


        if (year > 2000) {
            Toast.makeText(this,
                    "Sorry! you dont meet our user registration minimum age limits policy now. Please register with us after some time. Thank you for trying our app now!",
                    Toast.LENGTH_LONG).show();

        } else {
            AgeUser(strDate);
        }

    }


    private void AgeUser(String stringDateUser) {

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

        String finalString = "(" + ageS + "yrs) " + stringDateUser;

        textViewAccountUserBirthday.setText(finalString);

        ProfileUpdate("user_birthday", stringDateUser);

        ProfileUpdate("user_birthage", ageS);

    }
}
