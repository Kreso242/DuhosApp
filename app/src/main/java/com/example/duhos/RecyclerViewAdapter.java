package com.example.duhos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> pitanja= new ArrayList<String>();
    private ArrayList<String> odgovori = new ArrayList<String>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> pitanja,ArrayList<String> odgovori, Context context) {
        this.pitanja = pitanja;
        this.odgovori = odgovori;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.textViewPitanja.setText(pitanja.get(position));
         holder.textViewOdgovori.setText(odgovori.get(position));
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(context,pitanja.get(position),Toast.LENGTH_SHORT).show();
             }
         });
    }

    @Override
    public int getItemCount() {
        return pitanja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPitanja;
        TextView textViewOdgovori;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPitanja=itemView.findViewById(R.id.idPitanje);
            textViewOdgovori=itemView.findViewById(R.id.idOdgovor);
            relativeLayout=itemView.findViewById(R.id.parentLayout);
        }
    }
}
