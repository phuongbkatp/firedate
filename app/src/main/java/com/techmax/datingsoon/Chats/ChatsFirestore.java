package com.techmax.datingsoon.Chats;

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
import com.techmax.datingsoon.Message.MessageClass;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Profile.ProfileClass;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsFirestore extends FirestoreRecyclerAdapter<MessageClass, ChatsFirestore.ChatHolder> {


    private OnItemClickListener listener;

    public ChatsFirestore(@NonNull FirestoreRecyclerOptions<MessageClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatHolder holder, int position, @NonNull final MessageClass model) {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                ProfileClass profileClass = doc.getDocument().toObject(ProfileClass.class);
                                if (profileClass.getUser_uid().equals(model.getUser_receiver())) {
                                    holder.textViewChatsItemChatsName.setText(profileClass.getUser_name());
                                    if (profileClass.getUser_image().equals("image")) {
                                        holder.roundedImageViewChatsItemChatsImage.setImageResource(R.drawable.profile_image);
                                    } else {
                                        Picasso.get().load(profileClass.getUser_image()).into(holder.roundedImageViewChatsItemChatsImage);
                                    }
                                }
                            }
                        }

                    }
                });


        holder.textViewChatsItemChatsMessage.setText(model.getUser_message());

        if (!model.getUser_unread().equals("0")) {
            holder.textViewChatsItemChatsUnread.setVisibility(View.VISIBLE);
        } else {
            holder.textViewChatsItemChatsUnread.setVisibility(View.GONE);
        }

        holder.textViewChatsItemChatsUnread.setText(model.getUser_unread());

        holder.RelativeTimeChatsItemChatsDate.setReferenceTime(model.getUser_datesent().getTime());

    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chats_item, viewGroup, false);
        return new ChatHolder(v);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    class ChatHolder extends RecyclerView.ViewHolder {

        TextView textViewChatsItemChatsName;
        TextView textViewChatsItemChatsMessage;
        TextView textViewChatsItemChatsUnread;
        RelativeTimeTextView RelativeTimeChatsItemChatsDate;
        RoundedImageView roundedImageViewChatsItemChatsImage;
        CircleImageView circleImageViewChatsItemChatsOnline;
        CircleImageView circleImageViewChatsItemChatsOffline;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);

            textViewChatsItemChatsName = itemView.findViewById(R.id.textViewChatsItemChatsName);
            textViewChatsItemChatsMessage = itemView.findViewById(R.id.textViewChatsItemChatsMessage);
            textViewChatsItemChatsUnread = itemView.findViewById(R.id.textViewChatsItemChatsUnread);
            RelativeTimeChatsItemChatsDate = (RelativeTimeTextView) itemView.findViewById(R.id.RelativeTimeChatsItemChatsDate); //Or just use Butterknife!
            roundedImageViewChatsItemChatsImage = itemView.findViewById(R.id.roundedImageViewChatsItemChatsImage);

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
