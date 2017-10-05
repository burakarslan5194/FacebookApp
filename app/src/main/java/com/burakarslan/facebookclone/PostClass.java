package com.burakarslan.facebookclone;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.burakarslan.facebookclone.R.id.imageView2;

/**
 * Created by Burak on 9/24/2017.
 */

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> userEmail;
    private final ArrayList<String> userComment;
    private final ArrayList<String> userImage;
    private  final Activity context;


    public PostClass(ArrayList<String> userEmail,  ArrayList<String> userImage,ArrayList<String> userComment, FragmentAnasayfa context) {
        super(context.getActivity(),R.layout.custom_view,userEmail);
        this.userEmail = userEmail;
        this.userComment = userComment;
        this.userImage = userImage;
        this.context = context.getActivity();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_view,null,true);

        final TextView userEmailText=(TextView) customView.findViewById(R.id.userName);
        TextView commentText=(TextView) customView.findViewById(R.id.commentText);
        ImageView imageView=(ImageView) customView.findViewById(imageView2);

        userEmailText.setText(userEmail.get(position));
        commentText.setText(userComment.get(position));

        Picasso.with(context).load(userImage.get(position)).into(imageView);

        userEmailText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(context,FriendsProfile.class);
        intent.putExtra("Email",userEmailText.getText());
        context.startActivity(intent);
    }
});

        return customView;
    }

}
