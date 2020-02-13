package com.example.duhos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import static com.example.duhos.R.drawable.clicked_item_list;
import static com.example.duhos.R.drawable.list_item_background;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> pitanja= new ArrayList<String>();
    private ArrayList<String> odgovori = new ArrayList<String>();
    private Context context;
    private boolean isExpanded=false;


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
        holder.textViewBrojevi.setText(String.valueOf(position+1)+".");
    }

    @Override
    public int getItemCount() {
        return pitanja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewPitanja;
        TextView textViewOdgovori;
        TextView textViewBrojevi;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPitanja=itemView.findViewById(R.id.idPitanje);
            textViewOdgovori=itemView.findViewById(R.id.idOdgovor);
            relativeLayout=itemView.findViewById(R.id.parentLayout);
            textViewBrojevi=itemView.findViewById(R.id.redniBrojPitanja);

            textViewPitanja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isExpanded==false) {
                        textViewOdgovori.setVisibility(View.VISIBLE);
                        textViewPitanja.setTextColor(Color.parseColor("#FFFFFF"));
                        textViewOdgovori.setTextColor(Color.parseColor("#FFFFFF"));
                        isExpanded=true;
                        relativeLayout.setBackground(context.getResources().getDrawable(clicked_item_list));
                    }
                    else if(isExpanded==true){
                        textViewOdgovori.setVisibility(View.GONE);
                        textViewPitanja.setTextColor(Color.parseColor("#000000"));
                        isExpanded=false;
                        relativeLayout.setBackground(context.getResources().getDrawable(list_item_background));
                    }
                }
            });

            textViewOdgovori.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isExpanded==false) {
                        textViewOdgovori.setVisibility(View.VISIBLE);
                        textViewPitanja.setTextColor(Color.parseColor("#FFFFFF"));
                        textViewOdgovori.setTextColor(Color.parseColor("#FFFFFF"));
                        isExpanded=true;
                        relativeLayout.setBackground(context.getResources().getDrawable(clicked_item_list));
                    }
                    else if(isExpanded==true){
                        textViewOdgovori.setVisibility(View.GONE);
                        textViewPitanja.setTextColor(Color.parseColor("#000000"));
                        isExpanded=false;
                        relativeLayout.setBackground(context.getResources().getDrawable(list_item_background));
                    }
                }
            });
        }
    }
}