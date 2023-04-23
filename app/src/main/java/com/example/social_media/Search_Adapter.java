//package com.example.social_media;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.searchViewHolder> {
//
//    Context context;
//    ArrayList<User> list;
//
//    public Search_Adapter(Context context, ArrayList<User> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v =LayoutInflater.from(context).inflate(R.layout.user_item, parent, false); return new searchViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
//
//        User user = list.get(position);
//
//        holder.name.setText(user.getName());
//
//        holder.prof.setText(user.getProf());
//
//        holder.url.setText(user.getUrl());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public static class searchViewHolder extends RecyclerView.ViewHolder{
//        TextView name,prof,url;
//        Button btn;
//        public searchViewHolder(@NonNull View itemView) {
//            super(itemView);
//            url=itemView.findViewById(R.id.image_profile);
//            prof=itemView.findViewById(R.id.prof);
//            name=itemView.findViewById(R.id.username);
//            btn=itemView.findViewById(R.id.btn_follow);
//
//        }
//    }
//}
