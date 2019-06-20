package com.lzx.xiuxian.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.Item;

import java.util.Date;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item>  mItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ItemImage;
        TextView ItemName;

        public ViewHolder(View view){
            super(view);
            ItemImage = (ImageView)view.findViewById(R.id.item_image);
            ItemName = (TextView) view.findViewById(R.id.item_name);
        }

    }
    public ItemAdapter(List<Item> itemList){
        mItemList = itemList;
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
        Item item = mItemList.get(i);
        Date date = new Date(item.getStartTime());
        viewHolder.ItemName.setText(item.getName()+"\n"+(date.getMonth()+1)
                +"-"+(date.getDate())+"  "+date.getHours()+":"+date.getMinutes()
                +"    "+item.getActualTime()+"分钟"+"\n"+item.getSentence());
    }

    public int getItemCount() {
        return mItemList.size();
    }
}
