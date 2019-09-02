package com.techmax.datingsoon.Settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class ReportActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String currentUser;
    String profileUser;

    TextView textViewProfileReportTitle;
    TextView textViewProfileReportContent;
    CheckBox checkBoxProfileReportFake;
    CheckBox checkBoxProfileReportPhotos;
    CheckBox checkBoxProfileReportSpam;
    CheckBox checkBoxProfileReportAdverts;
    CheckBox checkBoxProfileReportAdult;
    CheckBox checkBoxProfileReportOffence;
    CheckBox checkBoxProfileReportAbusive;
    CheckBox checkBoxProfileReportReligious;
    CheckBox checkBoxProfileReportUnderage;
    EditText editTextProfileReportOther;
    Button buttonProfileReportButton;

    Toolbar toolbarReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbarReport = findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbarReport);
        getSupportActionBar().setTitle("Report User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarReport.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        currentUser = firebaseUser.getUid();
        profileUser = getIntent().getStringExtra("user_uid");


        textViewProfileReportContent = findViewById(R.id.textViewProfileReportContent);
        checkBoxProfileReportFake = findViewById(R.id.checkBoxProfileReportFake);
        checkBoxProfileReportPhotos = findViewById(R.id.checkBoxProfileReportPhotos);
        checkBoxProfileReportSpam = findViewById(R.id.checkBoxProfileReportSpam);
        checkBoxProfileReportAdverts = findViewById(R.id.checkBoxProfileReportAdverts);
        checkBoxProfileReportAdult = findViewById(R.id.checkBoxProfileReportAdult);
        checkBoxProfileReportOffence = findViewById(R.id.checkBoxProfileReportOffence);
        checkBoxProfileReportAbusive = findViewById(R.id.checkBoxProfileReportAbusive);
        checkBoxProfileReportReligious = findViewById(R.id.checkBoxProfileReportReligious);
        checkBoxProfileReportUnderage = findViewById(R.id.checkBoxProfileReportUnderage);
        editTextProfileReportOther = findViewById(R.id.editTextProfileReportOther);
        buttonProfileReportButton = findViewById(R.id.buttonProfileReportButton);


        buttonProfileReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> mapProfileUser = new HashMap<>();
                mapProfileUser.put("report_userby", currentUser);
                mapProfileUser.put("report_userto", profileUser);
                mapProfileUser.put("report_date", Timestamp.now());
                if (checkBoxProfileReportFake.isChecked()) {
                    mapProfileUser.put("report_fake", "This user is fake, fraud and duplicate");
                }
                if (checkBoxProfileReportPhotos.isChecked()) {
                    mapProfileUser.put("report_photos", "This user is using inappropriate photos");
                }
                if (checkBoxProfileReportSpam.isChecked()) {
                    mapProfileUser.put("report_spam", "This user is sending spam messages");
                }
                if (checkBoxProfileReportAdverts.isChecked()) {
                    mapProfileUser.put("report_adverts", "This user is sending advertisements");
                }
                if (checkBoxProfileReportAdult.isChecked()) {
                    mapProfileUser.put("report_adult", "This user is sending adult contents");
                }
                if (checkBoxProfileReportOffence.isChecked()) {
                    mapProfileUser.put("report_offence", "This user is offending me personally");
                }
                if (checkBoxProfileReportAbusive.isChecked()) {
                    mapProfileUser.put("report_abusive", "This user is using abusive languages");
                }
                if (checkBoxProfileReportReligious.isChecked()) {
                    mapProfileUser.put("report_religious", "This user is commenting on religions");
                }
                if (checkBoxProfileReportUnderage.isChecked()) {
                    mapProfileUser.put("report_underage", "This user is child and underage");
                }
                String stringReportOther = editTextProfileReportOther.getText().toString();
                if (!stringReportOther.equals("")) {
                    mapProfileUser.put("report_other", stringReportOther);
                }

                if (mapProfileUser.size() > 3) {

                    firebaseFirestore.collection("report")
                            .document(profileUser)
                            .collection(currentUser)
                            .add(mapProfileUser)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ReportActivity.this,
                                                "User Reported!", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(ReportActivity.this,
                            "Please select report types to send", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
