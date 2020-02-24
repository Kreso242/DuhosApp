package com.example.duhos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import static com.example.duhos.R.drawable.clicked_item_list;
import static com.example.duhos.R.drawable.list_item_background;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> pitanja = new ArrayList<String>();
    private ArrayList<String> odgovori = new ArrayList<String>();
    private Context context;
    private ArrayList<Boolean> heart = new ArrayList<>();
    private ArrayList<String> listaFavorita = new ArrayList<String>();

    private int pitanjePosition;
    private boolean pitanjeShow = false;


    public RecyclerViewAdapter(ArrayList<String> pitanja, ArrayList<String> odgovori, ArrayList<Boolean> heart, Context context) {
        this.pitanja = pitanja;
        this.odgovori = odgovori;
        this.heart = heart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewPitanja.setText(pitanja.get(position));
        holder.textViewOdgovori.setText(odgovori.get(position));
        //  holder.textViewBrojevi.setText(String.valueOf(position+1)+".");

        final boolean hr = heart.get(position);

        holder.imageButtonPunoSrce.setVisibility(hr ? View.VISIBLE : View.GONE);//ako je true postavi puno srce, ako false onda postavi prazno srce
        holder.imageButtonPraznoSrce.setVisibility(hr ? View.GONE : View.VISIBLE);//obrnuto od ovog gore


        if (pitanjePosition == position && pitanjeShow) {
            holder.textViewOdgovori.setVisibility(View.VISIBLE);
            holder.textViewPitanja.setTextColor(Color.parseColor("#FFFFFF"));
            holder.textViewOdgovori.setTextColor(Color.parseColor("#FFFFFF"));
            holder.isExpanded = true;
            holder.relativeLayout.setBackground(context.getResources().getDrawable(clicked_item_list));
        } else {
            holder.textViewOdgovori.setVisibility(View.GONE);
            holder.textViewPitanja.setTextColor(Color.parseColor("#000000"));
            holder.isExpanded = false;
            holder.relativeLayout.setBackground(context.getResources().getDrawable(list_item_background));
        }

        holder.imageButtonPunoSrce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.set(position, false);
                for(int i=0;i<listaFavorita.size();i++){
                    if(listaFavorita.get(i)==pitanja.get(position)){
                        listaFavorita.remove(i);
                    }
                }
                holder.imageButtonPunoSrce.setVisibility(hr ? View.VISIBLE : View.GONE);
                holder.imageButtonPraznoSrce.setVisibility(hr ? View.GONE : View.VISIBLE);
                notifyDataSetChanged();
            }
        });
        holder.imageButtonPraznoSrce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.set(position, true);
                listaFavorita.add(pitanja.get(position));
                holder.imageButtonPunoSrce.setVisibility(hr ? View.VISIBLE : View.GONE);
                holder.imageButtonPraznoSrce.setVisibility(hr ? View.GONE : View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isExpanded == false) {
                    pitanjePosition = position;
                    pitanjeShow = true;
                    notifyDataSetChanged();
                } else if (holder.isExpanded == true) {
                    pitanjeShow = false;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pitanja.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPitanja;
        TextView textViewOdgovori;
        //TextView textViewBrojevi;
        RelativeLayout relativeLayout;
        ImageButton imageButtonPunoSrce;
        ImageButton imageButtonPraznoSrce;
        private boolean isExpanded = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPitanja = itemView.findViewById(R.id.idPitanje);
            textViewOdgovori = itemView.findViewById(R.id.idOdgovor);
            relativeLayout = itemView.findViewById(R.id.parentLayout);
            // textViewBrojevi=itemView.findViewById(R.id.redniBrojPitanja);
            imageButtonPraznoSrce = itemView.findViewById(R.id.praznoSrce);
            imageButtonPunoSrce = itemView.findViewById(R.id.punoSrce);

        }
    }
}