package com.example.gungde.reminder_medicine.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gungde.reminder_medicine.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    ArrayList<ItemsListSingleItem> data;
    Context mContext;
    CustomItemClickListener listener;

    public ItemListAdapter(Context mContext, ArrayList<ItemsListSingleItem> data, CustomItemClickListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemsListSingleItem item = data.get(position);
        holder.judul.setText(item.getJudul());
        holder.catatan.setText(item.getCatatan());
        holder.jumlah.setText(String.valueOf(item.getJumlah()));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, catatan, jumlah;

        ViewHolder(View v) {
            super(v);
            judul = v.findViewById(R.id.txtJudul);
            catatan = v.findViewById(R.id.txtCatatan);
            jumlah = v.findViewById(R.id.txtJlh);
        }
    }
}