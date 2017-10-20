package com.tabian.tabfragments;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by usuario on 04/08/2017.
 */

public class AvisoAdapter extends RecyclerView.Adapter<AvisoAdapter.AvisoHolder>{

    List<Aviso> avisos;

    public AvisoAdapter(List<Aviso> avisos) {
        this.avisos = avisos;
    }

    @Override
    public AvisoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler2,parent,false);
        AvisoHolder holder= new AvisoHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AvisoHolder holder, int position) {
        Aviso a= avisos.get(position);


holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.tvNombre2.setText(a.getNombre());
        holder.tvDescrip2.setText(a.getDescripcion());




    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }

    public static  class AvisoHolder extends RecyclerView.ViewHolder
    {
        TextView tvNombre2,tvDescrip2;

        public AvisoHolder(final View itemView) {
            super(itemView);
            tvNombre2=(TextView) itemView.findViewById(R.id.tv_Nombre2);
            tvDescrip2=(TextView) itemView.findViewById(R.id.tv_Descripcion2);



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