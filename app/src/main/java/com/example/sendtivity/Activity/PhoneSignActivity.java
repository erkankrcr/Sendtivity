package com.example.sendtivity.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sendtivity.Class.ProfilePhoto;
import com.example.sendtivity.Class.User;
import com.example.sendtivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;


public class PhoneSignActivity extends Activity {
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
    private ImageView imageView;
    private EditText NameET;
    private EditText LastNameET;
    private EditText EmailET;
    private ProgressBar progressBar;
    private String Name;
    private String LastName;
    private String Email;
    private Uri filePath;
    private User user;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseDatabase databaseRef;
    private DatabaseReference myRef;
    private StorageReference StoreRef;
    private ProfilePhoto profilePhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StoreRef = mStorageRef.child("User/"+mAuth.getUid()+"/ProfilePhoto/"+myRef.push());
        databaseRef = FirebaseDatabase.getInstance();
        myRef = databaseRef.getReference("User");
        profilePhoto = new ProfilePhoto();

        imageView = ((ImageView) findViewById(R.id.PSA_photoImage));
        NameET = findViewById(R.id.PSA_NameET);
        LastNameET = findViewById(R.id.PSA_LastnameET);
        EmailET = findViewById(R.id.PSA_emailET);
        progressBar = findViewById(R.id.PSA_Progress);




        findViewById(R.id.PSA_photoImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), IMAGE_PICK);
            }

        });

        findViewById(R.id.PSA_SuccessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                Name = NameET.getText().toString();
                LastName = LastNameET.getText().toString();
                Email = EmailET.getText().toString();
                if(Name.isEmpty() || LastName.isEmpty() || Email.isEmpty()){
                    Toast.makeText(
                            PhoneSignActivity.this,
                            "Alanların Doldurulması Zorunludur",
                            Toast.LENGTH_LONG
                    ).show();
                }else {
                    user.mAuthID = mAuth.getUid();
                    user.Name = Name;
                    user.LastName = LastName;
                    user.Email = Email;

                    StoreRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                    user.profilePhoto = profilePhoto;
                                    myRef.child(mAuth.getUid()).setValue(user);
                                    Toast.makeText(
                                            PhoneSignActivity.this,
                                            "Kayıt Tamamlandı",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }, 500);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          Toast.makeText(
                                  PhoneSignActivity.this,
                                  e.getMessage(),
                                  Toast.LENGTH_LONG
                          ).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });

                }
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICK && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            profilePhoto.setImageUrl(filePath.getPath());
            profilePhoto.setName(mAuth.getUid());

            Picasso.get().load(filePath).into(imageView);
        }


    }
}
