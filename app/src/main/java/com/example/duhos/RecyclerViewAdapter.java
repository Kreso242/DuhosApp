package com.example.duhos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.duhos.R.drawable.clicked_item_list;
import static com.example.duhos.R.drawable.list_item_background;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> pitanja= new ArrayList<String>();
    private ArrayList<String> odgovori = new ArrayList<String>();
    private Context context;
    private int srcePosition;
    private HashMap<Integer, Boolean> srce = new HashMap<>();
    //    private ArrayList<Boolean> sr = new ArrayList<>();
    private boolean srceShow = false;
    private int pitanjePosition;
    private boolean pitanjeShow = false;


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
        //  holder.textViewBrojevi.setText(String.valueOf(position+1)+".");

        //TEST

//        Iterator it = srce.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            if(/*pair.getKey().equals(position) &&*/ pair.getValue().equals(true)){
////                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
//                holder.imageButtonPunoSrce.setVisibility(View.VISIBLE);
//                holder.imageButtonPraznoSrce.setVisibility(View.GONE);
//            }
//            else {
//                holder.imageButtonPunoSrce.setVisibility(View.GONE);
//                holder.imageButtonPraznoSrce.setVisibility(View.VISIBLE);
//            }
//        }

        if (srcePosition == position && srceShow){
            holder.imageButtonPunoSrce.setVisibility(View.VISIBLE);
            holder.imageButtonPraznoSrce.setVisibility(View.GONE);
        }else{
            holder.imageButtonPunoSrce.setVisibility(View.GONE);
            holder.imageButtonPraznoSrce.setVisibility(View.VISIBLE);
        }
        if (pitanjePosition == position && pitanjeShow){
            holder.textViewOdgovori.setVisibility(View.VISIBLE);
            holder.textViewPitanja.setTextColor(Color.parseColor("#FFFFFF"));
            holder.textViewOdgovori.setTextColor(Color.parseColor("#FFFFFF"));
            holder.isExpanded = true;
            holder. relativeLayout.setBackground(context.getResources().getDrawable(clicked_item_list));
        }else{
            holder.textViewOdgovori.setVisibility(View.GONE);
            holder.textViewPitanja.setTextColor(Color.parseColor("#000000"));
            holder.isExpanded = false;
            holder.relativeLayout.setBackground(context.getResources().getDrawable(list_item_background));
        }

        holder.imageButtonPunoSrce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.imageButtonPunoSrce.setVisibility(View.GONE);
//                holder.imageButtonPraznoSrce.setVisibility(View.VISIBLE);
//                srce.remove(position);

                srceShow = false;
                notifyDataSetChanged();
//                notifyItemChanged(position);
//                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageButtonPraznoSrce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.imageButtonPunoSrce.setVisibility(View.VISIBLE);
//                holder.imageButtonPraznoSrce.setVisibility(View.GONE);
//                srce.put(position, true);

                srcePosition = position;
                srceShow = true;
                notifyDataSetChanged();
//                notifyItemChanged(position);
//                Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isExpanded==false) {
//                    holder.textViewOdgovori.setVisibility(View.VISIBLE);
//                    holder.textViewPitanja.setTextColor(Color.parseColor("#FFFFFF"));
//                    holder.textViewOdgovori.setTextColor(Color.parseColor("#FFFFFF"));
//                    holder.isExpanded=true;
//                    holder. relativeLayout.setBackground(context.getResources().getDrawable(clicked_item_list));

                    pitanjePosition = position;
                    pitanjeShow = true;
                    notifyDataSetChanged();

//                    Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
                }
                else if(holder.isExpanded==true){
//                    holder.textViewOdgovori.setVisibility(View.GONE);
//                    holder.textViewPitanja.setTextColor(Color.parseColor("#000000"));
//                    holder.isExpanded=false;
//                    holder.relativeLayout.setBackground(context.getResources().getDrawable(list_item_background));

                    pitanjeShow = false;
                    notifyDataSetChanged();

//                    Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
                }
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
        //TextView textViewBrojevi;
        RelativeLayout relativeLayout;
        ImageButton imageButtonPunoSrce;
        ImageButton imageButtonPraznoSrce;
        private boolean isExpanded=false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPitanja=itemView.findViewById(R.id.idPitanje);
            textViewOdgovori=itemView.findViewById(R.id.idOdgovor);
            relativeLayout=itemView.findViewById(R.id.parentLayout);
            // textViewBrojevi=itemView.findViewById(R.id.redniBrojPitanja);
            imageButtonPraznoSrce=itemView.findViewById(R.id.praznoSrce);
            imageButtonPunoSrce=itemView.findViewById(R.id.punoSrce);

        }
    }
}