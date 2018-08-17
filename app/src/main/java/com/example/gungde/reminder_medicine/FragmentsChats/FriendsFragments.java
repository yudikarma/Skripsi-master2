package com.example.gungde.reminder_medicine.FragmentsChats;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.gungde.reminder_medicine.ActivityChats.ChatsRoom;
import com.example.gungde.reminder_medicine.ActivityChats.ProfilActivity;
import com.example.gungde.reminder_medicine.ActivityChats.UserDetail;
import com.example.gungde.reminder_medicine.R;

import com.example.gungde.reminder_medicine.model.Friends;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragments extends Fragment {
    private RecyclerView mFriendsList;
    private ImageView statusOnline;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mUserRef;
    private TextView notifnull;


    //private View mMainView;

    private FirebaseRecyclerAdapter<Friends,FriendsViewHolder> adapter;

    public FriendsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_friends_fragments, container, false);

        mFriendsList =  rootView.findViewById(R.id.friends_list);
        mAuth = FirebaseAuth.getInstance();
        statusOnline = (ImageView) rootView.findViewById(R.id.user_single_online_icon);
        notifnull = rootView.findViewById(R.id.notifnullfriends);

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));

        /* Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);*/
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id).orderByChild("date").limitToLast(50);
/*
        FirebaseRecyclerOptions<Friends> options =
                new FirebaseRecyclerOptions.Builder<Friends>()
                        .setQuery(query, Friends.class)
                        .setLifecycleOwner(this)
                        .build();*/
        FirebaseRecyclerOptions<Friends> options =
                new FirebaseRecyclerOptions.Builder<Friends>()
                        .setQuery(query2, Friends.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull Friends model) {
                holder.setDate(model.getDate());

                final String list_user_id = getRef(position).getKey();
                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String username = dataSnapshot.child("name").getValue().toString();
                        String image_thumb  = dataSnapshot.child("thumb_image").getValue().toString();

                        if (dataSnapshot.hasChild("online")){
                            String statusUseronline =  dataSnapshot.child("online").getValue().toString();
                            holder.setStatusOnline(statusUseronline);
                        }


                        holder.setName(username);
                        holder.setMcCircleImageView(image_thumb);

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]{"Open Profile","Send Message","Unfriends"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Select Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //click event for each item
                                        if (i == 0){
                                            Intent profilIntent = new Intent(getContext(),UserDetail.class);
                                            // i.putExtra("user_id",list_user_id);
                                            profilIntent.putExtra("user_id",list_user_id );
                                            startActivity(profilIntent);
                                        }
                                        if (i == 1){
                                            Intent profilIntent = new Intent(getContext(),ChatsRoom.class);
                                            // i.putExtra("user_id",list_user_id);
                                            profilIntent.putExtra("user_id",list_user_id );
                                            profilIntent.putExtra("user_name",username);
                                            startActivity(profilIntent);

                                        }if (i == 2){
                                            Intent profilIntent = new Intent(getContext(),ProfilActivity.class);
                                            // i.putExtra("user_id",list_user_id);
                                            profilIntent.putExtra("user_id",list_user_id );
                                            startActivity(profilIntent);
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout,parent,false);
                return new FriendsViewHolder(mView);
            }
        };
        FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long jumlah = dataSnapshot.getChildrenCount();
                if (jumlah>0){
                    mFriendsList.setAdapter(adapter);
                }else {
                    notifnull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();


        adapter.startListening();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mUserRef.child("online").setValue("true");

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mdisplayname,mstatus,userStatusView ;
        CircleImageView mcCircleImageView;
        ImageView userOnlineView;
        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mstatus  = (TextView) mView.findViewById(R.id.user_single_status);
            mcCircleImageView = (CircleImageView) mView.findViewById(R.id.profil_single_layout);

            userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);



        }

        public void setDate(String date){


            userStatusView.setText(date);

        }
        public void setName(String name){
            mdisplayname.setText(name);
        }
        public  void setMcCircleImageView(final String img_uri){
            //Picasso.with(UsersActivity.this).load(img_uri).placeholder(R.drawable.user).into(mcCircleImageView);
            if (!img_uri.equals("default")){
                Picasso.with(getContext()).load(img_uri).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.default_avatar).into(mcCircleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(img_uri).placeholder(R.drawable.default_avatar).into(mcCircleImageView);

                    }
                });

            }else{
                Picasso.with(getContext()).load(R.drawable.default_avatar).into(mcCircleImageView);

            }
        }

        public void setStatusOnline( String online){
            if (online.equals("true")){
                userOnlineView.setVisibility(View.VISIBLE);
            }else{
                userOnlineView.setVisibility(View.INVISIBLE);
            }

        }


    }

}
