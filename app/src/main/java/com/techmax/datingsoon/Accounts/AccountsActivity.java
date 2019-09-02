package com.techmax.datingsoon.Accounts;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.techmax.datingsoon.Main.MainActivity;
import com.techmax.datingsoon.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountsActivity extends AppCompatActivity {


    CircleImageView circleImageViewProfileAvatar;
    ViewPager viewPagerAccount;
    TabLayout tabLayoutAccount;
    AccountsAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private FloatingActionButton viewPagerBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts_activity);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarToolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        viewPagerAccount = (ViewPager) findViewById(R.id.viewPagerAccount);
        viewPagerAccount.setOffscreenPageLimit(7);

        viewPagerBack = findViewById(R.id.viewPagerBack);

        tabLayoutAccount = (TabLayout) findViewById(R.id.tabLayoutAccount);

        tabLayoutAccount.addTab(tabLayoutAccount.newTab().setText("Matches"));
        tabLayoutAccount.addTab(tabLayoutAccount.newTab().setText("Likes"));
        tabLayoutAccount.addTab(tabLayoutAccount.newTab().setText("Visits"));
        tabLayoutAccount.addTab(tabLayoutAccount.newTab().setText("Favorite"));


        tabLayoutAccount.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new AccountsAdapter
                (getSupportFragmentManager(), tabLayoutAccount.getTabCount());
        viewPagerAccount.setAdapter(adapter);

        viewPagerAccount.setCurrentItem(0);


        String tabShow;
        tabShow = getIntent().getStringExtra("tab_show");
        if (tabShow != null) {
            switch (tabShow) {
                case "tab_matches":
                    viewPagerAccount.setCurrentItem(0);
                    break;
                case "tab_likes":
                    viewPagerAccount.setCurrentItem(1);
                    break;
                case "tab_visitors":
                    viewPagerAccount.setCurrentItem(2);
                    break;
                case "tab_favorites":
                    viewPagerAccount.setCurrentItem(3);
                    break;
            }
        }

        viewPagerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AccountsActivity.this, MainActivity.class);
                intent.putExtra("tab_show", "tab_profile");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        viewPagerAccount.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutAccount));
        tabLayoutAccount.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerAccount.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    private void UserStatus(String status) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = firebaseUser.getUid();

        Map<String, Object> arrayUserStatus = new HashMap<>();
        arrayUserStatus.put("user_status", status);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(arrayUserStatus);
    }


    private void OnlineUser() {
        String currentUser = firebaseUser.getUid();

        Map<String, Object> arrayOnlineUser = new HashMap<>();
        arrayOnlineUser.put("user_online", Timestamp.now());

        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(arrayOnlineUser);
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserStatus("online");
        OnlineUser();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserStatus("offline");
    }
}
