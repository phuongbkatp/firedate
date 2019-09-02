package com.techmax.datingsoon.Users;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Profile.ProfileActivity;
import com.techmax.datingsoon.Profile.ProfileClass;
import com.techmax.datingsoon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    String currentUser;

    ArrayList<ProfileClass> arrayProfileClasses;
    Context context;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    public UsersAdapter(ArrayList<ProfileClass> arrayProfileClasses, Context context) {
        this.arrayProfileClasses = arrayProfileClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.users_item, viewGroup, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentUser = firebaseUser.getUid();


        return new ViewHolder(view, context, arrayProfileClasses);

    }

    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter.ViewHolder viewHolder, int i) {

        final ProfileClass userClass = arrayProfileClasses.get(i);

        String userName = userClass.getUser_name();
        String[] splitUserNames = userName.split(" ");
        viewHolder.textViewUsersItemUserName.setText(splitUserNames[0]);

        viewHolder.textViewUsersItemUsersCity.setText(userClass.getUser_city());

/*

// If you want to show distance below user name enable this

        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot!=null) {

                            latitudeCurrent = Double.valueOf(documentSnapshot.getString("user_latitude"));
                            longitudeCurrent = Double.valueOf(documentSnapshot.getString("user_longitude"));

                            latitudeChatUser = Double.valueOf(userClass.getUser_latitude());
                            longitudeChatUser = Double.valueOf(userClass.getUser_longitude());

                            float floatDistance[] = new float[10];
                            Location.distanceBetween(latitudeCurrent, longitudeCurrent, latitudeChatUser, longitudeChatUser, floatDistance);

                            int intDistance = Math.round(floatDistance[0]);

                            if (intDistance >= 1000) {
                                stringDistance = String.valueOf(intDistance / 1000) + " km away";
                            } else {
                                stringDistance = "Less than 1 km";
                            }

                            viewHolder.textViewUsersItemUsersDistance.setText(stringDistance);
                        }
                    }
                });

*/


        viewHolder.textViewUsersItemUsersDistance.setVisibility(View.GONE);


        if (userClass.getShare_location() != null && userClass.getShare_location().equals("no")) {
            viewHolder.textViewUsersItemUsersCity.setVisibility(View.GONE);
            viewHolder.textViewUsersItemUsersDistance.setVisibility(View.GONE);
        } else {
            viewHolder.textViewUsersItemUsersCity.setText(userClass.getUser_city());
        }


        if (userClass.getShare_birthage() != null && userClass.getShare_birthage().equals("no")) {
            viewHolder.textViewUsersItemUsersAge.setVisibility(View.GONE);
        } else {
            viewHolder.textViewUsersItemUsersAge.setText(userClass.getUser_birthage());
        }

        viewHolder.textViewUsersItemUsersAbout.setText(userClass.getUser_about());

        if (userClass.getUser_gender().equals("Male")) {
            viewHolder.textViewUsersItemUsersGender.setText("M");
        } else {
            viewHolder.textViewUsersItemUsersGender.setText("F");
        }

        if (userClass.getUser_image().equals("image")) {
            viewHolder.imageViewUsersItemUsersImage.setImageResource(R.drawable.profile_image);
        } else {
            Picasso.get().load(userClass.getUser_image()).into(viewHolder.imageViewUsersItemUsersImage);
        }

        if (userClass.getUser_status().equals("online")) {
            viewHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.VISIBLE);
            viewHolder.circleImageViewUsersItemUsersOffline.setVisibility(View.GONE);
        } else {
            viewHolder.circleImageViewUsersItemUsersOffline.setVisibility(View.VISIBLE);
            viewHolder.circleImageViewUsersItemUsersOnline.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayProfileClasses.size();
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

        TextView textViewUsersItemUserName;
        TextView textViewUsersItemUsersAge;
        TextView textViewUsersItemUsersCity;
        TextView textViewUsersItemUsersDistance;
        TextView textViewUsersItemUsersGender;
        TextView textViewUsersItemUsersAbout;
        ImageView imageViewUsersItemUsersImage;
        RoundedImageView roundedImageViewUsersItemUserImage;
        CircleImageView circleImageViewUsersItemUsersOnline;
        CircleImageView circleImageViewUsersItemUsersOffline;


        ArrayList<ProfileClass> intentUserClass = new ArrayList<ProfileClass>();
        Context context;

        public ViewHolder(@NonNull View itemView, Context context, ArrayList<ProfileClass> intentUserClass) {
            super(itemView);
            this.intentUserClass = intentUserClass;
            this.context = context;
            itemView.setOnClickListener(this);

            textViewUsersItemUserName = itemView.findViewById(R.id.textViewUsersItemUsersName);
            textViewUsersItemUsersCity = itemView.findViewById(R.id.textViewUsersItemUsersCity);
            textViewUsersItemUsersDistance = itemView.findViewById(R.id.textViewUsersItemUsersDistance);
            textViewUsersItemUsersGender = itemView.findViewById(R.id.textViewUsersItemUsersGender);
            textViewUsersItemUsersAge = itemView.findViewById(R.id.textViewUsersItemUsersAge);
            textViewUsersItemUsersAbout = itemView.findViewById(R.id.textViewUsersItemUsersAbout);
            imageViewUsersItemUsersImage = itemView.findViewById(R.id.imageViewUsersItemUsersImage);
            circleImageViewUsersItemUsersOnline = itemView.findViewById(R.id.circleImageViewUsersItemUsersOnline);
            circleImageViewUsersItemUsersOffline = itemView.findViewById(R.id.circleImageViewUsersItemUsersOffline);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            ProfileClass intentProfileClass = this.intentUserClass.get(position);

            Intent intent = new Intent(this.context, ProfileActivity.class);
            intent.putExtra("user_uid", intentProfileClass.getUser_uid());

            this.context.startActivity(intent);
        }
    }

}

