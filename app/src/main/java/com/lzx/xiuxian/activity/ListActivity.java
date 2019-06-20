package com.lzx.xiuxian.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.lzx.xiuxian.Adapter.ItemAdapter;
import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.Item;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Item> items = new ArrayList<>();
    private String TAG = "ListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.list);
            initItems();
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            ItemAdapter adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    private void initItems(){
        items = DataSupport.findAll(Item.class);
    }
}
