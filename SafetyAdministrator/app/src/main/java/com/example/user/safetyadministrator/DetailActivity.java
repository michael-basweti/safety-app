package com.example.user.safetyadministrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private String mPost_key=null;
    private DatabaseReference mDatabase;

    private ImageView mBlogSingleImage;
    private TextView mBlogSingleTitle;
    private TextView mBlogSingleDesc;



    private Button mSingleRemoveBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Request");

        mDatabase.keepSynced(true);


        mAuth= FirebaseAuth.getInstance();

        mPost_key=getIntent().getExtras().getString("blog_id");

        mBlogSingleDesc=(TextView)findViewById(R.id.singleBlogDesc);
        mBlogSingleImage=(ImageView)findViewById(R.id.singleBlogImage);
        mBlogSingleTitle=(TextView) findViewById(R.id.singleBlogTitle);


        mSingleRemoveBtn=(Button)findViewById(R.id.singleRemoveBtn);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.keepSynced(true);
                String post_title=(String)dataSnapshot.child("username").getValue();
                String post_desc=(String)dataSnapshot.child("description").getValue();
                String post_image=(String)dataSnapshot.child("image").getValue();

                String post_uid=(String)dataSnapshot.child("uid").getValue();

                mBlogSingleTitle.setText(post_title);
                mBlogSingleDesc.setText(post_desc);


                Picasso.with(DetailActivity.this).load(post_image).into(mBlogSingleImage);

                if (mAuth.getCurrentUser().getUid().equals(post_uid)){

                    mSingleRemoveBtn.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();
                Intent mainIntent=new Intent(DetailActivity.this,MainActivity.class);
                startActivity(mainIntent);

            }
        });

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

