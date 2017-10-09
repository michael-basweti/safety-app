package com.example.user.safetyadministrator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseUsers;
    private boolean mProcessLike=false;
    private DatabaseReference mDatabaseLikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null){

                    Intent homeIntent=new Intent(MainActivity.this,LoginActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }

            }
        };

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Request");



        mDatabase.keepSynced(true);


        mBlogList=(RecyclerView)findViewById(R.id.blog_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.keepSynced(true);



        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                final String post_key=getRef(position).getKey();

                viewHolder.setPhone_Number(model.getPhone_Number());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setAge(model.getAge());
                viewHolder.setGender(model.getGender());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsername(model.getUsername());
                viewHolder.setTextLat(model.getLatitude());
                viewHolder.setTextLong(model.getLongitude());



                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this,post_key,Toast.LENGTH_LONG).show();

                        Intent singleBlogIntent=new Intent(MainActivity.this,MapsActivity.class);
                        singleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(singleBlogIntent);

                    }
                });
                viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Intent singleBlogIntent=new Intent(MainActivity.this,DetailActivity.class);
                        singleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(singleBlogIntent);
                        return true;

                    }
                });


            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }




    public static class BlogViewHolder extends RecyclerView.ViewHolder{


        View mView;

        FirebaseAuth mAuth;
        public BlogViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

            mAuth=FirebaseAuth.getInstance();


        }
        public void setGender(String gender){
            TextView post_title=(TextView)mView.findViewById(R.id.post_gender);
            post_title.setText(gender);
        }
        public void setPhone_Number(String gender){
            TextView post_title=(TextView)mView.findViewById(R.id.post_phone);
            post_title.setText(gender);
        }

        public void setAge(String age){
            TextView post_title=(TextView)mView.findViewById(R.id.post_age);
            post_title.setText(age);
        }
        public void setDescription(String desc){
            TextView post_desc=(TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setUsername(String username){
            TextView post_username=(TextView)mView.findViewById(R.id.post_username);
            post_username.setText(username);
        }
        public void setTextLat(String latitude){
            TextView textLat=(TextView)mView.findViewById(R.id.textLatitude);
            textLat.setText(latitude);
        }
        public void setTextLong(String longitude){
            TextView textLong=(TextView)mView.findViewById(R.id.textLongitude);
            textLong.setText(longitude);
        }
        public void setImage(Context ctx, String image){

            ImageView post_image=(ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (item.getItemId()==R.id.action_logout){

            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        mAuth.signOut();

    }
}

