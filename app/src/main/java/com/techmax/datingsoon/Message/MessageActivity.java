package com.techmax.datingsoon.Message;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.techmax.datingsoon.Main.Application;
import com.techmax.datingsoon.Extra.BlockClass;
import com.techmax.datingsoon.Chats.ChatsClass;
import com.techmax.datingsoon.Main.MainActivity;
import com.techmax.datingsoon.Profile.ProfileActivity;
import com.techmax.datingsoon.R;
import com.techmax.datingsoon.Settings.ReportActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MessageActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    TextView toolbarTextViewUserName;
    TextView toolbarTextViewUserStatus;
    Toolbar toolbarToolbar;
    RoundedImageView toolbarImageViewUserImage;
    MessageAdapter messageAdapter;
    ArrayList<ChatsClass> listChatsClass;
    ArrayList<BlockClass> arraylistBlockUsers;
    RecyclerView recyclerViewMessageView;
    Bitmap image;
    String profileUser;
    String currentUser;
    EditText editTextMessageText;
    ImageButton buttonMessageSend;
    ImageButton buttonMessageImage;
    ListenerRegistration chatsListenerRegistration;
    ListenerRegistration seenListenerRegistration;
    ListenerRegistration blockCurrentListenerRegistration;
    ListenerRegistration blockChatUserListenerRegistration;
    ListenerRegistration deleteChatListenerRegistration;
    ListenerRegistration privacyListenerRegistration;

    Bitmap bitmapThumb;
    String stringThumb;

    ProgressDialog dialog;

    public static final int MESSAGE_IMAGE = 1;

    RelativeLayout relativeLayoutMessageChat;


    String genderCurrent;
    String imageCurrent;
    String verifiedCurrent;
    String premiumCurrent;
    String countryCurrent;

    String editTextChatHide = "no";


    String stringUnread;
    String stringUnreadz;
    int intUnread;
    private Menu menuMessage;

    private AdView adViewMessage;

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);

        toolbarTextViewUserName = findViewById(R.id.toolbarTextViewUserName);
        toolbarTextViewUserStatus = findViewById(R.id.toolbarTextViewUserStatus);
        toolbarImageViewUserImage = findViewById(R.id.toolbarImageViewUserImage);
        editTextMessageText = findViewById(R.id.editTextMessageText);
        buttonMessageSend = findViewById(R.id.buttonMessageSend);
        // buttonMessageImage = findViewById(R.id.buttonMessageImage);
        listChatsClass = new ArrayList<>();

        relativeLayoutMessageChat = findViewById(R.id.relativeLayoutMessageChat);

        toolbarToolbar = findViewById(R.id.toolbarToolbar);
        setSupportActionBar(toolbarToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adViewMessage = findViewById(R.id.adViewMessage);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewMessage.loadAd(adRequest);


        recyclerViewMessageView = findViewById(R.id.recyclerViewMessageView);
        recyclerViewMessageView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewMessageView.setLayoutManager(linearLayoutManager);

        currentUser = firebaseAuth.getCurrentUser().getUid();
        profileUser = getIntent().getStringExtra("user_uid");

        buttonMessageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chatMessage = editTextMessageText.getText().toString();
                if (!chatMessage.equals("")) {
                    sendMessage(
                            currentUser,
                            profileUser,
                            chatMessage
                    );
                } else {
                    Toast.makeText(MessageActivity.this,
                            "Please type message to send",
                            Toast.LENGTH_SHORT).show();
                }
                editTextMessageText.setText("");
            }
        });


        readMessage(currentUser, profileUser);

        toolbarTextViewUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatProfile();
            }
        });
        toolbarTextViewUserStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatProfile();
            }
        });

        toolbarImageViewUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatProfile();
            }
        });
    }

    private void chatProfile() {

        Intent intent = new Intent(MessageActivity.this, ProfileActivity.class);
        intent.putExtra("user_uid", profileUser);
        startActivity(intent);
    }

    private void sendMessage(
            final String chatSender,
            final String chatReceiver,
            final String chatMessage) {

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("chat_datesent", Timestamp.now());
        hashMap.put("chat_dateseen", Timestamp.now());
        hashMap.put("chat_sender", chatSender);
        hashMap.put("chat_receiver", chatReceiver);
        hashMap.put("chat_message", chatMessage);
        hashMap.put("chat_seenchat", "no");
        hashMap.put("delete_sender", "delete");
        hashMap.put("delete_receiver", "delete");

        firebaseFirestore.collection("chats")
                .add(hashMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            final HashMap<String, Object> userChatHashMap = new HashMap<>();
                            userChatHashMap.put("user_datesent", Timestamp.now());
                            userChatHashMap.put("user_sender", chatSender);
                            userChatHashMap.put("user_receiver", chatReceiver);
                            userChatHashMap.put("user_message", chatMessage);
                            userChatHashMap.put("user_unread", "0");


                            final HashMap<String, Object> chatSetHashMap = new HashMap<>();
                            chatSetHashMap.put("user_datesent", Timestamp.now());
                            chatSetHashMap.put("user_sender", chatReceiver);
                            chatSetHashMap.put("user_receiver", chatSender);
                            chatSetHashMap.put("user_message", chatMessage);
                            chatSetHashMap.put("user_unread", "0");

                            final HashMap<String, Object> chatUpdateHashMap = new HashMap<>();
                            chatUpdateHashMap.put("user_datesent", Timestamp.now());
                            chatUpdateHashMap.put("user_sender", chatReceiver);
                            chatUpdateHashMap.put("user_receiver", chatSender);
                            chatUpdateHashMap.put("user_message", chatMessage);

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("chats")
                                    .document(profileUser)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()) {
                                                firebaseFirestore.collection("users")
                                                        .document(currentUser)
                                                        .collection("chats")
                                                        .document(profileUser)
                                                        .update(userChatHashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("users")
                                                                            .document(profileUser)
                                                                            .collection("chats")
                                                                            .document(currentUser)
                                                                            .update(chatUpdateHashMap)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {

                                                                                    } else {
                                                                                        firebaseFirestore.collection("users")
                                                                                                .document(profileUser)
                                                                                                .collection("chats")
                                                                                                .document(currentUser)
                                                                                                .set(chatSetHashMap);
                                                                                    }
                                                                                }
                                                                            });


                                                                }
                                                            }
                                                        });


                                            } else {

                                                firebaseFirestore.collection("users")
                                                        .document(currentUser)
                                                        .collection("chats")
                                                        .document(profileUser)
                                                        .set(userChatHashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("users")
                                                                            .document(profileUser)
                                                                            .collection("chats")
                                                                            .document(currentUser)
                                                                            .set(chatSetHashMap);

                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                });


        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("chats")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        if (task.getResult().exists()) {

                            stringUnread = task.getResult().getString("user_unread");
                            intUnread = Integer.parseInt(stringUnread) + 1;
                            stringUnreadz = String.valueOf(intUnread);

                            final HashMap<String, Object> chatUnreadHashMap = new HashMap<>();
                            chatUnreadHashMap.put("user_unread", stringUnreadz);

                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("chats")
                                    .document(currentUser)
                                    .update(chatUnreadHashMap);

                        } else {

                        }

                    }
                });
    }

    private void readMessage(
            final String myid,
            final String userid) {

        chatsListenerRegistration = firebaseFirestore.collection("chats")
                .orderBy("chat_datesent", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                         @Override
                                         public void onEvent(
                                                 @Nullable QuerySnapshot queryDocumentSnapshots,
                                                 @Nullable FirebaseFirestoreException e) {
                                             if (queryDocumentSnapshots != null) {
                                                 for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                                     if (doc.getType() == DocumentChange.Type.ADDED) {
                                                         ChatsClass chatsClass = doc.getDocument().toObject(ChatsClass.class);
                                                         if (
                                                                 (chatsClass.getChat_receiver().equals(myid) &&
                                                                         chatsClass.getChat_sender().equals(userid) ||
                                                                         chatsClass.getChat_receiver().equals(userid) &&
                                                                                 chatsClass.getChat_sender().equals(myid)) &&
                                                                         (!chatsClass.getDelete_sender().equals(myid) &&
                                                                                 !chatsClass.getDelete_receiver().equals(myid))
                                                         ) {
                                                             listChatsClass.add(chatsClass);
                                                         }
                                                     }

                                                     if (doc.getType() == DocumentChange.Type.MODIFIED) {

                                                         ChatsClass chatsClassMod = doc.getDocument().toObject(ChatsClass.class);
                                                         for (int i = 0; i < listChatsClass.size(); i++) {
                                                             if (doc.getDocument().getDate("chat_datesent").equals(listChatsClass.get(i).getChat_datesent())) {
                                                                 if (doc.getDocument().getString("chat_message").equals(listChatsClass.get(i).getChat_message())) {
                                                                     listChatsClass.set(i, chatsClassMod);
                                                                 }
                                                             }
                                                         }

                                                     }
                                                 }

                                                 messageAdapter = new MessageAdapter(listChatsClass, MessageActivity.this);
                                                 recyclerViewMessageView.setAdapter(messageAdapter);
                                             }
                                         }

                                     }
                );
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        menuMessage = menu;

        menu.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);


        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        switch (item.getItemId()) {

            case (R.id.menuUserUnmatchUser):
                UnmatchUser();
                return true;

            case (R.id.menuUserDeleteChat):
                DeleteChat();
                return true;

            case (R.id.menuUserBlockUser):
                BlockUser();
                return true;


            case (R.id.menuUserFavoriteUser):
                FavoriteUser();
                return true;

            case (R.id.menuUserReportUser):
                ReportUser();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        BlockCheck();
        FavoriteCheck();
        UnmatchCheck();

        return super.onPrepareOptionsMenu(menu);
    }


    private void LikesUser() {


        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("likes")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            Map<String, Object> mapBlockUser = new HashMap<>();
                            mapBlockUser.put("user_likes", currentUser);
                            mapBlockUser.put("user_liked", Timestamp.now());
                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("likes")
                                    .document(currentUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });

                        } else {

                            Map<String, Object> mapBlockUser = new HashMap<>();
                            mapBlockUser.put("user_likes", currentUser);
                            mapBlockUser.put("user_liked", Timestamp.now());
                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("likes")
                                    .document(currentUser)
                                    .set(mapBlockUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    private void BlockUser() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUser = firebaseUser.getUid();
        profileUser = getIntent().getStringExtra("user_uid");


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("block")
                                    .document(profileUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MessageActivity.this,
                                                        "You have unblocked this user!", Toast.LENGTH_SHORT).show();

                                                BlockCheck();


                                            }
                                        }
                                    });
                        } else {
                            Map<String, Object> mapBlockUser = new HashMap<>();
                            mapBlockUser.put("user_block", profileUser);
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("block")
                                    .document(profileUser)
                                    .set(mapBlockUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MessageActivity.this,
                                                        "You have blocked this user!", Toast.LENGTH_SHORT).show();

                                                BlockCheck();

                                            }
                                        }
                                    });
                        }
                    }
                });

    }


    private void BlockCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Unblock User");
                        } else {
                            menuMessage.findItem(R.id.menuUserBlockUser).setTitle("Block User");

                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("block")
                                    .document(currentUser)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()) {
                                            } else {
                                                ChatControl();

                                            }
                                        }
                                    });

                        }
                    }
                });


    }

    private void BlockCheckChat() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                        } else {
                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("block")
                                    .document(currentUser)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()) {
                                            } else {
                                                ChatControl();
                                            }
                                        }
                                    });

                        }
                    }
                });


    }


    private void ChatControl() {

        if (editTextChatHide.equals("yes")) {
            relativeLayoutMessageChat.setVisibility(View.GONE);
        } else {
            relativeLayoutMessageChat.setVisibility(View.VISIBLE);
        }

    }


    private void DeleteChat() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUser = firebaseUser.getUid();
        profileUser = getIntent().getStringExtra("user_uid");

        deleteChatListenerRegistration = firebaseFirestore.collection("chats")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (queryDocumentSnapshots != null) {
                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                final ChatsClass chatsClass = doc.getDocument().toObject(ChatsClass.class);
                                if (
                                        (chatsClass.getChat_receiver().equals(currentUser) &&
                                                chatsClass.getChat_sender().equals(profileUser) ||
                                                chatsClass.getChat_receiver().equals(profileUser) &&
                                                        chatsClass.getChat_sender().equals(currentUser))
                                ) {
                                    if (chatsClass.getDelete_sender().equals("delete")) {

                                        Map<String, Object> arrayDeleteSender = new HashMap<>();
                                        arrayDeleteSender.put("delete_sender", currentUser);
                                        firebaseFirestore.collection("chats")
                                                .document(doc.getDocument().getId())
                                                .update(arrayDeleteSender);
                                    } else if (chatsClass.getDelete_receiver().equals("delete")) {

                                        Map<String, Object> arrayDeleteReceiver = new HashMap<>();
                                        arrayDeleteReceiver.put("delete_receiver", currentUser);
                                        firebaseFirestore.collection("chats")
                                                .document(doc.getDocument().getId())
                                                .update(arrayDeleteReceiver);
                                    }


                                }


                            }


                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("chats")
                                    .document(profileUser)
                                    .delete();


                            deleteChatListenerRegistration.remove();

                            Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                            intent.putExtra("tab_show", "tab_chats");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });

    }


    private void FavoriteUser() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUser = firebaseUser.getUid();
        profileUser = getIntent().getStringExtra("user_uid");

        Map<String, Object> arrayFavoriteUser = new HashMap<>();
        arrayFavoriteUser.put("user_favorite", profileUser);
        arrayFavoriteUser.put("user_favorited", Timestamp.now());


        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("favors")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("favors")
                                    .document(profileUser)
                                    .set(arrayFavoriteUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MessageActivity.this,
                                                        "User added in favorite", Toast.LENGTH_SHORT).show();

                                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);

                                            }
                                        }
                                    });
                        } else {
                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("favors")
                                    .document(profileUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MessageActivity.this,
                                                        "User removed from favorite", Toast.LENGTH_SHORT).show();

                                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_off);

                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void FavoriteCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("favors")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            if (documentSnapshot.exists()) {
                                menuMessage.findItem(R.id.menuUserFavoriteUser).setIcon(R.drawable.menu_favorite_on);

                            }
                        }
                    }
                });
    }


    private void UnmatchCheck() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(true);

                        } else {
                            menuMessage.findItem(R.id.menuUserUnmatchUser).setVisible(false);

                        }
                    }
                });

    }


    private void UnmatchUser() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = firebaseUser.getUid();

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("matches")
                .document(profileUser)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("loves")
                                    .document(profileUser)
                                    .delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                firebaseFirestore.collection("users")
                                                        .document(profileUser)
                                                        .collection("matches")
                                                        .document(currentUser)
                                                        .delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("users")
                                                                            .document(profileUser)
                                                                            .collection("loves")
                                                                            .document(currentUser)
                                                                            .delete()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {

                                                                                        firebaseFirestore.collection("users")
                                                                                                .document(profileUser)
                                                                                                .collection("likes")
                                                                                                .document(currentUser)
                                                                                                .delete()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            Toast.makeText(MessageActivity.this,
                                                                                                                    "User removed from your matches!", Toast.LENGTH_SHORT).show();


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
                    }
                });

    }


    private void ReportUser() {

        Intent intent = new Intent(MessageActivity.this, ReportActivity.class);
        intent.putExtra("user_uid", profileUser);
        startActivity(intent);


    }

    private void SeenMessage(final String userid) {

        currentUser = firebaseAuth.getCurrentUser().getUid();
        profileUser = getIntent().getStringExtra("user_uid");

        firebaseFirestore.collection("chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot querySnapshot : task.getResult()) {

                            ChatsClass chatsClass = querySnapshot.toObject(ChatsClass.class);

                            if (chatsClass.getChat_receiver().equals(firebaseUser.getUid()) &&
                                    chatsClass.getChat_sender().equals(userid)) {

                                HashMap<String, Object> seenHashMap = new HashMap<>();
                                seenHashMap.put("chat_seenchat", "yes");
                                seenHashMap.put("chat_dateseen", Timestamp.now());

                                if (chatsClass.getChat_seenchat().equals("no")) {

                                    querySnapshot.getReference().update(seenHashMap);
                                }
                            }
                        }
                    }
                });

        firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("chats")
                .document(profileUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {

                            final HashMap<String, Object> chatUnresetHashMap = new HashMap<>();
                            chatUnresetHashMap.put("user_unread", "0");

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("chats")
                                    .document(profileUser)
                                    .update(chatUnresetHashMap);

                        } else {

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        BlockCheckChat();


        if (Application.notificationManagerCompat != null) {
            Application.notificationManagerCompat.cancelAll();
        }

        profileUser = getIntent().getStringExtra("user_uid");


        UserStatus("online");


        UserCurrent();
        UserProfile();

    }


    private void UserCurrent() {
        firebaseFirestore.collection("users")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {
                            genderCurrent = documentSnapshot.getString("user_gender");
                            imageCurrent = documentSnapshot.getString("user_image");
                            verifiedCurrent = documentSnapshot.getString("user_verified");
                            premiumCurrent = documentSnapshot.getString("user_premium");
                            countryCurrent = documentSnapshot.getString("user_country");

                            String matchCurrent = documentSnapshot.getString("show_match");

                            if (matchCurrent != null && matchCurrent.equals("yes")) {

                                firebaseFirestore.collection("users")
                                        .document(currentUser)
                                        .collection("matches")
                                        .document(profileUser)
                                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                if (documentSnapshot != null && !documentSnapshot.exists()) {

                                                    editTextChatHide = "yes";

                                                    ChatControl();

                                                }
                                            }
                                        });


                            }


                        }
                    }
                });

        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("chats")
                .document(currentUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            UserChats("yes");
                        } else {
                            UserChats("no");

                        }
                    }
                });
    }


    private void UserChats(String stringChats) {

        firebaseFirestore.collection("users")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

                            String block_stranger = documentSnapshot.getString("block_stranger");

                            if (block_stranger != null && block_stranger.equals("yes") && stringChats.equals("no")) {

                                editTextChatHide = "yes";
                                ChatControl();

                            }

                        }
                    }
                });


    }


    private void UserProfile() {

        firebaseFirestore.collection("users")
                .document(profileUser)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null) {

                            String user_name = documentSnapshot.getString("user_name");
                            String user_status = documentSnapshot.getString("user_status");
                            String user_gender = documentSnapshot.getString("user_gender");
                            String user_birthage = documentSnapshot.getString("user_birthage");
                            String user_thumb = documentSnapshot.getString("user_thumb");
                            String user_country = documentSnapshot.getString("user_country");

                            String share_birthage = documentSnapshot.getString("share_birthage");

                            String show_match = documentSnapshot.getString("show_match");
                            String show_status = documentSnapshot.getString("show_status");

                            String block_genders = documentSnapshot.getString("block_genders");
                            String block_photos = documentSnapshot.getString("block_photos");

                            String allow_verified = documentSnapshot.getString("allow_verified");
                            String allow_premium = documentSnapshot.getString("allow_premium");
                            String allow_country = documentSnapshot.getString("allow_country");


                            if (show_match != null && show_match.equals("yes")) {

                                firebaseFirestore.collection("users")
                                        .document(currentUser)
                                        .collection("matches")
                                        .document(profileUser)
                                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                if (documentSnapshot != null && !documentSnapshot.exists()) {

                                                    editTextChatHide = "yes";

                                                    ChatControl();
                                                }
                                            }
                                        });
                            }

                            if (block_genders != null && block_genders.equals("yes") && user_gender.equals(genderCurrent)) {

                                editTextChatHide = "yes";

                                ChatControl();

                            }

                            if (block_photos != null && block_photos.equals("yes") && imageCurrent.equals("image")) {

                                editTextChatHide = "yes";

                                ChatControl();
                            }


                            if (allow_verified != null && allow_verified.equals("yes") && verifiedCurrent.equals("no")) {

                                editTextChatHide = "yes";

                                ChatControl();
                            }


                            if (allow_premium != null && allow_premium.equals("yes") && premiumCurrent.equals("no")) {

                                editTextChatHide = "yes";

                                ChatControl();
                            }


                            if (allow_country != null && allow_country.equals("yes") && !countryCurrent.equals(user_country)) {

                                editTextChatHide = "yes";

                                ChatControl();

                            }


                            String[] splitUserName = user_name.split(" ");
                            toolbarTextViewUserName.setText(splitUserName[0]);

                            if (show_status != null && show_status.equals("yes")) {
                                toolbarTextViewUserStatus.setText(user_status);
                            } else {
                                toolbarTextViewUserStatus.setText("offline");
                            }


                            if (user_thumb.equals("thumb")) {
                                Picasso.get().load(R.drawable.profile_image).into(toolbarImageViewUserImage);

                            } else {
                                Picasso.get().load(user_thumb).into(toolbarImageViewUserImage);
                            }

                        }
                    }
                });


    }

    private void UserStatus(String status) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUser = firebaseUser.getUid();

        Map<String, Object> arrayUserStatus = new HashMap<>();
        arrayUserStatus.put("user_status", status);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users")
                .document(currentUser)
                .update(arrayUserStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();

        profileUser = getIntent().getStringExtra("user_uid");
        SeenMessage(profileUser);
        currentUser = firebaseAuth.getCurrentUser().getUid();

        blockChatUserListenerRegistration = firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("block")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                if (doc.getDocument().getId().equals(currentUser)) {
                                    Toast.makeText(MessageActivity.this,
                                            "You have been blocked by this user!",
                                            Toast.LENGTH_SHORT).show();

                                    editTextMessageText.setVisibility(View.GONE);
                                    buttonMessageSend.setVisibility(View.GONE);
                                }
                            }
                            if (doc.getType() == DocumentChange.Type.REMOVED) {
                                if (doc.getDocument().getId().equals(currentUser)) {
                                    firebaseFirestore.collection("users")
                                            .document(currentUser)
                                            .collection("block")
                                            .document(profileUser)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.getResult().exists()) {
                                                    } else {
                                                        editTextMessageText.setVisibility(View.VISIBLE);
                                                        buttonMessageSend.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });


        blockCurrentListenerRegistration = firebaseFirestore.collection("users")
                .document(currentUser)
                .collection("block")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                if (doc.getDocument().getId().equals(profileUser)) {
                                    Toast.makeText(MessageActivity.this,
                                            "Please unblock user to send message!",
                                            Toast.LENGTH_SHORT).show();
                                    editTextMessageText.setVisibility(View.GONE);
                                    buttonMessageSend.setVisibility(View.GONE);
                                }
                            }
                            if (doc.getType() == DocumentChange.Type.REMOVED) {
                                if (doc.getDocument().getId().equals(profileUser)) {
                                    firebaseFirestore.collection("users")
                                            .document(profileUser)
                                            .collection("block")
                                            .document(currentUser)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.getResult().exists()) {
                                                    } else {
                                                        editTextMessageText.setVisibility(View.VISIBLE);
                                                        buttonMessageSend.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });
    }


    @Override
    protected void onPause() {
        super.onPause();
        UserStatus("offline");
        Application.appRunning = false;

        blockCurrentListenerRegistration.remove();
        blockChatUserListenerRegistration.remove();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @android.support.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == MESSAGE_IMAGE && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                final Uri resultUri = result.getUri();
                File fileThumb = new File(resultUri.getPath());
                String filePath = fileThumb.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUser = firebaseUser.getUid();

                String currentCover = "chat__";
                ThumbUpload(bitmap);


                dialog = new ProgressDialog(MessageActivity.this);
                dialog.setTitle("Please Wait");
                dialog.setMessage("Sending Photo...");
                dialog.setCancelable(false);
                dialog.show();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void ThumbUpload(final Bitmap bitmap) {

        bitmapThumb = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        ByteArrayOutputStream baosThumb = new ByteArrayOutputStream();
        bitmapThumb.compress(Bitmap.CompressFormat.JPEG, 75, baosThumb);
        byte[] byteThumb = baosThumb.toByteArray();

        long longTime = System.currentTimeMillis();
        String stringTime = String.valueOf(longTime);

        final StorageReference storageChat = storageReference
                .child(currentUser)
                .child("images")
                .child("chats")
                .child("chat_" + stringTime + ".jpg");
        storageChat.putBytes(byteThumb)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {

                            storageChat.getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri uriThumb = task.getResult();
                                            stringThumb = uriThumb.toString();

                                            Map<String, Object> mapThumb = new HashMap<>();
                                            mapThumb.put("chat_image", stringThumb);

                                            sendImage(stringThumb);

                                        }
                                    });
                        }
                    }
                });
    }


    private void sendImage(final String chatImage) {

        String chatMessage = "picture message!";

        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("chat_datesent", Timestamp.now());
        hashMap.put("chat_dateseen", Timestamp.now());
        hashMap.put("chat_sender", currentUser);
        hashMap.put("chat_receiver", profileUser);
        hashMap.put("chat_message", chatMessage);
        hashMap.put("chat_image", chatImage);
        hashMap.put("chat_media", "yes");
        hashMap.put("chat_seenchat", "no");
        hashMap.put("delete_sender", "delete");
        hashMap.put("delete_receiver", "delete");

        firebaseFirestore.collection("chats")
                .add(hashMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {

                            final HashMap<String, Object> userChatHashMap = new HashMap<>();
                            userChatHashMap.put("user_datesent", Timestamp.now());
                            userChatHashMap.put("user_sender", currentUser);
                            userChatHashMap.put("user_receiver", profileUser);
                            userChatHashMap.put("user_message", chatMessage);
                            userChatHashMap.put("user_unread", "0");


                            final HashMap<String, Object> chatSetHashMap = new HashMap<>();
                            chatSetHashMap.put("user_datesent", Timestamp.now());
                            chatSetHashMap.put("user_sender", profileUser);
                            chatSetHashMap.put("user_receiver", currentUser);
                            chatSetHashMap.put("user_message", chatMessage);
                            chatSetHashMap.put("user_unread", "0");

                            final HashMap<String, Object> chatUpdateHashMap = new HashMap<>();
                            chatUpdateHashMap.put("user_datesent", Timestamp.now());
                            chatUpdateHashMap.put("user_sender", profileUser);
                            chatUpdateHashMap.put("user_receiver", currentUser);
                            chatUpdateHashMap.put("user_message", chatMessage);

                            firebaseFirestore.collection("users")
                                    .document(currentUser)
                                    .collection("chats")
                                    .document(profileUser)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().exists()) {
                                                firebaseFirestore.collection("users")
                                                        .document(currentUser)
                                                        .collection("chats")
                                                        .document(profileUser)
                                                        .update(userChatHashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("users")
                                                                            .document(profileUser)
                                                                            .collection("chats")
                                                                            .document(currentUser)
                                                                            .update(chatUpdateHashMap)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                    } else {
                                                                                        firebaseFirestore.collection("users")
                                                                                                .document(profileUser)
                                                                                                .collection("chats")
                                                                                                .document(currentUser)
                                                                                                .set(chatSetHashMap)
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        dialog.dismiss();
                                                                                                    }
                                                                                                });

                                                                                    }
                                                                                }
                                                                            });


                                                                }
                                                            }
                                                        });


                                            } else {

                                                firebaseFirestore.collection("users")
                                                        .document(currentUser)
                                                        .collection("chats")
                                                        .document(profileUser)
                                                        .set(userChatHashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    firebaseFirestore.collection("users")
                                                                            .document(profileUser)
                                                                            .collection("chats")
                                                                            .document(currentUser)
                                                                            .set(chatSetHashMap)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    dialog.dismiss();
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


        firebaseFirestore.collection("users")
                .document(profileUser)
                .collection("chats")
                .document(currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        if (task.getResult().exists()) {

                            stringUnread = task.getResult().getString("user_unread");
                            intUnread = Integer.parseInt(stringUnread) + 1;
                            stringUnreadz = String.valueOf(intUnread);

                            final HashMap<String, Object> chatUnreadHashMap = new HashMap<>();
                            chatUnreadHashMap.put("user_unread", stringUnreadz);

                            firebaseFirestore.collection("users")
                                    .document(profileUser)
                                    .collection("chats")
                                    .document(currentUser)
                                    .update(chatUnreadHashMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.dismiss();
                                        }
                                    });

                        } else {

                        }

                    }
                });
    }


}
