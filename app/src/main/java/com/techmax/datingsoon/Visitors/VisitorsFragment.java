package com.techmax.datingsoon.Visitors;

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

public class VisitorsFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewVisitsView;
    private VisitorsFirestore visitorsFirestore;

    LinearLayout linearLayoutVisitorsContent;
    LinearLayout linearLayoutVisitorsEmpty;


    private AdView adViewVisitors;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.visitors_fragment, container, false);

        recyclerViewVisitsView = view.findViewById(R.id.recyclerViewVisitsView);

        VisitsRecyclerView();


        linearLayoutVisitorsContent = view.findViewById(R.id.linearLayoutVisitorsContent);
        linearLayoutVisitorsContent.setVisibility(View.VISIBLE);
        linearLayoutVisitorsEmpty = view.findViewById(R.id.linearLayoutVisitorsEmpty);
        linearLayoutVisitorsEmpty.setVisibility(View.GONE);


        adViewVisitors = view.findViewById(R.id.adViewVisitors);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewVisitors.loadAd(adRequest);

        firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("visits")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            if (queryDocumentSnapshots.size() == 0) {

                                linearLayoutVisitorsContent.setVisibility(View.GONE);
                                linearLayoutVisitorsEmpty.setVisibility(View.VISIBLE);

                            } else {

                                linearLayoutVisitorsContent.setVisibility(View.VISIBLE);
                                linearLayoutVisitorsEmpty.setVisibility(View.GONE);
                            }
                        }
                    }
                });


        return view;
    }

    private void VisitsRecyclerView() {

        Query query = firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("visits")
                .orderBy("user_visited", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<VisitorsClass> options = new FirestoreRecyclerOptions.Builder<VisitorsClass>()
                .setQuery(query, VisitorsClass.class)
                .build();

        visitorsFirestore = new VisitorsFirestore(options);

        recyclerViewVisitsView.setHasFixedSize(true);
        recyclerViewVisitsView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewVisitsView.setAdapter(visitorsFirestore);

        visitorsFirestore.setOnItemClickListener(new VisitorsFirestore.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final VisitorsClass visitorsClass = documentSnapshot.toObject(VisitorsClass.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();

                final Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user_uid", visitorsClass.getUser_visitor());
                startActivity(intent);

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        visitorsFirestore.startListening();

    }

}
