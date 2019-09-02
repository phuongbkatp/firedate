package com.techmax.datingsoon.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.techmax.datingsoon.Profile.ProfileActivity;
import com.techmax.datingsoon.R;

public class MatchesFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewMatchesView;
    private MatchesFirestore matchesFirestore;


    LinearLayout linearLayoutMatchContent;
    LinearLayout linearLayoutMatchEmpty;

    private AdView adViewMatches;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matches_fragment, container, false);

        recyclerViewMatchesView = view.findViewById(R.id.recyclerViewMatchesView);
        MatchesRecyclerView();


        linearLayoutMatchContent = view.findViewById(R.id.linearLayoutMatchContent);
        linearLayoutMatchContent.setVisibility(View.VISIBLE);
        linearLayoutMatchEmpty = view.findViewById(R.id.linearLayoutMatchEmpty);
        linearLayoutMatchEmpty.setVisibility(View.GONE);


        adViewMatches = view.findViewById(R.id.adViewMatches);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewMatches.loadAd(adRequest);


        firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("matches")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            if (queryDocumentSnapshots.size() == 0) {

                                linearLayoutMatchContent.setVisibility(View.GONE);
                                linearLayoutMatchEmpty.setVisibility(View.VISIBLE);

                            } else {

                                linearLayoutMatchContent.setVisibility(View.VISIBLE);
                                linearLayoutMatchEmpty.setVisibility(View.GONE);
                            }
                        }
                    }
                });

        return view;
    }

    private void MatchesRecyclerView() {

        Query query = firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("matches")
                .orderBy("user_matched", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MatchesClass> options = new FirestoreRecyclerOptions.Builder<MatchesClass>()
                .setQuery(query, MatchesClass.class)
                .build();

        matchesFirestore = new MatchesFirestore(options);
        recyclerViewMatchesView.setHasFixedSize(true);
        recyclerViewMatchesView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMatchesView.setAdapter(matchesFirestore);


        matchesFirestore.setOnItemClickListener(new MatchesFirestore.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final MatchesClass matchesClass = documentSnapshot.toObject(MatchesClass.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();

                final Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", matchesClass.getUser_matches());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        matchesFirestore.startListening();
    }
}
