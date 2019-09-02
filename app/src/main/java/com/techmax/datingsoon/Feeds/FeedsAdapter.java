package com.techmax.datingsoon.Feeds;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Profile.ProfileActivity;
import com.techmax.datingsoon.R;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    ArrayList<FeedsClass> arrayFeedsClasses;
    Context context;

    String user_keyz;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    public FeedsAdapter(ArrayList<FeedsClass> arrayFeedsClasses, Context context) {
        this.arrayFeedsClasses = arrayFeedsClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.feeds_item, viewGroup, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        return new ViewHolder(view, context, arrayFeedsClasses);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        FeedsClass feedsClass = arrayFeedsClasses.get(i);

        String currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("users")
                .document(feedsClass.getFeed_user())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (documentSnapshot != null) {
                            Picasso.get().load(documentSnapshot.getString("user_thumb")).into(viewHolder.imageViewFeedsItemFeedsThumb);

                            viewHolder.textViewFeedsItemFeedsUser.setText(documentSnapshot.getString("user_name"));
                        }
                    }
                });


        Picasso.get().load(feedsClass.getFeed_cover()).into(viewHolder.imageViewFeedsItemFeedsCover);


        viewHolder.textViewFeedsItemFeedsLikes.setText(String.valueOf(feedsClass.getFeed_like()));

        viewHolder.relativeTimeFeedsItemFeedsDate.setReferenceTime(feedsClass.feed_date.getTime());

        firebaseFirestore.collection("feeds")
                .document(feedsClass.getFeed_uid())
                .collection("likes")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            Picasso.get().load(R.drawable.tab_feed_like_on).into(viewHolder.imageViewFeedsItemFeedsLikes);
                        } else {
                            Picasso.get().load(R.drawable.tab_feed_like_off).into(viewHolder.imageViewFeedsItemFeedsLikes);
                        }
                    }
                });

        viewHolder.imageViewFeedsItemFeedsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedLike(viewHolder, viewHolder.imageViewFeedsItemFeedsLikes, viewHolder.textViewFeedsItemFeedsLikes, feedsClass);
            }
        });

    }


    private void FeedLike(ViewHolder viewHolder, ImageView imageView, TextView textView, FeedsClass feedsClass) {

        String currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("feeds")
                .document(feedsClass.getFeed_uid())
                .collection("likes")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            firebaseFirestore.collection("feeds")
                                    .document(feedsClass.getFeed_uid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                String stringLikes = task.getResult().getString("feed_like");
                                                long longLikes = Long.valueOf(stringLikes) - 1;
                                                String addLikes = String.valueOf(longLikes);

                                                HashMap<String, Object> hashMapUpdate = new HashMap<>();
                                                hashMapUpdate.put("feed_like", addLikes);

                                                firebaseFirestore.collection("feeds")
                                                        .document(feedsClass.getFeed_uid())
                                                        .update(hashMapUpdate)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("feeds")
                                                                            .document(feedsClass.getFeed_uid())
                                                                            .collection("likes")
                                                                            .document(currentUser)
                                                                            .delete()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        viewHolder.textViewFeedsItemFeedsLikes.setText(addLikes);
                                                                                        Picasso.get().load(R.drawable.tab_feed_like_off).into(viewHolder.imageViewFeedsItemFeedsLikes);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });


                                            }
                                        }
                                    });

                        } else {
                            firebaseFirestore.collection("feeds")
                                    .document(feedsClass.getFeed_uid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                String stringLikes = task.getResult().getString("feed_like");
                                                long longLikes = Long.valueOf(stringLikes) + 1;
                                                String addLikes = String.valueOf(longLikes);

                                                HashMap<String, Object> hashMapUpdate = new HashMap<>();
                                                hashMapUpdate.put("feed_like", addLikes);

                                                firebaseFirestore.collection("feeds")
                                                        .document(feedsClass.getFeed_uid())
                                                        .update(hashMapUpdate)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    HashMap<String, Object> hashMapUser = new HashMap<>();
                                                                    hashMapUser.put("feed_like_user", currentUser);
                                                                    hashMapUser.put("feed_like_date", Timestamp.now());

                                                                    firebaseFirestore.collection("feeds")
                                                                            .document(feedsClass.getFeed_uid())
                                                                            .collection("likes")
                                                                            .document(currentUser)
                                                                            .set(hashMapUser)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        viewHolder.textViewFeedsItemFeedsLikes.setText(addLikes);
                                                                                        Picasso.get().load(R.drawable.tab_feed_like_on).into(viewHolder.imageViewFeedsItemFeedsLikes);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });


                                            }
                                        }
                                    });

                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return arrayFeedsClasses.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewFeedsItemFeedsUser;
        TextView textViewFeedsItemFeedsLikes;

        CircleImageView imageViewFeedsItemFeedsThumb;
        ImageView imageViewFeedsItemFeedsCover;

        ImageView imageViewFeedsItemFeedsLikes;

        RelativeTimeTextView relativeTimeFeedsItemFeedsDate;


        ArrayList<FeedsClass> intentFeedsClasses = new ArrayList<FeedsClass>();
        Context context;

        public ViewHolder(@NonNull View itemView, Context context, ArrayList<FeedsClass> intentFeedsClasses) {
            super(itemView);
            this.intentFeedsClasses = intentFeedsClasses;
            this.context = context;
            itemView.setOnClickListener(this);

            textViewFeedsItemFeedsUser = itemView.findViewById(R.id.textViewFeedsItemFeedsUser);
            textViewFeedsItemFeedsLikes = itemView.findViewById(R.id.textViewFeedsItemFeedsLikes);

            imageViewFeedsItemFeedsThumb = itemView.findViewById(R.id.imageViewFeedsItemFeedsThumb);
            imageViewFeedsItemFeedsCover = itemView.findViewById(R.id.imageViewFeedsItemFeedsCover);

            imageViewFeedsItemFeedsLikes = itemView.findViewById(R.id.imageViewFeedsItemFeedsLikes);

            relativeTimeFeedsItemFeedsDate = itemView.findViewById(R.id.relativeTimeFeedsItemFeedsDate); //Or just use Butterknife!


        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            FeedsClass intentFeedsClass = this.intentFeedsClasses.get(position);

            Intent intent = new Intent(this.context, ProfileActivity.class);
            intent.putExtra("user_uid", intentFeedsClass.getFeed_user());

            this.context.startActivity(intent);
        }


    }

}

