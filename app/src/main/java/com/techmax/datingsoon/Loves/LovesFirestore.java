package com.techmax.datingsoon.Loves;

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

public class LovesFirestore extends FirestoreRecyclerAdapter<LovesClass, LovesFirestore.LovesHolder> {


    private OnItemClickListener listener;

    public LovesFirestore(@NonNull FirestoreRecyclerOptions<LovesClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final LovesHolder holder, int position, @NonNull final LovesClass model) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                ProfileClass profileClass = doc.getDocument().toObject(ProfileClass.class);
                                if (profileClass.getUser_uid().equals(model.getUser_loves())) {
                                    holder.textViewLovesItemLovesName.setText(profileClass.getUser_name());
                                    if (profileClass.getUser_thumb().equals("thumb")) {
                                        holder.roundedImageViewLovesItemLovesImage.setImageResource(R.drawable.profile_image);
                                    } else {
                                        Picasso.get().load(profileClass.getUser_thumb()).into(holder.roundedImageViewLovesItemLovesImage);
                                    }
                                }
                            }
                        }
                    }
                });

        SimpleDateFormat sfd = new SimpleDateFormat("d MMMM yyyy, hh:mm a");
        String x = sfd.format(new Date(model.getUser_loved().toString()));

        holder.RelativeTimeLovesItemLovesDate.setReferenceTime(model.user_loved.getTime());
    }

    @NonNull
    @Override
    public LovesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loves_item, viewGroup, false);
        return new LovesHolder(v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class LovesHolder extends RecyclerView.ViewHolder {

        TextView textViewLovesItemLovesName;
        TextView textViewLovesItemLovesMessage;
        TextView textViewLovesItemLovesUnread;
        RoundedImageView roundedImageViewLovesItemLovesImage;
        RelativeTimeTextView RelativeTimeLovesItemLovesDate;

        public LovesHolder(@NonNull View itemView) {
            super(itemView);

            textViewLovesItemLovesName = itemView.findViewById(R.id.textViewLovesItemLovesName);
            textViewLovesItemLovesMessage = itemView.findViewById(R.id.textViewLovesItemLovesMessage);
            textViewLovesItemLovesUnread = itemView.findViewById(R.id.textViewLovesItemLovesUnread);
            RelativeTimeLovesItemLovesDate = (RelativeTimeTextView) itemView.findViewById(R.id.RelativeTimeLovesItemLovesDate); //Or just use Butterknife!
            roundedImageViewLovesItemLovesImage = itemView.findViewById(R.id.roundedImageViewLovesItemLovesImage);

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
