package com.example.social_media;

import static com.example.social_media.R.id.rv_message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView;
    ImageButton send_btn,camera_btn,mic_btn;
    TextView username,typingtv;
    EditText messageEt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootref1,rootref2,typingref;
    MessageMember messageMember;
    Boolean typingchecker = false;
    String receiver_name,receiver_uid,sender_uid,url;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            url = bundle.getString("u");
            receiver_name = bundle.getString("n");
            receiver_uid = bundle.getString("uid");
        }else {
            Toast.makeText(this, "User Missing" ,Toast.LENGTH_SHORT).show();
        }

        messageMember = new MessageMember();
        recyclerView = findViewById(R.id.rv_message);
        camera_btn = findViewById(R.id.camera_send_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        imageView = findViewById(R.id.iv_message);
        messageEt = findViewById(R.id.messageEt);
        send_btn = findViewById(R.id.imageButton_send);
        username = findViewById(R.id.username_message_tv);
        typingtv= findViewById(R.id.typingstatus);

        Picasso.get().load(url).into(imageView);
        username.setText(receiver_name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sender_uid = user.getUid();

        rootref1 = database.getReference("Message").child(sender_uid).child(receiver_uid);
        rootref2 = database.getReference("Message").child(receiver_uid).child(sender_uid);
        typingref = database.getReference("typing");



        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }
        });

        typingref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(sender_uid).hasChild(receiver_uid))
                {
                    typingtv.setVisibility(View.VISIBLE);
                }
                else
                {
                    typingtv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        messageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                typing();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void typing() {

        typingchecker=true;
        typingref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(typingchecker.equals(true))
                {
                    if(snapshot.child(receiver_uid).hasChild(sender_uid))
                    {
                        typingchecker=false;
                    }
                    else {
                        typingref.child(receiver_uid).child(sender_uid).setValue(true);
                        typingchecker=false;
                    }
                }
                else
                {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MessageMember> options =
                new FirebaseRecyclerOptions.Builder<MessageMember>()
                        .setQuery(rootref1,MessageMember.class)
                        .build();
        FirebaseRecyclerAdapter<MessageMember,MessageViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<MessageMember, MessageViewHolder>(options) {
                    @Override
                    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull MessageMember model) {

                        holder.Set_message(getApplication(), model.getMessage(), model.getTime(),model.getDate(),model.getType(),model.getSender_uid(),model.getReceiver_uid());

                    }

                    @NonNull
                    @Override
                    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.message_layout,parent,false);
                        return new MessageViewHolder(view);
                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void SendMessage() {

        String message = messageEt.getText().toString();

        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());

        String time = savedate + ":" + savetime;

        if(message.isEmpty()){
            Toast.makeText(this, "Cannot send empty message", Toast.LENGTH_SHORT).show();
        }else {
            messageMember.setDate(savedate);
            messageMember.setTime(savetime);
            messageMember.setMessage(message);
            messageMember.setReceiver_uid(receiver_uid);
            messageMember.setSender_uid(sender_uid);
            messageMember.setType("text");

            String id = rootref1.push().getKey();
            rootref1.child(id).setValue(messageMember);

            String id1 = rootref2.push().getKey();
            rootref2.child(id1).setValue(messageMember);

            messageEt.setText("");
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        typingref.child(receiver_uid).child(sender_uid).removeValue();

    }
    public void onBackPressed(){
        super.onBackPressed();
        typingref.child(receiver_uid).child(sender_uid).removeValue();
    }

}