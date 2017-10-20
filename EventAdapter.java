package com.tabian.tabfragments;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.tabian.tabfragments.Tab1Fragment.dataRef;
import static com.tabian.tabfragments.Tab1Fragment.keysID;

/**
 * Created by usuario on 02/08/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

    List<Evento> event;

    public EventAdapter(List<Evento> event) {
        this.event = event;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent,false);
        EventHolder holder= new EventHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
    Evento e= event.get(position);


        if(e.getRama().equals("JM"))
            holder.itemView.setBackgroundColor(Color.parseColor("#42A5F5"));
        else if(e.getRama().equals("JF"))
            holder.itemView.setBackgroundColor(Color.parseColor("#FBA4FD"));
        else if(e.getRama().equals("Familia de la Providencia"))
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        else if(e.getRama().equals("Juventud"))
            holder.itemView.setBackgroundColor(Color.parseColor("#EF5350"));
        else if(e.getRama().equals("Madres"))
            holder.itemView.setBackgroundColor(Color.parseColor("#BA68C8"));
        else if(e.getRama().equals("Madrugadores"))
            holder.itemView.setBackgroundColor(Color.parseColor("#FFB74D"));
        else if(e.getRama().equals("Campaña del Rosario"))
            holder.itemView.setBackgroundColor(Color.parseColor("#8BC34A"));
        else if(e.getRama().equals("Obra de Familias"))
            holder.itemView.setBackgroundColor(Color.parseColor("#4DB6AC"));
        else if(e.getRama().equals("Fundadores"))
            holder.itemView.setBackgroundColor(Color.parseColor("#FF8A65"));
      /*  else if(e.getRama().equals("Matrimonios"))
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF176"));*/
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.tvNombre.setText(e.getNombre());
        holder.tvDescrip.setText(e.getDescripcion());
        holder.tvDescrip.setMaxLines(10);
        holder.tvDescrip.setMaxWidth(400);
       holder.tvFecha.setText(e.getFecha());

        if(String.valueOf(e.getMinutos()).length()<2)
        holder.tvHora.setText(e.getHora()+":0"+e.getMinutos());

        else
            holder.tvHora.setText(e.getHora()+":"+e.getMinutos());


    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public static  class EventHolder extends RecyclerView.ViewHolder
    {
        TextView tvNombre,tvDescrip,tvFecha,tvHora;

        public EventHolder(final View itemView) {
            super(itemView);
            tvNombre=(TextView) itemView.findViewById(R.id.tv_Nombre);
            tvDescrip=(TextView) itemView.findViewById(R.id.tv_Descripcion);
            tvFecha=(TextView) itemView.findViewById(R.id.tv_Fecha);
            tvHora=(TextView) itemView.findViewById(R.id.tv_Hora);


/*
            tvNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Test","Name clicked : "+getAdapterPosition());
                    dataRef.child(keysID.get(getAdapterPosition())).removeValue();
                }
            });
*/

        }
    }
}
