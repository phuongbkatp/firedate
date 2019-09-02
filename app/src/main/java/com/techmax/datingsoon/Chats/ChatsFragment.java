package com.techmax.datingsoon.Chats;

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
import com.techmax.datingsoon.Message.MessageActivity;
import com.techmax.datingsoon.Message.MessageClass;
import com.techmax.datingsoon.R;

public class ChatsFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView recyclerViewChatsView;
    private ChatsFirestore chatsFirestore;

    LinearLayout linearLayoutChatsContent;
    LinearLayout linearLayoutChatsEmpty;

    private AdView adViewChats;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chats_fragment, container, false);

        recyclerViewChatsView = view.findViewById(R.id.recyclerViewChatsView);
        ChatRecyclerView();


        adViewChats = view.findViewById(R.id.adViewChats);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewChats.loadAd(adRequest);

        linearLayoutChatsContent = view.findViewById(R.id.linearLayoutChatsContent);
        linearLayoutChatsContent.setVisibility(View.VISIBLE);
        linearLayoutChatsEmpty = view.findViewById(R.id.linearLayoutChatsEmpty);
        linearLayoutChatsEmpty.setVisibility(View.GONE);


        firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("chats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            if (queryDocumentSnapshots.size() == 0) {

                                linearLayoutChatsContent.setVisibility(View.GONE);
                                linearLayoutChatsEmpty.setVisibility(View.VISIBLE);

                            } else {

                                linearLayoutChatsContent.setVisibility(View.VISIBLE);
                                linearLayoutChatsEmpty.setVisibility(View.GONE);
                            }
                        }
                    }
                });


        return view;
    }

    private void ChatRecyclerView() {

        Query query = firebaseFirestore.collection("users")
                .document(firebaseUser.getUid())
                .collection("chats")
                .orderBy("user_datesent", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MessageClass> options = new FirestoreRecyclerOptions.Builder<MessageClass>()
                .setQuery(query, MessageClass.class)
                .build();


        chatsFirestore = new ChatsFirestore(options);

        recyclerViewChatsView.setHasFixedSize(true);
        recyclerViewChatsView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewChatsView.setAdapter(chatsFirestore);

        chatsFirestore.setOnItemClickListener(new ChatsFirestore.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final MessageClass messageClass = documentSnapshot.toObject(MessageClass.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                final Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("user_uid", messageClass.getUser_receiver());
                startActivity(intent);
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        chatsFirestore.startListening();
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
