package com.techmax.datingsoon.Likes;

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

public class LikesFirestore extends FirestoreRecyclerAdapter<LikesClass, LikesFirestore.LikesHolder> {


    private OnItemClickListener listener;

    public LikesFirestore(@NonNull FirestoreRecyclerOptions<LikesClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final LikesHolder holder, int position, @NonNull final LikesClass model) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                ProfileClass profileClass = doc.getDocument().toObject(ProfileClass.class);
                                if (profileClass.getUser_uid().equals(model.getUser_likes())) {
                                    holder.textViewLikesItemLikesName.setText(profileClass.getUser_name());

                                    if (profileClass.getUser_thumb().equals("thumb")) {
                                        holder.roundedImageViewLikesItemLikesImage.setImageResource(R.drawable.profile_image);
                                    } else {
                                        Picasso.get().load(profileClass.getUser_thumb()).into(holder.roundedImageViewLikesItemLikesImage);
                                    }
                                }
                            }
                        }
                    }
                });

        SimpleDateFormat sfd = new SimpleDateFormat("d MMMM yyyy, hh:mm a");
        String x = sfd.format(new Date(model.getUser_liked().toString()));

        holder.RelativeTimeLikesItemLikesDate.setReferenceTime(model.user_liked.getTime());

    }

    @NonNull
    @Override
    public LikesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.likes_item, viewGroup, false);
        return new LikesHolder(v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class LikesHolder extends RecyclerView.ViewHolder {

        TextView textViewLikesItemLikesName;
        TextView textViewLikesItemLikesMessage;
        TextView textViewLikesItemLikesUnread;
        RoundedImageView roundedImageViewLikesItemLikesImage;
        RelativeTimeTextView RelativeTimeLikesItemLikesDate;

        public LikesHolder(@NonNull View itemView) {
            super(itemView);

            textViewLikesItemLikesName = itemView.findViewById(R.id.textViewLikesItemLikesName);
            textViewLikesItemLikesUnread = itemView.findViewById(R.id.textViewLikesItemLikesUnread);
            RelativeTimeLikesItemLikesDate = (RelativeTimeTextView) itemView.findViewById(R.id.RelativeTimeLikesItemLikesDate); //Or just use Butterknife!
            roundedImageViewLikesItemLikesImage = itemView.findViewById(R.id.roundedImageViewLikesItemLikesImage);

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
