package com.lzx.xiuxian.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.User;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<User>  mUserList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ItemImage;
        TextView ItemName;

        public ViewHolder(View view){
            super(view);
            ItemImage = (ImageView)view.findViewById(R.id.item_image);
            ItemName = (TextView) view.findViewById(R.id.item_name);
        }

    }
    public FriendsAdapter(List<User> itemList){
        mUserList = itemList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        ViewHolder holder  = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = mUserList.get(i);
        viewHolder.ItemName.setText(user.getName()+"\n"+user.getPhone()+"\n"+"修行"+user.getScore()+"分钟");
    }

    public int getItemCount() {
        return mUserList.size();
    }
}
