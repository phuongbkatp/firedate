package com.techmax.datingsoon.Swipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Extra.EventClass;
import com.techmax.datingsoon.Loves.LovesClass;
import com.techmax.datingsoon.Matches.MatchesClass;
import com.techmax.datingsoon.Extra.NopesClass;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Profile.ProfileClass;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwipeFragment extends Fragment implements CardStackListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    String currentUser;

    CircleImageView imageViewProfileViewLovesUndo;
    CircleImageView imageViewProfileViewLovesNope;
    CircleImageView imageViewProfileViewLovesSuper;
    CircleImageView imageViewProfileViewLovesLike;
    CircleImageView imageViewProfileViewLovesBoost;


    CardStackLayoutManager manager;

    private ArrayList<ProfileClass> arrayUserClass;
    private List<String> arrayUserRemove;

    CardStackView cardStackView;

    int intSwipePositionFirst;
    int intSwipePositionLast;
    int intSwipePositionRewind;


    String stringCheckGender;
    String stringCheckAgesMin;
    String stringCheckAgesMax;
    String stringCheckLocation;
    String stringCheckMarital;
    String stringCheckSexual;
    String stringCheckSeeking;

    LinearLayout linearLayoutSwipeButtons;
    LinearLayout linearLayoutSwipeImageGroup;
    LinearLayout linearLayoutSwipeEmptyGroup;

    private AdView adViewSwipe;

    RippleBackground rippleSwipeAnimation;
    ImageView imageRippleSwipeUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.swipe_fragment, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseUser != null) {
            currentUser = firebaseUser.getUid();
        }


        arrayUserClass = new ArrayList<>();
        arrayUserRemove = new ArrayList<>();
        arrayUserRemove.add("demoUserWhenZero");

        imageViewProfileViewLovesUndo = view.findViewById(R.id.imageViewProfileViewLovesUndo);
        imageViewProfileViewLovesNope = view.findViewById(R.id.imageViewProfileViewLovesNope);
        imageViewProfileViewLovesSuper = view.findViewById(R.id.imageViewProfileViewLovesSuper);
        imageViewProfileViewLovesLike = view.findViewById(R.id.imageViewProfileViewLovesLike);
        imageViewProfileViewLovesBoost = view.findViewById(R.id.imageViewProfileViewLovesBoost);

        linearLayoutSwipeButtons = view.findViewById(R.id.linearLayoutSwipeButtons);
        linearLayoutSwipeButtons.setVisibility(View.GONE);

        linearLayoutSwipeImageGroup = view.findViewById(R.id.linearLayoutSwipeImageGroup);
        linearLayoutSwipeEmptyGroup = view.findViewById(R.id.linearLayoutSwipeEmptyGroup);

        manager = new CardStackLayoutManager(getContext(), this);
        cardStackView = view.findViewById(R.id.cardStackViewNavigationSwipe);
        cardStackView.setLayoutManager(manager);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(2);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.2f);
        manager.setMaxDegree(30.0f);
        manager.setDirections(Arrays.asList(Direction.Top, Direction.Left, Direction.Right));
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);


        UserRecyclerView();


        rippleSwipeAnimation = (RippleBackground) view.findViewById(R.id.rippleSwipeAnimation);
        imageRippleSwipeUser = (ImageView) view.findViewById(R.id.imageRippleSwipeUser);
        rippleSwipeAnimation.startRippleAnimation();


        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            String user_image = documentSnapshot.getString("user_image");
                            Picasso.get().load(user_image).placeholder(R.drawable.profile_image).into(imageRippleSwipeUser);
                        }
                    }
                });


        adViewSwipe = view.findViewById(R.id.adViewSwipe);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewSwipe.loadAd(adRequest);


        imageViewProfileViewLovesNope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(Duration.Normal.duration)
                        .build());
                cardStackView.swipe();


            }
        });


        imageViewProfileViewLovesLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .setDuration(Duration.Normal.duration)
                        .build());
                cardStackView.swipe();

            }
        });

        imageViewProfileViewLovesUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeUserRewind();
            }
        });

        imageViewProfileViewLovesSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeUserSuper();
            }
        });

        imageViewProfileViewLovesBoost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Under development!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    private void UserRecyclerView() {


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("loves")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    LovesClass lovesClass = doc.getDocument().toObject(LovesClass.class);
                                    arrayUserRemove.add(lovesClass.getUser_loves());

                                }
                            }


                        }
                    }
                });


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("nopes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    NopesClass nopesClass = doc.getDocument().toObject(NopesClass.class);
                                    arrayUserRemove.add(nopesClass.getUser_nopes());

                                }
                            }

                        }
                    }
                });


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    MatchesClass matchesClass = doc.getDocument().toObject(MatchesClass.class);
                                    arrayUserRemove.add(matchesClass.getUser_matches());

                                }
                            }


                        }
                    }
                });


        firebaseFirestore.collection("users")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        String stringShowMarital = documentSnapshot.getString("show_marital");
                        if (stringShowMarital != null) {
                            stringCheckMarital = stringShowMarital;
                        } else {
                            stringCheckMarital = "Any";
                        }

                        String stringShowSexual = documentSnapshot.getString("show_sexual");
                        if (stringShowSexual != null) {
                            stringCheckSexual = stringShowSexual;
                        } else {
                            stringCheckSexual = "Any";
                        }

                        String stringShowSeeking = documentSnapshot.getString("show_seeking");
                        if (stringShowSeeking != null) {
                            stringCheckSeeking = stringShowSeeking;
                        } else {
                            stringCheckSeeking = "Any";
                        }

                        String stringShowAges = documentSnapshot.getString("show_ages");
                        if (stringShowAges != null) {
                            String[] arrayCheckLookage = stringShowAges.trim().split("\\s*-\\s*");
                            stringCheckAgesMin = arrayCheckLookage[0];
                            stringCheckAgesMax = arrayCheckLookage[1];
                        } else {
                            stringCheckAgesMin = "16";
                            stringCheckAgesMax = "100000";
                        }

                        String stringShowLocation = documentSnapshot.getString("show_location");
                        if (stringShowLocation != null) {
                            stringCheckLocation = stringShowLocation;
                        } else {
                            String stringUserState = documentSnapshot.getString("user_state");

                            Map<String, Object> mapShowLocation = new HashMap<>();
                            mapShowLocation.put("show_location", stringUserState);
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .update(mapShowLocation);

                            stringCheckLocation = stringUserState;
                        }

                        String stringShowGender = documentSnapshot.getString("show_gender");
                        if (stringShowGender != null) {
                            if (stringShowGender.equals("Man")) {
                                stringCheckGender = "Male";
                                UsersDisplay(stringCheckGender, stringCheckAgesMin, stringCheckAgesMax,
                                        stringCheckLocation, stringCheckMarital, stringCheckSexual, stringCheckSeeking);

                            } else if (stringShowGender.equals("Woman")) {
                                stringCheckGender = "Female";
                                UsersDisplay(stringCheckGender, stringCheckAgesMin, stringCheckAgesMax,
                                        stringCheckLocation, stringCheckMarital, stringCheckSexual, stringCheckSeeking);
                            } else if (stringShowGender.equals("Any")) {
                                stringCheckGender = "Any";
                                UsersDisplay(stringCheckGender, stringCheckAgesMin, stringCheckAgesMax,
                                        stringCheckLocation, stringCheckMarital, stringCheckSexual, stringCheckSeeking);
                            }
                        } else {
                            String stringUserGender = documentSnapshot.getString("user_gender");

                            if (stringUserGender.equals("Male")) {
                                stringCheckGender = "Female";

                                Map<String, Object> mapShowGender = new HashMap<>();
                                mapShowGender.put("show_gender", "Woman");
                                firebaseFirestore.collection("users")
                                        .document(currentUser)
                                        .update(mapShowGender);

                                UsersDisplay(stringCheckGender, stringCheckAgesMin, stringCheckAgesMax,
                                        stringCheckLocation, stringCheckMarital, stringCheckSexual, stringCheckSeeking);

                            } else {
                                stringCheckGender = "Male";

                                Map<String, Object> mapShowGender = new HashMap<>();
                                mapShowGender.put("show_gender", "Man");
                                firebaseFirestore.collection("users")
                                        .document(currentUser)
                                        .update(mapShowGender);

                                UsersDisplay(stringCheckGender, stringCheckAgesMin, stringCheckAgesMax,
                                        stringCheckLocation, stringCheckMarital, stringCheckSexual, stringCheckSeeking);
                            }

                        }

                    }
                });
    }


    private void UsersDisplay(final String stringCheckGender,
                              final String stringCheckAgesMin,
                              final String stringCheckAgesMax,
                              final String stringCheckLocation,
                              final String stringCheckMarital,
                              final String stringCheckSexual,
                              final String stringCheckSeeking) {

        final String currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("users")
                .orderBy("user_online", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        for (QueryDocumentSnapshot querySnapshot : task.getResult()) {

                            ProfileClass profileClass = querySnapshot.toObject(ProfileClass.class);

                            if (!profileClass.getUser_uid().equals(currentUser)) {

                                if (stringCheckGender.equals("Any")) {

                                    int intBirthage = Integer.valueOf(profileClass.getUser_birthage());
                                    int intLookageMin = Integer.valueOf(stringCheckAgesMin);
                                    int intLookageMax = Integer.valueOf(stringCheckAgesMax);

                                    if (intBirthage >= intLookageMin && intBirthage <= intLookageMax) {

                                        if (stringCheckLocation.equals("Anywhere")) {

                                            if (stringCheckMarital.equals("Any")) {

                                                if (stringCheckSexual.equals("Any")) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }

                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }


                                                } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                }

                                            } else if (stringCheckMarital.equals(profileClass.getUser_marital())) {

                                                if (stringCheckSexual.equals("Any")) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                }
                                            }


                                        } else if (stringCheckLocation.equals(profileClass.getUser_city()) ||
                                                stringCheckLocation.equals(profileClass.getUser_state()) ||
                                                stringCheckLocation.equals(profileClass.getUser_country())) {

                                            if (stringCheckMarital.equals("Any")) {

                                                if (stringCheckSexual.equals("Any")) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (stringCheckMarital.equals(profileClass.getUser_marital())) {

                                                if (stringCheckSexual.equals("Any")) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                    if (stringCheckSeeking.equals("Any")) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                        if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                            if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                arrayUserClass.add(profileClass);

                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }


                                    }

                                } else {

                                    if (profileClass.getUser_gender().equals(stringCheckGender)) {


                                        int intBirthage = Integer.valueOf(profileClass.getUser_birthage());
                                        int intLookageMin = Integer.valueOf(stringCheckAgesMin);
                                        int intLookageMax = Integer.valueOf(stringCheckAgesMax);

                                        if (intBirthage >= intLookageMin && intBirthage <= intLookageMax) {

                                            if (stringCheckLocation.equals("Anywhere")) {

                                                if (stringCheckMarital.equals("Any")) {

                                                    if (stringCheckSexual.equals("Any")) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }


                                                    } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    }

                                                } else if (stringCheckMarital.equals(profileClass.getUser_marital())) {

                                                    if (stringCheckSexual.equals("Any")) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    }
                                                }


                                            } else if (stringCheckLocation.equals(profileClass.getUser_city()) ||
                                                    stringCheckLocation.equals(profileClass.getUser_state()) ||
                                                    stringCheckLocation.equals(profileClass.getUser_country())) {

                                                if (stringCheckMarital.equals("Any")) {

                                                    if (stringCheckSexual.equals("Any")) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (stringCheckMarital.equals(profileClass.getUser_marital())) {

                                                    if (stringCheckSexual.equals("Any")) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    } else if (stringCheckSexual.equals(profileClass.getUser_sexual())) {

                                                        if (stringCheckSeeking.equals("Any")) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        } else if (stringCheckSeeking.equals(profileClass.getUser_seeking())) {

                                                            if (!arrayUserRemove.contains(profileClass.getUser_uid())) {

                                                                if (profileClass.getShow_profile() == null || profileClass.getShow_profile().equals("yes")) {

                                                                    arrayUserClass.add(profileClass);

                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }


                                        }
                                    }

                                }
                            }

                            SwipeAdapter adapter = new SwipeAdapter(arrayUserClass, getActivity());
                            cardStackView.setAdapter(adapter);

                            if (arrayUserClass.size() == 0) {
                                linearLayoutSwipeButtons.setVisibility(View.GONE);
                                linearLayoutSwipeImageGroup.setVisibility(View.GONE);
                                linearLayoutSwipeEmptyGroup.setVisibility(View.VISIBLE);
                                rippleSwipeAnimation.stopRippleAnimation();
                                rippleSwipeAnimation.setVisibility(View.GONE);
                            } else {
                                linearLayoutSwipeButtons.setVisibility(View.VISIBLE);
                                linearLayoutSwipeImageGroup.setVisibility(View.VISIBLE);
                                linearLayoutSwipeEmptyGroup.setVisibility(View.GONE);
                                rippleSwipeAnimation.stopRippleAnimation();
                                rippleSwipeAnimation.setVisibility(View.GONE);
                            }
                        }


                    }
                });
    }


    private void SwipeUserMatch(String swipedUser) {

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("likes")
                .document(swipedUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            Map<String, Object> mapMatchesSwipe = new HashMap<>();
                            mapMatchesSwipe.put("user_matched", Timestamp.now());
                            mapMatchesSwipe.put("user_matches", swipedUser);

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("matches")
                                    .document(swipedUser)
                                    .set(mapMatchesSwipe)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Map<String, Object> mapMatchesCurrent = new HashMap<>();
                                                mapMatchesCurrent.put("user_matched", Timestamp.now());
                                                mapMatchesCurrent.put("user_matches", currentUser);

                                                firebaseFirestore.collection("users")
                                                        .document(swipedUser)
                                                        .collection("matches")
                                                        .document(currentUser)
                                                        .set(mapMatchesCurrent)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getContext(), "Hoorayy!! Matched!", Toast.LENGTH_SHORT).show();
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


    public void SwipeUserLoves() {


        final String swipedUser = arrayUserClass.get(intSwipePositionFirst).getUser_uid();

        Map<String, Object> mapLovesUser = new HashMap<>();
        mapLovesUser.put("user_loves", swipedUser);
        mapLovesUser.put("user_loved", Timestamp.now());
        mapLovesUser.put("user_super", "no");

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("loves")
                .document(swipedUser)
                .set(mapLovesUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<String, Object> mapLikesUser = new HashMap<>();
                        mapLikesUser.put("user_likes", currentUser);
                        mapLikesUser.put("user_liked", Timestamp.now());
                        mapLovesUser.put("user_super", "no");

                        firebaseFirestore.collection("users")
                                .document(swipedUser)
                                .collection("likes")
                                .document(currentUser)
                                .set(mapLikesUser)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SwipeUserMatch(swipedUser);
                                        }
                                    }
                                });


                    }
                });

        //  EventSend();

    }


    public void SwipeUserSuper() {


        final String swipedUser = arrayUserClass.get(intSwipePositionFirst).getUser_uid();

        Map<String, Object> mapLovesUser = new HashMap<>();
        mapLovesUser.put("user_loves", swipedUser);
        mapLovesUser.put("user_loved", Timestamp.now());
        mapLovesUser.put("user_super", "yes");

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("loves")
                .document(swipedUser)
                .set(mapLovesUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Map<String, Object> mapLikesUser = new HashMap<>();
                        mapLikesUser.put("user_likes", currentUser);
                        mapLikesUser.put("user_liked", Timestamp.now());
                        mapLovesUser.put("user_super", "yes");

                        firebaseFirestore.collection("users")
                                .document(swipedUser)
                                .collection("likes")
                                .document(currentUser)
                                .set(mapLikesUser)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            SwipeUserMatch(swipedUser);
                                        }
                                    }
                                });

                    }
                });


        manager.setSwipeAnimationSetting(new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Top)
                .setDuration(Duration.Normal.duration)
                .build());
        cardStackView.swipe();

        //  EventSend();

    }


    public void SwipeUserNopes() {


        final String swipedUser = arrayUserClass.get(intSwipePositionFirst).getUser_uid();

        Map<String, Object> mapNopesUser = new HashMap<>();
        mapNopesUser.put("user_nopes", swipedUser);
        mapNopesUser.put("user_noped", Timestamp.now());

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("nopes")
                .document(swipedUser)
                .set(mapNopesUser);

        //  EventSend();

    }


    public void SwipeUserRewind() {

        intSwipePositionRewind = intSwipePositionFirst - 1;

        if (intSwipePositionRewind >= 0) {


            final String swipedUser = arrayUserClass.get(intSwipePositionRewind).getUser_uid();

            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .collection("loves")
                    .document(swipedUser)
                    .delete();
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .collection("nopes")
                    .document(swipedUser)
                    .delete();
            firebaseFirestore.collection("users")
                    .document(currentUser)
                    .collection("matches")
                    .document(swipedUser)
                    .delete();
            firebaseFirestore.collection("users")
                    .document(swipedUser)
                    .collection("matches")
                    .document(currentUser)
                    .delete();
            firebaseFirestore.collection("users")
                    .document(swipedUser)
                    .collection("likes")
                    .document(currentUser)
                    .delete();

            manager.setRewindAnimationSetting(new RewindAnimationSetting.Builder()
                    .setDuration(Duration.Normal.duration)
                    .build());
            cardStackView.rewind();

        } else {
            Toast.makeText(getContext(),
                    "Swipe more cards to rewind!", Toast.LENGTH_SHORT).show();
        }

        // EventSend();


    }


    private void EventSend() {
        EventBus.getDefault().post(new EventClass("Hello everyone!"));
    }


    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {

        if (direction == Direction.Right) {
            SwipeUserLoves();
        } else if (direction == Direction.Left) {
            SwipeUserNopes();
        } else if (direction == Direction.Top) {
            SwipeUserSuper();
        }

    }

    @Override
    public void onCardRewound() {


    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

        intSwipePositionFirst = position;

    }

    @Override
    public void onCardDisappeared(View view, int position) {

        intSwipePositionLast = position;

    }
}
