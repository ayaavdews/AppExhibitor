package com.example.AppExhibitor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.AppExhibitor.Modul.ModelRecycler;
import com.example.AppExhibitor.R;

import java.util.ArrayList;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ModelRecycler> dataModelArrayList;

    public RetrofitAdapter(Context ctx, ArrayList<ModelRecycler> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public RetrofitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.retro_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RetrofitAdapter.MyViewHolder holder, int position) {
        holder.GuestName.setText(dataModelArrayList.get(position).getGuest_name());
        holder.GuestPurpose.setText("Purpose    : " + dataModelArrayList.get(position).getPurpose());
        holder.GuestDate.setText("Visit Date  : " + dataModelArrayList.get(position).getVisit_date() +" ("+dataModelArrayList.get(position).getVisit_time()+" WIB"+")");
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView GuestName, GuestPurpose, GuestDate;


        public MyViewHolder(View itemView) {
            super(itemView);

            GuestName    = (TextView) itemView.findViewById(R.id.guest_name);
            GuestPurpose = (TextView) itemView.findViewById(R.id.purpose);
            GuestDate    = (TextView) itemView.findViewById(R.id.visit_date);
        }

    }
}
