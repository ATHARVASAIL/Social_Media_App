package com.example.social_media;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class fragment1 extends Fragment implements View.OnClickListener {

    ImageView imageView;
    TextView nameEt,profEt,bioEt,emailEt,phoneEt,postTv;
    ImageButton ib_edit,imageButtonMenu;
    Button btnsendmessage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1,container,false);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView=getActivity().findViewById(R.id.iv_f1);
        nameEt=getActivity().findViewById(R.id.tv_name_f1);
        profEt=getActivity().findViewById(R.id.tv_prof_f1);
        bioEt=getActivity().findViewById(R.id.tv_bio_f1);
        emailEt=getActivity().findViewById(R.id.tv_email_f1);
        phoneEt=getActivity().findViewById(R.id.tv_phone_no_f1);
        postTv = getActivity().findViewById(R.id.tv_post_f1);
        ib_edit = getActivity().findViewById(R.id.ib_edit_f1);
        imageButtonMenu = getActivity().findViewById(R.id.ib_menu_f1);
        btnsendmessage = getActivity().findViewById(R.id.btn_sendmessage_f1);

        imageButtonMenu.setOnClickListener(this);
        ib_edit.setOnClickListener(this);
        imageView.setOnClickListener(this);
        postTv.setOnClickListener(this);
        btnsendmessage.setOnClickListener(this);

        String nameResult,bioResult,emailResult,phoneResult,url,profResult;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.ib_edit_f1:
                Intent intent =new Intent(getActivity(),UpdateProfile.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                BottomSheetMenu bottomSheetMenu = new BottomSheetMenu();
                bottomSheetMenu.show(getFragmentManager(),"bottomsheet");
                break;
            case R.id.iv_f1:
                Intent intent1 =new Intent(getActivity(),ImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_post_f1:
                Intent intent5 =new Intent(getActivity(),IndividualPost.class);
                startActivity(intent5);
                break;
            case R.id.btn_sendmessage_f1:
                Intent in =new Intent(getActivity(),ChatActivity.class);
                startActivity(in);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final String[] nameResult = new String[1];
        final String[] bioResult = new String[1];
        final String[] emailResult = new String[1];
        final String[] phoneResult = new String[1];
        final String[] url = new String[1];
        final String[] profResult = new String[1];
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentid=user.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        reference= firestore.collection("user").document(currentid);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists()){

                             nameResult[0] = task.getResult().getString("name");
                             bioResult[0] = task.getResult().getString("bio");
                             emailResult[0] = task.getResult().getString("email");
                             phoneResult[0] = task.getResult().getString("phone_no");
                             url[0] = task.getResult().getString("url");
                             profResult[0] = task.getResult().getString("prof");


                        }else {
                            Intent intent = new Intent(getActivity(),CreateProfile.class);
                            startActivity(intent);
                        }
                        Picasso.get().load(url[0]).into(imageView);
                        nameEt.setText(nameResult[0]);
                        bioEt.setText(bioResult[0]);
                        emailEt.setText(emailResult[0]);
                        phoneEt.setText(phoneResult[0]);
                        profEt.setText(profResult[0]);
                    }
                });
    }
}
