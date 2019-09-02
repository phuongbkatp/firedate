package com.techmax.datingsoon.Start;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.techmax.datingsoon.Extra.DateClass;
import com.techmax.datingsoon.Main.MainActivity;
import com.techmax.datingsoon.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RemindActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String string_city;
    String string_state;
    String string_country;
    String string_location;
    String stringLatitude;
    String stringLongitude;
    String stringLooking;

    AuthCredential authCredential;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private String currentUser;
    private String stringEmail;
    private String stringEpass;
    private Button btnRemind;
    private EditText editTextRemindPassword;
    private RadioGroup radioGroupRemindGender;
    private RadioButton radioButtonRemindGender;
    private TextView textViewRemindBirthday;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    LocationManager locationManager;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnRemind = (Button) findViewById(R.id.btnRemind);
        editTextRemindPassword = (EditText) findViewById(R.id.editTextRemindPassword);

        textViewRemindBirthday = (TextView) findViewById(R.id.textViewRemindBirthday);

        radioGroupRemindGender = findViewById(R.id.radioGroupRemindGender);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationPremissionCheck();
        GooglePlayServiceCheck();
        GPSLocationServiceCheck();

        textViewRemindBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateClass();
                datePicker.show(getSupportFragmentManager(), "Date Picker");

            }
        });

        btnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioButtonRemindGender != null) {
                    if (string_city != null && !string_city.equals("city") &&
                            string_state != null && !string_state.equals("state") &&
                            string_country != null && !string_country.equals("country")) {

                        final String stringGender = radioButtonRemindGender.getText().toString();
                        final String stringBirthday = textViewRemindBirthday.getText().toString();

                        if (stringGender.equals("Male")) {
                            stringLooking = "Woman";
                        } else {
                            stringLooking = "Man";
                        }

                        if (!TextUtils.isEmpty(stringGender) ||
                                !TextUtils.isEmpty(stringBirthday)) {

                            if (!stringBirthday.equals("")) {


                                dialog = new ProgressDialog(RemindActivity.this);
                                dialog.setTitle("Please Wait");
                                dialog.setMessage("Setting Profile...");
                                dialog.setCancelable(false);
                                dialog.show();

                                ProfileUpdate(stringGender, stringBirthday);

                            } else {

                                Toast.makeText(RemindActivity.this, "Please Fill in all the details to proceed!", Toast.LENGTH_SHORT).show();

                            }


                        } else {

                            Toast.makeText(RemindActivity.this, "Please Fill in all the details to proceed!", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(RemindActivity.this, "Please turn on Location service to continue.", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(RemindActivity.this, "Please Fill in all the details to proceed!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }



    public void radioButtonRemindGender(View view) {
        int radioId = radioGroupRemindGender.getCheckedRadioButtonId();
        radioButtonRemindGender = findViewById(radioId);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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
            textViewRemindBirthday.setText("");

        } else {
            textViewRemindBirthday.setText(strDate);
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

    private void ProfileUpdate(String stringGender, String stringBirthday) {

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("user_gender", stringGender);
        userProfile.put("user_birthday", stringBirthday);
        userProfile.put("user_birthage", AgeUser(stringBirthday));
        userProfile.put("user_city", string_city);
        userProfile.put("user_state", string_state);
        userProfile.put("user_country", string_country);
        userProfile.put("user_location", string_location);
        userProfile.put("user_looking", stringLooking);
        userProfile.put("user_latitude", stringLatitude);
        userProfile.put("user_longitude", stringLongitude);
        userProfile.put("user_dummy", "no");

        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(userProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(RemindActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            dialog.dismiss();

                        } else {

                            dialog.dismiss();
                        }
                    }
                });

    }


    private void LocationPremissionCheck() {

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location Permission")
                .setSettingsDialogTitle("Warning");
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                LocationRequest();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                LocationPremissionCheck();
            }
        });
    }


    private void LocationRetreive(Double locationLatitude, Double locationLongitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(locationLatitude, locationLongitude, 1);
            if (addresses != null && addresses.size() > 0) {
                string_city = addresses.get(0).getLocality();
                string_state = addresses.get(0).getAdminArea();
                string_country = addresses.get(0).getCountryName();
                string_location = addresses.get(0).getAddressLine(0);


                if (string_country == null) {
                    if (string_state != null) {
                        string_country = string_state;
                    } else if (string_city != null) {
                        string_country = string_city;
                    } else {
                        string_country = "null";
                    }
                }
                if (string_city == null) {
                    if (string_state != null) {
                        string_city = string_state;
                    } else {
                        string_city = string_country;
                    }
                }
                if (string_state == null) {
                    if (string_city != null) {
                        string_state = string_city;
                    } else {
                        string_state = string_country;
                    }
                }
                if (string_location == null) {
                    string_location = "Null";
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(RemindActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void LocationRequest() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {


            fusedLocationProviderClient = new FusedLocationProviderClient(this);

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {

                        Double locationLatitude = location.getLatitude();
                        Double locationLongitude = location.getLongitude();

                        stringLatitude = locationLatitude.toString();
                        stringLongitude = locationLongitude.toString();

                        if (!stringLatitude.equals("0.0") && !stringLongitude.equals("0.0")) {

                            LocationRetreive(locationLatitude, locationLongitude);

                        } else {

                            Toast.makeText(RemindActivity.this,
                                    "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(RemindActivity.this,
                                "Please turn on any GPS or location service and restart to use the app", Toast.LENGTH_SHORT).show();
                    }

                }

            });


        } else {

            LocationPremissionCheck();

        }
    }

    public boolean GooglePlayServiceCheck() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    private void GPSLocationServiceCheck() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, enable it to use this app?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                            Intent intent = new Intent(RemindActivity.this, StartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


        GPSLocationServiceCheck();


    }




    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
