package com.techmax.datingsoon.Main;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Start.RemindActivity;
import com.techmax.datingsoon.Start.StartActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageViewProfileAvatar;
    ViewPager viewPager;
    MainAdapter adapter;
    HashMap<Integer, String> map = new HashMap<>();
    ArrayList<Integer> arrayInteger = new ArrayList<Integer>();
    NotificationManager notificationManagers;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarToolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(6);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        View viewTab0 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        ImageView tabIcon0 = viewTab0.findViewById(R.id.tab_icon);
        Picasso.get().load(R.drawable.tab_profile_off).into(tabIcon0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab0));

        View viewTab1 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        ImageView tabIcon1 = viewTab1.findViewById(R.id.tab_icon);
        Picasso.get().load(R.drawable.tab_users_off).into(tabIcon1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab1));

        View viewTab2 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        ImageView tabIcon2 = viewTab2.findViewById(R.id.tab_icon);
        Picasso.get().load(R.drawable.tab_swipe_off).into(tabIcon2);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab2));

        View viewTab3 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        ImageView tabIcon3 = viewTab3.findViewById(R.id.tab_icon);
        Picasso.get().load(R.drawable.tab_chat_off).into(tabIcon3);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab3));

        View viewTab4 = getLayoutInflater().inflate(R.layout.main_tab_icon, null);
        ImageView tabIcon4 = viewTab4.findViewById(R.id.tab_icon);
        Picasso.get().load(R.drawable.tab_extra_off).into(tabIcon4);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab4));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MainAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        arrayInteger.add(11111);
        map.put(0, "hello");


        UserSettings("show_feeds", "yes");
        UserSettings("show_profile", "yes");
        UserSettings("show_status", "yes");

        UserSettings("share_location", "yes");
        UserSettings("share_birthage", "yes");
        UserSettings("share_visits", "yes");

        UserSettings("user_verified", "no");
        UserSettings("user_premium", "no");

        UserSettings("user_mobile", "+000000000000");


        UserSettings("alert_match", "yes");
        UserSettings("alert_chats", "yes");
        UserSettings("alert_likes", "yes");
        UserSettings("alert_super", "yes");
        UserSettings("alert_visits", "yes");
        UserSettings("alert_follows", "yes");


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


                if (tab.getPosition() == 0) {
                    Picasso.get().load(R.drawable.tab_profile_on).placeholder(R.drawable.tab_profile_off).into(tabIcon0);
                }
                if (tab.getPosition() == 1) {
                    Picasso.get().load(R.drawable.tab_users_on).placeholder(R.drawable.tab_users_off).into(tabIcon1);
                }
                if (tab.getPosition() == 2) {
                    Picasso.get().load(R.drawable.tab_swipe_on).placeholder(R.drawable.tab_swipe_off).into(tabIcon2);
                }
                if (tab.getPosition() == 3) {
                    Picasso.get().load(R.drawable.tab_chat_on).placeholder(R.drawable.tab_chat_off).into(tabIcon3);
                }
                if (tab.getPosition() == 4) {
                    Picasso.get().load(R.drawable.tab_extra_on).placeholder(R.drawable.tab_extra_off).into(tabIcon4);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                if (tab.getPosition() == 0) {
                    Picasso.get().load(R.drawable.tab_profile_off).placeholder(R.drawable.tab_profile_on).into(tabIcon0);
                }
                if (tab.getPosition() == 1) {
                    Picasso.get().load(R.drawable.tab_users_off).placeholder(R.drawable.tab_users_on).into(tabIcon1);
                }
                if (tab.getPosition() == 2) {
                    Picasso.get().load(R.drawable.tab_swipe_off).placeholder(R.drawable.tab_swipe_on).into(tabIcon2);
                }
                if (tab.getPosition() == 3) {
                    Picasso.get().load(R.drawable.tab_chat_off).placeholder(R.drawable.tab_chat_on).into(tabIcon3);
                }
                if (tab.getPosition() == 4) {
                    Picasso.get().load(R.drawable.tab_extra_off).placeholder(R.drawable.tab_extra_on).into(tabIcon4);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        tabLayout.getTabAt(2).select();

        String tabShow;
        tabShow = getIntent().getStringExtra("tab_show");
        if (tabShow != null) {
            switch (tabShow) {
                case "tab_profile":
                    tabLayout.getTabAt(0).select();
                    break;
                case "tab_users":
                    tabLayout.getTabAt(1).select();
                    break;
                case "tab_swipe":
                    tabLayout.getTabAt(2).select();
                    break;
                case "tab_chats":
                    tabLayout.getTabAt(3).select();
                    break;
                case "tab_feeds":
                    tabLayout.getTabAt(4).select();
                    break;
            }
        }
    }


    private void UserSettings(String userString, String userCheck) {

        if (firebaseUser != null) {

            String currentUser = firebaseUser.getUid();
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String user_setting = task.getResult().getString(userString);
                            if (user_setting == null) {
                                Map<String, Object> mapUserFeeds = new HashMap<>();
                                mapUserFeeds.put(userString, userCheck);
                                firebaseFirestore.collection("users")
                                        .document(currentUser)
                                        .update(mapUserFeeds);
                            }
                        }
                    });
        }
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
    protected void onStart() {
        super.onStart();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        Application.appRunning = true;


        if (firebaseUser == null) {

            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();


        } else {


            String currentUser = firebaseUser.getUid();

            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                String user_dummy = task.getResult().getString("user_dummy");

                                if (user_dummy != null && user_dummy.equals("yes")) {

                                    Intent intent = new Intent(MainActivity.this, RemindActivity.class);
                                    intent.putExtra("user_uid", currentUser);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        }
                    });

        }


    }


    private void UserStatus(String status) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            String currentUser = firebaseUser.getUid();

            Map<String, Object> arrayUserStatus = new HashMap<>();
            arrayUserStatus.put("user_status", status);

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .update(arrayUserStatus);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserStatus("online");
        OnlineUser();

        if (Application.notificationManagerCompat != null) {
            Application.notificationManagerCompat.cancelAll();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        UserStatus("offline");
        Application.appRunning = false;


    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

