package com.example.sendtivity.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.Class.PersonInfo;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.fabric.sdk.android.Fabric;


public class PhoneSignActivity extends Activity {
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
    private ImageView imageView;
    private EditText NameET;
    private EditText LastNameET;
    private EditText EmailET;
    private Button Successbtn;
    private ProgressBar progressBar;
    private String Name;
    private String LastName;
    private String Email;
    private Uri filePath;
    private User user;
    private Intent intent;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseDatabase databaseRef;
    private DatabaseReference myRef;
    private StorageReference StoreRef;
    private String PhotoId;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1905;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sign);

        Fabric.with(this, new Crashlytics());

        boolean permission = checkAndRequestPermissions();
        if (!permission){
            Toast.makeText(this,"Gerekli İzinler Alındı",Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Olamaz!!")
                    .setMessage("Gerekli İzinler Sağlanamadığı için işleme Devam Edemiyorum.")
                    .setCancelable(false)
                    .setPositiveButton("Hallediyorum", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(!checkAndRequestPermissions()){
                                dialog.dismiss();
                            }else{
                                Toast.makeText(PhoneSignActivity.this,"İznler Alınamadı, Uygulama Çökebilir!!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance();
        myRef = databaseRef.getReference("User");
        mStorageRef = FirebaseStorage.getInstance().getReference();
        PhotoId = myRef.push().getKey();
        StoreRef = mStorageRef.child("User/"+mAuth.getUid()+"/ProfilePhoto/"+PhotoId);
        user = new User();
        intent = getIntent();
        imageView = ((ImageView) findViewById(R.id.PSA_photoImage));
        NameET = findViewById(R.id.PSA_NameET);
        LastNameET = findViewById(R.id.PSA_LastnameET);
        EmailET = findViewById(R.id.PSA_emailET);
        Successbtn = findViewById(R.id.PSA_SuccessBtn);
        progressBar = findViewById(R.id.PSA_Progress);




        findViewById(R.id.PSA_photoImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), IMAGE_PICK);
            }

        });


        Successbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    UploadUser();
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
            user.profilePhoto.photoID=PhotoId;
            user.profilePhoto.useProfilePhoto = true;

            Picasso.get().load(filePath).into(imageView);
        }


    }

    private void UploadUser(){
        user.mAuthID = mAuth.getUid();
        user.Name = Name;
        user.LastName = LastName;
        user.Email = Email;


        StoreRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(0);
                        user.PhoneNumberList = ContactList();
                        StoreRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                             if(task.isSuccessful()){
                                 Uri uri = task.getResult();
                                 user.profilePhoto.ImageUrl = uri.toString();
                                 user.PhoneNumber = intent.getStringExtra("PhoneNumber");
                                 myRef.child(mAuth.getUid()).setValue(user);
                                 Toast.makeText(
                                         PhoneSignActivity.this,
                                         "Kayıt Tamamlandı",
                                         Toast.LENGTH_LONG
                                 ).show();
                                 SharedPreferences sharedPreferences = getSharedPreferences("AppInfo",MODE_PRIVATE);
                                 SharedPreferences.Editor editor = sharedPreferences.edit();
                                 Gson gson = new Gson();
                                 String UserJson = gson.toJson(user);
                                 editor.putString("UserJson",UserJson);
                                 editor.putBoolean("RememberMe",true);
                                 editor.commit();
                                 Intent intent = new Intent(PhoneSignActivity.this,MainActivity.class);
                                 startActivity(intent);
                                 Successbtn.setVisibility(View.VISIBLE);
                             }
                            }
                        });
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
                Successbtn.setVisibility(View.VISIBLE);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Successbtn.setVisibility(View.INVISIBLE);
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
            }
        });
    }

    private ArrayList ContactList(){
        ArrayList list = new ArrayList();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        if(cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); // id ye göre eşleşme yapılacak
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); // telefonda kayıtlı olan ismi
                String lastname = "";
                String lastnumber="";
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // telefon numarasına sahip ise if içine gir.
                    Cursor person_cursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (person_cursor.moveToNext()) {
                        String person_phoneNumber = person_cursor.getString(person_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (lastname==name || lastnumber == person_phoneNumber){

                        }else{
                            list.add(person_phoneNumber.replace(" ", "")); // ismini ve telefon numarasını list içine at
                            lastnumber = person_phoneNumber;
                            lastname = name;
                        }

                    }
                    person_cursor.close();
                }

            }
        }
        return list;
    }

    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int permissionWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionREAD_CONTACTS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionREAD_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (permissionWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }
}
