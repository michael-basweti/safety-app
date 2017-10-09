package com.example.user.safetyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSMS;
    TextView textLat;
    TextView textLong;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mSMS=(Button)findViewById(R.id.button2);
        textLat = (TextView) findViewById(R.id.textLat);
        textLong = (TextView) findViewById(R.id.textLong);
        mProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null){

                    Intent homeIntent=new Intent(AccountActivity.this,MainActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }

            }
        };
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Request");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, ll);


        mAuth= FirebaseAuth.getInstance();




    }
    class mylocationlistener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {

            if(location!=null){
                double pLong = location.getLongitude();
                double pLat=location.getLatitude();

                textLat.setText(Double.toString(pLat));
                textLong.setText(Double.toString(pLong));

            }
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        mSMS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messageTosend="Hi Admin,am in danger please help";
                String number="0701981451";
                SmsManager.getDefault().sendTextMessage(number,null,messageTosend,null,null);

                post();


            }
        });
    }


    private void post() {
        mProgress.setMessage("sending request...");
        mProgress.show();
        final String lat = textLat.getText().toString().trim();
        final String longitude = textLong.getText().toString().trim();
        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(longitude)) {


            final DatabaseReference newPost = mDatabase.push();


            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    newPost.child("latitude").setValue(lat);
                    newPost.child("longitude").setValue(longitude);

                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue("Username:" + dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    newPost.child("image").setValue(dataSnapshot.child("image").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    newPost.child("age").setValue("Age:" + dataSnapshot.child("age").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    newPost.child("phone_Number").setValue("Phone Number:" + dataSnapshot.child("Phone Number").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    newPost.child("description").setValue("Description:" + dataSnapshot.child("History").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                    newPost.child("gender").setValue("gender:" + dataSnapshot.child("gender").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(AccountActivity.this, "Please fill all the spaces or check your internet connection...", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mProgress.dismiss();
            Toast.makeText(AccountActivity.this, "Your Data Has been sent", Toast.LENGTH_LONG).show();

            Intent mainIntent = new Intent(AccountActivity.this, AccountActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.action_logout) {

            logout();

        }
        if (item.getItemId() == R.id.action_settings) {

            Intent mainIntent = new Intent(AccountActivity.this, HelpActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        mAuth.signOut();

    }

}
