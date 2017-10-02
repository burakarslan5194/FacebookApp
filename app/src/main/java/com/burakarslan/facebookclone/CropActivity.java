package com.burakarslan.facebookclone;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.burakarslan.facebookclone.R.id.imageView;

public class CropActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;


    Button btnCrop,btnToggleGesture,btnSend;
    ImageView btnSnap,btnRotate;
    CropperView cropperView;
    Bitmap mBitmap,bitmap,bmp;
    Uri selected,fileUri;
    int rotated=1;

    boolean isSnappedtoCenter=false;

    String commentText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        String image_path= intent.getStringExtra("imagePath");
        fileUri = Uri.parse(image_path);
        commentText=intent.getStringExtra("edittext3");

        initViews();



        //imageview.setImageUri(fileUri);

       //Bundle extras=new Bundle();
        //extras =getIntent().getExtras();

        //bitmap = getIntent().getParcelableExtra("bitmap");

        byte[] bytes = getIntent().getByteArrayExtra("bitmapbytes");
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

       // final byte [] byteArray=extras.getByteArray("bitmap");
       //bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);


        //Bitmap originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.charlize1);
        cropperView.setImageBitmap(bmp);


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

        cropperView.fitToCenter();

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rotated==1) {
                    cropperView.setImageBitmap(rotateBitmap(bmp, 90));
                    rotated=2;
                }
                else if (rotated==2){
                    cropperView.setImageBitmap(rotateBitmap(bmp, 180));
                    rotated=3;
                }
                else if (rotated==3){
                    cropperView.setImageBitmap(rotateBitmap(bmp, 270));
                    rotated=4;
                }
                else if (rotated==4){
                    cropperView.setImageBitmap(rotateBitmap(bmp, 360));
                    rotated=1;
                }


            }
        });

        /*btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                    }



               // selected=getImageUri(getApplicationContext(),cropperView.getCroppedBitmap());
                UUID uuidImage=UUID.randomUUID();
                String imageName="images/"+uuidImage+".jpg";
             // selected=getImageUri(getApplicationContext(),cropperView.getCroppedBitmap());


              // String s = saveToInternalStorage(cropperView.getCroppedBitmap());
               // selected= Uri.parse(s);
                Bitmap deneme=cropperView.getCroppedBitmap();
             String  s1=MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),deneme,"title",null);
                selected= Uri.parse(s1);


                StorageReference storageReference=mStorageRef.child(imageName);
                storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String downloadURL=taskSnapshot.getDownloadUrl().toString();

                        FirebaseUser user=mAuth.getCurrentUser();
                        String userEmail=user.getEmail().toString();
                        String userComment=commentText;

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
        });*/

    }






    private Bitmap rotateBitmap(Bitmap mBitmap, float angle) {

        Matrix matrix=new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);
    }

    private void cropImage() {
        bmp=cropperView.getCroppedBitmap();
        if(bmp!=null){
            cropperView.setImageBitmap(bmp);

        }
    }

    public void btnSend(View view) {

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            // selected=getImageUri(getApplicationContext(),cropperView.getCroppedBitmap());
            UUID uuidImage = UUID.randomUUID();
            String imageName = "images/" + uuidImage + ".jpg";
            // selected=getImageUri(getApplicationContext(),cropperView.getCroppedBitmap());


            // String s = saveToInternalStorage(cropperView.getCroppedBitmap());
            // selected= Uri.parse(s);
            Bitmap deneme = cropperView.getCroppedBitmap();
            String s1 = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), deneme, "title", null);
            selected = Uri.parse(s1);


            StorageReference storageReference = mStorageRef.child(imageName);
            storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    String downloadURL = taskSnapshot.getDownloadUrl().toString();

                    FirebaseUser user = mAuth.getCurrentUser();
                    String userEmail = user.getEmail().toString();
                    String userComment = commentText;

                    UUID uuid = UUID.randomUUID();
                    String uuidString = uuid.toString();

                    myRef.child("Posts").child(uuidString).child("useremail").setValue(userEmail);
                    myRef.child("Posts").child(uuidString).child("comment").setValue(userComment);
                    myRef.child("Posts").child(uuidString).child("downloadURL").setValue(downloadURL);

                    Toast.makeText(getApplicationContext(), "Post Shared", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

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

    private void initViews(){



        btnSend=(Button) findViewById(R.id.btnSend);
        btnCrop=(Button) findViewById(R.id.btnCrop);
        btnToggleGesture=(Button) findViewById(R.id.btnToggleGesture);

        btnRotate=(ImageView) findViewById(R.id.rotate_button);
        cropperView=(CropperView) findViewById(imageView);


    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path=MediaStore.Images.Media.insertImage(inContext.getContentResolver(),inImage,"title",null);
        return Uri.parse(path);

    }



    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.imgPicker);
           // img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }


}
