package com.burakarslan.facebookclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fenchtose.nocropper.CropperView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class CropActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;


    Button btnCrop,btnToggleGesture,btnSend;
    ImageView btnSnap,btnRotate;
    CropperView cropperView;
    Bitmap mBitmap,bitmap;
    Uri selected;

    boolean isSnappedtoCenter=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();

        initViews();



        Bundle extras =getIntent().getExtras();

        final byte [] byteArray=extras.getByteArray("bitmap");
       bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);



        cropperView.setImageBitmap(bitmap);
        //Bitmap originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.charlize1);

        btnCrop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cropImage();

            }

        });

        btnToggleGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled=cropperView.isGestureEnabled();
                cropperView.setGestureEnabled(!enabled);
                Toast.makeText(getBaseContext(),"Gesture :"+(enabled?"Enabled":"Disabled"),Toast.LENGTH_LONG).show();
            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSnappedtoCenter){
                    cropperView.cropToCenter();

                }else{

                    cropperView.fitToCenter();
                }
                isSnappedtoCenter= !isSnappedtoCenter;
            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropperView.setImageBitmap(rotateBitmap(mBitmap,90));
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UUID uuidImage=UUID.randomUUID();
                String imageName="images/"+uuidImage+".jpg";
                selected=getImageUri(getApplicationContext(),mBitmap);
                StorageReference storageReference=mStorageRef.child(imageName);
                storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String downloadURL=taskSnapshot.getDownloadUrl().toString();

                        FirebaseUser user=mAuth.getCurrentUser();
                        String userEmail=user.getEmail().toString();
                        String userComment="asdfg123";

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






            }
        });

    }

    private Bitmap rotateBitmap(Bitmap mBitmap, float angle) {
        Matrix matrix=new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);
    }

    private void cropImage() {
        mBitmap=cropperView.getCroppedBitmap();
        if(mBitmap!=null){
            cropperView.setImageBitmap(mBitmap);

        }
    }

    private void initViews(){



        btnSend=(Button) findViewById(R.id.btnSend);
        btnCrop=(Button) findViewById(R.id.btnCrop);
        btnToggleGesture=(Button) findViewById(R.id.btnToggleGesture);
        btnSnap=(ImageView) findViewById(R.id.snap_button);
        btnRotate=(ImageView) findViewById(R.id.rotate_button);
        cropperView=(CropperView) findViewById(R.id.imageView);


    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path= MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage,"title",null);
        return Uri.parse(path);

    }
}
