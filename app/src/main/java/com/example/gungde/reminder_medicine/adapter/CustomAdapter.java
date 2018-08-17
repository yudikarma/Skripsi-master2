package com.example.gungde.reminder_medicine.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gungde.reminder_medicine.R;
import com.example.gungde.reminder_medicine.Report;
import com.example.gungde.reminder_medicine.model.DataObat;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{
    private List<DataObat.Data> data;
    private Context context;

    public CustomAdapter(Context context,List<DataObat.Data> data){
        this.context = context;
        this.data = data;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtJudul;
        TextView txtJlh;
        TextView txtCatatan;
        TextView txtWaktu;
        TextView txtAlarm;
        TextView txtSisa;

        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtJudul = mView.findViewById(R.id.txtJudul);
            txtJlh = mView.findViewById(R.id.txtJlh);
            txtCatatan = mView.findViewById(R.id.txtCatatan);
            txtWaktu = mView.findViewById(R.id.txtWaktu);
            txtAlarm = mView.findViewById(R.id.txtAlarm);
            txtSisa = mView.findViewById(R.id.txtSisa);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Report.class);
                    i.putExtra("obat", data.get(getAdapterPosition()));
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtJudul.setText(data.get(position).getNama_obat());
        holder.txtJlh.setText(data.get(position).getJlh_maks());
        holder.txtCatatan.setText(data.get(position).getCatatan());
        holder.txtWaktu.setText(data.get(position).getWaktu_akhir());
        holder.txtAlarm.setText(data.get(position).getWaktu_alarm());
        holder.txtSisa.setText(data.get(position).getJlh_obat());
//        holder.txtAlarm.setText();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
