package com.techmax.datingsoon.Matches;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Profile.ProfileClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesFirestore extends FirestoreRecyclerAdapter<MatchesClass, MatchesFirestore.MatchesHolder> {


    private OnItemClickListener listener;

    public MatchesFirestore(@NonNull FirestoreRecyclerOptions<MatchesClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MatchesHolder holder, int position, @NonNull final MatchesClass model) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                ProfileClass profileClass = doc.getDocument().toObject(ProfileClass.class);
                                if (profileClass.getUser_uid().equals(model.getUser_matches())) {
                                    holder.textViewMatchesItemMatchesName.setText(profileClass.getUser_name());

                                    if (profileClass.getUser_thumb().equals("thumb")) {
                                        holder.roundedImageViewMatchesItemMatchesImage.setImageResource(R.drawable.profile_image);
                                    } else {
                                        Picasso.get().load(profileClass.getUser_thumb()).into(holder.roundedImageViewMatchesItemMatchesImage);
                                    }
                                }
                            }
                        }
                    }
                });

        SimpleDateFormat sfd = new SimpleDateFormat("d MMMM yyyy, hh:mm a");
        String x = sfd.format(new Date(model.getUser_matched().toString()));

        holder.RelativeTimeMatchesItemMatchesDate.setReferenceTime(model.getUser_matched().getTime());

    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.matches_item, viewGroup, false);
        return new MatchesHolder(v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class MatchesHolder extends RecyclerView.ViewHolder {

        TextView textViewMatchesItemMatchesName;
        RoundedImageView roundedImageViewMatchesItemMatchesImage;
        CircleImageView circleImageViewUsersItemUsersOnline;
        CircleImageView circleImageViewUsersItemUsersOffline;

        RelativeTimeTextView RelativeTimeMatchesItemMatchesDate;


        public MatchesHolder(@NonNull View itemView) {
            super(itemView);

            textViewMatchesItemMatchesName = itemView.findViewById(R.id.textViewMatchesItemMatchesName);
            roundedImageViewMatchesItemMatchesImage = itemView.findViewById(R.id.roundedImageViewMatchesItemMatchesImage);
            circleImageViewUsersItemUsersOnline = itemView.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            circleImageViewUsersItemUsersOffline = itemView.findViewById(R.id.circleImageViewUsersItemUsersOffline);
            RelativeTimeMatchesItemMatchesDate = itemView.findViewById(R.id.RelativeTimeMatchesItemMatchesDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

}
