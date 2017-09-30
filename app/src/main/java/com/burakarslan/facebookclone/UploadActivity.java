package com.burakarslan.facebookclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText editText2;
    ImageView imageView;
    Button button1;
    Button button2;
    Bitmap bitmap;
    Boolean b=false;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;
    Uri selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        if(b==true){
            Intent intent2=new Intent(getApplicationContext(),CropActivity.class);
            intent2.putExtra("bitmap",bitmap);
            Toast.makeText(getApplicationContext(),"sdfsdfsdf",Toast.LENGTH_LONG).show();
            startActivity(intent2);

        }

        editText2=(EditText) findViewById(R.id.editText3);
        imageView=(ImageView) findViewById(R.id.imageView);
        button1=(Button) findViewById(R.id.button3);
        button2=(Button) findViewById(R.id.button4);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();

    }

    /*public void uploadImage(View view){

        UUID uuidImage=UUID.randomUUID();
        String imageName="images/"+uuidImage+".jpg";

        StorageReference storageReference=mStorageRef.child(imageName);
        storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                String downloadURL=taskSnapshot.getDownloadUrl().toString();

                FirebaseUser user=mAuth.getCurrentUser();
                String userEmail=user.getEmail().toString();
                String userComment=editText2.getText().toString();

                UUID uuid=UUID.randomUUID();
                String uuidString=uuid.toString();

                myRef.child("Posts").child(uuidString).child("useremail").setValue(userEmail);
                myRef.child("Posts").child(uuidString).child("comment").setValue(userComment);
                myRef.child("Posts").child(uuidString).child("downloadURL").setValue(downloadURL);

                Toast.makeText(getApplicationContext(),"Post Shared",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),FeedActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }*/

    public void chooseImage(View view){

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }else{
           Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);







        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

               // Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // startActivityForResult(intent,2);

            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==2 && resultCode==RESULT_OK  && data!= null){

            selected=data.getData();
            try {
                b=true;
                bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected);






            // imageView.setImageBitmap(bitmap);

               Intent intent2=new Intent(this,CropActivity.class);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                intent2.putExtra("edittext3",editText2.getText().toString());
                intent2.putExtra("bitmapbytes",bytes);
                intent2.putExtra("imagePath", selected.toString());
               // Toast.makeText(getApplicationContext(),"sdfsdfsdf",Toast.LENGTH_LONG).show();
               startActivity(intent2);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"sdfsdfsdf",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"Olmadı",Toast.LENGTH_LONG).show();
        }


       super.onActivityResult(requestCode, resultCode, data);
    }



}
