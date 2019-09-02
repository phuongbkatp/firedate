package com.techmax.datingsoon.Feeds;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Matches.MatchesClass;

import java.util.ArrayList;
import java.util.List;

public class FeedsFragment extends Fragment {


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUser;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    SharedPreferences prefs;
    ProgressBar progressBarFeedsView;
    String stringShowLooking;
    SwipeRefreshLayout swipeRefreshLayout;
    String stringLookageMin;
    String stringLookageMax;
    String stringShowLookingIn;
    private RecyclerView recyclerViewUserView;
    private FeedsAdapter feedsAdapter;
    private ArrayList<FeedsClass> arrayUserClass;
    private ArrayList<FeedsClass> arrayFeedsClasses;

    RelativeLayout relativeLayoutFeedsContent;
    LinearLayout linearLayoutFeedsEmpty;

    private List<String> arrayUserFollows;
    private List<String> arrayUserFeedsCheck;


    private AdView adViewFeeds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feeds_fragment, container, false);


        if(firebaseUser!=null) {
            currentUser = firebaseUser.getUid();
        }
        prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        arrayUserClass = new ArrayList<>();
        arrayFeedsClasses = new ArrayList<>();
        progressBarFeedsView = view.findViewById(R.id.progressBarFeedsView);

        recyclerViewUserView = view.findViewById(R.id.recyclerViewFeedsView);

        relativeLayoutFeedsContent = view.findViewById(R.id.linearLayoutFeedsContent);
        relativeLayoutFeedsContent.setVisibility(View.VISIBLE);
        linearLayoutFeedsEmpty = view.findViewById(R.id.linearLayoutFeedsEmpty);
        linearLayoutFeedsEmpty.setVisibility(View.GONE);


        recyclerViewUserView.setHasFixedSize(true);
        recyclerViewUserView.setLayoutManager(new LinearLayoutManager(getContext()));


        arrayUserFeedsCheck = new ArrayList<>();
        arrayUserFollows = new ArrayList<>();
        arrayUserFollows.add("demoUserWhenZero");

        UserRecyclerViewNew();


        adViewFeeds = view.findViewById(R.id.adViewFeeds);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewFeeds.loadAd(adRequest);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.feedswipeRefreshLayout);
        int myColorBackground = Color.parseColor("#880e4f");
        int myColorForeground = Color.parseColor("#ffffff");
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(myColorForeground);
        swipeRefreshLayout.setColorSchemeColors(myColorBackground);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                swipeRefreshLayout.setRefreshing(true);
                                                arrayFeedsClasses.clear();
                                                arrayUserFollows.clear();
                                                arrayUserFeedsCheck.clear();
                                                UserRecyclerViewNew();
                                            }
                                        }
                );
            }
        });

        return view;
    }


    private void UserRecyclerViewNew() {

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentChange doc : task.getResult().getDocumentChanges()) {
                                MatchesClass matchesClass = doc.getDocument().toObject(MatchesClass.class);
                                arrayUserFollows.add(matchesClass.getUser_matches());
                            }
                        }
                    }
                });


        firebaseFirestore.collection("feeds")
                .orderBy("feed_date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentChange doc : task.getResult().getDocumentChanges()) {

                            FeedsClass feedsClass = doc.getDocument().toObject(FeedsClass.class);

                            if (arrayUserFollows.contains(feedsClass.getFeed_user())) {

                                if (feedsClass.getFeed_show() != null &&
                                        feedsClass.getFeed_show().equals("yes")) {

                                    arrayFeedsClasses.add(feedsClass);

                                }

                            }


                            feedsAdapter = new FeedsAdapter(arrayFeedsClasses, getActivity());
                            recyclerViewUserView.setAdapter(feedsAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                            progressBarFeedsView.setVisibility(View.GONE);

                        }


                        if (arrayFeedsClasses.size() == 0) {
                            progressBarFeedsView.setVisibility(View.GONE);
                            relativeLayoutFeedsContent.setVisibility(View.GONE);
                            linearLayoutFeedsEmpty.setVisibility(View.VISIBLE);
                        } else {
                            progressBarFeedsView.setVisibility(View.VISIBLE);
                            relativeLayoutFeedsContent.setVisibility(View.VISIBLE);
                            linearLayoutFeedsEmpty.setVisibility(View.GONE);
                        }

                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
