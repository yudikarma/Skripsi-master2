package com.example.gungde.reminder_medicine.FragmentsChats;


import android.app.ProgressDialog;
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

import com.example.gungde.reminder_medicine.ActivityChats.ProfilActivity;
import com.example.gungde.reminder_medicine.R;
import com.example.gungde.reminder_medicine.model.Users;

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
public class RequestFragments extends Fragment {

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

    private FirebaseRecyclerAdapter<Users,UserviewHolder> adapter;


    public RequestFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*return  inflater.inflate(R.layout.fragment_request_fragments, container,false);*/
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_request_fragments, container, false);

        mFriendsList = rootView.findViewById(R.id.list_request);
        mAuth = FirebaseAuth.getInstance();
        statusOnline = rootView.findViewById(R.id.user_single_online_icon);
        notifnull = rootView.findViewById(R.id.notifnullrequest);

        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));

        //* Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);*//*
        Query query = FirebaseDatabase.getInstance().getReference().child("Friend_request").child(mCurrent_user_id).orderByValue().limitToLast(50);


        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, UserviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserviewHolder holder, int position, @NonNull Users model) {
                final String listuid = getRef(position).getKey();
                FirebaseDatabase.getInstance().getReference().child("Users").child(listuid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String displayname = dataSnapshot.child("name").getValue().toString();
                        String status = dataSnapshot.child("status").getValue().toString();
                        String thumimage = dataSnapshot.child("thumb_image").getValue().toString();
                        holder.setNama(displayname);
                        holder.setstatus(status);
                        holder.setMcCircleImageView(thumimage);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getContext(), ProfilActivity.class);
                        i.putExtra("user_id", user_id);
                        startActivity(i);

                    }
                });
            }

            @NonNull
            @Override
            public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout, parent, false);
                return new UserviewHolder(mView);
            }
        };

        FirebaseDatabase.getInstance().getReference().child("Friend_request").child(mCurrent_user_id).addValueEventListener(new ValueEventListener() {
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









    public class UserviewHolder extends RecyclerView.ViewHolder {
        View mView;

        TextView mdisplayname,mstatus ;
        CircleImageView mcCircleImageView;
        public UserviewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mstatus  = (TextView) mView.findViewById(R.id.user_single_status);
            mcCircleImageView = (CircleImageView) mView.findViewById(R.id.profil_single_layout);



        }
        public void setNama(String display_name){

            mdisplayname.setText(display_name);

        }
        public void setstatus(String status){
            mstatus.setText(status);
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
    }

}
