package com.example.user.safetyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SetupActivity extends AppCompatActivity {
    private ImageButton mSetupImageBtn;
    private EditText mNameField;
    private Button mSubmitBtn;
    private EditText mAge;
    private EditText mBlood;
    private EditText mGender;
    private Uri mImageUri=null;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private StorageReference mStorageImage;
    private ProgressDialog mProgress;
    private EditText mHistory;
    private static final int GALLERY_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        mAuth=FirebaseAuth.getInstance();

        mStorageImage= FirebaseStorage.getInstance().getReference().child("Profile_images");


        mDatabaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress=new ProgressDialog(this);
        mSetupImageBtn=(ImageButton) findViewById(R.id.SetupImageBtn);
        mNameField=(EditText)findViewById(R.id.setupNameField);
        mSubmitBtn=(Button) findViewById(R.id.setupSubmitBtn);
        mHistory=(EditText) findViewById(R.id.history);
        mAge=(EditText)findViewById(R.id.setupAge);
        mBlood=(EditText) findViewById(R.id.setupBloodGroup);
        mGender=(EditText) findViewById(R.id.setupGender);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSetupAccount();

            }
        });

        mSetupImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);

            }
        });

    }


    private void startSetupAccount() {

        final String name = mNameField.getText().toString().trim();
        final String age = mAge.getText().toString().trim();
        final String gender = mGender.getText().toString().trim();
        final String bloodgroup = mBlood.getText().toString().trim();
        final String history = mHistory.getText().toString().trim();
        final String user_id=mAuth.getCurrentUser().getUid();
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(history)&&!TextUtils.isEmpty(age)&&!TextUtils.isEmpty(gender)&&!TextUtils.isEmpty(bloodgroup)&&mImageUri!=null){
            mProgress.setMessage("Finishing Setup");
            mProgress.show();

            StorageReference filepath=mStorageImage.child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri=taskSnapshot.getDownloadUrl().toString();

                    mDatabaseUsers.child(user_id).child("name").setValue(name);
                    mDatabaseUsers.child(user_id).child("age").setValue(age);
                    mDatabaseUsers.child(user_id).child("gender").setValue(gender);
                    mDatabaseUsers.child(user_id).child("Phone Number").setValue(bloodgroup);
                    mDatabaseUsers.child(user_id).child("History").setValue(history);
                    mDatabaseUsers.child(user_id).child("image").setValue(downloadUri);

                    mProgress.dismiss();

                    Intent mainIntent = new Intent(SetupActivity.this,AccountActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                }
            });



        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){

            Uri imageUri=data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)

                    .start(this);


        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                mImageUri=result.getUri();
                mSetupImageBtn.setImageURI(mImageUri);
            }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
            }
        }
    }
}


