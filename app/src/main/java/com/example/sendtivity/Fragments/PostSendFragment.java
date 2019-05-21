package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.sendtivity.Class.Post;
import com.example.sendtivity.Class.User;
import com.example.sendtivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


public class PostSendFragment extends Fragment {
    Post post;
    SharedPreferences sharedPreferences;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String UserJson;
    Gson gson;
    ImageView UserProfilePhoto;
    TextView UserName;
    ImageView PostImage;
    Button SendButton;
    EditText PostText;
    File localFile;
    Uri ImageUri;
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;

    public PostSendFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.post_send_fragment,container,false);
        sharedPreferences = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        UserJson = sharedPreferences.getString("UserJson","null");
        gson = new Gson();
        post = new Post();
        final User user = gson.fromJson(UserJson,User.class);
        UserProfilePhoto = view.findViewById(R.id.PS_Image_UserProfileImage);
        UserName = view.findViewById(R.id.PS_Text_UserName);
        PostImage = view.findViewById(R.id.PS_Edit_PostImage);
        SendButton = view.findViewById(R.id.PS_Button_Gonder);
        PostText = view.findViewById(R.id.PS_Edit_PostText);
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

                storageReference = FirebaseStorage
                        .getInstance()
                        .getReference()
                        .child("User")
                        .child(user.mAuthID)
                        .child("ProfilePhoto")
                        .child(user.profilePhoto.photoID);
        Picasso.get().load(user.profilePhoto.ImageUrl).placeholder(R.drawable.baseline_call_merge_black_48).into(UserProfilePhoto);
        post.UserProfileImageId = user.profilePhoto.photoID;
        post.PostUserImageUrl = user.profilePhoto.ImageUrl;
        UserName.setText(user.Name + " " +user.LastName);

        PostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoğraf Seçin"), 1);

            }
        });

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.PostText = PostText.getText().toString();
                post.PostID = databaseReference.push().getKey();
                post.UserName = user.Name+" "+user.LastName;
                post.UserID = user.mAuthID;
                post.PostImageId = databaseReference.push().getKey();
                storageReference = FirebaseStorage
                        .getInstance()
                        .getReference()
                        .child("Post");
               if(post.putImage){
                   storageReference.child(post.PostID).putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           storageReference.child(post.PostID).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                               @Override
                               public void onComplete(@NonNull Task<Uri> task) {
                                   if(task.isSuccessful()){
                                       Uri uri = task.getResult();
                                       post.PostImageId = uri.toString();
                                       databaseReference.child(post.PostID).setValue(post);

                                   }
                               }
                           });
                           Toast.makeText(getActivity(),"Tamamlandı",Toast.LENGTH_SHORT).show();

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           System.out.println(e.getMessage());

                       }
                   });
               }else{
                   databaseReference.child(post.PostID).setValue(post);
                   Toast.makeText(getActivity(),"Tamamlandı",Toast.LENGTH_SHORT).show();
               }
               getActivity().getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame,new TimeLineFragment()).commit();


            }
        });
        




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_PICK && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null )
        {
            Picasso.get().load(data.getData()).into(PostImage);
            ImageUri = data.getData();
            post.putImage = true;
        }
    }
}
